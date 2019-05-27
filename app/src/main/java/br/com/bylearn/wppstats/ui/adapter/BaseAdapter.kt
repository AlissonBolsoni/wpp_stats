package br.com.bylearn.wppstats.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.bylearn.wppstats.R
import br.com.bylearn.wppstats.data.model.ObStatus
import br.com.bylearn.wppstats.util.ImagemUtil
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseAdapter(private val listaStatus: ArrayList<ObStatus>) : RecyclerView.Adapter<BaseAdapter.ViewHolder>() {

    private val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    init {
        if (listaStatus.isNotEmpty())
            criarMensagemSistema()
    }

    protected fun addMensagemSistema(obStatus: ObStatus){
        listaStatus.add(0, obStatus)
    }

    protected fun removeMensagemSistema(){
        listaStatus.removeAt(0)
    }

    abstract fun criarMensagemSistema()

    abstract fun onClickItem(obStatus: ObStatus)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)


        val view = if (viewType == ViewHolder.TIPO_STATUS_SISTEMA)
            inflater.inflate(R.layout.sistema_item, viewGroup, false)
        else
            inflater.inflate(R.layout.status_item, viewGroup, false)

        val holder = ViewHolder(view, viewGroup.context)
        holder.titulo = view.findViewById(R.id.item_status_titulo)

        if (viewType == ViewHolder.TIPO_STATUS_PESSOAS){
            holder.subtitulo = view.findViewById(R.id.item_status_subtitulo)
            holder.imagem = view.findViewById(R.id.item_status_imagem)

            holder.itemView.setOnClickListener {
                val tag = holder.itemView.tag
                if (tag != null)
                    onClickItem(tag as ObStatus)
            }

        }
        return holder

    }

    override fun getItemCount() = listaStatus.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val status = getItem(position)
        holder.itemView.tag = status
        populaTela(status, holder)
    }

    private fun populaTela(status: ObStatus, holder: ViewHolder) {
        if (!status.ehSistema){
            holder.titulo!!.text = status.usuario.nome
            holder.subtitulo!!.text = sdf.format(status.data)
            ImagemUtil.montaImagem(holder.imagem!!, status.usuario.imagem, holder.context)
        }else{
            holder.titulo!!.text = status.texto
        }
    }

    override fun getItemViewType(position: Int): Int {
        val status = getItem(position)

        if (status.ehSistema)
            return ViewHolder.TIPO_STATUS_SISTEMA


        return ViewHolder.TIPO_STATUS_PESSOAS
    }

    protected fun getItem(position: Int): ObStatus {
        return listaStatus[position]
    }

    class ViewHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView) {
        companion object {
            const val TIPO_STATUS_SISTEMA = 10
            const val TIPO_STATUS_PESSOAS = 20
        }

        var titulo: TextView? = null
        var subtitulo: TextView? = null
        var imagem: ImageView? = null
    }
}