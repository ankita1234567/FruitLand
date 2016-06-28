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
import com.fruitland.fruitland.model.Customer_Bean;
import com.fruitland.fruitland.model.PaymentHistoryDetails_Bean;

import java.util.ArrayList;

public class PaymentHistoryDetails_Adapter extends ArrayAdapter<Customer_Bean> {


	private Context mContext;
	private ArrayList<PaymentHistoryDetails_Bean> mValues;

	public PaymentHistoryDetails_Adapter(Context context, ArrayList<PaymentHistoryDetails_Bean> list) {
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
		final PaymentHistoryDetails_Bean hm = this.mValues.get(position);

		if (convertView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.each_paymenthistorydetails, null);
			view = convertView;
		} else {
			view = convertView;
		}

        View color = (View) view.findViewById(R.id.viewcolor);
        if(position==0||position==3||position==6||position==9){
            color.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.red_box_10leftradius));
        }else if(position==1||position==4||position==7){
            color.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.green_box_10leftradius));
        }else{
            color.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.yellow_box_10leftradius));
        }

		TextView date = (TextView) view.findViewById(R.id.date);
        date.setText(hm.getDate());
        TextView amount = (TextView) view.findViewById(R.id.amount);
        amount.setText("Amount Paid Rs "+hm.getAmount());

		return view;
	}

}
