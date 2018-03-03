package arias.jenifer.juegosumas.levels;

/**
 * Created by j.arias.gallego on 03/03/2018.
 */

/**************************************************************************************
 *NIVEL 9 --> Numbers up: Unidades, Decenas.
 *            Numbers down: Unidades, Decenas, Centenas, Unidad de Millar.
 *            No Acarreo.
 ***************************************************************************************/

public class Level_9 {

    private int level;
    private int unid_up;
    private int dec_up;
    private int unid_down;
    private int dec_down;
    private int cent_down;
    private int mil_down;
    private boolean acarreo;

    //Constructor con números aleatórios
    public Level_9 (int unid_up, int dec_up, int unid_down, int dec_down, int cent_down, int mil_down) {
        this.level = 9;
        this.unid_up = unid_up;
        this.dec_up = dec_up;
        this.unid_down = unid_down;
        this.dec_down = dec_down;
        this.cent_down = cent_down;
        this.mil_down = mil_down;
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

    public int getCent_down() {
        return cent_down;
    }

    public void setCent_down(int cent_down) {
        this.cent_down = cent_down;
    }

    public int getMil_down() {
        return mil_down;
    }

    public void setMil_down(int mil_down) {
        this.mil_down = mil_down;
    }

    public boolean getAcarreo() {
        return acarreo;
    }

    public void setAcarreo(boolean acarreo) {
        this.acarreo = acarreo;
    }
}



