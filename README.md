# BookTracker

Aplicativo Android desenvolvido para a avaliação parcial da disciplina de
Programação Mobile.

## Objetivo

O aplicativo permite visualizar uma lista simulada de livros e acessar os
detalhes de cada obra.

Na tela de detalhes são exibidas informações como título, autor, editora,
gênero, sinopse e quantidade de páginas. Também é possível alterar o
progresso de leitura.

## Funcionalidades

- Lista de livros
- Navegação entre telas
- Tela de detalhes do livro
- Passagem do ID do livro através de Intent
- Atualização do progresso de leitura
- Dados simulados localmente

## Tecnologias utilizadas

- Kotlin
- Android Views
- XML
- ViewBinding
- RecyclerView
- Material Components

Não são utilizados banco de dados ou API nesta etapa.

## Como executar

1. Clone o repositório.
2. Abra o projeto no Android Studio.
3. Aguarde a sincronização do Gradle.
4. Utilize um dispositivo ou emulador com Android API 33 ou superior.
5. Execute o módulo `app`.

Também é possível compilar pelo terminal no Windows:

```powershell
.\gradlew.bat assembleDebug