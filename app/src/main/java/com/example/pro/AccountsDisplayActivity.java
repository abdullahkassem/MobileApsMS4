package com.example.pro;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import java.util.ArrayList;

public class AccountsDisplayActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    PersonalSettings mysetting;
    String []str_arr;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_display);

        mListView = (ListView) findViewById(R.id.listView1_account);

        mysetting = PersonalSettings.getInstance();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Your Accounts");




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.accounts_display, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

//        mysetting.addAccount(new FinancialAccount("Cash",0));
//        mysetting.addAccount(new FinancialAccount("Bank",0));

        str_arr = mysetting.getAccountsName();
        if(str_arr==null)
        {
            Log.i("TAG", "onCreateOptionsMenu: str_arr is null");
            str_arr = new String[]{"no accounts"};
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,str_arr
                );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);




        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        PersonalSettings mysett = PersonalSettings.getInstance();
        ArrayList<Transactions> transactionsList = mysett.getTransByAccount(str_arr[i]);
        TransactionsListAdapter adapter = new TransactionsListAdapter(this, R.layout.activity_list, transactionsList);
        mListView.setAdapter(adapter);

        Log.i("TAG", " item selected is " + str_arr[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}