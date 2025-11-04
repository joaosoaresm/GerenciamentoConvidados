# 🎉 Gerenciamento de Convidados

Aplicativo Android para gerenciar lista de convidados para eventos, festas e celebrações.

## 📱 Sobre o Projeto

Um app simples e intuitivo desenvolvido em **Kotlin** com **Jetpack Compose** que permite adicionar, editar e organizar convidados com status de confirmação, grupos e preferências de bebidas.

## ✨ Funcionalidades

- ✅ **Adicionar convidados** com informações detalhadas
- 🎨 **Sistema de status** com cores visuais:
  - 🟢 **SIM** - Confirmado
  - 🟠 **TALVEZ** - Indeciso
  - 🔴 **NÃO** - Não comparecerá
- 📊 **Contador automático** de convidados confirmados
- 👥 **Organização por grupos** (família, amigos, trabalho, etc.)
- 🍷 **Registro de preferências** de bebidas
- 📝 **Notas personalizadas** para cada convidado
- ✏️ **Edição de status** após cadastro
- 🗑️ **Remoção de convidados**
- 💾 **Persistência local** com Room Database

## 🛠️ Tecnologias Utilizadas

- **Kotlin** - Linguagem de programação
- **Jetpack Compose** - UI moderna e declarativa
- **Room Database** - Persistência de dados local
- **Material Design 3** - Design system
- **MVVM Architecture** - Arquitetura de software
- **Coroutines & Flow** - Programação assíncrona

## 📋 Pré-requisitos

- Android Studio Hedgehog (ou superior)
- JDK 11+
- SDK mínimo: Android 7.0 (API 24)
- SDK alvo: Android 14 (API 36)

## 🚀 Como Executar

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/gerenciamento-convidados.git
```

2. Abra o projeto no Android Studio

3. Sincronize as dependências do Gradle:
   - `Build → Clean Project`
   - `Build → Rebuild Project`

4. Execute o app:
   - Conecte um dispositivo Android ou inicie um emulador
   - Clique em `Run` (▶️) ou pressione `Shift + F10`

## 📂 Estrutura do Projeto

```
app/src/main/java/com/example/gerenciamentoconvidados/
├── MainActivity.kt              # Tela principal e UI
├── data/
│   ├── Guest.kt                 # Entidade do banco de dados
│   ├── GuestDao.kt              # Interface de acesso aos dados
│   └── AppDatabase.kt           # Configuração do Room
└── viewmodel/
    └── GuestViewModel.kt        # Lógica de negócio
```

## 🎨 Screenshots

<!-- Adicione prints do app aqui -->

## 📝 Como Usar

### Adicionar um Convidado

1. Clique em **"Mostrar"** no card "Adicionar Convidado"
2. Preencha o nome (obrigatório)
3. Selecione o status: **SIM**, **TALVEZ** ou **NÃO**
4. Adicione informações opcionais (grupo, bebidas, notas)
5. Clique em **"Adicionar Convidado"**

### Editar Status

1. Localize o convidado na lista
2. Clique em **"Editar Status"**
3. Selecione o novo status
4. Clique em **"Confirmar"**

### Visualizar Estatísticas

- O card no topo mostra:
  - Número de convidados confirmados (status = SIM)
  - Total de convidados cadastrados

## 🔧 Dependências Principais

```kotlin
// Compose
implementation("androidx.compose.ui:ui:1.6.0")
implementation("androidx.compose.material3:material3:1.2.0")
implementation("androidx.activity:activity-compose:1.9.0")

// Room Database
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")

// ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
```

## 🐛 Troubleshooting

### Erro de Package
Se encontrar erro `ClassNotFoundException`, verifique se todos os arquivos estão no package correto:
- `com.example.gerenciamentoconvidados` (package principal)
- `com.example.gerenciamentoconvidados.data` (entidades e DAOs)
- `com.example.gerenciamentoconvidados.viewmodel` (ViewModels)

### Erro de Build
Execute:
```bash
./gradlew clean
./gradlew build
```

### Contador não atualiza
O contador busca apenas convidados com status **"SIM"** (exatamente assim, em maiúsculas).

## 🔮 Próximas Funcionalidades

- [ ] Busca e filtros por nome/grupo
- [ ] Exportar lista em PDF/Excel
- [ ] Gráficos de estatísticas
- [ ] Modo escuro
- [ ] Backup e sincronização em nuvem
- [ ] Notificações de lembretes

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👤 Autor

Desenvolvido com ❤️ por João Vitor Soares
                         Lucas Henrique
                         Pedro Dias
---

⭐ Se este projeto te ajudou, deixe uma estrela!

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para:

1. Fazer um Fork do projeto
2. Criar uma Branch para sua feature (`git checkout -b feature/NovaFuncionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a Branch (`git push origin feature/NovaFuncionalidade`)
5. Abrir um Pull Request

## 📧 Contato

- Email: seu-email@exemplo.com
- LinkedIn: [seu-perfil](https://linkedin.com/in/seu-perfil)
- GitHub: [@seu-usuario](https://github.com/seu-usuario)
