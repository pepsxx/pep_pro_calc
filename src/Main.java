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


        //Предварительные проверки и подготовка переменных
        pfChek001(ppValidOperMin, ppValidOperMax);
        String ppValidnyeSimvoly = " " + ppValidnyeSimvolyArab + ppValidnyeSimvolyRim + String.join("", ppMasOperant);
        String[][] ppMMRimX = {ppMRimT, ppMRimS, ppMRimD, ppMRimE};

        //Диалог с пользователем (это не обязательный код, можно закомментировать строчку)
        pfDialog001(ppValidOperMin, ppValidOperMax, ppMasOperant);

        //Ввод данных пользователем
        System.out.print("Введите выражение: ");
        Scanner scanner = new Scanner(System.in);
        String ppSring001 = scanner.nextLine();

        //Исправления опечаток (это не обязательный код, можно закомментировать строчку)
        ppSring001 = pfIspravOpechatok(ppSring001, ppValidnyeSimvoly, ppMasOperant);

        //Разделение строки на элементы
        String[] ppMasElements = ppSring001.split(" ");

        //Дополнительные проверки перед вычислениями
        pfChek002(ppMasElements, ppMasOperant);


        //Проверка операндов и вычисление - Начало
        //Проверка операндов на соответствие Арабским символам и вычисление
        if (pfTips(ppMasElements[0], ppValidnyeSimvolyArab) && pfTips(ppMasElements[2], ppValidnyeSimvolyArab)) {
            System.out.printf("Цифры определены как Арабские, будет вычисляться выражение: [%s]%nОтвет: %s = %d%n", ppSring001, ppSring001, pfResult(Integer.parseInt(ppMasElements[0]), ppMasElements[1], Integer.parseInt(ppMasElements[2]), ppValidOperMin, ppValidOperMax));
        } else
            //Проверка операндов на соответствие Римским символам перевод и вычисление
            if (pfTips(ppMasElements[0], ppValidnyeSimvolyRim) && pfTips(ppMasElements[2], ppValidnyeSimvolyRim)) {
                System.out.printf("Цифры определены как Римские, будет вычисляться выражение: [%s]%n", ppSring001);

                //Перевод Римских цифр в Арабские
                int ppRimToArab0 = pfNumArab(ppMasElements[0], ppMMRimX);
                int ppRimToArab2 = pfNumArab(ppMasElements[2], ppMMRimX);

                //Вычисление выражения
                int ppResult = pfResult(ppRimToArab0, ppMasElements[1], ppRimToArab2, ppValidOperMin, ppValidOperMax);

                //Вывод результата
                if (ppResult > 3999)
                    throw new Exception("\n\nт.к. получилось число " + ppResult + ",а в Римской системе отсутствуют числа больше 3999\n");
                else if (ppResult <= 0)
                    throw new Exception("\n\nт.к. получилось число " + ppResult + ",а в Римской системе отсутствуют отрицательные числа и ноль\n");
                else
                    //Перевод Арабских цифр в Римские
                    System.out.printf("Ответ: %s = %s%n", ppSring001, pfNumRim(ppResult, ppMMRimX));
            } else {
                //Остановка если операнды разных типов
                throw new Exception("\n\nError: т.к. используются одновременно разные системы исчисления или присутствуют не допустимые символы\n");
            }
        //Проверка операндов и вычисление - Конец
    }


