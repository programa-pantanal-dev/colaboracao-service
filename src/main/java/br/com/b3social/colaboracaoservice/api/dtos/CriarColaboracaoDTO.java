package br.com.b3social.colaboracaoservice.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CriarColaboracaoDTO {
    @NotBlank()
    private String acaoSocialId;
}
