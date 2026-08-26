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
package gov.nyc.doitt.gis.geoclient.function;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.util.Assert;

/**
 * Core domain class representing a {@link WorkArea} field within a fixed-length record.
 * This class provides methods for reading from and writing to the underlying
 * {@link ByteBuffer} as well as metadata about the field necessary for a
 * {@link WorkArea} to work with the fixed-length record as a whole.
 *
 * @author mlipper
 * @since 1.0
 */
public class Field implements Comparable<Field> {
    /** Default comparator for sorting fields by start position and length. */
    public final static Comparator<Field> DEFAULT_SORT = new Field.StartLengthComparator();
    /** Comparator for sorting fields by name. */
    public final static Comparator<Field> NAME_SORT = new Field.NameStartLengthComparator();
    private final static Logger log = LoggerFactory.getLogger(Field.class);
    private final String id;
    private final Integer start;
    private final Integer length;
    private final boolean composite;
    private final boolean input;
    private final String alias;
    private final boolean whitespaceSignificant;
    private final String outputAlias;

    /**
     * Constructs a new Field with the specified id, start position, and length.
     *
     * @param id the field identifier.
     * @param start the start position of the field within the record.
     * @param length the length of the field.
     */
    public Field(String id, Integer start, Integer length) {
        this(id, start, length, false);
    }

    /**
     * Constructs a new Field with the specified id, start position, length, and composite flag.
     *
     * @param id the field identifier.
     * @param start the start position of the field within the record.
     * @param length the length of the field.
     * @param composite whether the field is composite.
     */
    public Field(String id, Integer start, Integer length, boolean composite) {
        this(id, start, length, composite, false, null);
    }

    /**
     * Constructs a new Field with the specified id, start position, length, composite flag, input flag, and alias.
     *
     * @param id the field identifier.
     * @param start the start position of the field within the record.
     * @param length the length of the field.
     * @param composite whether the field is composite.
     * @param input whether the field is an input field.
     * @param alias the alias for the field.
     */
    public Field(String id, Integer start, Integer length, boolean composite, boolean input, String alias) {
        this(id, start, length, composite, input, alias, false);
    }

    /**
     * Constructs a new Field with the specified id, start position, length, composite flag, input flag, alias, and whitespace significance.
     *
     * @param id the field identifier.
     * @param start the start position of the field within the record.
     * @param length the length of the field.
     * @param composite whether the field is composite.
     * @param input whether the field is an input field.
     * @param alias the alias for the field.
     * @param whitespace whether whitespace is significant for the field.
     */
    public Field(String id, Integer start, Integer length, boolean composite, boolean input, String alias,
            boolean whitespace) {
        this(id, start, length, composite, input, alias, whitespace, null);
    }

    /**
     * Constructs a new Field with the specified id, start position, length, composite flag, input flag, alias, whitespace significance, and output alias.
     *
     * @param id the field identifier.
     * @param start the start position of the field within the record.
     * @param length the length of the field.
     * @param composite whether the field is composite.
     * @param input whether the field is an input field.
     * @param alias the alias for the field.
     * @param whitespace whether whitespace is significant for the field.
     * @param outputAlias the output alias for the field.
     */
    public Field(String id, Integer start, Integer length, boolean composite, boolean input, String alias,
            boolean whitespace, String outputAlias) {
        Assert.notNull(id, "Parameter 'id' cannot be null");
        this.id = id;
        Assert.notNull(start, "Parameter 'start' cannot be null");
        this.start = start;
        Assert.notNull(length, "Parameter 'length' cannot be null");
        this.length = length;
        this.composite = composite;
        this.input = input;
        this.alias = alias;
        this.whitespaceSignificant = whitespace;
        this.outputAlias = outputAlias;
    }

    /**
     * Writes the field value to the specified ByteBuffer.
     *
     * @param buffer the ByteBuffer to write to.
     */
    public void write(ByteBuffer buffer) {
        write(null, buffer);
    }

    /**
     * Writes the specified value to the given ByteBuffer.
     *
     * @param value the value to write.
     * @param buffer the ByteBuffer to write to.
     */
    public void write(Object value, ByteBuffer buffer) {
        log.debug("Writing {} with value {}", this, value);
        buffer.position(this.start);
        buffer.put(getBytes(value));
    }

