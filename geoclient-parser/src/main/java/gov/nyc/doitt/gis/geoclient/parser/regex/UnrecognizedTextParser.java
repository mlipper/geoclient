/*
 * Copyright 2013-2024 the original author or authors.
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.nyc.doitt.gis.geoclient.parser.ParseContext;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.ChunkType;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;
import gov.nyc.doitt.gis.geoclient.parser.util.Assert;

/**
 * Parser that always matches zero or more characters in the current
 * {@link Chunk}
 * and marks the {@link TokenType} and {@link ChunkType} as a (possible) place
 * name.
 * <p>
 * This parser is used as a fallback to handle any input that does not match
 * more specific patterns. It uses a regular expression that matches any string,
 * including
 * the empty string, and assigns the {@link TokenType#PLACE} and
 * {@link ChunkType#PLACE}
 * to the resulting match.
 * </p>
 * <p>
 * Previously, this parser assigned the <code>TokenType#UNRECOGNIZED</code> and
 * <code>ChunkType#UNRECOGNIZED</code> but this was changed to reflect the fact that
 * is used for a place search after all other parsers have been tried.
 *
 * @author mlipper
 * @since 2.0
 *
 * @see TokenType#PLACE
 * @see ChunkType#PLACE
 */
public class UnrecognizedTextParser extends AbstractRegexParser {
    private final Pattern ANYTHING = Pattern.compile("(.*)");

    /**
     * Constructs a new {@link UnrecognizedTextParser} instance.
     * This parser uses a regex pattern that matches any input.
     *
     * @param parseContext Current parsing context
     * @see AbstractRegexParser#AbstractRegexParser(ParseContext)
     */
    @Override
    public void parse(ParseContext parseContext) {
        Chunk currentChunk = parseContext.getCurrent();
        Matcher matcher = ANYTHING.matcher(currentChunk.getText());
        Assert.isTrue(matcher.matches(), String.format("Pattern %s should match any input but it doesn't match '%s'",
            ANYTHING.pattern(), currentChunk.getText()));
        MatchBuilder builder = new MatchBuilder().add(matcher).add(MatchType.COMPLETE).add(parseContext).add(ANYTHING,
            1, TokenType.PLACE);
        handleMatch(builder.build(), ChunkType.PLACE);
    }

}
