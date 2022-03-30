package com.algebra.myfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.myfirebase.model.Todo
import com.algebra.myfirebase.ui.OnItemClickListener
import com.algebra.myfirebase.ui.TodosAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*


const val TODOS_DB_PATH = "todos"
const val DESCRIPTION = "description"
const val TITLE = "title"
const val TAG = "MainActivity"

class MainActivity : AppCompatActivity( ) {

    private          val todos : MutableList< Todo > = mutableListOf( )
    private lateinit var db    : FirebaseFirestore

    override fun onCreate( savedInstanceState : Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main )

        db = FirebaseFirestore.getInstance( )

        setupList( )
        fetchData( )
        setupListener( )
    }

    private fun fetchData( ) {
/*
        db.collection( TODOS_DB_PATH ).get( )
            .addOnSuccessListener { value ->
                todos.clear( )
                for( todo in value!! ) {
                    val id    = todo.id
                    val title = todo.data[ TITLE ]       as String
                    val desc  = todo.data[ DESCRIPTION ] as String

                    todos.add( Todo( id, title, desc ) )
                }
                rvList.adapter?.notifyDataSetChanged( )
            }
            .addOnFailureListener {
                Log.w( TAG, "Error while fetching data: ${ it.toString( ) }")
            }
*/
        db.collection( TODOS_DB_PATH )
            .addSnapshotListener { value, e ->
                todos.clear( )
                for( todo in value!! ) {
                    val id    = todo.id
                    val title = todo.data[ TITLE ]       as String?
                    val desc  = todo.data[ DESCRIPTION ] as String?

                    todos.add( Todo( id, if(title==null)"" else title, if(desc==null)"" else desc ) )
                }
                rvList.adapter?.notifyDataSetChanged( )
            }
    }

    private fun setupListener( ) {
        bSave.setOnClickListener {
            val title = etTaskName.toString( ).trim( )
            val desc  = etTaskDescription.toString( ).trim( )

            val data = hashMapOf(
                TITLE to title,
                DESCRIPTION to desc,
            )

            db.collection( TODOS_DB_PATH ).add( data )
        }
    }

    private fun setupList( ) {
        rvList.layoutManager = LinearLayoutManager( this )
        rvList.adapter       = TodosAdapter( todos, object : OnItemClickListener {
            override fun onItemClick( item : Todo ) {
                db.collection( TODOS_DB_PATH ).document( item.id ).delete( )
            }
        } )
    }
}