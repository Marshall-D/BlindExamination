package com.example.blindexam.activities

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class firebaseHelper (ref : DatabaseReference, student: Student){
    val  ref = FirebaseDatabase.getInstance().getReference("student")
    val student : ArrayList<Student> = ArrayList()

}