package com.ishimwe.digitalmarriagesystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/")
    public String dashboard() {
        return "forward:/dashboard.html";
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard.html";
    }
}
