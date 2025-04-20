package com.example.aplicativodefila;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class SenhaViewModel extends ViewModel {
    private MutableLiveData<List<Senha>> ultimasSenhas = new MutableLiveData<>();
    private MutableLiveData<Senha> senhaGerada = new MutableLiveData<>();

    private ApiClient apiClient = new ApiClient();

    public MutableLiveData<List<Senha>> getUltimasSenhas() {
        return ultimasSenhas;
    }

    public MutableLiveData<Senha> getSenhaGerada() {
        return senhaGerada;
    }

    // Método para gerar uma nova senha
    public void gerarSenha() {
        apiClient.gerarSenha(new ApiClient.Callback<Senha>() {
            @Override
            public void onSuccess(Senha data) {
                senhaGerada.setValue(data);
            }

            @Override
            public void onFailure(Exception e) {
                // Tratar erro, exibir mensagem ao usuário
            }
        });
    }

    // Método para listar as últimas senhas
    public void listarUltimasSenhas() {
        apiClient.listarUltimasSenhas(new ApiClient.Callback<List<Senha>>() {
            @Override
            public void onSuccess(List<Senha> data) {
                ultimasSenhas.setValue(data);
            }

            @Override
            public void onFailure(Exception e) {
                // Tratar erro, exibir mensagem ao usuário
            }
        });
    }
}
