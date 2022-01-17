package com.kry.livi.service;

import com.kry.livi.domain.Resource;
import com.kry.livi.domain.Status;
import com.kry.livi.repository.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.kry.livi.utils.ResourceUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConnectionServiceTest {
    @Mock
    private ResourceRepository repository;
    @Mock
    private HttpClient httpClient;
    @Captor
    ArgumentCaptor<Resource> resourceCaptor;
    private ConnectionService connectionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        connectionService = new ConnectionService(repository, httpClient);
    }

    @Test
    public void checkUrls_responseOK() {
        //given
        Resource resource = getResource(LocalDateTime.now());
        List<Resource> resources = Collections.singletonList(resource);
        when(repository.findAll()).thenReturn(resources);
        HttpResponse httpResponse = getHttpResponse(200);
        CompletableFuture response = CompletableFuture.completedFuture(httpResponse);
        when(httpClient.sendAsync(any(), any())).thenReturn(response);

        //when
        connectionService.checkUrls();

        //then
        verify(repository).findAll();
        verify(repository).save(resourceCaptor.capture());
        Resource arg = resourceCaptor.getValue();
        assertEquals(Status.OK.name(), arg.getStatus());
        assertEquals(RESOURCE_NAME, arg.getName());
        assertEquals(RESOURCE_URL, arg.getUrl());
    }

    @Test
    public void checkUrls_responseFAIL() {
        //given
        Resource resource = getResource(LocalDateTime.now());
        List<Resource> resources = Collections.singletonList(resource);
        when(repository.findAll()).thenReturn(resources);
        HttpResponse httpResponse = getHttpResponse(500);
        CompletableFuture response = CompletableFuture.completedFuture(httpResponse);
        when(httpClient.sendAsync(any(), any())).thenReturn(response);

        //when
        connectionService.checkUrls();

        //then
        verify(repository).findAll();
        verify(repository).save(resourceCaptor.capture());
        Resource arg = resourceCaptor.getValue();
        assertEquals(Status.FAIL.name(), arg.getStatus());
        assertEquals(RESOURCE_NAME, arg.getName());
        assertEquals(RESOURCE_URL, arg.getUrl());
    }


    private HttpResponse getHttpResponse(int statusCode) {
        return new HttpResponse() {
            @Override
            public int statusCode() {
                return statusCode;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public Object body() {
                return null;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };
    }
}