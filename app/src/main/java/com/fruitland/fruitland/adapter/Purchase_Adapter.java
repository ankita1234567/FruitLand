/**********************************************
 * Developer: Ankita Deshmukh
 **********************************************/

package com.fruitland.fruitland.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.model.Purchase_Bean;

import java.util.ArrayList;

public class Purchase_Adapter extends ArrayAdapter<Purchase_Bean> {


    private Context mContext;
    private ArrayList<Purchase_Bean> mValues;

    public Purchase_Adapter(Context context, ArrayList<Purchase_Bean> list) {
        super(context, R.layout.activity_home);
        this.mContext = context;
        this.mValues = list;

    }

    @Override
    public int getCount() {

        return mValues.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view;
        Activity activity = (Activity) this.mContext;
        final Purchase_Bean hm = this.mValues.get(position);

        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.each_purchase, null);
            view = convertView;
        } else {
            view = convertView;
        }

        TextView date = (TextView) view.findViewById(R.id.date);
        TextView total = (TextView) view.findViewById(R.id.total);
        date.setText(hm.getDate());
        total.setText("? "+hm.getTotal());


        return view;
    }

}
