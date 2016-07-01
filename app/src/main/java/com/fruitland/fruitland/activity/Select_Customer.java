package com.fruitland.fruitland.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
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
import com.fruitland.fruitland.adapter.SelectCustomer_RecycleAddAdapter;
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
public class Select_Customer extends Activity implements View.OnClickListener, VolleyCompleteListener {
    ArrayList<Customer_Bean> customer_list = new ArrayList<>();
    RecyclerView deliverycustlist;
    String checkeditems = "";
    Spinner weekspinner, monthspinner, areaspinner;

    SelectCustomer_RecycleAddAdapter delivery_addadapter;
    ImageView menu, filter;
    TextView title;
    boolean isClicked = true;
    Button btn_ok;
    FloatingActionButton adddeliverybut;
    Parse parse;

    public Hashtable<Integer, String> checked_customers = new Hashtable<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selectcustomer);
        initialize();

    }

    private void initialize() {
        parse = new Parse(this);
        title = (TextView) findViewById(R.id.title);
        title.setText("SEND MESSAGE");
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

        areaspinner.setSelection(0);

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

        Intent intent=new Intent(getApplicationContext(),Message_Activity.class);
        intent.putExtra("checkeditems",checkeditems);
        startActivity(intent);
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
        Utility.showSimpleProgressDialog(Select_Customer.this, null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL, Const.ServiceType.CUSTOMER_REGIONWISE);
        map.put(Const.Params.REGIONID, areaspinner.getSelectedItemPosition() + 1 + "");
        map.put(Const.Params.WEEK, weekspinner.getSelectedItem().toString());
        map.put(Const.Params.MONTH, monthspinner.getSelectedItemPosition() + "");
        new MyVolleyClass(Select_Customer.this, map, Const.ServiceCode.CUSTOMER_REGIONWISE, this);
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
                            customer_bean.setChecked(0);
                            customer_list.add(customer_bean);


                        }
                        delivery_addadapter = new SelectCustomer_RecycleAddAdapter(Select_Customer.this, customer_list, this);
                        deliverycustlist.setAdapter(delivery_addadapter);
                        RecyclerView.ItemDecoration itemDecoration =
                                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, 15);
                        deliverycustlist.addItemDecoration(itemDecoration);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;


        }
    }

    public void addItems(int id,String contact) {
        if (!checked_customers.containsKey(id)) {
            checked_customers.put(id, contact);


        } else {

            checked_customers.remove(id);
        }
    }
}
