package com.example.iweather

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import kotlin.random.Random

object ColorGradientUtil {


    private val arrayColor = arrayOf(
        Pair("#0093E9", "#80D0C7"),
        Pair("#4158D0", "#C850C0"),
        Pair("#8EC5FC", "#E0C3FC"),
        Pair("#FFFFFF", "#6284FF"),
        Pair("#8BC6EC", "#9599E2"),
        Pair("#85FFBD", "#FFFB7D"),
        Pair("#21D4FD", "#B721FF"),
        Pair("#FFDEE9", "#B5FFFC"),
        Pair("#08AEEA", "#2AF598"),
        Pair("#3EECAC", "#EE74E1"),
        Pair("#FA8BFF", "#2BD2FF"),
        Pair("#FF3CAC", "#784BA0"),
        Pair("#FAD961", "#F76B1C"),
    )

    fun generateColorGradient(position:Int): GradientDrawable {

        val (firstColor, secondColor) = if(position< arrayColor.size) arrayColor[position] else arrayColor[(0..12).shuffled().first()]

        val colors = intArrayOf(Color.parseColor(firstColor), Color.parseColor(secondColor))
        val gd = GradientDrawable(
            GradientDrawable.Orientation.TR_BL, colors
        )
        gd.cornerRadius = 50f
        return gd
    }
}