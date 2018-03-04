package arias.jenifer.juegosumas;


/**
 * Created by j.arias.gallego on 26/02/2018.
 */

//Clase que representa los niveles

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
