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
package gov.nyc.doitt.gis.geoclient.api.invoker;

import java.util.Map;

import gov.nyc.doitt.gis.geoclient.api.version.Version;
import gov.nyc.doitt.gis.geoclient.function.Function;

/**
 * Framework-neutral view of the collaborators that
 * {@link GeosupportServiceImpl} requires in order to invoke Geosupport
 * functions and assemble responses.
 * <p>
 * By depending on this interface rather than a concrete configuration class,
 * {@link GeosupportServiceImpl} can be used from any host application &mdash;
 * with or without Spring &mdash; provided the host supplies an
 * implementation. The convenience aliases ({@link #functionAP()},
 * {@link #function1B()}, etc.) are defined as {@code default} methods so
 * that implementers need only supply the three primary methods.
 *
 * @author mlipper
 */
public interface GeosupportServiceContext {

    /**
     * Look up a Geosupport function by its string id (e.g. {@code "1B"}).
     *
     * @param id the function id
     * @return the {@link Function} registered under {@code id}
     */
    Function geosupportFunction(String id);

    /**
     * The converter used to enrich responses with lat/long values as
     * doubles.
     *
     * @return the lat/long field-set converter
     */
    FieldSetConverter latLongFieldSetConverter();

    /**
     * Build a service-level {@link Version} from the raw parameter map
     * produced by a Geosupport HR-function call.
     *
     * @param functionHrData the raw HR-function response
     * @return a populated {@link Version}
     */
    Version version(Map<String, Object> functionHrData);

    // ------------------------------------------------------------------
    // Convenience aliases. Implementers should not normally override.
    // ------------------------------------------------------------------

    /**
     * Convenience alias for {@code geosupportFunction(Function.FAP)}.
     *
     * @return the Geosupport function {@code AP}
     */
    default Function functionAP() {
        return geosupportFunction(Function.FAP);
    }

    /**
     * Convenience alias for {@code geosupportFunction(Function.F1B)}.
     *
     * @return the Geosupport function {@code 1B}
     */
    default Function function1B() {
        return geosupportFunction(Function.F1B);
    }

    /**
     * Convenience alias for {@code geosupportFunction(Function.F2W)}.
     *
     * @return the Geosupport function {@code 2W}
     */
    default Function function2W() {
        return geosupportFunction(Function.F2W);
    }

    /**
     * Convenience alias for {@code geosupportFunction(Function.F3)}.
     *
     * @return the Geosupport function {@code 3}
     */
    default Function function3() {
        return geosupportFunction(Function.F3);
    }

    /**
     * Convenience alias for {@code geosupportFunction(Function.FBL)}.
     *
     * @return the Geosupport function {@code BL}
     */
    default Function functionBL() {
        return geosupportFunction(Function.FBL);
    }

    /**
     * Convenience alias for {@code geosupportFunction(Function.FBN)}.
     *
     * @return the Geosupport function {@code BN}
     */
    default Function functionBN() {
        return geosupportFunction(Function.FBN);
    }

    /**
     * Convenience alias for {@code geosupportFunction(Function.FHR)}.
     *
     * @return the Geosupport function {@code HR}
     */
    default Function functionHR() {
        return geosupportFunction(Function.FHR);
    }

    /**
     * Convenience alias for {@code geosupportFunction(Function.FN)}.
     *
     * @return the Geosupport function {@code N}
     */
    default Function functionN() {
        return geosupportFunction(Function.FN);
    }

    /**
     * Convenience alias for {@code geosupportFunction(Function.FD)}.
     *
     * @return the Geosupport function {@code D}
     */
    default Function functionD() {
        return geosupportFunction(Function.FD);
    }

    /**
     * Convenience alias for {@code geosupportFunction(Function.FDG)}.
     *
     * @return the Geosupport function {@code DG}
     */
    default Function functionDG() {
        return geosupportFunction(Function.FDG);
    }

    /**
     * Convenience alias for {@code geosupportFunction(Function.FDN)}.
     *
     * @return the Geosupport function {@code DN}
     */
    default Function functionDN() {
        return geosupportFunction(Function.FDN);
    }
}
