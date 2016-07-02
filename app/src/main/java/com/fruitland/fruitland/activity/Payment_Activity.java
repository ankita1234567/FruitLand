package com.fruitland.fruitland.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.adapter.Customer_Adapter;
import com.fruitland.fruitland.fragment.PaymentDue_Fragment;
import com.fruitland.fruitland.fragment.PaymentHistory_Fragment;
import com.fruitland.fruitland.model.Customer_Bean;
import com.navdrawer.SimpleSideDrawer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 3/25/2016.
 */
public class Payment_Activity extends AppCompatActivity implements View.OnClickListener {
    SimpleSideDrawer mNavv;
    ImageView menu;
    TextView title;
    FloatingActionButton addcustomer;
    ArrayList<Customer_Bean> customer_list = new ArrayList<>();
    ListView list_customer;
    Customer_Adapter customer_adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_payment);
        initialize();
    }

    private void initialize() {

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        title=(TextView)findViewById(R.id.title);
        title.setText("PAYMENT");


        MenuClass menuclass=new MenuClass();
        menuclass.simpleSlidingDrawer(this,"payment",3);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
          }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PaymentHistory_Fragment(), "Payment History");
        adapter.addFragment(new PaymentDue_Fragment(), "Payment Due");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

