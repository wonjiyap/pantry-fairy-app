package com.wonjiyap.pantryfairyapp.dto.remind_info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wonjiyap.pantryfairyapp.enums.RemindTerm;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemindInfoRequest {

    @NotBlank
    private String remindTerm ;
    private String remindDay;
    private String remindDate;
    @NotBlank
    private String remindTime;

}
