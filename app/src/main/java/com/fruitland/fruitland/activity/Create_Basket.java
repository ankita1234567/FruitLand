package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.adapter.FruitsRecycler_Adapter;

import java.util.Hashtable;
import java.util.Set;

/**
 * Created by Admin on 4/26/2016.
 */
public class Create_Basket extends Activity implements View.OnClickListener {
    TextView title, maptext;
    /*GridView fruitgrid;*/
    RecyclerView fruitgrid;
    String fruitsstr;
    public Hashtable<Integer, String> selectedfruits = new Hashtable<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_createbasket);
        initialize();
    }


    private void initialize() {
        MenuClass menuclass = new MenuClass();
        menuclass.simpleSlidingDrawer(this, "basket", 4);
        title = (TextView) findViewById(R.id.title);
        title.setText("WEEKLY BASKET");
        maptext = (TextView) findViewById(R.id.nexttext);
        maptext.setOnClickListener(this);
        maptext.setVisibility(View.VISIBLE);
        maptext.setText("NEXT");

        fruitgrid = (RecyclerView) findViewById(R.id.fruitgrid);

       /* FruitsGrid_Adapter fruitsGrid_adapter=new FruitsGrid_Adapter(Create_Basket.this);

        fruitgrid.setAdapter(fruitsGrid_adapter);

        fruitgrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),CreateBasketListActivity.class);
                startActivity(intent);
            }
        });*/
        FruitsRecycler_Adapter fruitsGrid_adapter = new FruitsRecycler_Adapter(com.fruitland.fruitland.activity.Create_Basket.this, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        fruitgrid.setLayoutManager(mLayoutManager);
        fruitgrid.setItemAnimator(new DefaultItemAnimator());
        fruitgrid.setAdapter(fruitsGrid_adapter);

    }

    public void addFruit(int position, String value) {

        try {

            if (selectedfruits.containsKey(
                    position)) {
                selectedfruits.remove(position);
            } else {
                if (selectedfruits.size() == 5) {
                    Toast.makeText(getApplicationContext(), "You can select maximum 5 fruits", Toast.LENGTH_LONG).show();
                } else {
                    selectedfruits.put(position, value);
                }


            }

        } catch (Exception e) {
            String sff = "fsgdh";
        }

    }


    private void selectFruits() {
        fruitsstr = "";
        Set<Integer> keys = selectedfruits.keySet();
        for (Integer key : keys) {
            fruitsstr += (key + 1) + ",";
        }
fruitsstr=fruitsstr.substring(0,fruitsstr.length()-1);
        Toast.makeText(getApplicationContext(), fruitsstr, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nexttext:
                Set<Integer> keys = selectedfruits.keySet();
                for (Integer key : keys) {
                    System.out.println("Value of " + key + " is: " + selectedfruits.get(key));
                }

                selectFruits();

                Intent intent=new Intent(getApplicationContext(),CreateBasketListActivity.class);
                intent.putExtra("fruitstr",fruitsstr);
                startActivity(intent);
                break;
        }
    }
}


