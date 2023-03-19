import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;

public class Hooks {

    @BeforeClass
    public void openHttpClient() {
        HttpClient.initHttpClient();
    }

    @AfterClass
    public void closeHttpClient() throws IOException {
        HttpClient.close();
    }
}
