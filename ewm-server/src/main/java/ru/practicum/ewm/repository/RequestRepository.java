package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.Request;
import ru.practicum.ewm.model.RequestStatus;
import ru.practicum.ewm.model.User;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Integer countAllByStatusIsAndEvent_id(RequestStatus status, long eventId);

    List<Request> findAllByRequesterIs(User user);

    List<Request> findAllByEvent_id(long eventId);

    Integer countAllByRequester_IdAndEvent_id(long userId, long eventId);

}
