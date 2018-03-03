package arias.jenifer.juegosumas.levels;

/**
 * Created by j.arias.gallego on 02/03/2018.
 */

/**************************************************************************************
 *NIVEL 1 --> Numbers up: Unidades. Numbers down: Unidades.
 *            No Acarreo.
 ***************************************************************************************/

public class Level_1  {

    private int level;
    private int unid_up;
    private int unid_down;
    private boolean acarreo;

    //Constructor con números aleatórios
    public Level_1 (int unid_up, int unid_down) {
        this.level = 1;
        this.unid_up = unid_up;
        this.unid_down = unid_down;
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

    public int getUnid_down() {
        return unid_down;
    }

    public void setUnid_down(int unid_down) {
        this.unid_down = unid_down;
    }

    public boolean getAcarreo() {
        return acarreo;
    }

    public void setAcarreo(boolean acarreo) {
        this.acarreo = acarreo;
    }

}
