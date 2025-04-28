package com.jjang051.jpa.service;

import com.jjang051.jpa.dto.QuestionDto;
import com.jjang051.jpa.entity.Question;
import com.jjang051.jpa.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    public void write(QuestionDto questionDto) {
//        Question question = Question.builder()
//                .title(questionDto.getTitle())
//                .content(questionDto.getContent())
//                            .build();

        Question question = QuestionDto.toEntity(questionDto);  //jpa  entity
        questionRepository.save(question);
    }

    public List<Question> list() {
        return questionRepository.findAll();
    }

    public Question view(Integer id) {
        Optional<Question> optionalQuestion =
                questionRepository.findById(id);
        if(optionalQuestion.isPresent()) return
                optionalQuestion.get();
        return null;
    }
    /*
    public Question modify(QuestionDto questionDto) {
        //update 쿼리가 나가야 한다.
        //같은 거는 update로 나간다.
        Optional<Question> optionalQuestion = questionRepository.findById(questionDto.getId());
        if(optionalQuestion.isPresent()) {
            return questionRepository.save(QuestionDto.toEntity(questionDto));
        }
        //return questionRepository.save(QuestionDto.toEntity(questionDto));
        return null;
    }
     */

    //@Service에서만 쓸 수 있는 annotation
    // 스프링에서 transation처리해준다.
    @Transactional
    public Question modify(QuestionDto questionDto) {
        Question question = questionRepository.findById(questionDto.getId()).orElseThrow();//일단 찾아서......
        question.changeContent(questionDto.getContent());
        question.changeTitle(questionDto.getTitle());
        //question.setTitle(questionDto.getTitle().trim());
        //question.setContent(questionDto.getContent().trim());
//        optionalQuestion.ifPresent(question -> {
//            Question updateQuestion = optionalQuestion.get();
//            updateQuestion = QuestionDto.toEntity(questionDto);
//        });
        return question;
    }
}
