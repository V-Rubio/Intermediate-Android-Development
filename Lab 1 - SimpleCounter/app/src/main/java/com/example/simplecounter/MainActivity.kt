package com.example.simplecounter

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type.systemBars

class MainActivity : AppCompatActivity() {

    private var total_count = 0
    private var increment_value = 1
    private var next_upgrade_goal = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Insets (optional for fullscreen, not needed unless you're using it)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val incrementButton = findViewById<Button>(R.id.increment_button)
        val upgradeButton = findViewById<Button>(R.id.upgrade_button)
        val counterTotalText = findViewById<TextView>(R.id.counter_total_text)

        // Initialize UI
        incrementButton.text = "Add $increment_value"
        upgradeButton.visibility = View.GONE // Initially hidden

        // Increment button click
        incrementButton.setOnClickListener {
            total_count += increment_value
            counterTotalText.text = total_count.toString()
            Toast.makeText(this, "Another one!", Toast.LENGTH_SHORT).show()

            // Show upgrade button if goal is reached
            if (total_count >= next_upgrade_goal) {
                upgradeButton.text = "Upgrade to Add ${increment_value+1}"
                upgradeButton.visibility = View.VISIBLE
            }
        }

        // Upgrade button click
        upgradeButton.setOnClickListener {
            upgradeButton.visibility = View.GONE
            increment_value++
            incrementButton.text = "Add $increment_value"
            Toast.makeText(this, "Upgraded to Add ${increment_value}!", Toast.LENGTH_SHORT).show()

            // Calculate next upgrade threshold
            next_upgrade_goal += (2 * increment_value) + total_count
        }
    }
}
