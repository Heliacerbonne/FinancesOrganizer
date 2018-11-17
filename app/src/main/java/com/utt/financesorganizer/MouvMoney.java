package com.utt.financesorganizer;

public class MouvMoney {

    protected String name;
    protected double amount;
    protected String date;
    protected String cat;

    public MouvMoney(String name, double amount, String date, String cat){
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.cat = cat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
}
