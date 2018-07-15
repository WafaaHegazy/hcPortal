package com.sirtts.service.impl;

import com.sirtts.security.SecurityUtils;
import com.sirtts.service.DiabetesSugarTestService;
import com.sirtts.domain.DiabetesSugarTest;
import com.sirtts.repository.DiabetesSugarTestRepository;
import com.sirtts.service.dto.DiabetesSugarTestDTO;
import com.sirtts.service.mapper.DiabetesSugarTestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Service Implementation for managing DiabetesSugarTest.
 */
@Service
public class DiabetesSugarTestServiceImpl implements DiabetesSugarTestService {

    private final Logger log = LoggerFactory.getLogger(DiabetesSugarTestServiceImpl.class);

    private final DiabetesSugarTestRepository diabetesSugarTestRepository;

    private final DiabetesSugarTestMapper diabetesSugarTestMapper;

    public DiabetesSugarTestServiceImpl(DiabetesSugarTestRepository diabetesSugarTestRepository, DiabetesSugarTestMapper diabetesSugarTestMapper) {
        this.diabetesSugarTestRepository = diabetesSugarTestRepository;
        this.diabetesSugarTestMapper = diabetesSugarTestMapper;
    }

    /**
     * Save a diabetesSugarTest.
     *
     * @param diabetesSugarTestDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DiabetesSugarTestDTO save(DiabetesSugarTestDTO diabetesSugarTestDTO) {
        log.debug("Request to save DiabetesSugarTest : {}", diabetesSugarTestDTO);
        if(diabetesSugarTestDTO.getUserid() == null){
            diabetesSugarTestDTO.setUserid(SecurityUtils.getCurrentUserLogin().get().toString());
        }
        DiabetesSugarTest diabetesSugarTest = diabetesSugarTestMapper.toEntity(diabetesSugarTestDTO);
        diabetesSugarTest = diabetesSugarTestRepository.save(diabetesSugarTest);
        return diabetesSugarTestMapper.toDto(diabetesSugarTest);
    }

    /**
     * Get all the diabetesSugarTests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<DiabetesSugarTestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DiabetesSugarTests");
        return diabetesSugarTestRepository.findAll(pageable)
            .map(diabetesSugarTestMapper::toDto);
    }

    /**
     * Get all the diabetesSugarTests by userid.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<DiabetesSugarTestDTO> findAllByUserid(String[] userids, String startDate, String endDate, Pageable pageable) {
        log.debug("Request to get all DiabetesSugarTests");
        if(userids == null) {
            userids = new String[1];
            userids[0] = SecurityUtils.getCurrentUserLogin().get();;
        }
        LocalDateTime start,end;
        if(startDate == null) start = LocalDateTime.ofEpochSecond(Integer.MIN_VALUE,0, ZoneOffset.UTC);
        else start = LocalDateTime.parse(startDate+"T00:00:00");
        if(endDate == null) end = LocalDateTime.ofEpochSecond(Integer.MAX_VALUE,0, ZoneOffset.UTC);
        else end  = LocalDateTime.parse(endDate+"T23:59:59");
        return diabetesSugarTestRepository.findAllByUseridInAndAndMeasurmentdateBetweenOrderByMeasurmentdateDesc(userids,start, end, pageable)
            .map(diabetesSugarTestMapper::toDto);
    }

    /**
     * Get one diabetesSugarTest by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public DiabetesSugarTestDTO findOne(String id) {
        log.debug("Request to get DiabetesSugarTest : {}", id);
        DiabetesSugarTest diabetesSugarTest = diabetesSugarTestRepository.findOne(id);
        return diabetesSugarTestMapper.toDto(diabetesSugarTest);
    }

    /**
     * Delete the diabetesSugarTest by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete DiabetesSugarTest : {}", id);
        diabetesSugarTestRepository.delete(id);
    }
}
