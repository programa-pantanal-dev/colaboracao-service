package br.com.b3social.colaboracaoservice.api.dtos;

import lombok.Data;

@Data
public class EmailDto {
    private String emailPara;
    private String assunto;
    private String texto;
}
