package com.example.blindexam.activities

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blindexam.R
import kotlinx.android.synthetic.main.question_card_item.view.*

class QuestionsAdapter (val items : ArrayList<String>,val context : Context): RecyclerView.Adapter<ViewHolder2>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        return ViewHolder2(LayoutInflater.from(context).inflate(R.layout.question_card_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        holder?.quesType?.text = items.get(position)
    }

}

class ViewHolder2 (view: View) : RecyclerView.ViewHolder(view){

    val quesType = view.question_title
//    init{
//        view.setOnClickListener { view: View? ->
//
//
//            var detailPage = Intent(view?.context, QuestionPageActivity::class.java)
//            view?.context?.startActivity(detailPage)
//
//        }
//    }
}