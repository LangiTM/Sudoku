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
    public static boolean gameStarted = false;
    Intent gameIntent, helpIntent, settingsIntent;
    Button startButton, newButton, helpButton, settingButton, aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        gameIntent = new Intent(MenuActivity.this, GameActivity.class);
        helpIntent = new Intent(MenuActivity.this, HelpActivity.class);
        settingsIntent = new Intent(MenuActivity.this, SettingsActivity.class);

        startButton = (Button) findViewById(R.id.playButton);
        newButton = (Button) findViewById(R.id.restartButton);
        helpButton = (Button) findViewById(R.id.helpButton);
        settingButton = (Button) findViewById(R.id.settingButton);
        aboutButton = (Button) findViewById(R.id.aboutButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!gameStarted) {
                    startActivity(gameIntent);
                } else {
                    generateNew();
                    startActivity(gameIntent);
                }
            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                generateNew();
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(helpIntent);
            }
        });

        settingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(settingsIntent);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(MenuActivity.this).create();
                alertDialog.setTitle("About Sudoku");
                alertDialog.setMessage("Tyler Langile\nB00615482\nCSCI4176");
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

    public void generateNew() {

    }
}
