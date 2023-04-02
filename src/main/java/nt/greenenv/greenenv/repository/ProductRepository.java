package nt.greenenv.greenenv.repository;

import nt.greenenv.greenenv.domain.shop.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllBySellerId(long sellerId);

    Page<Product> findAll(Pageable pageable);

    List<Product> findAllByPlantNameContaining(String plantName);
}
