package com.example.pro;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Transactions {
    private String transname;
    private String date_time_str;
    private Timestamp timestamp;
    private double amount;
    private String category;
    private String imgURL;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getDate_time_str() {
        return date_time_str;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getTransname() {
        return transname;
    }

    public void setTransname(String transname) {
        this.transname = transname;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Transactions(String transname,  double amount, String category, Timestamp ts) {
        this.transname = transname;
        this.category = category;
        this.amount = amount;
        timestamp =ts;
        Date d = ts.toDate();
        //date_time_str = Integer.toString(d.getDate()) + "/" +Integer.toString(d.getDay()+1) + "/" + Integer.toString(d.getYear()) + " ";
        date_time_str = d.toGMTString();

        if(amount>0)
            this.imgURL = "drawable://" + R.drawable.plus;
        else
            this.imgURL = "drawable://" + R.drawable.minus_button;

    }

    public void createTransaction(String transname, String date, String amount, String category) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("TransactionName", transname);
        user.put("date", date);
        user.put("amount", amount);
        user.put("category", category);

        // Add a new document with a generated ID
        db.collection("Transactions")
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
