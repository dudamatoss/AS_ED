# Jogo de Tabuleiro Estratégico - Banco Imobiliário

## Resumo do Projeto

Este projeto implementa uma simulação completa de um jogo de tabuleiro estratégico similar ao "Banco Imobiliário" desenvolvido em Java. A aplicação demonstra o uso prático de estruturas de dados fundamentais, com destaque para a **Lista Ligada Circular** para representar o tabuleiro e a **Pilha** para gerenciar o baralho de cartas de Sorte/Revés.

O jogo permite que múltiplos jogadores compitam em um tabuleiro circular, comprando propriedades, pagando aluguéis, lidando com eventos aleatórios e gerenciando suas finanças até que um vencedor seja determinado pelo maior patrimônio ao final da partida.

---

## Estrutura de Dados Utilizadas

### 1. Gerenciamento de Jogadores e Imóveis

**Estrutura Escolhida:** `ArrayList` (Java Collections Framework)

**Justificativa:**

Para o gerenciamento de jogadores e imóveis, foi escolhida a estrutura `ArrayList` da biblioteca padrão do Java. Esta escolha foi feita pelos seguintes motivos:

- **Operações CRUD Eficientes:** O `ArrayList` oferece acesso direto por índice com complexidade O(1), facilitando operações de busca, atualização e remoção por ID.

- **Inserção e Remoção Simples:** Para operações de cadastro e remoção, o `ArrayList` oferece complexidade O(1) no final da lista e O(n) no pior caso, o que é adequado para o contexto do jogo onde não há necessidade de inserções frequentes no meio da lista.

- **Facilidade de Iteração:** A estrutura permite iterações simples e eficientes para listagem e processamento de todos os elementos, essencial para operações como listar jogadores e calcular patrimônios.

- **Tamanho Dinâmico:** Diferente de arrays estáticos, o `ArrayList` cresce dinamicamente conforme necessário, permitindo flexibilidade no número de jogadores (até 6) e imóveis (até 40).

- **Compatibilidade:** A estrutura é amplamente utilizada e bem documentada, facilitando a manutenção e compreensão do código.

**Alternativas Consideradas:**
- **Array Estático:** Rejeitado por falta de flexibilidade e necessidade de gerenciamento manual de tamanho.
- **Lista Ligada Simples:** Rejeitada por não oferecer vantagens significativas neste contexto e ter acesso sequencial O(n) para busca por ID.
- **HashMap:** Considerada para busca rápida por ID, mas rejeitada por não manter ordem de inserção e adicionar complexidade desnecessária para o tamanho limitado de dados.

---

### 2. Lista Ligada Circular para o Tabuleiro

**Estrutura Escolhida:** Lista Ligada Circular (implementação customizada)

**Como a Lista Ligada Circular Representa o Fluxo Contínuo:**

A Lista Ligada Circular é a estrutura ideal para representar um tabuleiro de jogo porque:

1. **Ciclicidade Natural:** Cada nó possui uma referência para o próximo nó, e o último nó aponta de volta para o primeiro, criando um ciclo infinito. Isso representa perfeitamente a natureza circular de um tabuleiro onde os jogadores podem dar voltas completas.

2. **Movimentação Contínua:** Quando um jogador avança N casas a partir de uma posição, o algoritmo simplesmente percorre N nós seguindo as referências `proximo`. Se passar pelo último nó, automaticamente retorna ao início sem necessidade de verificações especiais ou cálculos de módulo.

3. **Detecção de Volta Completa:** A estrutura permite detectar facilmente quando um jogador completa uma volta ao verificar se a posição anterior está "atrás" da posição atual na ordem circular, garantindo que o salário seja pago corretamente.

**Por que é Mais Adequada que Array ou Lista Ligada Simples:**

**Comparação com Array:**
- **Vantagem:** Não requer cálculos de módulo (`posição % tamanho`) para avançar, tornando o código mais limpo e intuitivo.
- **Vantagem:** Permite inserção e remoção de casas dinamicamente sem necessidade de realocação de memória.
- **Vantagem:** A referência circular elimina a necessidade de verificações de limites explícitas.

