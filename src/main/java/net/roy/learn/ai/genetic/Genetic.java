package net.roy.learn.ai.genetic;

import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Roy on 2016/1/26.
 */
public abstract class Genetic {
    private int numChromosomes; // number of chromosomes
    List<Chromosome> chromosomes;
    private float crossoverRatio;
    private float mutationRatio;
    private int[] rouletteWheel;
    private int rouletteWheelSize;

    public Genetic(int numChromosomes, float crossoverRatio, float mutationRatio) {
        this.setNumChromosomes(numChromosomes);
        this.crossoverRatio = crossoverRatio;
        this.mutationRatio = mutationRatio;
        generateFirstGeneration();
    }

    private void generateFirstGeneration() {
        //generate chrosomes
        chromosomes=new ArrayList<>(getNumChromosomes());
        RandomDataGenerator generator=new RandomDataGenerator();
        for (int i=0;i<getNumChromosomes();i++) {
            Chromosome chromosome=new Chromosome(getNumGenesPerChromosome());
            for(int j=0;j<getNumGenesPerChromosome();j++) {
                chromosome.setBit(j,generator.nextInt(0,1));
            }
            chromosomes.add(chromosome);
        }
        //init rouletteWheel
        rouletteWheelSize=(1+getNumChromosomes())*getNumChromosomes()/2;
        rouletteWheel=new int[rouletteWheelSize];
        int num_trials=getNumChromosomes();
        int index=0;
        for (int i=0;i<getNumChromosomes();i++){
            Arrays.fill(rouletteWheel, index, index + num_trials, i);
            index+=num_trials;
            num_trials--;
        }
        calculateAllFitness();
    }

    private void calculateAllFitness(){
        for (Chromosome chromosome:chromosomes) {
            float fit= calculateFitness(chromosome);
            chromosome.setFitness(fit);
        }
    }

    protected abstract float calculateFitness(Chromosome chromosome);

    public void evolve(){
        sort();
        doCrossovers();
        doMutations();
        doRemoveDuplicates();
        calculateAllFitness();
        System.gc();
    }

    private void doRemoveDuplicates() {
        RandomDataGenerator generator=new RandomDataGenerator();
        for (int i= getNumChromosomes()-1;i>=0;i--){
            for (int j=0;j<i;j++) {
                if (chromosomes.get(i).equals(chromosomes.get(j))) {
                    int g= generator.nextInt(0, getNumGenesPerChromosome() -1);
                    setGene(i,g,!getGene(i,g));
                    break;
                }
            }
        }
    }

    protected void doMutations(){
        int crossNum = (int)(getNumChromosomes() * crossoverRatio);
        int keepNum= getNumChromosomes() -crossNum;
        RandomDataGenerator generator=new RandomDataGenerator();
        //Only new generated chromoses need mutation
        for (int i=keepNum;i< getNumChromosomes();i++){
            for (int j=0;j< getNumGenesPerChromosome();j++) {
                double r = generator.nextUniform(0, 1);
                if (r <= mutationRatio) {
                    boolean g=getGene(i,j);
                    setGene(i,j,!g);
                }
            }
        }
    }

    private void doCrossovers() {
        int crossNum = (int)(getNumChromosomes() * crossoverRatio);
        int keepNum= getNumChromosomes() -crossNum;
        List<Chromosome> newGeneration=new ArrayList<>();

        //Keep numChromosomes*(1-corssoverRatio) most fitted chromosomes
        newGeneration.addAll(chromosomes.subList(0,keepNum));

        //Generate new chromoses by crossover
        RandomDataGenerator generator=new RandomDataGenerator();
        for (int i=0;i<crossNum;i++) {
            int c1,c2;
            do{
                c1=generator.nextInt(0, getNumChromosomes() -1);
                c2=generator.nextInt(0, getNumChromosomes() -1);
            } while (c1==c2);
            int locus=generator.nextInt(1, getNumChromosomes() -2);
            Chromosome child=new Chromosome(getNumGenesPerChromosome());
            for (int j=0;j< getNumGenesPerChromosome();j++) {
                if (j<locus){
                     child.setBit(j,getGene(c1,j));
                } else {
                    child.setBit(j,getGene(c2,j));
                }
            }
            newGeneration.add(child);
        }

        //replace olodGeneration with new Generation
        chromosomes.clear();
        chromosomes.addAll(newGeneration);
    }

    public boolean getGene(int chromosomeIndex, int geneIndex) {
        return chromosomes.get(chromosomeIndex).getBit(geneIndex);
    }

    public void setGene(int chromosomeIndex, int geneIndex, int value) {
        chromosomes.get(chromosomeIndex).setBit(geneIndex, value != 0);
    }

    public void setGene(int chromosomeIndex, int geneIndex, boolean value) {
        chromosomes.get(chromosomeIndex).setBit(geneIndex, value);
    }

    protected Chromosome getChromosome(int index){
        return chromosomes.get(index);
    }

    private void sort(){
        Collections.sort(chromosomes,Collections.reverseOrder());
    }

    protected abstract int getNumGenesPerChromosome();

    public int getNumChromosomes() {
        return numChromosomes;
    }

    public float getCrossoverRatio() {
        return crossoverRatio;
    }

    public float getMutationRatio() {
        return mutationRatio;
    }

    public void setNumChromosomes(int numChromosomes) {
        this.numChromosomes = numChromosomes;
    }
}
