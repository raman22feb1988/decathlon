package com.example.ipl2021;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    sqliteDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new sqliteDB(MainActivity.this);

        final ArrayList<String> teams = db.getAllTeams();
        if(teams.size() == 0)
        {
            teams.add("Mumbai Indians");
            teams.add("Sunrisers Hyderabad");
            teams.add("Royal Challengers Bangalore");
            teams.add("Rajasthan Royals");
            teams.add("Chennai Super Kings");
            teams.add("Kolkata Knight Riders");
            teams.add("Delhi Capitals");
            teams.add("Punjab Kings");

            boolean q1 = db.insertTeam("Mumbai Indians");
            boolean q2 = db.insertTeam("Sunrisers Hyderabad");
            boolean q3 = db.insertTeam("Royal Challengers Bangalore");
            boolean q4 = db.insertTeam("Rajasthan Royals");
            boolean q5 = db.insertTeam("Chennai Super Kings");
            boolean q6 = db.insertTeam("Kolkata Knight Riders");
            boolean q7 = db.insertTeam("Delhi Capitals");
            boolean q8 = db.insertTeam("Punjab Kings");
        }

        final ListView l1 = findViewById(R.id.listview1);
        final customadapter[] cusadapter = {new customadapter(MainActivity.this, R.layout.teams, teams)};
        l1.setAdapter(cusadapter[0]);

        Button b3 = findViewById(R.id.button1);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                final View yourCustomView = inflater.inflate(R.layout.text, null);

                final EditText e2 = yourCustomView.findViewById(R.id.edittext1);

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("New Team Name")
                        .setView(yourCustomView)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String name = (e2.getText()).toString();

                                boolean q0 = db.insertTeam(name);
                                teams.add(name);

                                cusadapter[0] = new customadapter(MainActivity.this, R.layout.teams, teams);
                                l1.setAdapter(cusadapter[0]);
                            }
                        }).create();
                dialog.show();
            }
        });

        Button b4 = findViewById(R.id.button2);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 1;
                while(i < teams.size())
                {
                    i *= 2;
                }
                if(teams.size() >= 4 && i == teams.size())
                {
                    Intent intent1 = new Intent(MainActivity.this, Knockout.class);
                    intent1.putStringArrayListExtra("teams", teams);
                    startActivity(intent1);

                    finish();
                }
                else if(teams.size() <= 4)
                {
                    Toast.makeText(MainActivity.this, "There must be at least 4 teams", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Number of teams must be a power of 2", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public class customadapter extends ArrayAdapter<String>
    {
        Context con;
        int _resource;
        List<String> lival;

        public customadapter(Context context, int resource, List<String> li) {
            super(context, resource, li);
            // TODO Auto-generated constructor stub
            con = context;
            _resource = resource;
            lival = li;
        }

        @Override
        public View getView(final int position, View v, ViewGroup vg)
        {
            View vi = null;
            LayoutInflater linflate = (LayoutInflater) (MainActivity.this).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = linflate.inflate(_resource, null);

            TextView t1 = vi.findViewById(R.id.textview1);
            final String old_name = lival.get(position);
            t1.setText(old_name);

            Button b1 = vi.findViewById(R.id.button3);
            b1.setOnClickListener(new View.OnClickListener() {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                final View yourCustomView = inflater.inflate(R.layout.text, null);

                EditText e1 = yourCustomView.findViewById(R.id.edittext1);

                @Override
                public void onClick(View view) {
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Edit Team Name")
                            .setView(yourCustomView)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    String new_name = (e1.getText()).toString();

                                    int q9 = db.updateTeam(old_name, new_name);
                                    lival.set(position, new_name);

                                    final ListView l1 = findViewById(R.id.listview1);
                                    customadapter cusadapter = new customadapter(MainActivity.this, R.layout.teams, lival);
                                    l1.setAdapter(cusadapter);
                                }
                            }).create();
                    dialog.show();
                }
            });

            Button b2 = vi.findViewById(R.id.button4);
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.deleteTeam(lival.get(position));
                    lival.remove(position);

                    final ListView l1 = findViewById(R.id.listview1);
                    customadapter cusadapter = new customadapter(MainActivity.this, R.layout.teams, lival);
                    l1.setAdapter(cusadapter);
                }
            });

            return vi;
        }
    }
}