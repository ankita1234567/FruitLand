package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.utils.Const;
import com.fruitland.fruitland.utils.MyVolleyClass;
import com.fruitland.fruitland.utils.Parse;
import com.fruitland.fruitland.utils.Utility;
import com.fruitland.fruitland.utils.VolleyCompleteListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Admin on 4/3/2016.
 */
public class Signature_Activity extends Activity implements View.OnClickListener, VolleyCompleteListener {
    ImageView menu;
    TextView title;
    String delivery_id;
    Button done, clear;
    GestureOverlayView gestureView;
    private Parse parse;
    Boolean sig = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signature);
        initialize();
    }

    private void initialize() {

        parse = new Parse(this);
        delivery_id = getIntent().getStringExtra("deliveryid");

        title = (TextView) findViewById(R.id.title);
        title.setText("SIGNATURE");
        menu = (ImageView) findViewById(R.id.expanded_menu);
        menu.setImageDrawable(getResources().getDrawable(R.drawable.back));
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        done = (Button) findViewById(R.id.done);
        clear = (Button) findViewById(R.id.clear);
        done.setOnClickListener(this);
        clear.setOnClickListener(this);
        gestureView = (GestureOverlayView) findViewById(R.id.signaturePad);
        gestureView.setDrawingCacheEnabled(true);
        gestureView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                sig = true;
                findViewById(R.id.signHint).setVisibility(View.INVISIBLE);
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done:
                deliver();
                //finish();
                break;

            case R.id.clear:
                sig = false;
                gestureView.cancelClearAnimation();
                gestureView.clear(true);
                //   finish();
                break;
        }
    }

    private void deliver() {
        if (!Utility.isNetworkAvailable(this)) {
            Utility.showToast(
                    getResources().getString(R.string.dialog_no_inter_message),
                    this);
            return;
        }
        Utility.showSimpleProgressDialog(Signature_Activity.this, null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL, Const.ServiceType.DELIVERY_FRUIT);
        map.put(Const.Params.DELIVERYID, delivery_id);
        map.put(Const.Params.ISPAID, "0");

        new MyVolleyClass(Signature_Activity.this, map, Const.ServiceCode.DELIVERY_FRUIT, this);
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {

            case Const.ServiceCode.DELIVERY_FRUIT:
                Utility.removeSimpleProgressDialog();
                Log.e("%%%%%%%%%", response);
                if (parse.isSuccess(response)) {

                    try {
                        JSONObject obj = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getApplicationContext(),Pending_Delivery.class);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
        }
    }
}
