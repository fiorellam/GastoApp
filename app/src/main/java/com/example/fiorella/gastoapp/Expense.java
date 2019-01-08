package com.example.fiorella.gastoapp;

import java.io.Serializable;

public class Expense implements Serializable {
    private long _id;
    private String _concept;
    private double _amount;
    private String _classification;
    private String _date;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String get_concept() {
        return _concept;
    }

    public void set_concept(String _concept) {
        this._concept = _concept;
    }

    public double get_amount() {
        return _amount;
    }

    public void set_amount(double _amount) {
        this._amount = _amount;
    }

    public String get_classification() {
        return _classification;
    }

    public void set_classification(String _classification) {
        this._classification = _classification;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }
}
