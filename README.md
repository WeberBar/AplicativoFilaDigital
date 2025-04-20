# 📲 Fila Digital - Aplicativo Android

Este é um aplicativo Android criado com Java que se conecta a uma **API Laravel** para gerenciamento de **senhas digitais em filas**. Ideal para ser utilizado em estabelecimentos como **bancos, clínicas, restaurantes e demais locais com atendimento por ordem de chegada**.

---

## 🔗 API Utilizada

### 🧠 Sobre a Fila Digital - API

A [Fila Digital - API](https://github.com/Leon14789/API-UPX-V) é uma solução inovadora desenvolvida para otimizar o gerenciamento de filas em diversos ambientes. Esta API foi construída com **Laravel** e utiliza **MySQL** como banco de dados, permitindo:

- Criação de senhas digitais
- Chamada de clientes
- Verificação de quem é o próximo na fila
- Notificações quando for a vez do usuário
- Histórico completo dos atendimentos

### 📌 Funcionalidades da API

- **Geração de Senha Digital**  
  Endpoint: `POST /api/gerar-senha`

- **Chamada de Senha**  
  Endpoint: `POST /api/chamar-senha`

- **Listar Últimas Senhas Chamadas**  
  Endpoint: `GET /api/listar-ultimas-senhas`

- **Verificar Próxima Senha**  
  Endpoint: `GET /api/proxima`

- **Atender Senha**  
  Endpoint: `POST /api/atender/{id}`

- **Histórico de Atendimentos**  
  Endpoint: `GET /api/historico`

📁 Repositório da API:  
👉 [https://github.com/Leon14789/API-UPX-V](https://github.com/Leon14789/API-UPX-V)

---

## 🧩 Funcionalidades do App Android

- **Pegar minha senha**: Gera uma nova senha e exibe ao usuário.
- **Ver últimas senhas chamadas**: Lista as duas senhas mais recentes que foram chamadas.
- **Interface intuitiva e leve**: Layout limpo e responsivo.

---

## 📷 Interface do App

A interface foi construída com XML e possui os seguintes elementos:

- Logo centralizado
- Botão de gerar senha
- Texto com a senha atual
- Lista com as últimas senhas chamadas (`ListView`)

---

## 🛠️ Tecnologias Utilizadas

- Java
- Android SDK
- OkHttp (cliente HTTP)
- ViewModel + LiveData (MVVM)
- XML Layouts
- Laravel (Backend)
- MySQL (Banco de Dados)

---

