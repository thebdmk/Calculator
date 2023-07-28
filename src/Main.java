import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import static java.lang.Math.pow;

// Методы операций через массивы
// Римские цифвры в enum?
// Обрбатываем ввод через split?
class IntHolder {
    public Integer value;

    IntHolder(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.print("Введите выражение: ");
            System.out.println("Ответ: " + inputScan());
        }
    }


    static String inputScan() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String expression = reader.readLine(); //Ввод стоки
        Integer answer = 0;
        IntHolder countRomeNum = new IntHolder(0);
        String[] numStr = null;

        expression = expression.trim();

        if (expression.contains("+")) { //Проверяем наличие операторов
            numStr = expression.split("\\+");
            answer = addition(strToNum(numStr, countRomeNum));
        } else if (expression.contains("-")) {
            numStr = expression.split("-");
            answer = subtraction(strToNum(numStr, countRomeNum));
        } else if (expression.contains("*")) {
            numStr = expression.split("\\*");
            answer = multiplication(strToNum(numStr, countRomeNum));
        } else if (expression.contains("/")) {
            numStr = expression.split("/");
            answer = division(strToNum(numStr, countRomeNum));
        } else {
            throw new IOException("Недопустимый ввод");
        }
        return numToRome(answer, countRomeNum);
    } //Обработать ввод 2 значного числа через пробелы

    static String numToRome(Integer answer, IntHolder countRomeNum) throws IOException{
        String answerStr = answer.toString();

        String[] keys = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
        int[] vals = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };

        StringBuilder ret = new StringBuilder();
        int ind = 0;

        if (answer < 1 && countRomeNum.value == 2) {
            throw new IOException("Римское число меньше 0");
        } else if (answer >= 1 && countRomeNum.value == 2){
            while(ind < keys.length) {
                while(answer >= vals[ind]) {
                    var d = answer / vals[ind];
                    answer = answer % vals[ind];
                    for(int i=0; i<d; i++) {
                        ret.append(keys[ind]);
                    }
                }
                ind++;
            }
            answerStr = ret.toString();
        }
        return answerStr;
    }
    static int[] strToNum(String[] numStr, IntHolder countRomeNum) throws IOException{    //Проверка на ввод
        int i = 0;
        int[] numbers = new int[2];
        if (numStr.length != 2) {
            throw new IOException("Недопустимый ввод");
        }
        String[] rome = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        for (String str: numStr) {
            //str = str.replaceAll(" ", "");
            str = str.trim();
            boolean isNumRome = false;
            for (String s: rome) {
                if (str.equals(s)) {
                    isNumRome = true;
                    countRomeNum.value++;
                    break;
                }
            }
            if (str.length() == 0) {
                throw new IOException("Недопустимый ввод");
            } else if (isNumRome) {
                str = romeToArab(str);
            }
            numbers[i] = chrToNum(str);
            i++;
        }
        if (countRomeNum.value == 1) {
            throw new IOException("Недопустимый ввод");
        }
        return numbers;
    }
    static int chrToNum(String numStr) throws IOException{  //Переводим строку в массив интов
        char[] arrayCharStr = numStr.toCharArray();
        ArrayList<Integer> numArray = new ArrayList<Integer>();
        for (char c : arrayCharStr) {
            if (c >= 48 && c <= 57) {
                numArray.add(c - 48);
            } else {
                throw new IOException("Недопустимый ввод");
            }
        }
        int num = numArrayToNum(numArray);
        if (num > 10 || num < 0) {
            throw new IOException("Допустимы числа от 1 до 10");
        }
        return num;
    }

    static int numArrayToNum(ArrayList<Integer> numArray) { //Переводим массив интов в число
        int count = 1;
        if (numArray.size() != 1) {
            count = (int)pow(10, numArray.size() - 1);
        }
        int var = 0;
        for (int i = 0; i < numArray.size(); i++) {
            var = var + numArray.get(i) * count;
            count = count / 10;
        }
        return var;
    }

    static String romeToArab(String arabNum) {
        String romeNum;
        romeNum = switch (arabNum) {
            case "I" -> "1";
            case "II" -> "2";
            case "III" -> "3";
            case "IV" -> "4";
            case "V" -> "5";
            case "VI" -> "6";
            case "VII" -> "7";
            case "VIII" -> "8";
            case "IX" -> "9";
            case "X" -> "10";
            default -> "0";
        };
        return romeNum;
    } // Переводим Римское число в Арабское

    static int addition(int[] numbers) {
        return numbers[0] + numbers[1];
    }

    static int subtraction(int[] numbers) {
        return numbers[0] - numbers[1];
    }

    static int multiplication(int[] numbers) {
        return numbers[0] * numbers[1];
    }

    static int division(int[] numbers) {
        return numbers[0] / numbers[1];
    }
}