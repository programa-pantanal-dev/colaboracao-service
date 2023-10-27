package br.com.b3social.colaboracaoservice.api.dtos;

import br.com.b3social.colaboracaoservice.domain.models.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RetornarColaboracaoDTO {
    private String id;
    private Status status;
    private String acaoSocialId;
    private String colaboradorId;
    private String colaboradorNome;
    private String coordenadorId;
}
