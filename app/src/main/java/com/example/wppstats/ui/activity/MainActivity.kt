package com.example.wppstats.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import br.com.alissontfb.multifilepicker.config.FilePicker
import br.com.alissontfb.multifilepicker.utils.PARAM_RESULT_ITEMS_PATHS
import br.com.alissontfb.multifilepicker.utils.REQUEST_CODE_TO_RESULT
import com.example.wppstats.BuildConfig
import com.example.wppstats.R
import com.example.wppstats.data.dao.StatusDao
import com.example.wppstats.data.model.ObStatus
import com.example.wppstats.data.model.ObUsuario
import com.example.wppstats.ui.adapter.StatusLidosAdapter
import com.example.wppstats.ui.adapter.StatusNovosAdapter
import com.example.wppstats.utils.ImagemUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.status_item.*
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object {
        const val CAMERA_REQUEST = 159
    }

    private lateinit var adapterNovo: StatusNovosAdapter
    private lateinit var adapterLido: StatusLidosAdapter

    private var caminho: String = ""

    private lateinit var dao: StatusDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val meuUsusário = ObUsuario("Meu Usuário")

        ImagemUtil.montaImagem(item_usuario_imagem, meuUsusário.imagem, this)
        item_usuario_nome.text = "Meu Status"
        item_status_data.text = "Clique para criar um novo Status"
        item_status_divisor.visibility = View.GONE

        main_novo.setOnClickListener {
            criarFilePicker()
        }

        main_fab_camera.setOnClickListener {
            chamaCamera()
        }

        main_fab_create.setOnClickListener {
            chamaDialogo()
        }
    }

    private fun chamaDialogo() {
        val builder = AlertDialog.Builder(this)
        val text = EditText(this)
        builder.setTitle(getString(R.string.app_name))
        builder.setMessage("Escreva um texto para o Status!!!")
        builder.setView(text)
        builder.setPositiveButton(getText(android.R.string.ok)) { _, _ ->
            val texto = text.text.toString().trim()
            if (!texto.isEmpty())
                StatusDao().criaStatusTexto(texto)
            else
                Toast.makeText(this, "Não pode criar um Status sem texto", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton(getText(android.R.string.cancel)) { _, _ -> null }
        val dialog = builder.create()
        dialog.show()
    }


    private fun chamaCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        caminho = "${getExternalFilesDir(null)}/${System.currentTimeMillis()}.jpg"
        val arquivoFoto = File(caminho)

        intent.putExtra(
            MediaStore.EXTRA_OUTPUT,
            FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + ".provider", arquivoFoto
            )
        )

        startActivityForResult(intent, CAMERA_REQUEST)
    }

    private fun criarFilePicker() {
        FilePicker
            .Builder(this)
            .maxFiles(1)
            .showFiles(false)
            .showImages(true)
            .showAudios(false)
            .showVideos(false)
            .saveAudios(false)
            .saveImages(false)
            .saveVideos(false)
            .setSaveFolder(getString(R.string.app_name))
            .setProvider(BuildConfig.APPLICATION_ID + ".provider")
            .build()
    }

    override fun onResume() {
        super.onResume()
        dao = StatusDao()

        adapterNovo = StatusNovosAdapter(StatusDao().buscaStatusNovos()) {
            StatusDao().marcarComoVisto(it)
            atualizaTela()
            mostrarStatus(it)
        }

        main_rec_novos.adapter = adapterNovo
        adapterLido = StatusLidosAdapter(StatusDao().buscaStatusLidos()) {
            mostrarStatus(it)
        }
        main_rec_lidos.adapter = adapterLido

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (Activity.RESULT_OK == resultCode ) {
            if (REQUEST_CODE_TO_RESULT == requestCode && data != null) {
                val paths = data.getSerializableExtra(PARAM_RESULT_ITEMS_PATHS) as ArrayList<String>
                for (path in paths) {
                    StatusDao().criaStatusImagem(path)
                }
            } else if (requestCode == CAMERA_REQUEST) {
                    StatusDao().criaStatusImagem(caminho)
            }
        }
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
