package com.example.as.service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResetPwDTO implements Serializable {

  @NotNull private String email;

  @NotNull private String code;

  @NotNull private String password;

  @NotNull private String confirmPassword;

  @JsonProperty("isSuccess")
  private boolean success;
}
