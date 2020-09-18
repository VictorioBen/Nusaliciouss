package com.workspace.nusali.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.workspace.nusali.MainActivity;
import com.workspace.nusali.R;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText textEmail, textPassword;
    Button btnLogin;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    SharedPreferences sharedPreferences;
    String userIdKey = "";
    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final LoadingDialog loadingDialog = new LoadingDialog(LoginActivity.this);

        btnLogin = findViewById(R.id.btn_login);
        textEmail = findViewById(R.id.text_email_Login);
        textPassword = findViewById(R.id.text_password_login);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (currentUser != null) {
                    userId = currentUser.getUid();
                    sharedPreferences = getSharedPreferences(userIdKey, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("firebaseKey", userId);
                    editor.apply();
                    loadingDialog.startLoadingDialog();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismissDialog();
                        }
                    }, 4500);
                    Toast.makeText(LoginActivity.this, "Kamu sudah Masuk", Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(login);

                }
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = String.valueOf(textEmail.getText());
                String password = String.valueOf(textPassword.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Email tidak valid",
                            Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Password tidak valid",
                            Toast.LENGTH_LONG).show();
                }
                else if (!email.isEmpty() && !password.isEmpty()) {

                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Maaf Anda Gagal Masuk, Silahkan Ulangi", Toast.LENGTH_SHORT).show();
                                    } else {
                                        btnLogin.setText("Loading...");
                                        btnLogin.setEnabled(false);
                                        Intent login = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(login);
                                        finish();

                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(LoginActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}