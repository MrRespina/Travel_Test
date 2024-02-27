package com.travel.jo.board;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.travel.jo.user.SiteUser;
import com.travel.jo.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/board")
@Controller
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;
	private final UserService userService;
	
	@GetMapping("/list")
	public String list( Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw,
			@RequestParam(value = "index", defaultValue = "subject") String index, 
			Principal principal) {
		// page ~ 10개 board 결과x 리스트
		int pageSize = 10;
		int boardAllCnt = this.boardService.getCountBoard(index, kw);
		int totalPages = (boardAllCnt / pageSize);
		
		List<Board> boardList = this.boardService.search(page, pageSize, index, kw);
		System.out.println(kw);
		
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("boardAllCnt", boardAllCnt);
		model.addAttribute("boardList", boardList);
		if (principal != null) {
			model.addAttribute("r", principal.getName());
		}
		model.addAttribute("page", page);
		model.addAttribute("totalPages", totalPages);
		
		
		return "board_list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Board board = this.boardService.getBoard(id);
		board.setVote(this.boardService.cntVote(id));
		model.addAttribute("board", board);
		
		return "board_detail";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String boardCreate(BoardForm boardForm) {
		return "board_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String boardCreate(@Valid BoardForm boardForm, BindingResult bindingResult
			, Principal principal) {
		if (bindingResult.hasErrors()) {
			// 오류가 있다면 제목과 내용 작성 화면으로 이동
			return "board_form";
		}
		// 오류가 없다면 질문 등록 
		this.boardService.create(boardForm.getSubject(), boardForm.getContent(), principal);
		return "redirect:/board/list?page=1";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String boardModify (BoardForm boardForm, @PathVariable("id") Integer id, 
			Principal principal) {
		Board board = this.boardService.getBoard(id);
		if(!board.getAuthor_id().equals(principal.getName())
				&& !principal.getName().equals("admin")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		boardForm.setSubject(board.getSubject());
		boardForm.setContent(board.getContent());
		return "board_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String boardModify (@Valid BoardForm boardForm, BindingResult bindingResult 
			,@PathVariable("id") Integer id, Principal principal) {
		if(bindingResult.hasErrors()) {
			return "board_from";
		}
		
		Board board = this.boardService.getBoard(id);
		if(!board.getAuthor_id().equals(principal.getName())
				&& !principal.getName().equals("admin")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		
		this.boardService.modify(board, boardForm.getSubject(), boardForm.getContent());
		
		return String.format("redirect:/board/detail/%s", id);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String boardDelete(Principal principal, @PathVariable("id") Integer id) {
		Board board = this.boardService.getBoard(id);
		if(!board.getAuthor_id().equals(principal.getName())
				&& !principal.getName().equals("admin")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		}
		this.boardService.delete(board);
		
		return "redirect:/";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String boardVote(@PathVariable("id") Integer id, Principal principal) {
		if (this.boardService.getBoardVoteById(id, principal) == null) {
			this.boardService.vote(id, principal);
			this.boardService.setCntVote(id);
			return String.format("redirect:/board/detail/%s", id);
		}
		System.out.println("이미 추천함");
		return String.format("redirect:/board/detail/%s", id);
		
	}
}
