package com.jacobgreenland.finalproject;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;

/**
 * Created by Jacob on 11/07/16.
 */
public class CustomScaleAnimation extends ScaleAnimation
{
    private final View view;
    private final boolean showing;
    private final ViewGroup.MarginLayoutParams layoutParams;
    private final int marginBottomFromY;
    private final int marginBottomToY;

    public CustomScaleAnimation(View view, boolean showing)
    {
        this(view, showing, 300);
    }

    public CustomScaleAnimation(View view, boolean showing, long duration)
    {
        super(1.0f, 1.0f, showing ? 0.0f : 1.0f, showing ? 1.0f : 0.0f);
        setDuration(duration);
        setInterpolator(view.getContext(), android.R.interpolator.accelerate_decelerate);

        this.view = view;
        this.showing = showing;
        this.layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

        if(showing)
        {
            marginBottomFromY = -1 * getHeight(view);
            marginBottomToY = 0;
            updateMargin(0.0f);
            view.setVisibility(View.VISIBLE);
        }
        else
        {
            marginBottomFromY = layoutParams.bottomMargin;
            marginBottomToY = -1 * (layoutParams.bottomMargin + getHeight(view));
        }
    }

    private int getHeight(View view)
    {
        int h = view.getHeight();
        if(h == 0)
        {
            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            h = view.getMeasuredHeight();
        }
        return h;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t)
    {
        super.applyTransformation(interpolatedTime, t);
        updateMargin(interpolatedTime);
    }

    private void updateMargin(float interpolatedTime)
    {
        if(interpolatedTime < 1.0f)
        {
            int marginDelta = Math.round((marginBottomToY - marginBottomFromY) * interpolatedTime);
            layoutParams.setMargins(layoutParams.leftMargin,
                    layoutParams.topMargin,
                    layoutParams.rightMargin,
                    marginBottomFromY + marginDelta);
            view.getParent().requestLayout();
        }
        else if(!showing)
        {
            view.setVisibility(View.GONE);
        }
    }
}