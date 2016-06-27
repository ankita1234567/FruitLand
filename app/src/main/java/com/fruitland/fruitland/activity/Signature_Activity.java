package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * Created by Admin on 4/3/2016.
 */
public class Signature_Activity extends Activity implements View.OnClickListener, VolleyCompleteListener {
    ImageView menu;
    TextView title;
    String delivery_id,paid="0";
    Button done, clear;
    GestureOverlayView gestureView;
    private Parse parse;
    Boolean sig = false;
    LinearLayout  ll;
    Bitmap bm=null;
    String bitmapstr="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signature);
        initialize();
    }

    private void initialize() {

        parse = new Parse(this);
        delivery_id = getIntent().getStringExtra("deliveryid");
        paid = getIntent().getStringExtra("paid");
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

        getImage();
        Utility.showSimpleProgressDialog(Signature_Activity.this, null, "Please Wait...", false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.URL, Const.ServiceType.DELIVERY_FRUIT);
        map.put(Const.Params.DELIVERYID, delivery_id);
        map.put(Const.Params.ISPAID, paid);
        map.put("image", bitmapstr);
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




    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void getImage(){
        ll=(LinearLayout)findViewById(R.id.signview);

        ViewTreeObserver observer = ll.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                bm = Bitmap.createBitmap(ll.getWidth(), ll.getHeight(), Bitmap.Config.ARGB_8888);
                //   Log.e("path::",""+v.getLayoutParams().width+ v.getLayoutParams().height);
                Canvas c = new Canvas(bm);
                ll.layout(0, 0, ll.getWidth(), ll.getHeight());
                ll.draw(c);
                ll.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
            }
        });
        bm = Bitmap.createBitmap(gestureView.getDrawingCache());
        bitmapstr=getStringImage(bm);

/*try{
        //   bm = Bitmap.createBitmap(gestureView.getDrawingCache());
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + "androidsignature.png");
        f.createNewFile();
        if (f.exists()) {
            Toast.makeText(Signature_Activity.this, "Signature Saved\nPath::" + f.toString(), Toast.LENGTH_LONG).show();
            Log.v("path::", f.toString());
            FileOutputStream os = new FileOutputStream(f);
            os = new FileOutputStream(f);
            //compress to specified format (PNG), quality - which is ignored for PNG, and out stream
            bm.compress(Bitmap.CompressFormat.PNG, 00, os);
            os.close();

        }

    } catch (Exception e) {
        // TODO: handle exception
        Log.v("Gestures", "ffff");
        e.printStackTrace();
    }*/
    }
}
