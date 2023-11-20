package com.mpasistemas.cursofirebase.storage

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.mpasistemas.cursofirebase.R
import com.mpasistemas.cursofirebase.Util
import com.mpasistemas.cursofirebase.databinding.ActivityStorageDownloadBinding

class StorageDownloadActivity : AppCompatActivity(), View.OnClickListener {
private val binding by lazy {
    ActivityStorageDownloadBinding.inflate(layoutInflater)
}

    var storage: FirebaseStorage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.buttonStorageDownloadDownload.setOnClickListener(this)
        binding.buttonStorageDownloadRemover.setOnClickListener(this)


        binding.progressBarStorageDownload.visibility = View.GONE

        storage = Firebase.storage
    }




    //---------------------------------------AÇÕES DE CLICK-------------------------------------


    override fun onClick(p0: View?) {


        when(p0?.id){

            binding.buttonStorageDownloadDownload.id -> {

                button_Download()
            }
            binding.buttonStorageDownloadRemover.id -> {

                button_Remover()
            }


        }
    }





    fun button_Download(){


        if (Util.statusInternet(baseContext)){

            download_Imagem_3_Nome()

        }else{

            Util.exibirToast(baseContext,"Sem conexão com a Internet")
        }

    }


    fun button_Remover(){

        if (Util.statusInternet(baseContext)){

            remover_Imagem_2_Nome()

        }else{

            Util.exibirToast(baseContext,"Sem conexão com a Internet")
        }


    }















    //---------------------------------------DOWNLOAD IMAGEM FIREBASE-------------------------------------



    fun download_Imagem_1_Url(){


        val urlimagem = "https://firebasestorage.googleapis.com/v0/b/fir-cursosf.appspot.com/o/jafapps.png?alt=media&token=5c76a1aa-63ba-4225-aa36-effc2583262a"

        Glide.with(baseContext).asBitmap().load(urlimagem).placeholder(R.drawable.ic_progressbar_24).into(binding.imageViewStorageDownload)


    }




    fun download_Imagem_2_Url(){

        binding.progressBarStorageDownload.visibility = View.VISIBLE


        val urlimagem = "https://firebasestorage.googleapis.com/v0/b/fir-cursosf.appspot.com/o/jafapps.png?alt=media&token=5c76a1aa-63ba-4225-aa36-effc2583262a"

        Glide.with(baseContext).asBitmap().load(urlimagem).listener(object :
            RequestListener<Bitmap> {


            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {

                Util.exibirToast(baseContext,"Erro ao realizar o Download da imagem: ${e.toString()}")
                binding.progressBarStorageDownload.visibility = View.GONE

                return false
            }


            override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean ): Boolean {

                Util.exibirToast(baseContext,"Sucesso ao realizar o Download da imagem:")
                binding.progressBarStorageDownload.visibility = View.GONE

                return false
            }


        }).into(binding.imageViewStorageDownload)

    }







    fun download_Imagem_3_Nome(){


        //      val uid = Firebase.auth.currentUser?.uid

        val reference = storage!!.reference.child("imagem1").child("jafapps.png")

        ///   val reference = storage!!.reference.child("arquivosusuarios").child(uid.toString()).child("nomeImagem.jpg")

        reference.downloadUrl.addOnSuccessListener { task ->


            binding.progressBarStorageDownload.visibility = View.VISIBLE


            val urlimagem = task.toString()

            Glide.with(baseContext).asBitmap().load(urlimagem).listener(object :
                RequestListener<Bitmap> {


                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {

                    Util.exibirToast(baseContext,"Erro ao realizar o Download da imagem: ${e.toString()}")
                    binding.progressBarStorageDownload.visibility = View.GONE

                    return false
                }


                override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean ): Boolean {

                    Util.exibirToast(baseContext,"Sucesso ao realizar o Download da imagem:")
                    binding.progressBarStorageDownload.visibility = View.GONE

                    return false
                }


            }).into(binding.imageViewStorageDownload)






        }.addOnFailureListener{ error ->

            Util.exibirToast(baseContext,"Erro ao acessar imagem: ${error.message.toString()}")


        }



    }





















    //---------------------------------------REMOVER IMAGEM FIREBASE-------------------------------------



    fun remover_Imagem_1_Url(){

        val url = "https://firebasestorage.googleapis.com/v0/b/fir-cursosf.appspot.com/o/imagem1%2Fjafapps.png?alt=media&token=9eb57b88-d9a7-4835-9a48-3909c09389ae"


        val reference = storage!!.getReferenceFromUrl(url)


        reference.delete().addOnSuccessListener { task ->

            Util.exibirToast(baseContext,"Sucesso ao Remover imagem")

        }.addOnFailureListener{ error ->

            Util.exibirToast(baseContext,"Falha ao Remover imagem: ${error.message.toString()}")

        }

        /*

        reference.delete().addOnCompleteListener{ task ->

            if(task.isSuccessful){
                Util.exibirToast(baseContext,"Sucesso ao Remover imagem")
            }else{
                Util.exibirToast(baseContext,"Falha ao Remover imagem: ${task.exception.toString()}")
            }
        }
         */


    }



    fun remover_Imagem_2_Nome( ){

        val nome = "jafapps.png"

        val reference = storage!!.reference.child("imagem1").child(nome)


        reference.delete().addOnSuccessListener { task ->

            Util.exibirToast(baseContext,"Sucesso ao Remover imagem")

        }.addOnFailureListener{ error ->

            Util.exibirToast(baseContext,"Falha ao Remover imagem: ${error.message.toString()}")

        }
    }





}