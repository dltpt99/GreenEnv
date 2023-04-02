package nt.greenenv.greenenv.controller;

import nt.greenenv.greenenv.domain.member.Member;
import nt.greenenv.greenenv.domain.plant.Plant;
import nt.greenenv.greenenv.domain.shop.Product;
import nt.greenenv.greenenv.service.HomeService;
import nt.greenenv.greenenv.service.MemberService;
import nt.greenenv.greenenv.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final HomeService homeService;
    private final ShopService shopService;
    private final MemberService memberService;

    @Autowired
    public HomeController(HomeService homeService, ShopService shopService, MemberService memberService) {
        this.homeService = homeService;
        this.shopService = shopService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal Member member, Model model) {

        List<Product> productList = shopService.get4products();
        model.addAttribute("productList", productList);


        if (member != null) {
            model.addAttribute("member", member.checkBirthday());
        }
        return "/home/home";
    }

    @PostMapping("/search")
    public String searchPage(@RequestParam("search") String search,
                             Model model) {
        List<Product> productList = homeService.searchProduct(search);
        List<Plant> plantList = homeService.searchPlant(search);
        model.addAttribute("productList", productList);
        model.addAttribute("plantList", plantList);
        model.addAttribute("search", search);
        return "/home/searchResult";
    }
}
