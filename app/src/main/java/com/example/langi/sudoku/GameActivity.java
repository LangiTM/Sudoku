package com.example.langi.sudoku;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.R.color.holo_blue_dark;
import static android.R.color.holo_green_dark;
import static android.R.color.holo_red_dark;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    Button menuButton, modeButton;
    Intent menuIntent;
    Button[] numberSelector;
    Button[][] gameBoardGrid;
    Resources res;
    int selectedNumber;
    boolean writeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        menuIntent = new Intent(GameActivity.this, MenuActivity.class);

        menuButton = (Button)findViewById(R.id.menuButton);
        modeButton = (Button)findViewById(R.id.modeButton);

        gameBoardGrid = new Button[9][9];
        numberSelector = new Button[9];

        writeMode = true;

        menuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(menuIntent);
            }
        });

        modeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (writeMode) {
                    writeMode = false;
                    modeButton.setText("Editing");
                    modeButton.setBackground(getDrawable(holo_red_dark));
                } else {
                    writeMode = true;
                    modeButton.setText("Writing");
                    modeButton.setBackground(getDrawable(holo_green_dark));
                }
            }
        });

        for (int j = 0; j < 9; j++) {
            String elementName = "buttonS"+""+(j+1);
            int elementID = getResources().getIdentifier(elementName, "id", getPackageName());
            System.out.println((j+1)+" "+elementID+" "+elementName);
            numberSelector[j] = (Button)findViewById(elementID);
            numberSelector[j].setOnClickListener(this);
        }

        res = getResources();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String elementName = "button"+(i+1)+""+(j+1);
                int elementID = getResources().getIdentifier(elementName, "id", getPackageName());
                gameBoardGrid[i][j] = (Button)findViewById(elementID);
                gameBoardGrid[i][j].setOnClickListener(this);
                gameBoardGrid[i][j].setText(""+((i*2%9)+1));
            }
        }

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonS1:
                clearNumberSelector();
                numberSelector[0].setBackground(getDrawable(holo_green_dark));
                selectedNumber = 1;
                break;
            case R.id.buttonS2:
                clearNumberSelector();
                numberSelector[1].setBackground(getDrawable(holo_green_dark));
                selectedNumber = 2;
                break;
            case R.id.buttonS3:
                clearNumberSelector();
                numberSelector[2].setBackground(getDrawable(holo_green_dark));
                selectedNumber = 3;
                break;
            case R.id.buttonS4:
                clearNumberSelector();
                numberSelector[3].setBackground(getDrawable(holo_green_dark));
                selectedNumber = 4;
                break;
            case R.id.buttonS5:
                clearNumberSelector();
                numberSelector[4].setBackground(getDrawable(holo_green_dark));
                selectedNumber = 5;
                break;
            case R.id.buttonS6:
                clearNumberSelector();
                numberSelector[5].setBackground(getDrawable(holo_green_dark));
                selectedNumber = 6;
                break;
            case R.id.buttonS7:
                clearNumberSelector();
                numberSelector[6].setBackground(getDrawable(holo_green_dark));
                selectedNumber = 7;
                break;
            case R.id.buttonS8:
                clearNumberSelector();
                numberSelector[7].setBackground(getDrawable(holo_green_dark));
                selectedNumber = 8;
                break;
            case R.id.buttonS9:
                clearNumberSelector();
                numberSelector[8].setBackground(getDrawable(holo_green_dark));
                selectedNumber = 9;
                break;

        }
    }

    public void clearNumberSelector() {
        for (int i = 0; i < 9; i++) {
            numberSelector[i].setBackground(getDrawable(holo_blue_dark));
        }
    }

}
