# Test application

When a controller method used {+param} reserved characters (like /) are included in the parameter.
This is useful when you have a CMS like controller that lets callers specify a path as parmter.
However using the {+param} directive disables encoding and non-ascii parameters becomes garbage.

    @Controller("/")
    public class TestController {
        @Get(value = "/{+path}", produces = MediaType.TEXT_PLAIN)
        public String test(String path) {
            return path;
        }
    }
