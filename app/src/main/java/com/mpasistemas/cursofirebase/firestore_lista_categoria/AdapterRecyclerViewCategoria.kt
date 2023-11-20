package com.mpasistemas.cursofirebase.firestore_lista_categoria

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mpasistemas.cursofirebase.R


class AdapterRecyclerViewCategoria  (val context: Context, var categorias: ArrayList<Categoria>,
                                     var clickCategoria: ClickCategoria, var ultimoItemExibidoRecyclerView: UltimoItemExibidoRecyclerView):
    RecyclerView.Adapter<AdapterRecyclerViewCategoria.ViewHolder>() {





    //diz qual layout é dos itens
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_lista_categoria_recyclerview,parent,false)

        val holder = ViewHolder(view)

        return holder

    }



   //qual tamanho  da lista
    override fun getItemCount(): Int {

       return categorias.size

    }




    //inserir informações no layout
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val categoria: Categoria = categorias.get(position)

        holder.nome.text = categoria.nome

        holder.cardView.setOnClickListener{

            clickCategoria.clickCategoria(categoria)

        }




        if(position == getItemCount() - 1){


            ultimoItemExibidoRecyclerView.ultimoItemExibidoRecyclerView(true)

        }




    }




    //interface
    interface ClickCategoria{


        fun clickCategoria(categoria: Categoria)

    }



    interface  UltimoItemExibidoRecyclerView {


        fun ultimoItemExibidoRecyclerView(isExibido: Boolean)


    }





    //ligar variaveis com itens(elementos) do layout

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nome = itemView.findViewById<TextView>(R.id.textView_ListaItemCategoria_Nome)
        val cardView = itemView.findViewById<CardView>(R.id.cardView_ListaItemCategoria)

    }







}