package com.example.breadcrumbsview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.core.view.children
import kotlinx.android.synthetic.main.view_breadcrumb.view.*

class BreadCrumbsView(context: Context?, attrs: AttributeSet?) : HorizontalScrollView(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_breadcrumb, this, true)

    }

    val highLightTextColor = Color.RED
    val textColor = Color.DKGRAY

    val onNavItemClickListener: OnNavItemClickListener? = null

    fun addItems(navItems: List<NavigationItem>) {
        navItems.forEach {
            addView(it, false)
        }
    }

    fun addItem(navItem: NavigationItem, select: Boolean) {
        addView(navItem, select)
    }

    fun removeItemFrom(navItem: NavigationItem) {
        this.removeView(navItem)
    }

    fun goBack() {
        val iterator = this.main_view.children.iterator()
        while (iterator.hasNext()) {
            if (!iterator.hasNext()) {
                removeView(iterator.next())
            } else {
                iterator.next()
            }
        }
    }

    fun removeFrom(item: NavigationItem) {
        val iterator = this.main_view.children.iterator()
        var foundItem = false
        while (iterator.hasNext()) {
            val view = iterator.next()
            if (foundItem) {
                removeView(view)
            }
            if (view.tag == item) {
                foundItem = true
            }
        }
    }

    private fun removeView(item: NavigationItem) {
        val iterator = this.main_view.children.iterator()
        while (iterator.hasNext()) {
            val view = iterator.next()
            if (view.tag == item) {
                removeView(view)
            }
        }
    }

    private fun addView(item: NavigationItem, select: Boolean) {
        val textView = BreadCrumbView(context)

        textView.setText(item.text)
        textView.tag = item
        textView.highlightTextColor = highLightTextColor

        textView.setOnClickListener {
            val iterator = this.main_view.children.iterator()

            while (iterator.hasNext()) {
                val view = iterator.next()
                Log.d("MYTAG", "Clicked " + (view.tag == it.tag).toString())
                view.isActivated = view.tag == it.tag
            }
            onNavItemClickListener?.onClick(navItem = item)
        }
        if (select) {
            val iterator = this.main_view.children.iterator()
            while (iterator.hasNext()) {
                val view = iterator.next()
                view.isActivated = false
            }
            textView.isActivated = true
        }
        (main_view as ViewGroup).addView(textView)
    }

    companion object {
        interface OnNavItemClickListener {
            fun onClick(navItem: NavigationItem)
        }
    }
}