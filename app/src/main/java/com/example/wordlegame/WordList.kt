package com.example.wordlegame

object WordList {
    private val words = listOf("WORD", "GAME", "PLAY", "TEST", "STAR", "SAIL", "MOON", "FISH", "TREE", "FIRE")

    fun getRandomFourLetterWord(): String {
        return words.random()
    }
}
