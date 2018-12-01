package com.example.iuribreno.trabalhofinalofficial.ENTIDADES;

import android.util.Log;

import com.example.iuribreno.trabalhofinalofficial.DAO.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Consulta {
    private String id;
    private String especialidade;
    private String medico;
    private String usuario;
    private String relatorio;
    private String queixas;
    private boolean atendido;
    private boolean notificado;
    private long time = new Date().getTime();

    public void salvar() {
        DatabaseReference referenceFirebase = ConfiguracaoFirebase.getReferenciaFirebase();
        String key =  referenceFirebase.push().getKey();
        setId(key);
        referenceFirebase.child("consulta").child(String.valueOf(key)).setValue(this);
    }

    public void update() {
        DatabaseReference referenceFirebase = ConfiguracaoFirebase.getReferenciaFirebase();
        referenceFirebase.child("consulta").child(String.valueOf(getId())).setValue(this);
    }

    public Consulta() {
        this.relatorio = "";
        this.atendido = false;
        this.notificado = false;
    }

    public Consulta(Map.Entry<String, Object> map){
        Map singleUser = (Map) map.getValue();
        setTime( Long.parseLong(singleUser.get("time").toString()));
        setId(singleUser.get("id").toString());
        setMedico(singleUser.get("medico").toString());
        setUsuario(singleUser.get("usuario").toString());
        setEspecialidade(singleUser.get("especialidade").toString());
        setRelatorio(singleUser.get("relatorio").toString());
        setQueixas(singleUser.get("queixas").toString());
        setAtendido(singleUser.get("atendido").toString() == "true");
        setAtendido(singleUser.get("notificado").toString() == "true");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    public String getQueixas() {
        return queixas;
    }

    public void setQueixas(String queixas) {
        this.queixas = queixas;
    }

    public boolean isAtendido() {
        return atendido;
    }

    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }

    public boolean isNotificado() {
        return notificado;
    }

    public void setNotificado(boolean notificado) {
        this.notificado = notificado;
    }

    @Override
    public String toString() {
        return this.especialidade;
    }
}
