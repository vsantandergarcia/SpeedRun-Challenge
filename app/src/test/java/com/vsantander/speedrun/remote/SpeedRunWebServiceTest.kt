package com.vsantander.speedrun.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.vsantander.speedrun.data.remote.SpeedRunWebService
import com.vsantander.speedrun.data.remote.model.DefaultResponseWebService
import com.vsantander.speedrun.data.remote.model.GameTO
import com.vsantander.speedrun.data.remote.model.RunTO
import com.vsantander.speedrun.data.remote.model.UserTO
import com.vsantander.speedrun.extension.fromJson
import com.vsantander.speedrun.utils.factory.DataFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets

class SpeedRunWebServiceTest {

    private lateinit var service: SpeedRunWebService

    private lateinit var mockWebServer: MockWebServer

    @Before
    @Throws(IOException::class)
    fun createService() {
        mockWebServer = MockWebServer()

        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create<SpeedRunWebService>(SpeedRunWebService::class.java)
    }

    @After
    @Throws(IOException::class)
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    @Throws(JsonSyntaxException::class)
    fun getListGamesSuccess() {
        val fileContent = getFileContentAsString("games.json")
        val gamesTOList = Gson().fromJson<DefaultResponseWebService<List<GameTO>>>(fileContent).data

        mockWebServer.enqueue(MockResponse().setBody(fileContent))

        val testObserver = service.getListGames().test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        MatcherAssert.assertThat(result.data.size, `is`(gamesTOList.size))
        assert(result.data == gamesTOList)
    }

    @Test
    fun getListGamesBadRequest() {
        mockWebServer.enqueue(MockResponse().setBody("{error:\"bad request\"").setResponseCode(400))

        val testObserver = service.getListGames().test()
        testObserver.awaitTerminalEvent()

        testObserver.assertNotComplete()
        testObserver.assertError(HttpException::class.java)
    }

    @Test
    @Throws(JsonSyntaxException::class)
    fun getListRunsFromIdSuccess() {
        val fileContent = getFileContentAsString("runs.json")
        val runTOList = Gson().fromJson<DefaultResponseWebService<List<RunTO>>>(fileContent).data

        mockWebServer.enqueue(MockResponse().setBody(fileContent))

        val userId = DataFactory.randomUuid()
        val testObserver = service.getListRunsFromId(userId).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        MatcherAssert.assertThat(result.data.size, `is`(runTOList.size))
        assert(result.data == runTOList)
    }

    @Test
    fun getListRunsFromIdBadRequest() {
        mockWebServer.enqueue(MockResponse().setBody("{error:\"bad request\"").setResponseCode(400))

        val userId = DataFactory.randomUuid()
        val testObserver = service.getListRunsFromId(userId).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertNotComplete()
        testObserver.assertError(HttpException::class.java)
    }

    @Test
    @Throws(JsonSyntaxException::class)
    fun getUserSuccess() {
        val fileContent = getFileContentAsString("user.json")
        val userTO = Gson().fromJson<DefaultResponseWebService<UserTO>>(fileContent).data

        mockWebServer.enqueue(MockResponse().setBody(fileContent))

        val userId = DataFactory.randomUuid()
        val testObserver = service.getUserFromId(userId).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        assert(result.data == userTO)
    }

    @Test
    fun getUserBadRequest() {
        mockWebServer.enqueue(MockResponse().setBody("{error:\"bad request\"").setResponseCode(400))

        val userId = DataFactory.randomUuid()
        val testObserver = service.getUserFromId(userId).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertNotComplete()
        testObserver.assertError(HttpException::class.java)
    }

    @Throws(IOException::class)
    private fun getFileContentAsString(fileName: String): String {
        val inputStream =
                javaClass.classLoader.getResourceAsStream("ws-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        return source.readString(StandardCharsets.UTF_8)
    }
}