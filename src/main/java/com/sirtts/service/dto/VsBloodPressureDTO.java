package com.sirtts.service.dto;


import java.time.LocalDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VsBloodPressure entity.
 */
public class VsBloodPressureDTO implements Serializable {

    private String id;

    private String userid;

    @NotNull
    private Double systolic;

    @NotNull
    private Double diastolic;

    @NotNull
    private LocalDateTime measurmentdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Double getSystolic() {
        return systolic;
    }

    public void setSystolic(Double systolic) {
        this.systolic = systolic;
    }

    public Double getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(Double diastolic) {
        this.diastolic = diastolic;
    }

    public LocalDateTime getMeasurmentdate() {
        return measurmentdate;
    }

    public void setMeasurmentdate(LocalDateTime measurmentdate) {
        this.measurmentdate = measurmentdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VsBloodPressureDTO vsBloodPressureDTO = (VsBloodPressureDTO) o;
        if(vsBloodPressureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vsBloodPressureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VsBloodPressureDTO{" +
            "id=" + getId() +
            ", userid='" + getUserid() + "'" +
            ", systolic=" + getSystolic() +
            ", diastolic=" + getDiastolic() +
            ", measurmentdate='" + getMeasurmentdate() + "'" +
            "}";
    }
}
