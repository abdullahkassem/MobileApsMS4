package com.example.pro;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.DropDownPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreferenceCompat;

public class SettingsActivity extends AppCompatActivity {

    SettingsFragment settFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            settFrag = new SettingsFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, settFrag)
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        Button new_acc_button = (Button) findViewById(R.id.create_acc_button);
        new_acc_button.setOnClickListener(view -> {
            Intent i = new Intent(SettingsActivity.this, create_account.class);
            startActivity (i);
        });

        Button Save_button = (Button) findViewById(R.id.save_button);
        Save_button.setOnClickListener(view-> {
            settFrag.SavePreferences();
            Toast.makeText(getApplicationContext(), "Account updated", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(SettingsActivity.this, Mainmenu_Activity.class);
            startActivity (i);
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(SettingsActivity.this, Mainmenu_Activity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        EditTextPreference username_pref;
        EditTextPreference email_pref ;
        SwitchPreferenceCompat enable_noti;
        SeekBarPreference usablePercentage ;
        DropDownPreference currency_select ;
        EditTextPreference budget_pref;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            budget_pref = (EditTextPreference) findPreference("Budget_pref");
            if (budget_pref != null) {
                budget_pref.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
                    @Override
                    public void onBindEditText(@NonNull EditText editText) {
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

                    }
                });

            }

            username_pref = (EditTextPreference) findPreference("Username_pref");
            email_pref = (EditTextPreference) findPreference("Email_pref");
            enable_noti = (SwitchPreferenceCompat) findPreference("notifications_pref");
            usablePercentage = (SeekBarPreference) findPreference("usable_percentage_seekbar");
            currency_select = (DropDownPreference) findPreference("currency_selector");

            PersonalSettings mysett = PersonalSettings.getInstance();
            username_pref.setText(mysett.getUserName());
            email_pref.setText(mysett.getEmail());
            enable_noti.setChecked(mysett.isNotifications());
            usablePercentage.setValue( (int) mysett.getUsablePercentage() );
            currency_select.setValueIndex(mysett.getCurrIndex());
            budget_pref.setText( Double.toString(mysett.getBudget()) );

        }

        void SavePreferences(){
            String username;
            String email;
            boolean notifications_switch;
            double budg;
            double percent;
            String curr;

            username = username_pref.getText();
            email = email_pref.getText();
            notifications_switch = enable_noti.isChecked();
            budg = Double.parseDouble( budget_pref.getText());
            percent = usablePercentage.getValue();
            curr = currency_select.getEntry().toString();
            Log.d("TAG", "SavePreferences: curr is "+ curr);

            PersonalSettings s = PersonalSettings.getInstance();
            s.setPersonalSettings(username,email,notifications_switch,budg,percent,curr);


            FirestoreAPI f = FirestoreAPI.getInstance();
            f.createUser(f.getMy_UID(),username,email, percent, notifications_switch, budg, curr);
        }
    }
}