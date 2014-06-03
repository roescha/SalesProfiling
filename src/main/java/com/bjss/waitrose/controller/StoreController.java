package com.bjss.waitrose.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bjss.waitrose.service.StoreService;

@Controller
public class StoreController {

	private final StoreService storeService;

	@Inject
	public StoreController(StoreService branchService) {
		this.storeService = branchService;
	}

	@RequestMapping("/stores/all")
	public ModelAndView getListBranchesView() {
		ModelMap model = new ModelMap();
		model.addAttribute("stores", storeService.getAllStores());
		return new ModelAndView("store_list", model);
	}
}
