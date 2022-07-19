package com.example.pro;//package tabian.com.listadapter;

//import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started.");
        ListView mListView = (ListView) findViewById(R.id.listView1);

       FloatingActionButton add_transaction_button;
        add_transaction_button = findViewById(R.id.floating_action_button);
        add_transaction_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddTransaction.class));
            }
        });

        //Create the Transactions objects


//        Transactions john = new Transactions("mobile phone","12-20-1998","10000",
//                "category", "drawable://" + R.drawable.plus);
//        Transactions steve = new Transactions("quick","08-03-1987","50",
//                "category", "drawable://" + R.drawable.minus_button);


//        //Add the Transactions objects to an ArrayList

        PersonalSettings mysett = PersonalSettings.getInstance();
        Log.i(TAG, "onCreate: user name " + mysett.UserName);
        ArrayList<Transactions> transactionsList = mysett.getAllTrans();
        Log.d(TAG, "onCreate: mysett.getAllTrans() list size is " + mysett.getAllTrans().size());

//        transactionsList.add(john);
//        transactionsList.add(steve);



        TransactionsListAdapter adapter = new TransactionsListAdapter(this, R.layout.activity_list, transactionsList);
        mListView.setAdapter(adapter);
    }
}