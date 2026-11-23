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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import gov.nyc.doitt.gis.geoclient.function.Function;

/**
 * Fluent builders for assembling the argument map used to call a Geosupport
 * {@link Function}. Each builder starts with the function's required arguments.
 *
 * @author mlipper
 */
public class CallBuilder {

    /**
     * Creates a new instance.
     */
    public CallBuilder() {
    }

    private static class NumberedParamNames {
        private Map<Integer, String> mapping;

        NumberedParamNames(String... paramNames) {
            this.mapping = new HashMap<>(paramNames.length);
            for (int i = 0; i < paramNames.length; i++) {
                this.mapping.put(i, paramNames[i]);
            }
        }

        String nth(int ordinal) {
            return this.mapping.get(ordinal - 1);
        }
    }

    private static class Arguments {
        private final Map<String, Object> params = new HashMap<>();

        Object arg(String name, Object value) {
            return this.params.put(name, value);
        }

        void args(Map<String, Object> args) {
            this.params.putAll(args);
        }

        Map<String, Object> arguments() {
            return this.params;
        }
    }

    /**
     * Base class for call builders. The numbered methods (one, two, three) set the
     * arguments for the first, second and third streets or boroughs, respectively.
     */
    protected static abstract class Builder {
        /** The arguments accumulated by this builder. */
        protected final Arguments arguments = new Arguments();
        /** Names of the first, second and third borough code parameters. */
        protected final NumberedParamNames boroughCodeParams = new NumberedParamNames(InputParam.BOROUGH_CODE,
            InputParam.BOROUGH_CODE2, InputParam.BOROUGH_CODE3);
        /** Names of the first, second and third street code parameters. */
        protected final NumberedParamNames streetCodeParams = new NumberedParamNames(InputParam.STREET_CODE,
            InputParam.STREET_CODE2, InputParam.STREET_CODE3);
        /** Names of the first, second and third street name parameters. */
        protected final NumberedParamNames streetNameParams = new NumberedParamNames(InputParam.STREET_NAME,
            InputParam.STREET_NAME2, InputParam.STREET_NAME3);

        Builder(Function function) {
            this.arguments.args(function.getConfiguration().requiredArguments());
        }

        /**
         * Returns the arguments assembled so far, including the function's required
         * arguments.
         *
         * @return the argument map, keyed by parameter name
         */
        public Map<String, Object> arguments() {
            return this.arguments.arguments();
        }

        /**
         * Sets the first borough using its name or alias.
         *
         * @param boroughName borough name or alias
         * @return this builder
         * @throws NullPointerException     if the name is null
         * @throws IllegalArgumentException if the name is not recognized
         */
        public Builder boroughNameOne(String boroughName) {
            addBoroughName(boroughName, 1);
            return this;
        }

        /**
         * Sets the second borough using its name or alias.
         *
         * @param boroughName borough name or alias
         * @return this builder
         * @throws NullPointerException     if the name is null
         * @throws IllegalArgumentException if the name is not recognized
         */
        public Builder boroughNameTwo(String boroughName) {
            addBoroughName(boroughName, 2);
            return this;
        }

        /**
         * Sets the third borough using its name or alias.
         *
         * @param boroughName borough name or alias
         * @return this builder
         * @throws NullPointerException     if the name is null
         * @throws IllegalArgumentException if the name is not recognized
         */
        public Builder boroughNameThree(String boroughName) {
            addBoroughName(boroughName, 3);
            return this;
        }

        private void addBoroughName(String name, int number) {
            if (name == null) {
                throw new NullPointerException("Borough name argument cannot be null");
            }
            Borough borough = null;
            if ((borough = Boroughs.fromName(name)) == null) {
                throw new IllegalArgumentException(String.format("Unrecognized borough named '{}'", name));
            }
            addBorough(borough, number);
        }

        /**
         * Sets the first borough.
         *
         * @param borough the borough
         * @return this builder
         * @throws NullPointerException if the borough is null
         */
        public Builder boroughOne(Borough borough) {
            addBorough(borough, 1);
            return this;
        }

        /**
         * Sets the second borough.
         *
         * @param borough the borough
         * @return this builder
         * @throws NullPointerException if the borough is null
         */
        public Builder boroughTwo(Borough borough) {
            addBorough(borough, 2);
            return this;
        }

        /**
         * Sets the third borough.
         *
         * @param borough the borough
         * @return this builder
         * @throws NullPointerException if the borough is null
         */
        public Builder boroughThree(Borough borough) {
            addBorough(borough, 3);
            return this;
        }

        private void addBorough(Borough borough, int number) {
            if (borough == null) {
                throw new NullPointerException("Borough argument cannot be null");
            }
            addBoroughCode(borough.getCode(), number);
        }

        /**
         * Sets the first borough using its code.
         *
         * @param boroughCode borough code
         * @return this builder
         * @throws NullPointerException if the code is null
         */
        public Builder boroughCodeOne(String boroughCode) {
            addBoroughCode(boroughCode, 1);
            return this;
        }

        /**
         * Sets the second borough using its code.
         *
         * @param boroughCode borough code
         * @return this builder
         * @throws NullPointerException if the code is null
         */
        public Builder boroughCodeTwo(String boroughCode) {
            addBoroughCode(boroughCode, 2);
            return this;
        }

