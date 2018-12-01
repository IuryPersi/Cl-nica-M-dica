package com.example.iuribreno.trabalhofinalofficial.ENTIDADES;

import android.util.Log;

import com.example.iuribreno.trabalhofinalofficial.DAO.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Usuario {
    private String id;
    private String nome;
    private String telefone;
    private String endereco;
    private String email;
    private String senha;
    private String tipo;



    public Usuario() {
        this.tipo = "paciente";
    }

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public void salvar(){
        DatabaseReference referenceFirebase = ConfiguracaoFirebase.getReferenciaFirebase();
        referenceFirebase.child("usuario").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("id",getId());
        hashMapUsuario.put("nome",getNome());
        hashMapUsuario.put("email",getEmail());
        hashMapUsuario.put("telefone",getTelefone());
        hashMapUsuario.put("endereco",getEndereco());
        hashMapUsuario.put("senha",getSenha());
        hashMapUsuario.put("tipo", getTipo());
        return hashMapUsuario;
    }

    public Usuario(Map.Entry<String, Object> map){
        Map singleUser = (Map) map.getValue();
        setEmail(singleUser.get("email").toString());
        setEndereco(singleUser.get("endereco").toString());
        setId(singleUser.get("id").toString());
        setNome(singleUser.get("nome").toString());
        setSenha(singleUser.get("senha").toString());
        setTelefone(singleUser.get("telefone").toString());
        setTipo(singleUser.get("tipo").toString());
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return nome;
    }
}
