package com.amsdams.filevalidation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("resource")
public class ResourceServiceProperties {

	@Value( "${pathFFprobe}")
	String pathFFprobe;
	
	@Value( "${pathResources}")
	String pathResources;
	
}
