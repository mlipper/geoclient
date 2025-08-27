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
package gov.nyc.doitt.gis.geoclient.json;

import static gov.nyc.doitt.gis.geoclient.json.JsonConfiguration.FieldBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.function.Field;

public class FieldBuilderTest {

    private static final String TEST_FIELDS = """
            {\"fields\": [{ \"id\": \"functionCode\", \"start\": \"1\", \"length\": \"2\", \"input\": \"true\" },
            { \"id\": \"fubar\", \"start\": \"3\", \"length\": \"27\", \"type\": \"COMP\" },
            { \"id\": \"foo\", \"start\": \"3\", \"length\": \"16\", \"input\": \"true\", \"alias\": \"fooAka\", \"outputAlias\": \"fooOutputAka\" },
            { \"id\": \"bar\", \"start\": \"19\", \"length\": \"11\", \"input\": \"true\", \"alias\": \"barAka\" },
            { \"id\": \"censusTract\", \"start\": \"30\", \"length\": \"6\", \"whitespace\": \"true\" }]}
            """;

    @Test
    void test_FieldBuilder() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(TEST_FIELDS);
        ArrayNode fieldArrayNode = (ArrayNode) rootNode.get("fields");
        List<Field> fields = new ArrayList<>();
        for (JsonNode fieldNode : fieldArrayNode) {
            ObjectNode f = (ObjectNode) fieldNode;
            String id = f.get("id").asText();
            int start = f.get("start").asInt();
            int length = f.get("length").asInt();
            FieldBuilder builder = new FieldBuilder(id, start, length);
            Field field = builder.composite(f).input(f).alias(f).whitespaceSignificant(f).outputAlias(f).build();
            System.out.println(field);
            fields.add(field);
        }
        assertEquals(5, fields.size());
        assertEquals("functionCode", fields.get(0).getId());
        Field fubar = fields.get(1);
        assertEquals("fubar", fubar.getId());
        assertTrue(fubar.isComposite());
        assertFalse(fubar.isAliased());
        assertFalse(fubar.isInput());
        Field foo = fields.get(2);
        assertEquals("foo", foo.getId());
        assertFalse(foo.isComposite());
        assertTrue(foo.isInput());
        assertTrue(foo.isOutputAliased());
        assertEquals(foo.getOutputAlias(), "fooOutputAka");
        assertTrue(foo.isAliased());
        assertEquals("fooAka", foo.getAlias());
        Field censusTract = fields.get(4);
        assertEquals("censusTract", censusTract.getId());
        assertTrue(censusTract.isWhitespaceSignificant());

    }
}
