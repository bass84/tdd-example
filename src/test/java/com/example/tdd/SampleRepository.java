package com.example.tdd;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Board, Long> {
}
