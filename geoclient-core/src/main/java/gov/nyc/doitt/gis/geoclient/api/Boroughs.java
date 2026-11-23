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
package gov.nyc.doitt.gis.geoclient.api;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Utility class with constants for the five boroughs of New York City and
 * methods for looking them up by code or name.
 *
 * @author mlipper
 */
public class Boroughs {

    /** Manhattan (borough code 1). */
    public static final Borough MANHATTAN = new Manhattan();
    /** The Bronx (borough code 2). */
    public static final Borough BRONX = new Bronx();
    /** Brooklyn (borough code 3). */
    public static final Borough BROOKLYN = new Brooklyn();
    /** Queens (borough code 4). */
    public static final Borough QUEENS = new Queens();
    /** Staten Island (borough code 5). */
    public static final Borough STATEN_ISLAND = new StatenIsland();

    /** Value returned by {@link #parseInt(String)} when no borough code can be determined. */
    public static final int UNPARSABLE_BOROUGH_CODE_SENTINEL_VALUE = 0;

    /** All five boroughs ordered by borough code. */
    public static final List<Borough> THE_FIVE_BOROUGHS = Arrays.asList(MANHATTAN, BRONX, BROOKLYN, QUEENS,
        STATEN_ISLAND);

    /**
     * Finds the borough with the given code, ignoring case.
     *
     * @param code borough code
     * @return the matching borough or <code>null</code> if there is none
     */
    public static final Borough fromCode(String code) {
        // Note: this equality test does not consider whitespace.
        return findFirst((Borough b) -> b.getCode().equalsIgnoreCase(code));
    }

    /**
     * Finds the borough with the given name or alias, ignoring case.
     *
     * @param name borough name or alias, e.g., <code>BK</code>
     * @return the matching borough or <code>null</code> if there is none
     */
    public static final Borough fromName(String name) {
        return findFirst((Borough b) -> b.isAlsoKnownAs(name));
    }

    // Originally from gov.nyc.doitt.gis.geoclient.service.domain.Borough in the geoclient-service project.
    /**
     * Converts a borough code or name to an integer borough code.
     *
     * @param boroughCodeOrName a borough code, name or alias
     * @return the argument as an integer if it is parsable, regardless of whether
     *         it is a valid borough code; otherwise the code of the borough with
     *         that name, or {@link #UNPARSABLE_BOROUGH_CODE_SENTINEL_VALUE} if
     *         there is none
     */
    public static int parseInt(String boroughCodeOrName) {
        // If argument is a parse-able integer, return it regardless of validity as an actual borough code
        if (NumberUtils.isParsable(boroughCodeOrName)) {
            return Integer.valueOf(boroughCodeOrName);
        }

        Borough borough = fromName(boroughCodeOrName);

        if (borough != null) {
            return Integer.valueOf(borough.getCode());
        }

        return UNPARSABLE_BOROUGH_CODE_SENTINEL_VALUE;
    }

    /**
     * Finds the first borough matching the given predicate.
     *
     * @param predicate the test to apply to each borough
     * @return the first matching borough or <code>null</code> if there is none
     */
    public static final Borough findFirst(Predicate<? super Borough> predicate) {
        Optional<Borough> result = THE_FIVE_BOROUGHS.stream().filter(predicate).findFirst();
        return result.orElse(null);
    }

    /** Manhattan (borough code 1, alias <code>MN</code>). */
    public static class Manhattan extends Borough {
        /** Creates a Manhattan borough. */
        public Manhattan() {
            super("1", "MANHATTAN", new String[] { "MN" });
        }
    }

    /** The Bronx (borough code 2, alias <code>BX</code>). */
    public static class Bronx extends Borough {
        /** Creates a Bronx borough. */
        public Bronx() {
            super("2", "BRONX", new String[] { "BX" });
        }
    }

    /** Brooklyn (borough code 3, alias <code>BK</code>). */
    public static class Brooklyn extends Borough {
        /** Creates a Brooklyn borough. */
        public Brooklyn() {
            super("3", "BROOKLYN", new String[] { "BK" });
        }
    }

    /** Queens (borough code 4, alias <code>QN</code>). */
    public static class Queens extends Borough {
        /** Creates a Queens borough. */
        public Queens() {
            super("4", "QUEENS", new String[] { "QN" });
        }
    }

    /** Staten Island (borough code 5, aliases <code>SI</code> and <code>STATEN IS</code>). */
    public static class StatenIsland extends Borough {
        /** Creates a Staten Island borough. */
        public StatenIsland() {
            super("5", "STATEN ISLAND", new String[] { "SI", "STATEN IS" });
        }
    }

    private Boroughs() {
    }
}
