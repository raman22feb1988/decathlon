package com.example.ipl2021;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Knockout extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.knockout);

        final Button b1 = findViewById(R.id.button5);
        final Button b2 = findViewById(R.id.button6);

        b2.setEnabled(false);

        Bundle extras = getIntent().getExtras();
        final ArrayList<String> teams = extras.getStringArrayList("teams");

        ArrayList<String> select = new ArrayList<>();
        for(String item : teams)
        {
            select.add(item);
        }

        final ArrayList<String> pairs = new ArrayList<>();
        for(int i = 0; i < teams.size(); i++)
        {
            Random rand = new Random();
            int random = rand.nextInt(select.size());

            pairs.add(select.get(random));
            select.remove(random);
        }

        final ArrayList<String> winners = new ArrayList<>();
        final ArrayList<String> losers = new ArrayList<>();
        for(int j = 0; j < (teams.size() / 2); j++)
        {
            winners.add("");
            losers.add("");
        }

        final ListView l1 = findViewById(R.id.listview2);
        final customadapter[] cusadapter = {new customadapter(Knockout.this, R.layout.match, pairs, winners)};
        l1.setAdapter(cusadapter[0]);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int k = 0; k < (teams.size() / 2); k++)
                {
                    Random r = new Random();
                    int winner = r.nextInt(2);
                    int loser = 1 - winner;

                    winners.set(k, pairs.get((k * 2) + winner));
                    losers.set(k, pairs.get((k * 2) + loser));

                    cusadapter[0] = new customadapter(Knockout.this, R.layout.match, pairs, winners);
                    l1.setAdapter(cusadapter[0]);

                    b1.setEnabled(false);
                    b2.setEnabled(true);
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(winners.size() >= 4)
                {
                    Intent intent2 = new Intent(Knockout.this, Knockout.class);
                    intent2.putStringArrayListExtra("teams", winners);
                    startActivity(intent2);

                    finish();
                }
                else {
                    Intent intent3 = new Intent(Knockout.this, Finals.class);
                    intent3.putStringArrayListExtra("winners", winners);
                    intent3.putStringArrayListExtra("losers", losers);
                    startActivity(intent3);

                    finish();
                }
            }
        });
    }

    public class customadapter extends ArrayAdapter<String>
    {
        Context con;
        int _resource;
        List<String> lival1;
        List<String> lival2;

        public customadapter(Context context, int resource, List<String> li1, List<String> li2) {
            super(context, resource, li2);
            // TODO Auto-generated constructor stub
            con = context;
            _resource = resource;
            lival1 = li1;
            lival2 = li2;
        }

        @Override
        public View getView(final int position, View v, ViewGroup vg)
        {
            View vi = null;
            LayoutInflater linflate = (LayoutInflater) (Knockout.this).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = linflate.inflate(_resource, null);

            TextView t1 = vi.findViewById(R.id.textview2);
            TextView t2 = vi.findViewById(R.id.textview3);

            final String team1 = lival1.get(position * 2);
            final String team2 = lival1.get((position * 2) + 1);

            String result = lival2.get(position);
            if(result.length() > 0)
            {
                result += " won";
            }

            t1.setText(team1 + " vs " + team2);
            t2.setText(result);

            return vi;
        }
    }
}