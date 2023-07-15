package arh_calc_001;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        //Переменные определяющие рамки условий, можно менять, соответственно изменятся условия - Начало
        String[] ppMasOperant = {"+", "-", "*", "/"};
        String[] ppMasRimMax = {"N", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX", "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL", "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L", "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX", "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX", "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX", "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC", "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"};
        String ppValidnyeSimvolyArab = "0123456789";
        String ppValidnyeSimvolyRim = "IVXLC";
        int ppValidOperMin = 1;
        int ppValidOperMax = 10;
        // Переменные определяющие рамки условий, можно менять, соответственно изменятся условия - Конец

        //Предварительные проверки и вычисления - Начало
        if (ppValidOperMin > ppValidOperMax) {
            throw new Exception("Error: Задан некорректный диапазон, минимальное значение больше максимального");
        }
        String ppValidnyeSimvoly = " " + ppValidnyeSimvolyArab + ppValidnyeSimvolyRim + String.join("", ppMasOperant);
        //Предварительные проверки и вычисления - Конец

        //Диалог с пользователем (не обязательный код, можно закомментировать) - Начало
        System.out.println("Введите в одну строчку выражения для операций между двумя числами." +
                "\nПравила:" +
                "\n   Калькулятор умеет работать с двумя числами и только с целыми." +
                "\n   На входе числа должны быть в диапазоне [" + ppValidOperMin + ", " + ppValidOperMax + "]." +
                "\n   Калькулятор умеет работать только с Арабскими или Римскими числами одновременно." +
                "\n   Разрешенные операции: " + Arrays.stream(ppMasOperant).toList() + "." +
                "\n   Первое число не должно быть отрицательным." +
                "\nПримеры:" +
                "\n   V + L" +
                "\n   или" +
                "\n   10 - 5" +
                "\n");
        //Диалог с пользователем (не обязательный код, можно закомментировать) - Конец

        System.out.print("Введите выражение: ");
        Scanner scanner = new Scanner(System.in);
        String ppSring001 = scanner.nextLine();

        //Исправления опечаток (не обязательный код, можно закомментировать) - Начало
        //Перевод символов в верхний регистр
        ppSring001 = ppSring001.toUpperCase();
        System.out.println("Вы ввели: [" + ppSring001 + "]");
        //Проверка введенной строки на допустимые символы
        for (int i = 0; i < ppSring001.length(); i++) {
            if (!ppValidnyeSimvoly.contains(Character.toString(ppSring001.charAt(i)))) {
                System.out.println("Список допустимых символов: " + ppValidnyeSimvoly);
                throw new Exception("Error: Введены не допустимые символы");
            }
        }
        //Добавление по пробелу с права и слева от оператора (т.к. пробел будет использоваться в качестве разделителя)
        for (String s : ppMasOperant) {
            if (ppSring001.contains(s)) {
                ppSring001 = ppSring001.replace(s, " " + s + " ");
            }
        }
        //Удаление лишних пробелов с начала введенного выражения
        while (ppSring001.charAt(0) == ' ') {
            ppSring001 = ppSring001.substring(1);
        }
        //Удаление лишних пробелов с конца введенного выражения
        while (ppSring001.charAt(ppSring001.length() - 1) == ' ') {
            ppSring001 = ppSring001.substring(0, ppSring001.length() - 1);
        }
        //Проверка первого символа на "-"
        if (ppSring001.charAt(0) == '-') {
            throw new Exception("Error: Строка не должна начинаться с символа \"-\"");
        }
        //Удаление лишних пробелов между символами в выражение
        while (ppSring001.contains("  ")) {
            ppSring001 = ppSring001.replace("  ", " ");
        }
        //Исправления опечаток (не обязательный код, можно закомментировать) - Конец

        //Разделение строки
        String[] ppMasElements = ppSring001.split(" ");
        //Остановка если не три элемента
        if (ppMasElements.length != 3) {
            System.out.println("Error: выражение должно состоять из трех элементов");
            throw new Exception("т.к. строка не является математической операцией");
        }
        //Остановка если второй элемент не оператор
        if (!String.join("", ppMasOperant).contains(ppMasElements[1])) {
            System.out.println("Error: второй элемент не оператор из  списка разрешённых: " + Arrays.stream(ppMasOperant).toList());
            throw new Exception("т.к. строка не является математической операцией");
        }
        //Проверка операндов и вычисление
        if (pfTips(ppMasElements[0], ppValidnyeSimvolyArab) && pfTips(ppMasElements[2], ppValidnyeSimvolyArab)) {
            // Операнды состоит из разрешенных Арабских символов
            System.out.println("Цифры арабские, Будет вычисляться: [" + ppSring001 + "]");
            System.out.println("Ответ: " + ppSring001 + " = " + (pfResult(Integer.parseInt(ppMasElements[0]), ppMasElements[1], Integer.parseInt(ppMasElements[2]), ppValidOperMin, ppValidOperMax)));
        } else if (pfTips(ppMasElements[0], ppValidnyeSimvolyRim) && pfTips(ppMasElements[2], ppValidnyeSimvolyRim)) {
            //Операнды состоит из разрешенных Римских символов
            System.out.println("Цифры Римские, Будет вычисляться: [" + ppSring001 + "]");
            if (String.join(", ", ppMasRimMax).contains(ppMasElements[0]) && String.join("", ppMasRimMax).contains(ppMasElements[2])) {
                //Операнды состоят из разрешенного диапазона Римских цифр
                int ppRimToArab0 = 0;
                int ppRimToArab2 = 0;
                for (int i = 0; i < ppMasRimMax.length; i++) {
                    if (ppMasRimMax[i].equals(ppMasElements[0])) {
                        ppRimToArab0 = i;
                    }
                    if (ppMasRimMax[i].equals(ppMasElements[2])) {
                        ppRimToArab2 = i;
                    }
                }
                int ppResult = pfResult(ppRimToArab0, ppMasElements[1], ppRimToArab2, ppValidOperMin, ppValidOperMax);
                if (ppResult > 0) {
                    if (!(ppResult > ppMasRimMax.length - 1)) {
                        System.out.println("Ответ: " + ppSring001 + " = " + (ppMasRimMax[ppResult]));
                    } else {
                        throw new Exception("Error: Число получилось больше чем программе известно римских чисел");
                    }
                } else if (ppResult == 0) {
                    throw new Exception("т.к. по заданию ноль в римском ответе запрещен");
                } else {
                    throw new Exception("т.к. в римской системе нет отрицательных чисел");
                }
            } else {
                throw new Exception("Error: Римские цифры либо не верны либо больше 100");
            }
        } else {
            //Остановка если операнды разных типов
            throw new Exception("т.к. используются одновременно разные системы исчисления");
        }
    }

    //Функция: выполнения операций
    public static int pfResult(int ppE0, String ppE1, int ppE2, int ppMin, int ppMax) throws Exception {
        int r = 0;
        if (ppE0 >= ppMin && ppE0 <= ppMax && ppE2 >= ppMin && ppE2 <= ppMax) {
            switch (ppE1) {
                case "+" -> r = ppE0 + ppE2;
                case "-" -> r = ppE0 - ppE2;
                case "*" -> r = ppE0 * ppE2;
                case "/" -> {
                    r = ppE0 / ppE2;
                    int i = ppE0 % ppE2;
                    if (i != 0) {
                        System.out.println("В ответе будет отброшен остаток: " + i);
                    }
                }
                case "%" -> r = ppE0 % ppE2;
                default -> throw new Exception("Error: Не известный операнд");
            }
        } else {
            System.out.println("Минимальное  разрешённое число в программе: " + ppMin + "\nМаксимальное разрешённое число в программе: " + ppMax);
            throw new Exception("Error: Как минимум одно из чисел не отвечает требуемому диапазону");
        }
        return r;
    }

    //Функция: Проверки соответствия символов
    public static boolean pfTips(String ppElement, String ppRul) {
        boolean b = true;
        for (int i = 0; i < ppElement.length(); i++) {
            if (!ppRul.contains(String.valueOf(ppElement.charAt(i)))) {
                b = false;
                break;
            }
        }
        return b;
    }
}