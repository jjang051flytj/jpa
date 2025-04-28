package com.jjang051.jpa.entity;

import com.jjang051.jpa.dto.QuestionDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
//@Setter
@NoArgsConstructor
//@Table(name="table_question")  //table에 이름 바꾸고 싶을때...
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String title;
    private String content;

    //하나의 question에 여러개의 comment가 있을 수 있다. 1:N
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @ManyToOne
    @JoinColumn(name = "userID",
            referencedColumnName = "userID")
    private Member writer;

    @Builder
    public Question(Integer id,String title, String content,Member writer,
                    LocalDateTime regDate, LocalDateTime modifyDate) {
        this.id = id;
        this.title=title;
        this.writer=writer;
        this.content=content;
        this.regDate=regDate;
        this.modifyDate=modifyDate;
    }
    public static QuestionDto toDto(Question question) {
        QuestionDto questionDto = QuestionDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .commentList(question.getCommentList())
                .regDate(question.getRegDate())
                .modifyDate(question.getModifyDate())
                .build();
        return questionDto;
    }
    public void changeTitle(String title) {
        this.title=title;
    }
    public void changeContent(String content) {
        this.content=content;
    }
}
