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
 * Jackson wire-format contract for the {@code geoclient-service} REST API.
 * <p>
 * Framework-neutral domain classes from {@code geoclient-core} and
 * {@code geoclient-parser} are bound to Jackson serialization metadata here
 * via mix-in classes registered by {@link WebJacksonConfig}. Concentrating
 * the metadata in this package keeps the domain classes free of Jackson
 * imports.
 */
package gov.nyc.doitt.gis.geoclient.service.web.jackson;
