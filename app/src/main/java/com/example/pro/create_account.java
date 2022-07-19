package com.example.pro;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class create_account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        EditText enterName  = (EditText) findViewById(R.id.EnterAccName);
        EditText enterBal  = (EditText) findViewById(R.id.EnterBal);

        Button createAcc = (Button) findViewById(R.id.finish_creating_acc);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String AccName = String.valueOf(enterName.getText());
                double Bal =  Double.parseDouble( enterBal.getText().toString());
                PersonalSettings p = PersonalSettings.getInstance();
                FinancialAccount acc = new FinancialAccount(AccName,Bal);
                p.addAccount(acc);

                FirestoreAPI f = FirestoreAPI.getInstance();;
                f.addAccount(Bal,AccName);

                Toast toast = Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(create_account.this, SettingsActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}