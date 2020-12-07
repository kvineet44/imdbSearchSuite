package ui.base;

/**
 * @author vineetkumar
 * created 6/12/2020
 * Base class for SetUp Related fields like browserName, Environment , Device Details etc
 */

public class SetUpDetails {
    private String browserName;

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }
}
