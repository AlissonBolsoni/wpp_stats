package br.com.bylearn.wppstats.ui.adapter

import br.com.bylearn.wppstats.data.model.ObStatus

class StatusNovosAdapter(list: ArrayList<ObStatus>, private val onClick: (ObStatus) -> Unit): BaseAdapter(list) {

    override fun criarMensagemSistema() {
        val status = getItem(0)
        if (!status.ehSistema){
             addMensagemSistema(ObStatus.criarMensagemSistema(true))
        }else{
            if (itemCount == 1)
                removeMensagemSistema()
        }
    }

    override fun onClickItem(obStatus: ObStatus) {
        onClick(obStatus)
    }
}