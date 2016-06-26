package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fruitland.fruitland.R;
import com.navdrawer.SimpleSideDrawer;

/**
 * Created by Nanostuffs on 20-01-2016.
 */
public class MenuClass implements View.OnClickListener {

    Activity mActivity;

    SimpleSideDrawer mNav;
    ImageView menu;
    String fromActivity;
    LinearLayout ifLogin, visitLay;
    LinearLayout nav_home, nav_delivery, nav_payment, nav_weeklybasket, nav_purchase, nav_feedback, nav_sendmsg, nav_sendnoti;
    Button nav_settings, nav_logout, nav_login;
    TextView name, position, textweekly, textpayment, texthome, textdelivery, textpurchase, textfeedback, textmessage, textnotification;


    public void simpleSlidingDrawer(Activity context, String from, int sel_pos) {
        fromActivity = from;
        mActivity = context;
        mNav = new SimpleSideDrawer(context);


        mNav.setLeftBehindContentView(R.layout.drawer_layout);

        getIDs();
        disableClick();
        switch (sel_pos) {
            case 1:
                texthome.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.yellow_box_leftradius));
                texthome.setTextColor(mActivity.getResources().getColor(android.R.color.black));
                texthome.setCompoundDrawablesWithIntrinsicBounds(R.drawable.home_black, 0, 0, 0);

