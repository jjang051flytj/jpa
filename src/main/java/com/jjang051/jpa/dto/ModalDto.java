package com.jjang051.jpa.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModalDto {
    private String title;
    private String content;
    private boolean isShow;
}
