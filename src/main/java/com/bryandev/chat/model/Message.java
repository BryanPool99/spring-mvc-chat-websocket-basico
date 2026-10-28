package com.bryandev.chat.model;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Message {
    private String from;
    private String text;
    private LocalDateTime timestamp;
}
