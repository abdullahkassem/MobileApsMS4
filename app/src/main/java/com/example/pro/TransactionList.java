package com.example.pro;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TransactionList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);
        Log.d(TAG, "onCreate: Started.");
        ListView mListView = (ListView) findViewById(R.id.listView1);



        //Create the Transactions objects
        /*
        Transactions john = new Transactions("John","12-20-1998","Male",
                getResources().getIdentifier("@drawable/cartman_cop", null,this.getPackageName()));
                */

//        Transactions john = new Transactions("mobile phone","12-20-1998","10000",
//                "category", "drawable://" + R.drawable.plus);
//        Transactions steve = new Transactions("quick","08-03-1987","50",
//                "category", "drawable://" + R.drawable.minus_button);
//        Transactions stacy = new Transactions("mcdonalds","11-15-2000","200",
//                "category", "drawable://" + R.drawable.plus);
//        Transactions ashley = new Transactions("exmp5","07-02-1999","400",
//                "category", "drawable://" + R.drawable.minus_button);
//        Transactions matt = new Transactions("exmp4","03-29-2001","100",
//                "category", "drawable://" + R.drawable.minus_button);
//        Transactions matt2 = new Transactions("exmp3","03-29-2001","1000",
//                "category", "drawable://" + R.drawable.minus_button);
//        Transactions matt3 = new Transactions("exmp2","03-29-2001","10",
//                "category", "drawable://" + R.drawable.minus_button);
//        Transactions matt4 = new Transactions("exmp1","03-29-2001","60",
//                "category", "drawable://" + R.drawable.minus_button);
//
//
//
//        //Add the Transactions objects to an ArrayList
        ArrayList<Transactions> transactionsList = new ArrayList<>();
//        transactionsList.add(john);
//        transactionsList.add(steve);
//        transactionsList.add(stacy);
//        transactionsList.add(ashley);
//        transactionsList.add(matt);
//        transactionsList.add(matt2);
//        transactionsList.add(matt3);
//        transactionsList.add(matt4);
//        transactionsList.add(john);
//        transactionsList.add(steve);
//        transactionsList.add(stacy);
//        transactionsList.add(ashley);
//        transactionsList.add(matt);
//        transactionsList.add(matt2);
//        transactionsList.add(matt3);
//        transactionsList.add(matt4);
//        transactionsList.add(john);
//        transactionsList.add(steve);
//        transactionsList.add(stacy);
//        transactionsList.add(ashley);
//        transactionsList.add(matt);
//        transactionsList.add(matt2);
//        transactionsList.add(matt3);
//        transactionsList.add(matt4);
//        transactionsList.add(john);
//        transactionsList.add(steve);
//        transactionsList.add(stacy);
//        transactionsList.add(ashley);
//        transactionsList.add(matt);
//        transactionsList.add(matt2);
//        transactionsList.add(matt3);
//        transactionsList.add(matt4);
//        transactionsList.add(john);
//        transactionsList.add(steve);
//        transactionsList.add(stacy);
//        transactionsList.add(ashley);
//        transactionsList.add(matt);
//        transactionsList.add(matt2);
//        transactionsList.add(matt3);
//        transactionsList.add(matt4);
//        transactionsList.add(john);
//        transactionsList.add(steve);
//        transactionsList.add(stacy);
//        transactionsList.add(ashley);
//        transactionsList.add(matt);
//        transactionsList.add(matt2);
//        transactionsList.add(matt3);
//        transactionsList.add(matt4);
//        transactionsList.add(john);
//        transactionsList.add(steve);
//        transactionsList.add(stacy);
//        transactionsList.add(ashley);
//        transactionsList.add(matt);
//        transactionsList.add(matt2);
//        transactionsList.add(matt3);
//        transactionsList.add(matt4);
//        transactionsList.add(john);
//        transactionsList.add(steve);
//        transactionsList.add(stacy);
//        transactionsList.add(ashley);
//        transactionsList.add(matt);
//        transactionsList.add(matt2);
//        transactionsList.add(matt3);
//        transactionsList.add(matt4);
//        transactionsList.add(john);
//        transactionsList.add(steve);
//        transactionsList.add(stacy);
//        transactionsList.add(ashley);
//        transactionsList.add(matt);
//        transactionsList.add(matt2);
//        transactionsList.add(matt3);
//        transactionsList.add(matt4);
//        transactionsList.add(john);
//        transactionsList.add(steve);
//        transactionsList.add(stacy);
//        transactionsList.add(ashley);
//        transactionsList.add(matt);
//        transactionsList.add(matt2);
//        transactionsList.add(matt3);
//        transactionsList.add(matt4);
//        transactionsList.add(john);
//        transactionsList.add(steve);
//        transactionsList.add(stacy);
//        transactionsList.add(ashley);
//        transactionsList.add(matt);
//        transactionsList.add(matt2);
//        transactionsList.add(matt3);
//        transactionsList.add(matt4);
//        transactionsList.add(john);
//        transactionsList.add(steve);
//        transactionsList.add(stacy);
//        transactionsList.add(ashley);
//        transactionsList.add(matt);
//        transactionsList.add(matt2);
//        transactionsList.add(matt3);
//        transactionsList.add(matt4);


        TransactionsListAdapter adapter = new TransactionsListAdapter(this, R.layout.activity_list, transactionsList);
        mListView.setAdapter(adapter);
    }
}