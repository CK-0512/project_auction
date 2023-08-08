package com.project.auction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.auction.vo.Member;

@Mapper
public interface MemberDao {

	public void doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email, String account);

	public Member getMemberById(int id);

	public int getLastInsertId();

	public Member getMemberByLoginId(String loginId);

	public Member getMemberByNameAndEmail(String name, String email);

	public Member getMemberByNickname(String nickname);
	
	public void doChargeMoney(int loginedMemberId, int money);

	public void doModify(int loginedMemberId, String nickname, String cellphoneNum, String email, String account);

	public void doPasswordModify(int loginedMemberId, String loginPw);
	
	public int getMembersCnt(String authLevel, String searchKeywordType, String searchKeyword);
	
	public List<Member> getMembers(String authLevel, String searchKeywordType, String searchKeyword, int itemsInAPage, int limitStart);

	public void deleteMember(int id);
}