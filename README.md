# Jogo de Tabuleiro Estratégico - Banco Imobiliário

## Resumo do Projeto

Simulação de jogo de tabuleiro estilo "Banco Imobiliário" em Java, demonstrando aplicação prática de estruturas de dados: **Lista Ligada Circular** para o tabuleiro e **Pilha** para o baralho de cartas.

---

## Estruturas de Dados Utilizadas

### 1. Lista Ligada Circular (Tabuleiro)
- **Implementação customizada** para representar o tabuleiro circular
- Cada nó (`NoCasa`) contém uma `Casa` e aponta para o próximo nó
- Permite movimentação contínua sem cálculos de módulo
- Detecta automaticamente quando jogador completa uma volta

### 2. Pilha LIFO (Baralho de Cartas)
- **Implementação customizada** para gerenciar cartas de Sorte/Revés
- Embaralhamento usando algoritmo **Fisher-Yates**
- Reembaralhamento automático quando a pilha se esgota
- Operações O(1) para empilhar/desempilhar

### 3. ArrayList (Jogadores e Imóveis)
- Estrutura padrão do Java para operações CRUD
- Acesso direto por índice O(1)
- Tamanho dinâmico (até 6 jogadores, 40 imóveis)

---

## Funcionalidades

### Fase 1: Configuração
- CRUD de imóveis (mínimo 10)
- CRUD de jogadores (mínimo 2)
- Configurações da partida
- **Dados pré-cadastrados** (15 imóveis e 4 jogadores)

### Fase 2: Execução
- Movimentação com dados (lista circular)
- Compra de imóveis e pagamento de aluguel
- Imposto (5% do patrimônio)
- Restituição (10% do salário)
- Sistema de prisão (máx. 3 rodadas)
- Cartas de Sorte/Revés (20 cartas)

### Fase 3: Encerramento
- Término por rodadas máximas ou apenas 1 jogador ativo
- Cálculo de patrimônio e declaração de vencedor

---

## Estrutura do Projeto

```
src/
├── Main.java                    # Ponto de entrada
├── models/                     # Classes de modelo
├── estruturas/                  # ListaCircular, Pilha, NoCasa
├── gerenciadores/               # CRUD de jogadores e imóveis
├── jogo/                        # Lógica principal
└── interface_jogo/              # Menus e interface
```

---

## Como Executar

```bash
javac -d out src/**/*.java src/Main.java
java -cp out Main
```

---

## Conceitos Aplicados

- Lista Ligada Circular (implementação customizada)
- Pilha LIFO (implementação customizada)
- ArrayList (Java Collections)
- Algoritmo Fisher-Yates (embaralhamento)
- Programação Orientada a Objetos
