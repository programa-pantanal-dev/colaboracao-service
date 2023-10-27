package br.com.b3social.colaboracaoservice.api.dtos;

import br.com.b3social.colaboracaoservice.domain.models.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarColaboracaoDTO {
    private Status status;
}
