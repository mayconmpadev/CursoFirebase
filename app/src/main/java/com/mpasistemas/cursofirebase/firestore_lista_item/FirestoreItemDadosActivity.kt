package com.mpasistemas.cursofirebase.firestore_lista_item

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.br.jafapps.bdfirestore.util.DialogProgress
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.firebase.auth.ktx.auth
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.mpasistemas.cursofirebase.R
import com.mpasistemas.cursofirebase.Util
import com.mpasistemas.cursofirebase.databinding.ActivityFirestoreItemDadosBinding
import javax.sql.DataSource

class FirestoreItemDadosActivity : AppCompatActivity() {

private val binding by  lazy {
    ActivityFirestoreItemDadosBinding.inflate(layoutInflater)
}

    var storage: FirebaseStorage? = null
    var bd: FirebaseFirestore? = null

    var uri_Imagem: Uri? = null


    var itemSelecionado: Item ? =null

    var idCategoria: Int?=null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        storage = Firebase.storage
        bd = FirebaseFirestore.getInstance()

        itemSelecionado = intent.getParcelableExtra("item")
        idCategoria = intent.getIntExtra("idCategoria",0)

        Util.exibirToast(this,"Id Categoria ${idCategoria}")

        atualizarView()




        binding.imageViewItemDadosImagem.setOnClickListener{obterImagemGaleria() }

        binding.buttonItemDadosAtualizar.setOnClickListener{ buttonAtualizar()     }

        binding.buttonItemDadosRemover.setOnClickListener{ buttonRemover()
        }


    }





    fun atualizarView(){


        binding.editTextItemDadosNome.setText(itemSelecionado?.nome)
        binding.editTextItemDadosDescricao.setText(itemSelecionado?.descricao)


        Glide.with(this).asBitmap().load(itemSelecionado?.url_imagem).listener(object :
            RequestListener<Bitmap> {

            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {

                binding.progressBarItemDadosProgressbar.visibility = View.GONE
                Util.exibirToast(baseContext,"Erro ao exibir imagem")
                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Bitmap>?,
                dataSource: com.bumptech.glide.load.DataSource?,
                isFirstResource: Boolean
            ): Boolean {

                binding.progressBarItemDadosProgressbar.visibility = View.GONE
                return false
            }


        }).into(binding.imageViewItemDadosImagem)


    }




    //---------------------------------------------AÇÕES DE CLICK------------------------------------------------------------


    fun obterImagemGaleria(){

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

        startActivityForResult(Intent.createChooser(intent,"Escolha uma Imagem"),11)

    }







    fun buttonAtualizar(){


        val nome = binding.editTextItemDadosNome.text.toString()
        val descricao = binding.editTextItemDadosDescricao.text.toString()


        if(!itemSelecionado?.nome.equals(nome)  || !itemSelecionado?.descricao.equals(descricao) || uri_Imagem != null){


            if(!nome.trim().isEmpty() &&  !descricao.trim().isEmpty()){

                if(Util.statusInternet(baseContext)){


                    if(uri_Imagem!=null){

                        uploadAtualizarImagem(nome,descricao)

                    }else{

                        atualizarDados(nome,descricao,itemSelecionado?.url_imagem.toString())

                    }
                }else{

                    Util.exibirToast(this,"Se conexão com a internet")
                }
            }else{

                Util.exibirToast(this,"Você não pode deixar os campos vazios")
            }
        }else{

            Util.exibirToast(this,"Você não alterou nenhum item")
        }

    }





    //---------------------------------------------ALTERAR DADOS FIREBASE------------------------------------------------------------



    fun uploadAtualizarImagem(nome: String, descricao: String){



        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager,"1")


        val nomeImagem = itemSelecionado?.id.toString() +".jpg"

        //  val nomeImagem = UUID.randomUUID().toString() +".jpg"


        val uid =  Firebase.auth?.currentUser?.uid

        val reference = storage!!.reference.child("Categorias")
            .child(idCategoria.toString()).child("usuarios").child(uid!!)
            .child("itens").child(nomeImagem)



        var uploadTask = reference.putFile(uri_Imagem!!)


        uploadTask.continueWithTask{ task ->

            if(!task.isSuccessful){
                task.exception.let {
                    throw it!!
                }
            }
            reference.downloadUrl

        }.addOnSuccessListener { task->

            val url = task.toString()


            dialogProgress.dismiss()


            atualizarDados(nome,descricao,url)


        }.addOnFailureListener{ error ->

            dialogProgress.dismiss()
            Util.exibirToast(baseContext,"Erro ao realizar o upload da imagem: ${error.message.toString()}")
        }

    }






    fun atualizarDados(nome: String, descricao:String, url:String){



        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager, "0")


        val uid =  Firebase.auth?.currentUser?.uid


        val reference = bd!!.collection("Categorias")
            .document(idCategoria.toString()).collection("usuarios").document(uid!!)
            .collection("itens")

        val item = hashMapOf<String,Any>(
            //  "id" to itemSelecionado?.id!!,
            "nome" to nome,
            "descricao" to descricao,
            "url_imagem" to url
        )

        reference.document(itemSelecionado?.id.toString()).update(item).addOnSuccessListener{


            dialogProgress.dismiss()
            Util.exibirToast(baseContext, "Sucesso ao alterar dados")

            finish()


        }.addOnFailureListener { error ->
            dialogProgress.dismiss()

            Util.exibirToast(baseContext, "Erro ao alterar dados: ${error.message.toString()}")
            finish()

        }




    }







    //---------------------------------------------REMOVER DADOS FIREBASE------------------------------------------------------------



    fun buttonRemover(){


        val idItem  = itemSelecionado!!.id!!
        val url = itemSelecionado?.url_imagem!!

        removerImagem(idItem,url)

    }




    fun removerImagem(idItem: String,url: String){



        val reference = storage!!.getReferenceFromUrl(url)


        reference.delete().addOnSuccessListener { task ->

            Util.exibirToast(baseContext,"Sucesso ao Remover imagem")

            removerDados(idItem)

        }.addOnFailureListener{ error ->

            Util.exibirToast(baseContext,"Falha ao Remover imagem: ${error.message.toString()}")

        }
    }







    fun removerDados(idItem:String){




        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager, "0")


        val uid =  Firebase.auth?.currentUser?.uid
        val reference = bd!!.collection("Categorias")
            .document(idCategoria.toString()).collection("usuarios").document(uid!!)
            .collection("itens")


        reference.document(idItem.toString()).delete().addOnSuccessListener{


            dialogProgress.dismiss()
            Util.exibirToast(baseContext, "Sucesso ao Remover dados")

            finish()

        }.addOnFailureListener { error ->
            dialogProgress.dismiss()

            Util.exibirToast(baseContext, "Erro ao Reomver dados: ${error.message.toString()}")
            finish()

        }





    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if(resultCode == Activity.RESULT_OK){

            if( requestCode == 11 && data != null ){ // galeria

                uri_Imagem = data.data
                binding.imageViewItemDadosImagem.setImageURI(uri_Imagem)
            }


        }

    }


}