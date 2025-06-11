package com.example.aplicativodefila;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ListaEmpresasActivity extends AppCompatActivity {

    ListView listViewEmpresas;
    ArrayList<String> nomesEmpresas = new ArrayList<>();
    ArrayAdapter<String> adapter;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_empresas);

        listViewEmpresas = findViewById(R.id.listaEmpresas);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nomesEmpresas);
        listViewEmpresas.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        // Buscar empresas do Firestore
        db.collection("usuarios")
                .whereEqualTo("tipoConta", "Empresa")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        Toast.makeText(this, "Nenhuma empresa cadastrada.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String nome = doc.getString("nome");
                        if (nome != null) {
                            nomesEmpresas.add(nome);
                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erro ao carregar empresas: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });

        listViewEmpresas.setOnItemClickListener((adapterView, view, position, id) -> {
            String nomeEmpresa = nomesEmpresas.get(position);
            Intent intent = new Intent(ListaEmpresasActivity.this, FilaCliente.class);
            intent.putExtra("nomeEmpresa", nomeEmpresa);
            startActivity(intent);
        });
    }
}

