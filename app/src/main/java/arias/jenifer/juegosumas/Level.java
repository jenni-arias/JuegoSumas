package arias.jenifer.juegosumas;



/**
 * Created by j.arias.gallego on 26/02/2018.
 */


/******************************
 *          FORMA 1
 *
 *****************************/


class Level {
    private int numDigitsUp, numDigitsDown;
    private boolean[] carry;
    private int[] Numbers;
   // Random rand = new Random();

    private Level(int digitsUp, int digitsDown, boolean[] carry) {
        this.numDigitsDown = digitsDown;
        this.numDigitsUp = digitsUp;
        this.carry = carry;
    }

    //TODO: que no se genere el número 10!
    //Generar dígitos aleatórios
    private int[] generateDigits(int n) {
        int[] digits = new int[n];
        for (int i = 0; i < digits.length-1; i++) {
            digits[i] = (int) (Math.random() * 10);                  //digits[i] = rand.nextInt(10);
        }
        digits[digits.length-1] = (int) (Math.random() * 10) + 1;    // Que el primer número no sea un 0.
        return digits;
    }

    int[] generateUp() {
        return generateDigits(this.numDigitsUp);
    }

    int[] generateDown() {
        return generateDigits(this.numDigitsDown);
    }

    //Generamos números con el formato correcto
    int[] Numbers() { return generateNumbers(generateUp(), generateDown(), carry); }

    private int[] generateNumbers(int[] numbersUp, int[] numbersDown, boolean[] posCarry) {
        int[] numUp = new int[numbersUp.length];
        int[] numDown = new int[numbersDown.length];
        int min, dif = 0, n;

        //Diferente nº de dígitos
        if (numbersUp.length < numbersDown.length) {
            dif = numbersDown.length - numbersUp.length;
            min = numbersUp.length;
            n = 1;
        } else if (numbersUp.length > numbersDown.length) {
            dif = numbersUp.length - numbersDown.length;
            min = numbersDown.length;
            n = 2;
        } else { //Igual nº de dígitos
            min = numbersUp.length;
            n = 3;
        }

        //Comprobar que el formato es correcto
        for (int i = 0; i < min; i++) {
            if (numbersUp[i] + numbersDown[i] < 10 && !posCarry[i]) {
                numUp[i] = numbersUp[i];
                numDown[i] = numbersDown[i];
            } else if (numbersUp[i] + numbersDown[i] > 10 && posCarry[i]) {
                numUp[i] = numbersUp[i];
                numDown[i] = numbersDown[i];
            } else {
                if (n == 3 && i == numbersUp.length-1) {
                    numbersUp[i] = (int) (Math.random() * 10) + 1;         //Que el primer número no sea un 0.
                    numbersDown[i] = (int) (Math.random() * 10) + 1;
                } else {
                    numbersUp[i] = (int) (Math.random() * 10);
                    numbersDown[i] = (int) (Math.random() * 10);
                }
                i--;
            }
        }

        //Si los números tienen diferente nº de dígitos, añadimos el/los último/s
        if (n == 1 && dif == 1) {
            int end = numbersDown.length - 1;
            if (numbersDown[end] >= 9 && posCarry[posCarry.length - 2]) {
                numDown[end] = (int) (Math.random() * 9) + 1;               //Si la última suma tiene acarreo, el primer número no puede ser un 9. //TODO: Funciona?¿?¿
            } else { numDown[end] = numbersDown[end]; }
        } else if (n == 1 && dif == 2) {
            int end = numbersDown.length-1;
            numDown[end-1] = numbersDown[end-1];
            if (numbersDown[end] >= 9 && posCarry[posCarry.length-2]) {
                numDown[end] = (int) (Math.random() * 9) + 1;
            } else { numDown[end] = numbersDown[end]; }
        } else if (n == 2 && dif == 1) {
            int end = numbersUp.length-1;
            if (numbersUp[end] >= 9 && posCarry[posCarry.length-2]) {
                numUp[end] = (int) (Math.random() * 9) + 1;
            } else { numUp[end] = numbersUp[end]; }
        } else if (n == 2 && dif == 2) {
            int end = numbersUp.length-1;
            numUp[end-1] = numbersUp[end-1];
            if (numbersUp[end] >= 9 && posCarry[posCarry.length-2]) {
                numUp[end] = (int) (Math.random() * 9) + 1;
            } else { numUp[end] = numbersUp[end]; }
        }

        Numbers = concat(numUp, numDown);
        return Numbers;
    }

