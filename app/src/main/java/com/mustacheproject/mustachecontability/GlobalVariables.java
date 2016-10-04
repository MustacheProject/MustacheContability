package com.mustacheproject.mustachecontability;

import android.app.Application;

/**
 * Created by Eugenio on 12/11/2015.
 */
public class GlobalVariables extends Application {

    //true = positive - false=negative
    private boolean controlPosOrNeg = true;
    private double totalAmount = 0.00;
    private double cashAmount = 0.00;
    private double bankAmount = 0.00;
    private int controlNewData =0;
    private DataProvider dp;


    public DataProvider getDp() {
        return dp;
    }

    public void setDp(DataProvider dp) {
        this.dp = dp;
    }



    public int getControlNewData() {
        return controlNewData;
    }

    public void setControlNewData(int controlNewData) {
        this.controlNewData = controlNewData;
    }



    public double getCashAmount() {

        cashAmount=setDecimal(cashAmount);
        return cashAmount;
    }

    public void setCashAmount(double cashAmount) {
        this.cashAmount =this.cashAmount+ cashAmount;
        this.cashAmount=setDecimal(this.cashAmount);
        setTotalAmount();
    }



    public double getBankAmount() {
        bankAmount=setDecimal(bankAmount);
        return bankAmount;
    }

    public void setBankAmount(double bankAmount) {
        this.bankAmount =this.bankAmount+ bankAmount;
        this.bankAmount=setDecimal(bankAmount);
        setTotalAmount();
    }



    public double getTotalAmount() {

        totalAmount=setDecimal(totalAmount);
        return totalAmount;
    }

    public void setTotalAmount() {
        totalAmount =cashAmount+bankAmount;
        totalAmount=setDecimal(totalAmount);
    }


    public boolean getControlPosOrNeg() {
        return controlPosOrNeg;
    }

    public void setControlPosOrNeg(boolean newControl) {
        this.controlPosOrNeg = newControl;
    }

    public static double setDecimal(double value) {
        value = Math.ceil(value * 100);
        value = value / 100;
        return value;
    }
    public void cancelFromCashAccount(double cancelValue){
        cashAmount=cashAmount-cancelValue;
        setTotalAmount();
    }

    public void cancelFromBankAccount(double cancelValue){
        bankAmount=bankAmount-cancelValue;
        setTotalAmount();
    }

}
