package demo.config.security;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author admin
 * 2022/7/24 15:32
 **/
@Configuration
public class WebServerAutoConfiguration {

    static String PATH = "/err";

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST, PATH);
        ErrorPage errorPage401 = new ErrorPage(HttpStatus.UNAUTHORIZED, PATH);
        ErrorPage errorPage403 = new ErrorPage(HttpStatus.FORBIDDEN, PATH);
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, PATH);
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, PATH);
        factory.addErrorPages(errorPage400, errorPage401, errorPage403, errorPage404, errorPage500);
        return factory;
    }
}
