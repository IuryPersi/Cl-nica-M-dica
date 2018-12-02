package com.example.iuribreno.trabalhofinalofficial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iuribreno.trabalhofinalofficial.ENTIDADES.Consulta;
import com.example.iuribreno.trabalhofinalofficial.ENTIDADES.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PacienteVisualizarCOnsultaAtendida extends AppCompatActivity {

    private Consulta consulta;
    private String TAG = "PacienteVisualizarCOnsultaAtendida";

    private TextView visualizarEspecialidadeTextView;
    private TextView visualizarQueixaTextView;
    private TextView visualizarRelatorioTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_visualizar_consulta_atendida);

        visualizarEspecialidadeTextView = (TextView) findViewById(R.id.visualizarEspecialidadeTextView);
        visualizarQueixaTextView = (TextView) findViewById(R.id.visualizarQueixaTextView);
        visualizarRelatorioTextView= (TextView) findViewById(R.id.visualizarRelatorioTextView);

        final String consultaKey = getIntent().getStringExtra("CONSULTA_KEY");

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("consulta").child(consultaKey);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                consulta = dataSnapshot.getValue(Consulta.class);
                Log.d(TAG, consulta.toString());

                visualizarEspecialidadeTextView.setText(consulta.getEspecialidade());
                visualizarQueixaTextView.setText(consulta.getQueixas());
                visualizarRelatorioTextView.setText(consulta.getRelatorio());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
