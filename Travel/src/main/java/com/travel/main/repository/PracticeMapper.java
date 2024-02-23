package com.travel.main.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PracticeMapper {
	
	public Practice selectById(int id);
	public List<Practice> selectAll();
	public void joinMembership(Practice prac);
	public void updateMember(Practice prac);

}
