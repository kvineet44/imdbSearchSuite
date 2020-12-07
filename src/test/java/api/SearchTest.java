package api;

import api.apiBase.Client;
import api.apiBase.Method;
import api.apiBase.Request;
import api.apiBase.Response;
import api.entities.MovieList;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import ui.Pages.SearchPage;
import ui.base.CONSTANT;
import ui.base.ImdbSearchBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @author vineetkumar
 * created 06/12/2020
 * Test Class File for testing the Flow.
 */

public class SearchTest extends ImdbSearchBase {
    Logger logger = LoggerFactory.getLogger(SearchTest.class);
    WebDriver driver = null;

    @BeforeMethod
    public void setUP() {
        driver = startBrowser(CHROME);
    }

    @Test(description = "Search Lord of the Ring Trilogy via API and validate same in the Website")
    public void verifyRingTrilogy() throws IOException, URISyntaxException {
        /*
        STEP 1 & STEP 2 : API call to search Movie by name,
        Saving into MovieList Class and filtering RingsTrilogy Movies.
         */
        Client client = new Client();
        Map<String, String> queryParams = new TreeMap<>();
        queryParams.put(APIKEY, utility.getValueOf(DATA_PROPERTIES, APIKEY));
        queryParams.put(TYPE, MOVIE);
        queryParams.put(S, THE_LORD_OF_THE_RINGS);
        Map<String, String> header = new HashMap<>();
        Request request = null;
        request = new Request(BASE_URI, Method.POST, header, queryParams);
        logger.info("SEARCH API REQUEST:" + request);

        Response testResponse = client.post(request);
        logger.info("SEARCH API RESPONSE:" + testResponse);
        ObjectMapper mapper = new ObjectMapper();
        Gson gson = new Gson();
        MovieList nameList = null;
        Set<MovieList.Movie> ringTrilogyMovies = null;
        Set<String> moviesSetFromAPISearch = null;
        if (testResponse.getStatusCode() == 200) {

            /*          Mapping Result with MovieList Object */
            nameList = gson.fromJson(testResponse.getBody(), MovieList.class);
            ringTrilogyMovies = new HashSet<>();
            moviesSetFromAPISearch = new HashSet<>();
            for (MovieList.Movie m : nameList.getSearch()) {
                if (m.Title.equalsIgnoreCase(LORD_OF_RINGS_1) || m.Title.equalsIgnoreCase(LORD_OF_RINGS_2) || m.Title.equalsIgnoreCase(LORD_OF_RINGS_3)) {
                    ringTrilogyMovies.add(m);
                    moviesSetFromAPISearch.add(m.Title);
                }
            }
            logger.info("Filtered Movies:" + ringTrilogyMovies);
            logger.info("Filtered Movies Titles:" + moviesSetFromAPISearch);
        }

        /*
        STEP 3 to STEP 6 : UI test to search Movie by name and validation result with API result
         */
        goToURL(IMDB_URL);
        SearchPage page = new SearchPage(driver);
        page.searchMovie(CONSTANT.THE_LORD_OF_THE_RINGS);
        page.clickSearchOption();
        page.clickMovie();
        Set<String> moviesSetFromUISearch = page.getListOfMovies();
        logger.info("Movies List In Displaying results Table: " + moviesSetFromUISearch);

        /*Assert to validate Displaying results table contains movie titles from filtered response*/
        Assert.assertTrue(moviesSetFromUISearch.containsAll(moviesSetFromAPISearch));
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}