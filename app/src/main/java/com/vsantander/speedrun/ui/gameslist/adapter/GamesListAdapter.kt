package com.vsantander.speedrun.ui.gameslist.adapter

import android.view.ViewGroup
import com.vsantander.speedrun.domain.model.Game
import com.vsantander.speedrun.ui.base.adapter.RecyclerViewAdapterBase
import com.vsantander.speedrun.ui.base.adapter.ViewWrapper
import com.vsantander.speedrun.ui.base.item.ItemView
import com.vsantander.speedrun.ui.gameslist.item.GameItemView

class GamesListAdapter : RecyclerViewAdapterBase<Game, ItemView<Game>>() {

    var onClickAction: ((item: Game) -> Unit)? = null

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): ItemView<Game> {
        return GameItemView(parent.context)
            .apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun onBindViewHolder(holder: ViewWrapper<ItemView<Game>>, position: Int) {
        val game = items[position]

        holder.view.apply {
            bind(game)
        }

        holder.view.setOnClickListener { onClickAction?.invoke(items[position]) }
    }

}