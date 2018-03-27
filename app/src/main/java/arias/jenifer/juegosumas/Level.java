package arias.jenifer.juegosumas;



/**
 * Created by j.arias.gallego on 26/02/2018.
 */


/******************************
 *          FORMA 1
 *
 *****************************/

/*
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

*/
import java.util.concurrent.ThreadLocalRandom;

/******************************
 *          FORMA 2
 *
 *****************************/


class Level {
    private int numDigitsUp, numDigitsDown;
    private boolean[] carry;
    private int result;
    int[] Numbers;

    private Level(int digitsUp, int digitsDown, boolean[] carry) {
        this.numDigitsDown = digitsDown;
        this.numDigitsUp = digitsUp;
        this.carry = carry;
    }

    //Posicion de los acarreos
    boolean[] posCarry() {
        return this.carry;
    }


    //Generar resultados aleatórios
    private int[] generateDigitsResult (int numDigitsUp, int numDigitsDown, boolean[] posCarry) {
        int size, dif = 0, max;
        int total = numDigitsDown + numDigitsUp;
        Numbers = new int[total];
        boolean equal = false;

        //Diferente nº de dígitos
        if (numDigitsUp > numDigitsDown) {
            dif = numDigitsUp - numDigitsDown;
            max = numDigitsUp;
            size = numDigitsDown;
        } else if (numDigitsUp < numDigitsDown) {
            dif = numDigitsDown - numDigitsUp;
            max = numDigitsDown;
            size = numDigitsUp;
        } else { //Igual nº de dígitos
            size = numDigitsUp;
            max = numDigitsUp;
            equal = true;
        }

        int[] Result = new int[max];       //Dígitos definitivos para el resultado correcto
        int[] totalResult = new int[max];  //Números reales que representan el resultado

        if (size == 1) {
            if (equal) {
                if(!posCarry[0]) {
                    Result[0] = setResult(9);
                    totalResult[0] = Result[0];
                    Numbers[0] = generateNumbers(totalResult[0]);
                    Numbers[1] = totalResult[0] - Numbers[0];
                } else if (posCarry[0]) {
                    Result = new int[max + 1];
                    Result[0] = setResult(10);
                    Result[1] = 1;
                    totalResult[0] = Result[0] + 10;
                    Numbers[0] = generateNumbers(totalResult[0]);
                    Numbers[1] = totalResult[0] - Numbers[0];
                }
            } else {
                if(!posCarry[0]) {
                    Result[0] = setResult(5);
                    totalResult[0] = Result[0];
                    Numbers[0] = generateNumbers(totalResult[0]);
                    Numbers[1] = totalResult[0] - Numbers[0];
                } else if (posCarry[0]) {
                    Result[0] = setResult(6);
                    totalResult[0] = Result[0] + 10;
                    Numbers[0] = generateNumbers(totalResult[0]);
                    Numbers[1] = totalResult[0] - Numbers[0];
                }
                if (dif == 1) {
                    if(!posCarry[0] && !posCarry[1]) {
                        Result[1] = setResult(16);
                        totalResult[1] = Result[1];
                        Numbers[2] = totalResult[1];
                    } else if (posCarry[0] && !posCarry[1]) {
                        Result[1] = setResult(17);
                        totalResult[1] = Result[1];
                        Numbers[2] = totalResult[1] - 1;
                    }
                } else if (dif == 2) {
                    if(!posCarry[0] && !posCarry[1]) {
                        Result[1] = setResult(13);
                        totalResult[1] = Result[1];
                        Numbers[2] = totalResult[1];
                    } else if (posCarry[0] && !posCarry[1]) {
                        Result[1] = setResult(14);
                        totalResult[1] = Result[1];
                        Numbers[2] = totalResult[1] - 1;
                    }
                    if(!posCarry[1] && !posCarry[2]) {
                        Result[2] = setResult(16);
                        totalResult[2] = Result[2];
                        Numbers[3] = totalResult[2];
                    }
                }
            }
        } else if (size == 2) {
            if(!posCarry[0]) {
                Result[0] = setResult(1);
                totalResult[0] = Result[0];
                Numbers[0] = generateNumbers(totalResult[0]);
                Numbers[1] = totalResult[0] - Numbers[0];
            } else if (posCarry[0]) {
                Result[0] = setResult(2);
                totalResult[0] = Result[0] + 10;
                Numbers[0] = generateNumbers(totalResult[0]);
                Numbers[1] = totalResult[0] - Numbers[0];
            }
            if (equal) {
                if(!posCarry[0] && !posCarry[1]) {
                    Result[1] = setResult(9);
                    totalResult[1] = Result[1];
                    Numbers[2] = generateNumbers(totalResult[1]);
                    Numbers[3] = totalResult[1] - Numbers[2];
                } else if (!posCarry[0] && posCarry[1]) {
                    Result = new int[max + 1];
                    Result[0] = Result[0];
                    Result[1] = setResult(10);
                    Result[2] = 1;
                    totalResult[1] = Result[1] + 10;
                    Numbers[2] = generateNumbers(totalResult[1]);
                    Numbers[3] = totalResult[1] - Numbers[2];
                } else if (posCarry[0] && !posCarry[1]) {
                    Result[1] = setResult(11);
                    totalResult[1] = Result[1];
                    Numbers[2] = generateNumbers(totalResult[1]);
                    Numbers[3] = totalResult[1] - Numbers[2] - 1;
                } else if (posCarry[0] && posCarry[1]) {
                    Result = new int[max + 1];
                    Result[0] = Result[0];
                    Result[1] = setResult(12);
                    Result[2] = 1;
                    totalResult[1] = Result[1] + 10;
                    Numbers[2] = generateNumbers(totalResult[1]);
                    Numbers[3] = totalResult[1] - Numbers[2] - 1;
                }
            } else {
                if(!posCarry[0] && !posCarry[1]) {
                    Result[1] = setResult(5);
                    totalResult[1] = Result[1];
                    Numbers[2] = generateNumbers(totalResult[1]);
                    Numbers[3] = totalResult[1] - Numbers[2];
                } else if (!posCarry[0] && posCarry[1]) {
                    Result[1] = setResult(6);
                    totalResult[1] = Result[1] + 10;
                    Numbers[2] = generateNumbers(totalResult[1]);
                    Numbers[3] = totalResult[1] - Numbers[2];
                } else if (posCarry[0] && !posCarry[1]) {
                    Result[1] = setResult(7);
                    totalResult[1] = Result[1];
                    Numbers[2] = generateNumbers(totalResult[1]);
                    Numbers[3] = totalResult[1] - Numbers[2] - 1;
                } else if (posCarry[0] && posCarry[1]) {
                    Result[1] = setResult(8);
                    totalResult[1] = Result[1] + 10;
                    Numbers[2] = generateNumbers(totalResult[1]);
                    Numbers[3] = totalResult[1] - Numbers[2] - 1;
                }
                if (dif == 1) {
                    if(!posCarry[1] && !posCarry[2]) {
                        Result[2] = setResult(16);
                        totalResult[2] = Result[2];
                        Numbers[4] = totalResult[2];
                    } else if (posCarry[1] && !posCarry[2]) {
                        Result[2] = setResult(17);
                        totalResult[2] = Result[2];
                        Numbers[4] = totalResult[2] - 1;
                    }
                } else if (dif == 2) {
                    if(!posCarry[1] && !posCarry[2]) {
                        Result[2] = setResult(13);
                        totalResult[2] = Result[2];
                        Numbers[4] = totalResult[2];
                    } else if (posCarry[1] && !posCarry[2]) {
                        Result[2] = setResult(14);
                        totalResult[2] = Result[2];
                        Numbers[4] = totalResult[2] - 1;
                    }
                    if(!posCarry[2] && !posCarry[3]) {
                        Result[3] = setResult(16);
                        totalResult[3] = Result[3];
                        Numbers[5] = totalResult[3];
                    }
                }
            }
        } else if (size == 3) {
            if(!posCarry[0]) {
                Result[0] = setResult(1);
                totalResult[0] = Result[0];
                Numbers[0] = generateNumbers(totalResult[0]);
                Numbers[1] = totalResult[0] - Numbers[0];
            } else if (posCarry[0]) {
                Result[0] = setResult(2);
                totalResult[0] = Result[0] + 10;
                Numbers[0] = generateNumbers(totalResult[0]);
                Numbers[1] = totalResult[0] - Numbers[0];
            }
            if(!posCarry[0] && !posCarry[1]) {
                Result[1] = setResult(1);
                totalResult[1] = Result[1];
                Numbers[2] = generateNumbers(totalResult[1]);
                Numbers[3] = totalResult[1] - Numbers[2];
            } else if (!posCarry[0] && posCarry[1]) {
                Result[1] = setResult(2);
                totalResult[1] = Result[1] + 10;
                Numbers[2] = generateNumbers(totalResult[1]);
                Numbers[3] = totalResult[1] - Numbers[2];
            } else if (posCarry[0] && !posCarry[1]) {
                Result[1] = setResult(3);
                totalResult[1] = Result[1];
                Numbers[2] = generateNumbers(totalResult[1]);
                Numbers[3] = totalResult[1] - Numbers[2] - 1;
            } else if (posCarry[0] && posCarry[1]) {
                Result[1] = setResult(4);
                totalResult[1] = Result[1] + 10;
                Numbers[2] = generateNumbers(totalResult[1]);
                Numbers[3] = totalResult[1] - Numbers[2] - 1;
            }
            if (equal) {
                if(!posCarry[1] && !posCarry[2]) {
                    Result[2] = setResult(9);
                    totalResult[2] = Result[2];
                    Numbers[4] = generateNumbers(totalResult[2]);
                    Numbers[5] = totalResult[2] - Numbers[2];
                } else if (!posCarry[1] && posCarry[2]) {
                    Result = new int[max + 1];
                    Result[0] = Result[0];
                    Result[1] = Result[1];
                    Result[3] = 1;
                    Result[2] = setResult(10);
                    totalResult[2] = Result[2] + 10;
                    Numbers[4] = generateNumbers(totalResult[2]);
                    Numbers[5] = totalResult[2] - Numbers[4];
                } else if (posCarry[1] && !posCarry[2]) {
                    Result[2] = setResult(11);
                    totalResult[2] = Result[2];
                    Numbers[4] = generateNumbers(totalResult[2]);
                    Numbers[5] = totalResult[2] - Numbers[4] - 1;
                } else if (posCarry[1] && posCarry[2]) {
                    Result = new int[max + 1];
                    Result[0] = Result[0];
                    Result[1] = Result[1];
                    Result[3] = 1;
                    Result[2] = setResult(12);
                    totalResult[2] = Result[2] + 10;
                    Numbers[4] = generateNumbers(totalResult[2]);
                    Numbers[5] = totalResult[2] - Numbers[4] - 1;
                }
            } else {
                if (dif == 1) {
                    if(!posCarry[1] && !posCarry[2]) {
                        Result[2] = setResult(5);
                        totalResult[2] = Result[2];
                        Numbers[4] = generateNumbers(totalResult[2]);
                        Numbers[5] = totalResult[2] - Numbers[4];
                    } else if (!posCarry[1] && posCarry[2]) {
                        Result[2] = setResult(6);
                        totalResult[2] = Result[2] + 10;
                        Numbers[4] = generateNumbers(totalResult[2]);
                        Numbers[5] = totalResult[2] - Numbers[4];
                    } else if (posCarry[1] && !posCarry[2]) {
                        Result[2] = setResult(7);
                        totalResult[2] = Result[2];
                        Numbers[4] = generateNumbers(totalResult[2]);
                        Numbers[5] = totalResult[2] - Numbers[4] - 1;
                    } else if (posCarry[1] && posCarry[2]) {
                        Result[2] = setResult(8);
                        totalResult[2] = Result[2] + 10;
                        Numbers[4] = generateNumbers(totalResult[2]);
                        Numbers[5] = totalResult[2] - Numbers[4] - 1;
                    }
                    if(!posCarry[2] && !posCarry[3]) {
                        Result[3] = setResult(16);
                        totalResult[3] = Result[3];
                        Numbers[6] = totalResult[3];
                    } else if (posCarry[2] && !posCarry[3]) {
                        Result[3] = setResult(17);
                        totalResult[3] = Result[3];
                        Numbers[6] = totalResult[3] - 1;
                    }
                }
            }
        }
        return Numbers;
    }

