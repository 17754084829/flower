package web.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import web.filter.PermissionFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Config {
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return (factory -> {
            ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/resource/404.html");
            factory.addErrorPages(errorPage404);
            ErrorPage errorPage500=new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/resource/500.html");
            factory.addErrorPages(errorPage500);
        });
    }

    @Bean
    public FilterRegistrationBean permissionFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        PermissionFilter permissionFilter = new PermissionFilter();
        filterRegistrationBean.setFilter(permissionFilter);
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        filterRegistrationBean.setUrlPatterns(urls);//配置过滤规则
        return filterRegistrationBean;
    }

}
