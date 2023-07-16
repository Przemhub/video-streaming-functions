package com.example.videostreamfunction;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpRequest.HttpPart;
import com.google.cloud.functions.HttpResponse;
import com.google.cloud.storage.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;


public class PostFilm implements HttpFunction {

    private static final Logger logger = LoggerFactory.getLogger(PostFilm.class);
    private static final String GCLOUD_BUCKET = "video-streaming-functions-bucket";
    private final Storage storage;

    public PostFilm() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {

        if (!"POST".equals(request.getMethod())) {
            response.setStatusCode(HttpURLConnection.HTTP_BAD_METHOD);
            return;
        }
        Map<String, HttpPart> parts = request.getParts();
        Map<String, List<String>> queryParams = request.getQueryParameters();
        HttpPart videoFilePart = parts.get("video");

        if (videoFilePart == null || queryParams.get("description").isEmpty()) {
            response.setStatusCode(HttpURLConnection.HTTP_NO_CONTENT, "Missing request resources");
            return;
        }

        try {
            Bucket bucket = storage.get(GCLOUD_BUCKET);
            if (bucket == null) {
                logger.warn("Bucket not found creating new bucket: " + GCLOUD_BUCKET);
                storage.create(BucketInfo.newBuilder(GCLOUD_BUCKET).setLocation("europe-central2").build()).wait();
            }

            String videoDescription = queryParams.get("description").get(0);

            Blob videoBlob = bucket.create(videoFilePart.getFileName().get(), videoFilePart.getInputStream());
            BlobInfo updatedBlobInfo = BlobInfo.newBuilder(videoBlob.getBlobId())
                    .setMetadata(Map.of("description",videoDescription))
                    .build();
            storage.update(updatedBlobInfo);
            response.setStatusCode(HttpURLConnection.HTTP_CREATED);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            response.setStatusCode(HttpURLConnection.HTTP_INTERNAL_ERROR, e.getMessage());
        }
    }


}
