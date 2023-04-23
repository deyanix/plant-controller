package com.plantcontroller.server;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.List;

@Configuration
public class CorsFilterConfiguration {
	@Bean("corsFilter")
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		final CorsConfiguration config = buildCorsConfiguration();
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return new FilterRegistrationBean<>(new CorsFilter(source));
	}

	private CorsConfiguration buildCorsConfiguration() {
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedHeaders(List.of("GET", "POST", "OPTIONS"));
		config.setAllowedOriginPatterns(List.of("*"));
		return config;
	}
}

