package com.taotao.sso.spring.config;

import org.apache.catalina.Context;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {
	
	/**
	 * 为使cookie的domain域名合法，默认不支持以.开头，只能数字字母开头
	 * @return
	 */
	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
		return new WebServerFactoryCustomizer<TomcatServletWebServerFactory>() {

			@Override
			public void customize(TomcatServletWebServerFactory factory) {
				factory.addContextCustomizers(new TomcatContextCustomizer() {
					
					@Override
					public void customize(Context context) {
						context.setCookieProcessor(new LegacyCookieProcessor());
					}
					
				});
			}
			
		};
	}
	
}
