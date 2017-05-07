package com.example.langi.sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HelpActivity extends AppCompatActivity {
    Button menuButton;
    Intent menuIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        menuIntent = new Intent(HelpActivity.this, MenuActivity.class);
        menuButton = (Button)findViewById(R.id.helpMenuButton);

        menuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(menuIntent);
            }
        });
    }

}