**Comparação com Lista Ligada Simples:**
- **Vantagem:** A circularidade permite que operações de avanço funcionem naturalmente mesmo quando se ultrapassa o final da lista.
- **Vantagem:** Facilita a detecção de passagem pelo início através da comparação de referências de nós.
- **Vantagem:** Elimina casos especiais para tratamento do primeiro e último elemento.

**Implementação:**

```java
public NoCasa avancar(NoCasa atual, int casas) {
    NoCasa noAtual = atual;
    for (int i = 0; i < casas; i++) {
        noAtual = noAtual.getProximo(); // Naturalmente retorna ao início
    }
    return noAtual;
}
```

---

### 3. Pilha para o Baralho de Sorte/Revés

**Estrutura Escolhida:** Pilha (LIFO - Last-In, First-Out) - implementação customizada

**Como a Pilha Armazena e Gerencia as Cartas:**

A pilha armazena as cartas de forma que a última carta empilhada é a primeira a ser retirada. Cada carta é empilhada no topo, e quando uma carta é puxada, ela é removida do topo da pilha.

**Por que o Princípio LIFO é Ideal para um Baralho:**

1. **Simulação Realista:** Em um baralho físico, as cartas são empilhadas e retiradas do topo, então a estrutura pilha simula fielmente o comportamento de um baralho real.

2. **Aleatoriedade Controlada:** Após embaralhar as cartas, elas são empilhadas em ordem aleatória. O princípio LIFO garante que a ordem de embaralhamento seja preservada até que todas as cartas sejam utilizadas.

3. **Eficiência:** As operações de empilhar (`push`) e desempilhar (`pop`) têm complexidade O(1), tornando o acesso às cartas extremamente rápido.

4. **Gerenciamento Simples:** Quando a pilha se esgota, basta reembaralhar todas as cartas e reconstruir a pilha, processo simples e eficiente.

**Estratégia de Embaralhamento:**

Foi implementado o algoritmo **Fisher-Yates** para embaralhar as cartas:

1. **Criação do Conjunto:** Inicialmente, todas as cartas são criadas em um array fixo (mínimo de 16 cartas, implementação possui 20).

2. **Embaralhamento Fisher-Yates:**
   - Percorre o array do último elemento ao primeiro.
   - Para cada posição `i`, seleciona aleatoriamente uma posição `j` entre 0 e `i`.
   - Troca os elementos nas posições `i` e `j`.
   - Este algoritmo garante uma distribuição uniforme e aleatória.

3. **Empilhamento:** Após embaralhar, as cartas são empilhadas na ordem embaralhada, garantindo que a primeira carta puxada seja aleatória.

4. **Reembaralhamento Automático:** Quando a pilha se esgota (método `desempilhar()` retorna `null`), o sistema automaticamente reembaralha todas as cartas e reconstrói a pilha.

**Implementação:**

```java
public void embaralhar() {
    Carta[] cartasEmbaralhadas = todasCartas.clone();
    
    // Algoritmo Fisher-Yates
    for (int i = cartasEmbaralhadas.length - 1; i > 0; i--) {
        int j = random.nextInt(i + 1);
        Carta temp = cartasEmbaralhadas[i];
        cartasEmbaralhadas[i] = cartasEmbaralhadas[j];
        cartasEmbaralhadas[j] = temp;
    }
    
    // Reconstrói a pilha
    pilhaCartas = new Pilha();
    for (Carta carta : cartasEmbaralhadas) {
        pilhaCartas.empilhar(carta);
    }
}
```

---

## Lógica de Movimentação e Interação com as Casas

### Movimentação no Tabuleiro

A movimentação funciona da seguinte forma:

1. **Lançamento de Dados:** O sistema simula o lançamento de dois dados de 6 faces, gerando valores aleatórios de 1 a 6 para cada dado e somando os resultados.

2. **Avanço na Lista Circular:** Utilizando o método `avancar()` da lista circular, o jogador move-se N casas (onde N é a soma dos dados) a partir de sua posição atual. A estrutura circular garante que, se o avanço ultrapassar o final do tabuleiro, o jogador automaticamente retorna ao início.

