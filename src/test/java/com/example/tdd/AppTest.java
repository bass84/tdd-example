package com.example.tdd;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("사용자는 게시글 등록정보를 입력하여 게시글을 등록할 수 있다.")
    public void test1() throws Exception {
        // Given
        final var requestBody = new BoardCreationCommand("테스트 테이틀", "테스트 내용", "사용자");
        final var requestBodyString = objectMapper.writeValueAsString(requestBody);

        // when
        ResponseEntity<Void> actual = restTemplate.postForEntity("/boards", requestBody, Void.class);

        // then
        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        final var expected = restTemplate.getForEntity("/boards", String.class);
        Assertions.assertThat(expected).isNotNull();
    }

}
