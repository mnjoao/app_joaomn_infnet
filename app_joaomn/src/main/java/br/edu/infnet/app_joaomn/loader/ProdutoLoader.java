package br.edu.infnet.app_joaomn.loader;

import br.edu.infnet.app_joaomn.model.Produto;
import br.edu.infnet.app_joaomn.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProdutoLoader {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostConstruct
    public void carregarDados() {
        Resource resource = new ClassPathResource("dados.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String linha;
            List<Produto> produtos = new ArrayList<>();
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                if (campos[0].equals("Produto")) {
                    Produto produto = new Produto();
                    produto.setId(Long.parseLong(campos[1]));
                    produto.setNome(campos[2]);
                    produto.setQuantidade(Integer.parseInt(campos[3]));
                    produto.setPreco(Double.parseDouble(campos[4]));
                    produto.setDisponivel(Boolean.parseBoolean(campos[5]));
                    produtos.add(produto);
                }
            }
            produtoRepository.saveAll(produtos);
            produtos.forEach(produto -> System.out.println(produto));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
