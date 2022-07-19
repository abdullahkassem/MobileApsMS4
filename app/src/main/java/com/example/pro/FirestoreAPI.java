package com.example.pro;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirestoreAPI {

    private static FirestoreAPI ourInstance = new FirestoreAPI();

    public String my_UID;
    DocumentReference myUserDoc;
    PersonalSettings mysettings;
    private static boolean data_loaded;

    private FirestoreAPI()
    {
        my_UID = "N/A";
        data_loaded = false;
    }

    public static boolean isData_loaded() {
        Log.i(TAG, "isData_loaded: Data loaded is "+data_loaded);
        return data_loaded;
    }

     static void dataIsLoaded(){
        if(!data_loaded) {
            Log.i(TAG, "isData_loaded The Data has been loaded");
            data_loaded = true;
        }

    }

    public static FirestoreAPI getInstance()
    {
        return ourInstance;
    }

    void LoadData(String s){    //Load user Data from Database
        this.my_UID = s;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("users").whereEqualTo("uid",my_UID);
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i(TAG,"user document id: "+ document.getId() + " data: " + document.getData());
                                InitializePersonalSettings(document.getData());
                                myUserDoc = document.getReference();
                                ReadAccounts();
                            }
                        } else {
                            Log.i(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void createUser(String userame, String emailAddress, double usablePercentage, boolean notification, double budget, String currency) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

//        double expense = 0.0;
//        double income = 0.0;
        double outstandingBalance = 0.0;

        Map<String, Object> cash_account = new HashMap<>();
//        cash_account.put("Expense", expense);
//        cash_account.put("Income", income);
        cash_account.put("OutstandingBalance", outstandingBalance);
        cash_account.put("account name", "cash");

        db.collection("users")
                .document(my_UID)
                .collection("accounts")
                .document("cash")
                .set(cash_account)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

        Map<String, Object> credit_account = new HashMap<>();
//        credit_account.put("Expense", expense);
//        credit_account.put("Income", income);
        credit_account.put("OutstandingBalance", outstandingBalance);
        credit_account.put("account name", "credit");

        db.collection("users")
                .document(my_UID)
                .collection("accounts")
                .document("credit")
                .set(credit_account)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });



        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("budget", budget);
        user.put("currency", currency);
        user.put("email address", emailAddress);
        user.put("notification", notification);
        user.put("uid", my_UID);
        user.put("usable percentage", usablePercentage);
        user.put("username", userame);

        // Add a new document with a generated ID
        db.collection("users")
                .document(my_UID)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
//        }
    }

    void addAccount(double outstandingBalance, String accountName){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> accounts = new HashMap<>();
//        accounts.put("Expense", expense);
//        accounts.put("Income", income);
        accounts.put("OutstandingBalance", outstandingBalance);
        accounts.put("account name", accountName);

        db.collection("users")
                .document(my_UID)
                .collection("accounts")
                .document(accountName)
                .set(accounts)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    void addTransaction(String transactionName, String category, Timestamp date, double amount, String account) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> trans = new HashMap<>();
        trans.put("transaction name", transactionName);
        trans.put("category", category);
        trans.put("date", date);
        trans.put("amount", amount);

        db.collection("users")
                .document(my_UID)
                .collection("accounts")
                .document(account)
                .collection("transactions")
                .add(trans)
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


    void deleteTransaction(String docID, String account) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(my_UID)
                .collection("accounts")
                .document(account)
                .collection("transactions")
                .document(docID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    void InitializePersonalSettings(Map<String,Object> userInformationMap)
    {
        String name = (String) userInformationMap.get("username");
        boolean notifications = (boolean) userInformationMap.get("notification");
        double percent =   (Double) userInformationMap.get("usable percentage");
        String email = (String) userInformationMap.get("email address");
        String curr = (String) userInformationMap.get("currency");
        double budget =(double) userInformationMap.get("budget");
        //Log.i(TAG, "InitializePersonalSettings: budget read is " + budget);


        mysettings = PersonalSettings.getInstance();
        mysettings.setPersonalSettings(name,email,notifications,budget,percent,curr);

    }

    FinancialAccount createFinAcc(Map<String,Object> accountMap)
    {
        String Accname = (String) accountMap.get("account name");
        double OutstandingBalance =(double) accountMap.get("OutstandingBalance");
//        double Expense =(double) accountMap.get("Expense") ;
//        double Income =(double) accountMap.get("Income") ;

        Log.i(TAG, "createFinAcc: outBal read is " + OutstandingBalance);

        FinancialAccount f = new FinancialAccount(Accname,OutstandingBalance);

        return f;
    }

    void ReadAccounts(){    //Reads all the user's accounts


        myUserDoc.collection("accounts").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i(TAG, "document in collection with id " + document.getId() + " => " + document.getData());
                                //for every account
                                FinancialAccount f= createFinAcc(document.getData());
                                readTransactions(f,document.getReference().collection("transactions"));
                                mysettings.addAccount(f);

                            }
                        } else {
                            Log.i(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    void readTransactions(FinancialAccount f, CollectionReference c){   //reads all transactions in account

        c.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i(TAG, "transaction in collection with id " + document.getId() + " => " + document.getData());
                                //for every transaction
                                Transactions t = createTransObj(document.getData());

//                                if(f==null)
//                                    Log.e(TAG, "onComplete: f is null " );
//                                else
//                                    Log.i(TAG, "onComplete: f is not null " );
//
//                                if(t==null)
//                                    Log.e(TAG, "onComplete: t is null " );
//                                else
//                                    Log.i(TAG, "onComplete: t is not null " );

                                f.addTransaction(t);


                            }
                        } else {
                            Log.i(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        c.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                         @Override
                                         public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                             Log.i(TAG, "onSuccess: Transaction Succefully read");
                                             FirestoreAPI.dataIsLoaded();
                                         }
                                     }

        );
    }

    Transactions createTransObj(Map<String,Object> TransMap)
    {
        String Transname = (String) TransMap.get("transaction name");
        double amount =(double) TransMap.get("amount");
        String category =(String) TransMap.get("category");
        Timestamp date_ts = (Timestamp) TransMap.get ("date");



        String date = date_ts.toString();

        Log.i(TAG, "createFinAcc: date read is " + date);

        Transactions t = new Transactions(Transname,amount,category,date_ts);

        return t;
    }




    ArrayList<Transactions> getTransactionByAccount(){return null;}

    ArrayList<Transactions> getTransactionByUser(){return null;}

}
