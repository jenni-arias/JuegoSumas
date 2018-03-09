package arias.jenifer.juegosumas;


/**
 * Created by j.arias.gallego on 26/02/2018.
 */

class Level {
    private int numDigitsUp, numDigitsDown;
    private boolean[] carry;

    private Level(int digitsUp, int digitsDown, boolean[] carry) {
        this.numDigitsDown = digitsDown;
        this.numDigitsUp = digitsUp;
        this.carry = carry;
    }

    //
    // TODO: Métodos para generar una suma concreta con los requisitos del nivel
    //
    private static int[] generateDigits(int n) {
        int[] digits = new int[n];
        for (int i = 0; i < digits.length; i++) {
            digits[i] = (int) (Math.random() * 9);
        }
        return digits;
    }

    int[] generateUp() { return generateDigits(this.numDigitsUp); }
    int[] generateDown() { return generateDigits(this.numDigitsDown); }

    //Comprobar si el nivel acepta acarreo o no
    private static boolean acceptAcarreo(boolean[] carry) {
        boolean acarreo = false;
        for (int i = 0; i < carry.length; i++) {
            if (carry[i] && !acarreo) {
                acarreo = true;
            } else if (!acarreo){
                acarreo = false;
            }
        }
        return acarreo;
    }

    boolean acceptCarry() { return acceptAcarreo(this.carry); }

    //Posicion de los acarreos.
    boolean[] posCarry() { return this.carry; }

    // TABLA CON TODOS LOS NIVELES
        static Level[] ALL_LEVELS = {
            //Bloque 1: 1 suma, sin acarreo (Level 1 - 5)
            new Level(1, 1, new boolean[]{ false }),
            new Level(1, 2, new boolean[]{ false, false }),
            new Level(2, 1, new boolean[]{ false, false }),
            new Level(3, 1, new boolean[]{ false, false, false }),
            new Level(1, 3, new boolean[]{ false, false, false }),
            //Bloque 2: 2 sumas, sin acarreo (Level 6 - 10)
            new Level(2, 2, new boolean[]{ false, false }),
            new Level(2, 3, new boolean[]{ false, false, false }),
            new Level(3, 2, new boolean[]{ false, false, false }),
            new Level(4, 2, new boolean[]{ false, false, false, false }),
            new Level(2, 4, new boolean[]{ false, false, false, false }),
            //Bloque 3: 1 suma, 1 acarreo (Level 11 - 15)
            new Level(1, 1, new boolean[]{ true }),
            new Level(2, 1, new boolean[]{ true, false }),
            new Level(1, 2, new boolean[]{ true, false }),
            new Level(3, 1, new boolean[]{ true, false, false }),
            new Level(1, 3, new boolean[]{ true, false, false }),
            //Bloque 4: 2 sumas, 1 acarreo (Level 16 - 25)
            new Level(2, 2, new boolean[]{ true, false }),
            new Level(2, 3, new boolean[]{ true, false, false }),
            new Level(3, 2, new boolean[]{ true, false, false }),
            new Level(4, 2, new boolean[]{ true, false, false, false }),
            new Level(2, 4, new boolean[]{ true, false, false, false }),
            new Level(2, 2, new boolean[]{ false, true }),
            new Level(2, 3, new boolean[]{ false, true, false }),
            new Level(3, 2, new boolean[]{ false, true, false }),
            new Level(4, 2, new boolean[]{ false, true, false, false }),
            new Level(2, 4, new boolean[]{ false, true, false, false }),
            //Bloque 5: 3 sumas, sin acarreo (Level 26 - 28)
            new Level(3, 3, new boolean[]{ false, false, false }),
            new Level(4, 3, new boolean[]{ false, false, false, false }),
            new Level(3, 4, new boolean[]{ false, false, false, false }),
            //Bloque 6: 3 sumas, 1 acarreo (Level 29 - 37)
            new Level(3, 3, new boolean[]{ true, false, false }),
            new Level(4, 3, new boolean[]{ true, false, false, false }),
            new Level(3, 4, new boolean[]{ true, false, false, false }),
            new Level(3, 3, new boolean[]{ false, true, false }),
            new Level(4, 3, new boolean[]{ false, true, false, false }),
            new Level(3, 4, new boolean[]{ false, true, false, false }),
            new Level(3, 3, new boolean[]{ false, false, true }),
            new Level(4, 3, new boolean[]{ false, false, true, false }),
            new Level(3, 4, new boolean[]{ false, false, true, false }),
            //Bloque 7: 3 sumas, 2 acarreos (Level 38 - 40)
            new Level(3, 3, new boolean[]{ true, false, true }),
            new Level(4, 3, new boolean[]{ true, false, true, false }),
            new Level(3, 4, new boolean[]{ true, false, true, false }),
            //Bloque 8: 3 sumas, 2 acarreos (Level 41 - 43)
            new Level(2, 2, new boolean[]{ true, true }),
            new Level(2, 3, new boolean[]{ true, true, false }),
            new Level(3, 2, new boolean[]{ true, true, false }),
            //Bloque 9: 3 sumas, 3 acarreo (Level 44 - 46)
            new Level(3, 3, new boolean[]{ true, true, true }),
            new Level(4, 3, new boolean[]{ true, true, true }),
            new Level(3, 4, new boolean[]{ true, true, true }),

    };
}
