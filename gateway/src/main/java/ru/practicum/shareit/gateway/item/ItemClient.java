package ru.practicum.shareit.gateway.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.gateway.client.BaseClient;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ItemClient extends BaseClient {

    public static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addItem(long userId, ItemDto itemDto){
        return post("", userId, itemDto);
    };

    public ResponseEntity<Object> addComment(long userId, long itemId, CommentDto commentDto){
        return post("/" + itemId + "/comment", userId, commentDto);
    };

    public ResponseEntity<Object> updateItem(long userId, ItemDto itemDto){
        return patch("/" + itemDto.getId(), userId, itemDto);
    };

    public ResponseEntity<Object> getItem(long userid, long itemId){
        return get("/" + itemId, userid);
    };

    public ResponseEntity<Object> getAll(long userId){
        return get("", userId);
    };

    public ResponseEntity<Object> find(String pattern, int from, int size){
        Map<String, Object> params = new HashMap<>(){{
            put("text", pattern);
            put("from", from);
            put("size", size);
        }};
        return get("/search?text={text}&from={from}&size={size}", null, params);
    };





}
