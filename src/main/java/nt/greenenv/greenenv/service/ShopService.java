package nt.greenenv.greenenv.service;

import nt.greenenv.greenenv.domain.member.Member;
import nt.greenenv.greenenv.domain.shop.*;
import nt.greenenv.greenenv.repository.MemberRepository;
import nt.greenenv.greenenv.repository.OrderRepository;
import nt.greenenv.greenenv.repository.ProductRepository;
import nt.greenenv.greenenv.repository.ReviewRepository;
import org.aspectj.weaver.ast.Or;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;

@Service
public class ShopService {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private ReviewRepository reviewRepository;
    private MemberRepository memberRepository;

    @Autowired
    public ShopService(ProductRepository productRepository, OrderRepository orderRepository,
                       ReviewRepository reviewRepository, MemberRepository memberRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
    }

    public Product addNewProduct(ProductNewForm form, Member seller, List<MultipartFile> images) {
        Product product = new Product(form, seller.getId(), seller.getNick(), LocalDateTime.now());
        Product saved_product = productRepository.save(product);

        try {
            int i=1;
            for(MultipartFile image : images ) {
                if(!image.getContentType().startsWith("image")) continue;

                String path = "/image/" + saved_product.getId() + "/" + i + ".png";
                String store_path = "C:/Users/lab330/Desktop/Project/greenenv_image/"+ saved_product.getId() + "/" + i++ + ".png";
                image.transferTo(new File(store_path));

                if(saved_product.getImg() == null) {
                    saved_product.setImg(path);
                }
                else {
                    saved_product.setImg(
                            saved_product.getImg() + "," + path
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Image 필드 채워서 업데이트.
        productRepository.save(saved_product);
        return saved_product;
    }

    public Order newOrder(OrderNewForm form, Member buyer, long productId) {
        Optional<Product> ordered_product = productRepository.findById(productId);
        ordered_product.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다"));

//        public Order(OrderNewForm form, long buyerId, long sellerId, long productId, String buyerName, String sellerName,LocalDateTime orderDate ) {
        Order order = new Order(form, buyer.getId(), ordered_product.get().getSellerId(), productId, buyer.getNick(),
                ordered_product.get().getSellerName(),LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Review newReview(ReviewForm form, Member writer) {
        Optional<Order> orderOptional = orderRepository.findById(form.getOrderId());
        orderOptional.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다"));

        Review review = new Review(form, writer.getId());
        Order order = orderOptional.get();
        order.setReviewed(true);
        orderRepository.save(order);

        return reviewRepository.save(review);
    }

    public Review getReviewByOrderId(long id) {
        return reviewRepository.findByOrderId(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 리뷰입니다.")
        );
    }

    public List<Review> getReviewByProductId(long id) {
        List<Review> reviewList = reviewRepository.findAllByProductId(id);
        for (Review review : reviewList) {
            review.setWriterNick(memberRepository.findById(review.getWriterId()).orElseThrow(
                    () -> new IllegalArgumentException("작성자가 존재하지 않습니다.")
            ).getNick());
        }
        return reviewList;
    }

    public List<Review> getReviewByWriterId(long id) {
        return reviewRepository.findAllByWriterId(id);
    }

    public Order orderCheck(long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        order.orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));

        Order order_checked = order.get();
        order_checked.setChecked(true);
        order_checked.setDelivery(true);

        return orderRepository.save(order_checked);
    }

    public Order finishCheck(long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        order.orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));

        Order order_checked = order.get();
        order_checked.setFinished(true);

        return orderRepository.save(order_checked);
    }

    public Order getOrderById(long orderId) {
        Optional<Order> findOrder = orderRepository.findById(orderId);
        findOrder.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다"));

        return findOrder.get();
    }

    public List<Product> getAllProductBySeller(long sellerId) {
        return productRepository.findAllBySellerId(sellerId);
    }

    public Product getProductById(long no) {
        Optional<Product> product =  productRepository.findById(no);
        product.orElseThrow(() -> new IllegalArgumentException("존재하지 않은 상품입니다."));

        return product.get();
    }

    public List<Product> get4products() {
        Pageable limit = PageRequest.of(0, 4);
        return productRepository.findAll(limit).toList();
    }
    public List<Order> getAllOrderByBuyerId(long buyerId) {
        return orderRepository.findAllByBuyerId(buyerId);
    }
    public List<Order> getAllOrderBySellerId(long sellerId) {
        return orderRepository.findAllBySellerId(sellerId);
    }

    public List<Order> getAllOrder() { return orderRepository.findAll();}
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
}
