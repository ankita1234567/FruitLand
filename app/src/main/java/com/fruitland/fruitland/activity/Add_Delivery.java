package com.fruitland.fruitland.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.adapter.Delivery_RecycleAddAdapter;
import com.fruitland.fruitland.model.Customer_Bean;
import com.fruitland.fruitland.utils.Const;
import com.fruitland.fruitland.utils.MyVolleyClass;
import com.fruitland.fruitland.utils.Parse;
import com.fruitland.fruitland.utils.Utility;
import com.fruitland.fruitland.utils.VolleyCompleteListener;
import com.fruitland.fruitland.widget.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by Admin on 4/1/2016.
 */
public class Add_Delivery extends Activity implements View.OnClickListener, VolleyCompleteListener {
    ArrayList<Customer_Bean> customer_list = new ArrayList<>();
    RecyclerView deliverycustlist;
    String checkeditems = "";
    Spinner weekspinner, monthspinner, areaspinner;
    Delivery_RecycleAddAdapter delivery_addadapter;
    ImageView menu, filter;
    TextView title;
    boolean isClicked = true;
    Button btn_ok;
    FloatingActionButton adddeliverybut;
    Parse parse;

    public Hashtable<Integer, Integer> checked_customers = new Hashtable<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_adddelivery);
        initialize();

    }

    private void initialize() {
        parse = new Parse(this);
        title = (TextView) findViewById(R.id.title);
        title.setText("ADD DELIVERY");
        adddeliverybut = (FloatingActionButton) findViewById(R.id.adddeliverybut);
        adddeliverybut.setOnClickListener(this);
        filter = (ImageView) findViewById(R.id.filter);
        filter.setVisibility(View.VISIBLE);
        filter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setAnimations();
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

        deliverycustlist = (RecyclerView) findViewById(R.id.deliverycustlist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        deliverycustlist.setLayoutManager(mLayoutManager);
        deliverycustlist.setItemAnimator(new DefaultItemAnimator());
/*
        for (int i = 0; i < 10; i++) {
            Customer_Bean customer_bean = new Customer_Bean();
            customer_list.add(customer_bean);
        }

        delivery_addadapter = new Delivery_AddAdapter(Add_Delivery.this, customer_list);
        deliverycustlist.setAdapter(delivery_addadapter);

        */

        getCustomersDelivery();

       /* deliverycustlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                setAnimations();
                getCustomersDelivery();
                break;
            case R.id.adddeliverybut:
                createList();
                addCustomersDelivery();
                break;
        }
    }


    private void createList() {
        checkeditems = "";
      /*  for (int i = 0; i < checked_customers.size(); i++) {
            checkeditems += checked_customers.get(i) + ",";
        }*/


        Enumeration e = checked_customers.keys();
        while (e.hasMoreElements()) {
            Integer i = (Integer) e.nextElement();
            checkeditems += checked_customers.get(i) + ",";
        }
        checkeditems = checkeditems.substring(0, checkeditems.length() - 1);
        Log.e("vvvvvvvvvvvvvvvv", checkeditems);
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


    private void getCustomersDelivery() {
        if (!Utility.isNetworkAvailable(this)) {
            Utility.showToast(
                    getResources().getString(R.string.dialog_no_inter_message),
                    this);
            return;
        }
        Utility.showSimpleProgressDialog(Add_Delivery.this, null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL, Const.ServiceType.CUSTOMER_REGIONWISE);
        map.put(Const.Params.REGIONID, areaspinner.getSelectedItemPosition() + 1 + "");
        map.put(Const.Params.WEEK, weekspinner.getSelectedItem().toString());
        map.put(Const.Params.MONTH, monthspinner.getSelectedItemPosition() + "");
        new MyVolleyClass(Add_Delivery.this, map, Const.ServiceCode.CUSTOMER_REGIONWISE, this);
    }


    private void addCustomersDelivery() {
        if (!Utility.isNetworkAvailable(this)) {
            Utility.showToast(
                    getResources().getString(R.string.dialog_no_inter_message),
                    this);
            return;
        }
        Utility.showSimpleProgressDialog(Add_Delivery.this, null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL, Const.ServiceType.ADD_DELIVERY);

        map.put(Const.Params.CUSTOMER_IDS, checkeditems);
        map.put(Const.Params.REGIONID, "1");
        map.put(Const.Params.WEEK, weekspinner.getSelectedItem().toString());
        map.put(Const.Params.MONTH, monthspinner.getSelectedItemPosition() + "");
        new MyVolleyClass(Add_Delivery.this, map, Const.ServiceCode.ADD_DELIVERY, this);
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {

            case Const.ServiceCode.CUSTOMER_REGIONWISE:
                Utility.removeSimpleProgressDialog();
                Log.e("%%%%%%%%%", response);
                if (parse.isSuccess(response)) {

                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray jsonArray = obj.getJSONArray("data");
                        checked_customers.clear();
                        checked_customers=new Hashtable<>();
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
                            customer_bean.setChecked(objdata.getInt("is_already_selected"));
                            customer_bean.setFruits_avoided(objdata.getString("fruit_avoided"));
                            customer_list.add(customer_bean);


                        }
                        delivery_addadapter = new Delivery_RecycleAddAdapter(Add_Delivery.this, customer_list, this);
                        deliverycustlist.setAdapter(delivery_addadapter);
                        RecyclerView.ItemDecoration itemDecoration =
                                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, 15);
                        deliverycustlist.addItemDecoration(itemDecoration);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case Const.ServiceCode.ADD_DELIVERY:
                Utility.removeSimpleProgressDialog();
                Log.e("%%%%%%%%%", response);
                if (parse.isSuccess(response)) {

                    try {
                        JSONObject obj = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_LONG).show();
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }

    public void addItems(int id) {
        if (!checked_customers.containsKey(id)) {
            checked_customers.put(id, id);


        } else {

            checked_customers.remove(id);
        }
    }
}
