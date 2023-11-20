package com.mpasistemas.cursofirebase.firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.br.jafapps.bdfirestore.util.DialogProgress
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mpasistemas.cursofirebase.R
import com.mpasistemas.cursofirebase.Util
import com.mpasistemas.cursofirebase.databinding.ActivityFirestoreLerDadosBinding

class FirestoreLerDadosActivity : AppCompatActivity() {
private val binding by lazy {
    ActivityFirestoreLerDadosBinding.inflate(layoutInflater)
}

    var bd: FirebaseFirestore? = null
    var reference: CollectionReference? =null
    // var referenceDocumento : DocumentReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        bd = FirebaseFirestore.getInstance()


        reference = bd!!.collection("Gerentes")


        ouvinte_6()

    }







    //----------------------LER DADOS DE UM DOCUMENTO USANDO GET() COM SUCCESSLISTENER E ONCOMPLETLISTENER------------------

    fun ouvinte_1() {


        reference!!.document("gerente0").get().addOnSuccessListener { documento ->


            if (documento != null && documento.exists()) {

                val dados = documento.data

                val key = documento.id

                val nome = dados?.get("nome").toString()
                val idade = dados?.get("idade")
                val fumante = dados?.get("fumante")


                binding.textViewFirestoreLerDadosNome.text = key + "\n" + nome
                binding.textViewFirestoreLerDadosIdade.text = "$idade"
                binding.textViewFirestoreLerDadosFumante.text = "$fumante"

            } else {

                Util.exibirToast(
                    baseContext,
                    "Erro ao ler o documento, ele não existe ou está vazio"
                )

            }


        }.addOnFailureListener { error ->

            Util.exibirToast(
                baseContext,
                "Erro ao ler Dados do Servidor: ${error.message.toString()}"
            )


        }

        /*


        bd!!.collection("Gerentes").document("gerente0").get().addOnCompleteListener{ task ->


            if(task.isSuccessful){


                val documento = task.result

                if(documento !=null && documento.exists()){



                    val dados = documento.data

                    val nome = dados?.get("nome").toString()
                    val idade = dados?.get("idade")
                    val fumante = dados?.get("fumante")


                    textView_Firestore_LerDados_Nome.setText(nome)
                    textView_Firestore_LerDados_Idade.setText("$idade")
                    textView_Firestore_LerDados_Fumante.setText("$fumante")

                    if(){

                    }else{


                }else{

                    Util.exibirToast(baseContext,"Erro ao ler o documento, ele não existe ou está vazio")
                }

            }else{

                Util.exibirToast(baseContext,"Erro ao ler Dados do Servidor: ${task.exception.toString()}")
            }
        }


         */


    }






    //----------------------LER DADOS DE UM DOCUMENTO USANDO GET() E UM CLASSE --------------------------------------


    fun ouvinte_2() {


        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager, "0")

        reference!!.document("gerente0").get().addOnSuccessListener { documento ->


            dialogProgress.dismiss()

            if (documento != null && documento.exists()) {

                val key = documento.id

                val gerente = documento.toObject(Gerente::class.java)

                binding.textViewFirestoreLerDadosNome.setText(key + "\n" + gerente?.nome)
                binding.textViewFirestoreLerDadosIdade.setText("${gerente?.idade}")
                binding.textViewFirestoreLerDadosFumante.setText("${gerente?.fumante}")

            } else {

                Util.exibirToast(
                    baseContext,
                    "Erro ao ler o documento, ele não existe ou está vazio"
                )

            }


        }.addOnFailureListener { error ->

            dialogProgress.dismiss()

            Util.exibirToast(
                baseContext,
                "Erro ao ler Dados do Servidor: ${error.message.toString()}"
            )
        }


    }







    //----------------------LER DADOS DE VÁRIOS DOCUMENTOS USANDO GET() COM SUCCESSLISTENER E ONCOMPLETLISTENER------------------



    fun ouvinte_3() {

        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager, "0")

        var listaGerentes: MutableList<Gerente> = ArrayList<Gerente>();



        reference!!.get().addOnSuccessListener { documentos ->

            dialogProgress.dismiss()

            if (documentos != null) {


                for (documento in documentos) {

                    val key = documento.id

                    val gerente = documento.toObject(Gerente::class.java)

                    listaGerentes.add(gerente)

                }

                binding.textViewFirestoreLerDadosNome.setText("\n" + listaGerentes.get(0).nome)
                binding.textViewFirestoreLerDadosIdade.setText("${listaGerentes.get(0).idade}")
                binding.textViewFirestoreLerDadosFumante.setText("${listaGerentes.get(0).fumante}")

                binding.textViewFirestoreLerDadosNome2.setText("\n" + listaGerentes.get(1).nome)
                binding.textViewFirestoreLerDadosIdade2.setText("${listaGerentes.get(1).idade}")
                binding.textViewFirestoreLerDadosFumante2.setText("${listaGerentes.get(1).fumante}")

            } else {

                Util.exibirToast(
                    baseContext,
                    "Erro ao ler o documento, ele não existe ou está vazio"
                )
            }

        }.addOnFailureListener { error ->

            dialogProgress.dismiss()

            Util.exibirToast(
                baseContext,
                "Erro ao ler Dados do Servidor: ${error.message.toString()}"
            )
        }


        /*


        bd!!.collection("Gerentes").get().addOnCompleteListener{ task ->


            dialogProgress.dismiss()


            if(task.isSuccessful){

                val documentos = task.result?.documents


                if(documentos !=null){


                    for(documento in documentos ){

                        val key = documento.id

                        val gerente = documento.toObject(Gerente::class.java)

                        listaGerentes.add(gerente!!)

                    }

                    textView_Firestore_LerDados_Nome.setText("\n" + listaGerentes.get(0).nome)
                    textView_Firestore_LerDados_Idade.setText("${listaGerentes.get(0).idade}")
                    textView_Firestore_LerDados_Fumante.setText("${listaGerentes.get(0).fumante}")

                    textView_Firestore_LerDados_Nome2.setText("\n" + listaGerentes.get(1).nome)
                    textView_Firestore_LerDados_Idade2.setText("${listaGerentes.get(1).idade}")
                    textView_Firestore_LerDados_Fumante2.setText("${listaGerentes.get(1).fumante}")

                }else{

                    Util.exibirToast(baseContext, "Erro ao ler o documento, ele não existe ou está vazio")
                }



            }else{

                Util.exibirToast(baseContext, "Erro ao ler Dados do Servidor: ${task.exception.toString()}")


            }


        }




         */


    }





    //----------------------LER DADOS DE UM DOCUMENTO USANDO addSnapshotListener --------------------------



    fun ouvinte_4() {

        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager, "0")



        reference!!.document("gerente0").addSnapshotListener { documento, error ->

            //error

            dialogProgress.dismiss()

            if (error != null) {

                Util.exibirToast(
                    baseContext,
                    "Erro na comunicação com o servidor: ${error.message.toString()}"
                )

            } else if (documento != null && documento.exists()) {


                val key = documento.id

                val gerente = documento.toObject(Gerente::class.java)

                binding.textViewFirestoreLerDadosNome.setText(key + "\n" + gerente?.nome)
                binding.textViewFirestoreLerDadosIdade.setText("${gerente?.idade}")
                binding.textViewFirestoreLerDadosFumante.setText("${gerente?.fumante}")


            } else {
                Util.exibirToast(baseContext, "Essa pasta não existe ou está vazia")

            }


        }


    }






    //----------------------LER DADOS DE VÁRIOS DOCUMENTOS USANDO addSnapshotListener --------------------------


    fun ouvinte_5() {

        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager, "0")

        var listaGerentes: MutableList<Gerente> = ArrayList<Gerente>();



        reference!!.addSnapshotListener { documentos, error ->

            dialogProgress.dismiss()

            if (error != null) {

                Util.exibirToast(baseContext, "Erro na comunicação com o servidor: ${error.message.toString()}")

            }

            else if (documentos != null) {

                listaGerentes.clear()

                for (documento in documentos) {

                    val key = documento.id

                    val gerente = documento.toObject(Gerente::class.java)

                    Log.d(
                        "wwwjafapps",
                        "Key : ${key} Nome: ${gerente.nome}  Idade: ${gerente.idade}"
                    )
                    listaGerentes.add(gerente)

                }

                binding.textViewFirestoreLerDadosNome.setText("\n" + listaGerentes.get(0).nome)
                binding.textViewFirestoreLerDadosIdade.setText("${listaGerentes.get(0).idade}")
                binding.textViewFirestoreLerDadosFumante.setText("${listaGerentes.get(0).fumante}")

                binding.textViewFirestoreLerDadosNome2.setText("\n" + listaGerentes.get(1).nome)
                binding.textViewFirestoreLerDadosIdade2.setText("${listaGerentes.get(1).idade}")
                binding.textViewFirestoreLerDadosFumante2.setText("${listaGerentes.get(1).fumante}")


            } else {
                Util.exibirToast(baseContext, "Essa pasta não existe ou está vazia")

            }

        }

    }






    //----------------------LER DADOS DE VÁRIOS DOCUMENTOS USANDO addSnapshotListener (MAIS RECOMENDAVEL QUE OUVINTE_5() )  --------------------------


    fun ouvinte_6() {

        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager, "0")


        /// val query = reference!!.whereEqualTo("fumante",false)

        // val query = reference!!.whereLessThan("idade",10)

        //  val query = reference!!.whereGreaterThan("idade",10)

        //  val query = reference!!.whereEqualTo("fumante",false).whereGreaterThan("idade",10)


        //   val query = reference!!.orderBy("nome").startAt("l").endAt("l"+"\uf8ff") .limit(5)



        val query = reference!!.orderBy("nome", Query.Direction.DESCENDING).limit(3)



        query!!.addSnapshotListener { documentos, error ->

            dialogProgress.dismiss()

            if (error != null) {

                Util.exibirToast(baseContext, "Erro na comunicação com o servidor: ${error.message.toString()}")

            }

            else if (documentos != null) {



                for (documento in documentos.documentChanges){



                    when(documento.type){


                        DocumentChange.Type.ADDED ->{

                            val key = documento.document.id
                            val gerente = documento.document.toObject(Gerente::class.java);

                            Log.d("jafappswww","ADDED -  Key: ${key}   Nome: ${gerente.nome}")



                        }
                        DocumentChange.Type.MODIFIED ->{

                            val key = documento.document.id
                            val gerente = documento.document.toObject(Gerente::class.java);

                            Log.d("jafappswww","MODIFIED -  Key: ${key}   Nome: ${gerente.nome}")

                        }

                        DocumentChange.Type.REMOVED ->{


                            val key = documento.document.id
                            val gerente = documento.document.toObject(Gerente::class.java);

                            Log.d("jafappswww","REMOVED -  Key: ${key}   Nome: ${gerente.nome}")
                        }



                    }// fim do when


                }//fim do for







            } else {
                Util.exibirToast(baseContext, "Essa pasta não existe ou está vazia")

            }

        }

    }


}
