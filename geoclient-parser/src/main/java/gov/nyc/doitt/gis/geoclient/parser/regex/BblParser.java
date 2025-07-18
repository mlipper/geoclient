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
package gov.nyc.doitt.gis.geoclient.parser.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.nyc.doitt.gis.geoclient.parser.ParseContext;
import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.parser.token.ChunkType;
import gov.nyc.doitt.gis.geoclient.parser.token.TokenType;

public class BblParser extends AbstractRegexParser {
    private static final Pattern BBL_TEN_DIGIT = Pattern.compile("^(?:\\s*)([1-5])(\\d{5})(\\d{4})\\s*$");

    @Override
    public void parse(ParseContext parseContext) {
        Chunk currentChunk = parseContext.getCurrent();
        Matcher matcher = BblParser.BBL_TEN_DIGIT.matcher(currentChunk.getText());

        if (!matcher.matches()) {
            patternNotMatched(parseContext, BBL_TEN_DIGIT);
            return;
        }
        MatchBuilder builder = new MatchBuilder().add(matcher).add(MatchType.COMPLETE).add(parseContext).add(
            BBL_TEN_DIGIT, 1, TokenType.BOROUGH_CODE).add(BBL_TEN_DIGIT, 2, TokenType.BLOCK).add(BBL_TEN_DIGIT, 3,
                TokenType.LOT);
        handleMatch(builder.build(), ChunkType.BBL);
    }

}
