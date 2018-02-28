package arias.jenifer.juegosumas;


/**
 * Created by j.arias.gallego on 24/02/2018.
 */

/**************************************************************************************
 *TIPO 1
 ***************************************************************************************/

public class SumItem {              //Clase para guardar el número de cada nivel

    private Integer num_level;

    public SumItem(Integer num_level) {    //Constructor solo con el número Int
        this.num_level = num_level;
    }

    //Generación de getter y setter
    public Integer getNum_level() {
        return num_level;
    }

    public void setNum_level(Integer num_level) {
        this.num_level = num_level;
    }

}
