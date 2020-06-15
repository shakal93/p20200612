package com.example.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.vo.ItemVO;

@Service
@Transactional
public class ItemDAOImpl implements ItemDAO {
	@Autowired  //@Bean으로 만들어진 객체를 받아옴.
	private SqlSessionFactory sqlFactory = null;

	@Override
	public List<ItemVO> selectList(HashMap<String, Object> map) {
		return sqlFactory.openSession().selectList("Item.selectList", map);
	}
	
	@Override
	public List<ItemVO> selectItemWhere(int[] no) {
		return sqlFactory.openSession().selectList("Item.selectItemWhere", no);
	}
	
	@Override
	public ItemVO selectItemOne(int no) {
		return sqlFactory.openSession().selectOne("Item.selectOne", no);
	}

	@Override
	public List<ItemVO> selectItmeSearch(String txt) {
		return sqlFactory.openSession().selectList("Item.itemSearch", txt);
	}
	
	@Override
	public int insertItemBatch(List<ItemVO> list) {
		return sqlFactory.openSession().insert("Item.insertItemBatch", list);
	}
	
	@Override
	public int countItem() {
		return sqlFactory.openSession().selectOne("Item.count");
	}

	@Override
	public int deleteItemBatch(int[] no) {
		return sqlFactory.openSession().delete("Item.deleteItemBatch", no);
	}

	@Override
	public int updateItemBatch(List<ItemVO> list) {
		return sqlFactory.openSession().update("Item.updateItemBatch", list);
	}

	@Override
	public int deleteItemOne(int no) {
		return sqlFactory.openSession().delete("Item.deleteItemOne", no);
	}

	
}
