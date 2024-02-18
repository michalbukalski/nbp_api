package stepDefinitions;

import org.junit.Test;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CurrencyExchangeTest {

    @Test
    public void testDisplayCurrenciesWithRatesAbove() {
        // Dane testowe - lista kursów
        List<BigDecimal> rates = Arrays.asList(
                new BigDecimal("0.112"), new BigDecimal("4.0325"), new BigDecimal("2.6304"), new BigDecimal("0.5155"));

        // Wartość progowa
        BigDecimal rateThreshold = new BigDecimal("2");

        // Wywołanie metody
        CurrencyExchangeSteps steps = new CurrencyExchangeSteps();
        steps.i_display_currencies_with_rates_above(rateThreshold);

        // Pobierz listę kursów powyżej wartości progowej z CurrencyExchangeSteps
        List<BigDecimal> ratesAboveThreshold = steps.getRatesAboveThreshold();

        // Asercja sprawdzająca czy lista kursów powyżej wartości progowej nie jest pusta
        assertFalse(ratesAboveThreshold.isEmpty());

        // Asercja sprawdzająca czy lista kursów powyżej wartości progowej zawiera oczekiwane kursy
        assertTrue(ratesAboveThreshold.contains(new BigDecimal("4.0325")));
        assertTrue(ratesAboveThreshold.contains(new BigDecimal("2.6304")));
    }
}
