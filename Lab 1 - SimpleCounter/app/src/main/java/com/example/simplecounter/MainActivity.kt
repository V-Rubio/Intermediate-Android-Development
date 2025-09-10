package com.example.simplecounter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    private var total_count = 0
    private var increment_value = 1
    private var next_upgrade_goal = 10
    private var currentThemeIndex = 0

    private val totalThemes = 3 // Number of themes to cycle through

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainLayout = findViewById<ConstraintLayout>(R.id.main)
        val incrementButton = findViewById<Button>(R.id.increment_button)
        val upgradeButton = findViewById<Button>(R.id.upgrade_button)
        val counterTotalText = findViewById<TextView>(R.id.counter_total_text)
        val shuffleThemeButton = findViewById<Button>(R.id.shuffle_theme_button)
        val nextUpgradeGoalText = findViewById<TextView>(R.id.next_upgrade_goal)
        val nextUpgradeGoalTitleText = findViewById<TextView>(R.id.next_upgrade_goal_title)

        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI
        incrementButton.text = "Add $increment_value"
        upgradeButton.visibility = View.GONE

        // Increment logic
        incrementButton.setOnClickListener {
            total_count += increment_value
            counterTotalText.text = total_count.toString()
            Toast.makeText(this, "Another one!", Toast.LENGTH_SHORT).show()

            if (total_count >= next_upgrade_goal) {
                upgradeButton.text = "Upgrade to Add ${increment_value + 1}"
                upgradeButton.visibility = View.VISIBLE
            }
        }

        // Upgrade logic
        upgradeButton.setOnClickListener {
            upgradeButton.visibility = View.GONE
            increment_value++
            incrementButton.text = "Add $increment_value"
            Toast.makeText(this, "Upgraded to Add $increment_value!", Toast.LENGTH_SHORT).show()
            next_upgrade_goal += (2 * increment_value) + total_count
            nextUpgradeGoalText.text = next_upgrade_goal.toString()
        }

        // Shuffle theme logic
        shuffleThemeButton.setOnClickListener {
            currentThemeIndex = (currentThemeIndex + 1) % totalThemes
            when (currentThemeIndex) {
                0 -> {
                    // Default Theme
                    mainLayout.background = null
                    incrementButton.setBackgroundResource(android.R.drawable.btn_default)
                    incrementButton.text = "Add $increment_value"
                    incrementButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)  // Clear cookie drawable
                    counterTotalText.setTextColor(Color.BLACK)  // Reset text color if needed
                    incrementButton.gravity = Gravity.CENTER
                }

                1 -> {
                    // Dog background, text button
                    mainLayout.background = ContextCompat.getDrawable(this, R.drawable.dog_background)
                    counterTotalText.setTextColor(Color.WHITE)
                    incrementButton.setBackgroundResource(android.R.drawable.btn_default)
                    incrementButton.text = "Add $increment_value"
                }

                2 -> {
                    // White background, cookie image button (no text)
                    mainLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
                    incrementButton.setBackgroundResource(0) // Removes default background
                    incrementButton.text = "" // No text
                    incrementButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.cookie_button, 0, 0)
                    incrementButton.gravity = Gravity.CENTER
                    incrementButton.compoundDrawablePadding = 0
                    counterTotalText.setTextColor(Color.BLACK)

                }
            }
        }
    }
}
