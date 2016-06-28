package com.fruitland.fruitland.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.activity.PaymentHistoryDetails_Activity;
import com.fruitland.fruitland.adapter.PaymentHistory_Adapter;
import com.fruitland.fruitland.model.PaymentHistory_Bean;
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

/**
 * Created by Admin on 5/11/2016.
 */
public class PaymentHistory_Fragment extends Fragment implements VolleyCompleteListener {
    View rootView;
    ArrayList<PaymentHistory_Bean> customer_list = new ArrayList<>();
    ListView list_payment;
    PaymentHistory_Adapter customer_adapter;
    Parse parse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_paymenthistory, container, false);
        initialize();
        return rootView;
    }

    private void initialize() {
        parse = new Parse(getActivity());
        list_payment = (ListView) rootView.findViewById(R.id.list_payment);

     /*   for (int i = 0; i < 10; i++) {
            PaymentHistory_Bean paymentHistory_bean = new PaymentHistory_Bean();
            customer_list.add(paymentHistory_bean);
        }

        customer_adapter = new PaymentHistory_Adapter(getActivity(), customer_list);
        list_payment.setAdapter(customer_adapter);
     */   list_payment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PaymentHistoryDetails_Activity.class);
                intent.putExtra("id",customer_list.get(position).getCustomer_id());
                startActivity(intent);
            }
        });

        getCustomersPaymentHistory();
    }


    private void getCustomersPaymentHistory() {
        if (!Utility.isNetworkAvailable(getActivity())) {
            Utility.showToast(getActivity().
                            getResources().getString(R.string.dialog_no_inter_message),
                    getActivity());
            return;
        }
        Utility.showSimpleProgressDialog(getActivity(), null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL, Const.ServiceType.PAYMENT_HISTORY);
        new MyVolleyClass(getActivity(), map, Const.ServiceCode.PAYMENT_HISTORY, this);
    }


    @Override
    public void onTaskCompleted(String response, int serviceCode) {

        switch (serviceCode) {

            case Const.ServiceCode.PAYMENT_HISTORY:
                Utility.removeSimpleProgressDialog();
                Log.i("URL LOGIN RESPONSE", response);
                if (parse.isSuccess(response)) {

                    try {
                        JSONObject obj = new JSONObject(response);
                        JSONArray jsonArray = obj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            PaymentHistory_Bean paymentHistory_bean = new PaymentHistory_Bean();
                            JSONObject objdata = jsonArray.getJSONObject(i);
                            paymentHistory_bean.setName(objdata.getString("name"));
                            paymentHistory_bean.setCustomer_id(objdata.getString("id"));
                            paymentHistory_bean.setAmount(objdata.getString("last_paid_amount"));

                            paymentHistory_bean.setPackages(objdata.getString("package"));

                            customer_list.add(paymentHistory_bean);

                        }
                        customer_adapter = new PaymentHistory_Adapter(getActivity(), customer_list);
                        list_payment.setAdapter(customer_adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }
}
