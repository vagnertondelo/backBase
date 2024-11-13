package com.api.app.dtos;
import com.api.app.models.ProdutoModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.UUID;
@Data
public class LojaDto {

    private UUID id;

    @NotBlank(message = "Razão Social é obrigatória")
    private String razaoSocial;

    private List<ProdutoModel> produtos;

}
