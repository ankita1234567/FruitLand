package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fruitland.fruitland.R;

/**
 * Created by Admin on 4/27/2016.
 */
public class Purchase_Details extends Activity implements View.OnClickListener{
    ImageView menu;
    TextView title;
    Button addpurchase,viewpurchase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasedetails);
        initialize();
    }

    private void initialize() {
        title = (TextView) findViewById(R.id.title);
        title.setText("PURCHASE DETAILS");
        MenuClass menuclass=new MenuClass();
        menuclass.simpleSlidingDrawer(this,"purchase",5);
        addpurchase=(Button)findViewById(R.id.addpurchase);
        addpurchase.setOnClickListener(this);
        viewpurchase=(Button)findViewById(R.id.viewpurchase);
        viewpurchase.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.addpurchase:
                intent=new Intent(getApplicationContext(),Add_PurchaseDetails.class);
                startActivity(intent);
                break;
            case R.id.viewpurchase:
                intent=new Intent(getApplicationContext(),View_PurchaseDetailsList.class);
                startActivity(intent);
                break;
        }
    }
}
