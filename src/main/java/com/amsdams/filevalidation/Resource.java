package com.amsdams.filevalidation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Resource {
	String name;
	MediaType mediaType;
	List<CodecType> codecType;
}
