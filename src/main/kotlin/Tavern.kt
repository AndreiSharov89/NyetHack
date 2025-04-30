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
private val menuItemPrices: Map<String, Double> = List(menuData.size) { index ->
    val (_, name, price) = menuData[index].split(",")
    name to price.toDouble()
}.toMap()
private val menuItemTypes: Map<String, String> = List(menuData.size) { index ->
    val (type, name, _) = menuData[index].split(",")
    name to type
}.toMap()

fun visitTavern() {
    narrate("$heroName enters $TAVERN_NAME")
    narrate("There are several items for sale:\n")
    printMenu(menuData)
    val patrons: MutableSet<String> = mutableSetOf()

    val patronGold = mutableMapOf(
        TAVERN_MASTER to 86.00,
        heroName to 4.50
    )

    while (patrons.size < 5) {
        val patronName = "${firstNames.random()} ${lastNames.random()}"
        patrons += patronName
        patronGold += patronName to 6.0
    }
    displayPatronBalances(patronGold)
    narrate("$heroName sees several patrons in the tavern:")
    narrate(patrons.joinToString())

    repeat(3) {
        placeOrder(patrons.random(), menuItems.random(), patronGold)
    }
    displayPatronBalances(patronGold)
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
    return "$name" + ".".repeat(dif + 1) + price
}

private fun placeOrder(
    patronName: String,
    menuItemName: String,
    patrnGold: MutableMap<String, Double>
) {
    val itemPrice = menuItemPrices.getValue(menuItemName)
    narrate("$patronName speaks with $TAVERN_MASTER to place an order")
    if (itemPrice <= patrnGold.getOrDefault(patronName, 0.0)) {
        val action = when (menuItemTypes[menuItemName]){
            "shandy", "elixir" -> "pours"
            "meal" -> "serves"
            else -> "hands"
        }
        narrate("$TAVERN_MASTER $action $patronName a $menuItemName")
        narrate("$patronName pays $TAVERN_MASTER $itemPrice gold")
        patrnGold[patronName] = patrnGold.getValue(patronName) - itemPrice
        patrnGold[TAVERN_MASTER] = patrnGold.getValue(TAVERN_MASTER) + itemPrice
    } else {
        narrate("$TAVERN_MASTER says, \"You need more coin for $menuItemName\"")
    }
}

private fun displayPatronBalances(patronGold: Map<String, Double>) {
    narrate("$heroName intuitively knows how much money each patron has")
    patronGold.forEach {(patron, balace) ->
        narrate("$patron has ${"%.2f".format(balace)} gold")
    }
}