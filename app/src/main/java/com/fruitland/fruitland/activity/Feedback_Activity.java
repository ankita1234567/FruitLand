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
import com.fruitland.fruitland.adapter.Feedback_Adapter;
import com.fruitland.fruitland.model.Feedback_Bean;
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
public class Feedback_Activity extends Activity implements View.OnClickListener, VolleyCompleteListener {
    SimpleSideDrawer mNavv;
    ImageView menu;
    TextView title;
    FloatingActionButton addcustomer;
    ArrayList<Feedback_Bean> customer_list = new ArrayList<>();
    ListView list_customer;
    Feedback_Adapter customer_adapter;

    Parse parse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_feedback);
        initialize();
    }

    private void initialize() {

        parse = new Parse(this);
        title = (TextView) findViewById(R.id.title);
        title.setText("FEEDBACK DETAILS");
        MenuClass menuclass = new MenuClass();
        menuclass.simpleSlidingDrawer(this, "feedback", 6);
        list_customer = (ListView) findViewById(R.id.list_feedback);

      /*  for (int i = 0; i < 10; i++) {
            Feedback_Bean customer_bean = new Feedback_Bean();
            customer_list.add(customer_bean);
        }*/

        customer_adapter = new Feedback_Adapter(Feedback_Activity.this, customer_list);
        list_customer.setAdapter(customer_adapter);
        list_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Feedback_Details.class);
                intent.putExtra("feedbackdata",customer_list.get(position));
                startActivity(intent);
            }
        });

        getFeedbacks();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    private void getFeedbacks() {
        if (!Utility.isNetworkAvailable(this)) {
            Utility.showToast(
                    getResources().getString(R.string.dialog_no_inter_message),
                    this);
            return;
        }
        Utility.showSimpleProgressDialog(Feedback_Activity.this, null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL, Const.ServiceType.FEEDBACK);
        new MyVolleyClass(Feedback_Activity.this, map, Const.ServiceCode.FEEDBACK, this);
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {

            case Const.ServiceCode.FEEDBACK:
                Utility.removeSimpleProgressDialog();
                Log.i("URL LOGIN RESPONSE", response);
                if (parse.isSuccess(response)) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                Feedback_Bean customer_bean = new Feedback_Bean();
                                JSONObject objdata = jsonArray.getJSONObject(i);
                                customer_bean.setName(objdata.getString("name"));
                                customer_bean.setContact(objdata.getString("phone"));
                                customer_bean.setRating(objdata.getString("rating"));
                                customer_bean.setComment(objdata.getString("feedback"));
                         //       customer_bean.setPackages(objdata.getString("package"));
                                customer_list.add(customer_bean);

                            }
                            customer_adapter = new Feedback_Adapter(Feedback_Activity.this, customer_list);
                            list_customer.setAdapter(customer_adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }
}
