package com.example.aplicativodefila;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.List;

public class FilaCliente extends AppCompatActivity implements FilaAdapter.Callback {

    private static String minhaSenha = null; // <- MANTÉM MESMO FORA DA TELA

    private TextView txtMinhaSenha;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fila);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        txtMinhaSenha = findViewById(R.id.txtMinhaSenha);
        listView = findViewById(R.id.listView);

        Button btnPegarSenha = findViewById(R.id.btnPegarSenha);
        btnPegarSenha.setOnClickListener(v -> {
            if (minhaSenha == null) {
                minhaSenha = FilaAdapter.getInstance().adicionarSenha(this);
                txtMinhaSenha.setText("Minha Senha: " + minhaSenha);
                atualizarLista();
            } else {
                Toast.makeText(this, "Você já possui a senha: " + minhaSenha, Toast.LENGTH_SHORT).show();
            }
        });

        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v ->  { startActivity(new Intent(FilaCliente.this, MainActivity.class));finish();});

        // Verifica se é a vez dele logo ao abrir a tela
        verificarSeFoiChamado();

        if (minhaSenha != null) {
            txtMinhaSenha.setText("Minha Senha: " + minhaSenha);
        }

        atualizarLista();
    }
    private void mostrarNotificacao(String titulo, String mensagem) {
        String canalId = "canal_fila";
        String canalNome = "Notificações da Fila";

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(canalId, canalNome, NotificationManager.IMPORTANCE_HIGH);
            canal.setDescription("Canal para notificações de senha chamada");
            notificationManager.createNotificationChannel(canal);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, canalId)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // use o ícone do app ou adicione um personalizado
                .setContentTitle(titulo)
                .setContentText(mensagem)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());
    }
    private void mostrarDialogSenhaChamada() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sua vez chegou!");
        builder.setMessage("A sua senha foi chamada.");
        builder.setCancelable(false); // usuário só fecha clicando em OK
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                minhaSenha = null; // limpa a senha ao fechar o dialog
                txtMinhaSenha.setText("Sem senha");
                atualizarLista();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void verificarSeFoiChamado() {
        String ultimaChamada = FilaAdapter.getInstance().getUltimaSenhaChamada();
        if (minhaSenha != null && minhaSenha.equals(ultimaChamada)) {
            mostrarNotificacao("Fila digital","Sua Senha foi chamada ");
            mostrarDialogSenhaChamada();
            minhaSenha = null; // limpa a senha
            txtMinhaSenha.setText("Sem senha");
        }
    }

    private void atualizarLista() {
        List<String> filaAtual = FilaAdapter.getInstance().getFila();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filaAtual);
        listView.setAdapter(adapter);
    }



}
