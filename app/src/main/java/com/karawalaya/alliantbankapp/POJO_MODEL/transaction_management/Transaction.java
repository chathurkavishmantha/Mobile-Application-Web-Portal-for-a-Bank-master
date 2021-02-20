package com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management;

import java.sql.Date;

public class Transaction {
    private int transactionId;
    private int creditAccount;
    private Date transactionDate;
    private double transactionAmount;
    private int debitAccount;

    public Transaction() {}

    public Transaction(int creditAccount, Date transactionDate, double transactionAmount, int debitAccount) {
        this.setCreditAccount(creditAccount);
        this.setTransactionDate(transactionDate);
        this.setTransactionAmount(transactionAmount);
        this.setDebitAccount(debitAccount);
    }

    public Transaction(int transactionId, int creditAccount, Date transactionDate, double transactionAmount, int debitAccount) {
        this.setTransactionId(transactionId);
        this.setCreditAccount(creditAccount);
        this.setTransactionDate(transactionDate);
        this.setTransactionAmount(transactionAmount);
        this.setDebitAccount(debitAccount);
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(int creditAccount) {
        this.creditAccount = creditAccount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public int getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(int debitAccount) {
        this.debitAccount = debitAccount;
    }
}
