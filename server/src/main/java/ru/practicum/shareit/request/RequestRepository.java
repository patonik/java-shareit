package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<ItemRequest, Long> {
/*    @Query(value = "SELECT r.id, r.description, r.created, r.requester.id, r.items " +
        "from ItemRequest r where r.requester.id = :requesterId ")*/
    @Query(name = "request_query_dto", nativeQuery = true)
    List<ItemRequestDto> findAllByRequesterId(Long requesterId);


/*    @Query(value = "SELECT r.id, r.description, r.created, r.requester.id, r.items " +
        "from ItemRequest r where r.requester.id != :requesterId ")*/
    @Query(name = "request_query_dto_not", nativeQuery = true)
    List<ItemRequestDto> findAllByRequesterIdNot(Long requesterId);

}
