package com.minhtettharutycc.heynotepad.Adapter


import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.minhtettharutycc.heynotepad.Listener.Listener
import com.minhtettharutycc.heynotepad.Model.Note
import com.minhtettharutycc.heynotepad.R


class NoteAdapter(private val context: Context, private var notesList: ArrayList<Note>, private val listener: Listener):
                                                                                                    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.each_row,parent,false))
    }

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvData: TextView  = itemView.findViewById(R.id.tvData)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)

        init {
            itemView.setOnClickListener {
                listener.onClickListener(adapterPosition)
            }
        }

    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notesList[position]
        holder.tvData.text = note.data
        holder.tvDate.text = note.date

    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun setNotes(notesList: ArrayList<Note>){
        this.notesList = notesList
        notifyDataSetChanged()
    }
}