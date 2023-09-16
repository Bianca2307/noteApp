package com.example.noteapp.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.database.Note
import com.example.noteapp.databinding.FragmentNoteBinding

class NoteFragment : Fragment(), NoteClickDeleteInterface, NoteClickInterface {

    private lateinit var binding:FragmentNoteBinding
    private lateinit var notesRV:RecyclerView
    private lateinit var viewModel: NoteViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note, container, false)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(NoteViewModel::class.java)


        binding.lifecycleOwner = this

        notesRV = binding.notesRv


        notesRV.layoutManager = LinearLayoutManager(requireContext())

        val noteRVAdapter = NoteRVAdapter(requireContext(), this, this)
        notesRV.adapter = noteRVAdapter


        //on below line we are calling all notes method
        //from our view model class to observe the changes on list

        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                //on below line we are updating our list
                noteRVAdapter.updateList(it)
            }
        })

       binding.idFAB.setOnClickListener{ view:View ->
            view.findNavController().navigate(R.id.action_noteFragment_to_editNoteFragment)
        }



        return binding.root
    }

    override fun onDeleteIconClick(note: Note) {
        // in on note click method we are calling delete
        // method from our view modal to delete our not.
        viewModel.deleteNote(note)
        // displaying a toast message
        Toast.makeText( context,"${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
    }

    override fun onNoteClick(note: Note) {
        var noteTitle = note.noteTitle
        var noteDescription = note.noteDescription
        var noteId = note.id
        val noteType = "Edit"

        val action = NoteFragmentDirections.actionNoteFragmentToEditNoteFragment(noteTitle,noteDescription,noteId, noteType)

        findNavController().navigate(action)

    }
}