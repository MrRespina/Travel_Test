package com.travel.jo.board;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardVoteMapper {
	public abstract int insertVoteById(@Param("board_id") int board_id, @Param("username")String username);
	public abstract int cntVoteById(int id);
	public abstract int updateCntVote(@Param("board_id") int board_id);
	public abstract BoardVote selectBoardVoteById(@Param("board_id") int board_id, @Param("username") String username);
}
