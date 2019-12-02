package com.example.blindexam.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.TextView
import com.example.blindexam.R
import java.util.*

class ResultActivity : AppCompatActivity() {
    lateinit var textview : TextView
    lateinit var ttsobject : TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        ttsobject = TextToSpeech(this, TextToSpeech.OnInitListener(){
            fun onInit(status : Int){
                if (status == TextToSpeech.SUCCESS){
                    ttsobject.setLanguage(Locale.US)
                }


            }
        })
        textview = findViewById(R.id.textView5)


        val message = intent.getIntExtra("message", 0)
        textview.text = "Your Score is " + message + " out of 5"

        fun doSomething(v: View) {
            ttsobject.speak(textview.text.toString(), TextToSpeech.QUEUE_ADD, null)

        }

    }

}
