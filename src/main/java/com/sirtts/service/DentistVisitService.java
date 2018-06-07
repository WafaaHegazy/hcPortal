package com.sirtts.service;

import com.sirtts.service.dto.DentistVisitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DentistVisit.
 */
public interface DentistVisitService {

    /**
     * Save a dentistVisit.
     *
     * @param dentistVisitDTO the entity to save
     * @return the persisted entity
     */
    DentistVisitDTO save(DentistVisitDTO dentistVisitDTO);

    /**
     * Get all the dentistVisits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DentistVisitDTO> findAll(Pageable pageable);

    /**
     * Get the "id" dentistVisit.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DentistVisitDTO findOne(String id);

    /**
     * Delete the "id" dentistVisit.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
