package br.com.joaotadeu.DriverFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    private static WebDriver navegadorDriver;

    private DriverFactory() {}

    public static WebDriver getDriver() {
        if (navegadorDriver == null) {
            WebDriverManager.firefoxdriver().setup();
            navegadorDriver = new FirefoxDriver();
        }
        return navegadorDriver;
    }

    public static void killDriver() {
        if (navegadorDriver != null) {
            navegadorDriver.quit();
            navegadorDriver = null;
        }
    }

    // Método para reiniciar o WebDriver
    public static WebDriver restartDriver() {
        killDriver();
        WebDriverManager.firefoxdriver().setup();
        navegadorDriver = new FirefoxDriver();
        return navegadorDriver;
    }
}
