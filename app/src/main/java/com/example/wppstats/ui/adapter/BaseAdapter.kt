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
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseAdapter(private val listaStatus: ArrayList<ObStatus>) :
    RecyclerView.Adapter<BaseAdapter.ViewHolder>() {

    private val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    init {
        if (!listaStatus.isEmpty()) {
            criarMensagemSistema()
        }
    }

    protected fun getSize() = listaStatus.size

    protected fun addMensagemSistema(obStatus: ObStatus) {
        listaStatus.add(0, obStatus)
    }

    protected fun removeMensagemSistema() {
        listaStatus.removeAt(0)
    }

    abstract fun onClickItem(item: ObStatus)

    override fun getItemCount() = listaStatus.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)

        val view = if (viewType == ViewHolder.TIPO_STATUS_SISTEMA)
            inflater.inflate(R.layout.sistema_item, viewGroup, false)
        else
            inflater.inflate(R.layout.status_item, viewGroup, false)

        val itemHolder = ViewHolder(view, viewGroup.context)
        itemHolder.usuarioNome = view.findViewById(R.id.item_usuario_nome)

        if (viewType == ViewHolder.TIPO_STATUS_PESSOAS) {
            itemHolder.usuarioImagem = view.findViewById(R.id.item_usuario_imagem)
            itemHolder.dataStatus = view.findViewById(R.id.item_status_data )

            itemHolder.itemView.setOnClickListener {
                val tag = itemHolder.itemView.tag
                if (tag != null) {
                    onClickItem(tag as ObStatus)
                }
            }
        }
        return itemHolder
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val st = getItem(position)
        viewHolder.itemView.tag = st

        populaTela(st, viewHolder)

    }

    abstract fun criarMensagemSistema()

    private fun populaTela(
        st: ObStatus,
        viewHolder: ViewHolder
    ) {
        if (!st.ehSistema) {
            viewHolder.usuarioNome!!.text = st.usuario.nome
            ImagemUtil.montaImagem(viewHolder.usuarioImagem!!, st.usuario.imagem, viewHolder.context)
            viewHolder.dataStatus!!.text = sdf.format(st.data)
        } else
            viewHolder.usuarioNome!!.text = st.texto
    }

    protected fun getItem(position: Int): ObStatus {
        return listaStatus[position]
    }

    override fun getItemViewType(position: Int): Int {
        val st = getItem(position)

        if (st.ehSistema)
            return ViewHolder.TIPO_STATUS_SISTEMA

        return ViewHolder.TIPO_STATUS_PESSOAS
    }


    class ViewHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView) {
        companion object {
            const val TIPO_STATUS_SISTEMA = 10
            const val TIPO_STATUS_PESSOAS = 20
        }

        var usuarioNome: TextView? = null
        var dataStatus: TextView? = null
        var usuarioImagem: ImageView? = null
    }
}