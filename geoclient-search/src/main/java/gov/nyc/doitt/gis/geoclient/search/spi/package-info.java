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
 * Framework-neutral Service Provider Interface (SPI) for the
 * {@code geoclient-search} module.
 * <p>
 * Types declared in this package define the integration points that host
 * applications must implement to plug the single-field search pipeline into
 * a Geosupport backend. No Spring, Jackson, {@code jakarta.servlet}, or
 * {@code jakarta.validation} types are permitted here.
 *
 * @since 3.0
 */

package gov.nyc.doitt.gis.geoclient.search.spi;
