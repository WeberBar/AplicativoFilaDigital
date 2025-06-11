package com.example.aplicativodefila;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FilaEmpresa extends AppCompatActivity {

    private EditText editTextNumber;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fila_empresa);

        editTextNumber = findViewById(R.id.editTextNumber);
        listView = findViewById(R.id.listView);


        Button btnChamar = findViewById(R.id.btnChamar);
        btnChamar.setOnClickListener(v -> {
            String chamada = FilaAdapter.getInstance().chamarProxima(this);
            if (chamada != null) {
                editTextNumber.setText(chamada);
                atualizarLista();

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseFirestore.getInstance().collection("filas")
                        .document(uid)
                        .update("ultimaChamada", chamada);
            } else {
                Toast.makeText(this, "Fila vazia", Toast.LENGTH_SHORT).show();
            }
        });


        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> { startActivity(new Intent(FilaEmpresa.this, MainActivity.class));finish();});

        atualizarLista();
    }

    private void atualizarLista() {
        List<String> filaAtual = FilaAdapter.getInstance().getFila();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filaAtual);
        listView.setAdapter(adapter);
    }
}
