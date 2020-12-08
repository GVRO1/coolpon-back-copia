package br.com.coolpon.coolpon.api.dto.input;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

public class HistoricDate {
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime initialDate;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime finishDate;

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDateTime initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDateTime finishDate) {
        this.finishDate = finishDate;
    }
}
