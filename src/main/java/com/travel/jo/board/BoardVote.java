package com.travel.jo.board;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
public class BoardVote {
	private int id;
	private int board_id;
	private String username;
}
