package BD.Entidades;

import java.time.LocalTime;
import java.util.ArrayList;

public class Anuncio {
    private int cod;
    private String titulo, descricao, diasTrabalho;
    private LocalTime horInicio, horFim;
    private Usuario usuario;
    private Categoria categoria;
    private ArrayList<Foto> fotos;
    private ArrayList<Mensagem> mensagens;

    public Anuncio(int cod, String titulo, String descricao, String diasTrabalho, LocalTime horInicio, LocalTime horFim, Usuario usuario, Categoria categoria, ArrayList<Foto> fotos, ArrayList<Mensagem> mensagens) {
        this.cod = cod;
        this.titulo = titulo;
        this.descricao = descricao;
        this.diasTrabalho = diasTrabalho;
        this.horInicio = horInicio;
        this.horFim = horFim;
        this.usuario = usuario;
        this.categoria = categoria;
        this.fotos = fotos;
        this.mensagens = mensagens;
    }

    public Anuncio(String titulo, String descricao, String diasTrabalho, LocalTime horInicio, LocalTime horFim, Usuario usuario, Categoria categoria, ArrayList<Foto> fotos, ArrayList<Mensagem> mensagens) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.diasTrabalho = diasTrabalho;
        this.horInicio = horInicio;
        this.horFim = horFim;
        this.usuario = usuario;
        this.categoria = categoria;
        this.fotos = fotos;
        this.mensagens = mensagens;
    }

    public Anuncio(String titulo, String descricao, String diasTrabalho, LocalTime horInicio, LocalTime horFim, Usuario usuario, Categoria categoria, ArrayList<Foto> fotos) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.diasTrabalho = diasTrabalho;
        this.horInicio = horInicio;
        this.horFim = horFim;
        this.usuario = usuario;
        this.categoria = categoria;
        this.fotos = fotos;
    }
    
    @Override
    public String toString(){
        String strFotos = "";
        if(fotos.size() > 0){
        strFotos += fotos.get(0).getPath();
            for(int i = 1; i < fotos.size(); i++)
                strFotos += "$" + fotos.get(i).getPath();
        }
        return String.format(
                "%d$%s$%s$%s$%s$%s$%s$%s$%s",
                cod, titulo, descricao, diasTrabalho,
                horInicio.toString(), horFim.toString(), usuario.getLogin(), categoria.getCod(), strFotos
        );
    }
    
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDiasTrabalho() {
        return diasTrabalho;
    }

    public void setDiasTrabalho(String diasTrabalho) {
        this.diasTrabalho = diasTrabalho;
    }

    public LocalTime getHorInicio() {
        return horInicio;
    }

    public void setHorInicio(LocalTime horInicio) {
        this.horInicio = horInicio;
    }

    public LocalTime getHorFim() {
        return horFim;
    }

    public void setHorFim(LocalTime horFim) {
        this.horFim = horFim;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public ArrayList<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<Foto> fotos) {
        this.fotos = fotos;
    }

    public ArrayList<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(ArrayList<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    
}
