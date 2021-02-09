package com.example.shopisthan_bolte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditProfile extends AppCompatActivity {

   private EditText name, address, email, phone;
    private String Name, Address, Email, PhoneNo;
   private Button edit;
   private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name = (EditText) findViewById(R.id.editname);
        address = (EditText) findViewById(R.id.editAddress);
        email = (EditText) findViewById(R.id.editemail);
        phone = (EditText) findViewById(R.id.editphoneNumber);
        edit = (Button) findViewById(R.id.editdone);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit();
            }
        });
    }

    private void Edit() {

        Name =  name.getText().toString();
        Address = address.getText().toString();
        Email = email.getText().toString();
        PhoneNo = phone.getText().toString();

        Storeinfo();
    }

    private void Storeinfo()
    {

        FirebaseAuth mAuht;

        mAuht = FirebaseAuth.getInstance();
        uid=mAuht.getCurrentUser().getUid();

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("Profile");
        HashMap<String, Object> userdataMap = new HashMap<>();
        userdataMap.put("name", Name);
        userdataMap.put("address", Address);
        userdataMap.put("email", Email);
        userdataMap.put("phoneno", PhoneNo);

        databaseReference.child(uid).updateChildren(userdataMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(EditProfile.this, "Product is added successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(EditProfile.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}