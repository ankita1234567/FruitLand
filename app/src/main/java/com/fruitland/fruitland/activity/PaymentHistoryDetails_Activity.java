package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.adapter.PaymentHistoryDetails_Adapter;
import com.fruitland.fruitland.model.PaymentHistoryDetails_Bean;
import com.fruitland.fruitland.utils.Const;
import com.fruitland.fruitland.utils.MyVolleyClass;
import com.fruitland.fruitland.utils.Parse;
import com.fruitland.fruitland.utils.Utility;
import com.fruitland.fruitland.utils.VolleyCompleteListener;
import com.navdrawer.SimpleSideDrawer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Admin on 3/25/2016.
 */
public class PaymentHistoryDetails_Activity extends Activity implements View.OnClickListener, VolleyCompleteListener {
    SimpleSideDrawer mNavv;
    ImageView menu;
    TextView title;
    Parse parse;
    String id="";

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
id=getIntent().getStringExtra("id");
        parse = new Parse(this);
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

        list_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Feedback_Details.class);
                startActivity(intent);
            }
        });

        getCustomersPaymentHistoryDetails();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    private void getCustomersPaymentHistoryDetails() {
        if (!Utility.isNetworkAvailable(this)) {
            Utility.showToast(
                            getResources().getString(R.string.dialog_no_inter_message),
                    this);
            return;
        }
        Utility.showSimpleProgressDialog(this, null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL, Const.ServiceType.PAYMENT_HISTORYDETAILS);
        map.put("customer_id", id);
        new MyVolleyClass(this, map, Const.ServiceCode.PAYMENT_HISTORYDETAILS, this);
    }


    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {

            case Const.ServiceCode.PAYMENT_HISTORYDETAILS:
                Utility.removeSimpleProgressDialog();
                Log.i("URL LOGIN RESPONSE", response);
                if (parse.isSuccess(response)) {

                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray jsonArray = obj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            PaymentHistoryDetails_Bean paymentHistory_bean = new PaymentHistoryDetails_Bean();
                            JSONObject objdata = jsonArray.getJSONObject(i);


                            paymentHistory_bean.setAmount(objdata.getString("amount"));

                            paymentHistory_bean.setDate(objdata.getString("payment_date"));

                            customer_list.add(paymentHistory_bean);

                        }
                        customer_adapter = new PaymentHistoryDetails_Adapter(PaymentHistoryDetails_Activity.this, customer_list);
                        list_customer.setAdapter(customer_adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;
        }

    }
}
