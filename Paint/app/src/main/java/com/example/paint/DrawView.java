package com.example.paint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class DrawView extends View implements View.OnTouchListener {
    int mode;
    int thickness = 5;
    int color = Color.RED;

    List <ModedPointList> points = new ArrayList<>();
    ModedPointList actualPointList = new ModedPointList(new ArrayList<Point>(),mode,5,color);

    int eraserColor = Color.parseColor("#f4f4f4");
    boolean eraseOn = false;

    ModedPointList actualEraserList = new ModedPointList(new ArrayList<Point>(),0,5,eraserColor);

    @SuppressLint("ClickableViewAccessibility")
    public DrawView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        setBackgroundColor(eraserColor);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (ModedPointList pointList : points) {
            Paint paint = new Paint();

            switch (pointList.mode) {
                case 1:
                    paint.setShadowLayer(3,0,1,Color.BLACK);
                    break;
                case 2:
                    paint.setMaskFilter(new BlurMaskFilter(8,BlurMaskFilter.Blur.NORMAL));
            }

            paint.setColor(pointList.color);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(pointList.thickness);

            Path path = new Path();
            path.moveTo(pointList.list.get(0).x, pointList.list.get(0).y);

            for (int i = 1; i < pointList.list.size(); i++) {
                path.lineTo(pointList.list.get(i).x, pointList.list.get(i).y);
            }

            canvas.drawPath(path, paint);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (eraseOn) {
            actualEraserList.list.add(new Point((int) event.getX(), (int) event.getY()));

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                points.add(actualEraserList);
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                actualEraserList = new ModedPointList(new ArrayList<Point>(), 0, thickness, eraserColor);
            }

        } else {
            actualPointList.list.add(new Point((int) event.getX(), (int) event.getY()));

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                points.add(actualPointList);
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                actualPointList = new ModedPointList(new ArrayList<Point>(), mode, thickness, color);
            }
        }
        invalidate();

        return true;
    }

    public void setMode(int mode) {
        this.mode = mode;
        this.actualPointList.mode = mode;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
        this.actualPointList.thickness = thickness;
        this.actualEraserList.thickness = thickness;
    }

    public void setColor(int color) {
        this.color = color;
        this.actualPointList.color = color;
    }
}
