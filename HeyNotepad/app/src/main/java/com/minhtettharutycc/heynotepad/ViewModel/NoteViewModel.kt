package com.minhtettharutycc.heynotepad.ViewModel
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.minhtettharutycc.heynotepad.Model.Note
import com.minhtettharutycc.heynotepad.Repository.NoteRepository


class NoteViewModel : ViewModel() {

    fun insert(context: Context, note: Note) {
        NoteRepository.insert(context, note)
        Log.d("noteInsert:", "ViewModel")
    }

    fun update(context: Context, note: Note) {
        NoteRepository.update(context, note)
    }

    fun delete(context: Context, note: Note) {
        NoteRepository.delete(context, note)
    }

    fun search(context: Context, query:String):LiveData<List<Note>>?{
        return NoteRepository.search(context,query)
    }

    fun getNotes(context: Context): LiveData<List<Note>>? {
        return NoteRepository.getNotes(context)
    }

}