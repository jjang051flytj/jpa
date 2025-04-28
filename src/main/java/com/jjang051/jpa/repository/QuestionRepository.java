package com.jjang051.jpa.repository;

import com.jjang051.jpa.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
