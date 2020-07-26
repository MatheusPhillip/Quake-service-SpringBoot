# Quake Arena 3 Parser Com Spring Boot
> Este projeto visa lêr o conteúdo do arquivo games.log,
> identificando e categorizando suas informações.
> Para cada jogo encontrado ele exibirá algo como:
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
## Instalação
## Clone
- Clone este repositório em sua máquina local usando o link: `https://github.com/MatheusPhillip/Quake-service-SpringBoot.git`
## Downloads
- Os dados serão salvos no MySQL, então tenha certeza que ele está configurado na sua máquina local: `https://www.mysql.com/downloads/`
- As consultas podem ser realizadas no browser via localhost:8080, ou você pode configurar o Postman na sua máquina local: `https://www.postman.com/downloads/`
