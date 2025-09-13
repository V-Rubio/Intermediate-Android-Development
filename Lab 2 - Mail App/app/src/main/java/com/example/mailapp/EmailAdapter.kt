package com.example.mailapp

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmailAdapter(private val emails: MutableList<Email>) :
    RecyclerView.Adapter<EmailAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val senderImageView: ImageView = itemView.findViewById(R.id.senderImageView)
        val senderTextView: TextView = itemView.findViewById(R.id.senderTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val summaryTextView: TextView = itemView.findViewById(R.id.summaryTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.email_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val email = emails[position]
        // placeholder image: same for everyone
        holder.senderImageView.setImageResource(R.drawable.ic_sender_placeholder)
        holder.senderTextView.text = email.sender
        holder.dateTextView.text = email.date
        holder.titleTextView.text = email.title
        holder.summaryTextView.text = email.summary

        if (email.isUnread) {
            holder.senderTextView.setTypeface(null, Typeface.BOLD)
            holder.titleTextView.setTypeface(null, Typeface.BOLD)
        } else {
            holder.senderTextView.setTypeface(null, Typeface.NORMAL)
            holder.titleTextView.setTypeface(null, Typeface.NORMAL)
        }
    }

    override fun getItemCount(): Int {
        return emails.size
    }

    fun addEmails(newEmails: List<Email>) {
        val start = emails.size
        emails.addAll(newEmails)
        notifyItemRangeInserted(start, newEmails.size)
    }
}
