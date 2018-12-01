package com.example.iuribreno.trabalhofinalofficial.HELPER;

//Salvar quando o usuário estiver conectado no aparelho

import android.content.Context;
import android.content.SharedPreferences;

public class preferencias {

    private Context context;
    private SharedPreferences preferences;
    private String NOME_ARQUIVO = "firebase.preferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_EDENTIFICADORA = "identificarUsuarioLogado";
    private final String CHAVE_NOME = "nomeUsuarioLogado";

    public preferencias(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);

        editor = preferences.edit();
    }

    public void salvarUsuarioPreferencias(String identificadorUsuario, String nomeUsuario){
        editor.putString(CHAVE_EDENTIFICADORA, identificadorUsuario);
        editor.putString(CHAVE_NOME, nomeUsuario);
        editor.commit();
    }

    public String getIdentificador(){
        return preferences.getString(CHAVE_EDENTIFICADORA, null);
    }
    public String getNome(){
        return preferences.getString(CHAVE_NOME, null);
    }
}
