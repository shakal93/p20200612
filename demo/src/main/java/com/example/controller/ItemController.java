package com.example.controller;

import java.util.HashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dao.ItemDAO;
import com.example.vo.ItemVO;

@Controller
@RequestMapping(value = "/item")
public class ItemController {

	@Autowired
	private ItemDAO iDAO = null;
	
	@RequestMapping(value = "/itemlist", method = RequestMethod.GET)
	public String itemlist(Model model,
			@RequestParam(value="page", defaultValue = "1", required = false) int page) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("start", page*10-9);
		map.put("end", page*10);
		List<ItemVO> list = iDAO.selectList(map);
		
		int cnt = iDAO.countItem();
		
		model.addAttribute("list", list);
		model.addAttribute("cnt", (cnt-1)/10 + 1);
		
		return "/item/itemlist";
	}
}
