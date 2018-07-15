package com.sirtts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sirtts.service.DentistNextVisitService;
import com.sirtts.service.DentistVisitService;
import com.sirtts.service.dto.DentistNextVisitDTO;
import com.sirtts.web.rest.errors.BadRequestAlertException;
import com.sirtts.web.rest.util.HeaderUtil;
import com.sirtts.web.rest.util.PaginationUtil;
import com.sirtts.service.dto.DentistVisitDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DentistVisit.
 */
@RestController
@RequestMapping("/api")
public class DentistVisitResource {

    private final Logger log = LoggerFactory.getLogger(DentistVisitResource.class);

    private static final String ENTITY_NAME = "dentistVisit";

    private final DentistVisitService dentistVisitService;

    private final DentistNextVisitService dentistNextVisitService;

    public DentistVisitResource(DentistVisitService dentistVisitService, DentistNextVisitService dentistNextVisitService) {
        this.dentistVisitService = dentistVisitService;
        this.dentistNextVisitService = dentistNextVisitService;
    }

    /**
     * POST  /dentist-visits : Create a new dentistVisit.
     *
     * @param dentistVisitDTO the dentistVisitDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dentistVisitDTO, or with status 400 (Bad Request) if the dentistVisit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dentist-visits")
    @Timed
    public ResponseEntity<DentistVisitDTO> createDentistVisit(@Valid @RequestBody DentistVisitDTO dentistVisitDTO) throws URISyntaxException {
        log.debug("REST request to save DentistVisit : {}", dentistVisitDTO);
        if (dentistVisitDTO.getId() != null) {
            throw new BadRequestAlertException("A new dentistVisit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DentistVisitDTO result = dentistVisitService.save(dentistVisitDTO);
        return ResponseEntity.created(new URI("/api/dentist-visits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dentist-visits : Updates an existing dentistVisit.
     *
     * @param dentistVisitDTO the dentistVisitDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dentistVisitDTO,
     * or with status 400 (Bad Request) if the dentistVisitDTO is not valid,
     * or with status 500 (Internal Server Error) if the dentistVisitDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dentist-visits")
    @Timed
    public ResponseEntity<DentistVisitDTO> updateDentistVisit(@Valid @RequestBody DentistVisitDTO dentistVisitDTO) throws URISyntaxException {
        log.debug("REST request to update DentistVisit : {}", dentistVisitDTO);
        if (dentistVisitDTO.getId() == null) {
            return createDentistVisit(dentistVisitDTO);
        }
        DentistVisitDTO result = dentistVisitService.save(dentistVisitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dentistVisitDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dentist-visits : get all the dentistVisits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dentistVisits in body
     */
    @GetMapping("/dentist-visits")
    @Timed
    public ResponseEntity<List<DentistVisitDTO>> getAllDentistVisits(Pageable pageable) {
        log.debug("REST request to get a page of DentistVisits");
        Page<DentistVisitDTO> page = dentistVisitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dentist-visits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dentist-visits/byUserid : get all the dentistVisits by userid.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dentistVisits in body
     */
    @GetMapping("/dentist-visits/byUserid")
    @Timed
    public ResponseEntity<List<DentistVisitDTO>> getAllDentistVisitsByUserid(String[] userids, String startDate, String endDate, Pageable pageable) {
        log.debug("REST request to get a page of DentistVisits by userid");
        Page<DentistVisitDTO> page = dentistVisitService.findAllByUserid(userids, startDate, endDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dentist-visits/byUserid");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dentist-visits/:id : get the "id" dentistVisit.
     *
     * @param id the id of the dentistVisitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dentistVisitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dentist-visits/{id}")
    @Timed
    public ResponseEntity<DentistVisitDTO> getDentistVisit(@PathVariable String id) {
        log.debug("REST request to get DentistVisit : {}", id);
        DentistVisitDTO dentistVisitDTO = dentistVisitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dentistVisitDTO));
    }

    /**
     * DELETE  /dentist-visits/:id : delete the "id" dentistVisit.
     *
     * @param id the id of the dentistVisitDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dentist-visits/{id}")
    @Timed
    public ResponseEntity<Void> deleteDentistVisit(@PathVariable String id) {
        log.debug("REST request to delete DentistVisit : {}", id);
        dentistVisitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * GET Columns  /dentist-visits/:id : get dentist-visits columns with the next visit.
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/dentist-visits/columns")
    @Timed
    public ResponseEntity<List> getColumnsAndNextVisit() throws JSONException {
        log.debug("REST request to get columns");
        List response = dentistVisitService.findColumns();
        List<DentistNextVisitDTO> dentistNextVisit =  dentistNextVisitService.findAllByUserid(null, null, null, new PageRequest(0,1))
            .getContent();
        if(!dentistNextVisit.isEmpty()){
            response.add(dentistNextVisit.get(0));
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(response));
    }
}
