package com.example.ipl2021;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Finals extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finals);

        final Button b1 = findViewById(R.id.button7);
        final Button b2 = findViewById(R.id.button8);

        b2.setEnabled(false);

        Bundle extras = getIntent().getExtras();
        final ArrayList<String> winners = extras.getStringArrayList("winners");
        final ArrayList<String> losers = extras.getStringArrayList("losers");

        TextView t1 = findViewById(R.id.textview5);
        TextView t2 = findViewById(R.id.textview8);
        final TextView t3 = findViewById(R.id.textview6);
        final TextView t4 = findViewById(R.id.textview9);

        t1.setText(winners.get(0) + " vs " + winners.get(1));
        t2.setText(losers.get(0) + " vs " + losers.get(1));

        final ArrayList<String> toppers = new ArrayList<>();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random r1 = new Random();
                int winner1 = r1.nextInt(2);
                int loser1 = 1 - winner1;

                Random r2 = new Random();
                int winner2 = r2.nextInt(2);
                int loser2 = 1 - winner2;

                toppers.add(winners.get(winner1));
                toppers.add(winners.get(loser1));
                toppers.add(losers.get(winner2));
                toppers.add(losers.get(loser2));

                t3.setText(toppers.get(0) + " won");
                t4.setText(toppers.get(2) + " won");

                b1.setEnabled(false);
                b2.setEnabled(true);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(Finals.this, Results.class);
                intent4.putStringArrayListExtra("toppers", toppers);
                startActivity(intent4);

                finish();
            }
        });
    }
}