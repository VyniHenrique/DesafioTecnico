# Desafio Técnico

API de Clientes e Contas do desafio técnico

## Clone do projeto no GitHub

Clone o projeto seguindo o seguinte comando

```bash
  git clone https://github.com/VyniHenrique/DesafioTecnico
```

## Configure o banco de dados PostgreSQL com as credenciais abaixo ou altere no `application.properties`:

Clone o projeto seguindo o seguinte comando

```
spring.application.name=Desafio Tecnico
spring.datasource.url=jdbc:postgresql://localhost:5433/desafioTecnico
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
```

Você também pode aplicar configurações adicionais na classe `DataBaseConfiguration`.

## Acesse o diretório em que você clonou o repositório e execute o projeto com:

```
mvn spring-boot:run
```

## Execute o projeto e acesse a documentação com Swagger/OpenAPI copiando a seguinte URL:

```
localhost:8080/swagger-ui/index.html
```
