package com.example.livraison;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    EditText usn,pass;
    Button btn;
    TextView sgup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login for delivery");

        usn = (EditText) findViewById(R.id.usernameL);
        pass = (EditText) findViewById(R.id.passwordL);
        btn = (Button) findViewById(R.id.login);
        sgup = (TextView) findViewById(R.id.gotoSignup);
        final BaseDD bdd = new BaseDD(this);

        //Login
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isExist = bdd.checkUserExist(usn.getText().toString(), pass.getText().toString());

                if(isExist){
                    Toast.makeText(Login.this, "Logged in successfully!", Toast.LENGTH_LONG).show();
                    // account created Go to main activity
                    Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, "Username of password is wrong!", Toast.LENGTH_LONG).show();
                    usn.setText("");
                    pass.setText("");
                }
            }
        });

        // don't have an account? go to sign up page
        sgup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
            }
        });
    }
}
