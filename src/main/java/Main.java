import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println(cals(reader.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String cals(String input) throws Exception {
        Converter converter = new Converter();
        String result = "";

        if (input == null || input.equals("")) {
            throw new Exception("Нет входных данных");
        }
        String[] data = input.split(" ");
        if (data.length < 3) {
            throw new Exception("Строка не является математической операцией");
        }
        if (data.length > 3) {
            throw new Exception("Формат математической операции не удовлетворяет" +
                    " заданию - два операнда и один оператор (+, -, /, *)");
        }
        if (converter.isRim(data[0]) ^ converter.isRim(data[2])) {
            throw new Exception("Используются одновременно разные системы счисления");
        }
        if(parsInt(data[0])>10 || parsInt(data[0])<1 || parsInt(data[2])>10 || parsInt(data[2])<1){
            throw new Exception("Калькулятор принимать на вход числа от 1 до 10 включительно, не более.");
        }

        if (converter.isRim(data[0])) {
            if (data[1].equals("-") && (parsInt(data[0]) == parsInt(data[2]))) {
                throw new Exception("В римской системе нет отрицательных чисел");
            }
          result = converter.parsToRim(parsInt(calculation(data[1],parsInt(data[0]),parsInt(data[2]))));
        }else {
            result = calculation(data[1],parsInt(data[0]),parsInt(data[2]));
        }

        return result;
    }


    static String calculation(String arg, Integer value1, Integer value2) throws Exception {
        String result = "";
        switch (arg) {
            case ("+"): {
                result = String.valueOf(value1 + value2);
                break;
            }
            case ("-"): {
                result = String.valueOf(value1 - value2);
                break;
            }
            case ("*"): {
                result = String.valueOf(value1 * value2);
                break;
            }
            case ("/"): {
                result = String.valueOf(value1 / value2);
                break;
            }
            default:{
                throw new Exception("Не соответствующая арифметическая операция");
            }
        }
        return result;
    }

    static Integer parsInt(String string) {
        Integer result = null;
        try {
            result = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            if (new Converter().isRim(string)) {
                result = new Converter().parsToArab(string);
            }
        }
        return result;
    }

}