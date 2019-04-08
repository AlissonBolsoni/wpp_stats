package com.example.wppstats.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.wppstats.R
import com.example.wppstats.data.model.ObStatus
import com.example.wppstats.utils.ImagemUtil

abstract class BaseAdapter(private val list: ArrayList<ObStatus>): RecyclerView.Adapter<BaseAdapter.ViewHolder>() {

    abstract fun onClickItem(item: ObStatus)

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val co = viewGroup.context
        val view = LayoutInflater.from(co).inflate(R.layout.status_item, viewGroup, false)
        val itemHolder = ViewHolder(view, viewGroup.context)
        itemHolder.itemView.setOnClickListener {
            val tag = itemHolder.itemView.tag
            if (tag != null){
                onClickItem(tag as ObStatus)
            }
        }
        return itemHolder
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val st = list[position]
        viewHolder.itemView.tag = st

        ImagemUtil.montaImagem(viewHolder.usuarioImagem, st.usuario.imagem, viewHolder.context)
        viewHolder.usuarioNome.text = st.usuario.nome
    }


    class ViewHolder(itemView: View, var context: Context): RecyclerView.ViewHolder(itemView) {
        val usuarioImagem: ImageView = itemView.findViewById(R.id.item_usuario_imagem)
        val usuarioNome: TextView = itemView.findViewById(R.id.item_usuario_nome)
    }
}