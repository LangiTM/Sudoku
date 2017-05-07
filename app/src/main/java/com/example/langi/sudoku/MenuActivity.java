package com.example.langi.sudoku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class MenuActivity extends AppCompatActivity {
    Intent gameIntent, helpIntent, settingsIntent;
    Button startButton, helpButton, aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        gameIntent = new Intent(MenuActivity.this, GameActivity.class);
        helpIntent = new Intent(MenuActivity.this, HelpActivity.class);
        settingsIntent = new Intent(MenuActivity.this, SettingsActivity.class);

        startButton = (Button) findViewById(R.id.playButton);
        helpButton = (Button) findViewById(R.id.helpButton);
        aboutButton = (Button) findViewById(R.id.aboutButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(gameIntent);
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(helpIntent);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(MenuActivity.this).create();
                alertDialog.setTitle("About Sudoku");
                alertDialog.setMessage("Tyler Langile");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
    }

}
