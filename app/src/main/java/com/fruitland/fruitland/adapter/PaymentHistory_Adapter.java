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
import com.fruitland.fruitland.model.PaymentHistory_Bean;

import java.util.ArrayList;

public class PaymentHistory_Adapter extends ArrayAdapter<PaymentHistory_Bean> {


	private Context mContext;
	private ArrayList<PaymentHistory_Bean> mValues;

	public PaymentHistory_Adapter(Context context, ArrayList<PaymentHistory_Bean> list) {
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
		final PaymentHistory_Bean hm = this.mValues.get(position);

		if (convertView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.each_paymenthistory, null);
			view = convertView;
		} else {
			view = convertView;
		}


        View color = (View) view.findViewById(R.id.viewcolor);
       /* if(position==0||position==3||position==6||position==9){
            color.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.red_box_10leftradius));
        }else if(position==1||position==4||position==7||position==9){
            color.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.green_box_10leftradius));
        }else{
            color.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.yellow_box_10leftradius));
        }
       */ if(hm.getPackages().equals("The Essentials")){

            color.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.red_box_10leftradius));
        }else if(hm.getPackages().equals("Grand Medley")){
            color.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.green_box_10leftradius));
        }else if(hm.getPackages().equals("Exotica")){
            color.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.yellow_box_10leftradius));
        }

        TextView name = (TextView) view.findViewById(R.id.name);
		name.setText(hm.getName());

        TextView lastpaymentamnt = (TextView) view.findViewById(R.id.lastpaymentamnt);
        lastpaymentamnt.setText("Last Payment: Rs "+hm.getAmount());

        return view;
	}

}
