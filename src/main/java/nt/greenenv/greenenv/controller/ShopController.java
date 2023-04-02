package nt.greenenv.greenenv.controller;

import nt.greenenv.greenenv.domain.member.Member;
import nt.greenenv.greenenv.domain.shop.*;
import nt.greenenv.greenenv.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ShopController {

    private ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/shop")
    public String allProductShow(Model model) {

        List<Product> productList = shopService.getAllProduct();
        model.addAttribute("productList", productList);

        return "/shop/allProductList";
    }

    @GetMapping("/shop/new")
    public String newProductPage(@AuthenticationPrincipal Member principal, Model model) {
        if(principal == null) return "redirect:/member/login";

        model.addAttribute("nick", principal.getNick());
        return "/shop/new";
    }

    @PostMapping("/shop/new")
    public String newProduct(@AuthenticationPrincipal Member principal,
                             ProductNewForm form,
                             @RequestParam("image") List<MultipartFile> image,
                             Model model) {

        Product product = shopService.addNewProduct(form, principal, image);
        model.addAttribute("product", product);

        return "redirect:/shop/product/" + product.getId();
    }

    @GetMapping("/shop/order")
    public String showOrderList(@AuthenticationPrincipal Member principal,
                                Model model) {
        if(principal == null) return "redirect:/member/login";

        List<Order> orderList = shopService.getAllOrderByBuyerId(principal.getId());
        if(orderList != null) model.addAttribute("orderList", orderList);

        return "/shop/allOrderList";
    }

    @GetMapping("/shop/product/{no}")
    public String productInfo(@PathVariable("no") long no, Model model ) {
        Product product = shopService.getProductById(no);
        List<Review> reviewList = shopService.getReviewByProductId(no);
        model.addAttribute("product", product);
        if (!reviewList.isEmpty()) {
            model.addAttribute("reviewList", reviewList);
        }
        return "/shop/productInfo";
    }

    @GetMapping("/shop/order/{no}")
    public String productOrderPage(@PathVariable("no") long no,
                                   @AuthenticationPrincipal Member principal,
                                   Model model) {

        if(principal == null) return "redirect:/member/login";
        model.addAttribute("product", shopService.getProductById(no));
        return "/shop/orderPage";
    }

    @PostMapping("/shop/review")
    public String newReviewFromProduct(@AuthenticationPrincipal Member principal,
                                       ReviewForm form) {
        if(principal == null) return "redirect:/member/login";

        shopService.newReview(form, principal);
        return "redirect:/shop/product/"+form.getProductId();
    }

    @PostMapping("/shop/order/{no}")
    public String productOrder(@PathVariable("no") long no,
                               @AuthenticationPrincipal Member principal,
                               Model model, OrderNewForm form) {
        if(principal == null) return "redirect:/member/login";

        Order order = shopService.newOrder(form, principal, no);
        Product product = shopService.getProductById(no);
        model.addAttribute("order", order);
        model.addAttribute("product", product);
        return "/shop/orderInfo";
    }

    @GetMapping("/shop/order/info/{no}")
    public String detailOrderPage(@PathVariable("no") long no,
                                  @AuthenticationPrincipal Member member,
                                  Model model) {
        if(member == null) return "redirect:/member/login";

        Order order = shopService.getOrderById(no);
        if (order.isReviewed()) {
            model.addAttribute("review", shopService.getReviewByOrderId(no));
        }

        Product product = shopService.getProductById(order.getProductId());
        model.addAttribute("order", order);
        model.addAttribute("product", product);
        return "/shop/orderInfo";
    }

    @GetMapping("/shop/review/{no}")
    public String orderPageForReview(@PathVariable("no") long no,
                                  @AuthenticationPrincipal Member member,
                                  Model model) {
        if(member == null) return "redirect:/member/login";

        Order order = shopService.getOrderById(no);
        Product product = shopService.getProductById(order.getProductId());
        model.addAttribute("order", order);
        model.addAttribute("product", product);
        return "/shop/orderInfoForReview";
    }

    @PostMapping("/shop/order/check/{no}")
    public String orderCheck(@PathVariable("no") long no) {
        shopService.orderCheck(no);
        return "redirect:/shop/my";
    }

    @GetMapping("/shop/order/finish/{no}")
    public String finishCheck(@PathVariable("no") long no) {
        shopService.finishCheck(no);
        return "redirect:/shop/order";
    }

    @GetMapping("/shop/my")
    public String myShop(@AuthenticationPrincipal Member principal, Model model) {

        List<Product> productList = shopService.getAllProductBySeller(principal.getId());
        model.addAttribute("productList", productList);

        List<Order> orderList = shopService.getAllOrderBySellerId(principal.getId());
        model.addAttribute("orderList", orderList);

        return "/shop/Mypage";
    }
}