3. **Detecção de Passagem pelo Início:** O sistema verifica se o jogador passou pelo início de duas formas:
   - **Verificação Explícita:** Se a casa atual é do tipo `INICIO`.
   - **Verificação de Volta Completa:** Utiliza o método `passouPeloInicio()` que compara a posição anterior com a posição atual, verificando se houve uma transição que cruzou o nó de início na ordem circular.

4. **Pagamento de Salário:** Quando detectada a passagem pelo início, o jogador recebe automaticamente o salário configurado na partida.

### Interação com as Casas

Ao parar em uma casa, o sistema identifica o tipo através do enum `TipoCasa` e executa a ação correspondente:

- **INICIO:** Salário já foi pago durante a movimentação.
- **IMOVEL:** Verifica se tem dono; se não, oferece compra; se sim, cobra aluguel.
- **IMPOSTO:** Calcula 5% do patrimônio total e desconta do saldo.
- **RESTITUICAO:** Adiciona 10% do salário ao saldo do jogador.
- **PRISAO:** Apenas passa pela prisão (não prende automaticamente).
- **SORTE_REVES:** Puxa uma carta do baralho e aplica seu efeito.

---

## Gerenciamento Financeiro

### Cálculo de Aluguel

Quando um jogador para em um imóvel que possui dono:

1. **Verificação de Propriedade:** O sistema verifica se o imóvel possui um dono através do método `temDono()`.

2. **Cálculo:** O valor do aluguel é armazenado diretamente no objeto `Imovel` através do atributo `valorAluguel`, definido no momento do cadastro.

3. **Transação:** Se o jogador tem saldo suficiente:
   - Subtrai o valor do saldo do jogador que parou.
   - Adiciona o valor ao saldo do dono do imóvel.
   - Exibe mensagem informativa.

4. **Falência:** Se o saldo for insuficiente, o jogador é declarado falido e todas suas propriedades são liberadas.

### Cálculo de Imposto

Quando um jogador para na casa de Imposto:

1. **Cálculo de Patrimônio:** Utiliza o método `calcularPatrimonio()` do jogador, que soma:
   - Saldo atual (`saldo`)
   - Valor total dos imóveis possuídos (`valorCompra` de cada imóvel)

2. **Aplicação da Taxa:** Calcula 5% do patrimônio total:
   ```java
   double imposto = patrimonio * 0.05;
   ```

3. **Cobrança:** Subtrai o valor do saldo do jogador. Se insuficiente, declara falência.

### Verificação de Propriedade

O sistema verifica se um imóvel tem dono através de:

1. **Atributo `dono`:** Cada `Imovel` possui um atributo `dono` do tipo `Jogador`, que é `null` se não houver dono.

2. **Método `temDono()`:** Retorna `true` se `dono != null`, `false` caso contrário.

3. **Atribuição:** Quando um imóvel é comprado:
   - O método `adicionarPropriedade()` do jogador adiciona o imóvel à sua lista de propriedades.
   - O método também define `imovel.setDono(this)`, estabelecendo a relação bidirecional.

### Transações Financeiras

Todas as transações financeiras são gerenciadas através dos métodos do objeto `Jogador`:

- **`adicionarSaldo(double valor)`:** Adiciona valor ao saldo.
- **`subtrairSaldo(double valor)`:** Subtrai valor do saldo, garantindo que não fique negativo.
- **`calcularPatrimonio()`:** Calcula o patrimônio total (saldo + valor dos imóveis).

**Consistência:** Todas as operações garantem que o saldo nunca fique negativo (é zerado se necessário) e que as propriedades sejam atualizadas corretamente quando um jogador fali.

---

## Estrutura do Projeto

