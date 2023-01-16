package com.minhtettharutycc.heynotepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.minhtettharutycc.heynotepad.Model.Note
import com.minhtettharutycc.heynotepad.ViewModel.NoteViewModel
import java.util.*
import kotlin.properties.Delegates

class UpdateActivity : AppCompatActivity() {

    private lateinit var  noteViewModel: NoteViewModel
    private lateinit var note: Note

    private lateinit var etNote: EditText
    private lateinit var tvChars: TextView
    private lateinit var fabUpdate: FloatingActionButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        etNote = findViewById(R.id.etNote)
        tvChars = findViewById(R.id.tvChars)
        fabUpdate = findViewById(R.id.fabUpdateNote)

        noteViewModel = NoteViewModel()

        val bundle: Bundle? = intent.extras
        if(bundle != null){
            note = bundle.getSerializable("note") as Note

        }
        loadNote(note)
        etNote.addTextChangedListener(textWatcher)

        fabUpdate.setOnClickListener{
            updateNote()
        }

    }

    private fun updateNote() {

        note.data = etNote.text.trim().toString()
        note.date = getCurrentDate()
        note.characters = note.data.length.toLong()
        noteViewModel.update(applicationContext,note)

        finish()

    }


    private fun loadNote(note: Note){
        etNote.setText(note.data)
        tvChars.text = " | ${note.characters.toString()} Characters"

    }

    private val textWatcher = object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            tvChars.text =" | ${ s?.length.toString()} Characters"
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    fun getCurrentDate(): String{
        return Calendar.getInstance().time.toString()
    }



}