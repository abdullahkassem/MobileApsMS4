package com.example.pro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;

public class Mainmenu_Activity extends AppCompatActivity implements View.OnClickListener {

    PersonalSettings mysettings;
    double ProgressValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        mysettings = PersonalSettings.getInstance();

        ImageButton preferences_button = (ImageButton) findViewById(R.id.Prefrences_button);
        ImageButton transactions_button = (ImageButton) findViewById(R.id.transactionsButton);
        ImageButton accounts_button = (ImageButton) findViewById(R.id.accounts_button);
        ImageButton suggestions_button = (ImageButton) findViewById(R.id.price_suggestions);



        preferences_button.setOnClickListener(this);
        transactions_button.setOnClickListener(this);
        accounts_button.setOnClickListener(this);
        suggestions_button.setOnClickListener(this);

        ProgressBar progressBar= (ProgressBar) findViewById(R.id.progressBar);
        TextView percentagetxt = (TextView) findViewById(R.id.Progress_percentage_view);

        ProgressValue = mysettings.CalculateMoneyRemaining() / mysettings.Budget;
        if(Double.isNaN(ProgressValue)) {
            ProgressValue=99;
        }

        progressBar.setProgress((int)ProgressValue);

        Handler progressBarHandler = new Handler();
        progressBarHandler.post(new Runnable() {
            public void run() {
                progressBar.setProgress((int)ProgressValue);
            }
        });


        Log.i("TAG", "onCreate: Progress Value is "+ progressBar.getProgress());
        percentagetxt.setText(String.format("%s %%", progressBar.getProgress()));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.Prefrences_button) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        } else if(id == R.id.transactionsButton)
        {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        else if(id == R.id.accounts_button)
        {
//            FirestoreAPI f = FirestoreAPI.getInstance();;
//            f.createUser("andrew","andrew@auc.edu", 0, false, 0, "egp");
//            f.addAccount(10,10,10,"New");
//            f.addTransaction("supermarket","food", Timestamp.now(),100,"New");

            Intent i = new Intent(this, AccountsDisplayActivity.class);
            startActivity(i);
        }else if(id == R.id.price_suggestions)
        {
            Toast toast2 = Toast.makeText(getApplicationContext(), "Sorry, This feature is not Implemented\n Yet!!", Toast.LENGTH_SHORT);
            toast2.show();
        }
    }
}