package com.example.iuribreno.trabalhofinalofficial;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iuribreno.trabalhofinalofficial.DAO.ConfiguracaoFirebase;
import com.example.iuribreno.trabalhofinalofficial.ENTIDADES.Clinica;
import com.example.iuribreno.trabalhofinalofficial.ENTIDADES.Usuario;
import com.example.iuribreno.trabalhofinalofficial.broadcast.InternetBroadcast;
import com.example.iuribreno.trabalhofinalofficial.service.NotificacaoService;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityLogs";
    private EditText email;
    private EditText senha;
    private Button buttonEntrar;
    private FirebaseAuth auth;
    private Usuario usuario;
    private LoginButton loginFacebookButton;
    private CallbackManager mCallbackManager;



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

        getHashKey();

        auth = ConfiguracaoFirebase.getFirebaseAuth();
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginFacebookButton = findViewById(R.id.buttonFacebookLogin);
        loginFacebookButton.setReadPermissions("email", "public_profile");
        loginFacebookButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    String name = object.getString("name");

                                    handleFacebookAccessToken(loginResult.getAccessToken(), name);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });


    }

    public void validarLogin(){
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

    private void getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("mobile.ufc.br.novosispu", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token, final String userName) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser fbUser = auth.getCurrentUser();
                            String userKey = auth.getCurrentUser().getUid();
                            Usuario usuario = new Usuario();
                            usuario.setEmail(auth.getCurrentUser().getEmail());
                            usuario.setNome(userName);
                            usuario.setId(userKey);
                            usuario.setEndereco("");
                            usuario.setSenha("");
                            usuario.setTelefone("");
                            usuario.salvar();
                            abrirTelaMenus();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser!=null) {
            if (currentUser.getEmail().equals("medico@gmail.com") || currentUser.getEmail().equals("medico2@gmail.com"))
                abrirTelaMedico();
            else
                abrirTelaMenus();
        }
    }
}

