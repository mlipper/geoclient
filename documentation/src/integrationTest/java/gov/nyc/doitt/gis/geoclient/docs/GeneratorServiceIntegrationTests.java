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
package gov.nyc.doitt.gis.geoclient.docs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@EnabledIf("gov.nyc.doitt.gis.geoclient.test.RequiresRestServiceCustomCondtion#restServiceRunning")
@SpringBootTest
@EnableConfigurationProperties(ExternalProperties.class)
public class GeneratorServiceIntegrationTests {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorServiceIntegrationTests.class);

    @Autowired
    private GeneratorService service;

    @DisplayName("Generate address samples")
    @ParameterizedTest(name = "{index} ==> ''{0}''")
    @CsvFileSource(resources = "/address.csv", useHeadersInDisplayName = true)
    void addressExamples(@AggregateWith(SampleAggregator.class) Sample sample) {
        logger.info("{}", sample);
        assertNotNull(sample.getId());
        assertEquals(PathVariable.ADDRESS.toString(), sample.getPathVariable());
        assertNotNull(sample.getDescription());
        assertFalse(sample.getQueryString().isEmpty());
        logger.info("{}", sample.getQueryString());
        this.service.generate(sample);
    }

    @DisplayName("Generate addresspoint samples")
    @ParameterizedTest(name = "{index} ==> ''{0}''")
    @CsvFileSource(resources = "/addresspoint.csv", useHeadersInDisplayName = true)
    void addresspointExamples(@AggregateWith(SampleAggregator.class) Sample sample) {
        logger.info("{}", sample);
        assertNotNull(sample.getId());
        assertEquals(PathVariable.ADDRESS_POINT.toString(), sample.getPathVariable());
        assertNotNull(sample.getDescription());
        assertFalse(sample.getQueryString().isEmpty());
        logger.info("{}", sample.getQueryString());
        this.service.generate(sample);
    }

    @DisplayName("Generate bbl samples")
    @ParameterizedTest(name = "{index} ==> ''{0}''")
    @CsvFileSource(resources = "/bbl.csv", useHeadersInDisplayName = true)
    void bblExamples(@AggregateWith(SampleAggregator.class) Sample sample) {
        logger.info("{}", sample);
        assertNotNull(sample.getId());
        assertEquals(PathVariable.BBL.toString(), sample.getPathVariable());
        assertNotNull(sample.getDescription());
        assertFalse(sample.getQueryString().isEmpty());
        logger.info("{}", sample.getQueryString());
        this.service.generate(sample);
    }

    @DisplayName("Generate bin samples")
    @ParameterizedTest(name = "{index} ==> ''{0}''")
    @CsvFileSource(resources = "/bin.csv", useHeadersInDisplayName = true)
    void binExamples(@AggregateWith(SampleAggregator.class) Sample sample) {
        logger.info("{}", sample);
        assertNotNull(sample.getId());
        assertEquals(PathVariable.BIN.toString(), sample.getPathVariable());
        assertNotNull(sample.getDescription());
        assertFalse(sample.getQueryString().isEmpty());
        logger.info("{}", sample.getQueryString());
        this.service.generate(sample);
    }

    @DisplayName("Generate blockface samples")
    @ParameterizedTest(name = "{index} ==> ''{0}''")
    @CsvFileSource(resources = "/blockface.csv", useHeadersInDisplayName = true)
    void blockfaceExamples(@AggregateWith(SampleAggregator.class) Sample sample) {
        logger.info("{}", sample);
        assertNotNull(sample.getId());
        assertEquals(PathVariable.BLOCKFACE.toString(), sample.getPathVariable());
        assertNotNull(sample.getDescription());
        assertFalse(sample.getQueryString().isEmpty());
        logger.info("{}", sample.getQueryString());
        this.service.generate(sample);
    }

    @DisplayName("Generate intersection samples")
    @ParameterizedTest(name = "{index} ==> ''{0}''")
    @CsvFileSource(resources = "/intersection.csv", useHeadersInDisplayName = true)
    void intersectionExamples(@AggregateWith(SampleAggregator.class) Sample sample) {
        logger.info("{}", sample);
        assertNotNull(sample.getId());
        assertEquals(PathVariable.INTERSECTION.toString(), sample.getPathVariable());
        assertNotNull(sample.getDescription());
        assertFalse(sample.getQueryString().isEmpty());
        logger.info("{}", sample.getQueryString());
        this.service.generate(sample);
    }

    @DisplayName("Generate normalize samples")
    @ParameterizedTest(name = "{index} ==> ''{0}''")
    @CsvFileSource(resources = "/normalize.csv", useHeadersInDisplayName = true)
    void normalizeExamples(@AggregateWith(SampleAggregator.class) Sample sample) {
        logger.info("{}", sample);
        assertNotNull(sample.getId());
        assertEquals(PathVariable.NORMALIZE.toString(), sample.getPathVariable());
        assertNotNull(sample.getDescription());
        assertFalse(sample.getQueryString().isEmpty());
        logger.info("{}", sample.getQueryString());
        this.service.generate(sample);
    }

    @DisplayName("Generate place samples")
    @ParameterizedTest(name = "{index} ==> ''{0}''")
    @CsvFileSource(resources = "/place.csv", useHeadersInDisplayName = true)
    void placeExamples(@AggregateWith(SampleAggregator.class) Sample sample) {
        logger.info("{}", sample);
        assertNotNull(sample.getId());
        assertEquals(PathVariable.PLACE.toString(), sample.getPathVariable());
        assertNotNull(sample.getDescription());
        assertFalse(sample.getQueryString().isEmpty());
        logger.info("{}", sample.getQueryString());
        this.service.generate(sample);
    }

    @DisplayName("Generate search samples")
    @ParameterizedTest(name = "{index} ==> ''{0}''")
    @CsvFileSource(resources = "/search.csv", useHeadersInDisplayName = true)
    void searchExamples(@AggregateWith(SampleAggregator.class) Sample sample) {
        logger.info("{}", sample);
        assertNotNull(sample.getId());
        assertEquals(PathVariable.SEARCH.toString(), sample.getPathVariable());
        assertNotNull(sample.getDescription());
        assertFalse(sample.getQueryString().isEmpty());
        logger.info("{}", sample.getQueryString());
        this.service.generate(sample);
    }

    @DisplayName("Generate streetcode samples")
    @ParameterizedTest(name = "{index} ==> ''{0}''")
    @CsvFileSource(resources = "/streetcode.csv", useHeadersInDisplayName = true)
    void streetcodeExamples(@AggregateWith(SampleAggregator.class) Sample sample) {
        logger.info("{}", sample);
        assertNotNull(sample.getId());
        assertEquals(PathVariable.STREETCODE.toString(), sample.getPathVariable());
        assertNotNull(sample.getDescription());
        assertFalse(sample.getQueryString().isEmpty());
        logger.info("{}", sample.getQueryString());
        this.service.generate(sample);
    }

    @DisplayName("Generate version samples")
    @ParameterizedTest(name = "/version")
    @CsvFileSource(resources = "/version.csv", useHeadersInDisplayName = true)
    void versionExamples(@AggregateWith(SampleAggregator.class) Sample sample) {
        logger.info("{}", sample);
        assertNotNull(sample.getId());
        assertEquals(PathVariable.VERSION.toString(), sample.getPathVariable());
        assertNotNull(sample.getDescription());
        assertTrue(sample.getQueryString().isEmpty());
        logger.info("{}", sample.getQueryString());
        this.service.generate(sample);
    }
}
