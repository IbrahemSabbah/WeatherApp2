package com.example.iweather.ui.main.weatherList

import android.R.attr
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.StaggeredGridLayoutManager

import androidx.recyclerview.widget.GridLayoutManager
import android.R.attr.spacing


class SpacingDecoration(

) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view!!)
        val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        val gridLayoutManager = parent.layoutManager as GridLayoutManager?
        val spanSize = gridLayoutManager!!.spanSizeLookup.getSpanSize(position).toFloat()
        val totalSpanSize = gridLayoutManager.spanCount.toFloat()

        val n = totalSpanSize / spanSize

        val c = layoutParams.spanIndex / spanSize


        val leftPadding: Float = 2 * ((n - c) / n)
        val rightPadding: Float = 2 * ((c + 1) / n)

        outRect.left = leftPadding.toInt()
        outRect.right = rightPadding.toInt()
    }

}