package org.example

import java.awt.MenuItem
import java.io.File

private const val TAVERN_MASTER = "Taernyl"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

private val firstNames = setOf("Alex", "Mordoc", "Sophie", "Tariq")
private val lastNames = setOf("Ironfoot", "Fernsworth", "Baggins", "Downstrider")

private val menuData = File("data/tavern-menu-items.txt").readText().split("\n")
private val menuItems = List(menuData.size) { index ->
    val (_, name, _) = menuData[index].split(",")
    name
}

fun visitTavern() {
    narrate("$heroName enters $TAVERN_NAME")
    narrate("There are several items for sale:\n")
    printMenu(menuData)
    val patrons: MutableSet<String> = mutableSetOf()
    while (patrons.size < 10) {
        patrons += "${firstNames.random()} ${lastNames.random()}"
    }
    narrate("$heroName sees several patrons in the tavern:")
    narrate(patrons.joinToString())

    repeat(3) {
        placeOrder(patrons.random(), menuItems.random())
    }
}

private fun placeOrder(patronName: String, menuItemName: String) {
    narrate("$patronName speaks with $TAVERN_MASTER to place an order")
    narrate("$TAVERN_MASTER hands $patronName a $menuItemName")
}

private fun printMenu(menuData: List<String>) {
    val header = "*** Welcome to $TAVERN_NAME ***"
    var length = header.length
    val menuItems = List(menuData.size) { index ->
        val (_, name, _) = menuData[index].split(",")
        name
    }
    val menuTypes: MutableSet<String> = mutableSetOf()
    for (item in menuData) {
        val (type, _, _) = item.split(",")
        menuTypes.add(type)
    }
    for (item in menuItems) {
        if ((item.length + 6) > length) {
            length = item.length + 6
        }
    }
    println(printCentered(header, length))
    for (type in menuTypes) {
        println(printCentered("~[$type]~", length))
        for (item in menuData) {
            val (typein, name, price) = item.split(",")
            if (typein == type) {
                println(printDotted(item, length))
            }
        }
    }
    println()
}

private fun printCentered(s: String, l: Int): String {
    val dif = l - s.length
    if (dif % 2 == 0) {
        return " ".repeat(dif / 2) + "$s" + " ".repeat(dif / 2)
    } else {
        return " ".repeat(dif / 2) + "$s " + " ".repeat(dif / 2)
    }
}

private fun printDotted(s: String, l: Int): String {
    val (_, name, price) = s.split(",")
    val dif = l - name.length - price.length - 1
    return "$name" + ".".repeat(dif+1) + price
}