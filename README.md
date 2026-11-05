# vendingmachine

Simulador de máquina de vendas no terminal, feito totalmente em Java.

## O que faz

- Mostra um menu com 10 produtos, preço e quantidade em estoque.
- O cliente escolhe o produto e a quantidade.
- Aceita 3 formas de pagamento (simuladas):
  - **Dinheiro:** só aceita cédulas válidas (100, 50, 20, 10, 5, 2 e 1), soma o valor inserido e calcula o troco em cédulas e moedas.
  - **PIX:** gera um código aleatório e confirma o pagamento quando o usuário digita `sim`.
  - **Crédito:** pede a senha de 4 dígitos, com 3 tentativas. Se errar todas, exibe a mensagem de cartão bloqueado.
- Depois de cada venda, atualiza o estoque no arquivo CSV e imprime a nota fiscal no terminal.
- Digitar `0` cancela o pedido. Digitar `10000` desliga a máquina.

## Stack

- Java 21
- Arquivo CSV como armazenamento dos produtos e do estoque

## Como rodar

1. Clone o repositório:

       git clone https://github.com/luisfelipemaia/vendingmachine

2. Abra `VendingMachine.java` e altere a variável `caminho` para o local do seu `produtos.csv`.
3. Compile e execute a partir da pasta `src`:

       javac maquina_venda/VendingMachine.java
       java maquina_venda.VendingMachine

### Formato do `produtos.csv`

A primeira linha é o cabeçalho. As colunas são: código, nome, preço (número inteiro) e quantidade.

    codigo,nome,preco,quantidade
    1,Batata Frita Rufles 90g,5,10

(Valores de exemplo. A quantidade é só ilustrativa.)

## Decisões técnicas

- Os dados (nomes, preços, quantidades) são lidos do CSV para vetores no início de cada rodada do menu.
- O estoque é atualizado reescrevendo o CSV após cada compra.
- O troco usa a lógica de dar sempre a maior cédula possível primeiro.
- PIX e crédito são simulações, sem integração com nenhum serviço real.
