package com.example.exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangePassword extends AppCompatActivity {

    EditText newPasswordIn;
    Button savePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        newPasswordIn= (EditText)findViewById(R.id.newPassword);
        configureSaveButton();
    }
    private void configureSaveButton(){
        savePassword=(Button)findViewById(R.id.addNewPassword);
        savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListEntriesActivity.setPassword(newPasswordIn.getText().toString());
                finish();
            }
        });
    }
}
