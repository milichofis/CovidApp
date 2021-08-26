package com.example.covidapp.models;

import com.example.covidapp.common.DateTimeHelper;

public class CovidRecord {
    private String country;
    private String confirmed;
    private String deaths;
    private String recovered;
    private String active;

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {this.country=country;}

    public String getConfirmed() {
        return confirmed;
    }
    public void setConfirmed(String confirmed) {this.confirmed=confirmed;}

    public String getDeaths() {
        return deaths;
    }
    public void setDeaths(String deaths) {this.deaths=deaths;}

    public String getRecovered() {
        return recovered;
    }
    public void setRecovered(String recovered) {this.recovered=recovered;}


    public String getActive() {
        return active;
    }
    public void setActive(String active) {this.active=active;}

    public String getLastUpdate(){
        return DateTimeHelper.getDateNow();
    }
}
