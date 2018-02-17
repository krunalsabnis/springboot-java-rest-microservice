package com.kru.qualibrate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import org.junit.Before;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.AbstractMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

public abstract class AbstractControllerTest extends QualibrateJavaApiApplicationTests {

    protected HttpHeaders headers;
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper om;

    @Autowired
    protected WebApplicationContext wac;

    protected RestDocumentationResultHandler document;

    protected abstract String url();

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

    @Before
    public void setupMvcMock() {

        headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + "base64CredentialsHere");
        headers.add("Accept", "application/json");
        headers.add("Accept", "application/json");

        this.document = document("{class-name}/{method-name}/{step}",
                preprocessRequest(prettyPrint(), removeHeaders("Authorization")),
                preprocessResponse(prettyPrint(), removeHeaders("Content-Length", "X-Content-Type-Options",
                        "Cache-Control", "Pragma", "Expires", "Strict-Transport-Security")));

        AbstractMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac)
                .apply(documentationConfiguration(this.restDocumentation).uris()
                        .withScheme("https")
                        .withHost("supplies-host")
                        .withPort(8080))
                .alwaysDo(print(System.out))
                .alwaysDo(document);

        List<MockMvcConfigurer> configurers = getConfigurers();
        //if (CollectionUtils.isNotEmpty(configurers)) {
            //configurers.forEach(builder::apply);
        //}

        mockMvc = builder.build();
    }

    /**
     * @return A list of MockMvcConfigurers to apply to mockMvc, may be null. Concrete
     * classes may override to add configurers, such as springSecurity().
     */
    protected List<MockMvcConfigurer> getConfigurers() {
        return null;
    }

    protected <T> T get(Class<T> clazz, String urlTemplate, Object... urlPaths) throws Exception {
        MvcResult res = get(status().isOk(), fullUrl(urlTemplate), urlPaths).andReturn();
        return om.readValue(res.getResponse().getContentAsByteArray(), clazz);
    }

    protected ResultActions get(ResultMatcher status, String urlTemplate, Object... urlPaths) throws Exception {
        return mockMvc.perform(RestDocumentationRequestBuilders.get(urlTemplate, urlPaths).headers(headers))
                .andExpect(status);
    }

    protected ResultActions delete(ResultMatcher status, Object... urlPaths) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(fullUrl(urlPaths)).headers(headers)).andExpect(status);
    }

    protected ResultActions put(Object bodyObj, ResultMatcher status, Object... urlPaths) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.put(fullUrl(urlPaths)).contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsBytes(bodyObj)).headers(headers)).andExpect(status);
    }

    protected <T> List<T> list(Class<T> clazz, Object... urlPaths) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(fullUrl(urlPaths)).headers(headers);
        final MvcResult res = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        return list(clazz, res);
    }

    private <T> List<T> list(Class<T> clazz, MvcResult res) throws Exception {
        final JsonNode contentNode = om.readValue(res.getResponse().getContentAsByteArray(), JsonNode.class);
        return om.convertValue(contentNode,
                om.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    protected <T> List<T> page(Class<T> clazz, Object... urlPaths) throws Exception {
        

    	RequestBuilder requestBuilder = MockMvcRequestBuilders.get(fullUrl(urlPaths)).headers(headers);
        final MvcResult res = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        return page(clazz, res);
    }

    private <T> List<T> page(Class<T> clazz, MvcResult res) throws Exception {
        final JsonNode contentNode = om.readValue(res.getResponse().getContentAsByteArray(), JsonNode.class);
        return om.convertValue(contentNode.findPath("content"),
                om.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    protected String fullUrl(Object... urlPaths) {
        final String addedUrl = Arrays.stream(urlPaths).map(Object::toString).collect(Collectors.joining("/"));
        String postfix = url().endsWith("/") ? "" : "/";
        return url() + postfix + addedUrl;
    }

    @SuppressWarnings("unchecked")
    protected <T> T post(final T bodyObj, final Object... urlPaths) throws Exception {
        final MvcResult result = post(bodyObj, status().isCreated(), urlPaths).andReturn();
        return readEntity(result, (Class<T>) bodyObj.getClass());
    }

    protected <T> T post(final Object bodyObj, Class<T> respClass, ResultMatcher status, final Object... urlPaths)
            throws Exception {
        final MvcResult result = post(bodyObj, status, urlPaths).andReturn();
        return parseTo(result, respClass);
    }

    protected <T> T post(final Object bodyObj, Class<T> respClass, final Object... urlPaths) throws Exception {
        return post(bodyObj, respClass, status().isCreated(), urlPaths);
    }

    protected <T> T post(final Object bodyObj, Class<T> respClass, RequestPostProcessor requestPostProcessor,
                         final Object... urlPaths) throws Exception {
        final MvcResult result = post(bodyObj, status().isCreated(), requestPostProcessor, urlPaths).andReturn();
        return parseTo(result, respClass);
    }

    protected <T> ResultActions post(final T bodyObj, ResultMatcher status, final Object... urlPaths) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.post(fullUrl(urlPaths))
                        .contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bodyObj))
                        .headers(headers))
                .andExpect(status);
    }

    protected <T> ResultActions post(final T bodyObj, ResultMatcher status, RequestPostProcessor requestPostProcessor,
                                     final Object... urlPaths) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.post(fullUrl(urlPaths))
                        .with(requestPostProcessor).headers(headers)
                        .contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bodyObj)))
                .andExpect(status);
    }

    protected <T> T readEntity(MvcResult result, Class<T> entityClass) throws IOException {
        T value = parseTo(result, entityClass);
        assertThat(value, notNullValue());
        return value;
    }

    protected <T> T parseTo(MvcResult result, Class<T> entityClass) throws IOException {
        return om.readValue(result.getResponse().getContentAsString(), entityClass);
    }

    public static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        public ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        public FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}
