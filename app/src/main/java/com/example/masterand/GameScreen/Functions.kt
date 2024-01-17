package com.example.masterand.GameScreen

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color

fun selectNextAvailableColor(
    availableColors: List<Color>,
    chosenColors: List<Color>,
    buttonIndex: Int
): Color {
    var chosenColorIndexInc = availableColors.indexOf(chosenColors[buttonIndex])
    var found = false
    while (!found) {
        chosenColorIndexInc++
        if (chosenColorIndexInc > availableColors.count() - 1) {
            chosenColorIndexInc = 0
        }
        if (!chosenColors.contains(availableColors[chosenColorIndexInc])) {
            found = true
        }
    }
    return availableColors[chosenColorIndexInc]
}

fun selectRandomColors(availableColors: List<Color>): List<Color> {
    val c = availableColors.toMutableList()
    val c1 = c.random()
    c.remove(c1)
    val c2 = c.random()
    c.remove(c2)
    val c3 = c.random()
    c.remove(c3)
    val c4 = c.random()
    return listOf(c1, c2, c3, c4)
}

fun checkColors(
    choosenColors: List<Color>,
    correctColors: List<Color>,
    notFoundColor: Color
): SnapshotStateList<Color> {
    val feedbackColors: SnapshotStateList<Color> = SnapshotStateList()
    for (i in correctColors.indices) {
        if (correctColors[i] == choosenColors[i]) {
            feedbackColors.add(Color.Red)
        } else if (choosenColors[i] in correctColors) {
            feedbackColors.add(Color.Yellow)
        } else {
            feedbackColors.add(notFoundColor)
        }
    }
    return feedbackColors
}
