package com.mpasistemas.cursofirebase

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mpasistemas.cursofirebase.databinding.ActivityMainBinding
import com.mpasistemas.cursofirebase.firestore.FirestoreGravarAlterarRemoverActivity
import com.mpasistemas.cursofirebase.firestore.FirestoreLerDadosActivity
import com.mpasistemas.cursofirebase.firestore_lista_categoria.FirestoreListaCategoriaActivity
import com.mpasistemas.cursofirebase.storage.StorageDownloadActivity
import com.mpasistemas.cursofirebase.storage.StorageUploadActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.cardViewMainDowloadImagem.setOnClickListener(this)
        binding.cardViewMainUploadImagem.setOnClickListener(this)
        binding.cardViewMainLerDados.setOnClickListener(this)
        binding.cardViewMainGravarAlterarRemoverDados.setOnClickListener(this)
        binding.cardViewMainCategorias.setOnClickListener(this)
        binding.cardViewMainDeslogar.setOnClickListener(this)

        permissao()
        ouvinteAutenticacao()


    }


    //---------------------------------------PERMISSAO------------------------------------


    fun permissao() {

        val permissoes = arrayOf<String>(
           // Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )

        Util.permissao(this, 100, permissoes)

    }


    //---------------------------------------OUVINTE AUTENTICACAO-------------------------------------


    fun ouvinteAutenticacao() {

        Firebase.auth.addAuthStateListener { authAtual ->

            if (authAtual.currentUser != null) {

                Util.exibirToast(baseContext, "Usuário Logado")
            } else {

                Util.exibirToast(baseContext, "Usuário Deslogado")
            }
        }

    }


    //---------------------------------------AÇÕES DE CLICK-------------------------------------

    override fun onClick(p0: View?) {

        when (p0?.id) {


            binding.cardViewMainDowloadImagem.id -> {

                 startActivity(Intent(this, StorageDownloadActivity::class.java))
            }

            binding.cardViewMainUploadImagem.id -> {
                startActivity(Intent(this, StorageUploadActivity::class.java))

            }

            binding.cardViewMainLerDados.id -> {
                startActivity(Intent(this, FirestoreLerDadosActivity::class.java))

            }

            binding.cardViewMainGravarAlterarRemoverDados.id -> {
                startActivity(Intent(this, FirestoreGravarAlterarRemoverActivity::class.java))
            }

            binding.cardViewMainCategorias.id -> {
                startActivity(Intent(this, FirestoreListaCategoriaActivity::class.java))

            }

            binding.cardViewMainDeslogar.id -> {

                finish()
                Firebase.auth.signOut()
                startActivity(Intent(this, AberturaActivity::class.java))
            }

            else -> false

        }

    }


    //---------------------------------------PERMISSAO------------------------------------

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (result in grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {

                Util.exibirToast(baseContext, "Aceite as permissões para funcionar o aplicativo")
                finish()
                break
            }
        }


    }
}

