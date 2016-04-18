package org.askdn.bingo;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class BingoStartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener{


    // Key = Value of the selected grid Item : Value = position of the selected grid Item
    LinkedHashMap<Integer, Integer> lastComSelect = new LinkedHashMap<>();

    BingoNumber temp;
    HashMap<Integer,Integer> leftoutGrids = new HashMap<>();
    TextView title;
    public static boolean isPlaying = false;
    GridView mGridView;
    Button play_button, randomise_button;
    BingoAdapter mBingoAdapter;
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

        String text = "<font color=#2ecc71>B</font>ingo";
        title.setText(Html.fromHtml(text));

    }

    //Helper function to get a reference to the XML Views
    private void intialize() {

        //Intializing a set of avaliable grids
        for(int i=0;i<25;i++) {
            leftoutGrids.put(i,i);
        }
        title = (TextView) findViewById(R.id.title);
        mGridView = (GridView) findViewById(R.id.grid);
        randomise_button = (Button) findViewById(R.id.randomise);
        play_button = (Button) findViewById(R.id.play);
        }



    //Helper Function to set the Game state (Playing state)
    public void setGameState(boolean play) {
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

        View view = mGridView.getChildAt(position);
        view.setEnabled(false);
        view.setFocusable(false);
        view.setBackgroundColor(getColor(R.color.colorButton));
        computerTurn();
    }

    //Handler for the Randomize request
    public void randomise(View view) {
        mBingoAdapter = new BingoAdapter(this,populate());
        mGridView.setAdapter(mBingoAdapter);

        //Selecting the left out boxes.
        leftoutGrids = new HashMap<>();
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
                    setGameState(true);
                } else {
                    setGameState(false);
                }
                break;
            case R.id.randomise:
                randomise(v);
                break;
        }
    }

    private void showSnackBar() {

        //Snackbar.make()
    }

    void computerTurn() {


        Random generator = new Random();
        Object[] values = leftoutGrids.values().toArray();
        int randomValue = (Integer) values[generator.nextInt(values.length)];

        final int numVisibleChildren = mGridView.getChildCount();
        final int firstVisiblePosition = mGridView.getFirstVisiblePosition();

        //Computer
        for ( int i = 0; i < numVisibleChildren; i++ ) {
            int positionOfView = firstVisiblePosition + i;

            if (positionOfView == randomValue) {
                View view = mGridView.getChildAt(i);
                view.setEnabled(false);
                view.setFocusable(false);
                view.setBackgroundColor(getColor(R.color.colorButton));
                leftoutGrids.remove(randomValue);
                break;
            }
        }




        }

    }
