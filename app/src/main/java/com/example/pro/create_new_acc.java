package com.example.pro;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class create_new_acc extends AppCompatActivity {

    String userName;
    String emailAddress;
    TextView oldacc;
    EditText new_username, new_email, new_pass, conf_pass;
    Button register_new_details;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog pd;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        oldacc = findViewById(R.id.alreadyhaveanaccount);
        new_username = findViewById(R.id.editTexta);
        new_email = findViewById(R.id.editTextb);
        new_pass = findViewById(R.id.editTextc);
        conf_pass = findViewById(R.id.editTextd);
        register_new_details = findViewById(R.id.register);
        pd = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        oldacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(create_new_acc.this, login.class));
            }
        });

        register_new_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
                FirestoreAPI f = FirestoreAPI.getInstance();;
                f.createUser(userName,emailAddress, 0, false, 0, "egp");
            }
        });
    }
    private void PerforAuth() {
        String username = new_username.getText().toString();
        String email = new_email.getText().toString();
        String pass = new_pass.getText().toString();
        String confpass = conf_pass.getText().toString();

        if (!email.matches(emailPattern)) {
            new_email.setError("Enter a Valid email pattern");
        } else if(pass.isEmpty() || pass.length() < 5 ) {
            new_pass.setError("Password length must be more than 5");
        } else if (!pass.equals(confpass)) {
            conf_pass.setError("The password you entered is not the same as above! Confirm the password");
        } else  {

            pd.setMessage("Please wait for registration");
            pd.setTitle("Registration");
            pd.setCanceledOnTouchOutside(false);
            pd.show();

            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        pd.dismiss();
                        sendUserbacktologinpage();
                        Toast.makeText(create_new_acc.this, "Signed Up Successfully", Toast.LENGTH_SHORT).show();
                        userName = username;
                        emailAddress = email;

                    } else {
                        pd.dismiss();
                        Toast.makeText(create_new_acc.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    private void sendUserbacktologinpage() {

        Intent intent = new Intent(create_new_acc.this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void createUser(String userame, String id, double usable_percentage, boolean notification, double budget, String currency) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference messageRef = db
                .collection("users").document("roomA")
                .collection("messages").document("message1");
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("userame", userame);
        user.put("uid", id);
        user.put("usable_percentage", usable_percentage);
        user.put("notification", notification);
        user.put("budget", budget);
        user.put("currency", currency);

        db.collection("users").document("test").collection("messages").add(user);
        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    private static final String TAG = "name";

                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    private static final String TAG = "name";

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}

