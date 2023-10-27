package br.com.b3social.colaboracaoservice.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.b3social.colaboracaoservice.domain.models.Colaboracao;

import java.util.List;
import java.util.Optional;

public interface ColaboracaoRepository extends JpaRepository<Colaboracao, String>{
    List<Colaboracao> findByAcaoSocialIdAndCoordenadorId(String acaoSocialId, String coordenadorId);
    List<Colaboracao> findByColaboradorId(String colabodadorId);
    List<Colaboracao> findByCoordenadorId(String coordenadorId);
    Optional<Colaboracao> findById(String id);
}
