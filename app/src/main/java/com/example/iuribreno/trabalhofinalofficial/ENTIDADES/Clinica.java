package com.example.iuribreno.trabalhofinalofficial.ENTIDADES;

import com.example.iuribreno.trabalhofinalofficial.DAO.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Clinica {
    private String key;
    private double lat;
    private double lng;
    private String nome;
    private String medico;

    public Clinica(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Clinica() {
    }

    public void salvar(){
        if(this.key == null) {
            String key = FirebaseDatabase.getInstance().getReference("clinicas").push().getKey();
            setKey(key);
        }
        DatabaseReference referenceFirebase = ConfiguracaoFirebase.getReferenciaFirebase();
        referenceFirebase.child("clinicas").child(String.valueOf(getKey())).setValue(this);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }
}
