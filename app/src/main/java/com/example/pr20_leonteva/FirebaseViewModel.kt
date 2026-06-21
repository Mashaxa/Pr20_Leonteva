package com.example.pr20_leonteva

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf // <--- ВАЖНЫЙ ИМПОРТ
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance().getReference("notes")

    private val _notes = mutableStateListOf<Note>()
    val notes: List<Note> = _notes

    init {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _notes.clear()
                for (child in snapshot.children) {
                    val note = child.getValue(Note::class.java)
                    if (note != null) {
                        _notes.add(note)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Обработка ошибок
            }
        })
    }

    fun addNote(text: String) {
        if (text.isNotBlank()) {
            val newNote = Note(text = text)
            database.push().setValue(newNote)
        }
    }
}