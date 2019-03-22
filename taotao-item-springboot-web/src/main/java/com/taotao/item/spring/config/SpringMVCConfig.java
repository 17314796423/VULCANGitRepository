package com.taotao.item.spring.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {

	@Bean
    public CommandLineRunner customFreemarker(final FreeMarkerViewResolver resolver) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                resolver.setOrder(0);
            }
        };
    }
	
}
