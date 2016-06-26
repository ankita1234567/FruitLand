package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.model.Customer_Bean;
import com.fruitland.fruitland.utils.Const;
import com.fruitland.fruitland.utils.MyVolleyClass;
import com.fruitland.fruitland.utils.Parse;
import com.fruitland.fruitland.utils.Utility;
import com.fruitland.fruitland.utils.VolleyCompleteListener;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 3/25/2016.
 */
public class CustomerDetailsActivity extends Activity implements View.OnClickListener, VolleyCompleteListener {
    ImageView menu;
    TextView title;
    EditText name, contact, address, fruitsavoided;
    Button ess, med, exo, btn_addcust;
    FloatingActionButton addcustommer;
    String pkgsel = "";
    Parse parse;
    String fruitavoided = "";
    Customer_Bean customer_bean;
    CheckBox applecheck, mangocheck, pomecheck, lichicheck, papayacheck, watermeloncheck, muskmeloncheck, pearcheck, chikoocheck, orangecheck, sweetlimecheck, jammoncheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_customerdetails);
        initialize();
    }

    private void initialize() {
        parse = new Parse(this);
        customer_bean = new Customer_Bean();
        customer_bean = (Customer_Bean) getIntent().getSerializableExtra("customer");
        title = (TextView) findViewById(R.id.title);
        title.setText("DETAILS");
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
        fruitsavoided = (EditText) findViewById(R.id.fruitsavoided);
        ess = (Button) findViewById(R.id.ess);
        ess.setOnClickListener(this);
        med = (Button) findViewById(R.id.med);
        med.setOnClickListener(this);
        exo = (Button) findViewById(R.id.exo);
        exo.setOnClickListener(this);
        btn_addcust = (Button) findViewById(R.id.btn_addcustomer);
        btn_addcust.setOnClickListener(this);
        addcustommer = (FloatingActionButton) findViewById(R.id.addcustommer);
        addcustommer.setOnClickListener(this);
        ess.setClickable(false);
        med.setClickable(false);
        exo.setClickable(false);

        name.setText(customer_bean.getName());
        contact.setText(customer_bean.getContact());
        address.setText(customer_bean.getAddress());

        if (customer_bean.getPackages().equals("The Essentials")) {
            ess.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_box_radius_black));
        } else if (customer_bean.getPackages().equals("Grand Medley")) {
            med.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_box_radius_black));
        } else if (customer_bean.getPackages().equals("Exotica")) {
            exo.setBackgroundDrawable(getResources().getDrawable(R.drawable.yellow_box_radius_black));
        }

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

        setChecked();
    }


    private void setChecked() {
        String strfruitsavoidd = customer_bean.getFruits_avoided();
        List<String> items = Arrays.asList(strfruitsavoidd.split("\\s*,\\s*"));

        HashMap<String, String> productMap = new HashMap<String, String>();
        for (String product : items) {
            productMap.put(product, "yes");
        }

        if (productMap.containsKey("12")) {

            mangocheck.setChecked(true);
        }
        if (productMap.containsKey("4")) {

            chikoocheck.setChecked(true);
        }
        if (productMap.containsKey("2")) {

            pearcheck.setChecked(true);
        }
        if (productMap.containsKey("7")) {

            muskmeloncheck.setChecked(true);
        }
        if (productMap.containsKey("9")) {

            watermeloncheck.setChecked(true);
        }
        if (productMap.containsKey("6")) {

            orangecheck.setChecked(true);
        }
        if (productMap.containsKey("5")) {

            sweetlimecheck.setChecked(true);
        }
        if (productMap.containsKey("1")) {

            applecheck.setChecked(true);
        }
        if (productMap.containsKey("11")) {

            jammoncheck.setChecked(true);
        }
        if (productMap.containsKey("3")) {

            pomecheck.setChecked(true);
        }
        if (productMap.containsKey("10")) {

            lichicheck.setChecked(true);
        }
        if (productMap.containsKey("8")) {

            papayacheck.setChecked(true);
        }

    }

    private void updateCustomerDetails() {
        if (!Utility.isNetworkAvailable(this)) {
            Utility.showToast(
                    getResources().getString(R.string.dialog_no_inter_message),
                    this);
            return;
        }
        Utility.showSimpleProgressDialog(CustomerDetailsActivity.this, null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();


        map.put(Const.URL, Const.ServiceType.UPDATE_CUSTOMER);
        map.put("address", address.getText().toString());
         map.put("id", customer_bean.getCustomer_id());
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

        map.put("fruit_avoided", fruitavoided);
        new MyVolleyClass(CustomerDetailsActivity.this, map, Const.ServiceCode.UPDATE_CUSTOMER, this);
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
            case R.id.addcustommer:
                ess.setClickable(true);
                med.setClickable(true);
                exo.setClickable(true);
                btn_addcust.setVisibility(View.VISIBLE);
                addcustommer.setVisibility(View.GONE);
                name.setEnabled(true);
                contact.setEnabled(true);
                address.setEnabled(true);
                fruitsavoided.setEnabled(true);
                break;

            case R.id.btn_addcustomer:
                ess.setClickable(false);
                med.setClickable(false);
                exo.setClickable(false);
                checkIsChecked();
                updateCustomerDetails();
                btn_addcust.setVisibility(View.GONE);
                addcustommer.setVisibility(View.VISIBLE);

                name.setEnabled(false);
                contact.setEnabled(false);
                address.setEnabled(false);
                fruitsavoided.setEnabled(false);
                break;
        }
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {

            case Const.ServiceCode.UPDATE_CUSTOMER:
                Utility.removeSimpleProgressDialog();
                Log.i("URL LOGIN RESPONSE", response);
                if (parse.isSuccess(response)) {

                    try {
                        JSONObject obj = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_LONG).show();
                        finish();
                    } catch (Exception e) {

                    }
                }
        }
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

}
