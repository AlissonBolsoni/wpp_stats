package br.com.bylearn.wppstats.ui.activity

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import br.com.alissontfb.multifilepicker.config.CheckPermissions
import br.com.alissontfb.multifilepicker.config.FilePicker
import br.com.alissontfb.multifilepicker.utils.PARAM_RESULT_ITEMS_PATHS
import br.com.alissontfb.multifilepicker.utils.PERMISSION_REQUEST_STORAGE
import br.com.alissontfb.multifilepicker.utils.REQUEST_CODE_TO_RESULT
import br.com.bylearn.wppstats.BuildConfig
import br.com.bylearn.wppstats.R
import br.com.bylearn.wppstats.data.dao.StatusDao
import br.com.bylearn.wppstats.data.model.ObStatus
import br.com.bylearn.wppstats.data.model.ObUsuario
import br.com.bylearn.wppstats.ui.adapter.StatusLidosAdapter
import br.com.bylearn.wppstats.ui.adapter.StatusNovosAdapter
import br.com.bylearn.wppstats.util.ImagemUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.status_item.*
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object {
        const val CAMERA_RESULT = 741
    }

    private lateinit var adapterNovo: StatusNovosAdapter
    private lateinit var adapterLidos: StatusLidosAdapter

    private var caminho: String = ""

    private lateinit var dao: StatusDao

    private lateinit var permicao: CheckPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permicao = CheckPermissions(this, null)
        permicao.verifyPermissions(PERMISSION_REQUEST_STORAGE,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA))

        val meuUsuario = ObUsuario.meuUsuario()
        ImagemUtil.montaImagem(item_status_imagem, meuUsuario.imagem, this)
        item_status_titulo.text = "Meu Status"
        item_status_subtitulo.text = "Clique para criar um novo status"
        item_status_divisor.visibility = View.GONE

        main_novo_galeria.setOnClickListener {
            chamarLibGaleria()
        }

        main_fab_camera.setOnClickListener {
            chamaCamera()
        }

        main_fab_texto.setOnClickListener {
            ChamaDialogo()
        }

    }

    override fun onResume() {
        super.onResume()
        dao = StatusDao()

        adapterNovo = StatusNovosAdapter(dao.buscaStatusNovos()){
            mostrarStatus(it)
            dao.marcarComoVisto(it)
            atualizarTela()
        }
        main_rec_novos.adapter = adapterNovo

        adapterLidos = StatusLidosAdapter(dao.buscaStatusLidos()){
            mostrarStatus(it)
        }
        main_rec_lidos.adapter = adapterLidos

    }

    private fun atualizarTela() {
        adapterNovo.notifyDataSetChanged()
        adapterLidos.notifyDataSetChanged()
    }

    private fun mostrarStatus(obStatus: ObStatus) {
        val intent = Intent(this, VizualizacaoActivity::class.java)
        intent.putExtra(VizualizacaoActivity.STATUS, obStatus)
        startActivity(intent)
    }


    private fun ChamaDialogo() {
        val builder = AlertDialog.Builder(this)
        val editText = EditText(this)
        builder.setTitle(getString(R.string.app_name))
        builder.setMessage("Escreva um texto para o seu status!")
        builder.setView(editText)
        builder.setPositiveButton("Ok"){_, _ ->
            val texto = editText.text.toString().trim()
            if (texto.isNotEmpty()){
                dao.criaStatusTexto(texto)
            }else{
                Toast.makeText(this, "Não pode ser criado statos sem nenhum texto", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun chamaCamera() {

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        caminho = "${getExternalFilesDir(null)}/${System.currentTimeMillis()}.jpg"
        val arquivoFoto = File(caminho)

        intent.putExtra(MediaStore.EXTRA_OUTPUT,
            FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", arquivoFoto))

        startActivityForResult(intent, CAMERA_RESULT)

    }

    private fun chamarLibGaleria() {
        FilePicker.Builder(this)
            .maxFiles(1)
            .showImages(true)
            .showVideos(false)
            .showAudios(false)
            .showFiles(false)
            .saveImages(false)
            .saveVideos(false)
            .saveAudios(false)
            .setProvider(BuildConfig.APPLICATION_ID +".provider")
            .setSaveFolder(getString(R.string.app_name))
            .build()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null){
            if (requestCode == REQUEST_CODE_TO_RESULT){
                val caminhosRetorno = data.getSerializableExtra(PARAM_RESULT_ITEMS_PATHS) as ArrayList<String>
                for (caminhoRetorno in caminhosRetorno){
                    dao.criaStatusImagem(caminhoRetorno)
                }
            } else if (requestCode == CAMERA_RESULT){
                dao.criaStatusImagem(caminho)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (resultado in grantResults){
            if (resultado == PackageManager.PERMISSION_DENIED){
                permicao.alertPermissionValidation()
                break
            }
        }

    }


}
