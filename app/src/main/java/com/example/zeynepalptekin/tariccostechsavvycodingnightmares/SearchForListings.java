package com.example.zeynepalptekin.tariccostechsavvycodingnightmares;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchForListings extends AppCompatActivity {

    Account a;
    String[] parameters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_listings);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            String[] account = bundle.getStringArray("account");
            a = new Account(account[0],account[1],account[2],account[3],account[4]);
        }

        Button backToMain = findViewById(R.id.backToMain1);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain();
            }
        });

        Button searchListings = findViewById(R.id.searchButton);
        searchListings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parameters = getParameters();
                viewListings();
            }
        });
    }

    public String[] getParameters(){
        String[] params = new String[2];

        //adds the type to the arraylist of parameters
        EditText text = findViewById(R.id.typeEdit);
       String str = text.getText().toString();
       if(str.isEmpty()) str = "none";
        params[0] = str;

        //adds the max price to the arraylist of parameters
        text = findViewById(R.id.maxPrice);
        str = text.getText().toString();
        if(str.isEmpty()) str = "none";
        params[1] = str;

        return params;

    }

    public void backToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        if(a != null){
            String[] account = {a.getName(),a.getEmail(),a.getLocation().getTown(),a.getLocation().getState(),
            a.getPassword()};
            intent.putExtra("account",account);
        }
        startActivity(intent);
    }

    public void viewListings(){
        Intent intent = new Intent(this, ListingsView.class);
        if(a != null){
            String[] account = {a.getName(),a.getEmail(),a.getLocation().getTown(),a.getLocation().getState(),
                    a.getPassword()};
            intent.putExtra("account",account);
        }
        if(parameters!=null) intent.putExtra("parameters",parameters);
        startActivity(intent);
    }
}
