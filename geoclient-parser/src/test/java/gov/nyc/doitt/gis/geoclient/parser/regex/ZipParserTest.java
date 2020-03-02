/*
 * Copyright 2013-2019 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.parser.regex;

import static org.assertj.core.api.Assertions.assertThat;
import gov.nyc.doitt.gis.geoclient.parser.AbstractSpecTest;
import gov.nyc.doitt.gis.geoclient.parser.Input;
import gov.nyc.doitt.gis.geoclient.parser.ParseContext;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.ChunkType;
import gov.nyc.doitt.gis.geoclient.parser.token.Token;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipParserTest extends AbstractSpecTest
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ZipParserTest.class);
	private ZipParser parser;

	@BeforeEach
	public void setUp() throws Exception
	{
		parser = new ZipParser();
	}

	@Test
	public void testTokens()
	{
		testParser(parser, LOGGER);
	}

	@Test
	public void testParseSuccessChangesCurrentChunk()
	{
		String originalText = "280 Riverside Dr NY NY 10025 ";
		ParseContext context = new ParseContext(new Input("zip-chunk-state-change1", originalText));
		Chunk initialChunk = context.getCurrent();
		assertThat(initialChunk.getType()).isEqualTo(ChunkType.ORIGINAL_INPUT);
		parser.parse(context);
		assertThat(context.isParsed()).isFalse().as("ParseContext.isParsed should be");
		assertThat(initialChunk.getType()).isEqualTo(ChunkType.COUNTY).as("Chunk.Type of initial Chunk:");
		assertThat(initialChunk.contains(new Token(TokenType.ZIP, "10025", 23, 28))).isTrue().as("Initial Chunk contains:");
		assertThat(initialChunk.tokenCount()).isEqualTo(1).as("Initial Chunk token count:");
		Chunk actualChunk = context.getCurrent();
		assertThat(actualChunk.getType()).isEqualTo(ChunkType.SUBSTRING);
		assertThat(actualChunk.getText()).isEqualTo("280 Riverside Dr NY NY");
	}

}
