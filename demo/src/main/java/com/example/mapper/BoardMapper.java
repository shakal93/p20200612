package com.example.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import com.example.vo.BoardVO;

public interface BoardMapper {
	
	@Delete("DELETE FROM BOARD WHERE BRD_NO =#{brd_no}")
	public int deleteBoard(@Param("obj") BoardVO obj);
	
//	@Insert
//	
//	@Select
//	
//	@Update
}