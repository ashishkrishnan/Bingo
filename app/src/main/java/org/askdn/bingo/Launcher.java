package org.askdn.bingo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Button singlePlay = (Button) findViewById(R.id.singlePlay);

        TextView title = (TextView) findViewById(R.id.bold_text);

        String text = "<font color=#2ecc71>Bin</font>go";
        title.setText(Html.fromHtml(text));

        singlePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launch_singlePlay = new Intent(Launcher.this, BingoStartActivity.class);
                startActivity(launch_singlePlay);
            }
        });



    }
}
