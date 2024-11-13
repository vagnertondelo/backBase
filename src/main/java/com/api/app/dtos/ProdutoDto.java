package com.api.app.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class ProdutoDto {

    private UUID id;

   @NotBlank
   @Size(min = 2, max = 50)
    private String nome;
    @NotBlank
    private String descricao;

    @NotNull(message = "O preço é obrigatorio")
    @Positive(message = "Valor tem que ser positivo")
    private Double preco;

    @NotNull(message = "O Id da loja é obrigatorio")
    private UUID lojaId;

}
