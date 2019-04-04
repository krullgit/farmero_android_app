package com.example.matthes.farmero;

public class StoredDisease {
    String diseaseName;
    String diseaselon;
    String diseaselat;
    String diseasDate;

    public StoredDisease() {

    }

    public StoredDisease(String diseaseName, String diseaselon, String diseaselat, String diseasDate) {
        this.diseaseName = diseaseName;
        this.diseaselon = diseaselon;
        this.diseaselat = diseaselat;
        this.diseasDate = diseasDate;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public String getDiseaselon() {
        return diseaselon;
    }

    public String getDiseaselat() {
        return diseaselat;
    }

    public String getDiseasDate() {
        return diseasDate;
    }
}
