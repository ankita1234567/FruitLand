package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.adapter.Purchase_Adapter;
import com.fruitland.fruitland.model.Fruits_Bean;
import com.fruitland.fruitland.model.Purchase_Bean;
import com.fruitland.fruitland.utils.Const;
import com.fruitland.fruitland.utils.MyVolleyClass;
import com.fruitland.fruitland.utils.Parse;
import com.fruitland.fruitland.utils.Utility;
import com.fruitland.fruitland.utils.VolleyCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by Admin on 4/27/2016.
 */
public class View_PurchaseDetailsList extends Activity implements VolleyCompleteListener, OnClickListener {
    ImageView menu;
    ArrayList<Purchase_Bean> purchase_beanArrayList = new ArrayList<>();
    TextView title, date, nexttext;
    EditText totalamnt, desc;
    EditText mangorate, mangoqty, applerate, appleqty, muskrate, muskqty, pomerate, pomeqty, litchirate, litchiqty, papayarate, papayaqty, jammonrate, jammonqty, peerrate, peerqty, sweetlimerate, sweetlimeqty, orangerate, orangeqty, chikoorate, chikooqty, watermelonrate, watermelonqty;
    private Parse parse;
    Spinner mangounit, appleunit, muskunit, pomeunit, litchiunit, papayaunit, jammonunit, peerunit, sweetlimeunit, orangeunit, chikoounit, watermelonunit;
    JSONObject data;
    Purchase_Adapter purchase_adapter;
    ListView list_purchase;
    Hashtable<Integer,Fruits_Bean> fruits_beanArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpurchaselist);
        initialize();
    }

    private void initialize() {
        parse = new Parse(this);

        title = (TextView) findViewById(R.id.title);
        title.setText("PURCHASE DETAILS");


        menu = (ImageView) findViewById(R.id.expanded_menu);
        menu.setImageDrawable(getResources().getDrawable(R.drawable.back));
        menu.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        list_purchase = (ListView) findViewById(R.id.list_purchase);
        list_purchase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(getApplicationContext(),View_PurchaseDetails.class);
                    intent.putExtra("purchase",purchase_beanArrayList.get(position));
                    startActivity(intent);
            }
        });
        viewPurchaseDetails();

    }


    private void viewPurchaseDetails() {
        if (!Utility.isNetworkAvailable(this)) {
            Utility.showToast(
                    getResources().getString(R.string.dialog_no_inter_message),
                    this);
            return;
        }
        Utility.showSimpleProgressDialog(View_PurchaseDetailsList.this, null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL, Const.ServiceType.VIEW_PURCHASE);

        new MyVolleyClass(View_PurchaseDetailsList.this, map, Const.ServiceCode.VIEW_PURCHASE, this);
    }

    private void loadData() {
        data = new JSONObject();

        JSONArray fruits = new JSONArray();
        try {
            data.put("fruits", fruits);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {


            if (!mangoqty.getText().toString().equals("") && !mangorate.getText().toString().equals("")) {
                JSONObject mangoobj = new JSONObject();
                mangoobj.put("fruit_id", "12");
                mangoobj.put("quantity", mangoqty.getText().toString());
                mangoobj.put("unit", mangounit.getSelectedItem().toString().replace("/", ""));
                mangoobj.put("rate", mangorate.getText().toString());
                Double total = Double.parseDouble(mangoqty.getText().toString()) * Double.parseDouble(mangorate.getText().toString());
                mangoobj.put("total", "" + total);
                fruits.put(mangoobj);
            }


            if (!appleqty.getText().toString().equals("") && !applerate.getText().toString().equals("")) {
                JSONObject fruitobj1 = new JSONObject();

                fruitobj1.put("fruit_id", "1");
                fruitobj1.put("quantity", appleqty.getText().toString());
                fruitobj1.put("rate", applerate.getText().toString());
                fruitobj1.put("unit", appleunit.getSelectedItem().toString().replace("/", ""));
                Double total = Double.parseDouble(appleqty.getText().toString()) * Double.parseDouble(applerate.getText().toString());
                fruitobj1.put("total", "" + total);
                fruits.put(fruitobj1);
            }

          /*  if (!muskqty.getText().toString().equals("") && !muskrate.getText().toString().equals("")) {
                JSONObject fruitobj2 = new JSONObject();

                fruitobj2.put("fruit_id", "3");
                fruitobj2.put("quantity", muskqty.getText().toString());
                fruitobj2.put("rate", "250");
                fruitobj2.put("total", "" + 500);
                fruits.put(fruitobj2);
            }*/

            if (!muskqty.getText().toString().equals("") && !muskrate.getText().toString().equals("")) {
                JSONObject fruitobj = new JSONObject();
                fruitobj.put("fruit_id", "7");
                fruitobj.put("quantity", muskqty.getText().toString());
                fruitobj.put("rate", muskrate.getText().toString());
                fruitobj.put("unit", muskunit.getSelectedItem().toString().replace("/", ""));
                Double total = Double.parseDouble(muskqty.getText().toString()) * Double.parseDouble(muskrate.getText().toString());
                fruitobj.put("total", "" + total);
                fruits.put(fruitobj);
            }

            if (!pomeqty.getText().toString().equals("") && !pomerate.getText().toString().equals("")) {
                JSONObject fruitobj = new JSONObject();

                fruitobj.put("fruit_id", "3");
                fruitobj.put("quantity", pomeqty.getText().toString());
                fruitobj.put("rate", pomerate.getText().toString());
                fruitobj.put("unit", pomeunit.getSelectedItem().toString().replace("/", ""));
                Double total = Double.parseDouble(pomeqty.getText().toString()) * Double.parseDouble(pomerate.getText().toString());
                fruitobj.put("total", "" + total);
                fruits.put(fruitobj);
            }

            if (!litchiqty.getText().toString().equals("") && !litchirate.getText().toString().equals("")) {
                JSONObject fruitobj = new JSONObject();

                fruitobj.put("fruit_id", "10");
                fruitobj.put("quantity", litchiqty.getText().toString());
                fruitobj.put("rate", litchirate.getText().toString());
                fruitobj.put("unit", litchiunit.getSelectedItem().toString().replace("/", ""));
                Double total = Double.parseDouble(litchiqty.getText().toString()) * Double.parseDouble(litchirate.getText().toString());
                fruitobj.put("total", "" + total);
                fruits.put(fruitobj);
            }
            if (!papayaqty.getText().toString().equals("") && !papayarate.getText().toString().equals("")) {
                JSONObject fruitobj = new JSONObject();

                fruitobj.put("fruit_id", "8");
                fruitobj.put("quantity", papayaqty.getText().toString());
                fruitobj.put("rate", papayarate.getText().toString());
                fruitobj.put("unit", papayaunit.getSelectedItem().toString().replace("/", ""));
                Double total = Double.parseDouble(papayaqty.getText().toString()) * Double.parseDouble(papayarate.getText().toString());
                fruitobj.put("total", "" + total);
                fruits.put(fruitobj);
            }

            if (!jammonqty.getText().toString().equals("") && !jammonrate.getText().toString().equals("")) {
                JSONObject fruitobj = new JSONObject();

                fruitobj.put("fruit_id", "11");
                fruitobj.put("quantity", jammonqty.getText().toString());
                fruitobj.put("rate", jammonrate.getText().toString());
                fruitobj.put("unit", jammonunit.getSelectedItem().toString().replace("/", ""));
                Double total = Double.parseDouble(jammonqty.getText().toString()) * Double.parseDouble(jammonrate.getText().toString());
                fruitobj.put("total", "" + total);
                fruits.put(fruitobj);
            }

            if (!peerqty.getText().toString().equals("") && !peerrate.getText().toString().equals("")) {
                JSONObject fruitobj = new JSONObject();

                fruitobj.put("fruit_id", "2");
                fruitobj.put("quantity", peerqty.getText().toString());
                fruitobj.put("rate", peerrate.getText().toString());
                fruitobj.put("unit", peerunit.getSelectedItem().toString().replace("/", ""));
                Double total = Double.parseDouble(peerqty.getText().toString()) * Double.parseDouble(peerrate.getText().toString());
                fruitobj.put("total", "" + total);
                fruits.put(fruitobj);
            }


            if (!sweetlimeqty.getText().toString().equals("") && !sweetlimerate.getText().toString().equals("")) {
                JSONObject fruitobj = new JSONObject();

                fruitobj.put("fruit_id", "5");
                fruitobj.put("quantity", sweetlimeqty.getText().toString());
                fruitobj.put("rate", sweetlimerate.getText().toString());
                fruitobj.put("unit", sweetlimeunit.getSelectedItem().toString().replace("/", ""));
                Double total = Double.parseDouble(sweetlimeqty.getText().toString()) * Double.parseDouble(sweetlimerate.getText().toString());
                fruitobj.put("total", "" + total);
                fruits.put(fruitobj);
            }

            if (!orangeqty.getText().toString().equals("") && !orangerate.getText().toString().equals("")) {
                JSONObject fruitobj = new JSONObject();

                fruitobj.put("fruit_id", "6");
                fruitobj.put("quantity", orangeqty.getText().toString());
                fruitobj.put("rate", orangerate.getText().toString());
                fruitobj.put("unit", orangeunit.getSelectedItem().toString().replace("/", ""));
                Double total = Double.parseDouble(orangeqty.getText().toString()) * Double.parseDouble(orangerate.getText().toString());
                fruitobj.put("total", "" + total);
                fruits.put(fruitobj);
            }

            if (!chikooqty.getText().toString().equals("") && !chikoorate.getText().toString().equals("")) {
                JSONObject fruitobj = new JSONObject();

                fruitobj.put("fruit_id", "4");
                fruitobj.put("quantity", chikooqty.getText().toString());
                fruitobj.put("rate", chikoorate.getText().toString());
                fruitobj.put("unit", chikoounit.getSelectedItem().toString().replace("/", ""));
                Double total = Double.parseDouble(chikooqty.getText().toString()) * Double.parseDouble(chikoorate.getText().toString());
                fruitobj.put("total", "" + total);
                fruits.put(fruitobj);
            }

            if (!watermelonqty.getText().toString().equals("") && !watermelonrate.getText().toString().equals("")) {
                JSONObject fruitobj = new JSONObject();

                fruitobj.put("fruit_id", "9");
                fruitobj.put("quantity", watermelonqty.getText().toString());
                fruitobj.put("rate", watermelonrate.getText().toString());
                fruitobj.put("unit", watermelonunit.getSelectedItem().toString().replace("/", ""));
                Double total = Double.parseDouble(watermelonqty.getText().toString()) * Double.parseDouble(watermelonrate.getText().toString());
                fruitobj.put("total", "" + total);
                fruits.put(fruitobj);
            }

            data.put("total_amount", totalamnt.getText().toString());
            data.put("dat", date.getText().toString());
            data.put("description", desc.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        Log.e("%%%%%%", response);
        switch (serviceCode) {

            case Const.ServiceCode.VIEW_PURCHASE:
                Log.e("RRRRRRRR", response);

                if (parse.isSuccess(response)) {

                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray jsonArray = obj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Purchase_Bean purchase_bean = new Purchase_Bean();
                            JSONObject objdata = jsonArray.getJSONObject(i);
                            purchase_bean.setDate(objdata.getString("dat"));
                            purchase_bean.setDescription(objdata.getString("description"));
                            purchase_bean.setTotal(objdata.getString("total_amount"));

                            JSONArray jsonFruitsArray = objdata.getJSONArray("fruits");
                            fruits_beanArrayList=new Hashtable<>();
                            fruits_beanArrayList.clear();
                            for (int j = 0; j < jsonFruitsArray.length(); j++) {
                                JSONObject jsonfruitObj = jsonFruitsArray.getJSONObject(j);
                                Fruits_Bean fruits_bean = new Fruits_Bean();
                                fruits_bean.setFruit_id(jsonfruitObj.getString("fruit_id"));
                                fruits_bean.setRate(jsonfruitObj.getString("rate"));
                                fruits_bean.setQuantity(jsonfruitObj.getString("quantity"));
                                fruits_bean.setTotal(jsonfruitObj.getString("total"));
                                fruits_bean.setUnit(jsonfruitObj.getString("unit"));
                                purchase_bean.setFruits_bean(fruits_bean);
                                fruits_beanArrayList.put(Integer.parseInt(jsonfruitObj.getString("fruit_id")),fruits_bean);
                            }

                            purchase_bean.setFruits_beanArrayList(fruits_beanArrayList);

                            purchase_beanArrayList.add(purchase_bean);

                        }
                        purchase_adapter = new Purchase_Adapter(View_PurchaseDetailsList.this, purchase_beanArrayList);
                        list_purchase.setAdapter(purchase_adapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
