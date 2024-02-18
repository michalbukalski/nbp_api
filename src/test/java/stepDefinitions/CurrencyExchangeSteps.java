package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CurrencyExchangeSteps {

    private Response response;
    private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeSteps.class);
    private List<BigDecimal> ratesAboveThreshold;

    @Given("API NBP is available")
    public void apiNbpIsAvailable() {
        RestAssured.baseURI = "http://api.nbp.pl/api/exchangerates";
    }

    @When("I request for exchange rates from table A")
    public void iRequestForExchangeRatesFromTableA() {
        response = RestAssured.given()
                .when()
                .get("/tables/A");
    }

    @Then("^the response status code should be (\\d+)$")
    public void the_response_status_code_should_be(int statusCode) {
        response.then().statusCode(statusCode);
        System.out.println("Otrzymany response: " + response.getBody().asString());
    }

    @Then("^I display the rate for currency with code: (.*)$")
    public void i_display_the_rate_for_currency_with_code(String test) {
        System.out.println("Otrzymany response: " + response.asString());

        String currencyCode = "USD";


        List<Object> rates = response.then().extract().path("rates.findAll { it.code == '" + currencyCode + "' }");

        if (!response.getBody().asString().isEmpty()) {
            if (!rates.isEmpty()) {
                Object midValue = ((Map<String, Object>) rates.get(0)).get("code");
                System.out.println("Wartość code dla waluty " + currencyCode + ": " + midValue);
            } else {
                System.out.println("Brak danych dla waluty o kodzie: " + currencyCode);
            }
        } else {
            System.out.println("Otrzymana odpowiedź jest pusta.");
        }

//        response.then().body("find { it.code == '" + currencyCode + "' }.mid", notNullValue());
        response.then().body("rates.find { it.code == '" + currencyCode + "' }.mid", notNullValue());

    }

    @Then("^I display the rate for currency with name: (.*)$")
    public void i_display_the_rate_for_currency_with_name(String currencyName) {


        System.out.println("Otrzymany response: " + response.asString());

        List<Object> rates = response.then().extract().path("rates.findAll { it.code == '" + currencyName + "' }");
        if (!response.getBody().asString().isEmpty()) {
            if (!rates.isEmpty()) {
                Object midValue = ((Map<String, Object>) rates.get(0)).get("currency");
                System.out.println("Wartość mid dla waluty " + currencyName + ":" + midValue);
            } else {
                System.out.println("Brak danych dla waluty o kodzie: " + currencyName);
            }
        } else {
            System.out.println("Otrzymana odpowiedź jest pusta.");
        }

//        response.then().body("rates.find { it.currency == currencyName }.mid", notNullValue());



    }

    @Then("^I display currencies with rates above: (\\d+)$")
    public void i_display_currencies_with_rates_above(BigDecimal rateThreshold) {
        // Filtruj kursy większe od wartości progowej
        ratesAboveThreshold = response.getBody().jsonPath().getList("rates.findAll { it.mid > " + rateThreshold + " }.mid", BigDecimal.class);

        // Wyświetl kursy powyżej wartości progowej
        System.out.println("Currencies with rates above " + rateThreshold + ": " + ratesAboveThreshold);
    }

    @Then("^I display currencies with rates below: (\\d+)$")
    public void i_display_currencies_with_rates_below(double rateThreshold) {
        response.then().statusCode(200);

        // Pobranie listy kursów walut z odpowiedzi i przefiltrowanie ich
        List<BigDecimal> rates = response.getBody().jsonPath().getList("rates.mid", BigDecimal.class);
        List<BigDecimal> ratesBelowThreshold = rates.stream()
                .filter(rate -> rate.compareTo(new BigDecimal(String.valueOf(rateThreshold))) < 0)
                .collect(Collectors.toList());

        // Logowanie kursów walut poniżej wartości granicznej
        System.out.println("Currencies with rates below " + rateThreshold + ": " + ratesBelowThreshold);
    }

    // Metoda zwracająca listę kursów powyżej wartości progowej
    public List<BigDecimal> getRatesAboveThreshold() {
        return ratesAboveThreshold;
    }




}
