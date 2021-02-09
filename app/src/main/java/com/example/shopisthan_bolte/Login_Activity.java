package com.example.shopisthan_bolte;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.camera2.CameraManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hbb20.CountryCodePicker;

public class Login_Activity extends AppCompatActivity {

    WebView webView;

    private GoogleSignInClient mGoogleSignInClient;
    private Button signInButton, phonelog;
    private String TAG = "Login Activty";
    private FirebaseAuth mAuth;
    private int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        webView = findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alert_dialog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations =
                    android.R.style.Animation_Dialog;
            Button btTryAgain = dialog.findViewById(R.id.bt_try_again);
            btTryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            });
            dialog.show();
        } else {

            phonelog = (Button) findViewById(R.id.gopopup);
        phonelog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this, PhoneLoginActivity.class);
                startActivity(intent);
            }
        });

        signInButton = (Button) findViewById(R.id.google_signin);
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.mytoolbar);
        setActionBar(toolbar);
    }

}

        private void signIn () {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }
        private void handleSignInResult (Task < GoogleSignInAccount > completedTask) {
            try {
                GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
                Toast.makeText(Login_Activity.this, "Sign In Successfully", Toast.LENGTH_LONG).show();
                FirebaseGoogleAuth(acc);
            } catch (ApiException e) {
                Toast.makeText(Login_Activity.this, "Sign In Failed", Toast.LENGTH_LONG).show();
                FirebaseGoogleAuth(null);
            }
        }
        private void FirebaseGoogleAuth (GoogleSignInAccount acct){
            AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login_Activity.this, "Successfull", Toast.LENGTH_LONG).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                        Intent intent = new Intent(Login_Activity.this, Home_page.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login_Activity.this, "Failed", Toast.LENGTH_LONG).show();
                        updateUI(null);
                    }
                }
            });
        }
        private void updateUI (FirebaseUser fuser)
        {
        }
        private void setActionBar (Toolbar toolbar)
        {
        }
        @Override
        protected void onStart () {
            super.onStart();

            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                startActivity(new Intent(Login_Activity.this, Home_page.class));
                finish();
            }
        }
    }