        /**
         * Sets the third borough using its code.
         *
         * @param boroughCode borough code
         * @return this builder
         * @throws NullPointerException if the code is null
         */
        public Builder boroughCodeThree(String boroughCode) {
            addBoroughCode(boroughCode, 3);
            return this;
        }

        private void addBoroughCode(String value, int number) {
            if (value == null) {
                throw new NullPointerException("Borough code argument cannot be null");
            }
            this.arguments.arg(this.boroughCodeParams.nth(number), value);
        }

        /**
         * Sets the first street. The street's name is used if present, otherwise its
         * code.
         *
         * @param street the street
         * @return this builder
         * @throws NullPointerException     if the street is null
         * @throws IllegalArgumentException if the street's name and code are both
         *                                  null
         */
        public Builder streetOne(Street street) {
            addStreet(street, 1);
            return this;
        }

        /**
         * Sets the second street. The street's name is used if present, otherwise its
         * code.
         *
         * @param street the street
         * @return this builder
         * @throws NullPointerException     if the street is null
         * @throws IllegalArgumentException if the street's name and code are both
         *                                  null
         */
        public Builder streetTwo(Street street) {
            addStreet(street, 2);
            return this;
        }

        /**
         * Sets the third street. The street's name is used if present, otherwise its
         * code.
         *
         * @param street the street
         * @return this builder
         * @throws NullPointerException     if the street is null
         * @throws IllegalArgumentException if the street's name and code are both
         *                                  null
         */
        public Builder streetThree(Street street) {
            addStreet(street, 3);
            return this;
        }

        private void addStreet(Street street, int number) {
            if (street == null) {
                throw new NullPointerException("Street argument cannot be null");
            }
            if (street.getName() != null) {
                // Prefer using street name, if available
                addStreetName(street.getName(), number);
            }
            else if (street.getCode() != null) {
                addStreetCode(street.getCode(), number);
            }
            else {
                throw new IllegalArgumentException("Street argument's code and name fields cannot both be null");
            }
        }

        /**
         * Sets the name of the first street.
         *
         * @param streetName street name
         * @return this builder
         */
        public Builder streetNameOne(String streetName) {
            addStreetName(streetName, 1);
            return this;
        }

        /**
         * Sets the name of the second street.
         *
         * @param streetName street name
         * @return this builder
         */
        public Builder streetNameTwo(String streetName) {
            addStreetName(streetName, 2);
            return this;
        }

        /**
         * Sets the name of the third street.
         *
         * @param streetName street name
         * @return this builder
         */
        public Builder streetNameThree(String streetName) {
            addStreetName(streetName, 3);
            return this;
        }

        private void addStreetName(String streetName, int number) {
            this.arguments.arg(this.streetNameParams.nth(number), streetName);
        }

        /**
         * Sets the code of the first street.
         *
         * @param streetCode street code
         * @return this builder
         */
        public Builder streetCodeOne(String streetCode) {
            addStreetCode(streetCode, 1);
            return this;
        }

        /**
         * Sets the code of the second street.
         *
         * @param streetCode street code
         * @return this builder
         */
        public Builder streetCodeTwo(String streetCode) {
            addStreetCode(streetCode, 2);
            return this;
        }

        /**
         * Sets the code of the third street.
         *
         * @param streetCode street code
         * @return this builder
         */
        public Builder streetCodeThree(String streetCode) {
            addStreetCode(streetCode, 3);
            return this;
        }

        private void addStreetCode(String streetCode, int number) {
            this.arguments.arg(this.streetCodeParams.nth(number), streetCode);
        }
    }

    /**
     * Builder for address calls.
     */
    public static class Address extends Builder {
        /**
         * Creates a builder initialized with the given function's required arguments.
         *
         * @param function the Geosupport function to be called
         */
        public Address(Function function) {
            super(function);
        }

        Address street(Street street) {
            return (Address) this.streetOne(street);
        }

        Address streetCode(String code) {
            return (Address) this.streetCodeOne(code);
        }

        Address streetName(String name) {
            return (Address) this.streetNameOne(name);
        }

        Address borough(Borough borough) {
            return (Address) this.boroughOne(borough);
        }

        Address boroughCode(String code) {
            return (Address) this.boroughCodeOne(code);
        }

        Address boroughName(String name) {
            return (Address) this.boroughNameOne(name);
        }

        Address houseNumber(String value) {
            this.arguments.arg(InputParam.HOUSE_NUMBER, value);
            return this;
        }

        Address zipCode(String value) {
            this.arguments.arg(InputParam.ZIP_CODE, value);
            return this;
        }
    }

    /**
     * Creates a builder of the given type by calling its constructor that takes a
     * {@link Function}.
     *
     * @param <T>      the builder type
     * @param function the Geosupport function to be called
     * @param callType the builder class to instantiate
     * @return a new builder
     * @throws NoSuchMethodException     if the class has no public constructor
     *                                   taking a {@link Function}
     * @throws InvocationTargetException if the constructor throws an exception
     * @throws IllegalAccessException    if the constructor is not accessible
     * @throws IllegalArgumentException  if the constructor arguments are invalid
     * @throws InstantiationException    if the class is abstract
     */
    public static <T extends Builder> T newInstance(Function function, Class<T> callType) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException, IllegalArgumentException, InstantiationException {
        Constructor<T> ctor = callType.getConstructor(Function.class);
        return ctor.newInstance(function);
    }

}
