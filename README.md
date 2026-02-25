# SOUNDS API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)

Sounds é uma API REST desenvolvida com Spring Boot que permite autenticação de usuários via JWT, gerenciamento de músicas e playlists, além de controle administrativo com diferentes níveis de acesso.

## Funcionalidades Principais

 - Cadastro e login de usuários com tokens JWT.
 - Registro e gerenciamento de músicas (representação textual, sem arquivos reais).
 - Criação e gerenciamento de playlists.
 - Controle administrativo (gerenciamento de usuários e conteúdos).
 - Documentação com Swagger.
 - Relacionamentos entre usuários, playlists e músicas no banco de dados.
 - Tratamento de exceções.

 ## Controle de Acesso
 - User: pode gerenciar suas próprias playlists e músicas. 
 - Admin: possui permissão para gerenciar usuários e conteúdos da plataforma.
 
## Endpoints Principais

| Método | Rota               | Descrição                  |
|--------|--------------------|----------------------------|
| POST   | /auth/register  | Cadastra um usuário  |
| POST   | /auth/login  | Realiza login  |
| POST   | /music/register  | Registra uma música  |
| GET    | /music/show  | Lista as músicas (com filtro por nome)  |
| POST   | /playlist/create  | Cria uma playlist  |
| GET    | /playlist/show  | Lista as playlists criadas  |
| POST   | /playlist/insert/music  | Adiciona músicas a uma playlist  |

Obs: Todos os endpoints (exceto login e registro) precisam de autenticação via token JWT.

 > Outros endpoints estarão disponíveis na Documentação do Swagger em: http://localhost:8080/swagger-ui/index.html

## Tecnologias Utilizadas

 - Java (Versão 17)
 - Spring Boot
 - Spring Security
 - Spring Doc (Springdoc OpenAPI)
 - Spring Data JPA
 - Banco de Dados (MySQL) 
 - JSON Web Token (Token JWT)
 - Bean Validation
 - Lombok

 ## Instalação e Uso

 ```bash
#Clonar o arquivo do repositório
git clone git@github.com:nathanokaz/sounds-api.git
 ```

  - Crie um banco de dados MySQL local (ex: sounds_db).
  - Atualize o `application.properties` com a URL do banco de dados.

Endpoints podem ser testados em: http://localhost:8080/swagger-ui/index.html ou utilize ferramentas como Insomnia para enviar requisições HTTP.

 
