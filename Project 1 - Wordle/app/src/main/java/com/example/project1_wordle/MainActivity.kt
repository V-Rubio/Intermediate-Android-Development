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
        val displayCorrectGuessCount = findViewById<TextView>(R.id.displayCorrectGuessCount)
        val submitButton = findViewById<Button>(R.id.submitButton)
        var wordToGuess = FourLetterWordList.getRandomFourLetterWord().uppercase()
        val allWords = FourLetterWordList.getAllFourLetterWords()
        var correctGuessCount = 0
        var guessCount = 0

        displayWordToGuess.setText(wordToGuess)

        submitButton.setOnClickListener {
            val normalizedUserGuess = userGuess.text.toString().uppercase()
            val inputLength = normalizedUserGuess.length
            if (submitButton.text == "New Game"){
                wordToGuess = FourLetterWordList.getRandomFourLetterWord()
                displayWordToGuess.setText("XXXX")
                displayWordToGuess.setText(wordToGuess)
                resetView()
                return@setOnClickListener
            } else if (normalizedUserGuess.length != 4 || !normalizedUserGuess.matches(Regex("^[A-Z]{4}$"))) {
                Toast.makeText(this, "Please enter a 4-letter word using only A-Z letters.", Toast.LENGTH_SHORT).show()
                userGuess.setText("")
                return@setOnClickListener
            } else {
                guessCount++
                val results =
                    checkGuess(normalizedUserGuess, wordToGuess)
                if (guessCount <= 2) {
                    setResults(results, normalizedUserGuess, guessCount)
                    if (results == "OOOO"){
                        correctGuessCount++
                        displayCorrectGuessCount.text = correctGuessCount.toString()
                        guessCount = 0
                        submitButton.setText("New Game")
                        displayWordToGuess.setText(wordToGuess)
                        Toast.makeText(
                            this,
                            "You got it!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                } else {
                    setResults(results, normalizedUserGuess, guessCount)
                    if (results == "OOOO"){
                        correctGuessCount++
                        displayCorrectGuessCount.text = correctGuessCount.toString()
                        Toast.makeText(
                            this,
                            "You got it!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this,
                            "Good Game! The word was: ${wordToGuess}!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    guessCount = 0
                    submitButton.setText("New Game")
                    displayWordToGuess.setText(wordToGuess)
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
                else -> field.setTextColor(getColor(R.color.not_in_word)) // default fallback
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



