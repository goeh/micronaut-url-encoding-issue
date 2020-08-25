package url.encoding.issue;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/")
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Get(value = "/{+path}", produces = MediaType.TEXT_PLAIN)
    public String test(String path) {
        LOG.info("Value in controller: {}", path);
        return path;
    }
}
