package com.example.iuribreno.trabalhofinalofficial.service;

import android.util.Log;

import com.example.iuribreno.trabalhofinalofficial.ENTIDADES.Consulta;
import com.example.iuribreno.trabalhofinalofficial.ENTIDADES.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ConsultaService {
    public ArrayList<Consulta> getConsultasByMedico(final String email) {
        final ArrayList<Consulta> consultas = new ArrayList<>();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("consulta");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,Object> users = (Map<String,Object>) dataSnapshot.getValue();
                for (Map.Entry<String, Object> entry : users.entrySet()){
                    Consulta consulta = new Consulta(entry);
                    if(consulta.getMedico().equals(email)) {
                        Log.d("cosulta", consulta.getMedico());
                        Log.d("email", email);
                        consultas.add(consulta);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return consultas;
    }
}
