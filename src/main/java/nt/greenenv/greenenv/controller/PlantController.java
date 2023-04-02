package nt.greenenv.greenenv.controller;

import nt.greenenv.greenenv.domain.member.Member;
import nt.greenenv.greenenv.domain.plant.Plant;
import nt.greenenv.greenenv.domain.plant.PlantFilter;
import nt.greenenv.greenenv.domain.plant.SelfPlant;
import nt.greenenv.greenenv.domain.plant.SelfPlantNewForm;
import nt.greenenv.greenenv.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.*;

@Controller
public class PlantController {

    private PlantService service;

    @Autowired
    public PlantController(PlantService service) {
        this.service = service;
    }

    @GetMapping("/plant")
    public String plantHomePage(Model model) {
        List<Plant> plantList = service.getAllPlants();
        model.addAttribute("plantList", plantList);
        model.addAttribute("plantFilter", new PlantFilter());

        return "/plant/plantHome";
    }

    @GetMapping("/plant/list")
    public String showAllSelfPlant(@AuthenticationPrincipal Member member, Model model) {
        if(member == null) return "redirect:/member/login";

        List<SelfPlant> plantList = service.getMemberSelfPlants(member.getId());
        model.addAttribute("member", member);
        model.addAttribute("plantList", plantList);

        return "/plant/plantList";
    }

    @GetMapping("/plant/info/{no}")
    public String showPlantDetailInfo(@PathVariable("no") long no,
                                      Model model) {
        model.addAttribute("plant", service.getPlantById(no));
        return "/plant/plantInfo";
    }

    @GetMapping("/plant/watercheck/{no}")
    public String waterCheck(@PathVariable("no") long no,
                             @AuthenticationPrincipal Member member) {
        if(member==null) return "redirect:/member/login";

        service.waterCheck(no, LocalDate.now());
        return "redirect:/plant/list";
    }

    @GetMapping("/plant/add")
    public String selfPlantAddPage(@AuthenticationPrincipal Member member,
                                   Model model) {
        if (member == null) return "redirect:/member/login";

        model.addAttribute("platList", service.getAllPlants());
        return "/plant/plantAddPage";
    }

    @PostMapping("/plant/add")
    public String selfPlantAdd(@AuthenticationPrincipal Member member,
                               SelfPlantNewForm form) {
        service.addNewSelfPlant(form, member.getId());
        return "redirect:/plant/list";
    }

    @PostMapping("/plant")
    public String plantFilterPage(@ModelAttribute PlantFilter filter,
                                  Model model) {
        Set<Plant> plantList = null;

        if (filter.getOperation() == null) {
            filter.setOperation("or");
        }
        if(filter.getOperation().equalsIgnoreCase("and")) {
            plantList = service.filteredPlantsAndOp(filter);
        }
        else {
            plantList = service.filteredPlantsOrOp(filter);
        }

        model.addAttribute("plantList", plantList);
        model.addAttribute("plantFilter", filter);

        return "/plant/plantHome";
    }

    @ModelAttribute("levels")
    public List<String> LevelForm() {
        /*Map<String, String> levels = new LinkedHashMap<>();
        levels.put("매우 낮음", "VL");
        levels.put("낮음", "L");
        levels.put("보통", "M");
        levels.put("높음", "H");
        levels.put("매우 높음", "VH");*/
        List<String> levels = new ArrayList<>();
        levels.add("매우 낮음");
        levels.add("낮음");
        levels.add("보통");
        levels.add("높음");
        levels.add("매우 높음");

        return levels;
    }

    @ModelAttribute("speeds")
    public List<String> SpeedForm() {
        /*Map<String, String> levels = new LinkedHashMap<>();
        levels.put("매우 느림", "VL");
        levels.put("느림", "L");
        levels.put("보통", "M");
        levels.put("빠름", "H");
        levels.put("매우 빠름", "VH");*/
        List<String> levels = new ArrayList<>();
        levels.add("매우 느림");
        levels.add("느림");
        levels.add("보통");
        levels.add("빠름");
        levels.add("매우 빠름");
        return levels;
    }

    @ModelAttribute("seasons")
    public List<String> SeasonForm() {
        /*Map<String, String> levels = new LinkedHashMap<>();
        levels.put("초봄", "past_spring");
        levels.put("봄", "spring");
        levels.put("늦봄", "late_spring");
        levels.put("초여름", "past_summer");
        levels.put("여름", "summer");
        levels.put("가을", "spring");
        levels.put("늦가을", "late_spring");
        levels.put("겨울", "winter");*/
        List<String> levels = new ArrayList<>();
        levels.add("초봄");
        levels.add("봄");
        levels.add("늦봄");
        levels.add("초여름");
        levels.add("여름");
        levels.add("가을");
        levels.add("늦가을");
        levels.add("겨울");
        return levels;
    }

    @ModelAttribute("bugs")
    public List<String> BugForm() {
        List<String> levels = new ArrayList<>();
        levels.add("응애");
        levels.add("깍지벌레");
        levels.add("탄저병");
        levels.add("개각충");
        levels.add("진딧물");
        levels.add("곰팡이병");
        levels.add("온실가루이");
        levels.add("백수병");
        levels.add("팁번");
        levels.add("흰가루병");
        levels.add("총채벌레");
        levels.add("복숭아혹");
        return levels;
    }

    @ModelAttribute("plantType")
    public List<String> TypeForm() {
        List<String> levels = new ArrayList<>();
        levels.add("공기정화");
        return levels;
    }

    @ModelAttribute("difficulty")
    public List<String> DifficultyForm() {
        List<String> levels = new ArrayList<>();
        levels.add("쉬움");
        levels.add("보통");
        levels.add("어려움");
        return levels;
    }

    @ModelAttribute("fert")
    public Map<String,List<Integer>> fertilizerForm() {
        Map<String, List<Integer>> levels = new LinkedHashMap<>();
        levels.put("비료 필요없음", Arrays.asList(0));
        levels.put("~ 2주", Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14));
        levels.put("2주 ~ 1달", Arrays.asList(14, 15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30));
        levels.put("1달 이상", Arrays.asList(30, 60, 90));
        return levels;
    }

    @ModelAttribute("operation")
    public List<String> OperForm() {
        List<String> levels = new ArrayList<>();
        levels.add("OR");
        levels.add("AND");
        return levels;
    }
}
