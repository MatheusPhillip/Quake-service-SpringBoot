# Quake Arena 3 Parser Utilizando Spring Boot
> Este projeto tem como objetivo a construção de uma API 
> para realização de busca de um determinado jogo por ID.
> Um jogo possue essas características:
```
game_1: {
    total_kills: 45;
    players: ["Dono da bola", "Isgalamido", "Zeh"]
    kills: {
      "Dono da bola": 5,
      "Isgalamido": 18,
      "Zeh": 20
    }
  }
```
---
## Solução Proposta
> Este projeto tem como base https://github.com/MatheusPhillip/quake-service.
> É basicamente o mesmo projeto, mas adaptado com as normas e organização do Spring Boot,
> por isso decidi criar este novo projeto. 
> Optei por utilizar o Spring Boot, pois eu tinha um pouco de familiaridade. 
> E optei pelo uso do MySQL, pois já tinha trabalhado com ele. 
---
## Instalação
## Clone
- Clone este repositório em sua máquina local usando o link: `https://github.com/MatheusPhillip/Quake-service-SpringBoot.git`

## Downloads
- Os dados serão salvos no MySQL, então tenha certeza que ele está configurado na sua máquina local: `https://www.mysql.com/downloads/`
- As consultas podem ser realizadas no browser via localhost:8080, ou você pode configurar o Postman na sua máquina local: `https://www.postman.com/downloads/`

## Setup
- Configurando conexão com o MySQL: <br />
__Passo 1__ <br />
Abra o arquivo `application.properties` localizado na pasta: `\Quake-service-SpringBoot\src\main\resources` <br />
__Passo 2__ <br />
Na linha __1__ informe a URL do seu banco de dados, por exemplo: <br />
`jdbc:mysql://localhost:3306`/quakegames?createDatabaseIfNotExist=true&useSSL=false&useTimezone=true&serverTimezone=UTC <br />
__Passo 3__ <br />
Nas linhas __3__ e __4__ informe seu usuário e senha do banco de dados, por exemplo: <br />
```
ng.datasource.username=USUARIO
spring.datasource.password=SENHA
```

- Inicializando o projeto: <br />
__Passo 1__ <br />
Inicialize o projeto através da classe principal: `QuakeparseApplication.java` <br />
Após a inicialização será criada uma base de dados denominada __quakegames__ no MySQL.

- Buscar um jogo por ID: <br />
__Passo 1__ <br />
Com o projeto inicializado, abra o browser ou Postman(GET) e digite a URL `localhost:8080/game/getById/` <br />
Procurando o jogo de ID 2 por exemplo: `localhost:8080/game/getById/2`
