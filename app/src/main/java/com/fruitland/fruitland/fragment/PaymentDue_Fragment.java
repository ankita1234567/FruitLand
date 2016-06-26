package com.fruitland.fruitland.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.activity.CustomerDetailsActivity;
import com.fruitland.fruitland.adapter.PaymentDue_Adapter;
import com.fruitland.fruitland.model.PaymentDue_Bean;

import java.util.ArrayList;

/**
 * Created by Admin on 5/11/2016.
 */
public class PaymentDue_Fragment extends Fragment {
    View rootView;
    ArrayList<PaymentDue_Bean> customer_list = new ArrayList<>();
    ListView list_payment;
    PaymentDue_Adapter customer_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_paymenthistory, container, false);
        initialize();
        return rootView;
    }

    private void initialize() {
        list_payment = (ListView) rootView.findViewById(R.id.list_payment);

        for (int i = 0; i < 10; i++) {
            PaymentDue_Bean paymentHistory_bean = new PaymentDue_Bean();
            customer_list.add(paymentHistory_bean);
        }

        customer_adapter = new PaymentDue_Adapter(getActivity(), customer_list);
        list_payment.setAdapter(customer_adapter);
        list_payment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CustomerDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}
