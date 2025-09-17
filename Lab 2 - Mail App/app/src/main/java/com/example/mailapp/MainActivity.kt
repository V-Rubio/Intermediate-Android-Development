package com.example.mailapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var emailsRv: RecyclerView
    private lateinit var loadMoreBtn: Button
    private lateinit var adapter: EmailAdapter
    private var emailsList: MutableList<Email> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailsRv = findViewById(R.id.emailsRv)
        loadMoreBtn = findViewById(R.id.loadMoreBtn)

        // Initialize list with first 10 emails
        emailsList = EmailFetcher.getEmails()

        adapter = EmailAdapter(emailsList)
        emailsRv.adapter = adapter
        emailsRv.layoutManager = LinearLayoutManager(this)

        loadMoreBtn.setOnClickListener {
            // Fetch next 5
            val next = EmailFetcher.getNext5Emails()
            adapter.addEmails(next)
            // scroll to show new ones
            emailsRv.scrollToPosition(emailsList.size - 1)
        }
    }
}
