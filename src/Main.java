import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {


        //Таблица римских символов - Начало
        String[] ppMRimT = {"", "M", "MM", "MMM"};                                       //Тысячи
        String[] ppMRimS = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"}; //Сотни
        String[] ppMRimD = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"}; //Десятки
        String[] ppMRimE = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"}; //Единицы
        //Таблица римских символов - Конец


        //Переменные определяющие рамки условий, можно менять, соответственно изменятся условия - Начало
        String[] ppMasOperant = {"+", "-", "*", "/"}; //Разрешённые для ввода операции
        String ppValidnyeSimvolyArab = "0123456789";  //Разрешённые для ввода символы - Арабские
        String ppValidnyeSimvolyRim = "IVXLCDM";      //Разрешённые для ввода символы - Римские
        int ppValidOperMin = 1;                       //Разрешённое для ввода число   - Минимальное
        int ppValidOperMax = 5000;                    //Разрешённое для ввода число   - Максимальное
        // Переменные определяющие рамки условий, можно менять, соответственно изменятся условия - Конец


        //Предварительные проверки и вычисления - Начало
        if (ppValidOperMin > ppValidOperMax)
            throw new Exception("Error: Задан некорректный диапазон, минимальное значение больше максимального");
        String ppValidnyeSimvoly = " " + ppValidnyeSimvolyArab + ppValidnyeSimvolyRim + String.join("", ppMasOperant);
        //Предварительные проверки и вычисления - Конец


        //Диалог с пользователем (не обязательный код, можно за комментировать) - Начало
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
        //Диалог с пользователем (не обязательный код, можно за комментировать) - Конец


        System.out.print("Введите выражение: ");
        Scanner scanner = new Scanner(System.in);
        String ppSring001 = scanner.nextLine();


        //Исправления опечаток (не обязательный код, можно за комментировать) - Начало
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
        for (String s : ppMasOperant)
            if (ppSring001.contains(s))
                ppSring001 = ppSring001.replace(s, " " + s + " ");
        //Удаление лишних пробелов с начала введенного выражения
        while (ppSring001.charAt(0) == ' ')
            ppSring001 = ppSring001.substring(1);
        //Удаление лишних пробелов с конца введенного выражения
        while (ppSring001.charAt(ppSring001.length() - 1) == ' ')
            ppSring001 = ppSring001.substring(0, ppSring001.length() - 1);
        //Проверка первого символа на "-"
        if (ppSring001.charAt(0) == '-')
            throw new Exception("Error: Строка не должна начинаться с символа \"-\"");
        //Удаление лишних пробелов между символами в выражении
        while (ppSring001.contains("  "))
            ppSring001 = ppSring001.replace("  ", " ");
        //Исправления опечаток (не обязательный код, можно за комментировать) - Конец


        //Разделение строки
        String[] ppMasElements = ppSring001.split(" ");
        //Остановка если не три элемента
        if (ppMasElements.length != 3) {
            System.out.println("Error: выражение должно состоять из трех элементов");
            throw new Exception("т.к. строка не является математической операцией");
        }
        //Остановка если второй элемент не оператор
        if (!String.join("", ppMasOperant).contains(ppMasElements[1])) {
            System.out.println("Error: второй элемент не оператор из  списка разрешенных: " + Arrays.stream(ppMasOperant).toList());
            throw new Exception("т.к. строка не является математической операцией");
        }
        //Проверка операндов и вычисление
        if (pfTips(ppMasElements[0], ppValidnyeSimvolyArab) && pfTips(ppMasElements[2], ppValidnyeSimvolyArab)) {
            //Операнды состоит из разрешенных Арабских символов
            System.out.println("Цифры арабские, Будет вычисляется: [" + ppSring001 + "]");
            System.out.println("Ответ: " + ppSring001 + " = " + (pfResult(Integer.parseInt(ppMasElements[0]), ppMasElements[1], Integer.parseInt(ppMasElements[2]), ppValidOperMin, ppValidOperMax)));
        } else if (pfTips(ppMasElements[0], ppValidnyeSimvolyRim) && pfTips(ppMasElements[2], ppValidnyeSimvolyRim)) {
            //Операнды состоит из разрешенных Римских символов
            System.out.println("Цифры Римские, Будет вычисляется: [" + ppSring001 + "]");
            //Операнды состоят из разрешенного диапазона Римских цифр
            int ppRimToArab0 = pfNumArab(ppMasElements[0], ppMRimT, ppMRimS, ppMRimD, ppMRimE);
            int ppRimToArab2 = pfNumArab(ppMasElements[2], ppMRimT, ppMRimS, ppMRimD, ppMRimE);
            int ppResult = pfResult(ppRimToArab0, ppMasElements[1], ppRimToArab2, ppValidOperMin, ppValidOperMax);
            if (ppResult > 0 && ppResult <= 3999) {
                System.out.println("Ответ: " + ppSring001 + " = " + pfNumRim(ppResult, ppMRimT, ppMRimS, ppMRimD, ppMRimE));
            } else if (ppResult == 0)
                throw new Exception("т.к. по ноль в римском системе отсутствует");
            else if (ppResult > 3999)
                throw new Exception("т.к. получилось число больше 3999, такие числа в римском системе отсутствуют");
            else
                throw new Exception("т.к. в римской системе нет отрицательных чисел");
        } else
            //Остановка если операнды разных типов
            throw new Exception("т.к. используются одновременно разные системы исчисления");
    }


    //Функция: выполнения операций
    public static int pfResult(int ppE0, String ppE1, int ppE2, int ppMin, int ppMax) throws Exception {
        int ppResult = 0;
        if (ppE0 >= ppMin && ppE0 <= ppMax && ppE2 >= ppMin && ppE2 <= ppMax) {
            switch (ppE1) {
                case "+" -> ppResult = ppE0 + ppE2;
                case "-" -> ppResult = ppE0 - ppE2;
                case "*" -> ppResult = ppE0 * ppE2;
                case "/" -> {
                    ppResult = ppE0 / ppE2;
                    int i = ppE0 % ppE2;
                    if (i != 0)
                        System.out.println("В ответе будет отброшен остаток: " + i);
                }
                case "%" -> ppResult = ppE0 % ppE2;
                default -> throw new Exception("Error: Не известный операнд");
            }
        } else {
            System.out.println("Минимальное  разрешенное число в программе: " + ppMin + "\nМаксимальное разрешенное число в программе: " + ppMax);
            throw new Exception("Error: Как минимум одно из чисел не отвечает требуемому диапазону");
        }
        return ppResult;
    }


    //Функция: Проверки соответствия символов
    public static boolean pfTips(String ppElement, String ppRul) {
        boolean ppTips = true;
        for (int i = 0; i < ppElement.length(); i++) {
            if (!ppRul.contains(String.valueOf(ppElement.charAt(i)))) {
                ppTips = false;
                break;
            }
        }
        return ppTips;
    }


    //Функция: Перевода из Арабских в Римские
    public static String pfNumRim(int ppNumArab, String[] ppMRimT, String[] ppMRimS, String[] ppMRimD, String[] ppMRimE) {
        String ppNumRim = "";
        if (ppNumArab > 0 && ppNumArab <= 3999) {
            int t = ppNumArab / 1000;
            int s = (ppNumArab - (t * 1000)) / 100;
            int d = (ppNumArab - (t * 1000) - (s * 100)) / 10;
            int e = (ppNumArab - (t * 1000) - (s * 100) - (d * 10));
            ppNumRim = ppMRimT[t] + ppMRimS[s] + ppMRimD[d] + ppMRimE[e];
        } else
            System.out.println("Error: Слишком большое число");
        return ppNumRim;
    }


    //Функция: Перевода из Римские в Арабские (осн)
    public static int pfNumArab(String ppNumRim, String[] ppMRimT, String[] ppMRimS, String[] ppMRimD, String[] ppMRimE) throws Exception {
        int ppNumArab = 0;
        int[] ppMasArab = {0, 0};
        //Получение тысяч
        ppMasArab = pfMasArab(ppNumRim, ppMRimT, 1000);
        ppNumRim = ppNumRim.substring(ppMasArab[1]);
        ppNumArab += ppMasArab[0];
        //Получение сотен
        ppMasArab = pfMasArab(ppNumRim, ppMRimS, 100);
        ppNumRim = ppNumRim.substring(ppMasArab[1]);
        ppNumArab += ppMasArab[0];
        //Получение десятков
        ppMasArab = pfMasArab(ppNumRim, ppMRimD, 10);
        ppNumRim = ppNumRim.substring(ppMasArab[1]);
        ppNumArab += ppMasArab[0];
        //Получение единиц
        ppMasArab = pfMasArab(ppNumRim, ppMRimE, 1);
        ppNumRim = ppNumRim.substring(ppMasArab[1]);
        ppNumArab += ppMasArab[0];
        if (ppNumRim.length() > 0)
            throw new Exception ("Error: Не корректное римское число. Пример правильного римского числа: " + pfNumRim(ppNumArab, ppMRimT, ppMRimS, ppMRimD, ppMRimE));
        return ppNumArab;
    }


    //Функция: Перевода из Римские в Арабские (доп)
    public static int[] pfMasArab(String ppNumRim, String[] ppMRimX, int ppX) {
        int[] ppNumArab = new int[2];
        int ppMaxSimv = Math.min(ppNumRim.length(), 4);
        for (int j = ppMaxSimv; j > 0; j--) {
            for (int i = 0; i < ppMRimX.length; i++) {
                if (ppMRimX[i].equals(ppNumRim.substring(0, j))) {
                    ppNumArab[0] = ppNumArab[0] + (ppX * i);
                    ppNumArab[1] = j;
                    j = 0;
                    break;
                }
            }
        }
        return ppNumArab;
    }


}