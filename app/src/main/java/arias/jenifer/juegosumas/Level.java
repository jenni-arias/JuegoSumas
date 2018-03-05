package arias.jenifer.juegosumas;


/**
 * Created by j.arias.gallego on 26/02/2018.
 */

//Clase que representa los niveles
/*
public class Level {
    private String nombre;
    private int level;

    public Level (String nombre, int level) {
        this.nombre = nombre;
        this.level = level;
    }

    public String getNombre() {
        return nombre;
    }

    public int getLevel() {
        return level;
    }

    public int getId() {
        return nombre.hashCode();
    }

    public static Level[] ITEMS = {
            new Level("uno", 1),
            new Level("dos", 2),
            new Level("tres", 3),
            new Level("cuatro", 4),
            new Level("cinco", 5),
            new Level("seis", 6),
            new Level("siete", 7),
            new Level("ocho", 8),
            new Level("nueve", 9),
            new Level("diez", 10),
    };

    //Obtiene item basado en su identificador
    public static Level getItem (int id) {
        for (Level item : ITEMS) {
            if(item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
*/

public class Level {
    private String nombre;
    private Integer level;
    private Integer unid_up;
    private Integer dec_up;
    private Integer cent_up;
    private Integer mil_up;
    private Integer unid_down;
    private Integer dec_down;
    private Integer cent_down;
    private Integer mil_down;
    private boolean acarreo;

    public Level (String nombre, Integer level, Integer unid_up, Integer dec_up, Integer cent_up, Integer mil_up,
                  Integer unid_down, Integer dec_down, Integer cent_down, Integer mil_down, boolean acarreo) {
        this.nombre = nombre;
        this.level = level;
        this.unid_up = unid_up;
        this.dec_up = dec_up;
        this.cent_up = cent_up;
        this.mil_up = mil_up;
        this.unid_down = unid_down;
        this.dec_down = dec_down;
        this.cent_down = cent_down;
        this.mil_down = mil_down;
        this.acarreo = acarreo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getLevel() {
        return level;
    }

    public int getId() {
        return nombre.hashCode();
    }

    public static Level[] ITEMS = {
            new Level("uno", 1, 1, null, null, null,
                    1, null, null, null, false),
            new Level("dos", 2, 1, null, null, null,
                    1, 1, null, null, false),
            new Level("tres", 3, 1, 1, null, null,
                    1, null, null, null, false),
            new Level("cuatro", 4, 1, 1, 1, null,
                    1, null, null, null, false),
            new Level("cinco", 5, 1, null, null, null,
                    1, 1, 1, null, false),
            new Level("seis", 6, 1, 1, null, null,
                    1, 1, null, null, false),
            new Level("siete", 7, 1, 1, null, null,
                    1, 1, 1, null, false),
            new Level("ocho", 8, 1, 1, 1, null,
                    1, 1, null, null, false),
            new Level("nueve", 9, 1, 1, null, null,
                    1, 1, 1, 1, false),
            new Level("diez", 10, 1, 1, 1, 1,
                    1, 1, null, null, false),
    };

    //Obtiene item basado en su identificador
    public static Level getItem (int id) {
        for (Level item : ITEMS) {
            if(item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    //Getter y Setter de todos los posible números
    public Integer getUnid_up() {
        return unid_up;
    }

    public void setUnid_up(Integer unid_up) {
        this.unid_up = unid_up;
    }

    public Integer getDec_up() {
        return dec_up;
    }

    public void setDec_up(Integer dec_up) {
        this.dec_up = dec_up;
    }

    public Integer getCent_up() {
        return cent_up;
    }

    public void setCent_up(Integer cent_up) {
        this.cent_up = cent_up;
    }

    public Integer getMil_up() {
        return mil_up;
    }

    public void setMil_up(Integer mil_up) {
        this.mil_up = mil_up;
    }

    public Integer getUnid_down() {
        return unid_down;
    }

    public void setUnid_down(Integer unid_down) {
        this.unid_down = unid_down;
    }

    public Integer getDec_down() {
        return dec_down;
    }

    public void setDec_down(Integer dec_down) {
        this.dec_down = dec_down;
    }

    public Integer getCent_down() {
        return cent_down;
    }

    public void setCent_down(Integer cent_down) {
        this.cent_down = cent_down;
    }

    public Integer getMil_down() {
        return mil_down;
    }

    public void setMil_down(Integer mil_down) {
        this.mil_down = mil_down;
    }

    public boolean getAcarreo() {
        return acarreo;
    }
}
