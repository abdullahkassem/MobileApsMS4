package com.example.pro;

import static android.content.ContentValues.TAG;
import static java.lang.Thread.sleep;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;


public class login extends AppCompatActivity {

    Button signup, login;
    EditText emailin, passin;
    boolean b = false;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog pd;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ImageView googlebtn;
    int counter = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        PersonalSettings p = PersonalSettings.getInstance();
        p.setPossibleCurrencies(getResources().getStringArray(R.array.currency_names));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup = findViewById(R.id.button2);
        login = findViewById(R.id.button);
        emailin = findViewById(R.id.editText2);
        passin = findViewById(R.id.editText);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        pd = new ProgressDialog(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, create_new_acc.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performlogin();
            }
        });



        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    // intent call to second activity
                    Log.i("login", "onAuthStateChanged: User now logged in and UID is " + FirebaseAuth.getInstance().getCurrentUser().getUid());


                }
            }
        };

        mAuth.addAuthStateListener(mAuthListener);



    }

    private void performlogin() {

        String emaillogin = emailin.getText().toString();
        String passlogin = passin.getText().toString();


        if (!emaillogin.matches(emailPattern)) {
            emailin.setError("Enter a Valid email pattern");
        } else if(passlogin.isEmpty() || passlogin.length() < 5 ) {
            passin.setError("Password length must be more than 5");
        }  else  {

            pd.setMessage("Please wait for login ");
            pd.setTitle("Login");
            pd.setCanceledOnTouchOutside(false);
            pd.show();


            mAuth.signInWithEmailAndPassword(emaillogin,passlogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        pd.dismiss();
                        sendUsertonextpage();
                        //Toast.makeText(login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        pd.dismiss();
                        Toast.makeText(login.this, "Login Failed"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    private void sendUsertonextpage() {

        FirestoreAPI f = FirestoreAPI.getInstance();
        f.LoadData(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ProgressDialog nDialog;
        nDialog = new ProgressDialog(login.this);
        nDialog.setMessage("Loading..");
        nDialog.setTitle("Getting Data From database");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();



        Handler handler=new Handler();
        Runnable updateTextRunnable=new Runnable(){
            public void run() {
                if(FirestoreAPI.isData_loaded()) {
                    Log.i(TAG, "run: Will dismiss nDialog and move on");
                    nDialog.dismiss();
                    Intent intent = new Intent(login.this, Mainmenu_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    //Log.i(TAG, "run: isdataLoaded "+ FirestoreAPI.isData_loaded());
                    return;
                }

                Log.i(TAG, "run: Data is not loaded yet");
                counter++;
                if(counter > 80) {
                    nDialog.dismiss();
                    Toast.makeText(login.this, "Cant Load Data", Toast.LENGTH_LONG).show();
                    return;
                }
                handler.postDelayed(this, 200);
            }
        };

        handler.post(updateTextRunnable);



    }
}

