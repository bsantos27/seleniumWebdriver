package br.com.joaotadeu.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Map;

public class starbugsPages {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By listaDeCafesLocator = By.cssSelector(".sc-hhOBVt");
    private By checkoutPedidoLocator = By.cssSelector(".sc-hHTYSt");
    private By valorTotalCompraLocator = By.cssSelector("p.total-price");
    private By campoBuscaCEPLocator = By.cssSelector("input[name=cep]");
    private By botaoFinalizarCompraLocator = By.cssSelector(".sc-idXgbr");
    private By popupMensagemIndisponibilidadeLocator = By.cssSelector("#swal2-html-container");

    public starbugsPages(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void acessarPaginaPrincipal() {
        driver.get("https://starbugs.vercel.app/");
    }

    public boolean verificarListaCafesVisivel() {
        WebElement listaDeCafes = driver.findElement(listaDeCafesLocator);
        return listaDeCafes.isDisplayed();
    }

    public void comprarProduto(String nomeProduto, String precoEsperado) {
        WebElement produtoElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(@class, 'coffee-name') and text()='" + nomeProduto + "']")));
        WebElement precoElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(@class, 'hblMil') and text()='" + precoEsperado + "']")));
        WebElement botaoComprar = produtoElement.findElement(By.xpath("./following-sibling::div//button[contains(@class, 'buy-coffee')]"));
        botaoComprar.click();
    }

    public boolean verificarPaginaCheckoutVisivel() {
        WebElement checkoutPedido = wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutPedidoLocator));
        return checkoutPedido.isDisplayed();
    }

    public long obterValorTotalCompra() {
        WebElement valorTotalElement = driver.findElement(valorTotalCompraLocator);
        String valorTotalTexto = valorTotalElement.getText().replace("R$ ", "").replace(",", ".");
        return (long) (Double.parseDouble(valorTotalTexto) * 100);
    }

    public void buscarCEP(String Cep) {
        WebElement campoBuscaCEP = driver.findElement(campoBuscaCEPLocator);
        campoBuscaCEP.sendKeys(Cep);
        driver.findElement(By.cssSelector("input[value='Buscar CEP']")).click();
    }

    public String obterMensagemConfirmacao(String pedidoConfirmado) {
        By mensagemConfirmacaoLocator = By.xpath("//h1[contains(text(), '" + pedidoConfirmado + "')]");
        WebElement mensagemElement = wait.until(ExpectedConditions.visibilityOfElementLocated(mensagemConfirmacaoLocator));
        return mensagemElement.getText();
    }

    public void preencherDadosEndereco(Map<String, String> dados) {
        WebElement campoNumero = driver.findElement(By.cssSelector("input[name=number]"));
        campoNumero.sendKeys(dados.get("Numero"));
        WebElement campoComplemento = driver.findElement(By.cssSelector("input[name=complement]"));
        campoComplemento.sendKeys(dados.get("Detalhes"));
    }

    public void selecionarFormaPagamento(String metodoPagamento) {
        By selector = null;
        switch (metodoPagamento) {
            case "cartao de credito":
                selector = By.cssSelector("div.sc-fnGiBr:nth-child(1) > label:nth-child(2) > div:nth-child(1)");
                break;
            case "cartao de debito":
                selector = By.cssSelector("div.sc-fnGiBr:nth-child(2) > label:nth-child(2) > div:nth-child(1)");
                break;
            case "avista no pix":
                selector = By.cssSelector("div.sc-fnGiBr:nth-child(3) > label:nth-child(2) > div:nth-child(1)");
                break;
        }
        if (selector != null) {
            WebElement opcaoPagamento = driver.findElement(selector);
            opcaoPagamento.click();
        } else {
            System.out.println("Método de pagamento não reconhecido: " + metodoPagamento);
        }
    }

    public void finalizarCompra() {
        WebElement botaoFinalizar = driver.findElement(botaoFinalizarCompraLocator);
        botaoFinalizar.click();
    }

    public String obterPrazoEntrega() {

        WebElement prazoEntregaElement = driver.findElement(By.xpath("//strong[contains(text(), '20 min - 30 min')]"));
        return prazoEntregaElement.getText();
    }

    public String obterMensagemIndisponibilidade() {
        WebElement mensagemElement = wait.until(ExpectedConditions.visibilityOfElementLocated(popupMensagemIndisponibilidadeLocator));
        return mensagemElement.getText();
    }
}
