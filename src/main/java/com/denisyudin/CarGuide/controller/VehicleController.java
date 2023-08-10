package com.denisyudin.CarGuide.controller;

import com.denisyudin.CarGuide.entity.Category;
import com.denisyudin.CarGuide.entity.TransportType;
import com.denisyudin.CarGuide.rest.dto.VehicleRqDto;
import com.denisyudin.CarGuide.rest.dto.VehicleRsDto;
import com.denisyudin.CarGuide.rest.exceptions.BadRequestException;
import com.denisyudin.CarGuide.service.CategoryService;
import com.denisyudin.CarGuide.service.RegistrationNumberService;
import com.denisyudin.CarGuide.service.TransportTypeService;
import com.denisyudin.CarGuide.service.VehicleService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VehicleController {
    private final CategoryService categoryService;
    private final VehicleService vehicleService;
    private final TransportTypeService transportTypeService;
    private final RegistrationNumberService registrationNumberService;
    private Boolean isError = false;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping ("/update")
    public String viewUpdatePage(Model model, @RequestParam Long id){
        VehicleRsDto vehicleRsDto = vehicleService.getById(id);
        model.addAttribute("id", id);
        model.addAttribute("vehicle",vehicleRsDto);
        return "update";
    }

    @PatchMapping("/vehicle/{id}")
    public String updateVehicle(@ModelAttribute VehicleRqDto vehicleRqDto,
                                @PathVariable Long id) {
        if (vehicleRqDto.getIsTrailerAvailable() == null) {
            vehicleRqDto.setIsTrailerAvailable(false);
        }
        vehicleService.update(id, vehicleRqDto);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String viewAddPage(Model model) {
        return "add";
    }

    @PostMapping("/vehicle/add")
    public String addNewVehicle(@ModelAttribute VehicleRqDto vehicleRqDto, Model model){
        try {
            registrationNumberService.isAlreadyExists(vehicleRqDto.getRegistrationNumber());
        }
        catch (BadRequestException e) {
            model.addAttribute("errorNumExists", true);
            return "add";
        }
        if (vehicleRqDto.getIsTrailerAvailable() == null) {
            vehicleRqDto.setIsTrailerAvailable(false);
        }
        vehicleService.create(vehicleRqDto);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchVehicle(@ModelAttribute VehicleRqDto vehicleRqDto, Model model) {
        System.out.println("parameteres: " + vehicleRqDto.toString());
        List<VehicleRsDto> res = vehicleService.searchVehicles(vehicleRqDto);
        System.out.println("search res: " + res.toString());
        model.addAttribute("vehicles", res);

        return "index";
    }

    @ModelAttribute("vehicles")
    private List<VehicleRsDto> populateVehicles() {
        return vehicleService.getAll();
    }

    @ModelAttribute("categories")
    private List<Category> populateCategories() {
        return categoryService.getAll();
    }

    @ModelAttribute("transportTypes")
    private List<TransportType> populateTransportTypes() {
        return transportTypeService.getAll();
    }
}
