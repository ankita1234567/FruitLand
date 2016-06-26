package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fruitland.fruitland.R;

/**
 * Created by Admin on 4/1/2016.
 */
public class Delivery_Activity extends Activity implements View.OnClickListener {
    TextView title;

    Button add, pending, view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_delivery);
        initialize();
    }

    private void initialize() {
        add = (Button) findViewById(R.id.add);
        pending = (Button) findViewById(R.id.pending);

        view = (Button) findViewById(R.id.viewdel);
        view.setOnClickListener(this);
        
        pending.setOnClickListener(this);
        add.setOnClickListener(this);
        title=(TextView)findViewById(R.id.title);
        title.setText("DELIVERY");
        MenuClass menuclass=new MenuClass();
        menuclass.simpleSlidingDrawer(this,"delivery",2);



    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pending:
                intent=new Intent(getApplicationContext(),Pending_Delivery.class);
                startActivity(intent);
                break;


            case R.id.add:
                intent=new Intent(getApplicationContext(),Add_Delivery.class);
                startActivity(intent);
                break;

            case R.id.viewdel:
                intent=new Intent(getApplicationContext(),View_Delivery.class);
                startActivity(intent);
                break;
        }
    }
}