    private int generateNumbers(int totalResult) {
        int NumDigits = -1;

        switch (totalResult) {
            case 0:
                NumDigits = 0;
                break;
            case 1:
                NumDigits = ThreadLocalRandom.current().nextInt(0, 2);
                break;
            case 2:
                NumDigits = ThreadLocalRandom.current().nextInt(1, 2);
                break;
            case 3:
                NumDigits = ThreadLocalRandom.current().nextInt(1, 3);
                break;
            case 4:
                NumDigits = ThreadLocalRandom.current().nextInt(1, 4);
                break;
            case 5:
                NumDigits = ThreadLocalRandom.current().nextInt(1, 5);
                break;
            case 6:
                NumDigits = ThreadLocalRandom.current().nextInt(1, 6);
                break;
            case 7:
                NumDigits = ThreadLocalRandom.current().nextInt(1, 7);
                break;
            case 8:
                NumDigits = ThreadLocalRandom.current().nextInt(1, 8);
                break;
            case 9:
                NumDigits = ThreadLocalRandom.current().nextInt(1, 9);
                break;
            case 10:
                NumDigits = ThreadLocalRandom.current().nextInt(1, 10);
                break;
            case 11:
                NumDigits = ThreadLocalRandom.current().nextInt(2, 10);
                break;
            case 12:
                NumDigits = ThreadLocalRandom.current().nextInt(3, 10);
                break;
            case 13:
                NumDigits = ThreadLocalRandom.current().nextInt(4, 10);
                break;
            case 14:
                NumDigits = ThreadLocalRandom.current().nextInt(5, 10);
                break;
            case 15:
                NumDigits = ThreadLocalRandom.current().nextInt(6, 10);
                break;
            case 16:
                NumDigits = ThreadLocalRandom.current().nextInt(7, 10);
                break;
            case 17:
                NumDigits = ThreadLocalRandom.current().nextInt(8, 10);
                break;
            case 18:
                NumDigits = 9;
                break;
        }
        return NumDigits;
    }

