package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fruitland.fruitland.R;
import com.fruitland.fruitland.utils.Const;
import com.fruitland.fruitland.utils.MyVolleyClass;
import com.fruitland.fruitland.utils.Parse;
import com.fruitland.fruitland.utils.Utility;
import com.fruitland.fruitland.utils.VolleyCompleteListener;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Admin on 4/27/2016.
 */
public class Message_Activity extends Activity implements View.OnClickListener, VolleyCompleteListener {

    ImageView menu;
    TextView title;
    Button clear, done;
    Parse parse;
    EditText desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initialize();
    }

    private void initialize() {
        parse = new Parse(this);
        title = (TextView) findViewById(R.id.title);
        title.setText("SEND MESSAGE");
        MenuClass menuclass = new MenuClass();
        menuclass.simpleSlidingDrawer(this, "message", 7);

        clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(this);
        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(this);
        desc = (EditText) findViewById(R.id.desc);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear:
                desc.setText("");
                break;
        }
    }


    private void getCustomersDelivery() {
        if (!Utility.isNetworkAvailable(this)) {
            Utility.showToast(
                    getResources().getString(R.string.dialog_no_inter_message),
                    this);
            return;
        }
        Utility.showSimpleProgressDialog(Message_Activity.this, null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL, Const.ServiceType.CUSTOMER_REGIONWISE);

        new MyVolleyClass(Message_Activity.this, map, Const.ServiceCode.CUSTOMER_REGIONWISE, this);
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {

            case Const.ServiceCode.CUSTOMER_REGIONWISE:
                Utility.removeSimpleProgressDialog();
                Log.e("%%%%%%%%%", response);
                if (parse.isSuccess(response)) {

                    try {
                        JSONObject obj = new JSONObject(response);

                    } catch (Exception e) {

                    }

                }
        }
    }
}
