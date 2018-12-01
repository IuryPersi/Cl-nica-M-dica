package com.example.iuribreno.trabalhofinalofficial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ConsultasMarcadas extends AppCompatActivity {

    private Button buttonVoltarMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas_marcadas);

        buttonVoltarMenu = (Button) findViewById(R.id.buttonVoltarMenu);

        buttonVoltarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaMenus();
            }
        });
    }
    public void abrirTelaMenus(){
        Intent abrirTelaMenus = new Intent(this,Menus_Inicial.class);
        startActivity(abrirTelaMenus);
    }
}
