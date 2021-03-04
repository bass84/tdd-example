package com.example.tdd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {SimpleBoardService.class})
public class BoardServiceTest {

    @Autowired
    private BoardService sut;

    @MockBean
    private BoardRepository boardRepository;

    @Test
    @DisplayName("create 함수는 게시글 등록 커맨드를 입력받아 Repository에 저장 요청을 한다.")
    public void test1() {
        //  Given
        final var command = new BoardCreationCommand("테스트 타이틀", "테스트 내용", "사용자");


        // When
        sut.create(command);


        // Then
        Mockito.verify(boardRepository).save(
                Mockito.argThat(board ->
                    board.getTitle().equals("테스트 타이틀") &&
                    board.getContent().equals("테스트 내용") &&
                    board.getAuthor().equals("사용자"))
        );

    }


    @Test
    @DisplayName("getBoard 함수는 id를 입력받아 게시글 정보를 반환한다.")
    public void test2() {
        // given
        final var id = 10L;
        Mockito.when(boardRepository.findById(id))
                .thenReturn(Optional.of(new Board("테스트 타이틀", "테스트 내용", "사용자")));

        // when
        Board actual = sut.getBoard(id);

        // then
        Assertions.assertAll(
                () -> assertEquals("테스트 타이틀", actual.getTitle()),
                () -> assertEquals("사용자", actual.getAuthor()),
                () -> assertEquals("테스트 내용", actual.getContent())
                );
    }

    @Test
    @DisplayName("getBoard 함수는 존재하지 않는 id를 입력받으면 예외를 던진다.")
    public void test2_1() {
        // given
        final var id = 10L;
        Mockito.when(boardRepository.findById(id))
                .thenReturn(Optional.empty());

        // expected
        assertThrows(IllegalArgumentException.class, () -> sut.getBoard(id));

    }

    @Test
    @DisplayName("update 함수는 수정 커맨드와 ID를 입력받아 board 를 수정한다.")
    public void test3() {
        //given
        final var id = 10L;
        final var command = new BoardUpdateCommand("수정 타이틀", "수정 내용");
        final var board = new Board("테스트 타이틀", "테스트 내용", "사용자");
        Mockito.when(boardRepository.findById(id))
                .thenReturn(Optional.of(board));
        // when
        sut.update(command, id);

        //then

        Assertions.assertAll(
                () -> assertEquals("수정 타이틀", board.getTitle()),
                () -> assertEquals("수정 내용", board.getContent())
        );
    }


    @Test
    @DisplayName("getBoards 함수는 게시글 목록을 반환한다.")
    public void test4() {
        // given
        Board board1 = new Board("타이틀1", "내용1", "사용자1");
        Board board2 = new Board("타이틀2", "내용2", "사용자2");
        Board board3 = new Board("타이틀3", "내용3", "사용자3");
        List<Board> boardList = List.of(board1, board2, board3);
        Mockito.when(boardRepository.findAll()).thenReturn(boardList);

        // when
        List<Board> actual = sut.getBoards(new BoardFilter());

        // then
        assertEquals(boardList, actual);
    }

    @Test
    @DisplayName("getBoards 함수는 제목검색 필터가 주어지면 게시글 목록을 반환한다.")
    public void test4_1() {
        // given
        Board board1 = new Board("타이틀1", "내용1", "사용자1");
        List<Board> boardList = List.of(board1);
        Mockito.when(boardRepository.findByTitle("타이틀1")).thenReturn(boardList);

        // when
        List<Board> actual = sut.getBoards(new BoardFilter("타이틀1"));

        // then
        assertEquals(boardList, actual);
    }

    @Test
    @DisplayName("삭제 함수는 ID가 주어지면 Repository의 삭제함수를 호출한다.")
    public void test5() {
        // given
        final long id = 10L;
        Mockito.when(boardRepository.findById(id))
                .thenReturn(Optional.of(new Board("테스트 제목", "테스트 내용", "사용자")));
        // when
        sut.delete(id);

        // then
        Mockito.verify(boardRepository).deleteById(id);
    }

    @Test
    @DisplayName("삭제 함수는 존재하지 않는 ID가 주어지면 예외를 던진다.")
    public void test5_1() {
        // given
        final long id = 10L;
        Mockito.when(boardRepository.findById(id))
                .thenReturn(Optional.empty());

        // Expected
        assertThrows(EntityNotFoundException.class, () -> sut.delete(id));


    }
}