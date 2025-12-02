# Documentação do Projeto - Jogo de Tabuleiro Estratégico

## 1. Resumo do Projeto

Este projeto implementa uma simulação de jogo de tabuleiro estratégico, similar ao "Banco Imobiliário", desenvolvido em Java. O jogo permite que múltiplos jogadores se movam em um tabuleiro circular, comprem propriedades, paguem aluguéis, enfrentem eventos aleatórios e compitam para acumular o maior patrimônio possível.

O projeto foi desenvolvido com foco na aplicação prática de estruturas de dados fundamentais, especialmente **Lista Ligada Circular** para representar o tabuleiro e **Pilha (LIFO)** para gerenciar o baralho de cartas de Sorte/Revés.

## 2. Estruturas de Dados Utilizadas

### 2.1. Gerenciamento de Jogadores e Imóveis

**Estrutura escolhida:** `ArrayList` (do pacote `java.util`)

**Justificativa:**
- **Operações CRUD eficientes:** O `ArrayList` oferece acesso direto por índice (O(1)) para operações de busca, atualização e remoção quando temos o ID do elemento.
- **Inserção rápida:** Adicionar novos elementos ao final da lista é O(1) amortizado.
- **Facilidade de iteração:** Permite percorrer todos os elementos facilmente para listagem e validações.
- **Tamanho dinâmico:** Não precisamos definir um tamanho fixo antecipadamente, mas podemos limitar manualmente (máximo de 6 jogadores e 40 imóveis).
- **Simplicidade:** Para o contexto deste projeto, onde não há necessidade de ordenação complexa ou busca muito frequente, o `ArrayList` oferece a melhor relação entre simplicidade e eficiência.

**Operações CRUD implementadas:**
- **Create:** `cadastrar()` - Adiciona novo elemento ao final da lista
- **Read:** `listar()` e `buscarPorId()` - Itera pela lista ou busca linear
- **Update:** `atualizar()` - Busca por ID e atualiza os campos
- **Delete:** `remover()` - Busca por ID e remove da lista

### 2.2. Lista Ligada Circular para o Tabuleiro

**Estrutura escolhida:** Lista Ligada Circular (implementação própria)

**Implementação:**
A classe `ListaCircular` utiliza nós (`NoCasa`) que contêm uma referência para a próxima casa, formando um ciclo contínuo. O último nó aponta de volta para o primeiro, criando a circularidade.

**Por que Lista Ligada Circular é mais adequada que Array ou Lista Ligada Simples:**

1. **Fluxo Contínuo e Cíclico:**
   - O tabuleiro de um jogo de tabuleiro é inerentemente circular - após a última casa, volta-se ao início.
   - A lista circular representa perfeitamente essa natureza cíclica sem necessidade de verificações especiais de "volta ao início".

2. **Movimentação Natural:**
   - Quando um jogador avança N casas, simplesmente seguimos os ponteiros `proximo` N vezes.
   - Se chegarmos ao fim da lista, automaticamente continuamos no início, sem necessidade de cálculos de módulo ou verificações condicionais.

3. **Detecção de Volta Completa:**
   - Para detectar se um jogador completou uma volta (e deve receber salário), comparamos a posição anterior com a atual.
   - Se ao percorrer da posição anterior até a atual passarmos pelo nó inicial, sabemos que completou uma volta.
   - Isso é mais intuitivo e eficiente que calcular índices em um array.

4. **Vantagens sobre Array:**
   - **Flexibilidade:** Não precisamos definir um tamanho fixo antecipadamente.
   - **Inserção/Remoção:** Adicionar ou remover casas no meio do tabuleiro é mais simples (apenas ajustar ponteiros).
   - **Representação conceitual:** Cada casa é um objeto independente, não apenas um índice.

5. **Vantagens sobre Lista Ligada Simples:**
   - **Sem casos especiais:** Não precisamos verificar se chegamos ao fim para voltar ao início.
   - **Código mais limpo:** A lógica de movimentação é mais direta e intuitiva.
   - **Eficiência:** Evita verificações condicionais repetidas durante a movimentação.

**Exemplo de uso:**
```java
// Avançar 5 casas a partir da posição atual
NoCasa novaPosicao = tabuleiro.getCasas().avancar(posicaoAtual, 5);
// Se passar pelo início durante o avanço, a circularidade garante continuidade
```

### 2.3. Pilha para o Baralho de Sorte/Revés

**Estrutura escolhida:** Pilha (LIFO - Last-In, First-Out) - implementação própria

