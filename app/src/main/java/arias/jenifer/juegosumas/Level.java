package arias.jenifer.juegosumas;

import java.util.concurrent.ThreadLocalRandom;
import java.lang.System;

class Level {
    final static int UP = 0, DOWN = 1, RESULT = 2;
    private int size[] = {-1, -1, -1};
    private boolean[] carry;

    static ThreadLocalRandom R = ThreadLocalRandom.current();

    private Level(int sizeUp, int sizeDown, String scarry) {
        carry = new boolean[scarry.length()];
        for (int i = 0; i < carry.length; i++) {
            carry[i] = (scarry.charAt(i) != '_');
        }
        // assert(Math.max(sizeUp, sizeDown) == carry.length);
        this.size[UP]     = sizeUp;
        this.size[DOWN]   = sizeDown;
        this.size[RESULT] = -1;
        this.carry = carry;
    }

    boolean[] getCarry() { return carry; }

    public class Instance {
        int[][] digits;

        Instance() {
            size[RESULT] = Math.max(size[UP], size[DOWN]);
            // Si hay acarreo en la cifra más significativa -> el resultado tiene una cifra más
            if (carry[size[RESULT]-1]) {
                size[RESULT]++;
            }
            digits = new int[][]{
                    new int[size[UP]],
                    new int[size[DOWN]],
                    new int[size[RESULT]]
            };
        }

        int[] getUp()     { return digits[UP]; }
        int[] getDown()   { return digits[DOWN]; }
        int[] getResult() { return digits[RESULT]; }

        private int _value(int which) {
            int val = 0;
            for (int i = 0; i < size[which]; i++) {
                val = val*10 + digits[which][i];
            }
            return val;
        }

        boolean selfCheck() {
            int up   = _value(UP);
            int down = _value(DOWN);
            int res  = _value(RESULT);
            return up + down == res;
        }

        void generateColumn(int i)
        {
            if (i >= size[DOWN] && i >= size[UP]) {
                digits[RESULT][i] = 1;
                return;
            }

            boolean carry_last = (i - 1 >= 0 && carry[i - 1]);
            boolean carry_now  = (i < carry.length && carry[i]);
            boolean last[]     = { (i == (size[UP] - 1)), (i == (size[DOWN] - 1)) };
            boolean single[]   = { (i >= size[DOWN]),     (i >= size[UP])         };

            // Solo una cifra arriba o abajo
            if (single[UP] || single[DOWN]) {
                int which = (single[UP] ? UP : DOWN);
                if (carry_last && carry_now) {
                    // Acarreo antes y después, solo puede ser 9
                    digits[RESULT][i] = 9;
                    digits[which][i] = 9;
                    return;
                }
                int lower = (last[which] ? 1 : 0) + (carry_last ? 1 : 0);
                int upper = 9;
                digits[RESULT][i] = R.nextInt(lower, upper + 1);
                digits[which][i]  = digits[RESULT][i] - (carry_last ? 1 : 0);
                return;
            }

            // Dos cifras seguro

            // Generar un resultado entre 10 y 18
            if (carry_now) {
                int lower = 10;
                int upper = 18 + (carry_last ? 1 : 0);
                int sum = R.nextInt(lower, upper + 1);
                digits[RESULT][i] = sum % 10;
                sum -= (carry_last ? 1 : 0);
                if (sum == 18) {
                    digits[UP][i] = 9;
                    digits[DOWN][i] = 9;
                } else {
                    int start = sum - 9;
                    if (start == 0) {
                        start++;
                    }
                    digits[UP][i]   = start + R.nextInt(9 - start + 1);
                    digits[DOWN][i] = sum - digits[UP][i];
                }
                return;
            }

            // Generar un resultado entre lower y upper.
            int lower = (carry_last ? 1 : 0) + (last[UP] ? 1 : 0) + (last[DOWN] ? 1 : 0);
            int upper = 9;
            int res = R.nextInt(lower, upper + 1);
            digits[RESULT][i] = res;
            res -= (carry_last ? 1 : 0);

            if (!last[UP] && !last[DOWN]) {
                if (res == 0) {
                    digits[UP][i] = 0;
                    digits[DOWN][i] = 0;
                } else {
                    int x = R.nextInt(0, res + 1);
                    digits[UP][i]   = x;
                    digits[DOWN][i] = res - x;
                }
            } else if (last[UP] && last[DOWN]) { // last[UP] && last[DOWN]
                if (res == 2) {
                    digits[UP][i] = 1;
                    digits[DOWN][i] = 1;
                } else {
                    int x = R.nextInt(1, res); // sin +1 !
                    digits[UP][i] = x;
                    digits[DOWN][i] = res - x;
                }
            } else { // last[UP] || last[DOWN]
                int which = (last[UP] ? UP : DOWN);
                int other = (last[UP] ? DOWN : UP);

                if (digits[RESULT][i] == 1) {
                    digits[which][i] = 1;
                    digits[other][i] = 0;
                } else {
                    int x = R.nextInt(1, res + 1);
                    digits[which][i] = x;
                    digits[other][i] = res - x;
                }
            }
        }
    }

