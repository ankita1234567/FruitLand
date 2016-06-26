package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.fruitland.fruitland.R;

/**
 * Created by Admin on 4/27/2016.
 */
public class Message_Activity extends Activity {

        ImageView menu;
        TextView title;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_message);
            initialize();
        }

        private void initialize() {
            title = (TextView) findViewById(R.id.title);
            title.setText("SEND MESSAGE");
            MenuClass menuclass=new MenuClass();
            menuclass.simpleSlidingDrawer(this,"message",7);

        }
    }
