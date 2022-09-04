import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;


public class JUnitTutorial {
    WebDriver driver;
    @Before
    public void setup(){
        System.setProperty("webdriver.chrome.driver","./src/test/resources/chromedriver.exe");
        ChromeOptions options =new ChromeOptions();
        options.addArguments("--headed");
        driver= new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }
    @Test
    public void getTitle(){
        driver.get("https://demoqa.com/");
        String title =driver.getTitle();
        System.out.println(title);
        Assert.assertEquals(title,"ToolsQA");
        //Assert.assertTrue(title.contains("ToolsQA"));
    }
    @Test
    public void writeText(){
        driver.get("https://demoqa.com/text-box");

        //driver.findElement(By.id("userName")).sendKeys("Rakib");
        //driver.findElement(By.cssSelector("[type=text]")).sendKeys("Rakib");
        //driver.findElement(By.className("form-control")).sendKeys("Rakib");

        //for username
        WebElement txtFirstName=driver.findElement(By.id("userName"));
        txtFirstName.sendKeys("Rakib");

        //for email using xpath
        WebElement txtEmail=driver.findElement(By.xpath("//input[@id='userEmail']"));
        txtEmail.sendKeys("info@gmail.com");

        //for button
        List<WebElement> button=driver.findElements(By.tagName("button"));
        button.get(1).click();

    }
    @Test
    public void handleAlert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        //one types of button
        //driver.findElement(By.id("alertButton")).click();
       // Thread.sleep(2000);
        //driver.switchTo().alert().accept();

        //confirm button
        // main line
        driver.findElement(By.id("confirmButton")).click();
        //for selected ok
        //driver.switchTo().alert().accept();
        //for selected cancel
        driver.switchTo().alert().dismiss();

        //now I will test
        String text =driver.findElement(By.className("text-success")).getText();
        Assert.assertTrue(text.contains("Cancel"));




    }
    @Test
    public void selectDate(){
        driver.get("https://demoqa.com/date-picker");
        Actions actions=new Actions(driver);
        WebElement txtDate= driver.findElement(By.id("datePickerMonthYearInput"));
        actions.moveToElement(txtDate).doubleClick().click().keyDown(Keys.BACK_SPACE).keyUp(Keys.BACK_SPACE).sendKeys(Keys.ENTER).perform();
        driver.findElement(By.id("datePickerMonthYearInput")).clear();
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys("07/27/2022");
        txtDate.sendKeys(Keys.ENTER);
    }
    @Test
    public void selectDropdown(){
        driver.get("https://demoqa.com/select-menu");
        Select select=new Select(driver.findElement(By.id("oldSelectMenu")));
        //drop down er belay value jeta thakbe seta dite hobe
        select.selectByValue("2");

        //Multiple Values
        Select cars=new Select(driver.findElement(By.id("cars")));
        if(cars.isMultiple()){
            cars.selectByValue("volvo");
            cars.selectByValue("audi");
        }
    }
    @Test
    public void mouseHover(){
        driver.get("https://green.edu.bd/");
        //Hover korle actions class integrate korte hobe
        Actions actions=new Actions(driver);
       List<WebElement> list= driver.findElements(By.xpath("//a[contains(text(),\"About Us\")]"));
      actions.moveToElement( list.get(2)).perform();
    }

    @Test
    public void keyboardEvent() throws InterruptedException {
        //write in google searchbar
        driver.get("https://www.google.com/");
        WebElement searchElement= driver.findElement(By.name("q"));
        Actions action=new Actions(driver);
        action.moveToElement(searchElement);
        action.keyDown(Keys.SHIFT);
        action.sendKeys("Selenium WebDriver").keyUp(Keys.SHIFT).doubleClick().click().contextClick().perform();
        Thread.sleep(5000);
    }

    @Test
    public void actionClick(){
        driver.get("https://demoqa.com/buttons");
        Actions actions=new Actions(driver);
        //for double click
        actions.doubleClick(driver.findElement(By.id("doubleClickBtn"))).perform();
        //for right click
        actions.contextClick(driver.findElement(By.id("rightClickBtn"))).perform();
    }
    @Test
    public void screenShot() throws IOException {
        driver.get("https://demoqa.com");
        File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String time = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-aa").format(new Date());
        String fileWithPath = "./src/test/resources/screenshots/" + time + ".png";
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(screenshotFile, DestFile);
    }
    @Test
    public void uploadFile(){
        driver.get("https://demoqa.com/upload-download");
        driver.findElement(By.id("uploadFile")).sendKeys("F:\\a.jpg");
    }
    @Test
    public void downloadFile(){
        driver.get("https://demoqa.com/upload-download");
        driver.findElement(By.id("downloadButton")).click();

    }
    @Test
    public void handleTab() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("tabButton")).click();
        Thread.sleep(3000);
        ArrayList<String> w = new ArrayList(driver.getWindowHandles());
        //switch to open tab
        driver.switchTo().window(w.get(1));
        System.out.println("New tab title: " + driver.getTitle());
        String text = driver.findElement(By.id("sampleHeading")).getText();
        Assert.assertEquals(text,"This is a sample page");
        driver.close();
        driver.switchTo().window(w.get(0));
    }
    @Test
    public void handleWindow() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");

        Thread.sleep(5000);
        // WebDriverWait wait = new WebDriverWait(driver, 30);
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("windowButton")));
        driver.findElement(By.id(("windowButton"))).click();
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();

        while (iterator.hasNext()) {
            String ChildWindow = iterator.next();
            if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
                String text = driver.findElement(By.id("sampleHeading")).getText();
                Assert.assertTrue(text.contains("This is a sample page"));
            }
        }
    }
    @Test
    public void scrapData(){
        driver.get("https://demoqa.com/webtables");
        WebElement table = driver.findElement(By.className("rt-tbody"));
        List<WebElement> allRows = table.findElements(By.className("rt-tr"));
        int i=0;
        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(By.className("rt-td"));
            for (WebElement cell : cells) {
                i++;
                System.out.println("num["+i+"] "+ cell.getText());

            }
        }
    }
    @Test
    public void handleIframe(){
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame("frame2");
        String text= driver.findElement(By.id("sampleHeading")).getText();
        Assert.assertTrue(text.contains("This is a sample page"));
        driver.switchTo().defaultContent();

    }
    @After
    public void closeDriver(){

        //driver.close();
        //driver.quit();
    }
}
