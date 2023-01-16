package com.minhtettharutycc.heynotepad.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.minhtettharutycc.heynotepad.Dao.NoteDao
import com.minhtettharutycc.heynotepad.Model.Note


@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun getDao(): NoteDao

    companion object {
        private const val DATABASE_NAME = "NoteDatabase"

        @Volatile
        var instance: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase? {
            if (instance == null) {
                synchronized(NoteDatabase::class.java){
                    if(instance == null)
                        instance = Room.databaseBuilder(context,NoteDatabase::class.java,
                            DATABASE_NAME).build()
                }
            }

            return instance
        }

    }
}