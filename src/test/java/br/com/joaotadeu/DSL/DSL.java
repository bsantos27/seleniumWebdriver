package br.com.joaotadeu.DSL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DSL {
    private final WebDriver driver;

    public DSL(WebDriver driver) {
        this.driver = driver;
    }

    public void escreverId(String id_campo, String texto) {
        driver.findElement(By.id(id_campo)).sendKeys(texto);
    }

    public void escreverCSSselector(String cssSelector, String texto){
        driver.findElement(By.cssSelector(cssSelector)).sendKeys(texto);
    }
}
