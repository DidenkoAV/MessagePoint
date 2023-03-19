import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HttpClient {
    private static final Logger log = Logger.getLogger(HttpClient.class);
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    String URL = "https://www.omdbapi.com";
    String API_KEY = "b57aaf61";

    public static void main(String[] args) throws Exception {
        HttpClient httpClient1 = new HttpClient();
        try {
            httpClient1.searchGetRequest("STEM");
        } finally {
            httpClient1.close();
        }
    }

    public String searchGetRequest(String search) throws Exception {
        HttpGet request = new HttpGet(URL);
        String result = "";

        URI uri = new URIBuilder(request.getURI())
                .addParameter("apikey", API_KEY)
                .addParameter("s", search)
                .build();
        request.setURI(uri);
        log.info("Get URL is: " + uri );


        try (CloseableHttpResponse response = httpClient.execute(request)) {
            log.info("Response status code: " + response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();
            log.info("Response headers: " + entity.getContentType());

            if (entity != null) {
                 result = EntityUtils.toString(entity);
                 log.info("Response body: " + result);
            }
        } return result;
    }

    public String getByIdGetRequest(String id) throws URISyntaxException, IOException {
        HttpGet request = new HttpGet(URL);
        String result = "";

        URI uri = new URIBuilder(request.getURI())
                .addParameter("apikey", API_KEY)
                .addParameter("i", id)
                .build();
        request.setURI(uri);
        log.info("Get URL is: " + uri );

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            log.info("Response status code: " + response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();
            log.info("Response headers: " + entity.getContentType());

            if (entity != null) {
                 result = EntityUtils.toString(entity);
                log.info("Response body: " + result);
            }
        } return result;
    }

    public String getByTitleGetRequest(String title) throws URISyntaxException, IOException {
        HttpGet request = new HttpGet(URL);
        String result = "";

        URI uri = new URIBuilder(request.getURI())
                .addParameter("apikey", API_KEY)
                .addParameter("t", title)
                .build();
        request.setURI(uri);
        log.info("Get URL is: " + uri );


        try (CloseableHttpResponse response = httpClient.execute(request)) {
            log.info("Response status code: " + response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity);
                log.info("Response body: " + result);
            }
        }
        return result;
    }
    private void close() throws IOException {
        httpClient.close();
        log.info("httpClient has closed");
    }
}

