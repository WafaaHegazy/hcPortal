package com.sirtts.service;

import com.sirtts.service.dto.DiabetesSugarTestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DiabetesSugarTest.
 */
public interface DiabetesSugarTestService {

    /**
     * Save a diabetesSugarTest.
     *
     * @param diabetesSugarTestDTO the entity to save
     * @return the persisted entity
     */
    DiabetesSugarTestDTO save(DiabetesSugarTestDTO diabetesSugarTestDTO);

    /**
     * Get all the diabetesSugarTests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DiabetesSugarTestDTO> findAll(Pageable pageable);

    /**
     * Get all the diabetesSugarTests by userid.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DiabetesSugarTestDTO> findAllByUserid(String[] userids, String startDate, String endDate, Pageable pageable);

    /**
     * Get the "id" diabetesSugarTest.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DiabetesSugarTestDTO findOne(String id);

    /**
     * Delete the "id" diabetesSugarTest.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
