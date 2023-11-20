package com.mpasistemas.cursofirebase.storage

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.FileProvider
import com.br.jafapps.bdfirestore.util.DialogProgress
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.mpasistemas.cursofirebase.R
import com.mpasistemas.cursofirebase.Util
import com.mpasistemas.cursofirebase.databinding.ActivityStorageUploadBinding
import java.io.ByteArrayOutputStream
import java.io.File

class StorageUploadActivity : AppCompatActivity(), View.OnClickListener {

private val binding by lazy {
    ActivityStorageUploadBinding.inflate(layoutInflater)
}
    var uri_Imagem: Uri? = null
    var storage: FirebaseStorage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.buttonStorageUploadEnviar.setOnClickListener(this)


        storage = Firebase.storage

    }














    //---------------------------------------AÇÕES DE CLICK-------------------------------------


    override fun onClick(p0: View?) {


        when(p0?.id){


            binding.buttonStorageUploadEnviar.id -> {

                if( uri_Imagem != null){
                    if(Util.statusInternet(this)){
                        uploadImagem4()
                    }else{
                        Util.exibirToast(baseContext,"Sem conexão com a Internet")
                    }
                }else{
                    Util.exibirToast(baseContext,"Você não selecionou uma imagem")
                }
            }
        }
    }













    //---------------------------------------MENU SUPERIOR DIREITO------------------------------------



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_storage_upload,menu)
        return super.onCreateOptionsMenu(menu)
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when(item.itemId){

            R.id.menu_item_galeria -> {

                obterImagemGaleria()
            }

            R.id.menu_item_camera -> {

                obterImagemCamera()
            }

            else -> return true
        }

        return super.onOptionsItemSelected(item)
    }














    //---------------------------------------METODOS DE CLICKS GALERIA E CAMERA-------------------------------------


    fun obterImagemGaleria(){

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

        startActivityForResult(Intent.createChooser(intent,"Escolha uma Imagem"),11)

    }




    fun obterImagemCamera(){

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

            val contentValues = ContentValues()

            contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"image/jpeg")

            val resolver = contentResolver
            uri_Imagem = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

        }else{

            val autorizacao = "com.jafapps.firebasecursosf"
            val diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val nomeImagem = diretorio.path + "/CursoFS"+ System.currentTimeMillis() +".jpg"
            val file = File(nomeImagem)
            uri_Imagem = FileProvider.getUriForFile(baseContext,autorizacao,file)
        }




        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri_Imagem)
        startActivityForResult(intent,22)
    }




















    //---------------------------------------UPLOAD DE IMAGEM-------------------------------------


    fun uploadImagem1(){

        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager,"1")

        val bitmap = (binding.imageViewStorageUpload.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,10,baos)
        val data = baos.toByteArray()


        val reference = storage!!.reference.child("imagens/uploadImagem1.jpg")


        var uploadTask = reference.putBytes(data)


        uploadTask.addOnSuccessListener {

            dialogProgress.dismiss()

            Util.exibirToast(baseContext,"Sucesso ao realizar o upload da imagem")

        }.addOnFailureListener{ error ->

            dialogProgress.dismiss()
            Util.exibirToast(baseContext,"Erro ao realizar o upload da imagem: ${error.message.toString()}")

        }


    }











    fun uploadImagem2(){

        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager,"1")

        val reference = storage!!.reference.child("imagens/uploadImagem2.jpg")

        var uploadTask = reference.putFile(uri_Imagem!!)


        uploadTask.addOnSuccessListener {

            dialogProgress.dismiss()

            Util.exibirToast(baseContext,"Sucesso ao realizar o upload da imagem")

        }.addOnFailureListener{ error ->

            dialogProgress.dismiss()
            Util.exibirToast(baseContext,"Erro ao realizar o upload da imagem: ${error.message.toString()}")
        }
    }










    fun uploadImagem3(){

        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager,"1")

        val reference = storage!!.reference.child("imagens/uploadImagem3.jpg")

        var uploadTask = reference.putFile(uri_Imagem!!)


        uploadTask.continueWithTask{ task ->

            if(!task.isSuccessful){
                task.exception.let {
                    throw it!!
                }
            }
            reference.downloadUrl

        }.addOnSuccessListener { task->

            var url = task.toString()

            //  Log.d("testejone",url)

            dialogProgress.dismiss()
            Util.exibirToast(baseContext,"Sucesso ao realizar o upload da imagem")

        }.addOnFailureListener{ error ->

            dialogProgress.dismiss()
            Util.exibirToast(baseContext,"Erro ao realizar o upload da imagem: ${error.message.toString()}")
        }

        /*
                uploadTask.continueWithTask{ task ->

                    if(!task.isSuccessful){
                        task.exception.let {
                            throw it!!
                        }
                    }
                    reference.downloadUrl

                }.addOnCompleteListener{ task ->

                    if( task.isSuccessful){

                        val url = task.result.toString()
                        Util.exibirToast(baseContext,"Sucesso ao realizar o upload da imagem")
                    }else{

                        Util.exibirToast(baseContext,"Erro ao realizar o upload da imagem: ${task.exception.toString()}")
                    }
                }

         */



    }









    fun uploadImagem4(){



        Glide.with(baseContext).asBitmap().load(uri_Imagem).
        apply(RequestOptions.overrideOf(1024,768)).listener(object: RequestListener<Bitmap> {


            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {

                Util.exibirToast(baseContext,"Erro ao diminuir imagem")

                return false
            }

            override fun onResourceReady(bitmap: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                val dialogProgress = DialogProgress()
                dialogProgress.show(supportFragmentManager,"1")


                val baos = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.JPEG,50,baos)
                val data = baos.toByteArray()


                val reference = storage!!.reference.child("imagens/uploadImagem44.jpg")

                //uid == dSi6Ss4QJEPJauXw4fME4YJRZTa2
                //    val uid = Firebase.auth.currentUser?.uid

                //    val reference = storage!!.reference.child("arquivosusuarios").child(uid.toString()).child("nomeImagem.jpg")


                var uploadTask = reference.putBytes(data)


                uploadTask.addOnSuccessListener {

                    dialogProgress.dismiss()

                    Util.exibirToast(baseContext,"Sucesso ao realizar o upload da imagem")

                }.addOnFailureListener{ error ->

                    dialogProgress.dismiss()
                    Util.exibirToast(baseContext,"Erro ao realizar o upload da imagem: ${error.message.toString()}")
                }

                return false
            }


        }).submit()





    }

































    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if(resultCode == Activity.RESULT_OK){

            if( requestCode == 11 && data != null ){ // galeria

                uri_Imagem = data.data
                binding.imageViewStorageUpload.setImageURI(uri_Imagem)
            }



            else if(requestCode == 22 && uri_Imagem != null){// camera

                binding.imageViewStorageUpload.setImageURI(uri_Imagem)
            }
        }

    }







}