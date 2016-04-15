package org.askdn.bingo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BingoStartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{


    GridView mGridView;
    BingoAdapter mBingoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bingo_start);
        mGridView = (GridView) findViewById(R.id.grid);
        if(mGridView == null) {
            Log.e("GridError","is NUll");
        }
        mBingoAdapter = new BingoAdapter(this, populate());

        Log.e("GridError",mBingoAdapter.toString());
        mGridView.setAdapter(mBingoAdapter);
        mGridView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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


}
