package com.example.tdd;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public enum Format {
    SHORT("short", Response.shortOf() ),
    FULL("full", Response.fullOf());

    private final String formatName;
    private final Response response;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    Format(String formatName, Response response) {
        this.formatName = formatName;
        this.response = response;
    }

    public String getResponseJsonString() {
        try {
            return objectMapper.writeValueAsString(this.response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Format getByFormatName(String formatName) {
        return Stream.of(values())
                    .filter(format -> format.formatName.equals(formatName))
                    .findFirst()
                    .orElseThrow(RuntimeException::new);

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {
        private String status;
        private LocalDateTime currentTime;
        private String application;

        public String getStatus() {
            return status;
        }

        public LocalDateTime getCurrentTime() {
            return currentTime;
        }

        public String getApplication() {
            return application;
        }

        private static Response shortOf() {
            Response response = new Response();
            response.status = "OK";
            return response;
        }

        private static Response fullOf() {
            Response response = new Response();
            response.currentTime = LocalDateTime.now();
            response.application = "OK";
            return response;
        }
    }
}
