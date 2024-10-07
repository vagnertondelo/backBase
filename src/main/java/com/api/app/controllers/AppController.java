package com.api.app.controllers;


import com.api.app.models.ProdutoModel;
import com.api.app.services.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("app")
public class AppController {

    final private ProdutoService produtoService;
    public AppController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/minharota")
    public String retornaTexto(){
        return "MInha requisição deu certo";
    }

    @PostMapping("/salvar")
    public ResponseEntity<Object> saveProduto
            (@RequestBody ProdutoModel produtoModel){
        return ResponseEntity.ok().body(
                produtoService.save(produtoModel));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProdutoModel>> getAllProdutos(){
        return ResponseEntity.ok().body(
                produtoService.findAll());
    }


}
