package com.example.paint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class DrawActivity extends AppCompatActivity {

    DrawView drawView;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(FLAG_FULLSCREEN,FLAG_FULLSCREEN);
        drawView = new DrawView(this);
        setContentView(drawView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.normalModeItem:
                drawView.setMode(0);
                drawView.invalidate();
                return true;
            case R.id.embossModeItem:
                drawView.setMode(1);
                drawView.invalidate();
                return true;
            case R.id.blurModeItem:
                drawView.setMode(2);
                drawView.invalidate();
                return true;
            case R.id.clearAllItem:
                drawView.points.clear();
                drawView.invalidate();
                return true;
            case R.id.changeColorItem:
                changeColor();
                return true;
            case R.id.thicknessItem:
                changeThickness();
                return true;
            case R.id.eraserItem:
                drawView.eraseOn = !drawView.eraseOn;
                item.setTitle(drawView.eraseOn ? "Eraser Off" : "Eraser On");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void changeThickness() {
        final Dialog d = new Dialog(DrawActivity.this);

        final ThicknessView thicknessView = new ThicknessView(d.getContext());
        thicknessView.setColor(drawView.color);
        thicknessView.setMode(drawView.mode);
        thicknessView.setThickness(drawView.thickness);

        d.setContentView(R.layout.thickness_dialog);
        final float scale = d.getContext().getResources().getDisplayMetrics().density;

        d.addContentView(thicknessView, new ViewGroup.LayoutParams((int)(200*scale + 0.5F),(int)(100*scale + 0.5F)));

        final TextView thicknessTextView = d.findViewById(R.id.thicknessTextView);
        thicknessTextView.setText(String.valueOf(drawView.thickness));

        final SeekBar thicknessSeekBar = d.findViewById(R.id.thicknessSeekBar);

        thicknessSeekBar.setProgress(drawView.thickness);

        thicknessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                thicknessView.setThickness(progress);
                thicknessTextView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {/*not used*/}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {/*not used*/}
        });

        Button thicknessSetBtn = d.findViewById(R.id.thickness_set);
        Button thicknessCancelBtn = d.findViewById(R.id.thickness_cancel);

        thicknessSetBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                menu.findItem(R.id.thicknessItem).setTitle("Thickness (" + thicknessSeekBar.getProgress() + ")");
                drawView.setThickness(thicknessSeekBar.getProgress());
                d.dismiss();
            }
        });
        thicknessCancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }

    public void changeColor() {
        final Dialog d = new Dialog(DrawActivity.this);
        d.setContentView(R.layout.color_dialog);

        final View colorView = d.findViewById(R.id.colorView);

        final SeekBar redBar = d.findViewById(R.id.redBar);
        final SeekBar blueBar = d.findViewById(R.id.blueBar);
        final SeekBar greenBar = d.findViewById(R.id.greenBar);

        final TextView redTextView = d.findViewById(R.id.redTextView);
        final TextView greenTextView = d.findViewById(R.id.greenTextView);
        final TextView blueTextView = d.findViewById(R.id.blueTextView);

        redBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                redTextView.setText(String.valueOf(progress));
                colorView.setBackgroundColor(convertColor(  redBar.getProgress(),
                                                            greenBar.getProgress(),
                                                            blueBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {/*not used*/}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {/*not used*/}
        });

        greenBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                greenTextView.setText(String.valueOf(progress));
                colorView.setBackgroundColor(convertColor(  redBar.getProgress(),
                                                            greenBar.getProgress(),
                                                            blueBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {/*not used*/}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {/*not used*/}
        });

        blueBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blueTextView.setText(String.valueOf(progress));
                colorView.setBackgroundColor(convertColor(  redBar.getProgress(),
                                                            greenBar.getProgress(),
                                                            blueBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {/*not used*/}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {/*not used*/}
        });

        Button colorSetBtn = d.findViewById(R.id.color_set);
        Button colorCancelBtn = d.findViewById(R.id.color_cancel);

        colorSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.setColor(convertColor( redBar.getProgress(),
                                                greenBar.getProgress(),
                                                blueBar.getProgress()));
                d.dismiss();
            }
        });

        colorCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        d.show();
    }

    private int convertColor(int r, int g, int b) {
        String hex_r = Integer.toHexString(r);
        String hex_g = Integer.toHexString(g);
        String hex_b = Integer.toHexString(b);

        if(hex_r.length() == 1)
            hex_r = "0" + hex_r;
        if(hex_g.length() == 1)
            hex_g = "0" + hex_g;
        if(hex_b.length() == 1)
            hex_b = "0" + hex_b;

        return Color.parseColor("#" + hex_r + hex_g + hex_b);
    }
}