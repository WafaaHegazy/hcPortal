package com.sirtts.service.dto;


import java.time.LocalDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DiabetesSugarTest entity.
 */
public class DiabetesSugarTestDTO implements Serializable {

    private String id;

    private String userid;

    @NotNull
    private Double result;

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

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
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

        DiabetesSugarTestDTO diabetesSugarTestDTO = (DiabetesSugarTestDTO) o;
        if(diabetesSugarTestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diabetesSugarTestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiabetesSugarTestDTO{" +
            "id=" + getId() +
            ", userid='" + getUserid() + "'" +
            ", result=" + getResult() +
            ", measurmentdate='" + getMeasurmentdate() + "'" +
            "}";
    }
}
