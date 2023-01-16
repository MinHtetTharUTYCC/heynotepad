package com.minhtettharutycc.heynotepad.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.minhtettharutycc.heynotepad.Database.NoteDatabase

import com.minhtettharutycc.heynotepad.Model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class NoteRepository {

    companion object {

        private var noteDatabase: NoteDatabase? = null


        private fun initializeDB(context: Context): NoteDatabase? {

            return NoteDatabase.getInstance(context)

        }



        fun insert(context: Context, note: Note) {
            noteDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                noteDatabase?.getDao()?.insert(note)
            }

            Log.d("noteInsert:", "Repository")
        }

        fun update(context: Context, note: Note) {
            noteDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                noteDatabase?.getDao()?.update(note)
            }
        }


        fun delete(context: Context, note: Note) {
            noteDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                noteDatabase?.getDao()?.delete(note)
            }
        }

        fun search(context: Context, query: String): LiveData<List<Note>>? {
            noteDatabase = initializeDB(context)
            return noteDatabase?.getDao()?.search(query)

        }


        fun getNotes(context: Context): LiveData<List<Note>>?{
            noteDatabase = initializeDB(context)
            return noteDatabase?.getDao()?.getNotes()
        }




    }

}