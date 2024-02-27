package com.travel.jo.board;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private int id;
	private String subject;
	private String content;
	private LocalDateTime createDate;
	private String author_id;
	private LocalDateTime modifyDate;
	private int vote;
}
