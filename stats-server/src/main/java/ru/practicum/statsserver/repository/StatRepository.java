package ru.practicum.statsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.statsserver.model.Hit;
import ru.practicum.statsserver.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<Hit, Long> {

    @Query(value = " select h.app as app, h.uri as uri, count(*) as hits from hits h " +
            "where h.uri in (:uris) and h.created between :startDate and :endDate " +
            "group by h.uri, h.app",
            nativeQuery = true)
    List<Stats> getStat(@Param("uris") String uris,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

    @Query(value = " select h.app as app, h.uri  as uri, count(h.ip) as hits from hits h " +
            "where h.uri in (:uris) and h.created between :startDate and :endDate " +
            "group by h.ip, h.uri, h.app",
            nativeQuery = true)
    List<Stats> getUniqueStat(@Param("uris") String uris,
                              @Param("startDate") LocalDateTime startDate,
                              @Param("endDate") LocalDateTime endDate);

}
