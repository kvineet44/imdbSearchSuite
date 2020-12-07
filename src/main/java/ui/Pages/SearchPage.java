package ui.Pages;

import org.openqa.selenium.By;
import ui.base.ImdbSearchBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author vineetkumar
 * created 06/12/2020
 * Search Page & Methods implementation using PageFactory Model
 */
public class SearchPage extends ImdbSearchBase {
    static WebDriver driver;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(xpath = "//*[@id='suggestion-search']")
    WebElement searchBox;

    @FindBy(xpath = "//button[@id='suggestion-search-button']")
    WebElement searchBoxButton;

    @FindBy(linkText = "Movie")
    WebElement movie;

    @FindBy(xpath = "//div[@id='main']/div/h1")
    WebElement resultHeader;

    /**
     * SearchMovie Method to Search a Particular Movie by Name
     *
     * @param name
     */
    public void searchMovie(String name) {
        if (searchBox.isDisplayed()) {
            searchBox.sendKeys(name);
        }
    }

    /**
     * Clicking on Search Button
     */
    public void clickSearchOption() {
        if (searchBoxButton.isDisplayed()) {
            searchBoxButton.click();
        }
    }

    /**
     * Clicking on the category as movie
     */
    public void clickMovie() {
        if (movie.isDisplayed()) {
            movie.click();
        }
    }

    private String getMovieXpath(int i) {
        return "//div[@id='main']/div/div[2]/table/tbody/tr[" + i + "]/td[2]/a";
    }

    /**
     * @return Set of movies in the Result table
     *
     */
    public Set<String> getListOfMovies() {
        Set<String> ringTrilogySet = new HashSet<>();
        int totalMovies = Integer.parseInt(resultHeader.getText().replaceAll("[^0-9]", ""));
        for (int i = 1; i < totalMovies; i++) {
            String text = driver.findElement(By.xpath(getMovieXpath(i))).getText().trim();
            ringTrilogySet.add(text);
        }
        return ringTrilogySet;
    }

}
