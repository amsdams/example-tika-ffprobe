package com.amsdams.filevalidation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ResourceControllerIT {
	private static final String RESOURCE_MAPPING = "/resource/";
	@Autowired
	MockMvc mockMvc;
	
	
	@Test
	void doHome() throws Exception {
		mockMvc.perform(get(RESOURCE_MAPPING+"hello")).andDo(print()).andExpect(status().isOk());		
	}
	
	@Test
	void doGetResources() throws Exception {
		mockMvc.perform(get(RESOURCE_MAPPING)).andDo(print()).andExpect(status().isOk());		
	}
	
	@Test
	void doGetMediaType() throws Exception {
		String name = "file_example_MP4_480_1_5MG.mp4";
		mockMvc.perform(get(RESOURCE_MAPPING+"{name}/media-type", name)).andDo(print()).andExpect(status().isOk());		
	}
	
	@Test
	void doGetCodecTypes() throws Exception {
		String name = "file_example_MP4_480_1_5MG.mp4";
		mockMvc.perform(get(RESOURCE_MAPPING+"{name}/codec-types", name)).andDo(print()).andExpect(status().isOk());		

	}
	
}
