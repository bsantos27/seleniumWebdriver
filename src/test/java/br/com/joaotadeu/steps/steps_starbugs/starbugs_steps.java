package br.com.joaotadeu.steps.steps_starbugs;

import br.com.joaotadeu.DriverFactory.DriverFactory;
import br.com.joaotadeu.pages.starbugsPages;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class starbugs_steps {

    private WebDriver navegadorStarbugs;
    private starbugsPages page;

    @Before
    public void setUpStarbugsStarbugs() {
        navegadorStarbugs = DriverFactory.restartDriver();
        page = new starbugsPages(navegadorStarbugs);
        System.out.println("Iniciando Teste...");
    }

    @After(order = 0)
    public void tirarPrintWalkdogs(Scenario cenario) {
        if (navegadorStarbugs != null) {
            try {
                File file = ((TakesScreenshot)navegadorStarbugs).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(file, new File("evidencias/screenshot/" + cenario.getId() + ".jpg"));
            } catch (IOException e) {
                System.err.println("Erro ao capturar screenshot: " + e.getMessage());
            }
        }
    }

    @After(order = 1)
    public void fecharNavegadorStarbugs() {
        if (navegadorStarbugs != null) {
            System.out.println("Finalizando Teste...");
            navegadorStarbugs.quit();
            navegadorStarbugs = null;
        }
    }

    @Dado("que estou na pagina principal da Starbugs")
    public void que_estou_na_pagina_principal_da_starbugs() {
       page.acessarPaginaPrincipal();
    }

    @Então("eu devo visualizar uma lista de cafes disponíveis")
    public void eu_devo_visualizar_uma_lista_de_cafes_disponíveis() {
        assertTrue(page.verificarListaCafesVisivel());
    }

    @Quando("desejo comprar o seguinte produto")
    public void desejoComprarOSeguinteProduto(DataTable dataTable) {
        List<Map<String, String>> produtos = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> produto : produtos) {
            String nomeProduto = produto.get("Nome");
            String precoEsperado = produto.get("Preço");
            page.comprarProduto(nomeProduto, precoEsperado);
        }
    }

    @Então("devo ver a página de Checkout com os detalhes do produto")
    public void devoVerAPaginaDeCheckoutComOsDetalhesDoProduto() {
        assertTrue(page.verificarPaginaCheckoutVisivel());
    }

    @E("o valor total da compra deve ser de {string}")
    public void oValorTotalDaCompraDeveSerDe(String valorTotalEsperado) {
        long valorTotalEsperadoNum = (long) (Double.parseDouble(valorTotalEsperado.replace("R$ ", "").replace(",", ".")) * 100);
        long valorTotalTela = page.obterValorTotalCompra();
        Assert.assertEquals(valorTotalEsperadoNum, valorTotalTela);
    }

    @Quando("faço a busca do seguinte CEP: {string}")
    public void facoABuscaDoSeguinteCEP(String Cep) {
        page.buscarCEP(Cep);
    }

    @E("informo os demais dados do endereço:")
    public void informoOsDemaisDadosDoEndereco(DataTable dataTable) {
        List<Map<String, String>> dadosEndereco = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> dados : dadosEndereco) {
            page.preencherDadosEndereco(dados);
        }
    }

    @E("escolho a forma de pagamento:")
    public void escolhoAFormaDePagamento(DataTable dataTable) {
        List<Map<String, String>> formaPagamento = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> opcao : formaPagamento) {
            String metodoPagamento = opcao.get("Pagamento").toLowerCase();
            page.selecionarFormaPagamento(metodoPagamento);
        }
    }

    @E("por fim finalizo a compra")
    public void porFimFinalizoACompra() {
        page.finalizarCompra();
    }

    @Então("sou redirecionado para a página de confirmação de Pedidos e visualizo a mensagem {string}")
    public void souRedirecionadoParaAPaginaDeConfirmacaoDePedidosEVisualizoAMensagem(String pedidoConfirmado) {
        Assert.assertEquals(pedidoConfirmado, page.obterMensagemConfirmacao(pedidoConfirmado));
    }

    @E("deve ser informado o seguinte prazo de entrega: {string}")
    public void deveSerInformadoOSeguintePrazoDeEntrega(String prazoEntrega) {
        Assert.assertEquals(prazoEntrega, page.obterPrazoEntrega());
    }

    @Então("devo ver um popup informando que o produto está indisponível com a mensagem {string}")
    public void devoVerUmPopupInformandoQueOProdutoEstaIndisponivelComAMensagem(String compraindisponivel) {
        Assert.assertEquals(compraindisponivel, page.obterMensagemIndisponibilidade());
    }
}
