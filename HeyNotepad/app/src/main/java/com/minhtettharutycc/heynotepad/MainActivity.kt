package com.minhtettharutycc.heynotepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.util.query

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.minhtettharutycc.heynotepad.Adapter.NoteAdapter
import com.minhtettharutycc.heynotepad.Listener.Listener
import com.minhtettharutycc.heynotepad.Model.Note
import com.minhtettharutycc.heynotepad.ViewModel.NoteViewModel

class MainActivity : AppCompatActivity(),Listener {
    private lateinit var rvNotes: RecyclerView
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var notesList: ArrayList<Note>
    private lateinit var searchView: SearchView

    private lateinit var fabAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesList = ArrayList<Note>()

        noteAdapter = NoteAdapter(applicationContext,notesList,this)
        initialiseRecyclerView()

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.getNotes(this)?.observe(this, Observer {
            noteAdapter.setNotes(it as ArrayList<Note>)
            notesList = it
            Log.d("notesListSize:",notesList.size.toString())

            notesList.forEach {
                Log.d("note:",it.data)
            }

            noteAdapter.notifyDataSetChanged()


        })



        fabAdd = findViewById(R.id.fabAdd)
        fabAdd.setOnClickListener{
            val intent = Intent(this,AnotherActivity::class.java)
            startActivity(intent)
        }

        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
        itemTouchHelper.attachToRecyclerView(rvNotes)

    }


    private fun initialiseRecyclerView() {
        rvNotes = findViewById(R.id.rvNotes)
        rvNotes.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = noteAdapter

        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        val mnSearch = menu?.findItem(R.id.action_search)
        searchView = mnSearch?.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    getSearchItems(query)

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    getSearchItems(newText)
                }

                return true
            }

        })
        return true
    }

    private fun getSearchItems(query: String) {

        var searchText = "%$query%"

        noteViewModel.search(this,searchText)?.observe(this, Observer {
            Log.d("txtSearch:","$it")
            noteAdapter.setNotes(it as ArrayList<Note>)
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when(item.itemId){
            R.id.action_grid_view ->

                if(rvNotes.layoutManager is StaggeredGridLayoutManager){
                    Snackbar.make(findViewById(R.id.ln_main),"Already Grid View",Snackbar.LENGTH_SHORT).show()
                } else setGridView()


            R.id.action_list_view ->
                if(rvNotes.layoutManager is LinearLayoutManager){
                    Snackbar.make(findViewById(R.id.ln_main),"Already List View",Snackbar.LENGTH_SHORT).show()
                }
                else setListView()
            else ->  return true
        }
        return true
    }

    private fun setGridView() {
        rvNotes.apply {
            layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)

        }
    }

    private fun setListView() {
        rvNotes.apply {
            layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)

        }


    }




    override fun onClickListener(position: Int) {

        val intent = Intent(this,UpdateActivity::class.java)
        intent.putExtra("note",notesList[position])
        startActivity(intent)

    }


    val simpleCallBack = object: ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val note = notesList[position]

            when (direction) {
                ItemTouchHelper.RIGHT-> {
                    noteViewModel.delete(this@MainActivity,note)
                    noteAdapter.notifyDataSetChanged()

                }

                ItemTouchHelper.LEFT-> {
                    noteViewModel.delete(this@MainActivity,note)
                    noteAdapter.notifyDataSetChanged()
                }
            }
        }


    }
}