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
 * Service-tier types that remain scoped to {@code geoclient-service}
 * because they carry Jackson wire-format metadata bound to the REST API
 * (e.g. {@code @JsonRootName} on {@link GeosupportResponse}).
 * <p>
 * Framework-neutral response value objects previously in this package have
 * been moved to {@code gov.nyc.doitt.gis.geoclient.api.version} in the
 * {@code geoclient-core} module.
 */
package gov.nyc.doitt.gis.geoclient.service.domain;
