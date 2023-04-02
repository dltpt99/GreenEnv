package nt.greenenv.greenenv.repository;

import nt.greenenv.greenenv.domain.shop.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProductId(long productId);
    List<Review> findAllByWriterId(long writerId);

    Optional<Review> findByOrderId(long orderId);
}
