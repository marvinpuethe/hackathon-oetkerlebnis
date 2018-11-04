package com.oetker.oetkerlebnis.level;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.oetker.oetkerlebnis.MainActivity;
import com.oetker.oetkerlebnis.R;

public class LevelActivity extends AppCompatActivity {
    private int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
    }

    public void onClickCake(View v)
    {
        ImageButton imgbtn = (ImageButton) findViewById(R.id.imageButton);
        if(state == 0) {
            imgbtn.setBackground(this.getDrawable(R.drawable.lvl1));
            state = 1;
        } else if(state == 1) {
            imgbtn.setBackground(this.getDrawable(R.drawable.lvl2));
            state = 2;
        } else if(state == 2) {
            imgbtn.setBackground(this.getDrawable(R.drawable.lvl3));
            state = 3;
        } else if(state == 3) {
            state = 4;
        } else if(state == 4) {
            state = 5;
        }
    }
}
