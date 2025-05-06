package com.bignerdranch.nyethack

val player = Player()

fun main() {
    narrate("${player.name} is ${player.title}")
    player.changeName("Aurelia")
    narrate("${player.name}, ${player.title}, heads to the town square.")
    visitTavern()
    player.castFireball()
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

private fun promptHeroName(): String {
    narrate("A hero enters the town of Kronshtadt. What is their name?", ::makeYellow)
/*    val heroName = readlnOrNull()
    require(!heroName.isNullOrEmpty()) {
        "A hero must have a name."
    }
    return heroName*/
    println("Madrigal")
    return "Madrigal"
}