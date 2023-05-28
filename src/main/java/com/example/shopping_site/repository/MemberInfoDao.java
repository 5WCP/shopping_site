package com.example.shopping_site.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping_site.entity.MemberInfo;

@Repository
public interface MemberInfoDao extends JpaRepository<MemberInfo, String> {
	
	List<MemberInfo> findByMail(String mail);
	
	List<MemberInfo> findByPassword(String password);
	
	List<MemberInfo> findByPhone(String phone);
}
