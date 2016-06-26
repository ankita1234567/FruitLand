package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.model.Fruits_Bean;
import com.fruitland.fruitland.model.Purchase_Bean;

import java.util.Hashtable;

/**
 * Created by Admin on 4/27/2016.
 */
public class View_PurchaseDetails extends Activity implements OnClickListener {
    ImageView menu;
    TextView title, date, nexttext;
    EditText totalamnt, desc;
    EditText mangorate, mangoqty, applerate, appleqty, muskrate, muskqty, pomerate, pomeqty, litchirate, litchiqty, papayarate, papayaqty, jammonrate, jammonqty, peerrate, peerqty, sweetlimerate, sweetlimeqty, orangerate, orangeqty, chikoorate, chikooqty, watermelonrate, watermelonqty;

    Spinner mangounit, appleunit, muskunit, pomeunit, litchiunit, papayaunit, jammonunit, peerunit, sweetlimeunit, orangeunit, chikoounit, watermelonunit;
    TableRow mangorow, applerow, muskrow, pomerow, litchirow, papayarow, jammonrow, peerrow, sweetlimerow, orangerow, chikoorow, watermelonrow;

    Hashtable<Integer, Fruits_Bean> fruits_beanArrayList = new Hashtable<>();

    Purchase_Bean purchase_bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpurchase);
        initialize();
    }

    private void initialize() {

        purchase_bean = (Purchase_Bean) getIntent().getSerializableExtra("purchase");
        fruits_beanArrayList = purchase_bean.getFruits_beanArrayList();
        title = (TextView) findViewById(R.id.title);
        title.setText("PURCHASE DETAILS");


        menu = (ImageView) findViewById(R.id.expanded_menu);
        menu.setImageDrawable(getResources().getDrawable(R.drawable.back));
        menu.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        mangorate = (EditText) findViewById(R.id.mangorate);
       // mangorate.setEnabled(false);
        mangorate.setFocusable(false);

        applerate = (EditText) findViewById(R.id.applerate);
        applerate.setFocusable(false);
        muskrate = (EditText) findViewById(R.id.muskrate);
        muskrate.setFocusable(false);
        pomerate = (EditText) findViewById(R.id.pomerate);
        pomerate.setFocusable(false);
        litchirate = (EditText) findViewById(R.id.litchirate);
        litchirate.setFocusable(false);
        papayarate = (EditText) findViewById(R.id.papayarate);
        papayarate.setFocusable(false);
        jammonrate = (EditText) findViewById(R.id.jammonrate);
        jammonrate.setFocusable(false);
        peerrate = (EditText) findViewById(R.id.peerrate);
        peerrate.setFocusable(false);
        sweetlimerate = (EditText) findViewById(R.id.sweetlimerate);
        sweetlimerate.setFocusable(false);
        orangerate = (EditText) findViewById(R.id.orangerate);
        orangerate.setFocusable(false);

        chikoorate = (EditText) findViewById(R.id.chikoorate);
        chikoorate.setFocusable(false);
        watermelonrate = (EditText) findViewById(R.id.watermelonrate);
        watermelonrate.setFocusable(false);
        mangoqty = (EditText) findViewById(R.id.mangoqty);
        mangoqty.setFocusable(false);
        appleqty = (EditText) findViewById(R.id.appleqty);
        appleqty.setFocusable(false);
        muskqty = (EditText) findViewById(R.id.muskqty);
        muskqty.setFocusable(false);
        pomeqty = (EditText) findViewById(R.id.pomeqty);
        pomeqty.setFocusable(false);
        litchiqty = (EditText) findViewById(R.id.litchiqty);
        litchiqty.setFocusable(false);
        papayaqty = (EditText) findViewById(R.id.papayaqty);
        papayaqty.setFocusable(false);
        jammonqty = (EditText) findViewById(R.id.jammonqty);
        jammonqty.setFocusable(false);
        peerqty = (EditText) findViewById(R.id.peerqty);
        peerqty.setFocusable(false);
        sweetlimeqty = (EditText) findViewById(R.id.sweetlimeqty);
        sweetlimeqty.setFocusable(false);
        orangeqty = (EditText) findViewById(R.id.orangeqty);
        orangeqty.setFocusable(false);
        chikooqty = (EditText) findViewById(R.id.chikooqty);
        chikooqty.setFocusable(false);
        watermelonqty = (EditText) findViewById(R.id.watermelonqty);
        watermelonqty.setFocusable(false);


        mangounit = (Spinner) findViewById(R.id.mangounit);
        mangounit.setEnabled(false);
        appleunit = (Spinner) findViewById(R.id.appleunit);
        appleunit.setEnabled(false);
        muskunit = (Spinner) findViewById(R.id.muskunit);
        muskunit.setEnabled(false);
        pomeunit = (Spinner) findViewById(R.id.pomeunit);
        pomeunit.setEnabled(false);
        litchiunit = (Spinner) findViewById(R.id.litchiunit);
        litchiunit.setEnabled(false);
        papayaunit = (Spinner) findViewById(R.id.papayaunit);
        papayaunit.setEnabled(false);
        jammonunit = (Spinner) findViewById(R.id.jammonunit);
        jammonunit.setEnabled(false);
        peerunit = (Spinner) findViewById(R.id.peerunit);
        peerunit.setEnabled(false);
        sweetlimeunit = (Spinner) findViewById(R.id.sweetlimeunit);
        sweetlimeunit.setEnabled(false);
        orangeunit = (Spinner) findViewById(R.id.orangeunit);
        orangeunit.setEnabled(false);
        chikoounit = (Spinner) findViewById(R.id.chikoounit);
        chikoounit.setEnabled(false);
        watermelonunit = (Spinner) findViewById(R.id.watermelonunit);
        watermelonunit.setEnabled(false);


        mangorow = (TableRow) findViewById(R.id.mangorow);

        applerow = (TableRow) findViewById(R.id.applerow);

        muskrow = (TableRow) findViewById(R.id.muskrow);

        pomerow = (TableRow) findViewById(R.id.pomerow);

        litchirow = (TableRow) findViewById(R.id.litchirow);

        papayarow = (TableRow) findViewById(R.id.papayarow);

        jammonrow = (TableRow) findViewById(R.id.jammonrow);

        peerrow = (TableRow) findViewById(R.id.peerrow);

        sweetlimerow = (TableRow) findViewById(R.id.sweetlimerow);

        orangerow = (TableRow) findViewById(R.id.orangerow);

        chikoorow = (TableRow) findViewById(R.id.chikoorow);

        watermelonrow = (TableRow) findViewById(R.id.watermelonrow);


        date = (TextView) findViewById(R.id.date);
        date.setText(purchase_bean.getDate());
        totalamnt = (EditText) findViewById(R.id.total);
        totalamnt.setFocusable(false);
        totalamnt.setText("? "+purchase_bean.getTotal());
        desc = (EditText) findViewById(R.id.desc);
        desc.setFocusable(false);
        desc.setText(purchase_bean.getDescription());

        loadData();


    }


    private void loadData() {

        if (fruits_beanArrayList.containsKey(12)) {
            Fruits_Bean fruits_bean = fruits_beanArrayList.get(12);
            mangoqty.setText(fruits_bean.getQuantity());
            mangorate.setText(fruits_bean.getRate());
            if (fruits_bean.getUnit().equalsIgnoreCase("kg")) {
                mangounit.setSelection(0);
            } else {
                mangounit.setSelection(1);
            }
        }else{
            mangorow.setVisibility(View.GONE);
        }

        if (fruits_beanArrayList.containsKey(1)) {
            Fruits_Bean fruits_bean = fruits_beanArrayList.get(1);
            appleqty.setText(fruits_bean.getQuantity());
            applerate.setText(fruits_bean.getRate());
            if (fruits_bean.getUnit().equalsIgnoreCase("kg")) {
                appleunit.setSelection(0);
            } else {
                appleunit.setSelection(1);
            }
        }else{
            applerow.setVisibility(View.GONE);
        }

        if (fruits_beanArrayList.containsKey(7)) {
            Fruits_Bean fruits_bean = fruits_beanArrayList.get(7);
            muskqty.setText(fruits_bean.getQuantity());
            muskrate.setText(fruits_bean.getRate());
            if (fruits_bean.getUnit().equalsIgnoreCase("kg")) {
                muskunit.setSelection(0);
            } else {
                muskunit.setSelection(1);
            }
        }else{
            muskrow.setVisibility(View.GONE);
        }

        if (fruits_beanArrayList.containsKey(3)) {
            Fruits_Bean fruits_bean = fruits_beanArrayList.get(3);
            pomeqty.setText(fruits_bean.getQuantity());
            pomerate.setText(fruits_bean.getRate());
            if (fruits_bean.getUnit().equalsIgnoreCase("kg")) {
                pomeunit.setSelection(0);
            } else {
                pomeunit.setSelection(1);
            }
        }else{
            pomerow.setVisibility(View.GONE);
        }

        if (fruits_beanArrayList.containsKey(10)) {
            Fruits_Bean fruits_bean = fruits_beanArrayList.get(10);
            litchiqty.setText(fruits_bean.getQuantity());
            litchirate.setText(fruits_bean.getRate());
            if (fruits_bean.getUnit().equalsIgnoreCase("kg")) {
                litchiunit.setSelection(0);
            } else {
                litchiunit.setSelection(1);
            }
        }else{
            litchirow.setVisibility(View.GONE);
        }
        if (fruits_beanArrayList.containsKey(8)) {
            Fruits_Bean fruits_bean = fruits_beanArrayList.get(8);
            papayaqty.setText(fruits_bean.getQuantity());
            papayarate.setText(fruits_bean.getRate());
            if (fruits_bean.getUnit().equalsIgnoreCase("kg")) {
                papayaunit.setSelection(0);
            } else {
                papayaunit.setSelection(1);
            }
        }else{
            papayarow.setVisibility(View.GONE);
        }

        if (fruits_beanArrayList.containsKey(11)) {
            Fruits_Bean fruits_bean = fruits_beanArrayList.get(11);
            jammonqty.setText(fruits_bean.getQuantity());
            jammonrate.setText(fruits_bean.getRate());
            if (fruits_bean.getUnit().equalsIgnoreCase("kg")) {
                jammonunit.setSelection(0);
            } else {
                jammonunit.setSelection(1);
            }
        }else{
            jammonrow.setVisibility(View.GONE);
        }

        if (fruits_beanArrayList.containsKey(2)) {
            Fruits_Bean fruits_bean = fruits_beanArrayList.get(2);
            peerqty.setText(fruits_bean.getQuantity());
            peerrate.setText(fruits_bean.getRate());
            if (fruits_bean.getUnit().equalsIgnoreCase("kg")) {
                peerunit.setSelection(0);
            } else {
                peerunit.setSelection(1);
            }
        }else{
            peerrow.setVisibility(View.GONE);
        }

        if (fruits_beanArrayList.containsKey(5)) {
            Fruits_Bean fruits_bean = fruits_beanArrayList.get(5);
            sweetlimeqty.setText(fruits_bean.getQuantity());
            sweetlimerate.setText(fruits_bean.getRate());
            if (fruits_bean.getUnit().equalsIgnoreCase("kg")) {
                sweetlimeunit.setSelection(0);
            } else {
                sweetlimeunit.setSelection(1);
            }
        }else{
            sweetlimerow.setVisibility(View.GONE);
        }

        if (fruits_beanArrayList.containsKey(6)) {
            Fruits_Bean fruits_bean = fruits_beanArrayList.get(6);
            orangeqty.setText(fruits_bean.getQuantity());
            orangerate.setText(fruits_bean.getRate());
            if (fruits_bean.getUnit().equalsIgnoreCase("kg")) {
                orangeunit.setSelection(0);
            } else {
                orangeunit.setSelection(1);
            }

        }else{
            orangerow.setVisibility(View.GONE);
        }

        if (fruits_beanArrayList.containsKey(4)) {
            Fruits_Bean fruits_bean = fruits_beanArrayList.get(4);
            chikooqty.setText(fruits_bean.getQuantity());
            chikoorate.setText(fruits_bean.getRate());
            if (fruits_bean.getUnit().equalsIgnoreCase("kg")) {
                chikoounit.setSelection(0);
            } else {
                chikoounit.setSelection(1);
            }

        }else{
            chikoorow.setVisibility(View.GONE);
        }

        if (fruits_beanArrayList.containsKey(9)) {
            Fruits_Bean fruits_bean = fruits_beanArrayList.get(9);
            watermelonqty.setText(fruits_bean.getQuantity());
            watermelonrate.setText(fruits_bean.getRate());
            if (fruits_bean.getUnit().equalsIgnoreCase("kg")) {
                watermelonunit.setSelection(0);
            } else {
                watermelonunit.setSelection(1);
            }

        }else{
            watermelonrow.setVisibility(View.GONE);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
