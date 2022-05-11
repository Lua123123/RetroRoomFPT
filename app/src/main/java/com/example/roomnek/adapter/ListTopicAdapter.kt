package com.example.roomnek.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomnek.R
import com.example.roomnek.model.Success

class ListTopicAdapter(private var postsList: MutableList<Success>, private val context: Context) :
    RecyclerView.Adapter<ListTopicAdapter.ViewHolder>() {

    fun setData(postsList: List<Success>?) {
//        this.postsList?.clear()
//        this.postsList?.addAll(postsList ?: emptyList())
        this.postsList = postsList as MutableList<Success>;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_topic_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val success = postsList!![position]
        if (success == null) {
            return
        }
        holder.tvTitle.text = success.name
        holder.tvMount.text = success.soluong.toString()
    }

    override fun getItemCount(): Int {
        return if (postsList != null) {
            postsList!!.size
        } else 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView
        val tvMount: TextView
        private val item_topic: LinearLayout

        init {
            tvTitle = itemView.findViewById(R.id.topic)
            tvMount = itemView.findViewById(R.id.amount)
            item_topic = itemView.findViewById(R.id.item_topic)
        }
    }
}