package com.amsdams.filevalidation;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;

@Service
@Slf4j
@Configuration
@EnableConfigurationProperties(ResourceServiceProperties.class)
public class ResourceService {

	public ResourceService(ResourceServiceProperties resourceServiceProperties) throws IOException {
		ffprobe = new FFprobe(resourceServiceProperties.getPathFFprobe());

	}

	private FFprobe ffprobe;

	/*
	 * @Autowired ResourcePatternResolver resourcePatternResolver;
	 */

	@Autowired
	FileService fileService;

	@Autowired
	ResourceServiceProperties resourceServiceProperties;

	public com.amsdams.filevalidation.MediaType getMediaType(String name) throws IOException {
		File resource = getResource(name);
		return getMediaType(resource);
	}

	private com.amsdams.filevalidation.MediaType getMediaType(File file) throws IOException {
		Detector detector = new DefaultDetector();
		Metadata metadata = new Metadata();
		FileInputStream fileInputStream = new FileInputStream(file);
		BufferedInputStream bStream = new BufferedInputStream(fileInputStream);
		com.amsdams.filevalidation.MediaType mediaType = new com.amsdams.filevalidation.MediaType();
		mediaType.setName(detector.detect(bStream, metadata).toString());
		return mediaType;
	}

	public List<CodecType> getCodecTypes(String name) throws IOException {
		File resource = getResource(name);
		return getCodecTypes(resource);
	}

	private List<CodecType> getCodecTypes(File file) throws IOException {
		FFmpegProbeResult probeResult = ffprobe.probe(file.getAbsolutePath());
		List<CodecType> codecTypes = new ArrayList<>();
		List<FFmpegStream> streams = probeResult.getStreams();
		streams.stream().forEach(stream -> {
			CodecType codecType = new CodecType();
			codecType.setName(stream.codec_name);
			codecTypes.add(codecType);

		});
		return codecTypes;
	}

	private File getResource(String name) {
		String location = resourceServiceProperties.getPathResources() + "/" + name;
		return fileService.getFile(location);

	}

	public List<com.amsdams.filevalidation.Resource> getResources() throws IOException {

		String pattern = resourceServiceProperties.getPathResources();
		List<File> files = fileService.getFiles(pattern);
		List<com.amsdams.filevalidation.Resource> myResources = new ArrayList<>();
		for (File file : files) {
			try {
				myResources.add(new com.amsdams.filevalidation.Resource(file.getName(), getMediaType(file),
						getCodecTypes(file)));
			} catch (Exception e) {
				log.error("err {}", e.getMessage());
			}
		}
		return myResources;
	}

}
