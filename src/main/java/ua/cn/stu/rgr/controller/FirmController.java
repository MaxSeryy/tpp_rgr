package ua.cn.stu.rgr.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import ua.cn.stu.rgr.entity.Firm;
import ua.cn.stu.rgr.service.FirmService;

@Controller
@RequestMapping("/firms")
@RequiredArgsConstructor
@Slf4j
public class FirmController {
    
    private final FirmService firmService;

    @GetMapping
    public String getAllFirms(Model model) {
        log.info("Fetching all firms");
        model.addAttribute("firms", firmService.getAll());
        return "firms/firms";
    }

    @GetMapping("/{id}")
    public String getFirm(@PathVariable Long id, Model model) {
        log.info("Fetching firm with id: {}", id);
        model.addAttribute("firm", firmService.getById(id));
        return "firms/firm-detail";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String newFirmForm(Model model) {
        log.info("Opening new firm form");
        model.addAttribute("firm", new Firm());
        return "firms/form";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String createFirm(@Valid @ModelAttribute Firm firm, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Validation errors while creating firm");
            return "firms/form";
        }
        log.info("Creating new firm: {}", firm.getName());
        firmService.create(firm);
        return "redirect:/firms";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editFirmForm(@PathVariable Long id, Model model) {
        log.info("Opening edit form for firm id: {}", id);
        model.addAttribute("firm", firmService.getById(id));
        return "firms/form";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateFirm(@PathVariable Long id, @Valid @ModelAttribute Firm firm, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Validation errors while updating firm id: {}", id);
            return "firms/form";
        }
        log.info("Updating firm id: {}", id);
        firmService.update(id, firm);
        return "redirect:/firms/" + id;
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteFirm(@PathVariable Long id) {
        log.info("Deleting firm id: {}", id);
        firmService.delete(id);
        return "redirect:/firms";
    }
}