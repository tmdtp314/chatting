package com.example.chatpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class autoLogin extends AppCompatActivity {
private FirebaseAuth firebaseAuth;
private FirebaseAuth.AuthStateListener authStateListener;
private String RoomID;
private String password;
private FirebaseRemoteConfig firebaseRemoteConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_login);

        RoomID = "a";//번들1
        password="123456";//번들2
        firebaseRemoteConfig=FirebaseRemoteConfig.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        loginEvent();
//로그인 인터페이스 리스너
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
Toast.makeText(autoLogin.this,"로그인",Toast.LENGTH_SHORT).show();
                } else {

                }


            }};
    }
            void loginEvent() {
                firebaseAuth.signInWithEmailAndPassword(RoomID, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //로그인 확인되었는지만 알려주는 메소드 로그인 안되었을 때만 작동
                        if (!task.isSuccessful()) {
                            Toast.makeText(autoLogin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}