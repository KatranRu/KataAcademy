import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class Converter {

    private static final HashMap<Integer, Character> tableValue = new HashMap<>() {
        {
            put(1, 'I');
            put(5, 'V');
            put(10, 'X');
            put(50, 'L');
            put(100, 'C');
            put(500, 'D');
            put(1000, 'M');
        }
    };

    public boolean isRim(String string) {
        return string.matches("[IVXLCDM]+");
    }

    public String parsToRim(int arab) throws Exception {
        if (arab > 3999) {
            throw new Exception(String.format("Значение %d выходит за рамки стандартов перевода в римские числа", arab));
        }
        String result = "";
        int ed = arab % 10;
        int d = arab % 100 - ed;
        int s = arab % 1000 - d - ed;
        int t = arab - s - d - ed;

        for (int i = 0; i < t / 1000; i++) {
            result += tableValue.get(1000);
        }
        result += brut(s);
        result += brut(d);
        result += brut(ed);

        return result;
    }

    public Integer parsToArab(String rim) {
        int result = 0;

        char[] string = rim.toCharArray();
        for (int i = string.length-1; i > 0; i--) {
            int value1 =getKey(string[i]);
            int value2 =getKey(string[i-1]);
            if (value1 > value2) {
                result = result + value1 - value2;
                i--;
            } else {
                result += value1;
            }
        }
        return result + getKey(string[0]);
    }

    private String brut(int ed) {
        String result = "";
        if (ed == 0) {
            return result;
        }
        int mult = 0;
        if (ed / 100 == 0) {
            if (ed / 10 == 0) {
                mult = 1;
            } else {
                mult = 10;
            }
        } else {
            mult = 100;
        }
        if (ed < 9 * mult) {
            if (ed < 5 * mult) {
                if (ed != 4 * mult) {
                    for (int i = 0; i < ed / (1 * mult); i++) {
                        result += tableValue.get(1 * mult);
                    }
                } else {
                    result += tableValue.get(1 * mult);
                    result += tableValue.get(5 * mult);
                }
            } else {
                result += tableValue.get(5 * mult);
                for (int i = 0; i < (ed - 5 * mult) / (1 * mult); i++) {
                    result += tableValue.get(1 * mult);
                }
            }
        } else {
            result += tableValue.get(1 * mult);
            result += tableValue.get(10 * mult);
        }
        return result;
    }

    private Integer getKey(Character value) {

        Set<Map.Entry<Integer, Character>> entrySet = tableValue.entrySet();

        for (Map.Entry<Integer, Character> pair : entrySet) {
            if (value.equals(pair.getValue())) {
                return pair.getKey();
            }
        }
        return -1;
    }
}
