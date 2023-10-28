create table colaboracao (
    id TEXT UNIQUE NOT NULL,
    status VARCHAR(20) NOT NULL,
    acao_social_id TEXT NOT NULL,
    colaborador_id TEXT NOT NULL,
    colaborador_nome TEXT NOT NULL,
    colaborador_email VARCHAR(50),
    coordenador_id TEXT NOT NULL,
    PRIMARY KEY (id)
);