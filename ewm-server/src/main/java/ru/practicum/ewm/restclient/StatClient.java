package ru.practicum.ewm.restclient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm.model.Stats;
import ru.practicum.ewm.model.dto.HitDto;

@Slf4j
public class StatClient {
    private final static String STATSERVERPATH = "http://localhost:9090";

    public static List<Stats> getStat(LocalDateTime start, LocalDateTime end, String uris, Boolean unique) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );

        log.info(parameters.toString());
        List<Stats> statsList = new ArrayList<>();

        return restTemplate.getForObject(STATSERVERPATH + "/stats", statsList.getClass(), parameters);
    }

    public static void postHit(HitDto hitDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<HitDto> requestBody = new HttpEntity<>(hitDto, headers);
        HitDto hitDtoResp = restTemplate.postForObject(STATSERVERPATH + "/hit", requestBody, HitDto.class);

        if (hitDtoResp != null) {
            log.info("Статистика сохранена");
        } else {
            log.info("Ошибка сохранения статистики");
        }
    }
}
