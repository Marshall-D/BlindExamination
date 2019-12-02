package com.example.blindexam.activities

class Question(val questionText : String = "", val optionA : String = "", val optionB : String = ""
               , val optionC : String = "", val correctAnswer : String= ""){
    constructor() : this("","","","","")

}