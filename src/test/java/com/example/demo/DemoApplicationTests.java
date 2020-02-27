package com.example.demo;

import static org.hamcrest.Matchers.containsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DemoApplicationTests {
	@Autowired
	MockMvc mockMvc;

	@Test
	public void contextLoads() {
		assertThat(mockMvc).isNotNull();
	}
	
	@Test
	public void shouldReturnYesIfPathhExists() throws Exception {
		this.mockMvc
		.perform(get("/connected?origin=Boston&destination=Newark"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Yes")));
	}

	@Test
	public void shouldReturnNoIfPathDoesNotExists() throws Exception {
		this.mockMvc
		.perform(get("/connected?origin=Boston&destination=Albany"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("No")));
	}
}
