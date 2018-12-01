package com.example.iuribreno.trabalhofinalofficial.HELPER;

import android.util.Base64;

public class base64Custom {

    public static String codificadorBase64 (String texto){
        return Base64.encodeToString(texto.getBytes(),Base64.DEFAULT).replaceAll("(\\n|\\r)","");
    }
    public static String decodificadorBase64 (String textoCodificador){
        return new String (Base64.decode(textoCodificador, Base64.DEFAULT));
    }
}
