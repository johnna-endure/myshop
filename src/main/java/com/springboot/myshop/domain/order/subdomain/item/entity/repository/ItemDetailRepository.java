package com.springboot.myshop.domain.order.subdomain.item.entity.repository;

import com.springboot.myshop.domain.order.subdomain.item.entity.ItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDetailRepository extends JpaRepository<ItemDetail, Long> {
}
