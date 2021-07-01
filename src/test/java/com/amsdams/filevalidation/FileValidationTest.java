package com.amsdams.filevalidation;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;

@SpringBootTest
@Slf4j
public class FileValidationTest {
	// private FFmpeg ffmpeg;
	private FFprobe ffprobe;

	FileValidationTest() throws IOException {
		// ffmpeg = new FFmpeg("/usr/local/bin/ffmpeg");
		ffprobe = new FFprobe("/usr/local/bin/ffprobe");
	}

	@Autowired
	ResourcePatternResolver resourcePatternResolver;
///usr/local/bin/ffprobe

	public String extractContentUsingParser(InputStream stream) throws IOException, TikaException, SAXException {

		Parser parser = new AutoDetectParser();
		ContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		ParseContext context = new ParseContext();

		parser.parse(stream, handler, metadata, context);
		// log.info("handler.toString() {}", handler.toString());
		log.info("metadata.get(\"pdf:PDFVersion\") {}", metadata.get("pdf:PDFVersion"));
		log.info("metadata.toString() {}", metadata.toString());

		// log.info("context.toString() {}", context.toString());

		return handler.toString();
	}

	private MediaType getMediaType(InputStream inputStream) throws IOException {
		Detector detector = new DefaultDetector();
		Metadata metadata = new Metadata();
		BufferedInputStream bStream = new BufferedInputStream(inputStream);

		return detector.detect(bStream, metadata);

	}

	@Test
	void testFiles() throws IOException, TikaException, SAXException {
		Resource[] resources = resourcePatternResolver.getResources("classpath:data/*.*");
		for (Resource resource : resources) {
			log.info("resource {}", resource.getFilename());
			InputStream inputStream = resource.getInputStream();

			MediaType mediaType = getMediaType(inputStream);

			log.info("mediatype {}", mediaType);

			
			if (isAudioOrVideo( mediaType)) {
				printStreams(resource, ffprobe);
			};

			try {

				extractContentUsingParser(TikaInputStream.get(resource.getInputStream()));

			} catch (Exception e) {
				log.error("e {}", e.getMessage(), e);
			}

		}
	}

	private boolean isAudioOrVideo(MediaType mediaType) {

		return mediaType.getType().indexOf("audio") == 0 || mediaType.getType().indexOf("video") == 0;

	}

	private void printStreams(Resource resource, FFprobe ffprobe) throws IOException {
		FFmpegProbeResult probeResult = ffprobe.probe(resource.getFile().getAbsolutePath());
		FFmpegFormat format = probeResult.getFormat();
		log.info("File: '{}' ; Format: '{}' ; Duration: {}", format.filename, format.format_long_name, format.duration);

		List<FFmpegStream> streams = probeResult.getStreams();
		streams.stream().forEach(stream -> {
			log.info("Codec: '{}' ; Width: {} ; Height: {}", stream.codec_long_name, stream.width, stream.height);

		});

	}
}
