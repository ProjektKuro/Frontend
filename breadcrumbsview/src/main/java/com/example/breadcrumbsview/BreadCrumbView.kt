package com.example.breadcrumbsview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_item.view.*

class BreadCrumbView : FrameLayout {
    internal var fit = true
    var highlightTextColor = Color.RED
    var textColor = Color.DKGRAY

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
    }

    override fun setActivated(activated: Boolean) {
        super.setActivated(activated)
        if (activated) {
            Log.d("MYTAG", "isActive " + (activated).toString())
            main_text.setTextColor(highlightTextColor)
        } else {
            Log.d("MYTAG", "isActive " + (activated).toString())
            main_text.setTextColor(textColor)
        }
    }

    fun setText(text: String) {
        main_text.text = text
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_item, this, true)
    }

    companion object {
        internal val TAG = "TextFitTextView"
    }
}

