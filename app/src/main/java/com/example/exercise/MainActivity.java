package com.example.exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText inputPassword;
    ImageView loginView;
    TextView greeting;
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    long animationDuration=3500;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getApplicationContext().getSharedPreferences("Preferences",0);
        editor=sharedPreferences.edit();
        if(!sharedPreferences.contains("password")){
            editor.putString("password","admin");
            editor.commit();
        }
        inputPassword = (EditText)findViewById(R.id.password);
        loginView= (ImageView)findViewById(R.id.loginImage);
        loginView.setImageResource(R.drawable.tome_of_secrets);
        greeting=(TextView)findViewById(R.id.greeting);
        HandleAnimation(loginView);
        HandleAnimation(greeting);
        configureLoginButton();
    }
    private void configureLoginButton(){
        Button loginButton = (Button)findViewById(R.id.buttonNextActivity);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validatePassword()){
                startActivity(new Intent(MainActivity.this, ListEntriesActivity.class));
            }
            }
        });
    }
    private boolean validatePassword() {
        String passwordIn = inputPassword.getText().toString();
        if (passwordIn.equals(sharedPreferences.getString("password",null)))
        {
            return true;
        }
        return false;
    }
    public void HandleAnimation(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,View.ALPHA,0.0f,1.0f);
        animator.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();
    }
}

