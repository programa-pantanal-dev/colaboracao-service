package br.com.b3social.colaboracaoservice.api.dtos;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AcaoSocialDTO {
    String id;
    String titulo;
    String resumo;
    String descricao;
    Integer nivel;
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate dataInicio;
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate dataTermino;
    String banner;
    String coordenadorId;
    String coordenadorNome;
    String coordenadorEmail;
    String status;
    String createdBy;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date createdAt;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date deletedAt;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date updatedAt;
}