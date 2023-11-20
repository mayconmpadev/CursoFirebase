package com.mpasistemas.cursofirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.br.jafapps.bdfirestore.util.DialogProgress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mpasistemas.cursofirebase.databinding.ActivityAberturaBinding

class AberturaActivity : AppCompatActivity(), View.OnClickListener {

private val binding by lazy {
    ActivityAberturaBinding.inflate(layoutInflater)
}
    var auth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener(this)

        auth = Firebase.auth

        val user = auth?.currentUser



        if(user != null){


            val uid = user.uid

            finish()


            val intent =  Intent(this,MainActivity::class.java)


            startActivity(intent)
        }



    }








    override fun onClick(p0: View?) {

        when(p0?.id){

            binding.buttonLogin.id -> {

                buttonLogin()
            }
            else -> false
        }

    }








    fun buttonLogin(){

        val email = binding.editTextLoginEmail.text.toString()
        val senha = binding.editTextLoginSenha.text.toString()

        if( !email.trim().equals("") && !senha.trim().equals("")){

            if (Util.statusInternet(this)){

                login(email, senha)

            }else{
                Util.exibirToast(this,"Você não possui uma conexão com a internet")
            }
        }else{

            Util.exibirToast(this,"Preencha todos os dados")
        }
    }











    fun login(email:String, senha:String) {

        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager,"1")

        auth?.signInWithEmailAndPassword(email,senha)?.addOnCompleteListener(this){  task ->

            dialogProgress.dismiss()

            if (task.isSuccessful){

                Util.exibirToast(baseContext,"Sucesso ao fazer Login")

                finish()
                val intent =  Intent(this,MainActivity::class.java)
                startActivity(intent)
            }else{


                val erro = task.exception.toString()
                errosFirebase(erro)
            }

        }
            ?.addOnFailureListener{ e ->


                val errorCode = (e as FirebaseAuthException).errorCode



                when(errorCode){


                    "ERROR_INVALID_EMAIL" ->{
                        Log.d("wwer","Email invalido")
                    }
                }

            }




                auth?.signInWithEmailAndPassword(email,senha)?.addOnSuccessListener {

                        Util.exibirToast(baseContext,"Sucesso ao fazer Login")


                }?.addOnFailureListener{ error ->


                    val erro = error.message.toString()
                    errosFirebase(erro)

                }




    }















    fun errosFirebase(erro: String){


        if( erro.contains("There is no user record corresponding to this identifier")){

            Util.exibirToast(baseContext,"Esse e-mail não está cadastrado ainda")

        }
        else if( erro.contains("The password is invalid")){

            Util.exibirToast(baseContext,"Senha inválida")

        }
        else if(erro.contains("The email address is badly ")){

            Util.exibirToast(baseContext,"Este e-mail não é válido")

        }


    }




}