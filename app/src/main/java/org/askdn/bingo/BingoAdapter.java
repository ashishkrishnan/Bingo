package org.askdn.bingo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

/**
 * Created by ashish on 13/4/16.
 */
public class BingoAdapter extends ArrayAdapter<BingoNumber> {


    public BingoAdapter(Context context, List<BingoNumber> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        BingoNumber item = getItem(position);

        // If the view is being set for the first time.
        // View has not be recycled
        if(view == null) {
            view = LayoutInflater.from(
                    getContext()).inflate(R.layout.bingo_single_item, parent, false);
        }
        Button btn = (Button) view.findViewById(R.id.btn);

        btn.setText(""+item.getNumber());


        return view;






    }
}