package com.example.dao;

import java.util.HashMap;
import java.util.List;

import com.example.vo.ItemVO;

public interface ItemDAO {
	public List<ItemVO> selectList(HashMap<String, Object> map);
	public List<ItemVO> selectItemWhere(int[] no);
	public ItemVO selectItemOne(int no);
	public List<ItemVO> selectItmeSearch(String txt);
	public int insertItemBatch(List<ItemVO> list);
	public int deleteItemBatch(int[] no);
	public int deleteItemOne(int no);
	public int updateItemBatch(List<ItemVO> list);
	public int countItem();
}
