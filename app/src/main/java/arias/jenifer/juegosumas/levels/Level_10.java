package arias.jenifer.juegosumas.levels;

/**
 * Created by j.arias.gallego on 03/03/2018.
 */

/**************************************************************************************
 *NIVEL 10 --> Numbers up: Unidades, Decenas, Centenas, Unidades de Millar.
 *             Numbers down: Unidades, Decenas.
 *             No Acarreo.
 ***************************************************************************************/

public class Level_10 {

    private int level;
    private int unid_up;
    private int dec_up;
    private int cent_up;
    private int mil_up;
    private int unid_down;
    private int dec_down;
    private boolean acarreo;

    //Constructor con números aleatórios
    public Level_10 (int unid_up, int dec_up, int cent_up, int mil_up, int unid_down, int dec_down) {
        this.level = 10;
        this.unid_up = unid_up;
        this.dec_up = dec_up;
        this.cent_up = cent_up;
        this.mil_up = mil_up;
        this.unid_down = unid_down;
        this.dec_down = dec_down;
        this.acarreo = false;
    }

    //Generación de getter y setter
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUnid_up() {
        return unid_up;
    }

    public void setUnid_up(int unid_up) {
        this.unid_up = unid_up;
    }

    public int getDec_up() {
        return dec_up;
    }

    public void setDec_up(int dec_up) {
        this.dec_up = dec_up;
    }

    public int getCent_up() {
        return cent_up;
    }

    public void setCent_up(int cent_up) {
        this.cent_up = cent_up;
    }

    public int getMil_up() {
        return mil_up;
    }

    public void setMil_up(int mil_up) {
        this.mil_up = mil_up;
    }

    public int getUnid_down() {
        return unid_down;
    }

    public void setUnid_down(int unid_down) {
        this.unid_down = unid_down;
    }

    public int getDec_down() {
        return dec_down;
    }

    public void setDec_down(int dec_down) {
        this.dec_down = dec_down;
    }

    public boolean getAcarreo() {
        return acarreo;
    }

    public void setAcarreo(boolean acarreo) {
        this.acarreo = acarreo;
    }
}


