/*
 * Copyright 2013-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nyc.doitt.gis.geoclient.service.configuration;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import gov.nyc.doitt.gis.geoclient.service.search.web.response.SearchResultConverter;

/**
 * Customizes the default web configuration by implementing the Spring
 * {@link WebMvcConfigurer} interface.
 *
 * Some of the configuration is done to support legacy Spring MVC
 * content negotiation which is not generally a good idea.
 *
 * Once Geoclient doesn't have to support legacy functionality like
 * controller dispatch based on file type extensions, the legacy stuff
 * should be removed.
 *
 * NOTE: Spring Boot configures common Spring MVC defaults described in the
 * reference docs section "Spring MVC Auto-configuration". This happens
 * when a class has the @Configuration annotation and implements
 * WebMvcConfigurer.
 *
 * See https://www.baeldung.com/spring-mvc-content-negotiation-json-xml
*/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Implements part of the legacy content negotiation/path matching
     * strategy that will eventually be deprecated since it is insecure.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(searchResultConverter());
    }

    /**
     * Implements part of the legacy content negotiation/path matching
     * strategy that will eventually be deprecated since it is insecure.
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#configureContentNegotiation(org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer)
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                // Enable the query parameter strategy
                .favorParameter(true)
                // Set the name of the parameter (default is "format")
                .parameterName("f")
                // Set a default content type if the parameter is not specified
                .defaultContentType(MediaType.APPLICATION_JSON)
                // Map parameter values to actual MediaTypes
                .mediaType("json", MediaType.APPLICATION_JSON).mediaType("xml", MediaType.APPLICATION_XML);
    }

    /*
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#configureDefaultServletHandling(org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer)
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable("geoclientDispatcherServlet");
    }

    // Beans //

    @Bean
    public HttpExchangeRepository httpExchangeRepository() {
        return new InMemoryHttpExchangeRepository();
    }

    @Bean
    public SearchResultConverter searchResultConverter() {
        return new SearchResultConverter();
    }

}
