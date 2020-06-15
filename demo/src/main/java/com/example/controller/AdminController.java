package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.example.dao.ItemDAO;
import com.example.dao.MemberDAO;
import com.example.vo.ItemVO;
import com.example.vo.MemberVO;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	@Autowired
	private ItemDAO iDAO = null;
	@Autowired
	private MemberDAO mDAO = null;
	
	@RequestMapping(value = "/home")
	public String home() {
		return "/admin/home";
	}
	
	@RequestMapping(value = "/itemorder")
	public String itemorder() {
		return "admin/itemorder";
	}
	
	@RequestMapping(value = "/member", method = RequestMethod.GET)
	public String membermanage(Model model) {
		List<MemberVO> list = mDAO.selectMemberLIst();
		model.addAttribute("list", list);
		return "/admin/member";
	}
	
	@RequestMapping(value = "/member", method = RequestMethod.POST)
	public String membermanagepost(@RequestParam(value="chk[]", required = false) String[] id) {
		mDAO.deleteMemberBatch(id);
		return "redirect:/admin/member";
	}
	
	@RequestMapping(value = "/item", method = RequestMethod.GET)
	public String itemlist(Model model,
			@RequestParam(value="page", defaultValue = "1", required = false) int page) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("start", page*10-9);
		map.put("end", page*10);
		List<ItemVO> list = iDAO.selectList(map);
		
		int cnt = iDAO.countItem();
		
		model.addAttribute("list", list);
		model.addAttribute("cnt", (cnt-1)/10 + 1);
		
		return "/admin/item";
	}
	
	@RequestMapping(value = "/item", method = RequestMethod.POST)
	public String itembatch(@RequestParam("btn") String btn,
			RedirectAttributes redirectAttributes,
			@RequestParam(value="chk[]", required = false) int[] itemno) {
		if(btn.equals("일괄삭제")) {
			iDAO.deleteItemBatch(itemno);
		}
		else if (btn.equals("일괄수정")) {
			redirectAttributes.addFlashAttribute("abc", itemno);
			return "redirect:/admin/itemupdate";
		}
//		System.out.println(btn);
		return "redirect:/admin/item";
	}
	
	@RequestMapping(value="/itemdeleteone")
	public String itemdeleteone(@RequestParam(value = "no", defaultValue = "0") int no) {
		iDAO.deleteItemOne(no);
		return "redirect:/admin/item";
	}
	
	@RequestMapping(value="/itemupdate", method = RequestMethod.GET)
	public String itemupdate(Model model, HttpServletRequest req) {
		Map<String, ?> map = RequestContextUtils.getInputFlashMap(req);
		if(map != null) {
			int[] tmp = (int[]) map.get("abc");
//			for(int i=0;i<tmp.length;i++) {
//				System.out.println(tmp[i]);
//			}
			List<ItemVO> list = iDAO.selectItemWhere(tmp);
			model.addAttribute("list", list);
			return "/admin/itemupdate";
		}
		
		return "redirect:/admin/item";
	}
	
	@RequestMapping(value="/itemupdate", method = RequestMethod.POST)
	public String itemupdatepost(
			@RequestParam("no[]") int[] no,
			@RequestParam("name[]") String[] name,
			@RequestParam("price[]") int[] price,
			@RequestParam("qty[]") int[] qty,
			@RequestParam("des[]") String[] des) {
		List<ItemVO> list = new ArrayList<ItemVO>();
		for(int i=0; i<no.length; i++) {
			ItemVO obj = new ItemVO();
			obj.setItemno(no[i]);
			obj.setItemname(name[i]);
			obj.setItemprice(price[i]);
			obj.setItemqty(qty[i]);
			obj.setItemdes(des[i]);
			list.add(obj);
		}
		iDAO.updateItemBatch(list);
		return "redirect:/admin/item";
	}
	
	@RequestMapping(value = "/iteminsert")
	public String iteminsert() {
		return "/admin/iteminsert";
	}
	
	@RequestMapping(value = "/iteminsert", method=RequestMethod.POST)
	public String iteminsertpost(
			@RequestParam("name[]") String[] name,
			@RequestParam("price[]") int[] price,
			@RequestParam("qty[]") int[] qty,
			@RequestParam("content[]") String[] content) {
		
		List<ItemVO> list = new ArrayList<ItemVO>();
		for(int i=0; i<name.length; i++) {
			ItemVO obj = new ItemVO();
			obj.setItemname(name[i]);
			obj.setItemprice(price[i]);
			obj.setItemqty(qty[i]);
			obj.setItemdes(content[i]);
			
			list.add(obj);
		}
		
		iDAO.insertItemBatch(list);
		
		return "redirect:/admin/item"; //<a href="/admin/home">자동화</a>
	}
		
	
}
