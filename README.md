# INF1305-Blockchain-T2

Trabalho 2 da disciplina INF1305-Blockchain da PUC-Rio, 2020.2.

Alunos:

Ana Peixoto
Lola 
Stefano Pereira

## Estrutura

O chaincode (backend) se encontra na pasta "Contract-java" e a aplicação (frontend) na pasta WebApp.

Disclaimer: a apicação é um mock e não se comunica com o back-end. Seu objetivo é apenas prova de conceito.

## Desenvolvimento

O Trabalho foi desenvolvido utilizando o IBM Blockchain Platform extension para VSCode. Toda a parte de virtualização e criação do HyperLedger Fabric Network é tratada pela extensão para que o foco do desenvolvedor possa ser no desenvolvimento do contrato.

Para fins avaliativos e de praticidade, a pasta oculta com as configurações do network criado pela extensão foram adicionados a este repositório, na pasta ".fabric-vs".

## Instalaçao

### Backend

Para rodar o backend, então, é necessário a montagem de um HyperLedger Fabric network com 1 ORG (1 CA, 1 PEER, 1 CHANNEL) através da extensão, e em seguida instalar o contrato na rede. Alternativamente, o folder ".fabric-vs" pode ser copiado para "C:\Users\[usuario]" para a montagem do network, e então apenas a instalação do contrato é necessária.

### Frontend

Para rodar o frontend recomendamos o pacote [lite-server](https://www.npmjs.com/package/lite-server) para Node.js. Basta navegar até a pasta WebApp, instalar o pacote com:

--bash
npm install lite-server --save-dev

Criar, na mesma pasta, o arquivo "package.json" contendo:

--javascript
{
  "scripts": {
    "dev": "lite-server"
  }
}
---

E em seguida rodar o app usando:

--bash
npm run dev


