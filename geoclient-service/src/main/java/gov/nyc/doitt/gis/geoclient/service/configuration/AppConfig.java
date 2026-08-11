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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import gov.nyc.doitt.gis.geoclient.api.invoker.DoubleFieldSetConverter;
import gov.nyc.doitt.gis.geoclient.api.invoker.FieldSetConverter;
import gov.nyc.doitt.gis.geoclient.api.invoker.GeosupportInvoker;
import gov.nyc.doitt.gis.geoclient.api.invoker.GeosupportService;
import gov.nyc.doitt.gis.geoclient.api.invoker.GeosupportServiceContext;
import gov.nyc.doitt.gis.geoclient.api.invoker.GeosupportServiceImpl;
import gov.nyc.doitt.gis.geoclient.api.mapper.GeosupportVersionMapper;
import gov.nyc.doitt.gis.geoclient.api.mapper.Mapper;
import gov.nyc.doitt.gis.geoclient.api.version.FieldSet;
import gov.nyc.doitt.gis.geoclient.api.version.GeosupportVersion;
import gov.nyc.doitt.gis.geoclient.api.version.Version;
import gov.nyc.doitt.gis.geoclient.config.GeosupportConfig;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.jni.Geoclient;
import gov.nyc.doitt.gis.geoclient.jni.GeoclientJni;
import gov.nyc.doitt.gis.geoclient.parser.configuration.ParserConfig;
import gov.nyc.doitt.gis.geoclient.search.CountyResolver;
import gov.nyc.doitt.gis.geoclient.search.ResponseStatus;
import gov.nyc.doitt.gis.geoclient.search.SearchId;
import gov.nyc.doitt.gis.geoclient.search.SingleFieldSearchHandler;
import gov.nyc.doitt.gis.geoclient.search.spi.ResponseStatusReader;
import gov.nyc.doitt.gis.geoclient.search.task.DefaultInitialSearchTaskBuilder;
import gov.nyc.doitt.gis.geoclient.search.task.DefaultSpawnedTaskBuilder;
import gov.nyc.doitt.gis.geoclient.search.task.InitialSearchTaskBuilder;
import gov.nyc.doitt.gis.geoclient.search.task.SearchTaskFactory;
import gov.nyc.doitt.gis.geoclient.search.task.SpawnedSearchTaskBuilder;
import gov.nyc.doitt.gis.geoclient.search.mapper.ResponseStatusMapper;

/**
 * Java-based configuration for the <code>geoclient-service</code> application.
 *
 * @author mlipper
 * @since 1.0
 */
@Configuration
@PropertySource(value = "classpath:version.properties")
@ComponentScan(basePackages = { "gov.nyc.doitt.gis.geoclient.service",
        "gov.nyc.doitt.gis.geoclient.parser.configuration" })
public class AppConfig implements GeosupportServiceContext {

    @Autowired
    private Environment env;

    // Spring bean methods

    @Bean
    public ParserConfig parserConfig() {
        return new ParserConfig();
    }

    @Bean
    @Override
    public FieldSetConverter latLongFieldSetConverter() {
        return new DoubleFieldSetConverter(latLongConversions());
    }

    @Bean
    public List<FieldSet> latLongConversions() {
        List<FieldSet> conversions = new ArrayList<>();
        // Self-encapsulate by using instance methods instead of Function
        // constants directly to prevent having two places where function
        // id needs to stay consistent.
        conversions.add(new FieldSet("F" + functionAP().getId(), new String[] { "latitude", "longitude" }));
        conversions.add(new FieldSet("F" + function1B().getId(),
            new String[] { "latitude", "longitude", "latitudeInternalLabel", "longitudeInternalLabel" }));
        conversions.add(new FieldSet("F" + functionBL().getId(),
            new String[] { "latitudeInternalLabel", "longitudeInternalLabel" }));
        conversions.add(new FieldSet("F" + functionBN().getId(),
            new String[] { "latitudeInternalLabel", "longitudeInternalLabel" }));
        conversions.add(new FieldSet("F" + function2W().getId(), new String[] { "latitude", "longitude" }));
        return conversions;
    }

    @Bean
    public Geoclient geoclient() {
        return new GeoclientJni();
    }

    @Bean
    public SingleFieldSearchHandler singleFieldSearchHandler() {
        return new SingleFieldSearchHandler(searchId(), parserConfig().singleFieldSearchParser(), searchBuilder());
    }

    @Bean
    public SearchTaskFactory searchBuilder() {
        return new SearchTaskFactory(initialSearchTaskBuilder(), spawnedSearchTaskBuilder());
    }

    @Bean
    public InitialSearchTaskBuilder initialSearchTaskBuilder() {
        return new DefaultInitialSearchTaskBuilder(countyResolver(), geosupportInvoker(), responseStatusReader());
    }

    @Bean
    public SpawnedSearchTaskBuilder spawnedSearchTaskBuilder() {
        return new DefaultSpawnedTaskBuilder(countyResolver(), geosupportInvoker(), responseStatusReader());
    }

    @Bean
    public GeosupportInvoker geosupportInvoker() {
        return geosupportService();
    }

    @Bean
    public ResponseStatusReader responseStatusReader() {
        return SearchSpiAdapters.asStatusReader(responseStatusMapper());
    }

    @Bean
    public CountyResolver countyResolver() {
        return new CountyResolver(parserConfig().boroughNamesToBoroughMap(), parserConfig().cityNamesToBoroughMap());
    }

    @Bean
    public SearchId searchId() {
        return new SearchId(hostname());
    }

    @Bean
    public String hostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e) {
            return "UnknownHost";
        }
    }

    @Bean
    public GeosupportConfig geosupportConfiguration() {
        return new GeosupportConfig(geoclient());
    }

    @Bean
    public GeosupportService geosupportService() {
        return new GeosupportServiceImpl(this);
    }

    @Bean
    public Mapper<ResponseStatus> responseStatusMapper() {
        return new ResponseStatusMapper();
    }

    // Regular methods
    @Override
    public Function geosupportFunction(String id) {
        return geosupportConfiguration().getFunction(id);
    }

    public Mapper<GeosupportVersion> geosupportVersionMapper() {
        return new GeosupportVersionMapper();
    }

    // Do not declare as @Bean, but as a regular method
    // since we don't want proxies generated for incoming args
    @Override
    public Version version(Map<String, Object> functionHrData) {

        Version version = new Version();
        version.setGeosupportVersion(geosupportVersionMapper().fromParameters(functionHrData, new GeosupportVersion()));
        // Uses version.properties file created by geoclient-service gradle build
        version.setGeoclientJniVersion(env.getProperty("jni.version", "UNKNOWN"));
        version.setGeoclientVersion(env.getProperty("core.version", "UNKNOWN"));
        version.setGeoclientParserVersion(env.getProperty("parser.version", "UNKNOWN"));
        version.setGeoclientServiceVersion(env.getProperty("service.version", "UNKNOWN"));
        version.setAccessMethod("Local/JNI");
        return version;
    }

}
