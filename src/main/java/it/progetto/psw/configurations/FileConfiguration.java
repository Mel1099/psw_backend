package it.progetto.psw.configurations;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebMvc
public class FileConfiguration implements WebMvcConfigurer {

    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("/files/**")
                .addResourceLocations("file:///C:/Users/Trony/Desktop/gioielleriaFoto/prodotti/").setCachePeriod(3600).resourceChain(true).addResolver(new PathResourceResolver());
    }
}
