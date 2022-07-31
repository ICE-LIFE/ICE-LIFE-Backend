package life.inha.icemarket.controller;

import life.inha.icemarket.dto.UserDto;
import life.inha.icemarket.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/amdin")
public class AdminController {
    private AdminService adminService;

    @GetMapping("/admin/userList")
    public ModelAndView home(Model model) throws Exception {
        UserDto dto = new UserDto();
        ModelAndView mav = new ModelAndView();
        List<UserDto> list = adminService.list(dto);

        mav.setViewName("list");
        mav.addObject("list", list);

        return mav;
    }





}


