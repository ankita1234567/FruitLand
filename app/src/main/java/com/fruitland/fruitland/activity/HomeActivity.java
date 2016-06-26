package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.adapter.Customer_Adapter;
import com.fruitland.fruitland.model.Customer_Bean;
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
public class HomeActivity extends Activity implements View.OnClickListener, VolleyCompleteListener {
    SimpleSideDrawer mNavv;
    private Parse parse;
    ImageView menu;
    TextView title;
    FloatingActionButton addcustomer;
    ArrayList<Customer_Bean> customer_list = new ArrayList<>();
    ListView list_customer;
    Customer_Adapter customer_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_home);
        initialize();
    }

    private void initialize() {
        parse = new Parse(HomeActivity.this);
        title = (TextView) findViewById(R.id.title);
        title.setText("HOME");
        addcustomer = (FloatingActionButton) findViewById(R.id.addcustommer);
        addcustomer.setOnClickListener(this);


        MenuClass menuclass = new MenuClass();
        menuclass.simpleSlidingDrawer(this, "home", 1);

        list_customer = (ListView) findViewById(R.id.list_customer);

     /*   for (int i = 0; i < 10; i++) {
            Customer_Bean customer_bean = new Customer_Bean();
            customer_list.add(customer_bean);
        }
*/

        getCustomers();
        list_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CustomerDetailsActivity.class);
                intent.putExtra("customer",customer_list.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addcustommer:
                Intent intent = new Intent(getApplicationContext(), AddCustomerActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void getCustomers() {
        if (!Utility.isNetworkAvailable(this)) {
            Utility.showToast(
                    getResources().getString(R.string.dialog_no_inter_message),
                    this);
            return;
        }
        Utility.showSimpleProgressDialog(HomeActivity.this, null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL, Const.ServiceType.GET_CUSTOMERS);
        new MyVolleyClass(HomeActivity.this, map, Const.ServiceCode.GET_CUSTOMERS, this);
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        Intent intent;
        switch (serviceCode) {

            case Const.ServiceCode.GET_CUSTOMERS:
                Utility.removeSimpleProgressDialog();
                Log.i("URL LOGIN RESPONSE", response);
                if (parse.isSuccess(response)) {

                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray jsonArray = obj.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            Customer_Bean customer_bean = new Customer_Bean();
                            JSONObject objdata = jsonArray.getJSONObject(i);
                            customer_bean.setName(objdata.getString("name"));
                            customer_bean.setCustomer_id(objdata.getString("id"));
                            customer_bean.setContact(objdata.getString("phone"));
                            customer_bean.setAddress(objdata.getString("address"));
                            customer_bean.setPackages(objdata.getString("package"));
                            customer_bean.setFruits_avoided(objdata.getString("fruit_avoided"));
                            customer_list.add(customer_bean);

                        }
                        customer_adapter = new Customer_Adapter(HomeActivity.this, customer_list);
                        list_customer.setAdapter(customer_adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }

}
