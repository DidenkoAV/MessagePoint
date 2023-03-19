import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.SearchByIdPojo;
import pojo.SearchByTitlePojo;
import pojo.SearchPojo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ApiOmdbTest {
    //static String TEST_DATA3_CASE1 = "The STEM Journals";
    //static String TEST_DATA4_CASE1 = "Activision: STEM - in the Videogame Industry";

    @DataProvider(name="data-provider-searchMethodTest")
    public Object[][] dataProviderMethod() throws IOException {
        FileInputStream fis = new FileInputStream("src/main/resources/testData.properties");
        Properties properties = new Properties();
        properties.load(fis);
        return new Object[][] {{properties.getProperty("searchInput"), properties.getProperty("title"),
                properties.getProperty("title1")}};
    }

    @DataProvider(name="data-provider-searchByIdMethodTest")
    public Object[][] dataProviderMethod2() throws IOException {
        FileInputStream fis = new FileInputStream("src/main/resources/testData.properties");
        Properties properties = new Properties();
        properties.load(fis);
        return new Object[][] {{properties.getProperty("searchInput"), properties.getProperty("title"),
                properties.getProperty("released"), properties.getProperty("director")}};
    }

    @DataProvider(name="data-provider-searchByTitleMethodTest")
    public Object[][] dataProviderMethod3() throws IOException {
        FileInputStream fis = new FileInputStream("src/main/resources/testData.properties");
        Properties properties = new Properties();
        properties.load(fis);
        return new Object[][] {{properties.getProperty("searchInputByTitle"), properties.getProperty("plot"),
                 properties.getProperty("runtime")}};
    }

    @Test(dataProvider = "data-provider-searchMethodTest")
    @Description("Test the get of a 'search' request with a value of 'stem' ")
    public void searchMethodTest(String search, String title, String title1) throws Exception {
        HttpClient httpClient = new HttpClient();
        JSONObject jsonObject = new JSONObject(httpClient.searchGetRequest(search));
        Allure.addAttachment("Search response: ","application/json" ,jsonObject.toString(), ".json");
        ObjectMapper objectMapper = new ObjectMapper();
        SearchPojo rootSearch = objectMapper.readValue(jsonObject.toString(), SearchPojo.class);

        Assert.assertTrue(Integer.parseInt(rootSearch.totalResults)>30);
        Assert.assertTrue(rootSearch.search.stream()
                .anyMatch(n -> n.title.equals(title)));
        Assert.assertTrue(rootSearch.search.stream()
                .anyMatch(n -> n.title.equals(title1)));
        //Assert.assertTrue(rootSearch.search.stream()
        //.anyMatch(n -> n.title.equals(TEST_DATA3_CASE1)));
        //Assert.assertTrue(rootSearch.search.stream()
        // .anyMatch(n -> n.title.equals(TEST_DATA4_CASE1)));
        //Assert.assertTrue(rootSearch.search.stream().count()>30);
    }

    @Test(dataProvider = "data-provider-searchByIdMethodTest")
    @Description("Test 'search by id' method'")
    public void checkReleasedAndDirectorSearchByIdMethodTest(String search, String title, String released, String director) throws Exception {
        HttpClient httpClient = new HttpClient();
        JSONObject jsonObject = new JSONObject(httpClient.searchGetRequest(search));
        Allure.addAttachment("Search response: ", "application/json", jsonObject.toString(), ".json");
        ObjectMapper objectMapper = new ObjectMapper();
        SearchPojo searchPojo
                = objectMapper.readValue(jsonObject.toString(), SearchPojo.class);

        List<String> ids = searchPojo.search.stream()
                .filter(n -> n.title.equals(title))
                .map(n -> n.imdbID).collect(Collectors.toList());
        String id = ids.get(0);

        JSONObject stemCellItem = new JSONObject(httpClient.getByIdGetRequest(id));
        Allure.addAttachment("Search response by id: ", "application/json",jsonObject.toString()  ,".json");

        ObjectMapper stemCellItemObjectMapper = new ObjectMapper();
        SearchByIdPojo searchByIdPojo
                = stemCellItemObjectMapper.readValue(stemCellItem.toString(), SearchByIdPojo.class);

        Assert.assertTrue(searchByIdPojo.released.equals(released));
        Assert.assertTrue(searchByIdPojo.director.equals(director));
    }

    @Test(dataProvider = "data-provider-searchByTitleMethodTest")
    @Description("Test 'search by title' method'")
    public void searchByTitleMethodTest(String searchByTitle, String plot,String runtime) throws Exception {
        HttpClient httpClient = new HttpClient();
        JSONObject jsonObject = new JSONObject(httpClient.getByTitleGetRequest(searchByTitle));
        Allure.addAttachment("Search response by title: ", "application/json" , jsonObject.toString(), ".json");
        ObjectMapper objectMapper = new ObjectMapper();
        SearchByTitlePojo searchByTitlePojo
                = objectMapper.readValue(jsonObject.toString(), SearchByTitlePojo.class);

        Assert.assertTrue(searchByTitlePojo.plot.contains(plot));
        Assert.assertTrue(searchByTitlePojo.runtime.equals(runtime));
    }
}

