package com.mpasistemas.cursofirebase.firestore_lista_categoria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.br.jafapps.bdfirestore.util.DialogProgress
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mpasistemas.cursofirebase.R
import com.mpasistemas.cursofirebase.Util
import com.mpasistemas.cursofirebase.databinding.ActivityFirestoreListaCategoriaBinding
import com.mpasistemas.cursofirebase.firestore_lista_item.FirestoreListaItemActivity

class FirestoreListaCategoriaActivity : AppCompatActivity(), SearchView.OnQueryTextListener,

    SearchView.OnCloseListener, AdapterRecyclerViewCategoria.ClickCategoria,
    AdapterRecyclerViewCategoria.UltimoItemExibidoRecyclerView {


private val binding by lazy {
    ActivityFirestoreListaCategoriaBinding.inflate(layoutInflater)
}

    var searchView: SearchView? = null

    var adapterRecyclerViewCategoria: AdapterRecyclerViewCategoria? = null
    var categorias: ArrayList<Categoria> = ArrayList()

    var database: FirebaseFirestore? = null
    var reference: CollectionReference? = null
    var proximoQuery: Query? = null


    var isFiltrando = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        supportActionBar?.title = "Categorias"

        binding.buttonFirestoreListaCategoriaExibirMais.visibility = View.GONE

        /*
        button_Firestore_ListaCategoriaExibirMais.setOnClickListener{

           exibirMaisItensBD()
        }
         */




        database = FirebaseFirestore.getInstance()
        reference = database?.collection("Categorias")

        iniciarRecyclerView()


        exibirPrimeirosItensBD()



    }









    //--------------------------------------------------RECYCLERVIEW - LISTA---------------------------


    fun iniciarRecyclerView(){

        val data0 = hashMapOf("nome" to "Móveis", "id" to 0)
        reference!!.document("0").set(data0)
        val data1 = hashMapOf("nome" to "Carros", "id" to 1)
        reference!!.document("1").set(data1)
        val data2 = hashMapOf("nome" to "Celulares", "id" to 2)
        reference!!.document("2").set(data2)
        val data3 = hashMapOf("nome" to "Times Futebol", "id" to 3)
        reference!!.document("3").set(data3)
        val data4 = hashMapOf("nome" to "Cursos", "id" to 4)
        reference!!.document("4").set(data4)
        val data5 = hashMapOf("nome" to "Professores", "id" to 5)
        reference!!.document("5").set(data5)
        val data6 = hashMapOf("nome" to "Alunos", "id" to 6)
        reference!!.document("6").set(data6)
        val data7 = hashMapOf("nome" to "Motos", "id" to 7)
        reference!!.document("7").set(data7)
        val data8 = hashMapOf("nome" to "Bicicletas", "id" to 8)
        reference!!.document("8").set(data8)
        val data9 = hashMapOf("nome" to "Liguagens Programação", "id" to 9)
        reference!!.document("9").set(data9)
        val data10 = hashMapOf("nome" to "Nomes", "id" to 10)
        reference!!.document("10").set(data10)
        val data11 = hashMapOf("nome" to "Carnes", "id" to 11)
        reference!!.document("11").set(data11)
        val data12 = hashMapOf("nome" to "Refrigerantes", "id" to 12)
        reference!!.document("12").set(data12)
        val data13 = hashMapOf("nome" to "Lanches", "id" to 13)
        reference!!.document("13").set(data13)
        val data14 = hashMapOf("nome" to "Restaurantes", "id" to 14)
        reference!!.document("14").set(data14)
        val data15 = hashMapOf("nome" to "Pizzarias", "id" to 15)
        reference!!.document("15").set(data15)
        val data16 = hashMapOf("nome" to "Escolas", "id" to 16)
        reference!!.document("16").set(data16)
        val data17 = hashMapOf("nome" to "Faculdades", "id" to 17)
        reference!!.document("17").set(data17)
        val data18 = hashMapOf("nome" to "Universidades", "id" to 18)
        reference!!.document("18").set(data18)
        val data19 = hashMapOf("nome" to "Notebooks", "id" to 19)
        reference!!.document("19").set(data19)
        val data20 = hashMapOf("nome" to "Computadores", "id" to 20)
        reference!!.document("20").set(data20)

        val data21 = hashMapOf("nome" to "Hotel", "id" to 21)
        reference!!.document("21").set(data21)
        val data22 = hashMapOf("nome" to "Cidades", "id" to 22)
        reference!!.document("22").set(data22)
        val data23 = hashMapOf("nome" to "Estados", "id" to 23)
        reference!!.document("23").set(data23)
        val data24 = hashMapOf("nome" to "Paises", "id" to 24)
        reference!!.document("24").set(data24)
        val data25 = hashMapOf("nome" to "Continentes", "id" to 25)
        reference!!.document("25").set(data25)
        val data26 = hashMapOf("nome" to "Bancos", "id" to 26)
        reference!!.document("26").set(data26)
        val data27 = hashMapOf("nome" to "Pintores", "id" to 27)
        reference!!.document("27").set(data27)
        val data28 = hashMapOf("nome" to "Programadores", "id" to 28)
        reference!!.document("28").set(data28)
        val data29 = hashMapOf("nome" to "Aulas", "id" to 29)
        reference!!.document("29").set(data29)
        val data30 = hashMapOf("nome" to "Filmes", "id" to 30)
        reference!!.document("30").set(data30)

        val data31 = hashMapOf("nome" to "Heróis", "id" to 31)
        reference!!.document("31").set(data31)
        val data32 = hashMapOf("nome" to "Salários", "id" to 32)
        reference!!.document("32").set(data32)
        val data33 = hashMapOf("nome" to "Profissões", "id" to 33)
        reference!!.document("33").set(data33)
        val data34 = hashMapOf("nome" to "Padarias", "id" to 34)
        reference!!.document("34").set(data34)
        val data35 = hashMapOf("nome" to "Aplicativos", "id" to 35)
        reference!!.document("35").set(data35)

        val data36 = hashMapOf("nome" to "Empresas", "id" to 36)
        reference!!.document("36").set(data36)
        val data37 = hashMapOf("nome" to "Industrias", "id" to 37)
        reference!!.document("37").set(data37)
        val data38 = hashMapOf("nome" to "Livros", "id" to 38)
        reference!!.document("38").set(data38)
        val data39 = hashMapOf("nome" to "Prefeitos", "id" to 39)
        reference!!.document("39").set(data39)
        val data40 = hashMapOf("nome" to "Governadores", "id" to 40)
        reference!!.document("40").set(data40)

        val data41 = hashMapOf("nome" to "Feriados", "id" to 41)
        reference!!.document("41").set(data41)
        val data42 = hashMapOf("nome" to "Aniversariantes", "id" to 42)
        reference!!.document("42").set(data42)
        val data43 = hashMapOf("nome" to "Histórias", "id" to 43)
        reference!!.document("43").set(data43)
        val data44 = hashMapOf("nome" to "Vereadores", "id" to 44)
        reference!!.document("44").set(data44)
        val data45 = hashMapOf("nome" to "Senadores", "id" to 45)
        reference!!.document("45").set(data45)

        val data46 = hashMapOf("nome" to "Roupas", "id" to 46)
        reference!!.document("46").set(data46)
        val data47 = hashMapOf("nome" to "Calçados", "id" to 47)
        reference!!.document("47").set(data47)
        val data48 = hashMapOf("nome" to "Sapatos", "id" to 48)
        reference!!.document("48").set(data48)
        val data49 = hashMapOf("nome" to "Blusas", "id" to 49)
        reference!!.document("49").set(data49)
        val data50 = hashMapOf("nome" to "Animais", "id" to 50)
        reference!!.document("50").set(data50)


        adapterRecyclerViewCategoria =AdapterRecyclerViewCategoria(baseContext,categorias,this,this)


        binding.recyclerViewFirestoreListaCategoria.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFirestoreListaCategoria.adapter = adapterRecyclerViewCategoria



    }



    //CLICK EM ITEM DA LISTA
    override fun clickCategoria(categoria: Categoria) {



        val intent = Intent(this, FirestoreListaItemActivity::class.java)
        intent.putExtra("categoriaNome",categoria)

        startActivity(intent)


    }


    //ultimo item exibido
    override fun ultimoItemExibidoRecyclerView(isExibido: Boolean) {


        if(isFiltrando){

            //     Util.exibirToast(this,"Você está filtrando. Não vai ser exibido mais itens")

        }else{

            exibirMaisItensBD()
        }

    }

















    //--------------------------------------------------MENU OPÇÕES COM PESQUISA---------------------------



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search,menu)

        val search = menu!!.findItem(R.id.action_search)

        searchView = search.actionView as SearchView

        searchView?.queryHint = "Pesquisar nome"

        searchView?.setOnQueryTextListener(this)
        searchView?.setOnCloseListener(this)
        searchView?.inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES



        return super.onCreateOptionsMenu(menu)
    }














    //--------------------------------------------------METODOS SEARCH PARA PESQUISA---------------------------


    override fun onQueryTextSubmit(query: String?): Boolean {


        Log.d("yyyyui-onQueryTextS","onQueryTextSubmit")

        return true
    }




    override fun onQueryTextChange(newText: String?): Boolean {

        Log.d("yyyyui-onQueryTextC",newText.toString())


        isFiltrando = true
        pesquisarNome(newText.toString())

        return true
    }



    override fun onClose(): Boolean {

        isFiltrando = false

        searchView?.onActionViewCollapsed()


        categorias.clear()
        adapterRecyclerViewCategoria?.notifyDataSetChanged()

        exibirPrimeirosItensBD()


        return true
    }






















    //----------------------------------------------PESQUISA (FILTRO) POR NOME NO FIREBASE---------------------------




    fun pesquisarNome(newText: String){


        //j

        val query = database!!.collection("Categorias")
            .orderBy("nome").startAt(newText).endAt(newText+"\uf8ff").limit(3)



        query.get().addOnSuccessListener { documentos ->



            categorias.clear()

            for(documento in documentos){


                val categoria = documento.toObject(Categoria::class.java)
                categorias.add(categoria)
            }


            adapterRecyclerViewCategoria?.notifyDataSetChanged()


        }





    }





























    //--------------------------------------------------LER DADOS FIREBASE - PAGINAÇÃO---------------------------


    fun exibirPrimeirosItensBD(){


        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager,"1")


        var query = database!!.collection("Categorias").orderBy("nome").limit(10)



        query.get().addOnSuccessListener { documentos ->


            dialogProgress.dismiss()


            val ultimoDOcumento = documentos.documents[documentos.size() - 1]
            proximoQuery = database!!.collection("Categorias").orderBy("nome").startAfter(ultimoDOcumento).limit(10)


            for(documento in documentos){


                val categoria = documento.toObject(Categoria::class.java)
                categorias.add(categoria)
            }


            adapterRecyclerViewCategoria?.notifyDataSetChanged()


        }.addOnFailureListener {error ->


            Util.exibirToast(baseContext,"Error : ${error.message}")
            dialogProgress.dismiss()

        }

    }









    fun exibirMaisItensBD(){


        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager,"1")


        proximoQuery!!.get().addOnSuccessListener { documentos ->



            dialogProgress.dismiss()


            if(documentos.size() > 0 ){

                val ultimoDOcumento = documentos.documents[documentos.size() - 1]
                proximoQuery = database!!.collection("Categorias").orderBy("nome").startAfter(ultimoDOcumento).limit(10)


                for(documento in documentos){

                    val categoria = documento.toObject(Categoria::class.java)
                    categorias.add(categoria)
                }


                adapterRecyclerViewCategoria?.notifyDataSetChanged()

            }else{


                ///Util.exibirToast(baseContext,"Acabou a categorias")
                //  button_Firestore_ListaCategoriaExibirMais.visibility = View.GONE

            }


        }.addOnFailureListener {error ->


            Util.exibirToast(baseContext,"Error : ${error.message}")
            dialogProgress.dismiss()


        }





    }



}