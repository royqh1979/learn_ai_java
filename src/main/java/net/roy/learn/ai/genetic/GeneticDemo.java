package net.roy.learn.ai.genetic;

/**
 * Created by Roy on 2016/1/27.
 */
public class GeneticDemo extends Genetic {

    public GeneticDemo(int numChromosomes, float crossoverRatio, float mutationRatio) {
        super(numChromosomes, crossoverRatio, mutationRatio);
    }

    @Override
    protected float calculateFitness(Chromosome chromosome) {
        float x=geneToFloat(chromosome);
        return (float)(Math.sin(x)*Math.sin(0.4f*4)*Math.sin(3.0f*x));
    }

    @Override
    protected int getNumGenesPerChromosome() {
        return 10;
    }

    private float geneToFloat(Chromosome chromosome) {
        int base=1;
        float x=0;
        for (int i=0;i<getNumGenesPerChromosome();i++) {
            if (chromosome.getBit(i)) {
                x += base;
            }
            base*=2;
        }
        x/=102.4f;
        return x;
    }

    public static void main(String[] args) {
        GeneticDemo genetic_experiment =
                new GeneticDemo(20, 0.85f, 0.3f);
        int NUM_CYCLES = 500;
        for (int i=0; i<NUM_CYCLES; i++) {
            genetic_experiment.evolve();
            if ((i%(NUM_CYCLES/5))==0 || i==(NUM_CYCLES-1)) {
                System.out.println("Generation " + i);
                genetic_experiment.print();
            }
        }
    }

    private void print() {
        for (int i=0;i<getNumChromosomes();i++) {
            Chromosome ch=getChromosome(i);
            System.out.println("Fitness for chromsome "+i+" is "
                    +ch.getFitness()+", occurs at x="+geneToFloat(ch));
        }
    }
}
