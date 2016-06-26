/**********************************************
 * Developer: Ankita Deshmukh
 **********************************************/

package com.fruitland.fruitland.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.activity.Add_Delivery;
import com.fruitland.fruitland.model.Customer_Bean;

import java.util.ArrayList;

public class Delivery_RecycleAddAdapter extends RecyclerView.Adapter<Delivery_RecycleAddAdapter.MyViewHolder> {


    private Context mContext;
    private ArrayList<Customer_Bean> mValues;
    Add_Delivery add_delivery;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, contact, address;
        View color;
        CheckBox checkboxsel;

        public MyViewHolder(View view) {
            super(view);


            color = (View) view.findViewById(R.id.viewcolor);
            name = (TextView) view.findViewById(R.id.name);
            contact = (TextView) view.findViewById(R.id.contact);
            address = (TextView) view.findViewById(R.id.address);
            checkboxsel = (CheckBox) view.findViewById(R.id.checkboxsel);


        }
    }


    public Delivery_RecycleAddAdapter(Context context, ArrayList<Customer_Bean> list, Add_Delivery add_delivery) {
        this.mContext = context;
        this.add_delivery = add_delivery;
        this.mValues = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_custdelivery, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Customer_Bean hm = this.mValues.get(position);
        if (hm.getPackages().equals("The Essentials")) {

            holder.color.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.red_box_10leftradius));
        } else if (hm.getPackages().equals("Grand Medley")) {
            holder.color.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.green_box_10leftradius));
        } else if (hm.getPackages().equals("Exotica")) {
            holder.color.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.yellow_box_10leftradius));
        }

        holder.name.setText(hm.getName());
        holder.contact.setText(hm.getContact());
        holder.address.setText(hm.getAddress());
        holder.checkboxsel.setOnCheckedChangeListener(null);

        //if true, your checkbox will be selected, else unselected
        if (hm.getChecked() == 1) {
            add_delivery.addItems(Integer.parseInt(hm.getCustomer_id()));
            holder.checkboxsel.setChecked(true);
        } else {
            holder.checkboxsel.setChecked(false);
        }


        holder.checkboxsel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //    hm.setChecked(isChecked);

                add_delivery.addItems(Integer.parseInt(hm.getCustomer_id()));
                if (isChecked)
                    mValues.get(holder.getAdapterPosition()).setChecked(1);

                else
                    mValues.get(holder.getAdapterPosition()).setChecked(0);


            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
