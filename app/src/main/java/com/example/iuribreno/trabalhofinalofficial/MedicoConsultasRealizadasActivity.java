package com.example.iuribreno.trabalhofinalofficial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.iuribreno.trabalhofinalofficial.ENTIDADES.Consulta;
import com.example.iuribreno.trabalhofinalofficial.service.ConsultaService;
import com.example.iuribreno.trabalhofinalofficial.service.UsuarioService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MedicoConsultasRealizadasActivity extends AppCompatActivity {

    private ListView listView;
    private UsuarioService usuarioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico_consultas_realizadas);

        listView = (ListView) findViewById(R.id.consultasRealizadasList);
        usuarioService = new UsuarioService();

        final String email = usuarioService.getCurrentUserEmail();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("consulta");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,Object> users = (Map<String,Object>) dataSnapshot.getValue();
                final ArrayList<Consulta> consultas = new ArrayList<>();
                for (Map.Entry<String, Object> entry : users.entrySet()){
                    Consulta consulta = new Consulta(entry);
                    if(consulta.getMedico().equals(email) && consulta.isAtendido()) {
                        consultas.add(consulta);
                    }
                }

                ArrayAdapter<Consulta> adapter = new ArrayAdapter<Consulta>(MedicoConsultasRealizadasActivity.this, android.R.layout.simple_list_item_1, consultas);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                        Consulta selItem = (Consulta) consultas.get(position);
                        Intent intent = new Intent(MedicoConsultasRealizadasActivity.this, MedicoConsultaRealizadaActivity.class);
                        intent.putExtra("CONSULTA_KEY", selItem.getId());
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
