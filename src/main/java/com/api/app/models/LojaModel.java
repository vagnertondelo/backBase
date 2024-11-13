package com.api.app.models;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "TB_LOJA")
@Data
public class LojaModel {
        private  static final long serialVersionUID = 1;
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID id;

        @Column(nullable = false)
        private String razaoSocial;

        @OneToMany(mappedBy = "lojaModel", cascade = CascadeType.ALL )
        // INdica que essa parte da relação deve ser Serializada
        @JsonManagedReference
        private List<ProdutoModel> produtos = new ArrayList<>();



}
