package com.example.paint;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class ThicknessView extends View {
    private int thickness;
    private final Paint paint = new Paint();

    public ThicknessView(Context context) {
        super(context);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setMode(int mode) {
        switch (mode) {
            case 1:
                paint.setShadowLayer(3,0,1, Color.BLACK);
                break;
            case 2:
                paint.setMaskFilter(new BlurMaskFilter(8,BlurMaskFilter.Blur.NORMAL));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();

        path.moveTo(20,getHeight() / 2.0F);
        path.lineTo(getWidth() - 20,getHeight() / 2.0F);

        paint.setStrokeWidth(thickness);
        canvas.drawPath(path,paint);
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
        invalidate();
    }
}
