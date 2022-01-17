package com.kry.livi.service;

import com.kry.livi.domain.Resource;
import com.kry.livi.domain.Status;
import com.kry.livi.repository.ResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
public class ConnectionService {
    private final ResourceRepository repository;
    private final HttpClient httpClient;

    public ConnectionService(ResourceRepository repository, HttpClient httpClient) {
        this.repository = repository;
        this.httpClient = httpClient;
    }

    @Scheduled(fixedDelay = 2000)
    public void checkUrls() {
        List<Resource> resources = repository.findAll();
        for (Resource resource : resources) {
            try {
                checkStatusForUrl(resource);
            } catch (URISyntaxException e) {
                log.error("Invalid url: {}", resource.getUrl(), e);
            }
        }
    }

    private void checkStatusForUrl(final Resource resource) throws URISyntaxException {
        HttpRequest request = getRequest(resource);
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() >= 200 && response.statusCode() < 400) {
                        return Status.OK;
                    }
                    return Status.FAIL;
                })
                .exceptionally(t -> Status.FAIL)
                .thenAccept(status -> {
                    resource.setStatus(status.name());
                    repository.save(resource);
                });
    }

    private HttpRequest getRequest(Resource resource) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(resource.getUrl()))
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .timeout(Duration.of(1, ChronoUnit.SECONDS))
                .build();
    }
}
