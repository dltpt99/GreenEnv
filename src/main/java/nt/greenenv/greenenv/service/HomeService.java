package nt.greenenv.greenenv.service;

import nt.greenenv.greenenv.domain.plant.Plant;
import nt.greenenv.greenenv.domain.shop.Product;
import nt.greenenv.greenenv.repository.PlantsRepository;
import nt.greenenv.greenenv.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {
    private PlantsRepository plantsRepository;
    private ProductRepository productRepository;

    @Autowired
    public HomeService(PlantsRepository plantsRepository, ProductRepository productRepository) {
        this.plantsRepository = plantsRepository;
        this.productRepository = productRepository;
    }

    public List<Plant> searchPlant(String search) {
        return plantsRepository.findAllByPlantNameContaining(search);
    }

    public List<Product> searchProduct(String search) {
        return productRepository.findAllByPlantNameContaining(search);
    }
}