    // Todos los posibles tipos
    int setResult (int tipo) {
        switch (tipo) {
            case 1:     //Tipo 1, 13
                result = ThreadLocalRandom.current().nextInt(0, 10);
                break;
            case 2:     //Tipo 2
                result = ThreadLocalRandom.current().nextInt(0, 9);
                break;
            case 3 :     //Tipo 3, 4, 5, 14, 16
                result = ThreadLocalRandom.current().nextInt(1, 10);
                break;
            case 4:
                result = ThreadLocalRandom.current().nextInt(1, 10);
                break;
            case 5:
                result = ThreadLocalRandom.current().nextInt(1, 10);
                break;
            case 6:     //Tipo 6
                result = ThreadLocalRandom.current().nextInt(1, 9);
                break;
            case 7:     //Tipo 7, 8, 9, 17
                result = ThreadLocalRandom.current().nextInt(2, 10);
                break;
            case 8:
                result = ThreadLocalRandom.current().nextInt(2, 10);
                break;
            case 9:
                result = ThreadLocalRandom.current().nextInt(2, 10);
                break;
            case 10:    //Tipo 10
                result = ThreadLocalRandom.current().nextInt(2, 9);
                break;
            case 11:    //Tipo 11, 12
                result = ThreadLocalRandom.current().nextInt(3, 10);
                break;
            case 12:
                result = ThreadLocalRandom.current().nextInt(3, 10);
                break;
            case 13:
                result = ThreadLocalRandom.current().nextInt(0, 10);
                break;
            case 14:
                result = ThreadLocalRandom.current().nextInt(1, 10);
                break;
            case 15:    //Tipo 15
                result = ThreadLocalRandom.current().nextInt(9, 10);
                break;
            case 16:
                result = ThreadLocalRandom.current().nextInt(1, 10);
                break;
            case 17:
                result = ThreadLocalRandom.current().nextInt(2, 10);
                break;
        }
        return result;
    }

