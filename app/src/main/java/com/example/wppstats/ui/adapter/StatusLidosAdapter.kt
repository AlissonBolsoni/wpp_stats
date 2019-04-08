package com.example.wppstats.ui.adapter

import com.example.wppstats.data.model.ObStatus

class StatusLidosAdapter(list: ArrayList<ObStatus>, private val onCLick: (ObStatus) -> Unit): BaseAdapter(list) {

    override fun onClickItem(item: ObStatus) {
        onCLick(item)
    }
}