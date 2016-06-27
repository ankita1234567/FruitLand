package com.fruitland.fruitland.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.adapter.ViewDelivery_Adapter;
import com.fruitland.fruitland.model.Customer_Bean;
import com.fruitland.fruitland.utils.Const;
import com.fruitland.fruitland.utils.MyVolleyClass;
import com.fruitland.fruitland.utils.Parse;
import com.fruitland.fruitland.utils.Utility;
import com.fruitland.fruitland.utils.VolleyCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Admin on 4/1/2016.
 */
public class View_Delivery extends Activity implements View.OnClickListener ,VolleyCompleteListener {


    Spinner weekspinner, monthspinner, areaspinner;
    Button btn_ok;
    ArrayList<Customer_Bean> customer_list = new ArrayList<>();
    ListView list_customer;
    private Parse parse;
    ViewDelivery_Adapter viewDelivery_adapter;
    ImageView menu, filter;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcustomerdetails);
        initialize();
    }

    private void initialize() {
        parse = new Parse(View_Delivery.this);
        title = (TextView) findViewById(R.id.title);
        title.setText("PENDING DELIVERIES");
        filter = (ImageView) findViewById(R.id.filter);
        filter.setVisibility(View.VISIBLE);
        filter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setAnimations();
            }
        });
        list_customer = (ListView)findViewById(R.id.viewdeliverycustlist);
        areaspinner = (Spinner) findViewById(R.id.areaspinner);
        weekspinner = (Spinner) findViewById(R.id.weekspinner);
        monthspinner = (Spinner) findViewById(R.id.monthspinner);
        Calendar c = Calendar.getInstance();

        int month = c.get(Calendar.MONTH);
        Log.e("month", month + "");
        monthspinner.setSelection(month + 1);

        int week = c.get(Calendar.WEEK_OF_MONTH);
        Log.e("week", week + "");
        weekspinner.setSelection(week);

      //  loadList();

        list_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Delivery_CustomerDetails.class);
                intent.putExtra("deliverydata",customer_list.get(position));
                startActivity(intent);
            }
        });

        menu = (ImageView) findViewById(R.id.expanded_menu);
        menu.setImageDrawable(getResources().getDrawable(R.drawable.back));
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
         getCustomersDelivery();

    }

    private void loadList() {



        viewDelivery_adapter = new ViewDelivery_Adapter(View_Delivery.this, customer_list);
        list_customer.setAdapter(viewDelivery_adapter);
        list_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Delivery_CustomerDetails.class);
                intent.putExtra("deliverydata",customer_list.get(position));
                startActivity(intent);
            }
        });
    }


    private void setAnimations() {

        final LinearLayout mLayoutTab = (LinearLayout) findViewById(R.id.filterlay);

        if (mLayoutTab.getVisibility() == View.GONE) {
            mLayoutTab.animate()
                    .translationY(0).alpha(1.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            mLayoutTab.setVisibility(View.VISIBLE);
                            mLayoutTab.setAlpha(0.0f);
                        }
                    });
        } else {
            mLayoutTab.animate()
                    .translationY(0).alpha(0.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mLayoutTab.setVisibility(View.GONE);
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                setAnimations();
                getCustomersDelivery();
                break;
        }
    }

    private void getCustomersDelivery() {
        if (!Utility.isNetworkAvailable(this)) {
            Utility.showToast(
                    getResources().getString(R.string.dialog_no_inter_message),
                    this);
            return;
        }
        Utility.showSimpleProgressDialog(View_Delivery.this, null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL, Const.ServiceType.VIEW_DELIVERY);
        map.put(Const.Params.REGIONID, areaspinner.getSelectedItemPosition() + 1 + "");
        map.put(Const.Params.WEEK, weekspinner.getSelectedItem().toString());
        map.put(Const.Params.MONTH, monthspinner.getSelectedItemPosition() + "");
        new MyVolleyClass(View_Delivery.this, map, Const.ServiceCode.VIEW_DELIVERY, this);
    }




    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {

            case Const.ServiceCode.VIEW_DELIVERY:
                Utility.removeSimpleProgressDialog();
                Log.e("%%%%%%%%%", response);
                if (parse.isSuccess(response)) {

                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray jsonArray = obj.getJSONArray("data");
                        customer_list=new ArrayList<>();
                        customer_list.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Customer_Bean customer_bean = new Customer_Bean();
                            JSONObject objdata = jsonArray.getJSONObject(i);
                            customer_bean.setName(objdata.getString("name"));
                            customer_bean.setCustomer_id(objdata.getString("id"));
                            customer_bean.setContact(objdata.getString("phone"));
                            customer_bean.setAddress(objdata.getString("address"));
                            customer_bean.setLat(objdata.getString("lat"));
                            customer_bean.setLng(objdata.getString("lng"));
                            customer_bean.setPackages(objdata.getString("package"));
                            customer_bean.setChecked(objdata.getInt("is_delivered"));
                            customer_bean.setFruits_avoided(objdata.getString("fruit_avoided"));
                            customer_list.add(customer_bean);


                        }
                        //  ViewDelivery_Adapter = new ViewDelivery_Adapter(Pending_Delivery.this, customer_list);
                       // list_customer.setAdapter(ViewDelivery_Adapter);
                        loadList();

                   } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }
}
