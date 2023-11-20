package com.mpasistemas.cursofirebase.firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.br.jafapps.bdfirestore.util.DialogProgress
import com.google.firebase.firestore.FirebaseFirestore
import com.mpasistemas.cursofirebase.R
import com.mpasistemas.cursofirebase.Util
import com.mpasistemas.cursofirebase.databinding.ActivityFirestoreGravarAlterarRemoverBinding
import com.mpasistemas.cursofirebase.databinding.ActivityMainBinding

class FirestoreGravarAlterarRemoverActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy {
        ActivityFirestoreGravarAlterarRemoverBinding.inflate(layoutInflater)
    }

    var bd: FirebaseFirestore? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.buttonFirestoreGravarAlterarRemoverSalvar.setOnClickListener(this)
        binding.buttonFirestoreGravarAlterarRemoverAlterar.setOnClickListener(this)
        binding.buttonFirestoreGravarAlterarRemoverRemover.setOnClickListener(this)


        bd = FirebaseFirestore.getInstance()

    }




    //------------------------------------------------CLICKS------------------------------------------------------------

    override fun onClick(p0: View?) {


        when (p0?.id) {


            binding.buttonFirestoreGravarAlterarRemoverSalvar.id -> {

                buttonSalvar()

            }
            binding.buttonFirestoreGravarAlterarRemoverAlterar.id -> {

                buttonAlterar()


            }
            binding.buttonFirestoreGravarAlterarRemoverRemover.id -> {


                buttonRemover()


            }
            else -> return


        }
    }
























    //---------------------------------------------AÇÃO DE CLICKS------------------------------------------------------------


    fun buttonSalvar() {


        val nome = binding.editTextFirestoreGravarAlterarRemoverGerenteNome.text.toString()
        val idadeString = binding.editTextFirestoreGravarAlterarRemoverGerenteIdade.text.toString()
        val idNomePasta = binding.editTextFirestoreGravarAlterarRemoverNomePasta.text.toString()



        if (!nome.trim().isEmpty() && !idadeString.trim().isEmpty() && !idNomePasta.trim().isEmpty()) {


            if (Util.statusInternet(baseContext)) {


                val idade = idadeString.toInt()


                salvarDados(nome, idade, idNomePasta)

            } else {

                Util.exibirToast(baseContext, "Sem conexão com a Internet")
            }
        } else {

            Util.exibirToast(baseContext, "Insira todos campos de forma correta")
        }

    }








    fun buttonAlterar(){


        val nome = binding.editTextFirestoreGravarAlterarRemoverGerenteNome.text.toString()
        val idadeString = binding.editTextFirestoreGravarAlterarRemoverGerenteIdade.text.toString()
        val idNomePasta = binding.editTextFirestoreGravarAlterarRemoverNomePasta.text.toString()



        if (!nome.trim().isEmpty() && !idadeString.trim().isEmpty() && !idNomePasta.trim().isEmpty()) {


            if (Util.statusInternet(baseContext)) {


                val idade = idadeString.toInt()

                alterarDados(nome,idade, idNomePasta)

            } else {

                Util.exibirToast(baseContext, "Sem conexão com a Internet")
            }
        } else {

            Util.exibirToast(baseContext, "Insira todos campos de forma correta")
        }






    }





    fun buttonRemover(){



        val idNomePasta =binding.editTextFirestoreGravarAlterarRemoverNomePasta.text.toString()



        if ( !idNomePasta.trim().isEmpty()) {


            if (Util.statusInternet(baseContext)) {


                removerDados(idNomePasta)

            } else {

                Util.exibirToast(baseContext, "Sem conexão com a Internet")
            }
        } else {

            Util.exibirToast(baseContext, "Insira todos campos de forma correta")
        }






    }





















    //---------------------------------------------SALVAR DADOS FIREBASE------------------------------------------------------------


    fun salvarDados(nome: String, idade: Int, idNomePasta: String) {


        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager, "0")


        val reference = bd!!.collection("Gerentes")
        val gerente = Gerente(nome, idade, true)


        reference.document(idNomePasta).set(gerente).addOnSuccessListener {

            dialogProgress.dismiss()
            Util.exibirToast(baseContext, "Sucesso ao gravar dados")


        }.addOnFailureListener { error ->

            dialogProgress.dismiss()
            Util.exibirToast(baseContext, "Erro ao gravar dados: ${error.message.toString()}")


        }


        /*
             var gerente = hashMapOf(

                "nome" to nome,
                "idade" to idade,
                "fumante" to false
                )



         reference.add(gerente).addOnSuccessListener {
         reference.document().set(gerente).addOnSuccessListener {

         reference.document(idNomePasta).set(gerente).addOnCompleteListener { task ->

                 dialogProgress.dismiss()

             if(task.isSuccessful){

                   Util.exibirToast(baseContext,"Sucesso ao gravar dados")

              }else{

                    Util.exibirToast(baseContext,"Erro ao gravar dados: ${task.exception.toString()}")
             }
         }
    */
    }











    //---------------------------------------------ALTERAR DADOS FIREBASE------------------------------------------------------------


    fun alterarDados(nome: String, idade: Int, idNomePasta: String){



        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager, "0")


        val reference = bd!!.collection("Gerentes")

        val gerente = hashMapOf(

            "nome" to nome,
            "idade" to idade,
            "fumante" to false
        )


        reference.document(idNomePasta).update(gerente as Map<String, Any>).addOnSuccessListener{


            dialogProgress.dismiss()
            Util.exibirToast(baseContext, "Sucesso ao alterar dados")


        }.addOnFailureListener { error ->
            dialogProgress.dismiss()

            Util.exibirToast(baseContext, "Erro ao alterar dados: ${error.message.toString()}")

        }



        /*
      val gerente = hashMapOf(

          "nome" to gerente1.nome,
          "idade" to gerente1.idade,
          "fumante" to gerente1.fumante

      )


        reference.document(idNomePasta).update(gerente).addOnCompleteListener { task ->

            dialogProgress.dismiss()

            if(task.isSuccessful){

                Util.exibirToast(baseContext,"Sucesso ao alterar dados")

            }else{

                Util.exibirToast(baseContext,"Erro ao alterar dados: ${task.exception.toString()}")
            }
        }


         */


    }














    //---------------------------------------------REMOVER DADOS FIREBASE------------------------------------------------------------



    fun removerDados(idNomePasta: String){



        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager, "0")


        val reference = bd!!.collection("Gerentes")


        reference.document(idNomePasta).delete().addOnSuccessListener{


            dialogProgress.dismiss()
            Util.exibirToast(baseContext, "Sucesso ao Remover dados")


        }.addOnFailureListener { error ->
            dialogProgress.dismiss()

            Util.exibirToast(baseContext, "Erro ao Reomver dados: ${error.message.toString()}")

        }


        /*

                reference.document(idNomePasta).delete().addOnCompleteListener { task ->

                    dialogProgress.dismiss()

                    if(task.isSuccessful){

                        Util.exibirToast(baseContext,"Sucesso ao alterar dados")

                    }else{

                        Util.exibirToast(baseContext,"Erro ao alterar dados: ${task.exception.toString()}")
                    }
                }

         */



    }


}