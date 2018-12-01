package com.example.iuribreno.trabalhofinalofficial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.Map;

public class MedicoConsultaRealizadaActivity extends AppCompatActivity {

    private Consulta consulta;
    private String TAG = "MedicoConsultaRealizadaActivity";

    private TextView pacienteAtendidoRealizadoTextView;
    private TextView especialidadeAtendidoRealizadoTextView;
    private TextView queixasAtendidoRealizadoTextView;
    private TextView relatorioAtendidoRealizadoTextView;
    private Button goToHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico_consulta_realizada);

        pacienteAtendidoRealizadoTextView = (TextView) findViewById(R.id.pacienteAtendidoRealizadoTextView);
        especialidadeAtendidoRealizadoTextView = (TextView) findViewById(R.id.especialidadeAtendidoRealizadoTextView);
        queixasAtendidoRealizadoTextView = (TextView) findViewById(R.id.queixasAtendidoRealizadoTextView);
        relatorioAtendidoRealizadoTextView = (TextView) findViewById(R.id.relatorioAtendidoRealizadoTextView);
        goToHomeButton = (Button) findViewById(R.id.goToHomeButtonFromConsulta);

        final String consultaKey = getIntent().getStringExtra("CONSULTA_KEY");

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("consulta").child(consultaKey);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                consulta = dataSnapshot.getValue(Consulta.class);
                pacienteAtendidoRealizadoTextView.setText(consulta.getUsuario());
                especialidadeAtendidoRealizadoTextView.setText(consulta.getEspecialidade());
                queixasAtendidoRealizadoTextView.setText(consulta.getQueixas());
                relatorioAtendidoRealizadoTextView.setText(consulta.getRelatorio());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

        goToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicoConsultaRealizadaActivity.this, MedicoMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
