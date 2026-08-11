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
/**
 * Framework-neutral mappers for {@code geoclient-search} types.
 * <p>
 * {@link ResponseStatusMapper} converts between {@link gov.nyc.doitt.gis.geoclient.search.ResponseStatus}
 * and a {@code Map&lt;String, Object&gt;} of Geosupport output parameters.
 * The general-purpose mapper abstraction lives in
 * {@code gov.nyc.doitt.gis.geoclient.api.mapper} in {@code geoclient-core}.
 *
 * @since 2.0
 */

package gov.nyc.doitt.gis.geoclient.search.mapper;
