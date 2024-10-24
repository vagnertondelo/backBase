package com.api.app.controllers;
import com.api.app.dtos.LojaDto;
import com.api.app.models.LojaModel;
import com.api.app.services.LojaService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("loja")
public class LojaController {
    final private LojaService lojaService;

    public LojaController(LojaService lojaService) {
        this.lojaService = lojaService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<Object> saveLoja
            (@RequestBody @Valid LojaDto lojaDto){
        var lojaModel = new LojaModel();
        BeanUtils.copyProperties(lojaDto, lojaModel);
        return ResponseEntity.ok().body(
                lojaService.save(lojaModel));
    }
    @GetMapping("/listar")
    public ResponseEntity<List<LojaModel>> getAllLojas(){
        return ResponseEntity.ok().body(
                lojaService.findAll());
    }

    @PostMapping("/editar")
    public ResponseEntity<Object> editarLoja(
            @RequestBody @Valid LojaModel lojaModel) {

        // Busca a loja existente pelo ID dentro do objeto recebido
        Optional<LojaModel> lojaModelOptional = lojaService.findById(lojaModel.getId());

        // Verifica se a loja foi encontrada
        if (lojaModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loja não encontrada.");
        }

        // Obtém a loja existente
        LojaModel lojaExistente = lojaModelOptional.get();

        // Copia as propriedades do objeto recebido, exceto campos indesejados
        BeanUtils.copyProperties(lojaModel, lojaExistente, "id", "produtos");

        // Salva a loja atualizada
        LojaModel lojaAtualizada = lojaService.save(lojaExistente);

        return ResponseEntity.ok(lojaAtualizada);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> apagarLoja(
            @PathVariable(value = "id") UUID id
    ){
        Optional<LojaModel> lojaModelOptional =
                lojaService.findById(id);
        if(lojaModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Loja não encontrado"
            );
        }

        lojaService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                "Loja apagado com sucesso"
        );
    }

}