    Instance generateInstance() {
        Instance I = new Instance();
        for (int i = 0; i < size[RESULT]; i++) {
            I.generateColumn(i);
        }
        assert(I.selfCheck());
        return I;
    }

    private static String[] DIGITS = {"0","1","2","3","4","5","6","7","8","9"};

    public static void printDigits(int size, int[] digits) {
        String output = "";
        for (int i = size-1; i >= 0; i--) {
            if (i < digits.length) {
                output += DIGITS[digits[i]];
            } else {
                output += ' ';
            }
        }
        System.out.println(output);
    }

    public static void main(String[] args) {
        for (int i = 0; i < ALL_LEVELS.length; i++) {
            System.out.println("LEVEL " + i + "\n");
            for (int k = 0; k < 1000; k++) {
                Instance I = ALL_LEVELS[i].generateInstance();
                int L = I.digits[RESULT].length;
                printDigits(L, I.digits[UP]);
                printDigits(L, I.digits[DOWN]);
                printDigits(L, I.digits[RESULT]);
                System.out.println();
            }
        }
    }


    // TABLA CON TODOS LOS NIVELES
    static Level[] ALL_LEVELS = {
            //Bloque 1: 1 suma, sin acarreo (Level 1 - 3)
            new Level(1, 1, "_"),
            new Level(1, 2, "__"),
            new Level(2, 1, "__"),
            //Bloque 2: 2 sumas, sin acarreo (Level 4 - 6)
            new Level(2, 2, "__"),
            new Level(2, 3, "___"),
            new Level(3, 2, "___"),
            //Bloque 3: 1 suma, 1 acarreo (Level 7 - 9)
            new Level(1, 1, "c"),
            new Level(2, 1, "c_"),
            new Level(1, 2, "c_"),
            //Bloque 4: 2 sumas, 1 acarreo (Level 10 - 17)
            new Level(2, 2, "c_"),
            new Level(2, 3, "c__"),
            new Level(3, 2, "c__"),
            new Level(2, 2, "_c"),
            new Level(2, 3, "_c_"),
            new Level(3, 2, "_c_"),
            new Level(4, 2, "_c__"),
            new Level(2, 4, "_c__"),
            //Bloque 5: 3 sumas, sin acarreo (Level 18 - 20)
            new Level(3, 3, "___"),
            new Level(4, 3, "____"),
            new Level(3, 4, "____"),
            //Bloque 6: 3 sumas, 1 acarreo (Level 21 - 29)
            new Level(3, 3, "c__"),
            new Level(4, 3, "c___"),
            new Level(3, 4, "c___"),
            new Level(3, 3, "_c_"),
            new Level(4, 3, "_c__"),
            new Level(3, 4, "_c__"),
            new Level(3, 3, "__c"),
            new Level(4, 3, "__c_"),
            new Level(3, 4, "__c_"),
            //Bloque 7: 3 sumas, 2 acarreos (Level 30 - 41)
            new Level(2, 2, "cc"),
            new Level(2, 3, "cc_"),
            new Level(3, 2, "cc_"),
            new Level(3, 3, "c_c"),
            new Level(4, 3, "c_c_"),
            new Level(3, 4, "c_c_"),
            new Level(3, 3, "_cc"),
            new Level(4, 3, "_cc_"),
            new Level(3, 4, "_cc_"),
            new Level(4, 4, "cc__"),
            new Level(4, 4, "c_c_"),
            new Level(4, 4, "_cc_"),
            //Bloque 8: 3 sumas, 2 acarreos (Level 42 - 47)
            new Level(3, 3, "ccc"),
            new Level(4, 3, "ccc_"),
            new Level(3, 4, "ccc_"),
            new Level(4, 4, "_ccc"),
            new Level(4, 4, "c_cc"),
            new Level(4, 4, "cc_c"),    //carry del primer dígito al último
    };
}
