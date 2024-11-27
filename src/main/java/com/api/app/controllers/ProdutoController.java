package com.api.app.controllers;

import com.api.app.dtos.ProdutoDto;
import com.api.app.models.LojaModel;
import com.api.app.models.ProdutoModel;
import com.api.app.repositories.LojaRepository;
import com.api.app.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("produto")
public class ProdutoController {

    final private ProdutoService produtoService;
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }
    //mesma coisa que usar o construtor acima
    @Autowired
    private LojaRepository lojaRepository;

    @GetMapping("/minharota")
    public String retornaTexto(){
        return "MInha requisição deu certo";
    }
    @PostMapping("/salvar")
    public ResponseEntity<Object> saveProduto
            (@RequestBody @Valid ProdutoDto produtoDto, BindingResult result){

        if (result.hasErrors()){
            List<String> menssagensDeErro = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(menssagensDeErro);
        }
        //Verificar se a loja associada ao produto existe
        if(produtoDto.getLojaId() == null){
            return ResponseEntity
                    .badRequest().body("O produto deve estar assicuado a uma loja");
        }

        Optional<LojaModel> loja =
                lojaRepository.findById(produtoDto.getLojaId());
        if(!loja.isPresent()){
            return  ResponseEntity.badRequest().
                    body("LOja não encontra com Id fornecido");
        }

        // Criar o modelo do produto e asocia-lo ao produto existente
        ProdutoModel produtoModel = new ProdutoModel();
        BeanUtils.copyProperties(produtoDto, produtoModel);
        //Associar a loja existente
       produtoModel.setLojaModel(loja.get());
        return ResponseEntity.ok().body(
                produtoService.save(produtoModel));
    }



    @GetMapping("/lista")
    public ResponseEntity<List<ProdutoModel>> getAllProdutos(){
        return ResponseEntity.ok().body(
                produtoService.findAll());
    }



    @GetMapping("/listaprodutoloja/{id}")
    public ResponseEntity<List<ProdutoModel>> getAllProdutosLoja( @PathVariable(value = "id") UUID id){
        return ResponseEntity.ok().body(
                produtoService.findProdutosByLojaId(id));
    }

    @PostMapping("/editar")
    public ResponseEntity<Object> editarProduto(
            @RequestBody @Valid ProdutoDto produtoDto)
    {
        var produtoModel = new ProdutoModel();
        BeanUtils.copyProperties(produtoDto, produtoModel);

        // buscar um ProdutoMOdel no serciço pelo ID recebido
        Optional<ProdutoModel> produtoModelOptional =
                produtoService.findById(produtoModel.getId());
        // Verifica se o produto foi encontrado no banco de dados
        if(!produtoModelOptional.isPresent()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                   "Produto não encontrado"
           );
        }
//        var resposta = produtoService.save(produtoModel);
        // retorna objeto que foi editado
        return ResponseEntity.status(HttpStatus.OK).body(
                produtoService.save(produtoModel)
        );

    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> apagarProduto(
            @PathVariable(value = "id") UUID id
    ){
        Optional<ProdutoModel> produtoModelOptional =
                produtoService.findById(id);
        // Verifica se o produto foi encontrado no banco de dados
        if(!produtoModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Produto não encontrado"
            );
        }
        // se existir vai no sercvice e chama remover
        produtoService.delete(id);

        //Retorna resposta de removido com sucesso
        return ResponseEntity.status(HttpStatus.OK).body(
                "Produto apagado com sucesso"
        );
    }

}
