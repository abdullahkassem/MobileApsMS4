package com.example.pro;

import static android.content.ContentValues.TAG;
import static com.example.pro.R.layout.activity_add_transaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddTransaction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_add_transaction);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        PersonalSettings mySett = PersonalSettings.getInstance();

        EditText transName  = (EditText) findViewById(R.id.EnterTransName);
        Spinner transCategory  = (Spinner) findViewById(R.id.category_spinner);
        EditText transAmount  = (EditText) findViewById(R.id.Enteramount);

        Spinner chooseAcc = (Spinner) findViewById(R.id.account_spinner);

        ArrayList<String> CategoryList = new ArrayList<>(mySett.getCategories());

        CategoryList.add("Add Category");

        ArrayAdapter Categories_adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,CategoryList);
        transCategory.setAdapter(Categories_adapter);

        transCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == CategoryList.size()-1)
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(AddTransaction.this);

                    final EditText edittext = new EditText(AddTransaction.this);
                    alert.setMessage("Enter name of new Category");
                    alert.setTitle("Create new Category");

                    alert.setView(edittext);

                    alert.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String YouEditTextValue = edittext.getText().toString();
                            //CategoryList.add(0,YouEditTextValue);
                            Categories_adapter.remove("Add Category");
                            Categories_adapter.add(YouEditTextValue);
                            mySett.addCategory(YouEditTextValue);
                            CategoryList.add("Add Category");
                            //transCategory.setSelection(CategoryList.size()-2);


                            transCategory.setAdapter(Categories_adapter);
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();

                        }
                    });

                    alert.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter accountNames = new ArrayAdapter(this, android.R.layout.simple_spinner_item ,mySett.getAccNames());
        chooseAcc.setAdapter(accountNames);

        Button addTransaction = (Button) findViewById(R.id.finish_adding_trans);

        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String TransName = String.valueOf(transName.getText());
                String TransCategory = (String) transCategory.getSelectedItem();
                double Amount =  Double.parseDouble( transAmount.getText().toString());
                String AccName = (String) chooseAcc.getSelectedItem();
                //Log.i("TAG", "Spinner String is "+AccName);

                PersonalSettings p = PersonalSettings.getInstance();
                Transactions trans = new Transactions(TransName,Amount,TransCategory,Timestamp.now());

                p.addTransaction(trans,AccName);

                FirestoreAPI f = FirestoreAPI.getInstance();;
                Log.i(TAG, "onClick: adding transaction to account "+AccName);
                f.addTransaction(TransName,TransCategory,Timestamp.now(),Amount,AccName);

                Toast toast = Toast.makeText(getApplicationContext(), "Transaction Added", Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(AddTransaction.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(AddTransaction.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}