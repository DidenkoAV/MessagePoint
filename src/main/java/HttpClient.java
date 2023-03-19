import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class HttpClient {
    private static final Logger log = Logger.getLogger(HttpClient.class);
    static String TEST_DATA_PROPERTIES_PATH = "src/main/resources/testData.properties";

    public static CloseableHttpClient initHttpClient() {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.createDefault();
        } catch (Exception ex) {
            log.info("ERROR with HTTP Client init: " + ex.getMessage());
        }
        log.info("HTTP Client has opened");
        return httpClient;
    }

    /**
     * You can change URL in testData.properties
     */
    public static String initURL() {
        FileInputStream fis;
        Properties property = new Properties();
        String URL = null;
        try {
            fis = new FileInputStream(TEST_DATA_PROPERTIES_PATH);
            property.load(fis);
            URL = property.getProperty("URL");
        } catch (IOException e) {
            log.info("ERROR with URL init: " + e.getMessage());
        }
        return URL;
    }

    /**
     * You can change ApiKey in testData.properties
     */
    public static String initApiKey() {
        FileInputStream fis;
        Properties property = new Properties();
        String API_KEY = null;
        try {
            fis = new FileInputStream(TEST_DATA_PROPERTIES_PATH);
            property.load(fis);
            API_KEY = property.getProperty("API_KEY");
        } catch (IOException e) {
            log.info("ERROR with API_KEY init: " + e.getMessage());
        }
        return API_KEY;
    }


    public String searchGetRequest(String search) throws Exception {
        HttpGet request = new HttpGet(initURL());
        String result = "";

        URI uri = new URIBuilder(request.getURI())
                .addParameter("apikey", initApiKey())
                .addParameter("s", search)
                .build();
        request.setURI(uri);
        log.info("Get URL is: " + uri);

        try (CloseableHttpResponse response = initHttpClient().execute(request)) {
            log.info("Response status code: " + response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();
            log.info("Response headers: " + entity.getContentType());

            if (entity != null) {
                result = EntityUtils.toString(entity);
                log.info("Response body: " + result);
            }
        } catch (Exception ex) {
            log.info("ERROR with search getRequest: " + ex.getMessage());
        }
        return result;
    }

    public String getByIdGetRequest(String id) throws URISyntaxException, IOException {
        HttpGet request = new HttpGet(initURL());
        String result = "";

        URI uri = new URIBuilder(request.getURI())
                .addParameter("apikey", initApiKey())
                .addParameter("i", id)
                .build();
        request.setURI(uri);
        log.info("Get URL is: " + uri);

        try (CloseableHttpResponse response = initHttpClient().execute(request)) {
            log.info("Response status code: " + response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();
            log.info("Response headers: " + entity.getContentType());

            if (entity != null) {
                result = EntityUtils.toString(entity);
                log.info("Response body: " + result);
            }
        } catch (Exception ex) {
            log.info("ERROR with getById Request: " + ex.getMessage());
        }
        return result;
    }

    public String getByTitleGetRequest(String title) throws URISyntaxException, IOException {
        HttpGet request = new HttpGet(initURL());
        String result = "";

        URI uri = new URIBuilder(request.getURI())
                .addParameter("apikey", initApiKey())
                .addParameter("t", title)
                .build();
        request.setURI(uri);
        log.info("Get URL is: " + uri);

        try (CloseableHttpResponse response = initHttpClient().execute(request)) {
            log.info("Response status code: " + response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity);
                log.info("Response body: " + result);
            }
        } catch (Exception ex) {
            log.info("ERROR with getByTitle Request: " + ex.getMessage());
        }
        return result;
    }

    public static void close() throws IOException {
        initHttpClient().close();
        log.info("HTTP Client has closed");
    }
}

