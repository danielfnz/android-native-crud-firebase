package danielfnz.com.br.androidfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ListView listaViewPessoas;
    Button btnAdicionar;
    EditText nomeP, apelP, emailP, passwdP;

    private List<Pessoa> listaPessoa = new ArrayList<>();
    ArrayAdapter<Pessoa> arrayAdapterPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.inicializarFirebase();

        this.listaViewPessoas = (ListView) findViewById(R.id.listaPessoas);
        this.btnAdicionar = (Button) findViewById(R.id.adicionar);

        nomeP = (EditText) findViewById(R.id.nome);
        apelP = (EditText) findViewById(R.id.apelido);
        emailP = (EditText) findViewById(R.id.email);
        passwdP = (EditText) findViewById(R.id.senha);

        this.btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nomeP.getText().toString().equals("") || apelP.getText().toString().equals("") || emailP.getText().toString().equals("")) {
                    valida();
                } else {
                    addPessoa();
                }
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        this.getPessoas();
    }


    private void addPessoa(){
        String nome = nomeP.getText().toString();
        String apelido = apelP.getText().toString();
        String email = emailP.getText().toString();
        String senha = passwdP.getText().toString();

        Pessoa pessoa = new Pessoa();

        pessoa.setNome(nome);
        pessoa.setApelido(apelido);
        pessoa.setEmail(email);
        pessoa.setSenha(senha);

        databaseReference.child("pessoa").push().setValue(pessoa).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Adicionado",Toast.LENGTH_LONG).show();
                limparcampos();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Falha ao adicionar",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getPessoas() {
        databaseReference.child("pessoa").addValueEventListener(new ValueEventListener() {
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

}
