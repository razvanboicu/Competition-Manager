package database.model.enums;

public enum Role {
    ADMIN("ADMIN"), PARTICIPANT("PARTICIPANT");

    private String code;

    Role(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
