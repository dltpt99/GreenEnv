package nt.greenenv.greenenv.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("file:///C:/Users/lab330/Desktop/Project/greenenv_image/")
    private String realPath;
    @Value("/image/**")
    private String srcPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(srcPath)
                .addResourceLocations(realPath);
    }
}
