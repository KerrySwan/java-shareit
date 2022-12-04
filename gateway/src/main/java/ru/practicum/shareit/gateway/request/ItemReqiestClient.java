package ru.practicum.shareit.gateway.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.gateway.client.BaseClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class ItemReqiestClient extends BaseClient {

    private static final String API_PREFIX = "/requests";

    @Autowired
    public ItemReqiestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> add(long requesterId, ItemRequestDto dto) {
        return post("", requesterId, dto);
    }

    public ResponseEntity<Object> getByUserId(long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> getAllAsPage(long userId, int from, int size) {
        Map<String, Object> params = new HashMap<>() {{
            put("from", from);
            put("size", size);
        }};
        return get("/all", userId, params);
    }

    public ResponseEntity<Object> getByRequestId(long userId, long requestId) {
        return get("/" + requestId, userId);
    }

}
