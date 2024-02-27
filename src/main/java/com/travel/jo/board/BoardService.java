package com.travel.jo.board;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.travel.jo.DataNotFoundException;
import com.travel.jo.user.SiteUser;
import com.travel.jo.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	private final BoardMapper boardMapper;
	private final UserService uService;
	private final BoardVoteMapper qvMapper;
	
	public List<Board> getList() {
		return this.boardMapper.selectAllBoard();
	}
	
	public int getCountBoard(String index, String text) {
		return this.boardMapper.countAllBoard(index, text);
	}
	
	public List<Board> getListLimt(int page, int pageSize) {
		int offset  = (page - 1) * pageSize;
		return this.boardMapper.selectLimtBoard(pageSize, offset);
	}
	
	public int getPage(String index, String text) {
		int pageDiv = 10;
		if (getCountBoard(index, text) % pageDiv == 0) {
			return getCountBoard(index, text) / pageDiv; 
		} else {
			return (getCountBoard(index, text) / pageDiv) + 1; 
		}
	}
	
	public Board getBoard(Integer id) {
		Board board = this.boardMapper.selectBoardById(id);
		if(board != null) {
			return board;
		} else {
			throw new DataNotFoundException("Board not found");
		}
	}
	
	public void create(String subject, String content, Principal principal) {
		Board board = new Board();
		
		board.setSubject(subject);
		board.setContent(content);
		SiteUser siteUser = this.uService.getUser(principal.getName());
		board.setAuthor_id(siteUser.getUsername());
		boardMapper.insertBoard(board);
	}
	
	public void createTest(String subject, String content, int i, Principal principal){
		for (int j = 0; j < i; j++) {
			Board board = new Board();
			
			board.setSubject(subject+"["+ j +"]");
			board.setContent(content);
			SiteUser siteUser = this.uService.getUser(principal.getName());
			board.setAuthor_id(siteUser.getUsername());
			boardMapper.insertBoard(board);
		}
	}
	
	public void modify(Board board, String subject, String content) {
		board.setSubject(subject);
		board.setContent(content);
		board.setModifyDate(LocalDateTime.now());
		this.boardMapper.updateBoard(board);
	}
	
	public void delete (Board board) {
		System.out.println("삭제 시작");
		this.boardMapper.deleteBoard(board);
	}
	
	public void vote(Integer id, Principal principal) {
		this.qvMapper.insertVoteById(id, principal.getName());
		
	}
	public int cntVote(Integer id) {
		return this.qvMapper.cntVoteById(id);
	}
	
	public BoardVote getBoardVoteById(Integer id, Principal principal) {
		
		return this.qvMapper.selectBoardVoteById(id, principal.getName());
	}
	
	public void setCntVote(Integer board_id) {
		this.qvMapper.updateCntVote(board_id);
	}
	
	public List<Board> search(int page, int pageSize, String index, String text) {
		int offset  = (page - 1) * pageSize;
		return this.boardMapper.searchText(pageSize, offset, index, text);
	}
}
