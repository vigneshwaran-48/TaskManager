package com.task.client.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.task.library.dto.NoDataResponse;
import com.task.library.exception.AppException;
import com.task.library.service.ExportImportService;

@Service
public class ExportImportServiceImpl implements ExportImportService {

    @Autowired
    private WebClient webClient;

    @Value("${app.resource.server.baseurl}")
    private String resourceServerBaseURL;

    private final static String BASE_PATH = "/api/v1/data";
    private final static String SLASH = "/";

    private final static Logger LOGGER = LoggerFactory.getLogger(ExportImportServiceImpl.class);

    @Override
    public Optional<byte[]> exportData(String userId) throws AppException {
        StringBuffer urlBuffer = new StringBuffer(resourceServerBaseURL);
        urlBuffer.append(SLASH).append(BASE_PATH).append(SLASH).append("export");

        byte[] data = webClient.get()
                            .uri(urlBuffer.toString())
                            .retrieve()
                            .bodyToMono(byte[].class)
                            .block();
        return Optional.of(data);
    }

    @Override
    public void importData(String userId, byte[] data) throws AppException {
        StringBuffer urlBuffer = new StringBuffer(resourceServerBaseURL);
        urlBuffer.append(SLASH).append(BASE_PATH).append(SLASH).append("import");

        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("data", data).filename("task_manager_export.data");

        NoDataResponse response = webClient
                                        .post()
                                        .uri(urlBuffer.toString())
                                        .contentType(MediaType.MULTIPART_FORM_DATA)
                                        .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                                        .retrieve()
                                        .bodyToMono(NoDataResponse.class)
                                        .block();

        LOGGER.info("Imported data response from resource server => {}", response);
    }
    
}
