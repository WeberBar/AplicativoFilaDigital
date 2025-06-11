package com.example.aplicativodefila;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private Button btnCliente, btnEntrar;
    private EditText email, senha;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnCliente = findViewById(R.id.btnCadastrarUsuario);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnEntrar.setOnClickListener(v -> {
            String emailDigitado = email.getText().toString().trim();
            String senhaDigitada = senha.getText().toString().trim();

            if (emailDigitado.isEmpty() || senhaDigitada.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(emailDigitado, senhaDigitada)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String uid = user.getUid();
                                // Busca tipo de conta no Firestore
                                db.collection("usuarios").document(uid).get()
                                        .addOnSuccessListener(document -> {
                                            if (document.exists()) {
                                                String tipoConta = document.getString("tipoConta");
                                                if ("Empresa".equalsIgnoreCase(tipoConta)) {
                                                    startActivity(new Intent(this, FilaEmpresa.class));
                                                } else {
                                                    startActivity(new Intent(this, ListaEmpresasActivity.class));
                                                }
                                                finish(); // Finaliza a tela de login
                                            } else {
                                                Toast.makeText(this, "Tipo de conta não encontrado", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(this, "Erro ao buscar tipo de conta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        });
                            }
                        } else {
                            Toast.makeText(this, "Email ou senha inválidos", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        btnCliente.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Cadastro.class));
        });
    }
}
