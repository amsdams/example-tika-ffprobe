package com.amsdams.filevalidation.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class ResourceControllerIT {
	private static final String RESOURCE_MAPPING = "/resource/";
	@Autowired
	MockMvc mockMvc;

	@Test
	void doHome() throws Exception {
		mockMvc.perform(get(RESOURCE_MAPPING + "hello")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string("Hello Docker World"));
	}

	@Test
	void doGetResources() throws Exception {
		mockMvc.perform(get(RESOURCE_MAPPING)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("file_example_MP3_700KB.mp3"))
				.andExpect(jsonPath("$[0].mediaType.name").value("audio/mpeg"))
				.andExpect(jsonPath("$[0].codecType[0].name").value("mp3"))
				.andExpect(jsonPath("$[1].name").value("file_example_MP4_480_1_5MG.mp4"))
				.andExpect(jsonPath("$[1].mediaType.name").value("video/mp4"))
				.andExpect(jsonPath("$[1].codecType[0].name").value("h264"))
				.andExpect(jsonPath("$[1].codecType[1].name").value("aac"));

	}

	@Test
	void doGetMediaType_MP4() throws Exception {
		String name = "file_example_MP4_480_1_5MG.mp4";
		mockMvc.perform(get(RESOURCE_MAPPING + "{name}/media-type", name)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("video/mp4"));
	}

	@Test
	void doGetCodecTypes_MP4() throws Exception {
		String name = "file_example_MP4_480_1_5MG.mp4";
		mockMvc.perform(get(RESOURCE_MAPPING + "{name}/codec-types", name)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("h264")).andExpect(status().isOk())
				.andExpect(jsonPath("$[1].name").value("aac"));
	}

	@Test
	void doGetMediaType_MP3() throws Exception {
		String name = "file_example_MP3_700KB.mp3";
		mockMvc.perform(get(RESOURCE_MAPPING + "{name}/media-type", name)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("audio/mpeg"));
	}

	@Test
	void doGetCodecTypes_MP3() throws Exception {
		String name = "file_example_MP3_700KB.mp3";
		mockMvc.perform(get(RESOURCE_MAPPING + "{name}/codec-types", name)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("mp3"));
	}

}
