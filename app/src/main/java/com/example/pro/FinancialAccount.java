package com.example.pro;

import java.util.ArrayList;

public class FinancialAccount {
    private String Name;
    private double CurrentBalance;
    private double Total;
    private double OutstandingBalance;
    ArrayList<Transactions> ListOfTransactions = new ArrayList<Transactions>();
    private double Income;
    private double Expense;

    FinancialAccount(String name,double outBalance){
        Name = name;
        OutstandingBalance = outBalance;
        CurrentBalance = outBalance;
        Total = 0;
        Income = 0;
        Expense = 0;
    }

    void setName(String s){this.Name = s;}

    public String getName() {
        return Name;
    }

    void CalculateTotal()
    {
        Total = Income - Expense;
        calculateBalance();
    }

    void calculateBalance()
    {
        CurrentBalance = OutstandingBalance + Total;
    }

    void TransactionUpdate(double amount){ //called whenever a transaction happens
        if(amount >= 0)
            Income += amount;
        else
            Expense +=amount;

        CalculateTotal();

    }


    void addTransaction(Transactions t)
    {
        this.ListOfTransactions.add(t);
        TransactionUpdate(t.getAmount());
    }

    void TransferBalance() {
        OutstandingBalance+= CurrentBalance;
        CurrentBalance = 0;
    }

    public ArrayList<Transactions> getListOfTransactions() {
        return ListOfTransactions;
    }

    public double getCurrentBalance() {
        return CurrentBalance;
    }

    public double getExpense() {
        return Expense;
    }

    public double getIncome() {
        return Income;
    }

    public double getOutstandingBalance() {
        return OutstandingBalance;
    }


}
