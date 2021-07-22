package com.amsdams.filevalidation;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("resource")
public class ResourceController {

	private static final String NAME_MEDIATYPE = "{name}/media-type";
	private static final String NAME_CODECTYPES = "{name}/codec-types";

	@Autowired
	ResourceService resourceService;

	@RequestMapping("/hello")
	public String home() {
		return "Hello Docker World";
	}
	
	@GetMapping(path = "/", produces = "application/json")
	public List<com.amsdams.filevalidation.Resource> getResources() throws IOException {
		return resourceService.getResources();
	}
	@GetMapping(path = NAME_MEDIATYPE, produces = "application/json")
	public MediaType getMediaType(@PathVariable String name) throws IOException {
		return resourceService.getMediaType(name);
	}

	@GetMapping(path = NAME_CODECTYPES, produces = "application/json")
	public List<CodecType> getCodecTypes(@PathVariable String name) throws IOException {
		return resourceService.getCodecTypes(name);
	}
}
