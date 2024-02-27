package com.travel.jo.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardMapper {
	public abstract List<Board> selectAllBoard();
	public abstract List<Board> selectLimtBoard(@Param("pageSize") int pageSize, @Param("offset") int offset);
	public abstract Board selectBoardById(Integer id);
	public abstract List<Board> searchText(@Param("pageSize") int pageSize, @Param("offset") int offset,
			@Param("index") String index, @Param("text") String text);
	
	public abstract int insertBoard(Board board);
	public abstract int countAllBoard(@Param("index") String index, @Param("text") String text);
	public abstract int updateBoard(Board board);
	public abstract int deleteBoard(Board board);
}
