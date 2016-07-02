package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Admin on 3/26/2016.
 */
public class AddCustomerActivity extends Activity implements View.OnClickListener, VolleyCompleteListener {
    ImageView menu;
    Parse parse;
    String fruitavoided="";
    TextView title;
    EditText name, contact, address;
    Button ess, med, exo, btn_addcust;
Spinner areaspinner;
    CheckBox applecheck, mangocheck, pomecheck, lichicheck,papayacheck, watermeloncheck, muskmeloncheck, pearcheck, chikoocheck, orangecheck, sweetlimecheck, jammoncheck;
    String pkgsel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_addcustomer);
        initialize();
    }

    private void initialize() {
        areaspinner=(Spinner)findViewById(R.id.areaspinner);
        parse = new Parse(AddCustomerActivity.this);
        title = (TextView) findViewById(R.id.title);
        title.setText("ADD CUSTOMER");
        menu = (ImageView) findViewById(R.id.expanded_menu);
        menu.setImageDrawable(getResources().getDrawable(R.drawable.back));
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        name = (EditText) findViewById(R.id.name);
        contact = (EditText) findViewById(R.id.contact);
        address = (EditText) findViewById(R.id.address);

        ess = (Button) findViewById(R.id.ess);
        ess.setOnClickListener(this);
        med = (Button) findViewById(R.id.med);
        med.setOnClickListener(this);
        exo = (Button) findViewById(R.id.exo);
        exo.setOnClickListener(this);
        btn_addcust = (Button) findViewById(R.id.btn_addcustomer);
        btn_addcust.setOnClickListener(this);


        mangocheck = (CheckBox) findViewById(R.id.mangocheck);
        chikoocheck = (CheckBox) findViewById(R.id.chikoocheck);
        pomecheck = (CheckBox) findViewById(R.id.pomecheck);
        pearcheck = (CheckBox) findViewById(R.id.peercheck);
        orangecheck = (CheckBox) findViewById(R.id.orangecheck);
        sweetlimecheck = (CheckBox) findViewById(R.id.sweetlimecheck);
        applecheck = (CheckBox) findViewById(R.id.applecheck);
        watermeloncheck = (CheckBox) findViewById(R.id.watermeloncheck);
        muskmeloncheck = (CheckBox) findViewById(R.id.muskmeloncheck);
        papayacheck = (CheckBox) findViewById(R.id.papayacheck);
        jammoncheck = (CheckBox) findViewById(R.id.jammoncheck);
        lichicheck = (CheckBox) findViewById(R.id.lichicheck);
    }

    private void checkIsChecked() {

         fruitavoided = "";
        if (mangocheck.isChecked()) {
            fruitavoided += "12,";
        }
        if (chikoocheck.isChecked()) {
            fruitavoided += "4,";
        }
        if (pearcheck.isChecked()) {
            fruitavoided += "2,";
        }
        if (muskmeloncheck.isChecked()) {
            fruitavoided += "7,";
        }
        if (watermeloncheck.isChecked()) {
            fruitavoided += "9,";
        }
        if (orangecheck.isChecked()) {
            fruitavoided += "6,";
        }
        if (sweetlimecheck.isChecked()) {
            fruitavoided += "5,";
        }
        if (applecheck.isChecked()) {
            fruitavoided += "1,";
        }
        if (jammoncheck.isChecked()) {
            fruitavoided += "11,";
        }
        if (pomecheck.isChecked()) {
            fruitavoided += "3,";
        }
        if (lichicheck.isChecked()) {
            fruitavoided += "10,";
        }
        if (papayacheck.isChecked()) {
            fruitavoided += "8,";
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ess:
                pkgsel = "ess";
                ess.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_box_radius_black));
                med.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_box_radius));
                exo.setBackgroundDrawable(getResources().getDrawable(R.drawable.yellow_box_radius));
                break;
            case R.id.med:
                pkgsel = "med";
                ess.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_box_radius));
                med.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_box_radius_black));
                exo.setBackgroundDrawable(getResources().getDrawable(R.drawable.yellow_box_radius));
                break;
            case R.id.exo:
                pkgsel = "exo";
                ess.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_box_radius));
                med.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_box_radius));
                exo.setBackgroundDrawable(getResources().getDrawable(R.drawable.yellow_box_radius_black));
                break;
            case R.id.btn_addcustomer:
                validate();
                break;

        }


    }

    private void addCustomers() {
        if (!Utility.isNetworkAvailable(this)) {
            Utility.showToast(
                    getResources().getString(R.string.dialog_no_inter_message),
                    this);
            return;
        }
        Utility.showSimpleProgressDialog(AddCustomerActivity.this, null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();


        map.put(Const.URL, Const.ServiceType.ADD_CUSTOMER);
        map.put("address", address.getText().toString());
        map.put("region_id",areaspinner.getSelectedItemPosition()+1+"");
        map.put("name", name.getText().toString());
        map.put("phone", contact.getText().toString());
        if (pkgsel.equals("ess")) {
            map.put("package", "The Essentials");
        }
        if (pkgsel.equals("med")) {
            map.put("package", "Grand Medley");
        }
        if (pkgsel.equals("exo")) {
            map.put("package", "Exotica");
        }

        map.put("fruit_avoided", fruitavoided.substring(0,fruitavoided.length()-1));
        new MyVolleyClass(AddCustomerActivity.this, map, Const.ServiceCode.ADD_CUSTOMER, this);
    }

    private void validate() {
        if (name.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter name", Toast.LENGTH_LONG).show();
        } else if (contact.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter contact number", Toast.LENGTH_LONG).show();
        } else if (address.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter address", Toast.LENGTH_LONG).show();
        } else if (pkgsel.equals("")) {
            Toast.makeText(getApplicationContext(), "Please select package", Toast.LENGTH_LONG).show();
        } else {
            checkIsChecked();
            addCustomers();
        }
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        Intent intent;
        //{"success":true,"msg":"Customer Added Successfully"}
        switch (serviceCode) {

            case Const.ServiceCode.ADD_CUSTOMER:
                Utility.removeSimpleProgressDialog();
                Log.i("URL LOGIN RESPONSE", response);
                if (parse.isSuccess(response)) {

                    try {
                        JSONObject obj = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_LONG).show();
                        name.setText("");
                        address.setText("");
                        contact.setText("");

                        pkgsel = "";
                        ess.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_box_radius));
                        med.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_box_radius));
                        exo.setBackgroundDrawable(getResources().getDrawable(R.drawable.yellow_box_radius));
                        //  JSONArray jsonArray = obj.getJSONArray("data");

                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
