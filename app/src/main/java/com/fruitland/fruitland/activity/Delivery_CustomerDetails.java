package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.model.Customer_Bean;
import com.fruitland.fruitland.utils.Const;
import com.fruitland.fruitland.utils.MyVolleyClass;
import com.fruitland.fruitland.utils.Parse;
import com.fruitland.fruitland.utils.Utility;
import com.fruitland.fruitland.utils.VolleyCompleteListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Admin on 4/3/2016.
 */
public class Delivery_CustomerDetails extends Activity implements View.OnClickListener,VolleyCompleteListener {
    ImageView menu;
    TextView title,name,contact,address,packages,fruitsavoided;
    Button deliver, payndeliver;
    private Parse parse;


    Customer_Bean customer_bean; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deliverydetails);
        initialize();
    }

    private void initialize() {

        customer_bean=(Customer_Bean)getIntent().getSerializableExtra("deliverydata");
        parse = new Parse(Delivery_CustomerDetails.this);

        title = (TextView) findViewById(R.id.title);
        title.setText("PENDING DELIVERIES");
        menu = (ImageView) findViewById(R.id.expanded_menu);
        menu.setImageDrawable(getResources().getDrawable(R.drawable.back));
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        deliver = (Button) findViewById(R.id.deliver);
        payndeliver = (Button) findViewById(R.id.payndeliver);
        payndeliver.setOnClickListener(this);
        deliver.setOnClickListener(this);

        name = (TextView) findViewById(R.id.name);
        name.setText(customer_bean.getName());
        contact = (TextView) findViewById(R.id.contact);
        contact.setText(customer_bean.getContact());
        address = (TextView) findViewById(R.id.address);
        address.setText(customer_bean.getAddress());
        packages = (TextView) findViewById(R.id.packages);
        packages.setText(customer_bean.getPackages());
        fruitsavoided = (TextView) findViewById(R.id.fruitsavoided);
        fruitsavoided.setText(customer_bean.getFruits_avoided());

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.deliver:
                intent = new Intent(getApplicationContext(), Signature_Activity.class);
                intent.putExtra("deliveryid",customer_bean.getDeliveyid());
                startActivity(intent);
                break;

            case R.id.payndeliver:
                intent = new Intent(getApplicationContext(), Signature_Activity.class);
                startActivity(intent);

                break;
        }
    }


    private void deliver() {
        if (!Utility.isNetworkAvailable(this)) {
            Utility.showToast(
                    getResources().getString(R.string.dialog_no_inter_message),
                    this);
            return;
        }
        Utility.showSimpleProgressDialog(Delivery_CustomerDetails.this, null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL, Const.ServiceType.DELIVERY_FRUIT);
        map.put(Const.Params.DELIVERYID, customer_bean.getDeliveyid());
        map.put(Const.Params.ISPAID, "0");

        new MyVolleyClass(Delivery_CustomerDetails.this, map, Const.ServiceCode.DELIVERY_FRUIT, this);
    }
    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {

            case Const.ServiceCode.PENDING_DELIVERY:
                Utility.removeSimpleProgressDialog();
                Log.e("%%%%%%%%%", response);
                if (parse.isSuccess(response)) {

                    try {
                        JSONObject obj = new JSONObject(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
        }
    }
}
