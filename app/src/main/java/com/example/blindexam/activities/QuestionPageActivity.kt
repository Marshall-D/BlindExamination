package com.example.blindexam.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blindexam.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_question_page.*
import java.util.*
import kotlin.collections.ArrayList

class QuestionPageActivity : AppCompatActivity() {
    val qText : ArrayList<String> = ArrayList()
    val answer : ArrayList<String> = ArrayList()
    val optionAArray : ArrayList<String> = ArrayList()
    val optionBArray : ArrayList<String> = ArrayList()
    val optionCArray : ArrayList<String> = ArrayList()


    lateinit var ref : DatabaseReference
    lateinit var textview : TextView
//    lateinit var next : Button
//    lateinit var previous : Button
    lateinit var optionA : TextView
    lateinit var optionB : TextView
    lateinit var optionC : TextView
//    lateinit var optionSelect : RadioButton
//    lateinit var options : RadioGroup
    lateinit var reply : String
    lateinit var ttsobject : TextToSpeech
    lateinit var text: String
    lateinit var text2: String
    lateinit var text3: String
    lateinit var text4: String
    var text5: String = "tap the right bottom button to pick an answer.To pick option a, speak option a after the beep. " +
            "To pick option b, speak option b, And the same for option c. Tap the bottom left button to listen " +
            "to the question again."
     lateinit var text6: String

    lateinit var editText : EditText
    lateinit var textview5 : TextView

    var j : Int = 0
    var k : Int = 0
    var l : Int = 0
    var m : Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_page)
        ttsobject = TextToSpeech(this, TextToSpeech.OnInitListener(){
            fun onInit(status : Int){
                if (status == TextToSpeech.SUCCESS){
                    ttsobject.setLanguage(Locale.US)
                }


            }
        })

        textview = findViewById(R.id.textView)
        textview5 = findViewById(R.id.textView5)

        editText = findViewById(R.id.editText)

//        next = findViewById(R.id.button)
//        previous = findViewById(R.id.button2)
        optionA = findViewById(R.id.textView2)
        optionB = findViewById(R.id.textView3)
        optionC = findViewById(R.id.textView4)
        reply = "a"
        answer.add("false")
        answer.add("false")
        answer.add("false")
        answer.add("false")
        answer.add("false")



        val message = intent.getStringExtra("message")
        if (message == "1") {
            ref = FirebaseDatabase.getInstance().getReference("Department/1/exam/0/questions")
        } else if (message == "2"){
            ref = FirebaseDatabase.getInstance().getReference("Department/1/exam/1/questions")

        }
        else if (message == "3"){
            ref = FirebaseDatabase.getInstance().getReference("Department/0/exam/0/questions")

        }else if (message == "4"){
            ref = FirebaseDatabase.getInstance().getReference("Department/0/exam/1/questions")

        }



        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {


                for (h in snapshot.children) {

                    val yeah = h.getValue(Question::class.java)

                    qText.add(yeah!!.questionText)
                    optionAArray.add(yeah.optionA)
                    optionBArray.add(yeah.optionB)
                    optionCArray.add(yeah.optionC)
                    // textview.text= yeah!!.correctAnswer
                    textview.text = qText[j]
                    optionA.text = optionAArray[k]
                    optionB.text = optionBArray[l]
                    optionC.text = optionCArray[m]


                }


            }

        })

    }
    fun nextQuestion(v:View){
        if (j == 5){ j = 0 }
        if (k == 5){ k = 0 }
        if (l == 5){ l = 0 }
        if (m == 5){ m = 0 }


        if ( editText.text.toString() == "option a"){
            answer[j] = "true"
        }
        if ( editText.text.toString() == "option b"){
            answer[j] = "false"
        }
        if ( editText.text.toString() == "option c" || editText.text.toString() == "option see"|| editText.text.toString() == "option sea"){
            answer[j] = "false"
        }

        if ( editText.text.toString() != "option a" && editText.text.toString() != "option b"
            && editText.text.toString() != "option c" && editText.text.toString() != "option see" && editText.text.toString() != "option sea" ){
            ttsobject.speak("Your answer is not recognised.", TextToSpeech.QUEUE_ADD, null)
            ttsobject.speak(text5, TextToSpeech.QUEUE_ADD, null)

        }
        else {
            ttsobject.speak("You have successfully submitted your answer." +
                    " Tap the bottom left button to read the question for this page ",
                     TextToSpeech.QUEUE_ADD, null)

            textview.text = qText[j++]
            optionA.text = optionAArray[k++]
            optionB.text = optionBArray[l++]
            optionC.text = optionCArray[m++]
        }



    }
    fun seeResult(v:View){
        var t = 0

        for (g in answer){
            if (g == "true"){
                t++
            }

        }



        val intent = (Intent( this, ResultActivity::class.java))
        intent.putExtra("message", t)
        this.startActivity(intent)
        text6 = "Your score is " + t + " out of 5"
        ttsobject.speak(text6, TextToSpeech.QUEUE_ADD, null)

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
    fun doSomething(v: View) {
        when (v.id) {
            R.id.button4 -> {
                text = textview.getText().toString()
                text2 = optionA.getText().toString()
                text3 = optionB.getText().toString()
                text4 = optionC.getText().toString()

                ttsobject.speak(text, TextToSpeech.QUEUE_ADD, null)
                ttsobject.speak(text2, TextToSpeech.QUEUE_ADD, null)
                ttsobject.speak(text3, TextToSpeech.QUEUE_ADD, null)
                ttsobject.speak(text4, TextToSpeech.QUEUE_ADD, null)
                ttsobject.speak(text5, TextToSpeech.QUEUE_ADD, null)



            }

        }

    }


}
