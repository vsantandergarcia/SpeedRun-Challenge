package com.vsantander.speedrun.ui.gameslist.item

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.vsantander.speedrun.R
import com.vsantander.speedrun.domain.model.Game
import com.vsantander.speedrun.ui.base.item.ItemView
import com.vsantander.speedrun.utils.Constants
import kotlinx.android.synthetic.main.view_item_game.view.*
import org.jetbrains.anko.dimen

class GameItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ItemView<Game>(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_item_game, this, true)
        useCompatPadding = true
        radius = context.dimen(R.dimen.game_item_radius).toFloat()
        cardElevation = context.dimen(R.dimen.game_item_elevation).toFloat()
    }

    override fun bind(item: Game) {
        titleTextView.text = item.title

        Glide
                .with(context)
                .load(item.image)
                .transition(DrawableTransitionOptions.withCrossFade(Constants.DURATION_FADE_GLIDE))
                .into(gameImageView)

    }
}