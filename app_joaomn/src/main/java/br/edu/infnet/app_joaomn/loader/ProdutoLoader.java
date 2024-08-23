package br.edu.infnet.app_joaomn.loader;

import br.edu.infnet.app_joaomn.model.Produto;
import br.edu.infnet.app_joaomn.model.Categoria;
import br.edu.infnet.app_joaomn.model.Fornecedor;
import br.edu.infnet.app_joaomn.repository.ProdutoRepository;
import br.edu.infnet.app_joaomn.repository.CategoriaRepository;
import br.edu.infnet.app_joaomn.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProdutoLoader {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @PostConstruct
    public void carregarDados() {
        Resource resource = new ClassPathResource("dados.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String linha;
            List<Produto> produtos = new ArrayList<>();
            Map<Long, Categoria> categorias = new HashMap<>();
            Map<Long, Fornecedor> fornecedores = new HashMap<>();
            
            // Carregar dados
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                if (campos[0].equals("Categoria")) {
                    Categoria categoria = new Categoria();
                    categoria.setId(Long.parseLong(campos[1]));
                    categoria.setNome(campos[2]);
                    categoria.setDescricao(campos[3]);
                    categorias.put(categoria.getId(), categoria);
                } else if (campos[0].equals("Fornecedor")) {
                    Fornecedor fornecedor = new Fornecedor();
                    fornecedor.setId(Long.parseLong(campos[1]));
                    fornecedor.setNome(campos[2]);
                    fornecedor.setNumeroProdutos(Integer.parseInt(campos[3]));
                    fornecedores.put(fornecedor.getId(), fornecedor);
                } else if (campos[0].equals("Produto")) {
                    Produto produto = new Produto();
                    produto.setId(Long.parseLong(campos[1]));
                    produto.setNome(campos[2]);
                    produto.setQuantidade(Integer.parseInt(campos[3]));
                    produto.setPreco(Double.parseDouble(campos[4]));
                    produto.setDisponivel(Boolean.parseBoolean(campos[5]));

                    // Associe a categoria e o fornecedor se disponíveis
                    if (campos.length > 6) {
                        Long categoriaId = Long.parseLong(campos[6]);
                        Categoria categoria = categorias.get(categoriaId);
                        produto.setCategoria(categoria);
                    }
                    if (campos.length > 7) {
                        Long fornecedorId = Long.parseLong(campos[7]);
                        Fornecedor fornecedor = fornecedores.get(fornecedorId);
                        produto.setFornecedor(fornecedor);
                    }
                    produtos.add(produto);
                }
            }

            // Salvar categorias e fornecedores primeiro
            categoriaRepository.saveAll(categorias.values());
            fornecedorRepository.saveAll(fornecedores.values());

            // Salvar produtos com categorias e fornecedores já associados
            produtoRepository.saveAll(produtos);

            produtos.forEach(produto -> System.out.println(produto));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
