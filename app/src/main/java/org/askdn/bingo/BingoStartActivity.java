package org.askdn.bingo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BingoStartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener{


    static boolean isPlaying = false;
    GridView mGridView;
    Button play_button, randomise_button;
    BingoAdapter mBingoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bingo_start);

        intialize();
        mBingoAdapter = new BingoAdapter(this, populate());
        mGridView.setAdapter(mBingoAdapter);
        play_button.setOnClickListener(this);
        randomise_button.setOnClickListener(this);
        mGridView.setOnItemClickListener(this);

    }
    private void intialize() {

        mGridView = (GridView) findViewById(R.id.grid);
        randomise_button = (Button) findViewById(R.id.randomise);
        play_button = (Button) findViewById(R.id.play);

    }

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
        cView.setEnabled(false);
        cView.setFocusable(false);
    }

    public void randomise(View view) {
        mBingoAdapter = new BingoAdapter(this,populate());
        mGridView.setAdapter(mBingoAdapter);
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
        Log.d("Error",randomNumArray.toString());
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
}
