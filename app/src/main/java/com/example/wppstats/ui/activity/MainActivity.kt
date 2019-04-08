package com.example.wppstats.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.wppstats.R
import com.example.wppstats.data.dao.StatusDao
import com.example.wppstats.data.model.ObStatus
import com.example.wppstats.ui.adapter.StatusLidosAdapter
import com.example.wppstats.ui.adapter.StatusNovosAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapterNovo: StatusNovosAdapter
    private lateinit var adapterLido: StatusLidosAdapter

    private lateinit var dao: StatusDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dao = StatusDao()

        adapterNovo = StatusNovosAdapter(StatusDao().buscaStatusNovos()){
            StatusDao().marcarComoVisto(it)
            atualizaTela()
            mostrarStatus(it)
        }

        main_rec_novos.adapter = adapterNovo
        adapterLido = StatusLidosAdapter(StatusDao().buscaStatusLidos()){
            mostrarStatus(it)
        }
        main_rec_lidos.adapter = adapterLido
    }

    override fun onResume() {
        super.onResume()

        val novos = dao.buscaStatusNovos()
        val lidos = dao.buscaStatusLidos()

        if (novos.size == 0)
            text_novo.visibility = View.INVISIBLE
        else
            text_novo.visibility = View.VISIBLE

        if (lidos.size == 0)
            text_lido.visibility = View.INVISIBLE
        else
            text_lido.visibility = View.VISIBLE
    }

    private fun atualizaTela() {
        adapterNovo.notifyDataSetChanged()
        adapterLido.notifyDataSetChanged()
    }

    private fun mostrarStatus(it: ObStatus) {
        val intent = Intent(this, VizualizacaoActivity::class.java)
        intent.putExtra(VizualizacaoActivity.STATUS, it)
        startActivity(intent)
    }
}
