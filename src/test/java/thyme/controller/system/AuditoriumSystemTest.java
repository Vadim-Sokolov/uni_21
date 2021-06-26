package thyme.controller.system;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.hamcrest.Matchers;

import thyme.controller.rest.AuditoriumControllerRest;
import thyme.model.Auditorium;
import thyme.service.AuditoriumService;

@SpringBootTest
class AuditoriumSystemTest {

	@Autowired
	private AuditoriumControllerRest controller;
	@Autowired
	private AuditoriumService service;

	@BeforeAll
	@Sql("auditoriums.sql")
	public static void setup() {
	}

	@Test
	void getAuditoriumsTest() {
		// Given
		List<Auditorium> list = controller.getAuditoriums();
		// When
		// Then
		assertTrue(list.size() >= 2);
	}
}
