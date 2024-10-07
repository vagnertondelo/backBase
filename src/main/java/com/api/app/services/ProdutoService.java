package com.api.app.services;

import com.api.app.models.ProdutoModel;
import com.api.app.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

        final ProdutoRepository produtoRepository;
        public ProdutoService(ProdutoRepository produtoRepository) {
            this.produtoRepository = produtoRepository;
        }
    @Transactional
    public ProdutoModel save(ProdutoModel produtoModel) {
        return produtoRepository.save(produtoModel);
    }

    public List<ProdutoModel> findAll() {
            return produtoRepository.findAll();
    }

}
