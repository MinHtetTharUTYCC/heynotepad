package com.minhtettharutycc.heynotepad.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.minhtettharutycc.heynotepad.Model.Note

@Dao
interface NoteDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)


    @Query("SELECT * FROM note ORDER BY id DESC")
    fun getNotes() : LiveData<List<Note>>

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * FROM note WHERE data LIKE :query")
    fun search(query:String): LiveData<List<Note>>


    @Delete
    suspend fun delete(note: Note)




}