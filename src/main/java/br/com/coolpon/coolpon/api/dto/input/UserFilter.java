package br.com.coolpon.coolpon.api.dto.input;

import java.util.Calendar;

public class UserFilter {
    private String fullName;
    private String phone;
    private Calendar birthDate;
    private Boolean lastVisit;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Boolean lastVisit) {
        this.lastVisit = lastVisit;
    }
}
