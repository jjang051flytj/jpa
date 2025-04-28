package com.jjang051.jpa.dto;

import com.jjang051.jpa.entity.Comment;
import com.jjang051.jpa.entity.Member;
import com.jjang051.jpa.entity.Question;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class QuestionDto {
    private Integer id;
    private String title;
    private String content;
    private Member writer;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
    private List<Comment> commentList;

    @Builder
    public QuestionDto(Integer id,
                       String title,
                       String content,
                       Member writer,
                       List<Comment> commentList,
                       LocalDateTime regDate,
                       LocalDateTime modifyDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.regDate = regDate;
        this.modifyDate = modifyDate;
        this.commentList = commentList;
    }
    //dto -> entity db save()
    public static Question toEntity(QuestionDto questionDto) {
        return Question.builder()
                .id(questionDto.getId())
                .title(questionDto.getTitle())
                .writer(questionDto.getWriter())
                .content(questionDto.getContent())
                .regDate(questionDto.getRegDate())
                .modifyDate(questionDto.getModifyDate())
                .build();
    }
}
