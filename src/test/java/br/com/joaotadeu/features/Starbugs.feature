#language: pt

@starbugs
Funcionalidade: Catálogo de cafés
  Como um usuário do site, eu quero ver o catálogo de cafés na pagina principal
  Para que eu possa escolher e saber mais sobre os produtos disponíveis

  @validação_catalogo
  Cenario: Acessando pagina web do starbugs e efetuar compras
    Dado que estou na pagina principal da Starbugs
    Então eu devo visualizar uma lista de cafes disponíveis

  @checkout_cafe
  Esquema do Cenario: Iniciar a compra de um café
    Dado que estou na pagina principal da Starbugs
    Quando desejo comprar o seguinte produto
      | Nome   | Preço   | Entrega   |
      | <Nome> | <Valor> | <Entrega> |
    Então devo ver a página de Checkout com os detalhes do produto
    E o valor total da compra deve ser de '<Valor total>'

    Exemplos:
      | Nome                 | Valor | Entrega | Valor total |
      | Capuccino            | 19,99 | 10,00   | 29,99       |
      | Expresso Tradicional | 9,99  | 10,00   | 19,99       |

  @compra_sucesso
  Esquema do Cenario: Compra bem sucedida
    Dado que estou na pagina principal da Starbugs
    Quando desejo comprar o seguinte produto
      | Nome   | Preço   | Entrega   |
      | <Nome> | <Valor> | <Entrega> |
    Então devo ver a página de Checkout com os detalhes do produto
      E o valor total da compra deve ser de '<Valor total>'
    Quando faço a busca do seguinte CEP: "<CEP>"
      E informo os demais dados do endereço:
        | Numero   | Detalhes   |
        | <Numero> | <Detalhes> |
      E escolho a forma de pagamento:
        | Pagamento   |
        | <Pagamento> |
      E por fim finalizo a compra
    Então sou redirecionado para a página de confirmação de Pedidos e visualizo a mensagem "Uhull! Pedido confirmado"
      E deve ser informado o seguinte prazo de entrega: "20 min - 30 min"

    Exemplos:
      | Nome      | Valor | Entrega | Valor total | CEP      | Numero | Detalhes | Pagamento         |
      | Capuccino | 19,99 | 10,00   | 29,99       | 14020055 | 2072   | Apto 65  | cartao de credito |

  @compra_indisponivel
  Cenário: Café indisponível
    Dado que estou na pagina principal da Starbugs
    Quando desejo comprar o seguinte produto
      | Nome             | Preço |
      | Expresso Cremoso | 9,99  |
    Então devo ver um popup informando que o produto está indisponível com a mensagem "Produto indisponível"