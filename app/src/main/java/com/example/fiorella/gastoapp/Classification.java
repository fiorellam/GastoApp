package com.example.fiorella.gastoapp;

import java.io.Serializable;

public class Classification implements Serializable {

    private Integer classification_id;
    private String namee;
    private Integer limitt;

    public Classification(Integer classification_id, String namee, Integer limitt) {
        this.classification_id = classification_id;
        this.namee = namee;
        this.limitt = limitt;
    }

    public Classification(){

    }

    public Integer getClassification_id() {
        return classification_id;
    }

    public void setClassification_id(Integer classification_id) {
        this.classification_id = classification_id;
    }

    public String getNamee() {
        return namee;
    }

    public void setNamee(String namee) {
        this.namee = namee;
    }

    public Integer getLimitt() {
        return limitt;
    }

    public void setLimitt(Integer limitt) {
        this.limitt = limitt;
    }
}
