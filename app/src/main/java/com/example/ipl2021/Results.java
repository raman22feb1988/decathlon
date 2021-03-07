package com.example.ipl2021;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Results extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        Bundle extras = getIntent().getExtras();
        final ArrayList<String> toppers = extras.getStringArrayList("toppers");

        TextView t1 = findViewById(R.id.textview11);
        TextView t2 = findViewById(R.id.textview13);
        TextView t3 = findViewById(R.id.textview15);
        TextView t4 = findViewById(R.id.textview17);

        t1.setText(toppers.get(0));
        t2.setText(toppers.get(1));
        t3.setText(toppers.get(2));
        t4.setText(toppers.get(3));

        Button b1 = findViewById(R.id.button9);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(Results.this, MainActivity.class);
                startActivity(intent5);

                finish();
            }
        });
    }
}