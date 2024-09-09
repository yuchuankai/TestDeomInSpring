/**
 * @Project: testDeomInSpring
 * @ClassName: FileChange
 * @Date: 2024年 03月 19日 10:55
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package 蜻蜓寻优算法;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
/**
 * @Description:
 * @Date: 2024年 03月 19日 10:55
 * @Author: MR.Yu
 **/

class Individual {
    double position;
    double fitness;

    Individual(double position) {
        this.position = position;
    }
}
public class main {

    public static List<Individual> initializePopulation(int populationSize, double[] searchSpace) {
        List<Individual> population = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < populationSize; i++) {
            double position = searchSpace[0] + (searchSpace[1] - searchSpace[0]) * rand.nextDouble();
            population.add(new Individual(position));
        }
        return population;
    }

    public static void updatePositions(List<Individual> population, double[] searchSpace) {
        Random rand = new Random();
        for (Individual individual : population) {
            individual.position += rand.nextDouble() * (searchSpace[1] - searchSpace[0]);
        }
    }

    public static void evaluateFitness(List<Individual> population, Function<Double, Double> objectiveFunction) {
        for (Individual individual : population) {
            individual.fitness = objectiveFunction.apply(individual.position);
        }
    }

    public static List<Individual> updatePopulation(List<Individual> population, double elitePercentage) {
        population.sort(Comparator.comparingDouble(individual -> individual.fitness));
        int eliteCount = (int) (elitePercentage * population.size());
        List<Individual> elites = new ArrayList<>(population.subList(0, eliteCount));
        double minPosition = Collections.min(elites, Comparator.comparingDouble(individual -> individual.position)).position;
        double maxPosition = Collections.max(elites, Comparator.comparingDouble(individual -> individual.position)).position;
        List<Individual> newPopulation = initializePopulation(population.size() - eliteCount, new double[]{minPosition, maxPosition});
        newPopulation.addAll(elites);
        return newPopulation;
    }

    public static void dragonflyAlgorithm(int populationSize, double[] searchSpace, int maxIterations, double elitePercentage, Function<Double, Double> objectiveFunction) {
        List<Individual> population = initializePopulation(populationSize, searchSpace);
        Random rand = new Random();
        for (int iter = 0; iter < maxIterations; iter++) {
            updatePositions(population, searchSpace);
            evaluateFitness(population, objectiveFunction);
            population = updatePopulation(population, elitePercentage);
        }
        Individual bestSolution = Collections.min(population, Comparator.comparingDouble(individual -> individual.fitness));
        System.out.println("Best solution: " + bestSolution.position);
        System.out.println("Best fitness: " + bestSolution.fitness);
    }

    public static void main(String[] args) {
        int populationSize = 50;
        double[] searchSpace = {-10, 10};
        int maxIterations = 100;
        double elitePercentage = 0.2;
        Function<Double, Double> objectiveFunction = new Function<Double, Double>() {
            @Override
            public Double apply(Double a) {
                return a * a;
            }
        };

//        BiFunction<Double, Double, Double> twoFunction = (x, y) -> x * y;

        dragonflyAlgorithm(populationSize, searchSpace, maxIterations, elitePercentage, objectiveFunction);
    }

}
