package com.example.common.core.enums.exception;

public enum CommonCoreErrorCode implements ErrorCode {
  CLIENT_GROUP_ERROR("1", "Client group is incorrect"),
  CLIENT_GROUP_NULL("2", "Client group is null"),
  UNKNOWN_ERROR("3", "Unkown error"),
  CONSTRAINT_VIOLATION_ERROR("4", "Validation failed while persisting the entity"),
  ILLEGAL_ARGUMENT("5", "The required argument is incorrect"),
  AUTHORIZATION_FAILED("6", "User has no permission to access the resouces"),
  INVALID_TOKEN("7", "The access token is invalid"),
  TOKEN_EXPIRED("8", "The access token expired"),
  REFRESH_TOKEN_EXPIRED("9", "refresh token is expired"),
  FORMULA_INVALID("10", "Formula is not valid"),
  FORMULA_CALCULATION_ERROR("11", "Formula calculation error"),
  CONTROLLER_ILLEGAL_ARGUMENT("12", "The required argument cannot be parsed in controller"),
  VALIDATION_ERROR("13", "Validation failed"),
  RPC_EXCEPTION("14", "Rpc exception"),
  USERNAME_PASSWORD_INVALID("15", "username or password is invalid"),
  INVALID_REQUEST_CREATE_ROOM("15", "username or password is invalid");

  private String code;
  private String desc;

  private CommonCoreErrorCode(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDesc() {
    return this.desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getServicePrefix() {
    return "CC";
  }
}
