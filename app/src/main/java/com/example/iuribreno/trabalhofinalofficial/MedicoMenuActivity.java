package com.example.iuribreno.trabalhofinalofficial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.iuribreno.trabalhofinalofficial.ENTIDADES.Usuario;

public class MedicoMenuActivity extends AppCompatActivity {
    private Button pendentesButton;
    private Button relizadasButton;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico_menu);

        pendentesButton = (Button) findViewById(R.id.buttonVisConsultasPendentes);
        relizadasButton = (Button) findViewById(R.id.buttonVisConsultasRealizadas);
        logout = (Button) findViewById(R.id.efetuarLogout);

        pendentesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicoMenuActivity.this, MedicoConsultasPendentesActivity.class);
                startActivity(intent);
            }
        });

        relizadasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicoMenuActivity.this, MedicoConsultasRealizadasActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicoMenuActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
