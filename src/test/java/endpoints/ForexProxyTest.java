package endpoints;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnit4.class)
public class ForexProxyTest {


    private static String EUR = "EUR";
    private static String USD = "USD";
    private static String WRONG_CURRENCY = "EURRR";

    private static String TO = EUR;
    private static String FROM = USD;
    private static Double AMOUNT = 100.00;

    OkHttpClient client = Mockito.mock(OkHttpClient.class);


    @Test
    public void givenAmountAndExchangeRateThenCurrencyRateShouldMatched() {
        Double result = ForexProxy.calculateCurrencyExchange(100.00, 15.00);
        assertEquals(1500.0, result);
    }

    @Test
    public void givenAmountAndExchangeRateThenConvertCurrencyShouldMatchTheResult() {
        Double result = ForexProxy.convertCurrency(100.00, 15.00);
        assertEquals(1500.0, result);
    }

    @Test
    public void givenUSDCurrencyScoopDataShouldReturnCorrectData() {
        Double result = ForexProxy.getCurrencyScoopData(USD);
        assertEquals(1.0, result);
    }

    @Test
    public void givenWrongCurrencyScoopDataShouldReturnNull() {
        Double result = ForexProxy.getCurrencyScoopData(WRONG_CURRENCY);
        assertNull(result);
    }

    @Test
    public void getCurrencyExchangeApiData() throws IOException {

    }

    @Test
    public void whenCallToBuildCurrencyScoopApiRequestShouldReturnCorrectRequest() throws IOException {
        Request request = ForexProxy.buildCurrencyScoopApiRequest();
        assertNotNull(request);
    }

    @Test
    public void whenCallToBuildCurrencyScoopApiRequestShouldReturnCorrectRequestURL() throws IOException {
        Request request = ForexProxy.buildCurrencyScoopApiRequest();
        assertEquals("https://currencyscoop.p.rapidapi.com/latest", request.url().toString());
    }

    @Test
    public void whenCallToBuildCurrencyScoopApiRequestShouldReturnCorrectRequestWithHeaders() throws IOException {
        Request request = ForexProxy.buildCurrencyScoopApiRequest();
        assertTrue(request.headers().size() > 0);
    }

    @Test
    public void whenCallToBuildCurrencyExchangeApiRequestThenShouldReturnCorrectRequest() throws IOException {
        Request request = ForexProxy.buildCurrencyExchangeApiRequest(TO, FROM, AMOUNT);
        assertNotNull(request);
    }

    @Test
    public void whenCallToBuildCurrencyExchangeApiRequestThenShouldReturnCorrectRequestURL() throws IOException {
        Request request = ForexProxy.buildCurrencyExchangeApiRequest(TO, FROM, AMOUNT);
        assertEquals("https://currency-exchange.p.rapidapi.com/exchange?to=EUR&from=USD&q=100.0", request.url().toString());
    }

    @Test
    public void whenCallToBuildCurrencyExchangeApiRequestThenShouldReturnCorrectRequestWithHeaders() throws IOException {
        Request request = ForexProxy.buildCurrencyExchangeApiRequest(TO, FROM, AMOUNT);
        assertTrue(request.headers().size() > 0);
    }


}
