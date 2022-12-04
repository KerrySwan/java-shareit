package ru.practicum.shareit.gateway.booking;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BookingClient extends BaseClient {

    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> add(long userId, BookingDtoIdOnly bookingDtoIdOnly) {
        return post("", userId, bookingDtoIdOnly);
    }

    public ResponseEntity<Object> changeStatus(long userId, long bookingId, boolean isApproved) {
        Map<String, Object> params = new HashMap<>(){{
            put("isApproved", isApproved);
        }};
        return patch("/" + bookingId + "?approved={isApproved}" , userId , params, null);
    }

    public ResponseEntity<Object> getByUserIdAndBookingId(long userId, long bookingId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> findAllByUserId(long userId, int from, int size) {
        Map<String, Object> params = new HashMap<>(){{
            put("from", from);
            put("size", size);
        }};
        return get("?from={from}&size={size}", userId, params);
    }

    public ResponseEntity<Object> findAllByOwnerId(long ownerId, int from, int size) {
        Map<String, Object> params = new HashMap<>(){{
            put("from", from);
            put("size", size);
        }};
        return get("/owner?from={from}&size={size}", ownerId, params);
    }

    public ResponseEntity<Object> getUserIdAndByState(long userId, String state, int from, int size) {
        Map<String, Object> params = new HashMap<>(){{
            put("state", state);
            put("from", from);
            put("size", size);
        }};
        return get("?state={state}&from={from}&size={size}", userId, params);
    }

    public ResponseEntity<Object> getOwnerIdAndByState(long ownerId, String state, int from, int size) {
        Map<String, Object> params = new HashMap<>(){{
            put("state", state);
            put("from", from);
            put("size", size);
        }};
        return get("/owner?state={state}&from={from}&size={size}", ownerId, params);
    }

}
