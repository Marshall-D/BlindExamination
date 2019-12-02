package com.example.blindexam.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blindexam.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity() {
    lateinit var ref: DatabaseReference
    lateinit var ttsobject : TextToSpeech
    lateinit var textview4 : TextView
    lateinit var text: String
    lateinit var editText : EditText
    var text2: String = "You have been succesfully logged in. Tap the bottom left button " +
            "to listen to the questions and instructions for this page. "
    val quesTopic: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        editText = findViewById(R.id.editText)

//        question_title = findViewById(R.id.question_title)

        ttsobject = TextToSpeech(this, TextToSpeech.OnInitListener(){
            fun onInit(status : Int){
                if (status == TextToSpeech.SUCCESS){
                    ttsobject.setLanguage(Locale.US)
                }


            }
        })

        val message = intent.getStringExtra("message")
        if (message == "1") {
            ref = FirebaseDatabase.getInstance().getReference("Department/0/exam")
        } else if (message == "2") {
            ref = FirebaseDatabase.getInstance().getReference("Department/1/exam")

        }

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {


                for (h in snapshot.children) {

                    val yeah = h.getValue(Student::class.java)

                    quesTopic.add(yeah!!.exam_name)
                    text = yeah.exam_instructions


                }

            }

        })


    }

    fun doSomething(v: View) {
        when (v.id) {
            R.id.button4 -> {

                ttsobject.speak(text, TextToSpeech.QUEUE_FLUSH, null)

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

    fun checkExamName(v:View) {
        var check = false
        val name = editText.text.toString()
            for (i in quesTopic){
                if (i == name){
                     check = true
                }
            }



        if (check == true) {
            when (name) {
                "computer" -> {
                        var detailPage =
                            Intent(v?.context, QuestionPageActivity::class.java)
                        detailPage.putExtra("message", "3")

                        v?.context?.startActivity(detailPage)
                       ttsobject.speak(text2, TextToSpeech.QUEUE_FLUSH, null)



                }
                "chemistry" -> {

                        var detailPage =
                            Intent(v?.context, QuestionPageActivity::class.java)
                        detailPage.putExtra("message", "4")

                        v?.context?.startActivity(detailPage)
                        ttsobject.speak(text2, TextToSpeech.QUEUE_FLUSH, null)



                }
                "biology" -> {

                        var detailPage =
                            Intent(v?.context, QuestionPageActivity::class.java)
                        detailPage.putExtra("message", "1")

                        v?.context?.startActivity(detailPage)
                        ttsobject.speak(text2, TextToSpeech.QUEUE_FLUSH, null)


                }
                "English" -> {


                        var detailPage =
                            Intent(v?.context, QuestionPageActivity::class.java)
                        detailPage.putExtra("message", "2")

                        v?.context?.startActivity(detailPage)
                       ttsobject.speak(text2, TextToSpeech.QUEUE_FLUSH, null)


                }
                else -> {

                    ttsobject.speak(
                        "the Examination name  Entered does not belong to this department." +
                                "Tap the bottom right button to input the examination name again.",
                        TextToSpeech.QUEUE_FLUSH, null
                    )
                }
            }

        }
        else
            ttsobject.speak(
                "the Examination name  Entered does not belong to this department." +
                        "Tap the bottom right button to input the examination name again.",
                TextToSpeech.QUEUE_FLUSH, null
            )





    }
}


