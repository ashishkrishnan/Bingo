package org.askdn.bingo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class BingoStartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener{


    // Key = Value of the selected grid Item : Value = position of the selected grid Item
    LinkedHashMap<Integer, Integer> lastComSelect = new LinkedHashMap<>();

    int selectedGrid[] = new int[25];
    BingoStartActivity object;

    BingoNumber temp;
    HashMap<Integer,Integer> leftoutGrids = new HashMap<>();
    TextView title;
    int userCount =0;
    int compCount =0;
    public static boolean isPlaying = false;
    GridView mGridView;
    Button play_button, randomise_button, reset;
    BingoAdapter mBingoAdapter;
    public static final int NUM_COL = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bingo_start);

        intialize();
        mBingoAdapter = new BingoAdapter(this,populate());
        mGridView.setAdapter(mBingoAdapter);
        mGridView.setOnItemClickListener(this);
        play_button.setOnClickListener(this);
        randomise_button.setOnClickListener(this);
        reset.setOnClickListener(this);

    }

    //Helper function to get a reference to the XML Views
    private void intialize() {

        //Intializing a set of avaliable grids
        for(int i=0;i<25;i++) {
            leftoutGrids.put(i,i);
        }
        object = new BingoStartActivity();
        title = (TextView) findViewById(R.id.title);
        mGridView = (GridView) findViewById(R.id.grid);
        randomise_button = (Button) findViewById(R.id.randomise);
        play_button = (Button) findViewById(R.id.play);
        reset = (Button) findViewById(R.id.reset);

        for(int i=0;i<25;i++){
            selectedGrid[i] = 0;
        }
        Log.i("SelectedGrids",selectedGrid.toString());

        }



    //Helper Function to set the Game state (Playing state)
    public void setGamePlay(boolean play) {
       isPlaying = play;
        if(isPlaying) {
            randomise_button.setEnabled(false);
            play_button.setText("Stop");
        }
        else {
            randomise_button.setEnabled(true);
            play_button.setText("Play");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View cView, int position, long id) {
        //Mark it as used and disable the grid from further selection
        leftoutGrids.remove(position);

        BingoNumber cObject= (BingoNumber) parent.getItemAtPosition(position);
        View view = mGridView.getChildAt(position);
        view.setEnabled(false);
        view.setFocusable(false);
        if(isPlaying) {
            cObject.selected = true;
            view.setBackgroundColor(getColor(R.color.colorButton));
            selectedGrid[position] = 1;
            gameState(position,'U');
            //Log.i("User Plays: Grids", selectedGrid.toString());
            computerTurn(parent);
        }
    }

    //Handler for the Randomize request
    public void randomise(View view) {
        mBingoAdapter = new BingoAdapter(this,populate());
        mGridView.setAdapter(mBingoAdapter);

        //Selecting the left out boxes.
        leftoutGrids = new HashMap<>();
        object = null;
        object = new BingoStartActivity();
        for(int i=0;i<25;i++) {
            leftoutGrids.put(i, i);
        }
    }

    //Helper function for generating Random Numbered buttons
    public List<BingoNumber> populate() {
        BingoNumber[] randomNumArray = new BingoNumber[25];
        Random rd = new Random();
        for (int i = 0; i <25; i++) {
            randomNumArray[i] = new BingoNumber(i+1);
        }
        int j, x;
        for (int i = 0; i < 25; i++) {
            j = rd.nextInt(25);
            x = randomNumArray[i].getNumber();
            randomNumArray[i] = randomNumArray[j];
            randomNumArray[j] = new BingoNumber(x);
        }
        List<BingoNumber> bingoList = Arrays.asList(randomNumArray);
        return bingoList;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                showSnackBar();
                if(!isPlaying) {
                    setGamePlay(true);
                } else {
                    setGamePlay(false);
                }
                break;
            case R.id.randomise:
                randomise(v);
                break;
            case R.id.reset:
                Intent intent = new Intent(BingoStartActivity.this, BingoStartActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void showSnackBar() {

        //Snackbar.make()
    }

    void computerTurn(AdapterView<?> adapterView) {
        Random generator = new Random();
        Object[] values = leftoutGrids.values().toArray();
        int randomValue = (Integer) values[generator.nextInt(values.length)];

        final int numVisibleChildren = mGridView.getChildCount();
        final int firstVisiblePosition = mGridView.getFirstVisiblePosition();


        //Computer
        for ( int i = 0; i < numVisibleChildren; i++ ) {
            int positionOfView = firstVisiblePosition + i;

            if (positionOfView == randomValue) {
                BingoNumber cObject= (BingoNumber) adapterView.getItemAtPosition(positionOfView);
                cObject.selected = true;
                View view = mGridView.getChildAt(i);
                view.setEnabled(false);
                view.setFocusable(false);
                view.setBackgroundColor(getColor(R.color.colorButton));
                selectedGrid[positionOfView] = 1;

                leftoutGrids.remove(randomValue);
                gameState(positionOfView,'C');
                break;
            }
          }

        }

    public void gameState(int position, char c) {
        int rows = selectedGrid.length / NUM_COL;
        int[] columnValues = new int[rows]; //to store the item values at the given column index!
        int index = position % NUM_COL; //the column of which the values are to be accessed.
        //Any valid column index will do!
        for (int i = 0; i < rows; i++) {
            columnValues[i] = selectedGrid[i * NUM_COL + index];
        }

        int column_count = 0;
        for (int i = 0; i < columnValues.length; i++) {
            if (columnValues[i] == 0) {
                break;
            } else
                column_count++;
        }

       /* //Checking the row
        int row = selectedGrid.length / NUM_COL;
        int [] rowValues = new int [NUM_COL]; //to store the item values at the given row index!
*/
        /*for(int i=0;i<NUM_COL;i++) {

            for (int j = 0; j < NUM_COL; j++) {
                rowValues[j] = selectedGrid[(j *  + j];
            }
            int row_count = 0;

            for (int k = 0; k < rowValues.length; k++) {
                if (rowValues[k] == 0) {
                    break;
                } else
                    row_count++;
            }
            if (row_count == 5) {
                if (c == 'U' && userCount < 6) {
                    userCount++;
                    Log.i("Error",userCount+"");
                    gameResult(c, userCount);
                } else {
                    if (c == 'C' && compCount < 6) {
                        compCount++;
                        gameResult(c, compCount);
                    }
                }
            }
            else
                continue;

        }*/

        if (column_count == 5) {
            if (c == 'U' && userCount < 6) {
                userCount++;
                gameResult(c, userCount);
            } else {
                if (c == 'C' && compCount < 6) {
                    compCount++;
                    gameResult(c, compCount);
                }
            }

        }
    }


    public void gameResult(char c, int count) {
        String text;

        if(c=='U') {

            switch (count) {
                case 1:
                    text = "<font color=#2ecc71>B</font>ingo";
                    title.setText(Html.fromHtml(text));
                    break;
                case 2:
                    text = "<font color=#2ecc71>Bi</font>ngo";
                    title.setText(Html.fromHtml(text));
                    break;
                case 3:
                    text = "<font color=#2ecc71>Bin</font>go";
                    title.setText(Html.fromHtml(text));
                    break;
                case 4:
                    text = "<font color=#2ecc71>Bing</font>o";
                    title.setText(Html.fromHtml(text));
                    break;
                case 5:
                    text = "<font color=#2ecc71>Bingo</font>";
                    title.setText(Html.fromHtml(text));
                    Toast.makeText(this, "User Wins", Toast.LENGTH_LONG).show();
                    break;

            }
        }

    }
}
