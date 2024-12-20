package br.com.b3social.colaboracaoservice.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.b3social.colaboracaoservice.domain.models.Colaboracao;

import java.util.List;
import java.util.Optional;

public interface ColaboracaoRepository extends JpaRepository<Colaboracao, String>{
    @Query("SELECT COUNT(e) FROM colaboracao e WHERE e.acaoSocialId = :acaoSocialId AND e.status = 'INSCRICAO_DEFERIDA'")
    Optional<Integer> countByAcaoSocialId(@Param("acaoSocialId") String acaoSocialId);
    List<Colaboracao> findByAcaoSocialIdAndCoordenadorId(String acaoSocialId, String coordenadorId);
    List<Colaboracao> findByColaboradorId(String colabodadorId);
    List<Colaboracao> findByCoordenadorId(String coordenadorId);
    Optional<Colaboracao> findByAcaoSocialIdAndColaboradorId(String id, String colaboradorId);
    Optional<Colaboracao> findById(String id);
}
