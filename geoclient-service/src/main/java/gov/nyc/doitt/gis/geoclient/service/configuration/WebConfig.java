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

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.lang.NonNull;
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

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(WebConfig.class);

    /**
     * Implements part of the legacy content negotiation/path matching
     * strategy that will eventually be deprecated since it is insecure.
     */
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET");
    }

    @Override
    public void addFormatters(@NonNull FormatterRegistry registry) {
        registry.addConverter(searchResultConverter());
    }

    /**
     * Implements part of the legacy content negotiation/path matching
     * strategy that will eventually be deprecated since it is insecure.
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#configureContentNegotiation(org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer)
     */
    @Override
    public void configureContentNegotiation(@NonNull ContentNegotiationConfigurer configurer) {
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
    public void configureDefaultServletHandling(@NonNull DefaultServletHandlerConfigurer configurer) {
        configurer.enable("geoclientDispatcherServlet");
    }

    /**
     * Adds JSON and XML message converters.
     */
    @Override
    public void configureMessageConverters(@NonNull List<HttpMessageConverter<?>> converters) {
        converters.add(jsonMessageConverter());
        converters.add(xmlMessageConverter());
    }

    // Beans //

    @Bean
    public HttpMessageConverter<?> jsonMessageConverter() {
        //MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        //converter.getObjectMapper()
        //    .configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    public HttpExchangeRepository httpExchangeRepository() {
        return new InMemoryHttpExchangeRepository();
    }

    @Bean
    public JacksonXmlMarshaller marshaller() {
        JacksonXmlMarshaller marshaller = new JacksonXmlMarshaller();
        // Note: Jackson handles serialization differently, aliases are handled via annotations
        return marshaller;
    }

    @Bean
    public SearchResultConverter searchResultConverter() {
        return new SearchResultConverter();
    }

    @Bean
    public HttpMessageConverter<?> xmlMessageConverter() {
        return new MarshallingHttpMessageConverter(marshaller());
    }

    /**
     * Tomcat 8.5 before 8.5.44 and 9 before 9.0.23 do not have a certain API
     * that Spring assumes is there and so removing the
     * "tomcatWebServerFactoryCustomizer" is necessary to prevent a
     * NoSuchMethodError.
     *
     * However, since this bean isn't actually used when deploying a war file
     * to a container so it's safe to remove.
     *
     * Following an upgrade from spring-boot 2.7.5 to 2.7.11, this method
     * caused a NoSuchBeanDefinitionException in mock test
     * SingleFieldSearchControllerTest because this bean isn't there to begin
     * with.
     *
     * Adding a try/catch and ignoring the exception fixes the issue. Once
     * the affected versions of Tomcat are no longer in use, this method can
     * be removed.
     *
     * See https://github.com/spring-projects/spring-boot/issues/19308
     */
    @Bean
    public static BeanFactoryPostProcessor removeTomcatWebServerCustomizer() {
        return new BeanFactoryPostProcessor() {
            public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) {
                if (((DefaultListableBeanFactory) beanFactory).containsBeanDefinition(
                    "tomcatWebServerFactoryCustomizer")) {
                    //((DefaultListableBeanFactory)beanFactory).removeBeanDefinition("tomcatWebServerFactoryCustomizer");
                    //LOGGER.info("Removed bean \"tomcatWebServerFactoryCustomizer\".");
                    LOGGER.info(
                        "Bean \"tomcatWebServerFactoryCustomizer\" found: NOT removing it due to use of recent versions of Tomcat/Spring Boot.");
                }
                else {
                    LOGGER.info(
                        "Bean \"tomcatWebServerFactoryCustomizer\" not found: assuming recent versions of Tomcat/Spring Boot.");
                }
            }
        };
    }
}
