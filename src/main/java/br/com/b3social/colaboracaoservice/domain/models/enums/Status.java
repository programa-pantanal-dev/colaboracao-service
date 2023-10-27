package br.com.b3social.colaboracaoservice.domain.models.enums;

public enum Status {
    INSCRICAO_PENDENTE("INSCRICAO PENDENTE"),
    INSCRICAO_DEFERIDA("INSCRICAO DEFERIDA"),
    INSCRICAO_INDEFERIDA("INSCRICAO INDEFERIDA"),
    PRESENTE("PRESENTE"),
    AUSENTE("AUSENTE"),
    CANCELADA("CANCELADA");

    public final String label;

    private Status(String label) {
        this.label = label;
    }
}