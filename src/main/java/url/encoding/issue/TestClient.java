package url.encoding.issue;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client("/")
public interface TestClient {

    @Get("{path}")
    String standard(String path);

    @Get("{+path}")
    String includeReservedChars(String path);

    @Get("/encoded/{path}")
    String withEncodedPath(String path);
}
