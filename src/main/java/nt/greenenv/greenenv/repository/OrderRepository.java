package nt.greenenv.greenenv.repository;

import nt.greenenv.greenenv.domain.shop.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllBySellerId(long sellerId);

    List<Order> findAllByBuyerId(long buyerId);
}
