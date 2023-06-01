package com.example.shopping_site.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping_site.entity.OrderStatus;
import com.example.shopping_site.entity.UserBuyProduct;

@Repository
public interface OrderStatusDao extends JpaRepository<OrderStatus, UserBuyProduct> {
	
	List<OrderStatus> findByUserIdAndProductIdAndState(String userid, String productId, String state);
	
	List<OrderStatus> findByUserIdAndState(String userid, String state);
	
	List<OrderStatus> findByProductId(String productId);
	
	List<OrderStatus> findByUserId(String userId);
	
	List<OrderStatus> findByOrderNumber(String orderNumber);
}
