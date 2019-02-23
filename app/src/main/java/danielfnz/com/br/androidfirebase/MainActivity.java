package danielfnz.com.br.androidfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import danielfnz.com.br.androidfirebase.model.Pessoa;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Pessoa pessoaSelecionada;

    ListView listaViewPessoas;
    EditText nomeP, apelP, emailP, passwdP;

    private List<Pessoa> listaPessoa = new ArrayList<>();
    ArrayAdapter<Pessoa> arrayAdapterPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.inicializarFirebase();

        this.listaViewPessoas = (ListView) findViewById(R.id.listaPessoas);

        nomeP = (EditText) findViewById(R.id.nome);
        apelP = (EditText) findViewById(R.id.apelido);
        emailP = (EditText) findViewById(R.id.email);
        passwdP = (EditText) findViewById(R.id.senha);

        listaViewPessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pessoaSelecionada = (Pessoa) parent.getItemAtPosition(position);
                nomeP.setText(pessoaSelecionada.getNome());
                apelP.setText(pessoaSelecionada.getApelido());
                emailP.setText(pessoaSelecionada.getEmail());
                passwdP.setText(pessoaSelecionada.getSenha());
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        this.getPessoas();
    }


    private void getPessoas() {
        databaseReference.child("Pessoa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaPessoa.clear();
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    Pessoa p = objSnapshot.getValue(Pessoa.class);
                    listaPessoa.add(p);

                    arrayAdapterPessoa = new ArrayAdapter<Pessoa>(MainActivity.this, android.R.layout.simple_list_item_1, listaPessoa);
                    listaViewPessoas.setAdapter(arrayAdapterPessoa);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void limparcampos() {
        nomeP.setText("");
        apelP.setText("");
        emailP.setText("");
        passwdP.setText("");
    }

    private void valida() {
        String nome = nomeP.getText().toString();
        String apelido = apelP.getText().toString();
        String email = emailP.getText().toString();
        String senha = passwdP.getText().toString();

        if(nome.equals("")){
            nomeP.setError("Campo Requerido");
        }
        if(apelido.equals("")){
            apelP.setError("Campo Requerido");
        }
        if(email.equals("")){
            emailP.setError("Campo Requerido");
        }
        if(senha.equals("")){
            passwdP.setError("Campo Requerido");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        String nome = nomeP.getText().toString();
        String apelido = apelP.getText().toString();
        String email = emailP.getText().toString();
        String senha = passwdP.getText().toString();

        switch (item.getItemId()){
            case R.id.icon_add: {
                if (nome.equals("") || apelido.equals("") || email.equals("")) {
                    valida();
                } else {
                    Pessoa p = new Pessoa();
                    p.setUID(UUID.randomUUID().toString());
                    p.setNome(nome);
                    p.setApelido(apelido);
                    p.setEmail(email);
                    p.setSenha(senha);

                    databaseReference.child("Pessoa").child(p.getUID()).setValue(p);
                    Toast.makeText(this,"Adicionado",Toast.LENGTH_LONG).show();
                    limparcampos();
                }

                break;
            }

            case R.id.icon_del:{

                Pessoa p = new Pessoa();
                p.setUID(pessoaSelecionada.getUID());
                databaseReference.child("Pessoa").child(p.getUID()).removeValue();
                Toast.makeText(this,"Registro Excluído",Toast.LENGTH_LONG).show();
                limparcampos();
                break;
            }

            case R.id.icon_save:{

                Pessoa p = new Pessoa();
                p.setUID(pessoaSelecionada.getUID());
                p.setNome(nomeP.getText().toString());
                p.setApelido(apelP.getText().toString());
                p.setEmail(emailP.getText().toString());
                p.setSenha(passwdP.getText().toString());

                databaseReference.child("Pessoa").child(p.getUID()).setValue(p);
                Toast.makeText(this,"Alterado",Toast.LENGTH_LONG).show();
                limparcampos();

                break;
            }

            default:break;
        }
        return true;
    }


}
