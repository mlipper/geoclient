/*
 * Copyright 2013-2026 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.config.jackson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class GeoclientXmlReader {

    private GeoclientXml geoclientXml;

    public GeoclientXmlReader(String configFile) throws IOException {
        this.geoclientXml = readGeoclientXml(new XmlMapper(), configFile);
    }

    public GeoclientXmlReader(InputStream inputStream) throws IOException {
        this.geoclientXml = readGeoclientXml(new XmlMapper(), inputStream);
    }

    public GeoclientXml getGeoclientXml() {
        return geoclientXml;
    }

    public List<FunctionXml> getFunctions() {
        return geoclientXml.getFunctions().getFunctions();
    }

    public List<WorkAreaXml> getWorkAreas() {
        return geoclientXml.getWorkAreas().getWorkAreas();
    }

    private GeoclientXml readGeoclientXml(XmlMapper mapper, InputStream inputStream) throws IOException {
        try {
            return mapper.readValue(inputStream, GeoclientXml.class);
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private GeoclientXml readGeoclientXml(XmlMapper mapper, String configFile) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile);
        return readGeoclientXml(mapper, inputStream);
    }

}
