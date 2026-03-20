package gov.nyc.doitt.gis.geoclient.service.web;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import gov.nyc.doitt.gis.geoclient.api.InvalidStreetCodeException;

/**
 * Provides a global exception handler that uses Spring's
 * <a href="https://datatracker.ietf.org/doc/html/rfc9457">RFC 9457</a>
 * support.
 *
 * @mlipper 
 * @see ResponseEntityExceptionHandler
 */
@RestControllerAdvice
public class CustomRestControllerAdvice extends ResponseEntityExceptionHandler {

    static final String QUERY_PARAM_FORMAT = "f";
    static final String FORMAT_JSON = "json";
    static final String FORMAT_XML = "xml";

    @ExceptionHandler(InvalidStreetCodeException.class)
    public ResponseEntity<Object> handleInvalidStreetCodeRequestParameter(InvalidStreetCodeException exception,
            WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        return handleExceptionInternal(exception, problemDetail, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MissingBoroughAndZipRequestParameters.class)
    public ResponseEntity<Object> handleMissingBoroughAndZipRequestParameters(
            MissingBoroughAndZipRequestParameters exception, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        return handleExceptionInternal(exception, problemDetail, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> createResponseEntity(@Nullable Object body, HttpHeaders headers,
            HttpStatusCode statusCode, WebRequest request) {
        HttpHeaders writableHeaders = new HttpHeaders();
        writableHeaders.putAll(headers);
        applyRequestedFormatHeader(writableHeaders, request);
        return super.createResponseEntity(body, writableHeaders, statusCode, request);
    }

    private void applyRequestedFormatHeader(HttpHeaders headers, WebRequest request) {
        if (!(request instanceof ServletWebRequest servletWebRequest)) {
            return;
        }
        String format = servletWebRequest.getRequest().getParameter(QUERY_PARAM_FORMAT);
        if (FORMAT_XML.equalsIgnoreCase(format)) {
            headers.setContentType(MediaType.APPLICATION_XML);
            return;
        }
        if (FORMAT_JSON.equalsIgnoreCase(format)) {
            headers.setContentType(MediaType.APPLICATION_JSON);
            return;
        }
        MediaType acceptedMediaType = acceptedMediaType(servletWebRequest);
        if (acceptedMediaType != null) {
            headers.setContentType(acceptedMediaType);
            return;
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    private @Nullable MediaType acceptedMediaType(ServletWebRequest request) {
        String acceptHeader = request.getRequest().getHeader(HttpHeaders.ACCEPT);
        if (acceptHeader == null || acceptHeader.isBlank()) {
            return null;
        }
        try {
            List<MediaType> acceptedTypes = MediaType.parseMediaTypes(acceptHeader);
            for (MediaType acceptedType : acceptedTypes) {
                if (acceptedType.isCompatibleWith(MediaType.APPLICATION_XML)
                        || acceptedType.isCompatibleWith(MediaType.APPLICATION_PROBLEM_XML)) {
                    return MediaType.APPLICATION_XML;
                }
                if (acceptedType.isCompatibleWith(MediaType.APPLICATION_JSON)
                        || acceptedType.isCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON)) {
                    return MediaType.APPLICATION_JSON;
                }
            }
        }
        catch (IllegalArgumentException ignored) {
            return null;
        }
        return null;
    }

}
