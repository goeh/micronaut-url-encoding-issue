package url.encoding.issue;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client("/")
public interface TestClient {

    @Get("/{path}")
    String filename(String path);

    @Get("/{+path}")
    String path(String path);
}
