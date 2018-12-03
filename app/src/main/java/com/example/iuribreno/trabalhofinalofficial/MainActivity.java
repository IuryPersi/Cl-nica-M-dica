package com.example.iuribreno.trabalhofinalofficial;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iuribreno.trabalhofinalofficial.DAO.ConfiguracaoFirebase;
import com.example.iuribreno.trabalhofinalofficial.ENTIDADES.Usuario;
import com.example.iuribreno.trabalhofinalofficial.broadcast.InternetBroadcast;
import com.example.iuribreno.trabalhofinalofficial.service.NotificacaoService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button buttonEntrar;
    private FirebaseAuth auth;
    private Usuario usuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email);
        senha = (EditText) findViewById(R.id.senha);

        buttonEntrar = (Button) findViewById(R.id.buttonEntrar);

        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().equals("") && !senha.getText().toString().equals("")){

                    usuario = new Usuario();
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());

                    validarLogin();

                }else{
                    Toast.makeText(MainActivity.this,"Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");

        InternetBroadcast internetBroadcast = new InternetBroadcast();
        registerReceiver(internetBroadcast, filter);
    }

    public void validarLogin(){
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if(usuario.getEmail().equals("medico@gmail.com") || usuario.getEmail().equals("medico2@gmail.com"))
                        abrirTelaMedico();
                    else
                        abrirTelaMenus();
                    Toast.makeText(MainActivity.this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    public void abrirTelaMenus(){
        Intent telaMenus = new Intent(this,Menus_Inicial.class);
        startActivity(telaMenus);
    }

    public void abrirTelaMedico(){
        Intent telaMenus = new Intent(this, MedicoMenuActivity.class);
        startActivity(telaMenus);
    }

    public void TelaCadastro(View view){
        Intent telaCadastro = new Intent(this,Cadastro.class);
        startActivity(telaCadastro);
    }
}

