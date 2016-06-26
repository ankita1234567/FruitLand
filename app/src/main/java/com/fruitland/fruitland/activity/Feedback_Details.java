package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.model.Feedback_Bean;

/**
 * Created by Admin on 4/27/2016.
 */
public class Feedback_Details extends Activity {

        ImageView menu;
        TextView title,name,comments;
    RatingBar ratingBar;
    Feedback_Bean feedback_bean;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_feedbackdetails);
            initialize();
        }

        private void initialize() {

            feedback_bean=(Feedback_Bean)getIntent().getSerializableExtra("feedbackdata");
            title = (TextView) findViewById(R.id.title);
            title.setText("FEEDBACK DETAILS");
            menu = (ImageView) findViewById(R.id.expanded_menu);
            menu.setImageDrawable(getResources().getDrawable(R.drawable.back));
            menu.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                }
            });
            ratingBar=(RatingBar)findViewById(R.id.ratingBar);
            name = (TextView) findViewById(R.id.name);
            name.setText(feedback_bean.getName());
            comments = (TextView) findViewById(R.id.comments);
            comments.setText(feedback_bean.getComment());
            ratingBar.setRating(Integer.parseInt(feedback_bean.getRating()))  ;

        }
    }
