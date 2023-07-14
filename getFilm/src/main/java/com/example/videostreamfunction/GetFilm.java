package com.example.videostreamfunction;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.cloud.storage.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

public class GetFilm implements HttpFunction {
    private static final Logger logger = LoggerFactory.getLogger(GetFilm.class);
    private static final String GCLOUD_BUCKET = "video-streaming-functions-bucket";
    private final Storage storage;


    public GetFilm() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {

        if (!"GET".equals(request.getMethod())) {
            response.setStatusCode(HttpURLConnection.HTTP_BAD_METHOD);
            return;
        }
        Map<String, List<String>> queryParams = request.getQueryParameters();
        if (queryParams.get("videoName").isEmpty()) {
            response.setStatusCode(HttpURLConnection.HTTP_NO_CONTENT, "Missing request resources");
            return;
        }

        Bucket bucket = storage.get(GCLOUD_BUCKET);
        if (bucket == null) {
            logger.warn("Bucket not found creating new bucket: " + GCLOUD_BUCKET);
            storage.create(BucketInfo.of(GCLOUD_BUCKET));
        }

        String videoName = queryParams.get("videoName").get(0);
        logger.error(videoName);
        Blob videoBlob = bucket.get(videoName);
        response.setContentType("video/mp4");
        response.appendHeader("Content-Disposition", "attachment; filename=\"" + videoName + "\"");
        response.appendHeader("Connection", "keep-alive");

        streamResource(videoBlob,response);
    }

    private void streamResource(Blob videoBlob,HttpResponse response) throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(videoBlob.getContent());
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[10000];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

}
