package url.encoding.issue;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EncodingTests {

    static EmbeddedServer embeddedServer;
    static RxHttpClient httpClient;

    @BeforeAll
    public static void startup() {
        embeddedServer = ApplicationContext.run(EmbeddedServer.class);
        httpClient = embeddedServer.getApplicationContext().createBean(RxHttpClient.class, embeddedServer.getURL());
    }

    private String encode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    @Test
    public void singlePathAscii() {
        String result = httpClient.toBlocking().exchange("/filename.txt", String.class).body();
        assertEquals("filename.txt", result);
    }

    @Test
    public void singlePathNonAscii() {
        String result = httpClient.toBlocking().exchange("/göran.txt", String.class).body();
        assertEquals("göran.txt", result);
    }

    @Test
    public void singlePathUrlEncoded() throws Exception {
        String result = httpClient.toBlocking().exchange("/" + encode("göran.txt"), String.class).body();
        assertEquals("göran.txt", result);
    }

    @Test
    public void singlePathNonAsciiDeclarativeClient() throws Exception {
        TestClient client = embeddedServer.getApplicationContext().getBean(TestClient.class);
        String result = client.filename("göran.txt");
        assertEquals("göran.txt", result);
    }

    @Test
    public void multiPathAscii() {
        String result = httpClient.toBlocking().exchange("/foo/bar/filename.txt", String.class).body();
        assertEquals("foo/bar/filename.txt", result);
    }

    @Test
    public void multiPathNonAsciiDeclarativeClient() throws Exception {
        TestClient client = embeddedServer.getApplicationContext().getBean(TestClient.class);
        String result = client.path("/foo/bar/göran.txt");
        assertEquals("foo/bar/göran.txt", result);
    }
}
