package com.fruitland.fruitland.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.activity.Create_Basket;

/**
 * Created by Admin on 6/19/2016.
 */
public class FruitsRecycler_Adapter extends RecyclerView.Adapter<FruitsRecycler_Adapter.MyViewHolder> {
    public Integer[] mThumbIds = {
            R.drawable.apple, R.drawable.peer,
            R.drawable.pomegranate, R.drawable.chikoo,
            R.drawable.sweetlime, R.drawable.orange,
            R.drawable.muskmelon, R.drawable.papya,
            R.drawable.watermelon, R.drawable.litchi,
            R.drawable.jammon, R.drawable.mango,
    };

    Create_Basket create_basket;

    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public CheckBox fruitCheck;

        public MyViewHolder(View view) {
            super(view);

            imageView = (ImageView) view
                    .findViewById(R.id.fruit);
            fruitCheck = (CheckBox) view
                    .findViewById(R.id.fruitcheck);


        }
    }


    public FruitsRecycler_Adapter(Context mContext, Create_Basket create_basket) {
        this.mContext = mContext;
        this.create_basket = create_basket;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_fruit, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(mThumbIds[position]));

        holder.fruitCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(create_basket.selectedfruits.size()>=5){
                        buttonView.setChecked(false);
                        Toast.makeText(mContext,"You can select maximum 5 fruits",Toast.LENGTH_LONG).show();
                    }else{
                        create_basket.addFruit(position,mThumbIds[position].toString());
                    }



                }else{
                    create_basket.addFruit(position,mThumbIds[position].toString());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return 12;
    }
}

