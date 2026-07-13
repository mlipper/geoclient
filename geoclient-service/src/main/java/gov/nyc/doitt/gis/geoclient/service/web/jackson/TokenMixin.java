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
package gov.nyc.doitt.gis.geoclient.service.web.jackson;

import org.springframework.boot.jackson.JacksonMixin;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Jackson mix-in that supplies wire-format metadata for {@link Token}.
 */
@JacksonMixin(Token.class)
@JsonRootName("token")
@JsonPropertyOrder({ "value", "type", "start", "end" })
abstract class TokenMixin {

    @JacksonXmlProperty(localName = "start")
    private int start;

    @JacksonXmlProperty(localName = "end")
    private int end;
}
