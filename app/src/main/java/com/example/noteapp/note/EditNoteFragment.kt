package com.example.noteapp.note

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteapp.R
import com.example.noteapp.database.Note
import com.example.noteapp.databinding.FragmentEditNoteBinding
import java.text.SimpleDateFormat
import java.util.Date

class EditNoteFragment : Fragment() {

    private lateinit var binding:FragmentEditNoteBinding
    private lateinit var  noteTitleEdit: EditText
    private lateinit var noteEdt:EditText
    private lateinit var saveBtn: Button

    var noteID = -1


    private lateinit var viewModel: NoteViewModel


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_note, container, false)


        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(NoteViewModel::class.java)


        noteTitleEdit = binding.idEdtNoteName
        noteEdt = binding.idEdtNoteDesc
        saveBtn = binding.idBtn

        val args = arguments?.let { EditNoteFragmentArgs.fromBundle(it) }
        val noteType = args!!.noteType

        Toast.makeText(context, "Title: ${args!!.noteTitle}, Description: ${args!!.noteDescription}, ID:${args!!.noteId}", Toast.LENGTH_LONG).show()


        if(noteType == "Edit"){

            val noteTitle = args.noteTitle
            val noteDescription = args.noteDescription
            noteID = args.noteId
            saveBtn.text = "UPDATE"
            noteTitleEdit.setText(noteTitle)
            noteEdt.setText(noteDescription)

        }else {
            saveBtn.text = "SAVE"
        }

//        // Retrieve existing note data from arguments
//        arguments?.let {
//            val existingTitle = it.getString("existingTitle", "")
//            val existingDescription = it.getString("existingDescription", "")
//            existingNote = Note(0, existingTitle, existingDescription, "")
//        }

//        // Check if existingNote is not null before accessing it
//        existingNote?.let { note ->
//            // Pre-fill EditText fields with existing note data
//            noteTitleEdit.setText(note.noteTitle)
//            noteEdt.setText(note.noteDescription)
//        }


        saveBtn.setOnClickListener {

            val noteTitle = noteTitleEdit.text.toString()
            val noteDescription = noteEdt.text.toString()

            if(noteType == "Edit"){
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    val updatedNote = Note(noteTitle, noteDescription, currentDateAndTime)
                    updatedNote.id = noteID
                    viewModel.updateNote(updatedNote)
                    Toast.makeText(requireContext(), "Note Updated..", Toast.LENGTH_LONG).show()
                }
                else {
                    if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                        val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                        val currentDateAndTime: String = sdf.format(Date())
                        viewModel.addNote(Note(noteTitle, noteDescription, currentDateAndTime))
                        Toast.makeText(requireContext(), "$noteTitle Added", Toast.LENGTH_LONG).show()
                    }
                }
                findNavController().navigateUp()
            }




        }



        return binding.root
    }


}



//if ( noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
//    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
//    val currentDateAndTime: String = sdf.format(Date())
//
//    viewModel.addNote(Note(0, noteTitle, noteDescription, currentDateAndTime))
//    Toast.makeText(context, "$noteTitle Added", Toast.LENGTH_LONG).show()
//
//    findNavController().navigate(R.id.action_editNoteFragment_to_noteFragment)
//}else {
//
//}