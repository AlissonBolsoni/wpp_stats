package br.com.bylearn.wppstats.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.bylearn.wppstats.R
import br.com.bylearn.wppstats.data.model.ObStatus
import br.com.bylearn.wppstats.util.ImagemUtil
import kotlinx.android.synthetic.main.activity_vizualizacao.*

class VizualizacaoActivity : AppCompatActivity() {

    companion object {
        const val STATUS = "STATUS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vizualizacao)

        if(intent.hasExtra(STATUS)){
            val obStatus = intent.getSerializableExtra(STATUS) as ObStatus
            if (obStatus.imagem.isEmpty() && obStatus.texto.isNotEmpty()){
                vizualizacao_imagem.visibility = View.GONE
                vizualizacao_texto.visibility = View.VISIBLE
                vizualizacao_texto.text = obStatus.texto
            }else{
                vizualizacao_imagem.visibility = View.VISIBLE
                vizualizacao_texto.visibility = View.GONE
                ImagemUtil.montaImagem(vizualizacao_imagem, obStatus.imagem, this)
            }
        }


    }
}
