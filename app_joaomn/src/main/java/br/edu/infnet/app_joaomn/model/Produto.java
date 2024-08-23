package br.edu.infnet.app_joaomn.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;

@Entity
@Inheritance
public class Produto extends Item {

    private double preco;
    private boolean disponivel;
    private Integer quantidade;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Fornecedor fornecedor;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Categoria categoria;

    // Getters e Setters
    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", descricao='" + getDescricao() + '\'' +
                ", preco=" + preco +
                ", disponivel=" + disponivel +
                ", categoria=" + categoria +
                ", fornecedor=" + fornecedor +
                ", quantidade=" + quantidade +
                '}';
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
