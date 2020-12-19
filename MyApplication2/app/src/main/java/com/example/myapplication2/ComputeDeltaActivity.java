package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ComputeDeltaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute_delta);
        ((TextView)findViewById(R.id.X2TextView)).setText(Html.fromHtml("x<sup>2</sup> + "));
        ((TextView)findViewById(R.id.XTextView)).setText("x + ");
        ((TextView)findViewById(R.id.ZeroTextView)).setText(" = 0");
        ((Button)findViewById(R.id.ComputeBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText aEditText = findViewById(R.id.AEditText);
                EditText bEditText = findViewById(R.id.BEditText);
                EditText cEditText = findViewById(R.id.CEditText);

                if (aEditText.getText().toString().equals("")) {
                    Toast errorToast = Toast.makeText(ComputeDeltaActivity.this, "Error, please write the value of a parameter", Toast.LENGTH_SHORT);
                    errorToast.show();
                    return;
                }

                if (bEditText.getText().toString().equals("")) {
                    Toast errorToast = Toast.makeText(ComputeDeltaActivity.this, "Error, please write the value of b parameter", Toast.LENGTH_SHORT);
                    errorToast.show();
                    return;
                }

                if (cEditText.getText().toString().equals("")) {
                    Toast errorToast = Toast.makeText(ComputeDeltaActivity.this, "Error, please write the value of c parameter", Toast.LENGTH_SHORT);
                    errorToast.show();
                    return;
                }

                double  a = Double.parseDouble(aEditText.getText().toString()),
                        b = Double.parseDouble(bEditText.getText().toString()),
                        c = Double.parseDouble(cEditText.getText().toString());

                if (a == 0) {

                    if (b == 0) {
                        if (c == 0) {
                            ((TextView)findViewById(R.id.resultView)).setText("The equation has infinite number of roots");
                        } else {
                            ((TextView)findViewById(R.id.resultView)).setText("The equation has no roots");
                        }
                        return;
                    }

                    ((TextView)findViewById(R.id.resultView)).setText("The equation has one root. It's x = " + (-c / b));
                    return;
                }

                if (b == 0) {

                    if (c == 0) {
                        ((TextView)findViewById(R.id.resultView)).setText("The equation has one root. This is 0");
                        return;
                    }

                    double res = -c/a;

                    if (res < 0) {
                        ((TextView)findViewById(R.id.resultView)).setText("The equation has no roots");
                    } else {
                        res = Math.round(Math.sqrt(res) * 100.0) / 100.0;
                        ((TextView)findViewById(R.id.resultView)).setText("The equation has two roots. The first root is " + res + " and the second one is " + (-res));
                    }
                    return;
                }

                double d = Math.pow(b,2)-4*a*c;

                if (d < 0) {
                    ((TextView)findViewById(R.id.resultView)).setText("The equation has no roots");
                    return;
                }

                if (d == 0) {
                    ((TextView)findViewById(R.id.resultView)).setText("The equation has one root. This is " + (-b/(2*a)));
                }

                d = Math.sqrt(d);

                double  res1 = Math.round(((-b + d) / (2*a)) * 100.0) / 100.0,
                        res2 = Math.round(((-b - d) / (2*a)) * 100.0) / 100.0;

                ((TextView)findViewById(R.id.resultView)).setText("The equation has two roots. The first root is " + res1 + " and the second one is " + res2);

            }
        });
    }
}