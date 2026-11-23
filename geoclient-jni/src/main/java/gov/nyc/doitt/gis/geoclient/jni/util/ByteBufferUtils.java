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
package gov.nyc.doitt.gis.geoclient.jni.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

/**
 * Low level utility for checking the Geosupport return code directly from a
 * {@link java.nio.ByteBuffer}.
 *
 * Used primarily in tests.
 *
 * @author mlipper
 */
public class ByteBufferUtils {

    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final CharsetEncoder ENCODER = CHARSET.newEncoder();
    private static final CharsetDecoder DECODER = CHARSET.newDecoder();
    private static final String GEOSUPPORT_RC_SUCCESS = "00";
    private static final String GEOSUPPORT_RC_SUCCESS_WITH_WARN = "01";
    private static final int GEOSUPPORT_RC_LENGTH = 2;
    private static final int GEOSUPPORT_RC_START = 716;

    /**
     * Decodes the remaining bytes of the given buffer as UTF-8. Consumes the
     * buffer, i.e., advances its position.
     *
     * @param buffer the buffer to decode
     * @return the decoded string
     * @throws CharacterCodingException if the bytes are not valid UTF-8
     */
    public static String decode(ByteBuffer buffer) throws CharacterCodingException {
        return DECODER.decode(buffer).toString();
    }

    /**
     * Converts the given bytes to a string using the platform's default charset.
     *
     * @param buffer the bytes to convert
     * @return the resulting string
     */
    public static String decode(byte[] buffer) {
        return new String(buffer);
    }

    /**
     * Encodes the given characters as UTF-8 into the given buffer.
     *
     * @param charBuffer the characters to encode
     * @param buffer     the destination buffer
     * @throws CharacterCodingException if encoding fails, e.g., due to
     *                                  unmappable characters or insufficient
     *                                  space in the destination buffer
     */
    public void encode(CharBuffer charBuffer, ByteBuffer buffer) throws CharacterCodingException {
        CoderResult result = ENCODER.encode(charBuffer, buffer, true);
        if (result.isError()) {
            result.throwException();
        }
    }

    /**
     * Extracts the two character Geosupport return code from the contents of Work
     * Area 1.
     *
     * @param workAreaOneResult the string contents of Work Area 1 after calling
     *                          Geosupport
     * @return the Geosupport return code
     * @throws StringIndexOutOfBoundsException if the argument is too short to
     *                                         contain a return code
     */
    public static String getReturnCode(String workAreaOneResult) {
        return workAreaOneResult.substring(GEOSUPPORT_RC_START, GEOSUPPORT_RC_START + GEOSUPPORT_RC_LENGTH);
    }

    /**
     * Determines whether the given Geosupport return code indicates success.
     *
     * @param returnCode a Geosupport return code
     * @return <code>true</code> if the code is <code>00</code> (success) or
     *         <code>01</code> (success with warning); <code>false</code> for any
     *         other value, including <code>null</code> or empty
     */
    public static boolean isSuccess(String returnCode) {
        if (isNotNullOrEmpty(returnCode)) {
            return GEOSUPPORT_RC_SUCCESS.equals(returnCode) || GEOSUPPORT_RC_SUCCESS_WITH_WARN.equals(returnCode);
        }
        return false;
    }

    /**
     * Decodes the remaining bytes of the given buffer as UTF-8 without changing
     * the buffer's position.
     *
     * @param buffer the buffer to read
     * @return the decoded string
     * @throws CharacterCodingException if the bytes are not valid UTF-8
     */
    public static String readString(ByteBuffer buffer) throws CharacterCodingException {
        int position = buffer.position();
        CharBuffer charBuffer = DECODER.decode(buffer);
        buffer.position(position);
        return charBuffer.toString();
    }

    private static boolean isNotNullOrEmpty(String s) {
        return s != null && s.length() > 0;
    }

}