**Implementação:**
A classe `Pilha` utiliza uma lista ligada simples onde cada nó (`NoPilha`) contém uma carta e aponta para a próxima carta abaixo na pilha. O topo da pilha é sempre acessível diretamente.

**Por que o princípio LIFO é ideal para um baralho:**

1. **Comportamento Natural de Baralho:**
   - Em um baralho físico, você sempre puxa a carta do topo.
   - As cartas são empilhadas e retiradas na ordem inversa de inserção, exatamente como uma pilha.

2. **Simplicidade de Operações:**
   - **Empilhar (push):** Adiciona uma nova carta no topo - O(1)
   - **Desempilhar (pop):** Remove e retorna a carta do topo - O(1)
   - **Ver topo (peek):** Visualiza a carta do topo sem remover - O(1)

3. **Embaralhamento:**
   - Quando o baralho se esgota, todas as cartas são embaralhadas usando o algoritmo **Fisher-Yates**.
   - O algoritmo percorre o array de cartas do fim para o início, trocando cada elemento com um elemento aleatório anterior.
   - Após embaralhar, as cartas são empilhadas novamente na ordem embaralhada.

4. **Eficiência:**
   - Não há necessidade de buscar uma carta específica no meio do baralho.
   - A única operação necessária é pegar a próxima carta disponível, que é exatamente o que uma pilha oferece de forma otimizada.

**Estratégia de Embaralhamento:**
```java
// Algoritmo Fisher-Yates
for (int i = cartas.length - 1; i > 0; i--) {
    int j = random.nextInt(i + 1);
    Carta temp = cartas[i];
    cartas[i] = cartas[j];
    cartas[j] = temp;
}
```
Este algoritmo garante uma distribuição uniforme e aleatória das cartas.

## 3. Lógica de Movimentação e Interação com as Casas

### 3.1. Movimentação no Tabuleiro

**Processo:**
1. **Lançamento de Dados:** O sistema simula o lançamento de dois dados de 6 faces, gerando valores aleatórios de 1 a 6 cada.
2. **Cálculo do Movimento:** A soma dos dois dados determina quantas casas o jogador avança.
3. **Avanço na Lista Circular:** Utilizando o método `avancar()` da `ListaCircular`, o sistema percorre N nós a partir da posição atual do jogador.
4. **Atualização da Posição:** A nova posição do jogador é atualizada na referência `posicaoAtual` do objeto `Jogador`.

**Detecção de Passagem pelo Início:**
O sistema detecta se o jogador completou uma volta completa de duas formas:
- **Método `passouPeloInicio()`:** Compara a posição anterior com a atual. Se ao percorrer da anterior até a atual passarmos pelo nó inicial, significa que completou uma volta.
- **Verificação direta:** Se a casa atual for do tipo `INICIO`, o jogador também recebe o salário.

Quando uma volta é completada, o jogador recebe automaticamente o valor do salário configurado.

### 3.2. Interação com as Casas

O sistema identifica o tipo da casa onde o jogador parou e executa a ação correspondente:

**Tipos de Casas e Ações:**

1. **INICIO:**
   - Jogador recebe salário (já processado durante a movimentação)

2. **IMOVEL:**
   - Se não tem dono: Oferece opção de compra ao jogador
   - Se tem dono diferente: Jogador paga aluguel ao dono
   - Se é do próprio jogador: Apenas informa que já é dele

3. **IMPOSTO:**
   - Calcula 5% do patrimônio total (saldo + valor dos imóveis)
   - Desconta do saldo do jogador

4. **RESTITUICAO:**
   - Calcula 10% do valor do salário
   - Adiciona ao saldo do jogador

5. **PRISAO:**
   - Se apenas passar: Nenhuma ação
   - Se for enviado por carta: Jogador é preso e deve tentar sair

6. **SORTE_REVES:**
   - Puxa uma carta do topo do baralho
   - Aplica o efeito da carta imediatamente

## 4. Gerenciamento Financeiro

### 4.1. Cálculo de Aluguel

Quando um jogador para em um imóvel que possui dono:
- O valor do aluguel é definido no momento do cadastro do imóvel.
- O sistema verifica se o jogador tem saldo suficiente.
- Se sim: Desconta do saldo do pagador e adiciona ao saldo do dono.
- Se não: O jogador fica falido.

### 4.2. Cálculo de Imposto

Quando um jogador para na casa de Imposto:
```java
double patrimonio = jogador.calcularPatrimonio(); // saldo + valor dos imóveis
double imposto = patrimonio * 0.05; // 5% do patrimônio
```

O método `calcularPatrimonio()` soma:
- Saldo atual do jogador
- Valor de compra de todas as propriedades possuídas

