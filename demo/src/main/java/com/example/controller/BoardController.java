package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.dao.BoardDAO;
import com.example.mapper.BoardMapper;
import com.example.vo.BoardVO;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	
	@Autowired
	private BoardDAO bDAO = null;
	
	@Autowired
	private BoardMapper boardMapper = null;
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(HttpServletRequest request, Model model,
			@RequestParam(value = "no", defaultValue = "0") int no) {
		BoardVO vo = bDAO.selectBoardOne(no);
		model.addAttribute("vo", vo);
		return "/board/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest request,
			@ModelAttribute BoardVO obj,
			@RequestParam MultipartFile[] img) throws IOException {
		if (img != null) {
			for (MultipartFile one : img) {
//				if (!one.getOriginalFilename().equals("")) { //파일명이 비어 있지 않다면
				if(one.getSize() > 0) { //첨부한 파일의 용량이 있는냐?
					obj.setBrd_img(one.getBytes());
				}
			}
		}
		bDAO.updateBoard(obj);
		return "redirect:" + request.getContextPath() + "/board/content?no=";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest request,
			@RequestParam(value = "no", defaultValue = "0") int no) {
		BoardVO obj = new BoardVO();
		obj.setBrd_no(no);
		//int ret = bDAO.deleteBoard(obj);
		int ret = boardMapper.deleteBoard(obj);
		if(ret>0) { //성공하면 목록화면
			return "redirect:" + request.getContextPath() + "/board/list";
		}
		//실패하면 이전화면
		return "redirect:" + request.getContextPath() + "/board/content?no="+ no;
	}
	
	@RequestMapping(value = "/getimg")
	public ResponseEntity<byte[]> getimg(
			@RequestParam("no") int no,
			HttpServletRequest request){
		BoardVO obj = bDAO.selectBoardImg(no);
		try {
			if (obj.getBrd_img().length > 0) {
				HttpHeaders header = new HttpHeaders();
				header.setContentType(MediaType.IMAGE_JPEG);
				ResponseEntity<byte[]> ret = new ResponseEntity<byte[]>
				(obj.getBrd_img(), header, HttpStatus.OK);
				return ret;
			}
			return null;
		}
		catch (Exception e) {
			InputStream in
				= request.getServletContext().getResourceAsStream("/resources/img/default.jpg");
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.IMAGE_JPEG);
			ResponseEntity<byte[]> ret;
			try {
				ret = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), header, HttpStatus.OK);
				return ret;
			} catch(Exception e1) {
				return null;
			}
		}
	}
	
	@RequestMapping(value = "/insertbatch", method = RequestMethod.GET)
	public String boardbatch(HttpSession httpSession, Model model) {
		String userid = (String)httpSession.getAttribute("SESSION_ID");
		if(userid == null) {
			return "redirect:/member/login";
		}
		return "/board/insertbatch";
	}
	
	@RequestMapping(value = "/insertbatch", method = RequestMethod.POST)
	public String boardbatchpost(
			@RequestParam("title[]") String[] title,
			@RequestParam("content[]") String[] content,
			@RequestParam("id[]") String[] id) {
		
		List<BoardVO> list = new ArrayList<BoardVO>();
		for(int i=0; i<title.length; i++) {
			BoardVO obj = new BoardVO();
			obj.setBrd_title(title[i]);
			obj.setBrd_content(content[i]);
			obj.setBrd_id(id[i]);
			list.add(obj);
		}
		
		bDAO.insertBoardBatch(list);
		return "redirect:/board/list";
	}
	
	@RequestMapping(value = "/content", method = RequestMethod.GET)
	public String content(Model model, HttpSession httpSession,
			@RequestParam(value="no", defaultValue = "0", required = false) int no) {
		if(no == 0) {
			return "redirect:/board/list";
		}
		
		Integer chk = (Integer)httpSession.getAttribute("SESSION_BOARD_HIT_CHECK");
		if(chk != null) {
			if(chk == 1) {
				bDAO.updateHit(no);
			}
			httpSession.setAttribute("SESSION_BOARD_HIT_CHECK", 0);
		}
		
		
		BoardVO obj = bDAO.selectBoardOne(no);
		model.addAttribute("obj", obj);
		
		int p = bDAO.selectBoardPrev(no);
		model.addAttribute("prev", p);
		
		int n = bDAO.selectBoardNext(no);
		model.addAttribute("next", n);
		return "/board/content";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HttpSession httpSession, 
			HttpServletRequest request,
			@RequestParam(value="page", defaultValue = "0", required = false) int page,
			@RequestParam(value="text", defaultValue = "", required = false) String text) {
		
		if(page == 0) {
			return "redirect:" + request.getContextPath() + "/board/list?page=1"; 
		}
		
		httpSession.setAttribute("SESSION_BOARD_HIT_CHECK", 1);

		// 목록
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", page*10-9); 	//시작위치
		map.put("end", page*10);		//종료위치	
		map.put("text", text);			//검색어
		List<BoardVO> list = bDAO.selectBoard(map);
		model.addAttribute("list", list);
		
		// 게시물 개수
		int cnt = bDAO.countBoard(text); //검색어를 넘겨줌.
		//System.out.println( (int) Math.ceil(n/10.0) );
		model.addAttribute("cnt", (cnt-1)/10+1);
		
		return "/board/list";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insertBoard(HttpSession httpSession, Model model) {
		String userid = (String)httpSession.getAttribute("SESSION_ID");
		if(userid == null) {
			return "redirect:/member/login";
		}
		model.addAttribute("userid", userid);
		return "/board/insert";
	}
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertBoardPost(@ModelAttribute BoardVO obj,
			@RequestParam MultipartFile[] imgs) throws IOException {
		if(imgs != null && imgs.length > 0) {
			for (MultipartFile one : imgs) {
				obj.setBrd_img(one.getBytes());
			}
		}
		//DAO로 obj값 전달하기
		bDAO.insertBoard(obj);
		return "redirect:/board/list";
	}
}
