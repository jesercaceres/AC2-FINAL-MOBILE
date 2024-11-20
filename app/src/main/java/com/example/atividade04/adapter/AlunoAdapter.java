package com.example.atividade04.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atividade04.R;
import com.example.atividade04.models.Aluno;

import java.util.List;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder> {
    private final List<Aluno> alunos;

    public AlunoAdapter(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlunoViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_aluno, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {
        Aluno aluno = alunos.get(position);
        holder.ra.setText(String.valueOf(aluno.getRa()));
        holder.nome.setText(aluno.getNome());
        holder.cep.setText(aluno.getCep());
        holder.logradouro.setText(aluno.getLogradouro());
        holder.complemento.setText(aluno.getComplemento());
        holder.bairro.setText(aluno.getBairro());
        holder.cidade.setText(aluno.getLocalidade());
        holder.uf.setText(aluno.getUf());
    }

    @Override
    public int getItemCount() {
        return alunos != null ? alunos.size() : 0;
    }

    public static class AlunoViewHolder extends RecyclerView.ViewHolder {
        TextView ra, nome, cep, logradouro, complemento, bairro, cidade, uf;

        public AlunoViewHolder(View itemView) {
            super(itemView);
            ra = itemView.findViewById(R.id.raAluno);
            nome = itemView.findViewById(R.id.nomeAluno);
            cep = itemView.findViewById(R.id.cepAluno);
            logradouro = itemView.findViewById(R.id.logradouroAluno);
            complemento = itemView.findViewById(R.id.complementoAluno);
            bairro = itemView.findViewById(R.id.bairroAluno);
            cidade = itemView.findViewById(R.id.cidadeAluno);
            uf = itemView.findViewById(R.id.ufAluno);
        }
    }
}

