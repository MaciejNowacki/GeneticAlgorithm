package geneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    private static final int MIN = 1;
    private static final int MAX = 127;
    private static final int numerOfEntities = 50; // wielkoœæ populacji
    private static final int numberOfGenerations = 50;
    private static final double chanceForNewGeneration = 0.8;
    private static final double chanceForMutation = 0.1;
    private static final int bits = (int) Math.ceil((Math.log(MAX) / Math.log(2)));

    private static List<Entity> listOfEntities = new ArrayList<>();
    private static Random r = new Random();
    private static double sumOfValues = 0;

    public static void main(String[] args) {
        int i = 0;
        generateFirstGeneration();
        calculateValuesOfEntities();
        Collections.sort(listOfEntities);
        System.out.println("------------------ [" + (i++) + " GEN] -------------------");
        printValues();

        while ((i++) < numberOfGenerations - 1) {
            findTheBest();
            cross();
            calculateValuesOfEntities();
            Collections.sort(listOfEntities);
            System.out.println("------------------ [" + i + " GEN] -------------------");
            printValues();
        }
    }

    public static String decToBin(int number) {
        String decToBinString = Integer.toString(number, 2);
        String newString = "";
        if (decToBinString.length() < bits) {
            for (int i = 0; i < bits - decToBinString.length(); i++) {
                newString += '0';
            }
            decToBinString = newString + decToBinString;
        }
        return decToBinString;
    }

    public static int randomNewEntity() {
        return r.nextInt(MAX) + MIN;
    }

    public static void cross() {
        List<Entity> newListOfEntities = new ArrayList<>();
        Entity indexA, indexB;
        int intersection;
        String decToBinString1, decToBinString2;

        while (newListOfEntities.size() != listOfEntities.size()) {
            indexA = listOfEntities.get(r.nextInt(listOfEntities.size()));
            indexB = listOfEntities.get(r.nextInt(listOfEntities.size()));
            if (r.nextDouble() < chanceForNewGeneration) {
                intersection = r.nextInt(bits);
                decToBinString1 = decToBin(indexA.getPosX());
                decToBinString2 = decToBin(indexB.getPosX());
                decToBinString1 = decToBinString1.substring(0, intersection) + decToBinString2.substring(intersection);
                if (r.nextDouble() < chanceForMutation) {
                    decToBinString1 = mutate(decToBinString1);
                }
                newListOfEntities.add(new Entity(Integer.parseInt(decToBinString1, 2)));
            }
        }

        listOfEntities = newListOfEntities;
    }

    private static String mutate(String text) {
        int mutation = r.nextInt(bits);
        String newString;
        if (text.charAt(mutation) == '0') {
            newString = text.substring(0, mutation) + '1' + text.substring(mutation + 1);
        } else {
            newString = text.substring(0, mutation) + '0' + text.substring(mutation + 1);
        }
        return newString;
    }

    public static void findTheBest() {
        List<Entity> newListOfEntities = new ArrayList<>();
        double randomNumber, currentNumber;
        int i;

        while (newListOfEntities.size() != listOfEntities.size()) {
            randomNumber = r.nextDouble() * sumOfValues;
            currentNumber = 0;
            i = 0;
            do {
                currentNumber += listOfEntities.get(i++)
                                               .getFunctionValue();
            }
            while (currentNumber < randomNumber);
            newListOfEntities.add(listOfEntities.get(i - 1));
        }

        listOfEntities = newListOfEntities;
    }

    public static void generateFirstGeneration() {
        for (int i = 0; i < numerOfEntities; i++) {
            listOfEntities.add(new Entity(randomNewEntity()));
        }
    }

    public static void calculateValuesOfEntities() { // trzeba robiæ po ka¿dej zmianie x
        double tmp;
        sumOfValues = 0;

        for (Entity entity : listOfEntities) {
            tmp = function(entity.getPosX());
            entity.setFunctionValue(tmp);
            sumOfValues += tmp;
        }
    }

    public static double function(double x) {
        return 2 * (Math.pow(x, 2) + 1);
    }

    public static void printValues() {
        for (Entity entity : listOfEntities) {
            System.out.println("f(" + entity.getPosX() + ") = " + entity.getFunctionValue());
        }
    }
}