//====================================================================================================================//


    //Функция: Первоначальный диалог с пользователем
    public static void pfDialog001(int ppValidOperMin, int ppValidOperMax, String[] ppMasOperant) {
        System.out.printf("""
                Введите в одну строчку выражения для операций между двумя числами.
                Правила:
                   Калькулятор умеет работать с двумя числами и только с целыми.
                   На входе числа должны быть в диапазоне [%s, %s].
                   Калькулятор умеет работать только с Арабскими или Римскими числами одновременно.
                   Разрешенные операции: %s.
                   Первое число не должно быть отрицательным.
                Примеры:
                   V + L
                   или
                   10 - 5
                """, ppValidOperMin, ppValidOperMax, Arrays.toString(ppMasOperant));
    }


    //Функция: Предварительная проверка
    public static void pfChek001(int ppValidOperMin, int ppValidOperMax) throws Exception {
        if (ppValidOperMin > ppValidOperMax) {
            throw new Exception("\n\nError: Задан некорректный диапазон, минимальное значение больше максимального\n");
        }
    }


    //Функция: Дополнительные проверки перед вычислениями
    public static void pfChek002(String[] ppMasElements, String[] ppMasOperant) throws Exception {
        //Остановка если не три элемента
        if (ppMasElements.length != 3)
            throw new Exception("\n\nError: Выражение должно состоять из трех элементов\nError: т.к. строка не является математической операцией\n");

        //Остановка если второй элемент не оператор
        if (!String.join("", ppMasOperant).contains(ppMasElements[1]))
            throw new Exception("\n\nError: Второй элемент не оператор из списка разрешённых: " + Arrays.toString(ppMasOperant) + "\nError: т.к. строка не является математической операцией\n");
    }


    //Функция: Исправления опечаток
    public static String pfIspravOpechatok(String ppSring001, String ppValidnyeSimvoly, String[] ppMasOperant) throws Exception {
        System.out.printf("--------------------------------------------------%nВы  ввели выражение: [%s]%n", ppSring001);
        //Перевод символов в верхний регистр
        ppSring001 = ppSring001.toUpperCase();

        //Проверка введенной строки на допустимые символы
        for (int i = 0; i < ppSring001.length(); i++) {
            if (!ppValidnyeSimvoly.contains(Character.toString(ppSring001.charAt(i)))) {
                throw new Exception("\n\nСписок допустимых символов: " + ppValidnyeSimvoly + "\nError: Введены не допустимые символы\n");
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
            throw new Exception("\n\nError: Строка не должна начинаться с символа \"-\"\n");
        }
        //Удаление лишних пробелов между символами в выражение
        while (ppSring001.contains("  ")) {
            ppSring001 = ppSring001.replace("  ", " ");
        }
        System.out.printf("Оно преобразовано в: [%s]%n--------------------------------------------------%n", ppSring001);
        return ppSring001;
    }


    //Функция: выполнения операций
    public static int pfResult(int ppE0, String ppE1, int ppE2, int ppMin, int ppMax) throws Exception {
        int r;
        if (ppE0 >= ppMin && ppE0 <= ppMax && ppE2 >= ppMin && ppE2 <= ppMax) {
            switch (ppE1) {
                case "%" -> r = ppE0 % ppE2;
                case "+" -> r = ppE0 + ppE2;
                case "-" -> r = ppE0 - ppE2;
                case "*" -> r = ppE0 * ppE2;
                case "/" -> {
                    if (ppE2 == 0) throw new Exception("\n\nError: На нль делить нельзя\n");
                    r = ppE0 / ppE2;
                    int i = ppE0 % ppE2;
                    if (i != 0) System.out.println("В ответе будет отброшен остаток: " + i);
                }
                default -> throw new Exception("\n\nError: Не известный оператор\n");
            }
        } else {
            throw new Exception("\n\nМинимальное  разрешённое число в программе: " + ppMin + "\nМаксимальное разрешённое число в программе: " + ppMax + "\nError: Как минимум одно из чисел не отвечает требуемому диапазону\n");
        }
        return r;
    }


    //Функция: Проверки соответствия символов
    public static boolean pfTips(String ppElement, String ppRul) {
        for (int i = 0; i < ppElement.length(); i++)
            if (!ppRul.contains(String.valueOf(ppElement.charAt(i)))) return false;
        return true;
    }


    //Функция: Перевода из Арабских в Римские
    public static String pfNumRim(int i, String[][] ppMMRimX) {
        return ppMMRimX[0][i / 1000] + ppMMRimX[1][i % 1000 / 100] + ppMMRimX[2][i % 100 / 10] + ppMMRimX[3][i % 10];
    }


    //Функция: Перевода из Римские в Арабские
    public static int pfNumArab(String ppElement, String[][] ppMMRimX) throws Exception {
        String ppNumRim = ppElement;
        int ppNumArab = 0;
        for (int i = 0; i < 4; i++) {                                                                //Четыре итерации поиска: Тысячи, Сотни, Десятки, Единицы.
            for (int ppSimvKolVo = Math.min(ppNumRim.length(), 4); ppSimvKolVo > 0; ppSimvKolVo--) { //Если пришло [XXVII], 1 цикл проверка соответствия [XXVI], 2 цикл проверка соответствия [XXV], 3 цикл проверка соответствия [XX].
                for (int j = 0; j < ppMMRimX[i].length; j++) {                                       //В данном примере производится проверка соответствия с этим: X, XX, XXX, XL, L, LX, LXX, LXXX, XC. (Десятки)
                    if (ppMMRimX[i][j].equals(ppNumRim.substring(0, ppSimvKolVo))) {                 //В итоге найдется соответствие с [XX].
                        ppNumArab = ppNumArab + (int) (1000/Math.pow(10,i) * j);                     //Если пришло [XXVII], найдется [XX] зафиксируется как 2, Далее A=A+(r*2), где r разрядность, в этом примере r=10. (Десятки)
                        ppNumRim = ppNumRim.substring(ppSimvKolVo);                                  //Если пришло [XXVII], найдется [XX] и отрежется, останется [VII] для следующего цикла проверки.
                        ppSimvKolVo = 0;
                        break;
                    }
                }
            }
        }
        if (ppNumRim.length() > 0)
            throw new Exception("\n\nError: Не корректное римское число.\nПример правильного римского  числа: " + pfNumRim(ppNumArab, ppMMRimX) + "\n");
        return ppNumArab;
    }


}