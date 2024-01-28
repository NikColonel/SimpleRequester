import com.github.nikcolonel.Request;
import com.github.nikcolonel.model.HttpMethod;
import com.github.nikcolonel.model.HttpResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class TestHttpRequest {

    @Test
    public void testGet() {
        try {
            HttpResponse httpResponse = new Request()
                    .https()
                    .httpMethod(HttpMethod.GET)
                    .url("https://google.com")
                    .header("Content-Type", "*")
                    .isLogging(true)
                    .build()
                    .send();
            Assert.assertEquals(200, httpResponse.getResponseCode());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
