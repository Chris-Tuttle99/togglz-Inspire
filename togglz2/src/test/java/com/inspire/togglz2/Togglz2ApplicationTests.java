package com.inspire.togglz2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire.togglz2.controller.TogglzControllerTest;
import com.inspire.togglz2.entity.Product;
import com.inspire.togglz2.service.ProductService;
import com.inspire.togglz2.service.ProductServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
class Togglz2ApplicationTests {
	@Autowired
	private MockMvc mockMvc;

//	@Mock
//	private ProductServiceImpl productService;

//	@InjectMocks
//	private TogglzControllerTest togglzControllerTest;

	List<Product> prods=Stream.of(new Product(1, "Pizza", 12.00),
                new Product(2,"Garlic Bread",4.50)).collect(Collectors.toList());

//	@Before
//	public void setUp(){
//		MockitoAnnotations.openMocks(this);
//		this.mockMvc= MockMvcBuilders.standaloneSetup(togglzControllerTest).build();
//	}

	/*
	Tips:
	@BeforeEach
	To do before each test case
	 */

	ObjectMapper objectMapper=new ObjectMapper();

	@Test
	public void getProducts_noDiscount() throws Exception {
		//Setting Discount Feature flag to false
		String json="{\"name\":\"" +"DISCOUNT_APPLIED"+ "\",\"enabled\":\""+"false"+"\"}";
		this.mockMvc.perform(post("/actuator/togglz/DISCOUNT_APPLIED").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
//		ProductService productService= mock(ProductService.class);
//		when(productService.getProducts()).thenReturn(prods);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/products").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(4)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].productName",is("Hamburger")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].productPrice",is(8.0)));
	}

	@Test
	public void getProducts_DiscountAppliedFallback() throws Exception {
		//Setting Discount Feature flag to false
		String json="{\"name\":\"" +"DISCOUNT_APPLIED"+ "\",\"enabled\":\""+"true"+"\"}";
		this.mockMvc.perform(post("/actuator/togglz/DISCOUNT_APPLIED").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
//		ProductService productService= mock(ProductService.class);
//		when(productService.getProducts()).thenReturn(prods);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/products").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].productName",is("FallbackProduct")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].productPrice",is(0.0)));
	}


	@Test
	void contextLoads() {
	}



}
