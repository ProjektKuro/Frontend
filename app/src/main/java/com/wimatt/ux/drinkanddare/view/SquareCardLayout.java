package com.wimatt.ux.drinkanddare.view;

import android.content.Context;
import android.util.AttributeSet;
import androidx.cardview.widget.CardView;

public class SquareCardLayout extends CardView {
    public SquareCardLayout(Context context) {
        super(context);
    }

    public SquareCardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareCardLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //noinspection SuspiciousNameCombination
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
