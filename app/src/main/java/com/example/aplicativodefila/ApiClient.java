package com.example.aplicativodefila;
import com.google.gson.Gson;
import okhttp3.*;
import java.io.IOException;
import java.util.List;

public class ApiClient {
    private static final String BASE_URL = "http://localhost:8081/api"; // Substitua pelo seu URL real

    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();

    // Método para gerar uma nova senha
    public void gerarSenha(final Callback<Senha> callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/gerar-senha")
                .post(RequestBody.create("", MediaType.get("application/json; charset=utf-8")))
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Senha senha = gson.fromJson(response.body().string(), Senha.class);
                    callback.onSuccess(senha);
                } else {
                    callback.onFailure(new IOException("Error: " + response.code()));
                }
            }
        });
    }

    // Método para listar as últimas duas senhas
    public void listarUltimasSenhas(final Callback<List<Senha>> callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/listar-ultimas-senhas")
                .get()
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    List<Senha> senhas = gson.fromJson(response.body().string(), List.class);
                    callback.onSuccess(senhas);
                } else {
                    callback.onFailure(new IOException("Error: " + response.code()));
                }
            }
        });
    }

    // Interface para o callback
    public interface Callback<T> {
        void onSuccess(T data);
        void onFailure(Exception e);
    }
}
