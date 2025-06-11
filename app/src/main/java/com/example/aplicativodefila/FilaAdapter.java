package com.example.aplicativodefila;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilaAdapter {

    public interface Callback {

    }

    private static final FilaAdapter instance = new FilaAdapter();

    private final List<String> fila = new ArrayList<>();
    private final HashMap<String, Callback> clientes = new HashMap<>();
    private String senhaAtual = null;

    private FilaAdapter() {}

    public static FilaAdapter getInstance() {
        return instance;
    }

    public String adicionarSenha(Callback callback) {
        String senha = "S" + (fila.size() + 1);
        fila.add(senha);
        clientes.put(senha, callback);
        return senha;
    }

    public List<String> getFila() {
        return new ArrayList<>(fila);
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }
    private String ultimaSenhaChamada = null;

    public String getUltimaSenhaChamada() {
        return ultimaSenhaChamada;
    }


    public String chamarProxima(Context context) {
        if (fila.isEmpty()) {
            return null;
        }

        senhaAtual = fila.remove(0);
        ultimaSenhaChamada = senhaAtual;

        Callback callback = clientes.remove(senhaAtual);
        if (callback != null) {

            Toast.makeText(context, "Chamando senha: " + senhaAtual, Toast.LENGTH_SHORT).show();
        }

        return senhaAtual;
    }


    public boolean isMinhaSenha(String senha, Callback callback) {
        return clientes.get(senha) == callback;
    }
}