    /**
     * Reads the field value from the specified ByteBuffer.
     *
     * @param buffer the ByteBuffer to read from.
     * @return the field value as a String.
     */
    public String read(ByteBuffer buffer) {
        byte[] bytes = new byte[this.length];
        buffer.position(this.start);
        buffer.get(bytes);
        String fieldValue = new String(bytes);
        if (!whitespaceSignificant || fieldValue.trim().isEmpty()) {
            // Do not preserve whitespace
            fieldValue = fieldValue.trim();
        }
        log.debug("Read {}='{}'", this.id, fieldValue);
        return fieldValue;
    }

    /**
     * Checks if the field is composite.
     *
     * @return true if the field is composite, false otherwise.
     */
    public boolean isComposite() {
        return composite;
    }

    /**
     * Gets the alias of the field.
     *
     * @return the alias of the field.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Checks if the field has an alias.
     *
     * @return true if the field has an alias, false otherwise.
     */
    public boolean isAliased() {
        return this.alias != null;
    }

    /**
     * Checks if the field is an input field.
     *
     * @return true if the field is an input field, false otherwise.
     */
    public boolean isInput() {
        return input;
    }

    /**
     * Checks if whitespace is significant for the field.
     *
     * @return true if whitespace is significant, false otherwise.
     */
    public boolean isWhitespaceSignificant() {
        return whitespaceSignificant;
    }

    /**
     * Gets the output alias of the field.
     *
     * @return the output alias of the field.
     */
    public String getOutputAlias() {
        return outputAlias;
    }

    /**
     * Checks if the field has an output alias.
     *
     * @return true if the field has an output alias, false otherwise.
     */
    public boolean isOutputAliased() {
        return this.outputAlias != null;
    }

    /**
     * Converts the specified value to a byte array suitable for writing to a ByteBuffer.
     *
     * @param value the value to convert.
     * @return the byte array representation of the value.
     */
    protected byte[] getBytes(Object value) {
        // Allocate result array
        byte[] bytes = new byte[this.length];
        // Fill with blanks
        Arrays.fill(bytes, (byte) ' ');
        // Set value or null
        String stringValue = value == null ? null : value.toString();
        if (stringValue != null && !stringValue.equals("")) {
            // Value is not null and not the empty string: copy over values to
            // result
            byte[] valueBytes = value.toString().getBytes();
            // Make sure not to copy more bytes than exist in the dest array
            int numberToCopy = valueBytes.length > this.length ? this.length : valueBytes.length;
            // Copy to result
            System.arraycopy(valueBytes, 0, bytes, 0, numberToCopy);
        }
        return bytes;
    }

    /**
     * Default ordering uses start, length.
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Field o) {
        return DEFAULT_SORT.compare(this, o);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((length == null) ? 0 : length.hashCode());
        result = prime * result + ((start == null) ? 0 : start.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Field other = (Field) obj;
        if (length == null) {
            if (other.length != null) {
                return false;
            }
        }
        else if (!length.equals(other.length)) {
            return false;
        }
        if (start == null) {
            if (other.start != null) {
                return false;
            }
        }
        else if (!start.equals(other.start)) {
            return false;
        }
        return true;
    }

    /**
     * Returns the length of this field.
     *
     * @return the length of the field
     */
    public Integer getLength() {
        return length;
    }

    /**
     * Returns the identifier of this field.
     *
     * @return the field identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the start position of this field.
     *
     * @return the start position of the field
     */
    public Integer getStart() {
        return start;
    }

    @Override
    public String toString() {
        return "Field [id=" + id + ", start=" + start + ", length=" + length + ", composite=" + composite + ", input="
                + input + ", alias=" + alias + ", whitespaceSignificant=" + whitespaceSignificant + ", outputAlias="
                + outputAlias + "]";
    }

    /**
     * Comparator that compares fields based on their start and length.
     */
    public static class StartLengthComparator implements Comparator<Field> {

        @Override
        public int compare(Field o1, Field o2) {
            int startComparison = o1.start.compareTo(o2.start);

            if (startComparison != 0) {
                return startComparison;
            }

            return o1.length.compareTo(o2.length);
        }
    }

    /**
     * Comparator that compares fields based on their name, start, and length.
     */
    public static class NameStartLengthComparator implements Comparator<Field> {

        @Override
        public int compare(Field o1, Field o2) {
            int nameComparison = o1.id.compareTo(o2.id);
            if (nameComparison != 0) {
                return nameComparison;
            }
            int startComparison = o1.start.compareTo(o2.start);
            if (startComparison != 0) {
                return startComparison;
            }
            return o1.length.compareTo(o2.length);
        }

    }

}
