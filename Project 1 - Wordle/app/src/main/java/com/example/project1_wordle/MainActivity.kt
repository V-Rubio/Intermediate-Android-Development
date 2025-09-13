package com.example.project1_wordle

import ThemeAdapter
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import checkGuess
import com.example.project1_wordle.util.FourLetterWordList
import com.github.jinatonic.confetti.CommonConfetti
import com.github.jinatonic.confetti.ConfettiManager

class MainActivity : AppCompatActivity() {
    private var confettiManager: ConfettiManager? = null
    private var selectedTheme: String = "Scrambled"  // default theme
    private lateinit var adapter: ThemeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the container
        val container = findViewById<ConstraintLayout>(R.id.main)

        val userGuess = findViewById<EditText>(R.id.textInput)
        val displayWordToGuess = findViewById<TextView>(R.id.displayWordToGuess)
        val displayCorrectGuessCount = findViewById<TextView>(R.id.displayCorrectGuessCount)
        val submitButton = findViewById<Button>(R.id.submitButton)
        var wordToGuess = when (selectedTheme) {
            "Animals" -> FourLetterWordList.getRandomAnimalWord()
            "Foods" -> FourLetterWordList.getRandomFoodWord()
            "Space" -> FourLetterWordList.getRandomSpaceWord()
            else -> FourLetterWordList.getRandomFourLetterWord() // default "Scrambled"
        }.uppercase()

        val allWords = FourLetterWordList.getAllFourLetterWords()
        var correctGuessCount = 0
        var guessCount = 0

        val recyclerView = findViewById<RecyclerView>(R.id.themeRecyclerView)
        val themes = listOf("Scrambled", "Animals", "Foods", "Space") // colors and countries

        adapter = ThemeAdapter(themes, selectedTheme) { newTheme ->
            selectedTheme = newTheme
            Toast.makeText(this, "Selected theme: $selectedTheme", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.visibility = View.GONE

//        displayWordToGuess.setText(wordToGuess)

        submitButton.setOnClickListener {
            val normalizedUserGuess = userGuess.text.toString().uppercase()
            val inputLength = normalizedUserGuess.length
            if (submitButton.text == "New Game"){
                recyclerView.visibility = View.GONE
                confettiManager?.terminate()
                wordToGuess = when (selectedTheme) {
                    "Animals" -> FourLetterWordList.getRandomAnimalWord()
                    "Foods" -> FourLetterWordList.getRandomFoodWord()
                    "Space" -> FourLetterWordList.getRandomSpaceWord()
                    else -> FourLetterWordList.getRandomFourLetterWord() // default "Scrambled"
                }.uppercase()
                displayWordToGuess.setText("XXXX")
//                displayWordToGuess.setText(wordToGuess)
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
                        confettiManager = CommonConfetti.rainingConfetti(
                            container,
                            intArrayOf(getColor(R.color.gold))
                        ).stream(300)
                        recyclerView.visibility = View.VISIBLE
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
                        confettiManager = CommonConfetti.rainingConfetti(
                            container,
                            intArrayOf(getColor(R.color.gold))
                        ).stream(300)
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
                    recyclerView.visibility = View.VISIBLE
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



