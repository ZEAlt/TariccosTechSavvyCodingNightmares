package com.example.zeynepalptekin.tariccostechsavvycodingnightmares;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * class that allows the user to log into an existing account
 * or allow user to continue onto an activity that will allow
 * the user to create an account
 */
public class LoginScreen extends AppCompatActivity {
    /**
     * account of the user
     */
    Account a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        Button button = findViewById(R.id.createAccountButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCreateAccount();
            }
        });

        Button button2 = findViewById(R.id.loginButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        Button button3 = findViewById(R.id.backToMain3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain();
            }
        });
    }

    /**
     * logs the user into a pre-existing account by
     * pulling accounts from firebase and looking for
     * matching emails and passwords
     */
    public void login() {
        EditText user = findViewById(R.id.emailEdit);
        final String email = user.getText().toString();
        Log.d("email","email from box: " + email);

        EditText pass = findViewById(R.id.passwordEdit);
        final String password = pass.getText().toString();
        Log.d("password","password from box: " + password);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference usersRef = database.getReference("users");
        Log.d("firebase","firebase referenced");


        /**
         * pulls accounts from firebase and checks if the account email and password
         * match the user-entered email and password
         */
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Account account;
                for (DataSnapshot newAccount : dataSnapshot.getChildren()) {
                    Log.d("loop","yes");
                    account = newAccount.getValue(Account.class);
                    Log.d("each account retrieved","most recent account: " + account.getEmail());
                    if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
                        Log.d("email", account.getEmail());
                        a = account;
                        backToMain();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

}

    /**
     * brings user to the FirstScreen class
     */
    public void clickCreateAccount() {
        Log.d("clickCreateAccount","clickCreateAccount is running");
        Intent intent = new Intent(this, FirstScreen.class);
        startActivity(intent);
    }

    /**
     * brings the user back to the main menu
     * passes the account information of the user back to the main menu
     */
    public void backToMain() {
        Intent intent = new Intent(this,MainActivity.class);
        if(a != null) {
            String[] account = {a.getName(),a.getEmail(),a.getLocation().getTown(),
            a.getLocation().getState(),a.getPassword()};
            intent.putExtra("account",account);
        }
        startActivity(intent);
    }
}
