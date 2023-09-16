package com.example.noteapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//on below line we are specifying our table name
@Entity(tableName = "notesTable")

//on below line we are specifying our column info
//and inside that we are passing our column name
 class Note(
    @ColumnInfo(name = "title")
    var noteTitle: String,

    @ColumnInfo(name = "description")
    var noteDescription: String,

    @ColumnInfo(name = "timestamp")
    val timeStamp: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
 }