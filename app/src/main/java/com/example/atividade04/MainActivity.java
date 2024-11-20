package com.example.atividade04;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.atividade04.models.Aluno;
import com.example.atividade04.models.Endereco;
import com.example.atividade04.services.AlunoService;
import com.example.atividade04.services.ApiClient;
import com.example.atividade04.services.ViaCepService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView txtRa, txtNome, txtCep, txtLogradouro, txtComplemento, txtBairro, txtCidade, txtUf;

    Button btnCadastraAluno, btnVerAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        txtRa = findViewById(R.id.edtRa);
        txtNome = findViewById(R.id.edtNome);
        txtCep = findViewById(R.id.edtCep);
        txtLogradouro = findViewById(R.id.edtLogradouro);
        txtComplemento = findViewById(R.id.edtComplemento);
        txtBairro = findViewById(R.id.edtBairro);
        txtCidade = findViewById(R.id.edtCidade);
        txtUf = findViewById(R.id.edtUf);
        btnCadastraAluno = findViewById(R.id.btnCadastrarAluno);
        btnVerAlunos = findViewById(R.id.btnVerAlunos);


        txtCep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String cep = s.toString().trim();
                if (cep.length() == 8) {
                    buscaEnderecoPorCep(cep);
                }
            }
        });


        btnCadastraAluno.setOnClickListener(v -> cadastraAluno());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnVerAlunos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListaAlunosActivity.class);
            startActivity(intent);
        });
    }


    private void buscaEnderecoPorCep(String cep) {
        ViaCepService viaCepService = ApiClient.getViaCepService();
        Call<Endereco> call = viaCepService.buscarCep(cep);

        call.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Endereco endereco = response.body();
                    txtLogradouro.setText(endereco.getLogradouro());
                    txtComplemento.setText(endereco.getComplemento());
                    txtBairro.setText(endereco.getBairro());
                    txtCidade.setText(endereco.getLocalidade());
                    txtUf.setText(endereco.getUf());
                } else {
                    Toast.makeText(MainActivity.this, "Endereço não encontrado.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Log.e("API_ERROR", "Erro ao buscar endereço: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Erro na conexão com o ViaCEP.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void cadastraAluno() {
        int ra = Integer.parseInt(txtRa.getText().toString());
        String nome = txtNome.getText().toString().trim();
        String cep = txtCep.getText().toString().trim();
        String logradouro = txtLogradouro.getText().toString().trim();
        String complemento = txtComplemento.getText().toString().trim();
        String bairro = txtBairro.getText().toString().trim();
        String cidade = txtCidade.getText().toString().trim();
        String uf = txtUf.getText().toString().trim();

        Aluno aluno = new Aluno(ra, nome, cep, logradouro, complemento, bairro, cidade, uf);

        AlunoService alunoService = ApiClient.getAlunoService();
        Call<Aluno> call = alunoService.adicionarAlunos(aluno);

        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Aluno cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    txtRa.setText("");
                    txtNome.setText("");
                    txtCep.setText("");
                    txtLogradouro.setText("");
                    txtComplemento.setText("");
                    txtBairro.setText("");
                    txtCidade.setText("");
                    txtUf.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao cadastrar aluno.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Log.e("API_ERROR", "Erro ao cadastrar aluno: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Erro  MockAPI.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}