package com.example.dice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

@SuppressLint("ViewConstructor")
public class Dice extends View {
    int value = 6;

    private final int size;
    private final float pointRadius;

    Paint paint = new Paint();

    public Dice(Context context, int size) {
        super(context);
        this.size = size;
        this.pointRadius = size / 12F;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(0,0,size,size,20,20,paint);
        paint.setColor(Color.GREEN);

        switch (value) {
            case 1:
                draw1(canvas);
                break;
            case 2:
                draw2(canvas);
                break;
            case 3:
                draw1(canvas);
                draw2(canvas);
                break;
            case 4:
                draw4(canvas);
                break;
            case 5:
                draw1(canvas);
                draw4(canvas);
                break;
            case 6:
                draw4(canvas);
                canvas.drawCircle(size / 4.8F, size / 2F, pointRadius, paint);
                canvas.drawCircle(size - size / 4.8F,size / 2F, pointRadius, paint);
        }
    }

    private void draw1 (final Canvas canvas) {
        canvas.drawCircle(size / 2F, size / 2F, pointRadius, paint);
    }

    private void draw2 (final Canvas canvas) {
        canvas.drawCircle(size/4.8F, size - size / 4F, pointRadius, paint);
        canvas.drawCircle(size - size/4.8F,size / 4F, pointRadius, paint);
    }

    private void draw4 (final Canvas canvas) {
        canvas.drawCircle(size / 4.8F, size / 4F, pointRadius, paint);
        canvas.drawCircle(size - size / 4.8F,size / 4F, pointRadius, paint);
        canvas.drawCircle(size / 4.8F, size - size / 4F, pointRadius, paint);
        canvas.drawCircle(size - size / 4.8F,size - size / 4F, pointRadius, paint);
    }

    public void setValue(int value) {
        this.value = value;
        invalidate();
    }
}
