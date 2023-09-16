package com.example.noteapp.note

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.database.Note

class NoteRVAdapter(
    val context: Context,
    val noteClickDeleteInterface: NoteClickDeleteInterface,
    val noteClickInterface: NoteClickInterface
) :
    RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {

    //on below line we are creating a
    //variable for our all notes list
    private val allNotes = ArrayList<Note>()

    //on below line we are creating a view holder class
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //on below line we are creating an initializing all our
        //variables which we have added in layout file

        val noteTV: TextView = itemView.findViewById(R.id.idTvNote)
        val dateTV: TextView = itemView.findViewById(R.id.idTvDate)
        val deleteIV: ImageView = itemView.findViewById(R.id.idIvDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //inflating our layout file for each item of recycler view
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_rv_item,
            parent, false
        )

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {

        //on below line we are
        //returning our list size
        return allNotes.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = allNotes[position]


        //on below line we are setting data to item of recycler view
        holder.noteTV.text = note.noteTitle
        holder.dateTV.text = "Last Updated: " + note.timeStamp

        //on below line we are adding click listener to our delete image view icon
        holder.deleteIV.setOnClickListener {
            //on below line we are calling a note click
            //interface and we are passing a position to it
            noteClickDeleteInterface.onDeleteIconClick(note)
        }

        holder.itemView.setOnClickListener{
            noteClickInterface.onNoteClick(note)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Note>){
        //on below line we are clearing
        //our notes array list
        allNotes.clear()

        //we are adding a new list to our all notes list
        allNotes.addAll(newList)

        //on below line we are calling notify data
        //change method to notify our adapter
        notifyDataSetChanged()
    }
}

interface NoteClickInterface {
     fun onNoteClick(note: Note)
}

interface NoteClickDeleteInterface {
    //creating a method for click
    //action on delete image view
    fun onDeleteIconClick(note: Note)
}
