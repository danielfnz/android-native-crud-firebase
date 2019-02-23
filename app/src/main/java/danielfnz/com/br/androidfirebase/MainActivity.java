package danielfnz.com.br.androidfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import danielfnz.com.br.androidfirebase.model.Pessoa;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.inicializarFirebase();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        this.adicionarPessoa();
    }


    private void adicionarPessoa(){
        Pessoa pessoa = new Pessoa();

        pessoa.setNome("Daniel Leonardo");
        pessoa.setApelido("Daniel");
        pessoa.setEmail("daniel.fnz@hotmail.com");
        pessoa.setSenha("123456");

        databaseReference.child("pessoa").push().setValue(pessoa);
    }

}
