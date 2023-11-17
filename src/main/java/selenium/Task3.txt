package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Task3 {
    public static void main(String[] args) throws InterruptedException {
        // Set the path to the location where ChromeDriver executable is stored
        System.setProperty("webdriver.chrome.driver", "C:/Users/deonv/Desktop/ACC/Assignment3/chromedriver-win64/chromedriver.exe");

        // Create ChromeOptions
        ChromeOptions options = new ChromeOptions();

        // Add the start-maximized argument
        options.addArguments("--start-maximized");

        // Create a new instance of the Chrome driver with the configured ChromeOptions
        WebDriver driver = new ChromeDriver(options);

        // Navigate to the website
        driver.get("https://www.bhg.com/gardening/plant-dictionary/perennial/babys-breath/");
        Thread.sleep(5000);

        // Click on the facebook link to share Baby's breath on post
        WebElement shareOnFacebook = driver.findElement(By.cssSelector("span[title='Share on Facebook']"));
        shareOnFacebook.click();

        // Wait for the popup window to be present
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust the timeout as needed
        wait.until(ExpectedConditions.numberOfWindowsToBe(2)); // Assuming there are two windows - the main window and the popup

        // Switch to the popup window
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }

        // Now I am in the popup window, wait for an element on the popup page to be present
        // Locate the username input field by its ID attribute
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
        // Enter the username
        usernameField.sendKeys("testppxx99@gmail.com");

        // Locate the password input field by its ID attribute
        WebElement passwordField = driver.findElement(By.id("pass"));
        // Enter the password
        passwordField.sendKeys("Password123!@#");
        Thread.sleep(5000);

        // Click on the login button
        WebElement loginButton = driver.findElement(By.id("loginbutton"));
        loginButton.click();
        Thread.sleep(5000);

        //Enter some deatils about your post. Locate the textarea by its ID attribute
        WebElement textarea = driver.findElement(By.cssSelector("textarea[name='xhpc_message_text']"));
        // Enter text into the textarea
        textarea.sendKeys("Baby's Breath is my favorite flower");
        Thread.sleep(5000);

        // Locate the button by its ID attribute
        WebElement postButton = driver.findElement(By.name("__CONFIRM__"));
        postButton.click();

        Thread.sleep(5000);

        driver.quit();
    }
}
