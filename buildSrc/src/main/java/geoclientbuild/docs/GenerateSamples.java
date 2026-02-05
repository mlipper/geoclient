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
package geoclientbuild.docs;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFile;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import geoclientbuild.client.HttpClient;
import geoclientbuild.client.Request;
import geoclientbuild.client.Response;
import geoclientbuild.client.RestClient;

abstract public class GenerateSamples extends DefaultTask {

    public static final String ASCIIDOC_BEGIN_TAG = "// tag::user_guide[]";
    public static final String ASCIIDOC_END_TAG = "// end::user_guide[]";

    private final ObjectMapper mapper = new ObjectMapper();

    @Input
    abstract public Property<URI> getBaseUri();

    @InputFile
    abstract public RegularFileProperty getRequestsFile();

    @OutputDirectory
    abstract public DirectoryProperty getDestinationDirectory();

    @TaskAction
    public void generateSamples() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = getRequestsFile().getAsFile().get();
        List<HttpRequestAdapter> requests = loadRequests(file);
        RestClient restClient = new HttpClient();
        for (HttpRequestAdapter request : requests) {
            try {
                StringBuffer buff = new StringBuffer(ASCIIDOC_BEGIN_TAG);
                buff.append('\n');
                getLogger().lifecycle("request: {}", request.toString());
                Response response = restClient.call(request);
                getLogger().lifecycle("response: {}", response.toString());
                if(response.getHttpCode() != 200) {
                    throw new RuntimeException(
                            String.format("Non-200 response code received: %d", response.getHttpCode()));
                }
                buff.append(format(response.getBody()));
                buff.append('\n');
                buff.append(ASCIIDOC_END_TAG);
                buff.append('\n');
                String responseStr = buff.toString();
                getLogger().info(responseStr);
                writeResponse(request, responseStr);
            }
            catch (Exception e) {
                getLogger().error(e.getMessage());
                throw new RuntimeException("Build failed:", e);
            }
        }
    }

    private List<HttpRequestAdapter> adapt(List<HttpRequestAdapter> requests) {
        final URI uri = insureEndsWithSlash(getBaseUri().get());
        getLogger().lifecycle("Using service base URI: {}", uri.toString());
        return requests.stream().map(r -> {
            String serviceUrl = uri.resolve(r.getType()).toString();
            getLogger().lifecycle("Using service endpoint URL: {}", serviceUrl);
            r.setUri(serviceUrl);
            r.setMethod(Request.HTTP_GET_METHOD);
            return r;
        }).collect(Collectors.toList());
    }

    private URI insureEndsWithSlash(URI uri) {
        String uriStr = uri.toString();
        if (!uriStr.endsWith("/")) {
            try {
                return new URI(uriStr + "/");
            } catch (URISyntaxException e) {
                throw new RuntimeException("Invalid URI: " + uriStr, e);
            }
        }
        return uri;
    }

    private List<HttpRequestAdapter> loadRequests(File file) {
        try {
            JsonNode node = mapper.readTree(file);
            JsonNode requestsNode = node.get("requests");
            TypeReference<List<HttpRequestAdapter>> typeReference = new TypeReference<List<HttpRequestAdapter>>() {
            };
            List<HttpRequestAdapter> requests = mapper.convertValue(requestsNode, typeReference);
            return adapt(requests);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String format(String response) throws JsonProcessingException {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        JsonNode root = mapper.readTree(response);
        return mapper.writeValueAsString(root);
    }

    private Path outputFilePath(String fileName) {
        RegularFile regularFile = getDestinationDirectory().file(fileName).get();
        File javaIoFile = regularFile.getAsFile();
        return Paths.get(javaIoFile.toURI());
    }

    private void writeResponse(HttpRequestAdapter request, String response) {
        String fileName = String.format("%s-%s.jsonc", request.getType(), request.getId());
        Path filePath = outputFilePath(fileName.replace('/', '-'));
        try {
            Files.writeString(filePath, response, StandardCharsets.UTF_8);
            getLogger().info("Successfully wrote {} file.", filePath.toString());
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
