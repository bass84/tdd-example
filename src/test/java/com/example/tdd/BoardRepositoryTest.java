package com.example.tdd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("findByTitle 함수는 제목이 주어지면 일치하는 게시글 목록을 반환한다.")
    public void test1() {

        // given
        Board board1 = new Board("타이틀1", "내용1", "사용자");
        Board board2 = new Board("타이틀2", "내용2", "사용자");
        Board board3 = new Board("타이틀3", "내용3", "사용자");

        em.persist(board1);
        em.persist(board2);
        em.persist(board3);

        // when
        List<Board> actual = boardRepository.findByTitle("타이틀1");

        // then
        Assertions.assertEquals(1, actual.size());
    }
}