![Frame 2](https://github.com/user-attachments/assets/4ab107c2-bfd8-4645-a6ce-2111cb930945)


# Microserviço de Colaboração

Este microserviço é responsável pela gestão das colaborações dentro do sistema. Ele fornece endpoints para criação, edição, exclusão e consulta de colaborações, garantindo integração eficiente com os demais serviços do ecossistema.

## Tecnologias Utilizadas
- **Linguagem**: [Java]
- **Framework**: [Spring Boot]
- **Banco de Dados**: [PostgreSQL]
- **Autenticação**: [KeyCloak | OAuth2]

## Estrutura do Projeto
```
/colaboracao-service
├── src
│   ├── main
|   |   ├── java/br/com/b3social/colaboracaoservice
|   |   |   ├── annotations
|   |   |   ├── api
|   |   |   |   ├── producers
|   |   |   |   ├── consumers
|   |   |   |   ├── controllers
|   |   |   |   └── dtos
|   |   |   ├── config
|   |   |   ├── domain
|   |   |   |   ├── models
|   |   |   |   |   └── enums
|   |   |   |   ├── repositories
|   |   |   |   └── services
|   |   |   └── security
|   |   |   |   └── expressions
|   |   └── resoures
│   └── test/java/br/com/b3social/colaboracaoservice
└── README.md
```

## Diagramas e Arquitetura

### Diagrama BPMN
Diagrama do fluxo geral da aplicação completa.

![inscrição-de-ação-social](https://github.com/user-attachments/assets/b388bf58-0a01-47e9-a688-047e8d5e70ca)

### Diagrama de micro serviços
Diagrama de micro serviços da aplicação completa.

![image](https://github.com/user-attachments/assets/dc5e22bd-9458-477a-ae50-9c18c404f7e4)

## Endpoints

| Método  | Rota                            | Descrição                                                                 | Autorização |
|---------|---------------------------------|--------------------------------------------------------------------------|-------------|
| `POST`  | `/colaboracao`                  | Realiza o cadastro de uma colaboração.                                   | Sim         |
| `GET`   | `/colaboracao/acaosocial/{id}`  | Retorna todas as inscrições de uma ação social do coordenador logado.   | Sim         |
| `GET`   | `/colaboracao/numero-colaboradores/{id}` | Retorna a quantidade de inscrições em uma ação social.           | Sim         |
| `GET`   | `/colaboracao/colaborador/{id}` | Retorna todas as inscrições de um colaborador.                          | Sim         |
| `GET`   | `/colaboracao/coordenador/{id}` | Retorna todas as inscrições relacionadas às ações sociais de um coordenador. | Sim  |
| `GET`   | `/colaboracao/cancelar/{id}`    | Cancela uma colaboração pelo ID.                                         | Sim         |
| `PUT`   | `/colaboracao/{id}`             | Altera o status da colaboração pelo ID.                                 | Sim         |
