package com.example.iuribreno.trabalhofinalofficial;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iuribreno.trabalhofinalofficial.DAO.ConfiguracaoFirebase;
import com.example.iuribreno.trabalhofinalofficial.ENTIDADES.Usuario;
import com.example.iuribreno.trabalhofinalofficial.HELPER.PreferenciasAndroid;
import com.example.iuribreno.trabalhofinalofficial.HELPER.base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class Cadastro extends AppCompatActivity {

    private EditText EditTextnome;
    private EditText EditTexttelefone;
    private EditText EditTextendereco;
    private EditText EditTextemail;
    private EditText EditTextSenha;

    private Button button_cadastrarBD;
    private Button button_CancelarCad;

    private Usuario usuario;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        //Inicializa componentes da nossa tela
        EditTextnome = (EditText)findViewById(R.id.nome);
        EditTexttelefone = (EditText)findViewById(R.id.telefone);
        EditTextendereco = (EditText)findViewById(R.id.endereco);
        EditTextemail = (EditText)findViewById(R.id.email);
        EditTextSenha = (EditText)findViewById(R.id.senha);
        button_cadastrarBD = (Button)findViewById(R.id.button_cadastrarBD);
        button_CancelarCad = (Button) findViewById(R.id.button_cancelarCad);

        button_cadastrarBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!EditTextemail.getText().toString().equals("") || EditTextSenha.getText().toString().equals("")){

                    usuario = new Usuario();
                    usuario.setNome(EditTextnome.getText().toString());
                    usuario.setEmail(EditTextemail.getText().toString());
                    usuario.setTelefone(EditTexttelefone.getText().toString());
                    usuario.setEndereco(EditTextendereco.getText().toString());
                    usuario.setSenha(EditTextSenha.getText().toString());

                    cadastrarUsuario();

                }else{
                    Toast.makeText(Cadastro.this,"Preencha todos os campos", Toast.LENGTH_LONG).show();
                }
            }
        });

        button_CancelarCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaMain();
            }
        });
    }

    private void cadastrarUsuario(){

        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()




        ).addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Cadastro.this,"Usuario Cadastrado.", Toast.LENGTH_LONG).show();

                    String identificadorUsuario = base64Custom.codificadorBase64(usuario.getEmail());

                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuario.setId(identificadorUsuario);
                    usuario.salvar();

                    PreferenciasAndroid preferenciasAndroid = new PreferenciasAndroid(Cadastro.this);
                    preferenciasAndroid.salvarUsuarioPreferencias(identificadorUsuario, usuario.getNome());

                    abrirLoginUsuario();

                }else{










                    String erroExcecao = "";

                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = " Digite uma senha mais forte, contendo no mínimo 8 caracteres de letras e números";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao = "E-mail inválido.";
                    }catch (FirebaseAuthUserCollisionException e){
                        erroExcecao = "O e-mail já está cadastrado.";
                    }catch (Exception e){
                        erroExcecao = "Erro ao efetuar o cadastro.";
                        e.printStackTrace();
                    }
                    Toast.makeText(Cadastro.this,"Erro : " + erroExcecao, Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    public void abrirLoginUsuario(){
        Intent intent = new Intent(Cadastro.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrirTelaMain(){
        Intent intent = new Intent(Cadastro.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
