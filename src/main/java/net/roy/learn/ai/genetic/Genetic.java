package net.roy.learn.ai.genetic;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Roy on 2016/1/26.
 */
public abstract class Genetic {
    private int numGenesPerChromosome; // number of genes per chromosome
    private int numChromosomes; // number of chromosomes
    List<Chromosome> chromosomes;
    private float crossoverRatio;
    private float mutationRatio;
    private int[] rouletteWheel;
    private int rouletteWheelSize;

    public Genetic(int numGenesPerChromosome, int numChromosomes, float crossoverRatio, float mutationRatio) {
        this.numGenesPerChromosome = numGenesPerChromosome;
        this.numChromosomes = numChromosomes;
        this.crossoverRatio = crossoverRatio;
        this.mutationRatio = mutationRatio;

        //generate chrosomes
        chromosomes=new ArrayList<>(numChromosomes);
        RandomDataGenerator generator=new RandomDataGenerator();
        for (int i=0;i<numChromosomes;i++) {
            Chromosome chromosome=new Chromosome(numGenesPerChromosome);
            for(int j=0;j<numGenesPerChromosome;j++) {
                chromosome.setBit(j,generator.nextInt(0,1));
            }
            chromosomes.add(chromosome);
        }
        //init rouletteWheel
        rouletteWheelSize=(1+numChromosomes)*numChromosomes/2;
        rouletteWheel=new int[rouletteWheelSize];
        int num_trials=numChromosomes;
        int index=0;
        for (int i=0;i<numChromosomes;i++){
            Arrays.fill(rouletteWheel,index,index+num_trials,i);
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
        for (int i=numChromosomes-1;i>=0;i++){
            for (int j=0;j<i;j++) {
                if (chromosomes.get(i).equals(chromosomes.get(j))) {
                    int g= generator.nextInt(0,numGenesPerChromosome-1);
                    setGene(i,g,!getGene(i,g));
                    break;
                }
            }
        }
    }

    protected void doMutations(){
        int crossNum = (int)(numChromosomes * crossoverRatio);
        int keepNum=numChromosomes-crossNum;
        RandomDataGenerator generator=new RandomDataGenerator();
        //Only new generated chromoses need mutation
        for (int i=keepNum;i<numChromosomes;i++){
            for (int j=0;j<numGenesPerChromosome;j++) {
                double r = generator.nextUniform(0, 1);
                if (r <= mutationRatio) {
                    boolean g=getGene(i,j);
                    setGene(i,j,!g);
                }
            }
        }
    }

    private void doCrossovers() {
        int crossNum = (int)(numChromosomes * crossoverRatio);
        int keepNum=numChromosomes-crossNum;
        List<Chromosome> newGeneration=new ArrayList<>();

        //Keep numChromosomes*(1-corssoverRatio) most fitted chromosomes
        newGeneration.addAll(chromosomes.subList(0,keepNum));

        //Generate new chromoses by crossover
        RandomDataGenerator generator=new RandomDataGenerator();
        for (int i=0;i<crossNum;i++) {
            int c1,c2;
            do{
                c1=generator.nextInt(0,numChromosomes-1);
                c2=generator.nextInt(0,numChromosomes-1);
            } while (c1==c2);
            int locus=generator.nextInt(1,numChromosomes-2);
            Chromosome child=new Chromosome(numGenesPerChromosome);
            for (int j=0;j<numGenesPerChromosome;j++) {
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

    private void sort(){
        Collections.sort(chromosomes,Collections.reverseOrder());
    }

    public int getNumGenesPerChromosome() {
        return numGenesPerChromosome;
    }

    public int getNumChromosomes() {
        return numChromosomes;
    }

    public float getCrossoverRatio() {
        return crossoverRatio;
    }

    public float getMutationRatio() {
        return mutationRatio;
    }

}
