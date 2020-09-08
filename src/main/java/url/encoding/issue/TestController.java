package url.encoding.issue;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Get(value = "{+path}", produces = MediaType.TEXT_PLAIN)
    public String test(String path) {
        LOG.info("Value as decoded by Micronaut : {}", path);
        return path;
    }

    @Get(value = "/encoded/{path}", produces = MediaType.TEXT_PLAIN)
    public String encoded(String path) {
        String decodedPath;
        try {
            decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        LOG.info("Value as decoded by Micronaut : {}", path);
        LOG.info("Value as decoded in Controller: {}", decodedPath);
        return decodedPath;
    }
}
