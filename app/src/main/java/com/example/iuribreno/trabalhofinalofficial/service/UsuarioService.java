package com.example.iuribreno.trabalhofinalofficial.service;

import android.util.Log;

import com.example.iuribreno.trabalhofinalofficial.ENTIDADES.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsuarioService {
    private FirebaseAuth mAuth;

    public UsuarioService() {
        mAuth = FirebaseAuth.getInstance();
    }

    public String getCurrentUserEmail() {
        FirebaseUser currentFirebaseUser = mAuth.getCurrentUser();
        final String currentEmail = currentFirebaseUser.getEmail();
        return currentEmail;
    }

    public Usuario getCurrentUser() {
        FirebaseUser currentFirebaseUser = mAuth.getCurrentUser();
        final String currentEmail = currentFirebaseUser.getEmail();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("usuario");
        final List<Usuario> connectedUser= new ArrayList<Usuario>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,Object> users = (Map<String,Object>) dataSnapshot.getValue();
                for (Map.Entry<String, Object> entry : users.entrySet()){
                    Usuario usuario = new Usuario(entry);
                    if(usuario.getEmail().equals(currentEmail)) {
                        connectedUser.add(usuario);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(connectedUser != null && connectedUser.size() > 0) {
            Log.d("currentemail", connectedUser.get(0).getEmail());
        }
        return null;
    }

    public ArrayList<Usuario> getMedicos() {
        final ArrayList<Usuario> medicos = new ArrayList<>();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("usuario");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,Object> users = (Map<String,Object>) dataSnapshot.getValue();
                for (Map.Entry<String, Object> entry : users.entrySet()){
                    Usuario usuario = new Usuario(entry);
                    if(usuario.getTipo().equals("medico")) {
                        medicos.add(usuario);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return medicos;
    }
}
