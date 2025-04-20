package com.example.aplicativodefila;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UltimaSenhaAdapter extends BaseAdapter {

    private Context context;
    private List<Senha> senhas;

    public UltimaSenhaAdapter(Context context, List<Senha> senhas) {
        this.context = context;
        this.senhas = senhas;
    }

    @Override
    public int getCount() {
        return senhas.size();
    }

    @Override
    public Object getItem(int position) {
        return senhas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        Senha senha = senhas.get(position);

        // Exibe o número da senha
        textView.setText("Senha " + senha.getNumero());
        return convertView;
    }
}
