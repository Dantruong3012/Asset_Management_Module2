package com.asset_management.dantruong.user_services;

import java.io.Serializable;

public class Users implements Serializable {
    private String loginAccountCode;
    private String passWord;
    private String diplayName;

public Users(String loginAccountName, String passWord, String diplayName){
    this.loginAccountCode = loginAccountName;
    this.passWord = passWord;
    this.diplayName = diplayName;
}

public String getLoginAccountName() {
    return loginAccountCode;
}

public void setLoginAccountName(String loginAccountName) {
    this.loginAccountCode = loginAccountName;
}

public String getPassWord() {
    return passWord;
}

public void setPassWord(String passWord) {
    this.passWord = passWord;
}

public String getDiplayName() {
    return diplayName;
}

public void setDiplayName(String diplayName) {
    this.diplayName = diplayName;
}


}
