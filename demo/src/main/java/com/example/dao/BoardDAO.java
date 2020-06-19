package com.example.dao;

import java.util.HashMap;
import java.util.List;

import com.example.vo.BoardVO;

public interface BoardDAO {
	public int insertBoard(BoardVO obj);
	public List<BoardVO> selectBoard(HashMap<String, Object> map);
	public BoardVO selectBoardOne(int no);
	public int updateBoard(BoardVO obj);
	public int deleteBoard(BoardVO obj);
	public int insertBoardBatch(List<BoardVO> list);
	public int countBoard(String text); //전체 개수 구하기
	
	public int updateHit(int no);
	public int selectBoardPrev(int no);
	public int selectBoardNext(int no);
	public BoardVO selectBoardImg(int no);
}
