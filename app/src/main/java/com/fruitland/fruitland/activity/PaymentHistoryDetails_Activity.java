package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.adapter.PaymentHistoryDetails_Adapter;
import com.fruitland.fruitland.model.PaymentHistoryDetails_Bean;
import com.navdrawer.SimpleSideDrawer;

import java.util.ArrayList;

/**
 * Created by Admin on 3/25/2016.
 */
public class PaymentHistoryDetails_Activity extends Activity implements View.OnClickListener {
    SimpleSideDrawer mNavv;
    ImageView menu;
    TextView title;

    ArrayList<PaymentHistoryDetails_Bean> customer_list = new ArrayList<>();
    ListView list_customer;
    PaymentHistoryDetails_Adapter customer_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_paymenthistorydetails);
        initialize();
    }

    private void initialize() {


        title = (TextView) findViewById(R.id.title);
        title.setText("PAYMENT HISTORY DETAILS");
        menu = (ImageView) findViewById(R.id.expanded_menu);
        menu.setImageDrawable(getResources().getDrawable(R.drawable.back));
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        list_customer = (ListView) findViewById(R.id.list_paymenthistorydetails);

        for (int i = 0; i < 10; i++) {
            PaymentHistoryDetails_Bean customer_bean = new PaymentHistoryDetails_Bean();
            customer_list.add(customer_bean);
        }

        customer_adapter = new PaymentHistoryDetails_Adapter(PaymentHistoryDetails_Activity.this, customer_list);
        list_customer.setAdapter(customer_adapter);
        list_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Feedback_Details.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
                 }
    }
}
