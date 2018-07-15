package com.sirtts.service;

import com.sirtts.service.dto.DentistNextVisitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DentistNextVisit.
 */
public interface DentistNextVisitService {

    /**
     * Save a dentistNextVisit.
     *
     * @param dentistNextVisitDTO the entity to save
     * @return the persisted entity
     */
    DentistNextVisitDTO save(DentistNextVisitDTO dentistNextVisitDTO);

    /**
     * Get all the dentistNextVisits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DentistNextVisitDTO> findAll(Pageable pageable);

    /**
     * Get all the dentistNextVisits by userid.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DentistNextVisitDTO> findAllByUserid(String[] userids, String startDate, String endDate, Pageable pageable);

    /**
     * Get the "id" dentistNextVisit.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DentistNextVisitDTO findOne(String id);

    /**
     * Delete the "id" dentistNextVisit.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
