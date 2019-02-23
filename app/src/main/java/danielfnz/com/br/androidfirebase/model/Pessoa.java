package danielfnz.com.br.androidfirebase.model;

/**
 * Created by Alunoinf_2 on 22/02/2019.
 */

public class Pessoa {

    private String nome;
    private String apelido;
    private String email;
    private String senha;

    public Pessoa() {

    }

    public Pessoa(String nome, String apelido, String email, String senha) {
        this.nome = nome;
        this.apelido = apelido;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
