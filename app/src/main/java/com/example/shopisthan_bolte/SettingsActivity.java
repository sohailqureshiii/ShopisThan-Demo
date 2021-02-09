package com.example.shopisthan_bolte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {


    TextInputLayout InputShopName ,InputShopType ,InputShopCategory ,InputShopPincode,InputShopDescription,InputShopGst;
    Button CreatShop,successful,viewshop, pro;
    ProgressDialog loadingBar;
    String  shopname, shopType, shopcategory, shopPincode ,shopdescription ,GSTno;
    String uid;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CreatShop = (Button) findViewById(R.id.shopcreat);
        viewshop =(Button) findViewById(R.id.viewourshop);
        pro = (Button) findViewById(R.id.product);
        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,AdminAddNewProductActivity.class);
                startActivity(intent);
            }
        });

        viewshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,EditProfile.class);
                startActivity(intent);
            }
        });

        CreatShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewContactDialog();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),Home_page.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.store:
                        return true;

                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(),FavActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.settings:

                        return true;

                }
                return false;
            }
        });
    }



    public void createNewContactDialog(){

        dialogBuilder= new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.shopedetailspop,null);


        successful = (Button) contactPopupView.findViewById(R.id.creatsuccessfull);
        InputShopName = (TextInputLayout)  contactPopupView.findViewById(R.id.shopname);
        InputShopType = (TextInputLayout) contactPopupView.findViewById(R.id.shopType);
        InputShopCategory = (TextInputLayout)  contactPopupView.findViewById(R.id.shopcategory);
        InputShopPincode = (TextInputLayout)contactPopupView.findViewById(R.id.shopPincode);
        InputShopDescription = (TextInputLayout)contactPopupView.findViewById(R.id.shopdescription);
        InputShopGst = (TextInputLayout)contactPopupView.findViewById(R.id.GSTno);


        loadingBar = new ProgressDialog(this);

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        successful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateShop();
            }
            private void CreateShop() {

                shopname =  InputShopName.getEditText().getText().toString();
                shopType = InputShopType.getEditText().getText().toString();
                shopcategory = InputShopCategory.getEditText().getText().toString();
                shopPincode = InputShopPincode.getEditText().getText().toString();
                shopdescription =InputShopDescription.getEditText().getText().toString();
                GSTno =InputShopGst.getEditText().getText().toString();

                if (TextUtils.isEmpty(shopname))
                {
                    Toast.makeText(SettingsActivity.this, "Please write your Shopname...", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(shopType))
                {
                    Toast.makeText(SettingsActivity.this, "Please write your ShopType...", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(shopcategory))
                {
                    Toast.makeText(SettingsActivity.this, "Please write your Shop Category...", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(shopPincode))
                {
                    Toast.makeText(SettingsActivity.this, "Please write your Shop Pincode...", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(shopdescription))
                {
                    Toast.makeText(SettingsActivity.this, "Please write your Shopdescription...", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(GSTno))
                {
                    Toast.makeText(SettingsActivity.this, "Please write your GST No...", Toast.LENGTH_SHORT).show();
                }

                else {

                    loadingBar.setTitle("Create Shop");
                    loadingBar.setMessage("Please wait, while we are checking the credentials.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    StoreInfo();

                }
            }
            private void StoreInfo() {

                FirebaseAuth mAuht;

                mAuht = FirebaseAuth.getInstance();
                uid=mAuht.getCurrentUser().getUid();

                DatabaseReference Reference= FirebaseDatabase.getInstance().getReference().child("Store").child(uid);
                HashMap<String, Object> userdataMap = new HashMap<>();
                userdataMap.put("SName", shopname);
                userdataMap.put("SType", shopType);
                userdataMap.put("SCategory", shopcategory);
                userdataMap.put("SPincode", shopPincode);
                userdataMap.put("SDescription", shopdescription);
                userdataMap.put("SGSTNo", GSTno);


                Reference.updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(SettingsActivity.this, "Created Store Succesfully", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(SettingsActivity.this, Home_page.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(SettingsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
            }
        });
    }
}