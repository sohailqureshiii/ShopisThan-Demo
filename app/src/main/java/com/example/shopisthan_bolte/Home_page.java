package com.example.shopisthan_bolte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class
Home_page extends AppCompatActivity  {

    private RecyclerView recyclerView;
    RecyclerView newrecyclerView;

    ArrayList<MainModel> mainModels;
    MainAdapter mainAdapter;

    RecyclerView phoneRecycler,categories;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ImageSlider imageSlider = findViewById(R.id.slider);

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.homebanner));
        slideModels.add(new SlideModel(R.drawable.homebanner));
        slideModels.add(new SlideModel(R.drawable.homebanner));
        slideModels.add(new SlideModel(R.drawable.homebanner));
        slideModels.add(new SlideModel(R.drawable.homebanner));
        imageSlider.setImageList(slideModels,true);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:

                        return true;

                    case R.id.store:
                        return true;

                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(),FavActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        newrecyclerView = findViewById(R.id.recycler_view);

        Integer[] langLogo = {R.drawable.appliancescategory,R.drawable.bookscategory,R.drawable.fashioncategory,R.drawable.furniturecategory,
                R.drawable.handcraftcategory,R.drawable.shoescategory,R.drawable.sportscategory,R.drawable.toyscategory};

        String[] langName = {"APPLIANCES","BOOKS","FASHION","FURNITURE","HANDCRAFT","SHOES","SPORTS","TOYS"};

        mainModels  = new ArrayList<>();
        for (int i =0;i<langLogo.length;i++){
            MainModel model = new MainModel(langLogo[i],langName[i]);
            mainModels.add(model);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                Home_page.this,LinearLayoutManager.HORIZONTAL,false
        );
        newrecyclerView.setLayoutManager(layoutManager);
        newrecyclerView.setItemAnimator(new DefaultItemAnimator());

        mainAdapter = new MainAdapter(Home_page.this,mainModels);
        newrecyclerView.setAdapter(mainAdapter);

    }
}