```
src/
├── Main.java                          # Ponto de entrada da aplicação
├── modelos/                           # Classes de modelo
│   ├── Casa.java                     # Representa uma casa no tabuleiro
│   ├── Jogador.java                  # Representa um jogador
│   ├── Imovel.java                   # Representa um imóvel
│   ├── Carta.java                    # Representa uma carta de Sorte/Revés
│   ├── TipoCasa.java                 # Enum dos tipos de casas
│   └── TipoAcaoCarta.java            # Enum dos tipos de ações das cartas
├── estruturas/                       # Estruturas de dados customizadas
│   ├── ListaCircular.java           # Lista ligada circular para o tabuleiro
│   ├── NoCasa.java                  # Nó da lista circular
│   └── Pilha.java                   # Pilha para o baralho de cartas
├── gerenciadores/                    # Gerenciadores de entidades
│   ├── GerenciadorJogadores.java    # CRUD de jogadores (usa ArrayList)
│   └── GerenciadorImoveis.java      # CRUD de imóveis (usa ArrayList)
├── jogo/                             # Lógica principal do jogo
│   ├── Tabuleiro.java               # Gerencia o tabuleiro
│   ├── Baralho.java                 # Gerencia o baralho de cartas
│   ├── Jogo.java                    # Lógica principal da partida
│   └── ConfiguracaoPartida.java     # Configurações da partida
└── interface_jogo/                   # Interface do usuário
    └── MenuPrincipal.java           # Menus e interação com usuário
```

---

## Funcionalidades Implementadas

### Fase 1: Configuração Pré-Jogo

✅ **Gerenciamento de Imóveis (CRUD):**
- Cadastrar novos imóveis (máximo de 40)
- Listar todos os imóveis cadastrados
- Atualizar dados de um imóvel
- Remover um imóvel
- Validação: mínimo de 10 imóveis para iniciar

✅ **Gerenciamento de Jogadores (CRUD):**
- Cadastrar novos jogadores (máximo de 6)
- Listar os jogadores participantes
- Atualizar dados de um jogador
- Remover um jogador da partida
- Validação: mínimo de 2 jogadores para iniciar

✅ **Configurações da Partida:**
- Definir saldo inicial padrão
- Definir valor do salário por volta
- Determinar número máximo de rodadas
- Dados pré-cadastrados para teste rápido

### Fase 2: Execução do Jogo

✅ **Movimentação:**
- Simulação de lançamento de dois dados
- Avanço no tabuleiro (lista circular)
- Detecção de passagem pelo início
- Pagamento automático de salário

✅ **Lógica de Interação:**
- Compra de imóveis
- Pagamento de aluguel
- Cobrança de imposto (5% do patrimônio)
- Recebimento de restituição (10% do salário)
- Sistema de prisão (máximo 3 rodadas, dados duplos para sair)
- Cartas de Sorte/Revés (20 cartas variadas)

✅ **Feedback ao Usuário:**
- Mensagens claras sobre todas as ações
- Exibição de saldo e patrimônio
- Informações sobre compras e transações

### Fase 3: Encerramento do Jogo

✅ **Condições de Término:**
- Número máximo de rodadas atingido
- Apenas um jogador não falido restante

✅ **Declaração de Vencedor:**
- Cálculo de patrimônio total (saldo + imóveis)
- Comparação entre todos os jogadores ativos
- Exibição do vencedor e seu patrimônio

---

## Como Executar

1. **Compilar o projeto:**
   ```bash
   javac -d out src/**/*.java src/Main.java
   ```

2. **Executar o jogo:**
   ```bash
   java -cp out Main
   ```

3. **Ou usar um IDE Java (IntelliJ IDEA, Eclipse, etc.):**
   - Abra o projeto na IDE
   - Execute a classe `Main.java`

---

## Conceitos de Estruturas de Dados Aplicados

- ✅ **Lista Ligada Circular:** Implementação customizada para o tabuleiro
- ✅ **Pilha (LIFO):** Implementação customizada para o baralho
- ✅ **ArrayList:** Uso da estrutura padrão do Java para gerenciamento de jogadores e imóveis
- ✅ **Algoritmos de Embaralhamento:** Fisher-Yates para aleatoriedade
- ✅ **Estruturas de Dados Lineares:** Aplicação prática de listas e pilhas
- ✅ **Gerenciamento de Memória:** Uso eficiente de referências e objetos

---

## Observações Técnicas

- O projeto utiliza apenas estruturas de dados lineares (listas e pilhas), conforme os requisitos.
- Todas as estruturas de dados customizadas foram implementadas do zero, demonstrando compreensão dos conceitos fundamentais.
- O código segue boas práticas de programação orientada a objetos, com separação de responsabilidades e modularização.
- O sistema é totalmente funcional e testável através da opção de dados pré-cadastrados.

---

## Autor

Desenvolvido como projeto acadêmico para demonstrar aplicação prática de estruturas de dados em Java.
