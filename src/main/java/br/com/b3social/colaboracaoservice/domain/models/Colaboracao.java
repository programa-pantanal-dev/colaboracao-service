package br.com.b3social.colaboracaoservice.domain.models;

import br.com.b3social.colaboracaoservice.domain.models.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="colaboracao")
@Table(name="colaboracao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Colaboracao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String acaoSocialId;

    private String colaboradorId;

    private String colaboradorNome;

    private String coordenadorId;
}
