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

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.model.PaymentDue_Bean;
import com.fruitland.fruitland.model.PaymentHistory_Bean;

import java.util.ArrayList;

public class PaymentDue_Adapter extends ArrayAdapter<PaymentHistory_Bean> {


	private Context mContext;
	private ArrayList<PaymentDue_Bean> mValues;

	public PaymentDue_Adapter(Context context, ArrayList<PaymentDue_Bean> list) {
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
		final PaymentDue_Bean hm = this.mValues.get(position);

		if (convertView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.each_paymentdue, null);
			view = convertView;
		} else {
			view = convertView;
		}

        View color = (View) view.findViewById(R.id.viewcolor);
        if(position==0||position==3||position==6||position==9){
            color.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.red_box_10leftradius));
        }else if(position==1||position==4||position==7||position==9){
            color.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.green_box_10leftradius));
        }else{
            color.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.yellow_box_10leftradius));
        }

	//	TextView name = (TextView) view.findViewById(R.id.docname);
	//	name.setText(hm.getName());

		return view;
	}

}