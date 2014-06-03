package com.bjss.waitrose.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import com.bjss.waitrose.domain.persistence.Store;
import com.bjss.waitrose.service.StoreService;
import com.bjss.waitrose.util.StoreUtil;

@RunWith(MockitoJUnitRunner.class)
public class StoreControllerTest {
	@Mock
	private StoreService storeService;

	private StoreController storeController;

	@Before
	public void setUp() throws Exception {
		storeController = new StoreController(storeService);
	}

	@Test
	public void shouldGetBranchListPage() {
		List<Store> branchList = stubServiceToReturnExistingBranches(5);
		ModelAndView view = storeController.getListBranchesView();
		// verify StoreService was called once
		verify(storeService, times(1)).getAllStores();
		assertEquals("View name should be right", "store_list", view.getViewName());
	/*	assertEquals(	"Model should contain attribute with the list of branches coming from the service",
									branchList, view.getModel().get("stores"));*/
	}

	private List<Store> stubServiceToReturnExistingBranches(int howMany) {
		List<Store> branchList = StoreUtil.createStores(howMany);
		when(storeService.getAllStores()).thenReturn(branchList);
		return branchList;
	}
}
