package BD.Entidades;

import java.time.LocalDate;

public class Usuario {
    private String login, senha, nome, CPF, email, telefone, endereco;
    private int nivel;
    private String foto;
    private LocalDate dtNascimento;
    private Servico servico;

    public Usuario(String login, String senha, String nome, String CPF, String email, String telefone, String endereco, int nivel, String foto, LocalDate dtNascimento, Servico servico) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.CPF = CPF;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.nivel = nivel;
        this.foto = foto;
        this.dtNascimento = dtNascimento;
        this.servico = servico;
    }

    @Override
    public String toString(){
        return String.format(
            "%s$%s$%s$%s$%s$%s$%s$%d$%s$%s$%d",
            login, senha, nome, CPF, email, telefone, endereco, nivel,
            foto, dtNascimento.toString(), servico.getCod()
        );
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }
}
