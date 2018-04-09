package arias.jenifer.juegosumas;

import java.io.Serializable;

/**
 * Created by j.arias.gallego on 06/04/2018.
 */

public class User implements Serializable {

    private int userId;
    private String NAME;
    private String LASTNAME_1;
    private String LASTNAME_2;
    private String AGE;
    private String LEVEL;
    private String EXERCISE;

    public User(int userId, String NAME, String LASTNAME_1,
                String LASTNAME_2, String AGE, String LEVEL, String EXERCISE) {
        this.userId = userId;
        this.NAME = NAME;
        this.LASTNAME_1 = LASTNAME_1;
        this.LASTNAME_2 = LASTNAME_2;
        this.AGE = AGE;
        this.LEVEL = LEVEL;
        this.EXERCISE = EXERCISE;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getLASTNAME_1() {
        return LASTNAME_1;
    }

    public void setLASTNAME_1(String LASTNAME_1) {
        this.LASTNAME_1 = LASTNAME_1;
    }

    public String getLASTNAME_2() {
        return LASTNAME_2;
    }

    public void setLASTNAME_2(String LASTNAME_2) {
        this.LASTNAME_2 = LASTNAME_2;
    }

    public String getAGE() {
        return AGE;
    }

    public void setAGE(String AGE) {
        this.AGE = AGE;
    }

    public String getLEVEL() {
        return LEVEL;
    }

    public void setLEVEL(String LEVEL) {
        this.LEVEL = LEVEL;
    }

    public String getEXERCISE() {
        return EXERCISE;
    }

    public void setEXERCISE(String EXERCISE) {
        this.EXERCISE = EXERCISE;
    }


}


