package com.example.aplicativodefila;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SenhaViewModel senhaViewModel;
    private TextView txtAtual;
    private ListView listView;
    private Button btnPegarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtAtual = findViewById(R.id.txtAtual);
        listView = findViewById(R.id.listView);
        btnPegarSenha = findViewById(R.id.btnPegarSenha);

        senhaViewModel = new ViewModelProvider(this).get(SenhaViewModel.class);

        // Observa as mudanças no estado da senha gerada e nas últimas senhas
        senhaViewModel.getUltimasSenhas().observe(this, ultimas -> {
            if (ultimas != null && !ultimas.isEmpty()) {
                UltimaSenhaAdapter adapter = new UltimaSenhaAdapter(this, ultimas);
                listView.setAdapter(adapter);

                // Atualiza o texto com a última senha chamada
                if (ultimas.size() > 0) {
                    txtAtual.setText("Senha chamada: " + ultimas.get(0).getNumero());
                }
            }
        });

        senhaViewModel.getSenhaGerada().observe(this, senha -> {
            if (senha != null) {
                // Atualiza o texto da nova senha gerada
                txtAtual.setText("Senha gerada: " + senha.getNumero());
            }
        });

        // Ao clicar no botão, gera uma nova senha e lista as últimas senhas
        btnPegarSenha.setOnClickListener(v -> {
            senhaViewModel.gerarSenha();
            senhaViewModel.listarUltimasSenhas();
        });

        // Chama as últimas senhas na inicialização
        senhaViewModel.listarUltimasSenhas();
    }
}
