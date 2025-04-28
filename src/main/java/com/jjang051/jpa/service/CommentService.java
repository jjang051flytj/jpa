package com.jjang051.jpa.service;

import com.jjang051.jpa.entity.Comment;
import com.jjang051.jpa.entity.Member;
import com.jjang051.jpa.entity.Question;
import com.jjang051.jpa.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    //CommentRepository를 만들고
    //여기에 di
    // 생성자 주입방식
    private final CommentRepository commentRepository;
    public Comment write(String content, Question question, Member writer) {
        //comment entity
        Comment comment = Comment.builder()
                .content(content)
                .question(question)
                .writer(writer)
                .build();
        //
        return commentRepository.save(comment); //entity를 리턴해준다.
    }

    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }
}
