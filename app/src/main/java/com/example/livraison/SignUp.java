package com.example.livraison;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SignUp extends AppCompatActivity {

    EditText usn,mail,pass;
    Button btn;
    TextView lgin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("Sign up for delivery");

        usn = (EditText) findViewById(R.id.usernameS);
        mail = (EditText) findViewById(R.id.mail);
        pass = (EditText) findViewById(R.id.passwordS);
        btn = (Button) findViewById(R.id.signup);
        lgin = (TextView) findViewById(R.id.gotoLogin);
        final BaseDD bdd = new BaseDD(this);

        // Sign up
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usn.length()!=0 && mail.length()!=0 && pass.length()!=0) {
                    boolean isInserted = bdd.insertData(usn.getText().toString(), mail.getText().toString(), pass.getText().toString());
                    if (isInserted == true) {
                        // empty the fields
                        usn.setText("");
                        mail.setText("");
                        pass.setText("");
                        Toast.makeText(SignUp.this, "Account Created successfully!", Toast.LENGTH_LONG).show();
                        // account created Go to main activity
                        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

                    }else
                        Toast.makeText(SignUp.this, "ERROR!!", Toast.LENGTH_LONG).show();
                }

                else
                Toast.makeText(SignUp.this, "Please fill all fields!!", Toast.LENGTH_LONG).show();
            }
        });


        // Already have an account go to login page
        lgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });

    }
}
