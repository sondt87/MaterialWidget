package com.github.android.material.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by mountain on 10/23/13.
 * Material Design Widget
 * This layout help to divide child to ratio: 16:9,4:3...
 * Help you devide childrent view to ratio which describle in material design:
 * http://www.google.com/design/spec/layout/metrics-and-keylines.html#metrics-and-keylines-ratio-keylines
 * 
 *
 *
 */
public class RatioLayout extends ViewGroup {

    public RatioLayout(Context context) {
        super(context);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(),attrs);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int count = getChildCount();
        int top = 0;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            RatioLayout.LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            int height = getHeight(layoutParams.ratio,width);

            int childHeight = height - top;

            child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));

            top = height;
        }

        setMeasuredDimension(width, top);

    }

    private int getHeight(int ratio, int width) {
        float ratioValue = 0;

        switch (ratio){
            case LayoutParams.RATIO_16_9:
                ratioValue = 16f/9f;
                break;
            case LayoutParams.RATIO_4_3:
                ratioValue = 4f/3f;
                break;
            case LayoutParams.RATIO_3_2:
                ratioValue = 3f/2f;
                break;
            case LayoutParams.RATIO_1_1:
                ratioValue = 1f;
                break;
            case LayoutParams.RATIO_3_4:
                ratioValue = 3f/4f;
                break;
            case LayoutParams.RATIO_2_3:
                ratioValue = 2f/3f;
                break;
        }
        return (int) (width/ratioValue);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int top = 0;

        final int width = r - l;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            RatioLayout.LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            int height = getHeight(layoutParams.ratio, width);

            child.layout(0,top,width,height);

            top = height;
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }


    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }


    public static class LayoutParams extends ViewGroup.LayoutParams{
        public static final int RATIO_NONE = 0;
        public static final int RATIO_16_9 = 1;
        public static final int RATIO_3_2 = 2;
        public static final int RATIO_4_3 = 3;
        public static final int RATIO_1_1 = 4;
        public static final int RATIO_3_4 = 5;
        public static final int RATIO_2_3 = 6;


        private int ratio = RATIO_NONE;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs,
                    R.styleable.RatioLayout);
            ratio = a.getInt(R.styleable.RatioLayout_ratio,RATIO_NONE);

            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(LinearLayout.LayoutParams source) {
            super(source);
        }
    }
}
