# Jackson 2 to Jackson 3 Migration

## Current Use of Jackson 2

```txt
# buildSrc

- geoclientbuild.client.shutdown.HttpShutdownSettings:
  com.fasterxml.jackson.core.exc.StreamReadException
  com.fasterxml.jackson.databind.DatabindException
  com.fasterxml.jackson.databind.ObjectMapper

- geoclientbuild.docs.GenerateSamples:
  com.fasterxml.jackson.core.JsonProcessingException
  com.fasterxml.jackson.core.type.TypeReference
  com.fasterxml.jackson.databind.JsonNode
  com.fasterxml.jackson.databind.ObjectMapper
  com.fasterxml.jackson.databind.SerializationFeature

# geoclient-parser

- gov.nyc.doitt.gis.geoclient.parser.token.Chunk:
  com.fasterxml.jackson.annotation.JsonPropertyOrder
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

- gov.nyc.doitt.gis.geoclient.parser.token.Token:
  com.fasterxml.jackson.annotation.JsonPropertyOrder
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

  src/test
- gov.nyc.doitt.gis.geoclient.parser.test.SpecBuilder:
  com.fasterxml.jackson.dataformat.xml.XmlMapper

  src/test
- gov.nyc.doitt.gis.geoclient.parser.test.UnparsedSpec:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

# geoclient-service
- gov.nyc.doitt.gis.geoclient.service.configuration.GeosupportResponseXmlMapAdapter:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
  
- gov.nyc.doitt.gis.geoclient.service.configuration.JacksonXmlMarshaller:
  com.fasterxml.jackson.dataformat.xml.XmlMapper
  
- gov.nyc.doitt.gis.geoclient.service.domain.BadRequest:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
  
- gov.nyc.doitt.gis.geoclient.service.domain.GeosupportVersion:
  com.fasterxml.jackson.annotation.JsonPropertyOrder
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  
- gov.nyc.doitt.gis.geoclient.service.domain.ThinFileInfo:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  
- gov.nyc.doitt.gis.geoclient.service.domain.Version:
  com.fasterxml.jackson.annotation.JsonPropertyOrder
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
  
- gov.nyc.doitt.gis.geoclient.service.search.web.response.SearchResponse:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
  
- gov.nyc.doitt.gis.geoclient.service.search.web.response.SearchSummary:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

# geoclient-xml
- gov.nyc.doitt.gis.geoclient.config.jackson.ConfigurationXml:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  
- gov.nyc.doitt.gis.geoclient.config.jackson.FieldXml:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  
- gov.nyc.doitt.gis.geoclient.config.jackson.FiltersXml:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  
- gov.nyc.doitt.gis.geoclient.config.jackson.FilterXml:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  
- gov.nyc.doitt.gis.geoclient.config.jackson.FunctionsXml:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  
- gov.nyc.doitt.gis.geoclient.config.jackson.FunctionXml:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  
- gov.nyc.doitt.gis.geoclient.config.jackson.GeoclientXml:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
  
- gov.nyc.doitt.gis.geoclient.config.jackson.GeoclientXmlReader:
  com.fasterxml.jackson.dataformat.xml.XmlMapper
  
- gov.nyc.doitt.gis.geoclient.config.jackson.OutputFiltersXml:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  
- gov.nyc.doitt.gis.geoclient.config.jackson.RequiredArgumentXml:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  
- gov.nyc.doitt.gis.geoclient.config.jackson.WorkAreaReferenceXml:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  
- gov.nyc.doitt.gis.geoclient.config.jackson.WorkAreasXml:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  
- gov.nyc.doitt.gis.geoclient.config.jackson.WorkAreaXml:
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
  com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
  
- gov.nyc.doitt.gis.geoclient.config.jackson.GeoclientXmlReaderTest:
  com.fasterxml.jackson.databind.DeserializationFeature
  com.fasterxml.jackson.dataformat.xml.XmlMapper
```

## Current Jackson 2 Imports

```txt
com.fasterxml.jackson.annotation.JsonPropertyOrder
com.fasterxml.jackson.core.JsonProcessingException
com.fasterxml.jackson.core.exc.StreamReadException
com.fasterxml.jackson.core.type.TypeReference
com.fasterxml.jackson.databind.DatabindException
com.fasterxml.jackson.databind.DeserializationFeature
com.fasterxml.jackson.databind.JsonNode
com.fasterxml.jackson.databind.ObjectMapper
com.fasterxml.jackson.databind.SerializationFeature
com.fasterxml.jackson.dataformat.xml.XmlMapper
com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText
```
