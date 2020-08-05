package com.springboot.myshop.domain.order.subdomain.item.entity.repository;

import com.springboot.myshop.domain.order.subdomain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
