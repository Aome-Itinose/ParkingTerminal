package org.kanik.parkingterminal.store.repositories;

import org.kanik.parkingterminal.store.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {
}
