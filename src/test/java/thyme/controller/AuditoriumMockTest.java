package thyme.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import thyme.model.Auditorium;
import thyme.service.AuditoriumService;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(AuditoriumController.class)
public class AuditoriumMockTest {

	@MockBean
	private AuditoriumService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testWithMockMvc() throws Exception {
		ArrayList<Auditorium> expected = new ArrayList<Auditorium>();
		Auditorium auditorium = new Auditorium();
		auditorium.setId(1);
		auditorium.setName("A1");
		auditorium.setCapacity(100);
		expected.add(auditorium);

		when(service.getAuditoriums()).thenReturn(expected);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/auditoriums"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("auditorium/auditoriums"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("auditoriumList"));
		// .andExpect(MockMvcResultMatchers.model().attribute("auditoriumList", instanceOf(List.class)));
		// .andExpect(content().
	}
}
