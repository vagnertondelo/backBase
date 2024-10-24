package com.api.app.models;
import jakarta.persistence.*;
import lombok.Data;

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



}
