package com.example.tdd;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SimpleBoardService implements BoardService {

    private final BoardRepository boardRepository;

    public SimpleBoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public void create(BoardCreationCommand command) {
        final var board = new Board(command.getTitle(), command.getContent(), command.getAuthor());
        boardRepository.save(board);
    }

    @Override
    public Board getBoard(long id) {
        return boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    @Transactional
    public void update(BoardUpdateCommand command, long id) {
        Board board = boardRepository.findById(id).get();
        board.update(command);
    }

    @Override
    public List<Board> getBoards(BoardFilter filter) {
        if(filter.getTitle() != null && !filter.getTitle().isBlank()) {
            return boardRepository.findByTitle(filter.getTitle());
        }
        return boardRepository.findAll();
    }

    @Override
    public void delete(long id) {
        boardRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        boardRepository.deleteById(id);
    }
}