                break;
            case 2:
                textdelivery.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.yellow_box_leftradius));
                textdelivery.setTextColor(mActivity.getResources().getColor(android.R.color.black));
                textdelivery.setCompoundDrawablesWithIntrinsicBounds(R.drawable.delivery_black, 0, 0, 0);

                break;
            case 3:
                textpayment.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.yellow_box_leftradius));
                textpayment.setTextColor(mActivity.getResources().getColor(android.R.color.black));
                textpayment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.payment_black, 0, 0, 0);
                break;
            case 4:
                textweekly.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.yellow_box_leftradius));
                textweekly.setTextColor(mActivity.getResources().getColor(android.R.color.black));
                textweekly.setCompoundDrawablesWithIntrinsicBounds(R.drawable.weklybasket_black, 0, 0, 0);
                break;
            case 5:
                textpurchase.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.yellow_box_leftradius));
                textpurchase.setTextColor(mActivity.getResources().getColor(android.R.color.black));
                textpurchase.setCompoundDrawablesWithIntrinsicBounds(R.drawable.purchase_black, 0, 0, 0);
                break;
            case 6:
                textfeedback.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.yellow_box_leftradius));
                textfeedback.setTextColor(mActivity.getResources().getColor(android.R.color.black));
                textfeedback.setCompoundDrawablesWithIntrinsicBounds(R.drawable.viewfeedback_black, 0, 0, 0);
                break;
            case 7:
                textmessage.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.yellow_box_leftradius));
                textmessage.setTextColor(mActivity.getResources().getColor(android.R.color.black));
                textmessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.send_feedback_black, 0, 0, 0);
                break;
            case 8:
                textnotification.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.yellow_box_leftradius));
                textnotification.setTextColor(mActivity.getResources().getColor(android.R.color.black));
                textnotification.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sendnotifications_black, 0, 0, 0);
                break;
        }
        menu = (ImageView) context.findViewById(R.id.expanded_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav.toggleLeftDrawer();
               enableClick();
            }
        });


    }

    private void getIDs() {

        nav_home = (LinearLayout) mNav.findViewById(R.id.nav_home);
        nav_delivery = (LinearLayout) mNav.findViewById(R.id.nav_delivery);
        nav_payment = (LinearLayout) mNav.findViewById(R.id.nav_payment);
        nav_weeklybasket = (LinearLayout) mNav.findViewById(R.id.nav_weeklybasket);
        nav_purchase = (LinearLayout) mNav.findViewById(R.id.nav_purchase);
        nav_feedback = (LinearLayout) mNav.findViewById(R.id.nav_feedback);
        nav_sendmsg = (LinearLayout) mNav.findViewById(R.id.nav_sendmsg);
        nav_sendnoti = (LinearLayout) mNav.findViewById(R.id.nav_sendnoti);

        nav_home.setOnClickListener(this);
        nav_delivery.setOnClickListener(this);
        nav_payment.setOnClickListener(this);
        nav_weeklybasket.setOnClickListener(this);
        nav_purchase.setOnClickListener(this);
        nav_feedback.setOnClickListener(this);
        nav_sendmsg.setOnClickListener(this);
        nav_sendnoti.setOnClickListener(this);

        textweekly = (TextView) mNav.findViewById(R.id.textweekly);
        textpayment = (TextView) mNav.findViewById(R.id.textpayment);
        texthome = (TextView) mNav.findViewById(R.id.texthome);
        textdelivery = (TextView) mNav.findViewById(R.id.textdelivery);
        textpurchase = (TextView) mNav.findViewById(R.id.textpurchase);
        textfeedback = (TextView) mNav.findViewById(R.id.textfeedback);
        textmessage = (TextView) mNav.findViewById(R.id.textmessage);
        textnotification = (TextView) mNav.findViewById(R.id.textnotification);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_home:
                if (fromActivity.equalsIgnoreCase("Home")) {
                    mNav.toggleLeftDrawer();
                } else {
                    mNav.closeLeftSide();
                    Intent intent = new Intent(mActivity, HomeActivity.class);
                    mActivity.startActivity(intent);
                    mActivity.finish();
                }

                break;
            case R.id.nav_delivery:
                mNav.closeLeftSide();
                Intent intent1 = new Intent(mActivity, Delivery_Activity.class);
                mActivity.startActivity(intent1);
                mActivity.finish();

                break;
            case R.id.nav_payment:
                mNav.closeLeftSide();
                Intent intent2 = new Intent(mActivity, Payment_Activity.class);
                mActivity.startActivity(intent2);
                mActivity.finish();

                break;
            case R.id.nav_weeklybasket:

                mNav.closeLeftSide();
                Intent intent3 = new Intent(mActivity, Create_Basket.class);
                mActivity.startActivity(intent3);
                mActivity.finish();

                break;

            case R.id.nav_purchase:
                mNav.closeLeftSide();
                Intent intent4 = new Intent(mActivity, Purchase_Details.class);
                mActivity.startActivity(intent4);
                mActivity.finish();


                break;
            case R.id.nav_feedback:
                mNav.closeLeftSide();
                Intent intent5 = new Intent(mActivity, Feedback_Activity.class);
                mActivity.startActivity(intent5);
                mActivity.finish();
                break;


            case R.id.nav_sendmsg:
                mNav.closeLeftSide();
                Intent intent6 = new Intent(mActivity, Message_Activity.class);
                mActivity.startActivity(intent6);
                mActivity.finish();
                break;

            case R.id.nav_sendnoti:
                mNav.closeLeftSide();
                Intent intent7 = new Intent(mActivity, Message_Activity.class);
                mActivity.startActivity(intent7);
                mActivity.finish();
                break;


        }

    }

    private void disableClick(){
        nav_home.setClickable(false);
        nav_delivery.setClickable(false);
        nav_payment.setClickable(false);
        nav_weeklybasket.setClickable(false);
        nav_purchase.setClickable(false);
        nav_feedback.setClickable(false);
        nav_sendmsg.setClickable(false);
        nav_sendnoti.setClickable(false);
    }

    private void enableClick(){
        nav_home.setClickable(true);
        nav_delivery.setClickable(true);
        nav_payment.setClickable(true);
        nav_weeklybasket.setClickable(true);
        nav_purchase.setClickable(true);
        nav_feedback.setClickable(true);
        nav_sendmsg.setClickable(true);
        nav_sendnoti.setClickable(true);
    }
}

