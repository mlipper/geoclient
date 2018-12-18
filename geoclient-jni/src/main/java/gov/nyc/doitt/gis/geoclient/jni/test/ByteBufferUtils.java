package gov.nyc.doitt.gis.geoclient.jni.test;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class ByteBufferUtils {

    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final CharsetDecoder DECODER = CHARSET.newDecoder();
    private static final String GEOSUPPORT_RC_SUCCESS = "00";
    private static final String GEOSUPPORT_RC_SUCCESS_WITH_WARN = "01";
    private static final int GEOSUPPORT_RC_LENGTH = 2;
    private static final int GEOSUPPORT_RC_START = 716;

    public static String decode(ByteBuffer buffer) throws CharacterCodingException {
        return DECODER.decode(buffer).toString();
    }
    
    public static String decode(byte[] buffer) {
        return new String(buffer);
    }
    
    public static String getReturnCode(String workAreaOneResult) {
        return workAreaOneResult.substring(GEOSUPPORT_RC_START, GEOSUPPORT_RC_START + GEOSUPPORT_RC_LENGTH);
    }

    public static boolean isSuccess(String returnCode) {
        if (isNotNullOrEmpty(returnCode)) {
            return GEOSUPPORT_RC_SUCCESS.equals(returnCode) || GEOSUPPORT_RC_SUCCESS_WITH_WARN.equals(returnCode);
        }
        return false;
    }

    private static boolean isNotNullOrEmpty(String s) {
        return s != null && s.length() > 0;
    }

}