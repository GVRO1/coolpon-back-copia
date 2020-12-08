package br.com.coolpon.coolpon.api.model;

public class Cnpj {

    private String cep;
    private String logadouro;
    private String numero;
    private String bairro;
    private String municipio;
    private String telefone;
    private String uf;
    private String complemento;
    private String status;
    private String error;
    private String nome;
    private String email;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setLogadouro(String logadouro) {
        this.logadouro = logadouro;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCep() {
        return cep;
    }

    public String getLogadouro() {
        return logadouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getUf() {
        return uf;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getStatus() {
        return status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
