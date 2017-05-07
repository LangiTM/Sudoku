package com.example.langi.sudoku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.R.color.holo_blue_dark;
import static android.R.color.holo_green_dark;
import static android.R.color.holo_red_dark;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    Button menuButton, modeButton, restartButton;
    Intent menuIntent;
    Button[] numberSelector;
    Button[][] gameBoardGrid;
    Resources res;
    int selectedNumber;
    int filled;
    int seed;
    boolean writeMode;
    Random r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        menuIntent = new Intent(GameActivity.this, MenuActivity.class);

        menuButton = (Button)findViewById(R.id.menuButton);
        modeButton = (Button)findViewById(R.id.modeButton);
        restartButton = (Button)findViewById(R.id.restartButton);

        gameBoardGrid = new Button[9][9];
        numberSelector = new Button[9];

        writeMode = true;

        selectedNumber = 1;

        menuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(menuIntent);
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                resetPuzzle(seed);
            }
        });

        modeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //In editing mode, you can place numbers "temporarily". They still need to be correct, but don't count towards filled
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
            numberSelector[j] = (Button)findViewById(elementID);
            numberSelector[j].setOnClickListener(this);
        }
        numberSelector[0].setBackground(getDrawable(holo_green_dark));

        res = getResources();

        //set clicklisteners on gameBoard buttons
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String elementName = "button"+(i+1)+""+(j+1);
                int elementID = getResources().getIdentifier(elementName, "id", getPackageName());
                gameBoardGrid[i][j] = (Button)findViewById(elementID);
                gameBoardGrid[i][j].setOnClickListener(this);
                gameBoardGrid[i][j].setText("");
            }
        }

        r = new Random();

        seed = r.nextInt(4 - 1) + 1;
        buildPuzzle(seed);

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
            default:
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        String elementName = "button" + (i + 1) + "" + (j + 1);
                        int elementID = getResources().getIdentifier(elementName, "id", getPackageName());
                        if (view.getId() == elementID && gameBoardGrid[i][j].getCurrentTextColor() == Color.RED) {
                            gameBoardGrid[i][j].setText("");
                            gameBoardGrid[i][j].setTextColor(Color.WHITE);
                            //Always allow edit mode cells to be changed
                        }
                        if (view.getId() == elementID && numberLegal(i,j)) {
                            if (writeMode) {
                                if (gameBoardGrid[i][j].getText().equals("")) {
                                    filled++;
                                    //only increase the filled counter if adding a legal number, in write mode
                                }
                                gameBoardGrid[i][j].setText("" + selectedNumber);
                                gameBoardGrid[i][j].setTextColor(Color.WHITE);
                            } else {
                                gameBoardGrid[i][j].setText("" + selectedNumber);
                                gameBoardGrid[i][j].setTextColor(Color.RED);
                                //edit mode cells are just for personal reference, so don't increase filled
                            }
                            if (filled == 81) {
                                winGame();
                                //if all 81 cells have been filled, call win game condition
                                //all 81 cells can only be filled if they are all filled correctly
                            }
                            else if (filled > 70) {
                                checkIfGameLost();
                            }
                        }
                    }
                }
        }
    }

    public void clearNumberSelector() {
        for (int i = 0; i < 9; i++) {
            numberSelector[i].setBackground(getDrawable(holo_blue_dark));
            //set all number selection cells as blue
        }
    }

    public boolean numberLegal(int i, int j) {
        String sNum;
        if (gameBoardGrid[i][j].getCurrentTextColor() == Color.YELLOW) {
            return false;
            //yellow numbers are the game-provide board, and are always correct & so cannot be changed
        }
        for (int m = 0; m < 9; m++) {
            sNum = Integer.toString(selectedNumber);
            if (gameBoardGrid[i][m].getText().equals(sNum) || gameBoardGrid[m][j].getText().equals(sNum)) {
                return false;
                //if the select number already appear in the desired cell's column or row, return number illegal
            }
        }
        if (i < 3) {
            i = 0;
        }
        else if (i < 6) {
            i = 3;
        }
        else if (i < 9) {
            i = 6;
        }
        if (j < 3) {
            j = 0;
        }
        else if (j < 6) {
            j = 3;
        }
        else if (j < 9) {
            j = 6;
        }
        for (int k = i; k < i+3; k++) {
            for (int t = j; t < j+3; t++) {
                sNum = Integer.toString(selectedNumber);
                if (gameBoardGrid[k][t].getText().equals(sNum)) {
                    return false;
                    //check the other 3x3 block cells for the selected number, return illegal if found
                }
            }
        }
        return true;
        //return true if none of the other conditions have returned false
    }

    public void resetPuzzle(int seed) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gameBoardGrid[i][j].setText("");
                gameBoardGrid[i][j].setTextColor(Color.WHITE);
                //clear board
            }
        }
        buildPuzzle(seed);
        //rebuild puzzle
    }

    public void buildPuzzle(int seed) {
        filled = 0;
        //no filled in cells yet
        switch (seed) {
            case 1:
                buildPuzzleHelper(1, 7, 9);
                buildPuzzleHelper(1, 8, 1);
                buildPuzzleHelper(2, 5, 7);
                buildPuzzleHelper(2, 7, 3);
                buildPuzzleHelper(3, 1, 5);
                buildPuzzleHelper(3, 4, 6);
                buildPuzzleHelper(3, 5, 9);
                buildPuzzleHelper(3, 6, 8);
                buildPuzzleHelper(4, 4, 1);
                buildPuzzleHelper(4, 7, 8);
                buildPuzzleHelper(4, 8, 4);
                buildPuzzleHelper(4, 9, 9);
                buildPuzzleHelper(5, 1, 6);
                buildPuzzleHelper(5, 2, 5);
                buildPuzzleHelper(5, 8, 3);
                buildPuzzleHelper(5, 9, 1);
                buildPuzzleHelper(6, 1, 9);
                buildPuzzleHelper(6, 2, 1);
                buildPuzzleHelper(6, 3, 8);
                buildPuzzleHelper(6, 6, 2);
                buildPuzzleHelper(7, 4, 8);
                buildPuzzleHelper(7, 5, 1);
                buildPuzzleHelper(7, 6, 9);
                buildPuzzleHelper(7, 9, 3);
                buildPuzzleHelper(8, 3, 9);
                buildPuzzleHelper(8, 5, 3);
                buildPuzzleHelper(9, 2, 2);
                buildPuzzleHelper(9, 3, 5);
                break;
            case 2:
                buildPuzzleHelper(1, 2, 8);
                buildPuzzleHelper(1, 6, 6);
                buildPuzzleHelper(1, 8, 3);
                buildPuzzleHelper(1, 9, 9);
                buildPuzzleHelper(2, 3, 6);
                buildPuzzleHelper(2, 5, 3);
                buildPuzzleHelper(3, 3, 3);
                buildPuzzleHelper(3, 5, 2);
                buildPuzzleHelper(3, 8, 8);
                buildPuzzleHelper(4, 4, 2);
                buildPuzzleHelper(4, 8, 6);
                buildPuzzleHelper(4, 9, 4);
                buildPuzzleHelper(5, 4, 7);
                buildPuzzleHelper(5, 6, 5);
                buildPuzzleHelper(6, 1, 3);
                buildPuzzleHelper(6, 2, 4);
                buildPuzzleHelper(6, 6, 8);
                buildPuzzleHelper(7, 2, 2);
                buildPuzzleHelper(7, 5, 7);
                buildPuzzleHelper(7, 7, 5);
                buildPuzzleHelper(8, 5, 5);
                buildPuzzleHelper(8, 7, 7);
                buildPuzzleHelper(9, 1, 5);
                buildPuzzleHelper(9, 2, 1);
                buildPuzzleHelper(9, 4, 4);
                buildPuzzleHelper(9, 8, 9);
                break;
            case 3:
                buildPuzzleHelper(1, 3, 6);
                buildPuzzleHelper(1, 4, 9);
                buildPuzzleHelper(1, 7, 1);
                buildPuzzleHelper(2, 1, 9);
                buildPuzzleHelper(2, 3, 4);
                buildPuzzleHelper(2, 5, 6);
                buildPuzzleHelper(2, 8, 2);
                buildPuzzleHelper(3, 5, 5);
                buildPuzzleHelper(3, 8, 9);
                buildPuzzleHelper(4, 1, 2);
                buildPuzzleHelper(4, 4, 3);
                buildPuzzleHelper(4, 9, 1);
                buildPuzzleHelper(5, 5, 8);
                buildPuzzleHelper(6, 1, 6);
                buildPuzzleHelper(6, 6, 7);
                buildPuzzleHelper(6, 9, 5);
                buildPuzzleHelper(7, 2, 9);
                buildPuzzleHelper(7, 5, 3);
                buildPuzzleHelper(8, 2, 1);
                buildPuzzleHelper(8, 5, 9);
                buildPuzzleHelper(8, 7, 3);
                buildPuzzleHelper(8, 9, 8);
                buildPuzzleHelper(9, 3, 7);
                buildPuzzleHelper(9, 6, 5);
                buildPuzzleHelper(9, 7, 9);
        }
    }

    public void buildPuzzleHelper(int i, int j, int n) {
        gameBoardGrid[i-1][j-1].setText(Integer.toString(n));
        gameBoardGrid[i-1][j-1].setTextColor(Color.YELLOW);
        filled++;
        //set the desired number in the desired cell
        //set the text colour as yellow to indicate it is a game-provided cell
    }

    public void winGame() {
        AlertDialog alertDialog = new AlertDialog.Builder(GameActivity.this).create();
        alertDialog.setTitle("Congratulations!");
        alertDialog.setMessage("All cells have been filled,\nYou win!\nReturn to the menu to start a new game.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void checkIfGameLost() {
        int hold = selectedNumber;
        int movesLeft = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (gameBoardGrid[i][j].getText().equals("") || gameBoardGrid[i][j].getCurrentTextColor() == Color.RED) {
                    //for each remaining cell that hasn't been filled or is filled with an edit mode
                    for (int k = 1; k < 10; k++) {
                        //check to see if that cell has any legal numbers that can be placed there
                        selectedNumber = k;
                        if (numberLegal(i,j)) {
                            movesLeft++;
                        }
                    }
                }

            }
        }
        selectedNumber = hold;
        numberSelector[hold-1].setBackground(getDrawable(holo_green_dark));
        if (movesLeft == 0) {
            //if there are no moves that can be done, call lose condition
            loseGame();
        }
    }

    public void loseGame() {
        AlertDialog alertDialog = new AlertDialog.Builder(GameActivity.this).create();
        alertDialog.setTitle("Oh no!");
        alertDialog.setMessage("You have no more valid moves.\nYou can try to fix incorrect cells,\nOr return to the menu\n To start a new game.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
