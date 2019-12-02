package com.example.blindexam.activities

import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.blindexam.R
import kotlinx.android.synthetic.main.question_card_item.view.*

class QuesTopicAdapter (val items : ArrayList<String>,val context : Context): RecyclerView.Adapter<ViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.question_card_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder?.quesType?.text = items.get(position)


    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
    val quesType = view.question_title





    init{


        view.setOnClickListener { view: View? ->
            if (quesType.text == "biology") {


                var detailPage = Intent(view?.context, QuestionPageActivity::class.java)
                detailPage.putExtra("message", "1")

                view?.context?.startActivity(detailPage)
            }

            else if (quesType.text == "english") {


                var detailPage = Intent(view?.context, QuestionPageActivity::class.java)
                detailPage.putExtra("message", "2")

                view?.context?.startActivity(detailPage)
                }
            else if (quesType.text == "Csc") {


                var detailPage = Intent(view?.context, QuestionPageActivity::class.java)
                detailPage.putExtra("message", "3")

                view?.context?.startActivity(detailPage)
            }
            else if (quesType.text == "chemistry") {


                var detailPage = Intent(view?.context, QuestionPageActivity::class.java)
                detailPage.putExtra("message", "4")

                view?.context?.startActivity(detailPage)
            }

        }
    }
}