package ru.practicum.ewm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAllByInitiator_Id(long id, Pageable pageable);

    @Query("Select e from Event e " +
            "where (e.initiator.id in :users)" +
            " and (e.state in :state or :state is null) " +
            "and (e.category.id in (:category) or :category is null)" +
            "and e.eventDate >= :startdate " +
            "and e.eventDate <= :enddate")
    Page<Event> allEventsByUserWithEndDate(@Param("users") List<Long> users,
                                @Param("state") List<EventState> state,
                                @Param("category") List<Long> category,
                                @Param("startdate") LocalDateTime rangeStart,
                                @Param("enddate") LocalDateTime rangeEnd, Pageable pageable);

    @Query("Select e from Event e " +
            "where (e.initiator.id in :users)" +
            " and (e.state in :state or :state is null) " +
            "and (e.category.id in (:category) or :category is null)" +
            "and e.eventDate >= :startdate ")
    Page<Event> allEventsByUser(@Param("users") List<Long> users,
                                @Param("state") List<EventState> state,
                                @Param("category") List<Long> category,
                                @Param("startdate") LocalDateTime rangeStart,
                                Pageable pageable);


    @Query("Select e from Event e " +
            "where (UPPER(e.annotation) LIKE UPPER(:text) or e.description LIKE UPPER(:text) or :text is null)" +
            "and (e.category.id in (:category) or :category is null)" +
            " and (e.paid = :paid or :paid = false) " +
            "and e.eventDate >= :startdate " +
            "and e.state = 'PUBLISHED'")
    Page<Event> allEventsShort(@Param("text") String text,
                               @Param("category") List<Long> category,
                               @Param("paid") boolean paid,
                               @Param("startdate") LocalDateTime rangeStart, Pageable pageable);

    @Query("Select e from Event e " +
            "where (UPPER(e.annotation) LIKE UPPER(:text) or e.description LIKE UPPER(:text) or :text is null)" +
            "and (e.category.id in (:category) or :category is null)" +
            " and (e.paid = :paid or :paid = false) " +
            "and e.eventDate >= :startdate " +
            "and (e.eventDate <= :enddate)" +
            "and e.state = 'PUBLISHED'")
    Page<Event> allEventsShortWithEndDate(@Param("text") String text,
                               @Param("category") List<Long> category,
                               @Param("paid") boolean paid,
                               @Param("startdate") LocalDateTime rangeStart,
                               @Param("enddate") LocalDateTime rangeEnd, Pageable pageable);
}
