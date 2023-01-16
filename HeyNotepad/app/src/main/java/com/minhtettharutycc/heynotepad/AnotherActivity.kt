package com.minhtettharutycc.heynotepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.GetChars
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import com.google.android.material.appbar.MaterialToolbar
import com.minhtettharutycc.heynotepad.Model.Note
import com.minhtettharutycc.heynotepad.ViewModel.NoteViewModel
import java.util.*
import kotlin.properties.Delegates

class AnotherActivity : AppCompatActivity() {

    private lateinit var strNote: String
    private lateinit var strDate: String
    private var getChars by Delegates.notNull<Long>()


    private lateinit var noteViewModel: NoteViewModel

    private lateinit var tvCharacters: TextView
    private  lateinit var etNote: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_another)

        etNote = findViewById(R.id.etNote)
        tvCharacters = findViewById(R.id.tvCharacters)

        etNote.addTextChangedListener(textWatcher)

        noteViewModel = NoteViewModel()

    }

    private val textWatcher = object: TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            tvCharacters.text =" | Characters " + s?.length.toString()
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    private fun saveDataInDatabase() {
        strNote = etNote.text.toString()
        strDate = getTodayDate()
        getChars = strNote.trim().length.toLong()
    }

    private fun getTodayDate():String {
        return  Calendar.getInstance().time.toString()

    }

    private fun backToHomePage() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.another_menu,menu)
        return true


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnSave-> {
                saveDataInDatabase()
                if (strNote.isNotEmpty()) {
                    noteViewModel.insert(applicationContext, Note(strNote,strDate,getChars))
                    Log.d("strNote:",strNote)
                    Log.d("strDate",strDate)
                    Log.d("getChars:", getChars.toString())
                    backToHomePage()


                }else {
                    Toast.makeText(applicationContext,"Type some characters",Toast.LENGTH_SHORT).show()

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}