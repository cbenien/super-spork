package com.superspork.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PreDestroy;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RestClient elasticSearchRestClient;
    private final ObjectMapper mapper;

    public ApiController()
    {
        elasticSearchRestClient = RestClient
                .builder(new HttpHost("elasticsearch", 9200, "http"))
                .build();

        mapper = new ObjectMapper();
        // ignore additional properties from ElasticSearch
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @PreDestroy
    public void dispose()
    {
        try
        {
            logger.info("Closing ElasticSearch REST client...");
            elasticSearchRestClient.close();
        }
        catch (IOException ex)
        {
            logger.error("Failed to close ElasticSearch REST client", ex);
        }
    }

    @GetMapping("/users")
    public @ResponseBody List<User> users()
        throws IOException
    {
        logger.info("users was called...");

        Response getResponse = elasticSearchRestClient.performRequest(
                "GET",
                "/users/user/_search");

        int statusCode = getResponse.getStatusLine().getStatusCode();
        if (statusCode != 200)
        {
            logger.info("Got unexpected response from ElasticSearch: {}", getResponse);
            throw new IOException("Unexpected response from ElasticSearch: " + getResponse);
        }

        logger.info("Got response from ElasticSearch: {}", getResponse);
        String esUserJson = streamToString(getResponse.getEntity().getContent());
        logger.info(esUserJson);

        EsSearchResult esSearchResult = mapper
                .readerFor(EsSearchResult.class)
                .readValue(esUserJson);

        if (esSearchResult.getHits().getTotal() == 0)
        {
            return new ArrayList<>();
        }

        return Arrays.stream(esSearchResult.getHits().getHits())
                .map(x -> new User(
                        x.get_source().getUserName(),
                        x.get_source().getEmail(),
                        x.get_source().getFullName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{userId}")
    public @ResponseBody User userById(@PathVariable String userId)
        throws IOException
    {
        logger.info("userById was called with id {} ...", userId);

        Response getResponse = elasticSearchRestClient.performRequest(
                "GET",
                "/users/user/" + userId);

        logger.info("Got response from ElasticSearch: {}", getResponse);
        String esUserJson = streamToString(getResponse.getEntity().getContent());
        logger.info(esUserJson);

        EsUserGetWrapper esUserResponse = mapper
                .readerFor(EsUserGetWrapper.class)
                .readValue(esUserJson);

        EsUser esUser = esUserResponse.get_source();

        return new User(esUser.getUserName(), esUser.getEmail(), esUser.getFullName());
    }

    private String streamToString(InputStream inputStream)
            throws IOException
    {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }

        return result.toString("UTF-8");
    }

    @PutMapping("/users/{userId}")
    public @ResponseBody User putUser(@PathVariable String userId, @RequestBody User user)
        throws IOException
    {
        //TODO check for valid ID (e.g. regex)
        //TODO check for duplicate userName or email

        logger.info("userById was called with id {} ...", userId);

        EsUser esUser = new EsUser(
                user.getUserName(),
                user.getEmail(),
                user.getFullName(),
                new Date());

        String esUserJson = mapper.writeValueAsString(esUser);
        logger.info("Serialized json for ES: {}", esUserJson);

        HttpEntity entity = new NStringEntity(esUserJson, ContentType.APPLICATION_JSON);

        Response putResponse = elasticSearchRestClient.performRequest(
                "PUT",
                "/users/user/" + userId,
                Collections.emptyMap(),
                entity);

        int statusCode = putResponse.getStatusLine().getStatusCode();
        if (statusCode != 201 && statusCode != 200)
        {
            logger.info("Got unexpected response from ElasticSearch: {}", putResponse);
            throw new IOException("Unexpected response from ElasticSearch: " + putResponse);
        }

        return user;
    }

}

