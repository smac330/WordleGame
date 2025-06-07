package com.example.wordlegame

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge

class MainActivity : AppCompatActivity() {
    private lateinit var guessInput: EditText
    private lateinit var submitButton: Button
    private lateinit var resetButton: Button
    private lateinit var resultText: TextView
    private lateinit var guessViews: List<TextView>
    private lateinit var checkViews: List<TextView>

    private var wordToGuess = ""
    private var attempts = 0
    private val maxAttempts = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Bind UI elements
        guessInput = findViewById(R.id.guessInput)
        submitButton = findViewById(R.id.submitButton)
        resetButton = findViewById(R.id.resetButton)
        resultText = findViewById(R.id.wordToGuess)

        guessViews = listOf(
            findViewById(R.id.guess1),
            findViewById(R.id.guess2),
            findViewById(R.id.guess3)
        )

        checkViews = listOf(
            findViewById(R.id.check1),
            findViewById(R.id.check2),
            findViewById(R.id.check3)
        )

        startNewGame()

        submitButton.setOnClickListener {
            val guess = guessInput.text.toString().uppercase()
            if (guess.length != 4) {
                Toast.makeText(this, "Enter a 4-letter word", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = checkGuess(wordToGuess, guess)

            if (attempts < maxAttempts) {
                guessViews[attempts].text = "Guess #${attempts + 1}: $guess"
                checkViews[attempts].text = "Check #${attempts + 1}: $result"
            }

            attempts++
            if (guess == wordToGuess || attempts == maxAttempts) {
                endGame()
            }

            guessInput.text.clear()
        }

        resetButton.setOnClickListener {
            startNewGame()
        }
    }

    private fun startNewGame() {
        wordToGuess = WordList.getRandomFourLetterWord()
        attempts = 0

        for (i in 0 until maxAttempts) {
            guessViews[i].text = ""
            checkViews[i].text = ""
        }

        resultText.text = ""
        guessInput.isEnabled = true
        submitButton.isEnabled = true
        resetButton.visibility = Button.GONE
    }

    private fun endGame() {
        submitButton.isEnabled = false
        guessInput.isEnabled = false
        resultText.text = "The word was: $wordToGuess"
        resetButton.visibility = Button.VISIBLE
    }

    private fun checkGuess(wordToGuess: String, userGuess: String): String {
        val result = StringBuilder()

        for (i in userGuess.indices) {
            val guessChar = userGuess[i]
            val targetChar = wordToGuess[i]

            when {
                guessChar == targetChar -> result.append("O")
                guessChar in wordToGuess -> result.append("+")
                else -> result.append("X")
            }
        }

        return result.toString()
    }
}
