package com.sirtts.service.impl;

import com.sirtts.security.SecurityUtils;
import com.sirtts.service.VsSpo2Service;
import com.sirtts.domain.VsSpo2;
import com.sirtts.repository.VsSpo2Repository;
import com.sirtts.service.dto.VsSpo2DTO;
import com.sirtts.service.mapper.VsSpo2Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


/**
 * Service Implementation for managing VsSpo2.
 */
@Service
public class VsSpo2ServiceImpl implements VsSpo2Service {

    private final Logger log = LoggerFactory.getLogger(VsSpo2ServiceImpl.class);

    private final VsSpo2Repository vsSpo2Repository;

    private final VsSpo2Mapper vsSpo2Mapper;

    public VsSpo2ServiceImpl(VsSpo2Repository vsSpo2Repository, VsSpo2Mapper vsSpo2Mapper) {
        this.vsSpo2Repository = vsSpo2Repository;
        this.vsSpo2Mapper = vsSpo2Mapper;
    }

    /**
     * Save a vsSpo2.
     *
     * @param vsSpo2DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VsSpo2DTO save(VsSpo2DTO vsSpo2DTO) {
        log.debug("Request to save VsSpo2 : {}", vsSpo2DTO);
        if(vsSpo2DTO.getUserid() == null){
            vsSpo2DTO.setUserid(SecurityUtils.getCurrentUserLogin().get().toString());
        }
        VsSpo2 vsSpo2 = vsSpo2Mapper.toEntity(vsSpo2DTO);
        vsSpo2 = vsSpo2Repository.save(vsSpo2);
        return vsSpo2Mapper.toDto(vsSpo2);
    }

    /**
     * Get all the vsSpo2S.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<VsSpo2DTO> findAll(Pageable pageable) {
        log.debug("Request to get all VsSpo2S");
        return vsSpo2Repository.findAll(pageable)
            .map(vsSpo2Mapper::toDto);
    }

    /**
     * Get all the vsSpo2S by userid.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<VsSpo2DTO> findAllByUserid(String[] userids, String startDate, String endDate, Pageable pageable) {
        log.debug("Request to get all VsSpo2S");
        if(userids == null) {
            userids = new String[1];
            userids[0] = SecurityUtils.getCurrentUserLogin().get();;
        }
        LocalDateTime start,end;
        if(startDate == null) start = LocalDateTime.ofEpochSecond(Integer.MIN_VALUE,0, ZoneOffset.UTC);
        else start = LocalDateTime.parse(startDate+"T00:00:00");
        if(endDate == null) end = LocalDateTime.ofEpochSecond(Integer.MAX_VALUE,0, ZoneOffset.UTC);
        else end  = LocalDateTime.parse(endDate+"T23:59:59");
        return vsSpo2Repository.findAllByUseridInAndAndMeasurmentdateBetweenOrderByMeasurmentdateDesc(userids, start, end, pageable)
            .map(vsSpo2Mapper::toDto);
    }

    /**
     * Get one vsSpo2 by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public VsSpo2DTO findOne(String id) {
        log.debug("Request to get VsSpo2 : {}", id);
        VsSpo2 vsSpo2 = vsSpo2Repository.findOne(id);
        return vsSpo2Mapper.toDto(vsSpo2);
    }

    /**
     * Delete the vsSpo2 by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete VsSpo2 : {}", id);
        vsSpo2Repository.delete(id);
    }
}
