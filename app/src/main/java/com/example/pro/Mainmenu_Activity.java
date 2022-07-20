package com.example.pro;

import static android.content.ContentValues.TAG;

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



        Handler progressBarHandler = new Handler();
        progressBarHandler.post(new Runnable() {
            public void run() {

                ProgressValue = mysettings.getProgress();
                if(Double.isNaN(ProgressValue)) {
                    Log.i(TAG, "onCreate: Progress Value is Nan");
                    ProgressValue=0;
                }
                Log.i(TAG, "Thread Progress value is "+ProgressValue);
                progressBar.setProgress((int)ProgressValue);
                percentagetxt.setText(String.format("%s %%", progressBar.getProgress()));
                progressBarHandler.postDelayed(this, 500);
            }
        });


        //Log.i("TAG", "onCreate: Progress Value is "+ progressBar.getProgress());

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
            Intent i = new Intent(this, AccountsDisplayActivity.class);
            startActivity(i);
        }else if(id == R.id.price_suggestions)
        {
            Intent i = new Intent(this, AnalysisActivity.class);
            startActivity(i);
        }
    }
}