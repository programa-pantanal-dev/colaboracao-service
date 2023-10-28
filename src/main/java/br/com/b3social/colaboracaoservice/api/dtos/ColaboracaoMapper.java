package br.com.b3social.colaboracaoservice.api.dtos;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import br.com.b3social.colaboracaoservice.domain.models.Colaboracao;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
componentModel = "spring")
public interface ColaboracaoMapper {
    Colaboracao DtoToInscricao(CriarColaboracaoDTO criarInscricaoDTO);
    Colaboracao DtoToInscricao(AtualizarColaboracaoDTO atualizarInscricaoDTO);
    RetornarColaboracaoDTO InscricaoToDto(Colaboracao inscricao);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(@MappingTarget Colaboracao inscricao, AtualizarColaboracaoDTO atualizarInscricaoDTO);
}