### 4.3. Verificação de Propriedade

Cada `Imovel` possui uma referência para seu `dono` (do tipo `Jogador`):
- Se `dono == null`: Imóvel está disponível para compra
- Se `dono != null`: Imóvel pertence ao jogador referenciado

O método `temDono()` retorna `true` se o imóvel possui dono.

### 4.4. Transações Financeiras

Todas as transações seguem o padrão:
1. **Verificação de saldo:** Antes de qualquer débito, verifica se há saldo suficiente
2. **Execução:** Se houver saldo, realiza a transação
3. **Falência:** Se não houver saldo suficiente, o jogador é declarado falido:
   - Saldo zerado
   - Todas as propriedades são liberadas (dono = null)
   - Jogador não pode mais jogar

**Operações implementadas:**
- `adicionarSaldo(double valor)` - Adiciona valor ao saldo
- `subtrairSaldo(double valor)` - Subtrai valor do saldo (com verificação)
- Transações entre jogadores (aluguel, cartas de pagar/receber todos)

## 5. Estrutura do Projeto

```
src/
├── Main.java                          # Ponto de entrada do programa
├── models/                           # Classes de modelo
│   ├── Casa.java                      # Representa uma casa no tabuleiro
│   ├── Imovel.java                    # Representa um imóvel
│   ├── Jogador.java                   # Representa um jogador
│   ├── Carta.java                     # Representa uma carta de Sorte/Revés
│   ├── TipoCasa.java                  # Enum dos tipos de casas
│   └── TipoAcaoCarta.java             # Enum dos tipos de ações das cartas
├── estruturas/                        # Estruturas de dados
│   ├── ListaCircular.java             # Lista ligada circular
│   ├── NoCasa.java                    # Nó da lista circular
│   └── Pilha.java                     # Pilha LIFO para cartas
├── gerenciadores/                     # Gerenciadores CRUD
│   ├── GerenciadorImoveis.java        # CRUD de imóveis
│   └── GerenciadorJogadores.java      # CRUD de jogadores
├── jogo/                              # Lógica do jogo
│   ├── Jogo.java                      # Classe principal do jogo
│   ├── Tabuleiro.java                 # Gerencia o tabuleiro
│   ├── Baralho.java                   # Gerencia o baralho de cartas
│   └── ConfiguracaoPartida.java       # Configurações da partida
└── interface/                         # Interface do usuário
    └── MenuPrincipal.java             # Menus e interação
```

## 6. Funcionalidades Implementadas

### Fase 1: Configuração Pré-Jogo
- ✅ CRUD completo de Imóveis (máximo 40)
- ✅ CRUD completo de Jogadores (máximo 6)
- ✅ Configuração de saldo inicial, salário e rodadas máximas
- ✅ Validação mínima: 10 imóveis e 2 jogadores
- ✅ Dados pré-cadastrados para teste rápido

### Fase 2: Execução do Jogo
- ✅ Movimentação com dados aleatórios
- ✅ Interação com todos os tipos de casas
- ✅ Sistema de compra e aluguel de imóveis
- ✅ Cálculo de imposto (5% do patrimônio)
- ✅ Restituição (10% do salário)
- ✅ Sistema de prisão (máximo 3 tentativas)
- ✅ Baralho de Sorte/Revés (20+ cartas)
- ✅ Embaralhamento automático quando o baralho se esgota
- ✅ Feedback claro ao usuário

### Fase 3: Encerramento
- ✅ Detecção de fim de jogo (rodadas máximas ou apenas 1 jogador ativo)
- ✅ Cálculo de patrimônio final
- ✅ Declaração do vencedor

## 7. Como Executar

1. Compile todos os arquivos Java:
```bash
javac -d out src/**/*.java src/*.java
```

2. Execute o programa:
```bash
java -cp out Main
```

3. No menu principal, você pode:
   - Gerenciar imóveis e jogadores manualmente
   - Ou usar a opção "Dados Pré-cadastrados" para iniciar rapidamente
   - Configurar as opções da partida
   - Iniciar a partida

## 8. Observações Técnicas

- **Linguagem:** Java
- **Estruturas de Dados:** Lista Ligada Circular, Pilha, ArrayList
- **Algoritmos:** Fisher-Yates para embaralhamento
- **Padrões:** Separação de responsabilidades, modularização
- **Validações:** Implementadas em todas as operações críticas

## 9. Melhorias Futuras

- Interface gráfica (GUI)
- Salvamento e carregamento de partidas
- Mais tipos de casas e eventos
- Sistema de negociação entre jogadores
- Estatísticas detalhadas da partida

