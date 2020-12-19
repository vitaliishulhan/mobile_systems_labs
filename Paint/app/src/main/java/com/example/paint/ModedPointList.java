package com.example.paint;

import android.graphics.Point;
import java.util.List;

public class ModedPointList {
    int mode;
    final List<Point> list;
    int thickness;
    int color;

    public ModedPointList(List<Point> list, int mode, int thickness, int color) {
        this.list = list;
        this.mode = mode;
        this.thickness = thickness;
        this.color = color;
    }
}
