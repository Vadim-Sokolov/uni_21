package thyme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import thyme.controller.thyme.AuditoriumControllerThyme;
import thyme.model.Auditorium;
import thyme.service.AuditoriumService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WebMvcTest(AuditoriumControllerThyme.class)
public class AuditoriumControllerRestAssuredTest {

	@MockBean
	private AuditoriumService service;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() {
		RestAssuredMockMvc.mockMvc(mockMvc);
	}
	
	@Test
	void getAuditoriumsTest() {
		ArrayList<Auditorium> expected = new ArrayList<Auditorium>();
		Auditorium auditorium = new Auditorium();
		auditorium.setId(1);
		auditorium.setName("A1");
		auditorium.setCapacity(100);
		expected.add(auditorium);
		
		when(service.getAuditoriums()).thenReturn(expected);
		
		RestAssuredMockMvc.when().get("/auditoriums")
		.then()
		.statusCode(200);
		//.body("$.size()", Matchers.equalTo(3))
		//.body("$.get(0).getId()", Matchers.equalTo(1));
	}
}
