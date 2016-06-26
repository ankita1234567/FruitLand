package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.adapter.CreateBasket_Adapter;
import com.fruitland.fruitland.model.CreateBasket_Bean;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 3/25/2016.
 */
public class CreateBasketListActivity extends Activity implements View.OnClickListener,VolleyCompleteListener {
    SimpleSideDrawer mNavv;
    ImageView menu;
    TextView title;
    FloatingActionButton addcustomer;
    ArrayList<CreateBasket_Bean> customer_list = new ArrayList<>();
    ListView list_customer;
    CreateBasket_Adapter customer_adapter;
    Parse parse;;
    String fruitstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_basketlist);
        initialize();
    }

    private void initialize() {

        fruitstr=getIntent().getStringExtra("fruitstr");
        parse=new Parse(this);
        title=(TextView)findViewById(R.id.title);
        title.setText("WEEKLY BASKET");


        menu = (ImageView) findViewById(R.id.expanded_menu);
        menu.setImageDrawable(getResources().getDrawable(R.drawable.back));
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        }); 

        list_customer = (ListView) findViewById(R.id.list_createbasket);
        createBasket();
      /*  for (int i = 0; i < 10; i++) {
            CreateBasket_Bean customer_bean = new CreateBasket_Bean();
            customer_list.add(customer_bean);
        }

        customer_adapter = new CreateBasket_Adapter(CreateBasketListActivity.this, customer_list);
        list_customer.setAdapter(customer_adapter);
        list_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CustomerDetailsActivity.class);
                startActivity(intent);
            }
        });*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


    private void createBasket() {
        if (!Utility.isNetworkAvailable(this)) {
            Utility.showToast(
                    getResources().getString(R.string.dialog_no_inter_message),
                    this);
            return;
        }
        Utility.showSimpleProgressDialog(CreateBasketListActivity.this, null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL, Const.ServiceType.CREATE_BASKET);

        map.put("fruit_ids", fruitstr);
        new MyVolleyClass(CreateBasketListActivity.this, map, Const.ServiceCode.CREATE_BASKET, this);
    }
    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        Log.e("%%%%%%", response);
        switch (serviceCode) {

            case Const.ServiceCode.CREATE_BASKET:
                Log.e("RRRRRRRR", response);

                if (parse.isSuccess(response)) {

                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray jsonArray = obj.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            CreateBasket_Bean customer_bean = new CreateBasket_Bean();
                            JSONObject objdata = jsonArray.getJSONObject(i);
                            customer_bean.setName(objdata.getString("name"));
                            customer_bean.setAddress(objdata.getString("address"));
                            String fruitsavoided=objdata.getString("avoided_fruits");
                            List<String> items = Arrays.asList(fruitsavoided.split("\\s*,\\s*"));
                            if(items.size()==1){
                                customer_bean.setFruit1(items.get(0));
                                customer_bean.setFruit2("No");
                                customer_bean.setFruit3("No");
                                customer_bean.setFruit4("No");
                                customer_bean.setFruit5("No");
                            }
                            if(items.size()==2){
                                customer_bean.setFruit1(items.get(0));
                                customer_bean.setFruit2(items.get(1));
                                customer_bean.setFruit3("No");
                                customer_bean.setFruit4("No");
                                customer_bean.setFruit5("No");
                            }
                            if(items.size()==3){
                                customer_bean.setFruit1(items.get(0));
                                customer_bean.setFruit2(items.get(1));
                                customer_bean.setFruit3(items.get(2));
                                customer_bean.setFruit4("No");
                                customer_bean.setFruit5("No");
                            }
                            if(items.size()==4){
                                customer_bean.setFruit1(items.get(0));
                                customer_bean.setFruit2(items.get(1));
                                customer_bean.setFruit3(items.get(2));
                                customer_bean.setFruit4(items.get(3));
                                customer_bean.setFruit5("No");
                            }
                            if(items.size()==5){
                                customer_bean.setFruit1(items.get(0));
                                customer_bean.setFruit2(items.get(1));
                                customer_bean.setFruit3(items.get(2));
                                customer_bean.setFruit4(items.get(3));
                                customer_bean.setFruit5(items.get(4));
                            }


                            customer_list.add(customer_bean);

                        }
                        customer_adapter = new CreateBasket_Adapter(CreateBasketListActivity.this, customer_list);
                        list_customer.setAdapter(customer_adapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                }
        }
    }
}
