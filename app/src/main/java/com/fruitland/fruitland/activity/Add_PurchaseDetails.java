package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.utils.Const;
import com.fruitland.fruitland.utils.MyVolleyClass;
import com.fruitland.fruitland.utils.Parse;
import com.fruitland.fruitland.utils.Utility;
import com.fruitland.fruitland.utils.VolleyCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Admin on 4/27/2016.
 */
public class Add_PurchaseDetails extends Activity implements VolleyCompleteListener, OnClickListener {
    ImageView menu;
    TextView title, date, nexttext;
    EditText totalamnt, desc;
    EditText mangorate, mangoqty, applerate, appleqty, muskrate, muskqty, pomerate, pomeqty, litchirate, litchiqty, papayarate, papayaqty, jammonrate, jammonqty, peerrate, peerqty, sweetlimerate, sweetlimeqty, orangerate, orangeqty, chikoorate, chikooqty, watermelonrate, watermelonqty;
    private Parse parse;
    Spinner mangounit,  appleunit,  muskunit,  pomeunit, litchiunit,  papayaunit,  jammonunit,  peerunit,  sweetlimeunit,  orangeunit,  chikoounit,  watermelonunit;
    JSONObject data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpurchase);
        initialize();
    }

    private void initialize() {
        parse = new Parse(this);

        title = (TextView) findViewById(R.id.title);
        title.setText("ADD DETAILS");

        nexttext = (TextView) findViewById(R.id.nexttext);
        nexttext.setText("ADD");
        nexttext.setVisibility(View.VISIBLE);
        nexttext.setOnClickListener(this);


        menu = (ImageView) findViewById(R.id.expanded_menu);
        menu.setImageDrawable(getResources().getDrawable(R.drawable.back));
        menu.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        mangorate = (EditText) findViewById(R.id.mangorate);
        applerate = (EditText) findViewById(R.id.applerate);
        muskrate = (EditText) findViewById(R.id.muskrate);
        pomerate = (EditText) findViewById(R.id.pomerate);
        litchirate = (EditText) findViewById(R.id.litchirate);
        papayarate = (EditText) findViewById(R.id.papayarate);
        jammonrate = (EditText) findViewById(R.id.jammonrate);
        peerrate = (EditText) findViewById(R.id.peerrate);
        sweetlimerate = (EditText) findViewById(R.id.sweetlimerate);
        orangerate = (EditText) findViewById(R.id.orangerate);
        chikoorate = (EditText) findViewById(R.id.chikoorate);
        watermelonrate = (EditText) findViewById(R.id.watermelonrate);
        mangoqty = (EditText) findViewById(R.id.mangoqty);
        appleqty = (EditText) findViewById(R.id.appleqty);
        muskqty = (EditText) findViewById(R.id.muskqty);
        pomeqty = (EditText) findViewById(R.id.pomeqty);
        litchiqty = (EditText) findViewById(R.id.litchiqty);
        papayaqty = (EditText) findViewById(R.id.papayaqty);
        jammonqty = (EditText) findViewById(R.id.jammonqty);
        peerqty = (EditText) findViewById(R.id.peerqty);
        sweetlimeqty = (EditText) findViewById(R.id.sweetlimeqty);
        orangeqty = (EditText) findViewById(R.id.orangeqty);
        chikooqty = (EditText) findViewById(R.id.chikooqty);
        watermelonqty = (EditText) findViewById(R.id.watermelonqty);


        mangounit = (Spinner) findViewById(R.id.mangounit);
        appleunit = (Spinner) findViewById(R.id.appleunit);
        muskunit = (Spinner) findViewById(R.id.muskunit);
        pomeunit = (Spinner) findViewById(R.id.pomeunit);
        litchiunit = (Spinner) findViewById(R.id.litchiunit);
        papayaunit = (Spinner) findViewById(R.id.papayaunit);
        jammonunit = (Spinner) findViewById(R.id.jammonunit);
        peerunit = (Spinner) findViewById(R.id.peerunit);
        sweetlimeunit = (Spinner) findViewById(R.id.sweetlimeunit);
        orangeunit = (Spinner) findViewById(R.id.orangeunit);
        chikoounit = (Spinner) findViewById(R.id.chikoounit);
        watermelonunit = (Spinner) findViewById(R.id.watermelonunit);

        date = (TextView) findViewById(R.id.date);
        date.setOnClickListener(this);
        totalamnt = (EditText) findViewById(R.id.total);
        desc = (EditText) findViewById(R.id.desc);


      /*  loadData();
        addPurchaseDetails();*/


    }


    private void addPurchaseDetails() {
        if (!Utility.isNetworkAvailable(this)) {
            Utility.showToast(
                    getResources().getString(R.string.dialog_no_inter_message),
                    this);
            return;
        }
        Utility.showSimpleProgressDialog(Add_PurchaseDetails.this, null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL, Const.ServiceType.ADD_PURCHASE);
        Log.e("DATAAAAAAAAAAAAAA", "data=" + data.toString());
        map.put("data", data.toString());
        new MyVolleyClass(Add_PurchaseDetails.this, map, Const.ServiceCode.ADD_PURCHASE, this);
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
                mangoobj.put("unit", mangounit.getSelectedItem().toString().replace("/",""));
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
                fruitobj1.put("unit", appleunit.getSelectedItem().toString().replace("/",""));
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
                fruitobj.put("unit", muskunit.getSelectedItem().toString().replace("/",""));
                Double total = Double.parseDouble(muskqty.getText().toString()) * Double.parseDouble(muskrate.getText().toString());
                fruitobj.put("total", "" + total);
                fruits.put(fruitobj);
            }

            if (!pomeqty.getText().toString().equals("") && !pomerate.getText().toString().equals("")) {
                JSONObject fruitobj = new JSONObject();

                fruitobj.put("fruit_id", "3");
                fruitobj.put("quantity", pomeqty.getText().toString());
                fruitobj.put("rate", pomerate.getText().toString());
                fruitobj.put("unit", pomeunit.getSelectedItem().toString().replace("/",""));
                Double total = Double.parseDouble(pomeqty.getText().toString()) * Double.parseDouble(pomerate.getText().toString());
                fruitobj.put("total", "" + total);
                fruits.put(fruitobj);
            }

            if (!litchiqty.getText().toString().equals("") && !litchirate.getText().toString().equals("")) {
                JSONObject fruitobj = new JSONObject();

                fruitobj.put("fruit_id", "10");
                fruitobj.put("quantity", litchiqty.getText().toString());
                fruitobj.put("rate", litchirate.getText().toString());
                fruitobj.put("unit", litchiunit.getSelectedItem().toString().replace("/",""));
                Double total = Double.parseDouble(litchiqty.getText().toString()) * Double.parseDouble(litchirate.getText().toString());
                fruitobj.put("total", "" + total);
                fruits.put(fruitobj);
            }
            if (!papayaqty.getText().toString().equals("") && !papayarate.getText().toString().equals("")) {
                JSONObject fruitobj = new JSONObject();

                fruitobj.put("fruit_id", "8");
                fruitobj.put("quantity", papayaqty.getText().toString());
                fruitobj.put("rate", papayarate.getText().toString());
                fruitobj.put("unit", papayaunit.getSelectedItem().toString().replace("/",""));
                Double total = Double.parseDouble(papayaqty.getText().toString()) * Double.parseDouble(papayarate.getText().toString());
                fruitobj.put("total", "" + total);
                fruits.put(fruitobj);
            }

            if (!jammonqty.getText().toString().equals("") && !jammonrate.getText().toString().equals("")) {
                JSONObject fruitobj = new JSONObject();

                fruitobj.put("fruit_id", "11");
                fruitobj.put("quantity", jammonqty.getText().toString());
                fruitobj.put("rate", jammonrate.getText().toString());
                fruitobj.put("unit", jammonunit.getSelectedItem().toString().replace("/",""));
                Double total = Double.parseDouble(jammonqty.getText().toString()) * Double.parseDouble(jammonrate.getText().toString());
                fruitobj.put("total", "" + total);
                fruits.put(fruitobj);
            }

            if (!peerqty.getText().toString().equals("") && !peerrate.getText().toString().equals("")) {
                JSONObject fruitobj = new JSONObject();

                fruitobj.put("fruit_id", "2");
                fruitobj.put("quantity", peerqty.getText().toString());
                fruitobj.put("rate", peerrate.getText().toString());
                fruitobj.put("unit", peerunit.getSelectedItem().toString().replace("/",""));
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
                fruitobj.put("unit", watermelonunit.getSelectedItem().toString().replace("/",""));
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

            case Const.ServiceCode.ADD_PURCHASE:
                Log.e("RRRRRRRR", response);

                if (parse.isSuccess(response)) {

                    try {
                        JSONObject obj = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_LONG).show();
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                }
        }
    }

    private void showDialog() {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date.setText(year + "-"
                                + (monthOfYear + 1) + "-" + dayOfMonth);


                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    private void validate() {

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        if (date.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Select Date", Toast.LENGTH_LONG).show();
        } else if (totalamnt.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Select Total Amount", Toast.LENGTH_LONG).show();

        } else {
            loadData();
            addPurchaseDetails();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date:
                showDialog();
                break;
            case R.id.nexttext:
                validate();
                break;
        }
    }
}