    int[] generateResult () {return generateDigitsResult(this.numDigitsUp, this.numDigitsDown, this.carry);}


    //Número de arriba
    int[] numbersUp() { return numsUp(numDigitsUp, numDigitsDown, Numbers); }

    private int[] numsUp (int numDigitsUp, int numDigitsDown, int[] Numbers) {
        int[] numUp = new int[numDigitsUp];
        int posNumbers = 0, min;
        boolean more;

        //Diferente nº de dígitos
        if (numDigitsUp > numDigitsDown) {
            min = numDigitsDown;
            more = true;
        } else if (numDigitsUp < numDigitsDown) {
            min = numDigitsUp;
            more = false;
        } else { //Igual nº de dígitos
            min = numDigitsUp;
            more = false;
        }

        for (int j = 0; j < min; j++) {
            numUp[j] = Numbers[posNumbers];
            posNumbers = posNumbers + 2;
        }
        if (more) {
            for (int j = min; j < numDigitsUp; j++) {
                numUp[j] = Numbers[posNumbers];
                posNumbers++;
            }
        }
        return numUp;
    }

    //Número de abajo
    int[] numbersDown() { return numsDown(numDigitsUp, numDigitsDown, Numbers); }

    private int[] numsDown (int numDigitsUp, int numDigitsDown, int[] Numbers) {
        int[] numDown = new int[numDigitsDown];
        int posNumbers = 1, min;
        boolean more;

        //Diferente nº de dígitos
        if (numDigitsUp > numDigitsDown) {
            min = numDigitsDown;
            more = false;
        } else if (numDigitsUp < numDigitsDown) {
            min = numDigitsUp;
            more = true;
        } else { //Igual nº de dígitos
            min = numDigitsDown;
            more = false;
        }

        for (int j = 0; j < min; j++) {
            numDown[j] = Numbers[posNumbers];
            posNumbers = posNumbers + 2;
        }
        posNumbers = posNumbers - 1;
        if (more) {
            for (int j = min; j < numDigitsDown; j++) {
                numDown[j] = Numbers[posNumbers];
                posNumbers++;
            }
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
            new Level(4, 3, new boolean[]{ true, true, true, false }),
            new Level(3, 4, new boolean[]{ true, true, true, false }),

    };
}
