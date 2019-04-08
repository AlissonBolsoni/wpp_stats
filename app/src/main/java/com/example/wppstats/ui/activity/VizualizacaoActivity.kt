package com.example.wppstats.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.wppstats.R
import com.example.wppstats.data.model.ObStatus
import com.example.wppstats.utils.ImagemUtil
import kotlinx.android.synthetic.main.activity_vizualizacao.*

class VizualizacaoActivity : AppCompatActivity() {

    companion object {
        const val STATUS = "STATUS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vizualizacao)

        if (intent.hasExtra(STATUS)){
            val st = intent.getSerializableExtra(STATUS) as ObStatus
            if (st.imagem.isEmpty() && st.texto.isNotEmpty()){
                view_imagem.visibility = View.GONE
                view_text.visibility = View.VISIBLE
                view_text.text = st.texto
            }else{
                view_imagem.visibility = View.VISIBLE
                view_text.visibility = View.GONE
                ImagemUtil.montaImagem(view_imagem, st.imagem, this)
            }
        }

    }
}
