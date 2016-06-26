package com.fruitland.fruitland.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fruitland.fruitland.R;

/**
 * Created by Admin on 3/25/2016.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    Button btn_login;
    ImageView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_login);
        initialize();
    }

    private void initialize() {
        menu = (ImageView) findViewById(R.id.expanded_menu);
        menu.setVisibility(View.INVISIBLE);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
