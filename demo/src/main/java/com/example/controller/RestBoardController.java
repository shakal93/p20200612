package com.example.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.BoardDAO;
import com.example.vo.BoardVO;

@CrossOrigin("*") //CROS 해제
@RestController
public class RestBoardController {
	@Autowired
	private BoardDAO bDAO = null;
	
	@RequestMapping(value = "/rest/boardlist.json", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody HashMap<String, Object> boardList(
			@RequestParam(value="page", defaultValue = "1", required = false) int page){
		HashMap<String, Object> pmap = new HashMap<String, Object>();
		pmap.put("start", page*10-9);
		pmap.put("end", page*10);
		List<BoardVO> list = bDAO.selectBoard(pmap);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ret", list);
		return map;
	}
}
