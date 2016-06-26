/**********************************************
 * Developer: Ankita Deshmukh
 
 **********************************************/

package com.fruitland.fruitland.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.model.CreateBasket_Bean;

import java.util.ArrayList;

public class CreateBasket_Adapter extends ArrayAdapter<CreateBasket_Bean> {

	private final Drawable[] mFruitsPictures;
	private Context mContext;
	private ArrayList<CreateBasket_Bean> mValues;

	public CreateBasket_Adapter(Context context, ArrayList<CreateBasket_Bean> list) {
		super(context, R.layout.activity_home);
		this.mContext = context;
		Resources resources = context.getResources();
		this.mValues = list;
		TypedArray a = resources.obtainTypedArray(R.array.fruits_picture);
		mContext = context;
		mFruitsPictures = new Drawable[a.length()];
		for (int i = 0; i < mFruitsPictures.length; i++) {
			mFruitsPictures[i] = a.getDrawable(i);
		}
		a.recycle();

	}

	@Override
	public int getCount() {

		return mValues.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View view;
		Activity activity = (Activity) this.mContext;
		final CreateBasket_Bean hm = this.mValues.get(position);

		if (convertView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.each_basket, null);
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

		TextView name = (TextView) view.findViewById(R.id.name);
		name.setText(hm.getName());
		TextView address = (TextView) view.findViewById(R.id.address);
		address.setText(hm.getAddress());


		ImageView fruit1=(ImageView)view.findViewById(R.id.fruit_1);
		ImageView fruit2=(ImageView)view.findViewById(R.id.fruit_2);
		ImageView fruit3=(ImageView)view.findViewById(R.id.fruit_3);
		ImageView fruit4=(ImageView)view.findViewById(R.id.fruit_4);
		ImageView fruit5=(ImageView)view.findViewById(R.id.fruit_5);

		if(!hm.getFruit1().equals("No")){
			fruit1.setImageDrawable(mFruitsPictures[Integer.parseInt(hm.getFruit1()) - 1]);
		}else{
			fruit1.setImageDrawable(null);
		}
		if(!hm.getFruit2().equals("No")){
			fruit2.setImageDrawable(mFruitsPictures[Integer.parseInt(hm.getFruit2()) - 1]);
		}else{
			fruit2.setImageDrawable(null);
		}
		if(!hm.getFruit3().equals("No")){
			fruit3.setImageDrawable(mFruitsPictures[Integer.parseInt(hm.getFruit3()) - 1]);
		}else{
			fruit3.setImageDrawable(null);
		}
		if(!hm.getFruit4().equals("No")) {
			fruit4.setImageDrawable(mFruitsPictures[Integer.parseInt(hm.getFruit4()) - 1]);
		}else{
			fruit4.setImageDrawable(null);
		}
		if(!hm.getFruit5().equals("No")){
			fruit5.setImageDrawable(mFruitsPictures[Integer.parseInt(hm.getFruit5()) - 1]);
		}else{
			fruit5.setImageDrawable(null);
		}

		return view;
	}

}
