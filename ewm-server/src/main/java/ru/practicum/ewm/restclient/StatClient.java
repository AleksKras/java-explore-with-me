
package ru.practicum.ewm.restclient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm.model.Stats;
import ru.practicum.ewm.model.dto.HitDto;

@Slf4j
@AllArgsConstructor
@Component
public class StatClient {

    private final Environment environment;


    public List<Stats> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );

        log.info(parameters.toString());
        List<Stats> statsList = new ArrayList<>();
        String statServerPath = environment.getProperty("spring.server.url");
        return restTemplate.getForObject(statServerPath + "/stats", statsList.getClass(), parameters);
    }

    public void postHit(HitDto hitDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<HitDto> requestBody = new HttpEntity<>(hitDto, headers);
        String statServerPath = environment.getProperty("spring.server.url");
        HitDto hitDtoResp = restTemplate.postForObject(statServerPath + "/hit", requestBody, HitDto.class);

        if (hitDtoResp != null) {
            log.info("Статистика сохранена");
        } else {
            log.info("Ошибка сохранения статистики");
        }
    }
}
