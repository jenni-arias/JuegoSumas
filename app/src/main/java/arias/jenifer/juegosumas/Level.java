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
            digits[i] = 1; // FIXME: Metemos un 1 en cada dígito, esto es solo una prueba
        }
        return digits;
    }

    int[] generateUp() { return generateDigits(this.numDigitsUp); } // FIXME: Esto es incorrecto ahora
    int[] generateDown() { return generateDigits(this.numDigitsDown); } // FIXME: Esto es incorrecto ahora

    // TABLA CON TODOS LOS NIVELES
    // TODO: Rellenar con los niveles según la tabla que te pasé. El acarreo
    // TODO: es una tabla porque hay ciertas cifras que lo tienen y ciertas no.
    static Level[] ALL_LEVELS = {
        new Level(1, 1, new boolean[]{ false }),
        new Level(2, 1, new boolean[]{ false, false }),
        new Level(3, 1, new boolean[]{ false, false, false }),
        new Level(2, 2, new boolean[]{ false, false }),
    };
}
