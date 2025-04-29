package org.example

fun main() {
    narrate("A hero enters the town of Kronshtadt. What is their name?", ::makeYellow)
    val heroName = readLine()
    require(heroName != null && heroName.isNotEmpty()) {
        "A hero must have a name."
    }

    changeNarratorMood()
    narrate("$heroName, ${createTitle(heroName)} heads to the town square.")
}

private fun makeYellow(message: String) = "\u001b[33;1m$message\u001b[0m"

private fun createTitle(name: String): String {
    return when {
        name.all { it.isDigit() } -> "The Identifiable"
        name.none { it.isLetter() } -> "The Witness Protection Member"
        name.count { it.lowercase() in "aeiou" } > 4 -> "The Master of Vowel"
        else -> "The Renowned Hero"
    }
}