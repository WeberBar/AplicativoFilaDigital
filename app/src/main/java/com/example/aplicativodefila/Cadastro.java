package com.example.aplicativodefila;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class Cadastro extends AppCompatActivity {
    EditText nome, email, senha;
    Spinner tipoConta;
    Button btnCadastrar;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_empresa);

        nome = findViewById(R.id.editNome);
        email = findViewById(R.id.editEmail);
        senha = findViewById(R.id.editSenha);
        tipoConta = findViewById(R.id.spinnerTipoConta);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Configura opções no spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.tipos_conta,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoConta.setAdapter(adapter);

        btnCadastrar.setOnClickListener(v -> {
            String nomeDigitado = nome.getText().toString().trim();
            String emailDigitado = email.getText().toString().trim();
            String senhaDigitada = senha.getText().toString().trim();
            String tipoSelecionado = tipoConta.getSelectedItem().toString();

            if (nomeDigitado.isEmpty() || emailDigitado.isEmpty() || senhaDigitada.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(emailDigitado, senhaDigitada)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user == null) return;

                            String uid = user.getUid();

                            // Salvar no Firestore: usuários
                            HashMap<String, Object> dadosUsuario = new HashMap<>();
                            dadosUsuario.put("nome", nomeDigitado);
                            dadosUsuario.put("email", emailDigitado);
                            dadosUsuario.put("tipoConta", tipoSelecionado);

                            db.collection("usuarios").document(uid).set(dadosUsuario)
                                    .addOnSuccessListener(unused -> Log.d("FIREBASE", "Usuário salvo com sucesso"))
                                    .addOnFailureListener(e -> Log.e("FIREBASE", "Erro ao salvar usuário", e));

                            // Se for empresa, cria também o documento da fila
                            if (tipoSelecionado.equals("Empresa")) {
                                ArrayList<String> senhasIniciais = new ArrayList<>();
                                for (int i = 1; i <= 6; i++) {
                                    String senha = String.format("S%02d", i);
                                    senhasIniciais.add(senha);

                                    // Adiciona no FilaAdapter (local)
                                    FilaAdapter.getInstance().adicionarSenha(null);
                                }

                                HashMap<String, Object> dadosFila = new HashMap<>();
                                dadosFila.put("senhas", senhasIniciais);
                                dadosFila.put("ultimaChamada", "");

                                db.collection("filas").document(uid).set(dadosFila)
                                        .addOnSuccessListener(unused -> Log.d("FIREBASE", "Fila criada com sucesso"))
                                        .addOnFailureListener(e -> Log.e("FIREBASE", "Erro ao criar fila", e));
                            }


                            Toast.makeText(this, "Usuário criado com sucesso!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Cadastro.this, MainActivity.class));finish();
                            
                        } else {
                            Toast.makeText(this, "Erro ao criar usuário: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("FIREBASE", "Erro", task.getException());
                        }
                    });
        });
    }
}
