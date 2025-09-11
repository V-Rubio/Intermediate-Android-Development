package com.example.project1_wordle

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import checkGuess
import com.example.project1_wordle.util.FourLetterWordList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userGuess = findViewById<EditText>(R.id.textInput)
        val displayWordToGuess = findViewById<TextView>(R.id.displayWordToGuess)
        val submitButton = findViewById<Button>(R.id.submitButton)
        var wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        val allWords = FourLetterWordList.getAllFourLetterWords()
        var guessCount = 0

        submitButton.setOnClickListener {
            val inputLength = userGuess.text.toString().length
            if (submitButton.text == "New Game"){
                wordToGuess = FourLetterWordList.getRandomFourLetterWord()
                displayWordToGuess.setText("XXXX")
                resetView()
                return@setOnClickListener
            } else if (inputLength != 4) {
                Toast.makeText(
                    this,
                    "Word should only be 4 letters. Try again.",
                    Toast.LENGTH_SHORT
                ).show()
                userGuess.setText("")
            } else {
                guessCount++
                val results =
                    checkGuess(userGuess.text.toString().uppercase(), wordToGuess.uppercase())
                if (guessCount <= 2) {
                    setResults(results, userGuess.text.toString().uppercase(), guessCount)
                } else {
                    setResults(results, userGuess.text.toString().uppercase(), guessCount)
                    guessCount = 0
                    Toast.makeText(
                        this,
                        "Good Game! The word was: ${wordToGuess.uppercase()}!",
                        Toast.LENGTH_SHORT
                    ).show()
                    submitButton.setText("New Game")
                    displayWordToGuess.setText(wordToGuess.uppercase())
                }
            }
        }
    }

    fun setResults(results: String, userGuess: String, linePlacement: Int) {
        // Mapping for each guess line
        val guessFields = arrayOf(
            arrayOf(R.id.guess1a, R.id.guess1b, R.id.guess1c, R.id.guess1d),
            arrayOf(R.id.guess2a, R.id.guess2b, R.id.guess2c, R.id.guess2d),
            arrayOf(R.id.guess3a, R.id.guess3b, R.id.guess3c, R.id.guess3d)
        )

        // Defensive: Make sure linePlacement is in range
        if (linePlacement !in 1..3 || userGuess.length < 4 || results.length < 4) {
            Toast.makeText(this, "Invalid input or guess count", Toast.LENGTH_SHORT).show()
            return
        }

        // Loop through each character in the guess
        for (i in 0..3) {
            val letter = userGuess[i].toString()
            val result = results[i]
            val viewId = guessFields[linePlacement-1][i]
            val field = findViewById<EditText>(viewId)

            // Set the letter
            field.setText(letter)

            // Set the color based on result
            when (result) {
                'O' -> field.setTextColor(getColor(R.color.correct_position)) // green
                '+' -> field.setTextColor(getColor(R.color.wrong_position))   // yellow
                'X' -> field.setTextColor(getColor(R.color.not_in_word))      // black
            }

            // Optional: disable the field so it's not editable
            field.isEnabled = false
        }

        // Clear the textInput after handling the guess
        val inputField = findViewById<EditText>(R.id.textInput)
        inputField.setText("")
    }

    private fun resetView() {
        // List of all EditText field IDs that should be cleared
        val fieldsToClear = listOf(
            R.id.guess1a, R.id.guess1b, R.id.guess1c, R.id.guess1d,
            R.id.guess2a, R.id.guess2b, R.id.guess2c, R.id.guess2d,
            R.id.guess3a, R.id.guess3b, R.id.guess3c, R.id.guess3d,
            R.id.textInput // The main input field
        )

        // Loop through each and clear the text
        for (id in fieldsToClear) {
            val field = findViewById<EditText>(id)
            field.setText("")
        }

        val submitButton = findViewById<Button>(R.id.submitButton)
        submitButton.setText("Submit")
    }
}



