package com.example.blindexam.activities

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blindexam.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    lateinit var textview : TextView
    lateinit var textview1: TextView
    lateinit var textview4 : TextView
    lateinit var ttsobject : TextToSpeech
    lateinit var editText : EditText
    lateinit var matList : MutableList<Student>
    lateinit var login : Button
    lateinit var text: String
    var text2: String = "You have been succesfully logged in. Tap the bottom left button " +
                        "to listen to the instructions for this page. "
    var student : ArrayList<String> = ArrayList()
    lateinit var ref :DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ttsobject = TextToSpeech(this, TextToSpeech.OnInitListener(){
            fun onInit(status : Int){
                if (status == TextToSpeech.SUCCESS){
                    ttsobject.setLanguage(Locale.US)
                }


            }
        })

        editText = findViewById(R.id.matric)
         login = findViewById(R.id.login)
        textview = findViewById(R.id.textView2)
        textview1 = findViewById(R.id.textView3)
        textview4 = findViewById(R.id.textView4)
        matList = mutableListOf()


        ref = FirebaseDatabase.getInstance().getReference("student")


       ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {

                for (h in snapshot.children) {

                        val yeah = h.getValue(Student::class.java)

                    student.add(yeah!!.matric)


                }
            }
        })


        login.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {

                checkMatric()


            }
        })
    }
    fun doSomething(v : View){
    when(v.id){
        R.id.button4 -> {
            ttsobject.speak("Welcome to the Examination Application." +
                    "You are in the home page. Tap the bottom right button" +
                    "to speak your matric number and be logged in for your examination." +
                    "When you tap the right bottom button, wait for the beep before speaking " +
                    "your matric number into it. Wait for another beep After speaking your matric number, then," +
                    "Tap the button at the top screen to log in. To hear this instruction again," +
                    "Tap the bottom left button.",
                TextToSpeech.QUEUE_FLUSH, null)
        }
    }

    }
    fun getSpeech(view : View){
        val intent = (Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH))
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        startActivityForResult(intent, 10)
    }

    override fun onActivityResult( requestCode : Int  ,  resultCode : Int ,  data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){
             10 -> {
                 if (resultCode == RESULT_OK && data != null) {
                     var result: ArrayList<String> =
                         data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                     editText.setText(result.get(0))
                 }
             }



        }
    }


    fun checkMatric(){
        val name = editText.text.toString()


            when (name){
                "100" ->{
                    val intent = (Intent( this, HomeActivity::class.java))
                    intent.putExtra("message", "1")
                    this.startActivity(intent)

                    ttsobject.speak(text2, TextToSpeech.QUEUE_FLUSH, null)
                }
                "200" -> {
                    val intent = (Intent( this, HomeActivity::class.java))
                    intent.putExtra("message", "2")
                    this.startActivity(intent)
                    ttsobject.speak(text2, TextToSpeech.QUEUE_FLUSH, null)

                }
                "101" -> {
                    val intent = (Intent( this, HomeActivity::class.java))
                    intent.putExtra("message", "1")
                    this.startActivity(intent)
                    ttsobject.speak(text2, TextToSpeech.QUEUE_FLUSH, null)

                }
                "102" -> {
                    val intent = (Intent( this, HomeActivity::class.java))
                    intent.putExtra("message", "1")
                    this.startActivity(intent)
                    ttsobject.speak(text2, TextToSpeech.QUEUE_FLUSH, null)

                }
                "201" -> {
                    val intent = (Intent( this, HomeActivity::class.java))
                    intent.putExtra("message", "2")
                    this.startActivity(intent)
                    ttsobject.speak(text2, TextToSpeech.QUEUE_FLUSH, null)

                }
                else -> {
                    ttsobject.speak("the Matric Number Entered does not belong to any student." +
                            "Tap the bottom right corner to input your matric number again. Tap the bottom left button" +
                            "to listen to the instructions for this page",
                        TextToSpeech.QUEUE_FLUSH, null)
                }





        }

    }
}
