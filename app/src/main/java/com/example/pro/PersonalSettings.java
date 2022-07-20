package com.example.pro;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.nfc.cardemulation.CardEmulation;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonalSettings implements Serializable {

    //Singleton Class
    private static final PersonalSettings ourInstance = new PersonalSettings();


    private String UserName = "";
    private String Email = "";
    private boolean Notifications;
    private double Budget;
    private double usablePercentage;
    private String Currency;
    private ArrayList<FinancialAccount> myAccounts = new ArrayList<FinancialAccount>();
    private double totalAccountsBalance;
    private double moneyRemaining;    //This will be divided among the remaining days
    private ArrayList<String> Categories= new ArrayList<String>();
    private ArrayList<Transactions> allTrans = new ArrayList<Transactions>();
    private ArrayList<String> PossibleCurrencies;

    public static PersonalSettings getInstance(){
        return ourInstance;
    }

    private PersonalSettings(){

        Categories.add("Food");
        Categories.add("Household");
        Categories.add("Social Life");
        Categories.add("Health");
        Categories.add("Taxes");
        Categories.add("Gift");
        Categories.add("Transportation");
        Categories.add("Other");


    }

    void setPersonalSettings(String username,String Email,boolean nt,double budget,double usable_Percentage,String currency){
        this.UserName = username;
        this.Email = Email;
        this.Notifications = nt;
        this.Budget = budget;
        this.usablePercentage = usable_Percentage;


        setCurrencyByName(currency);


    }

    void setPossibleCurrencies(String[] arrayFromResource){
        PossibleCurrencies = new ArrayList<String>( Arrays.asList(arrayFromResource));
    }

    public String getUserName() {
        return UserName;
    }

    public double getBudget() {
        return Budget;
    }

    public double getMoneyRemaining() {
        return moneyRemaining;
    }

    public ArrayList<FinancialAccount> getMyAccounts() {
        return myAccounts;
    }

    public double getTotalAccountsBalance() {
        return totalAccountsBalance;
    }

    public double getUsablePercentage() {
        return usablePercentage;
    }

    public String getEmail() {
        return Email;
    }

    public String getCurrency() {
        return Currency;
    }

    public ArrayList<String> getCategories() {
        return Categories;
    }

    void addTransaction(Transactions t, String accName)
    {
        myAccounts.get(searchAccsByName(accName)).addTransaction(t);
    }

    ArrayList getAccNames(){
        ArrayList<String> temp = new ArrayList<String>();
        for (FinancialAccount f: myAccounts) {
            temp.add(f.getName());
        }

        return temp;
    }

    int searchAccsByName(String accName)    //Returns index to the required account in myAccounts
    {
        int index = 0;
        for(FinancialAccount f: this.myAccounts){

            if(accName.equals(f.getName()))
            {
                return index;
            }
            index++;
        }
        Log.e(TAG, "Invalid Account Name entered" );
        return -1;
    }

    public ArrayList<Transactions> getAllTrans() {
        allTrans.clear();
        for (FinancialAccount acc:this.myAccounts) {
            allTrans.addAll( acc.getListOfTransactions() );
            //Log.d(TAG, " allTrans size before "+allTrans.size()+" adding to it "+acc.getListOfTransactions().size()+" transactions, all Trans new size "+ allTrans.size());
        }



        return allTrans;
    }

    public ArrayList<Transactions> getTransByAccount(String AccName) {
        ArrayList<Transactions> temp = new ArrayList<Transactions>();

        for (FinancialAccount f:getMyAccounts())
        {
            if(f.getName() == AccName)
            {
                return f.getListOfTransactions();
            }
        }

        Log.w(TAG, " Did not find account with this Name" );
        return null;
    }


    String[] getAccountsName()
    {
        if(myAccounts==null)
            return null;
        ArrayList<String> arr = new ArrayList<String>();
        if(myAccounts.isEmpty()) {
            return null;
        }
        for (FinancialAccount f: this.myAccounts) {
            Log.i("TAG", "account name "+f.getName());
            arr.add(f.getName());
        }



        String []str_arr = new String[arr.size()];
        arr.toArray(str_arr);


        return str_arr;
    }


    boolean enterUserName(String s) {
        //Need to check if username is not Used
        this.UserName = s;
        return true; //return true if done successfully
    }

    boolean enterEmail(String s) {
        this.Email = s;
        return true; //return true if done successfully
    }

    boolean setNotifications(boolean n){
        this.Notifications = n;
        return true;//return true if done successfully
    }

    public boolean isNotifications() {
        return Notifications;
    }

    boolean setBudget(double number) {
        if(number > 0) {
            Log.e("TAG", "setBudget: negative budget entered" );
            return false;
        }

        this.Budget = number;
        return true;
    }

    boolean setUsablePercentage(double number) {
        if(number > 0)
            return false;

        this.usablePercentage = number;
        return true;
    }

    boolean setCurrencyByIndex(int index){

        if( index < PossibleCurrencies.size() && index>0)
        {
            this.Currency = PossibleCurrencies.get(index);
            return true;
        }
        return false;

    }

    int getCurrIndex(){

        Log.i(TAG, "currency chosen is "+getCurrency()+" and its index in resource array is "+ PossibleCurrencies.indexOf(Currency));
        return PossibleCurrencies.indexOf(Currency);
    }

    boolean setCurrencyByName(String currStr){

        if( PossibleCurrencies.contains(currStr))
        {
            this.Currency = currStr;
            return true;
        }else {
            Log.e(TAG, "Unknown Currency entered will use Default " );
            setCurrencyByIndex(0);
            return false;
        }
    }

    void addAccount(FinancialAccount acc) {
        myAccounts.add(acc);

    }

    boolean addCategory(String s) {
        for (int i = 0; i < this.Categories.size(); i++)
        {
            if(s.equals(Categories.get(i)))
            {
                Log.e("TAG", "addCategory: Category with same name exists" );
                return false;
            }
        }
        Categories.add(s);
        return true;
    }

    double getUsableBudget(){
        return Budget * usablePercentage/100;
    }

    public double CalculateTotalAccountsBalance() {
        double sum=0;
        for(FinancialAccount i:this.myAccounts)
        {
            sum += i.getCurrentBalance();
        }
        this.totalAccountsBalance = sum;
        return sum;
    }

    int getProgress(){

        int ProgressValue = (int)(CalculateMoneyRemaining()*100 / getBudget() );
        //Log.i(TAG, "onCreate: Progress value = "+ProgressValue+" = calculate money remaining "+CalculateMoneyRemaining() +" / "+getBudget());
        return ProgressValue;
    }

    double CalculateMoneyRemaining(){
        this.moneyRemaining = Budget - CalculateTotalAccountsBalance();
        return this.moneyRemaining;
    }



}
