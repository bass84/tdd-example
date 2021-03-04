package com.example.tdd;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/boards")
    public void create(@RequestBody BoardCreationCommand command) {
        boardService.create(command);
    }
    @GetMapping("/boards/{id}")
    public BoardDto getBoard(@PathVariable("id") long id) {
        Board board = boardService.getBoard(id);
        return new BoardDto(board.getId(), board.getTitle(), board.getContent(), board.getAuthor(),
                board.getCreatedAt(), board.getUpdatedAt());
    }

    @PutMapping("/boards/{id}")
    public void update(@RequestBody BoardUpdateCommand command, @PathVariable("id") long id) {
        boardService.update(command,id);
    }

    @GetMapping("/boards")
    public ItemsDto<BoardDto> getList(BoardFilter filter) {
        List<Board> boards = boardService.getBoards(filter);
        List<BoardDto> boardDtos = boards.stream()
                .map(board -> new BoardDto(board.getId(), board.getTitle(), board.getContent(), board.getAuthor(),
                        board.getCreatedAt(), board.getUpdatedAt()))
                .collect(toList());
        return new ItemsDto<>(boardDtos);
    }

    @DeleteMapping("/boards/{id}")
    public void delete(@PathVariable("id") long id) {
        boardService.delete(id);
    }

}
