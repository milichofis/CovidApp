package com.example.covidapp.data;

public class HistoryEntity {
    public static final String TableName = "History";
    public static final String IdColumn = "id";
    public static final String CountryColumn = "Country";
    public static final String UpdateDateColumn = "LastUpdate";

    private int id;
    private String updateDate;
    private String country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }


}
