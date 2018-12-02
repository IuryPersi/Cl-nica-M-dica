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

public class RealizarConsultaActity extends AppCompatActivity {

    private Consulta consulta;
    private String TAG = "RealizarConsultaActivity";

    private TextView pacienteAtendidoTextView;
    private TextView especialidadeAtendidoTextView;
    private TextView queixasAtendidoTextView;
    private EditText relatorioEditText;
    private Button salvarConsultaRealizadaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_consulta_actity);

        pacienteAtendidoTextView = (TextView) findViewById(R.id.pacienteAtendidoTextView);
        especialidadeAtendidoTextView = (TextView) findViewById(R.id.especialidadeAtendidoTextView);
        queixasAtendidoTextView= (TextView) findViewById(R.id.queixasAtendidoTextView);
        salvarConsultaRealizadaButton = (Button) findViewById(R.id.salvarConsultaRealizadaButton);
        relatorioEditText = (EditText) findViewById(R.id.relatorioEditText);

        final String consultaKey = getIntent().getStringExtra("CONSULTA_KEY");

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("consulta").child(consultaKey);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                consulta = dataSnapshot.getValue(Consulta.class);
                pacienteAtendidoTextView.setText(consulta.getUsuario());
                especialidadeAtendidoTextView.setText(consulta.getEspecialidade());
                queixasAtendidoTextView.setText(consulta.getQueixas());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

        salvarConsultaRealizadaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consulta.setRelatorio(relatorioEditText.getText().toString());
                consulta.setAtendido(true);
                consulta.update();

                Toast.makeText(RealizarConsultaActity.this, "Atendimento realizado com sucesso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RealizarConsultaActity.this, MedicoMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
