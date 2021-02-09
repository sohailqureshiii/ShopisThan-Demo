package com.example.shopisthan_bolte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;

public class PhoneLoginActivity extends AppCompatActivity {
    CountryCodePicker ccp;
    EditText t1;
    Button b1;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        t1 = (EditText)  findViewById(R.id.t1);
        ccp = (CountryCodePicker)  findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(t1);
        b1 = (Button)  findViewById(R.id.b1);
        back = (ImageView) findViewById(R.id.backlogin);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhoneLoginActivity.this,Login_Activity.class);
                startActivity(intent);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhoneLoginActivity.this, managotp.class);
                intent.putExtra("mobile", ccp.getFullNumberWithPlus().replace(" ", ""));
                startActivity(intent);
            }
        });

    }
}