/**********************************************
 * Developer: Ankita Deshmukh
 
 **********************************************/

package com.fruitland.fruitland.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.fruitland.fruitland.R;

public class FruitsGrid_Adapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.apple, R.drawable.chikoo,
            R.drawable.peer, R.drawable.pomegranate,
            R.drawable.sweetlime, R.drawable.orange,
            R.drawable.muskmelon, R.drawable.papya,
            R.drawable.watermelon, R.drawable.litchi,
            R.drawable.jammon, R.drawable.mangoanim,
           };

    // Constructor
    public FruitsGrid_Adapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(mContext);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.each_fruit, null);
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.fruit);


            imageView.setImageDrawable(mContext.getResources().getDrawable(mThumbIds[position]));

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}