    //Concatenar todos los dígitos para enviarlos juntos en el return
    private int[] concat(int[] numUp, int[] numDown) {
        int[] Numbers = new int[numUp.length + numDown.length];
        int j=0;

        for (int i = 0; i < numDigitsUp; i++) {
            Numbers[i] = numUp[i];
        }
        for (int i = numDigitsUp; i < Numbers.length; i++) {
            Numbers[i] = numDown[j];
            j++;
        }
        return Numbers;
    }

    //Número de arriba
    int[] numbersUp() { return numsUp(numDigitsUp, Numbers); }

    private int[] numsUp (int numDigitsUp, int[] Numbers) {
        int[] numUp = new int[numDigitsUp];
        for (int i = 0; i < numDigitsUp; i++) {
            numUp[i] = Numbers[i];
        }
        return numUp;
    }

    //Número de abajo
    int[] numbersDown() { return numsDown(numDigitsUp, numDigitsDown, Numbers); }

    private int[] numsDown (int numDigitsUp, int numDigitsDown, int[] Numbers) {
        int[] numDown = new int[numDigitsDown];
        int j = 0;
        for (int i = numDigitsUp; i < Numbers.length; i++) {
            numDown[j] = Numbers[i];
            j++;
        }
        return numDown;
    }

    //Posicion de los acarreos
    boolean[] posCarry() {
        return this.carry;
    }


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


/******************************
 *          FORMA 2
 *
 *****************************/

/*
class Level {
    private int numDigitsUp, numDigitsDown;
    private boolean[] carry;
    private int[] Numbers;
    private int result;
    // Random rand = new Random();

    private Level(int digitsUp, int digitsDown, boolean[] carry) {
        this.numDigitsDown = digitsDown;
        this.numDigitsUp = digitsUp;
        this.carry = carry;
    }


    //Generar resultados aleatórios
    private int[] generateResult (int numDigitsUp, int numDigitsDown, boolean[] posCarry) {
        int max;

        //Diferente nº de dígitos
        if (numDigitsUp > numDigitsDown) {
            max = numDigitsUp;
        } else if (numDigitsUp < numDigitsDown) {
            max = numDigitsDown;
        } else { //Igual nº de dígitos
            max = numDigitsUp;
        }

        int[] results = new int[max];
        for (int i = 0; i < posCarry.length; i++) {
            if (!posCarry[i]) {
                results[i] = (int) (Math.random() * (9 - 2 + 1) + 2);            //  (int)(Math.random()*(HASTA - DESDE + 1) + DESDE);
            }
            else { results[i] = (int) (Math.random() * (18 - 10 + 1) + 10); }
        }

        results = findNumbers(results);
        return results;
    }

    private int[] findNumbers(int[] results) {
        int[] Numbers = new int[results.length];

        for (int i = 0; i < results.length*2; i = i+2) {
            if (results[i] == 2) {
                Numbers[i] = 0;
                Numbers[i+1] = 2 ;
            }
        }
    }

    // Todos los posibles resultados
    int[] allResults (int[] allResults) {
        int j = 0;
        for (int i = 2; i < 19; i++) {
            allResults[j] = i;
            j++;
        }
        return  allResults;
    }

    int[] generateNumbers () {return generateResult(this.numDigitsUp, this.numDigitsDown, this.carry)};

    //Posicion de los acarreos
    boolean[] posCarry() {
        return this.carry;
    }




    //Generar dígitos aleatórios
    private int[] generateDigits(int n) {
        int[] digits = new int[n];
        for (int i = 0; i < digits.length-1; i++) {
            digits[i] = (int) (Math.random() * 10);                  //digits[i] = rand.nextInt(10);
        }
        digits[digits.length-1] = (int) (Math.random() * 10) + 1;    // Que el primer número no sea un 0.
        return digits;
    }

    int[] generateUp() {
        return generateDigits(this.numDigitsUp);
    }

    int[] generateDown() {
        return generateDigits(this.numDigitsDown);
    }

    //Generamos números con el formato correcto
    int[] Numbers() { return generateNumbers(generateUp(), generateDown(), carry); }

    private int[] generateNumbers(int[] numbersUp, int[] numbersDown, boolean[] posCarry) {
        int[] numUp = new int[numbersUp.length];
        int[] numDown = new int[numbersDown.length];
        int min, dif = 0, n;

        //Diferente nº de dígitos
        if (numbersUp.length < numbersDown.length) {
            dif = numbersDown.length - numbersUp.length;
            min = numbersUp.length;
            n = 1;
        } else if (numbersUp.length > numbersDown.length) {
            dif = numbersUp.length - numbersDown.length;
            min = numbersDown.length;
            n = 2;
        } else { //Igual nº de dígitos
            min = numbersUp.length;
            n = 3;
        }

        //Comprobar que el formato es correcto
        for (int i = 0; i < min; i++) {
            if (numbersUp[i] + numbersDown[i] < 10 && !posCarry[i]) {
                numUp[i] = numbersUp[i];
                numDown[i] = numbersDown[i];
            } else if (numbersUp[i] + numbersDown[i] > 10 && posCarry[i]) {
                numUp[i] = numbersUp[i];
                numDown[i] = numbersDown[i];
            } else {
                if (n == 3 && i == numbersUp.length-1) {
                    numbersUp[i] = (int) (Math.random() * 10) + 1;         //Que el primer número no sea un 0.
                    numbersDown[i] = (int) (Math.random() * 10) + 1;
                } else {
                    numbersUp[i] = (int) (Math.random() * 10);
                    numbersDown[i] = (int) (Math.random() * 10);
                }
                i--;
            }
        }

        //Si los números tienen diferente nº de dígitos, añadimos el/los último/s
        if (n == 1 && dif == 1) {
            int end = numbersDown.length - 1;
            if (numbersDown[end] >= 9 && posCarry[posCarry.length - 2]) {
                numDown[end] = (int) (Math.random() * 9) + 1;               //Si la última suma tiene acarreo, el primer número no puede ser un 9.
            } else { numDown[end] = numbersDown[end]; }
        } else if (n == 1 && dif == 2) {
            int end = numbersDown.length-1;
            numDown[end-1] = numbersDown[end-1];
            if (numbersDown[end] >= 9 && posCarry[posCarry.length-2]) {
                numDown[end] = (int) (Math.random() * 9) + 1;
            } else { numDown[end] = numbersDown[end]; }
        } else if (n == 2 && dif == 1) {
            int end = numbersUp.length-1;
            if (numbersUp[end] >= 9 && posCarry[posCarry.length-2]) {
                numUp[end] = (int) (Math.random() * 9) + 1;
            } else { numUp[end] = numbersUp[end]; }
        } else if (n == 2 && dif == 2) {
            int end = numbersUp.length-1;
            numUp[end-1] = numbersUp[end-1];
            if (numbersUp[end] >= 9 && posCarry[posCarry.length-2]) {
                numUp[end] = (int) (Math.random() * 9) + 1;
            } else { numUp[end] = numbersUp[end]; }
        }

        Numbers = concat(numUp, numDown);
        return Numbers;
    }

    //Concatenar todos los dígitos para enviarlos juntos en el return
    private int[] concat(int[] numUp, int[] numDown) {
        int[] Numbers = new int[numUp.length + numDown.length];
        int j=0;

        for (int i = 0; i < numDigitsUp; i++) {
            Numbers[i] = numUp[i];
        }
        for (int i = numDigitsUp; i < Numbers.length; i++) {
            Numbers[i] = numDown[j];
            j++;
        }
        return Numbers;
    }

    //Número de arriba
    int[] numbersUp() { return numsUp(numDigitsUp, Numbers); }

    private int[] numsUp (int numDigitsUp, int[] Numbers) {
        int[] numUp = new int[numDigitsUp];
        for (int i = 0; i < numDigitsUp; i++) {
            numUp[i] = Numbers[i];
        }
        return numUp;
    }

    //Número de abajo
    int[] numbersDown() { return numsDown(numDigitsUp, numDigitsDown, Numbers); }

    private int[] numsDown (int numDigitsUp, int numDigitsDown, int[] Numbers) {
        int[] numDown = new int[numDigitsDown];
        int j = 0;
        for (int i = numDigitsUp; i < Numbers.length; i++) {
            numDown[j] = Numbers[i];
            j++;
        }
        return numDown;
    }


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
*/