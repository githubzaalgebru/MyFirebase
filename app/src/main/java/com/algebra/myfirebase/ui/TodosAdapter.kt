package com.algebra.myfirebase.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.algebra.myfirebase.R
import com.algebra.myfirebase.model.Todo

interface OnItemClickListener {
    fun onItemClick( item : Todo )
}

class TodosAdapter( val todos : List< Todo >, val listener : OnItemClickListener ) : RecyclerView.Adapter< TodoViewHolder >( ) {
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int ) : TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater
                .from( parent.context )
                .inflate( R.layout.todo_item, parent, false )
        )
    }
    override fun onBindViewHolder( holder: TodoViewHolder, position : Int ) {
        val todo = todos[ position ]
        holder.tvTitle.text       = todo.title
        holder.tvDescription.text = todo.description

        holder.itemView.setOnClickListener {
            listener.onItemClick( todo )
        }

        holder
            .itemView
            .setBackgroundColor(
                Color.parseColor( if( position%2==0 ) "#FFCCCC" else "#CCCCFF" )
            )
    }
    override fun getItemCount( ) : Int = todos.size
}
class TodoViewHolder( view : View) : RecyclerView.ViewHolder( view ) {
    val tvTitle       : TextView = view.findViewById( R.id.tvName )
    val tvDescription : TextView = view.findViewById( R.id.tvDescription )
}