package url.encoding.issue;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeclarativeClientEncodingTests {

    static EmbeddedServer embeddedServer;
    static TestClient client;

    @BeforeAll
    public static void startup() {
        embeddedServer = ApplicationContext.run(EmbeddedServer.class);
        client = embeddedServer.getApplicationContext().getBean(TestClient.class);
    }

    private String encode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    @Test
    public void singlePathNonAsciiDeclarativeClient() throws Exception {
        String result = client.standard("göran.txt");
        assertEquals("göran.txt", result);
    }

    @Test
    public void multiPathNonAsciiDeclarativeClient() throws Exception {
        String result = client.standard("/foo/bar/göran.txt");
        assertEquals("/foo/bar/göran.txt", result);
    }

    @Test
    public void multiPathNonAsciiIncludeReservedCharsDeclarativeClient() throws Exception {
        String result = client.includeReservedChars("/foo/bar/göran.txt");
        assertEquals("foo/bar/göran.txt", result);
    }
}
