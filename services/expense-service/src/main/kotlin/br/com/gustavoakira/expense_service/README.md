# Estrutura DDD inicial

## Camadas

- `config`: configurações transversais do Spring.
- `shared`: abstrações compartilhadas entre contextos.
- `expense`: bounded context inicial do serviço.

## Dentro de `expense`

- `domain/model`: entidades, value objects e aggregates.
- `domain/repository`: contratos do domínio.
- `domain/service`: serviços de domínio.
- `application/dto`: comandos e saídas dos casos de uso.
- `application/usecase`: portas de entrada da aplicação.
- `infrastructure/persistence`: adapters de banco e mapeamentos.
- `interfaces/rest`: controllers e contratos HTTP.
