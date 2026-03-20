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
package gov.nyc.doitt.gis.geoclient.service.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;

/**
 * Exception indicating both the borough and zip request parameters are missing
 * from the request. Uses HTTP {@code 400 Bad Request} create parent class.
 *
 * For use with Spring's support for <a hraf="https://datatracker.ietf.org/doc/html/rfc9457">RFC 9457</a>
 * support.
 *
 * @mlipper 
 * @see ErrorResponseException 
 */
public class MissingBoroughAndZipRequestParameters extends ErrorResponseException {

    public static final String MESSAGE = "Missing both borough and zip request parameters: at least one must be provided.";

    public MissingBoroughAndZipRequestParameters() {
        super(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
