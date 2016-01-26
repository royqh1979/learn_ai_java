package net.roy.learn.ai.genetic;

import java.util.BitSet;

/**
 * Created by Roy on 2016/1/26.
 */
public class Chromosome implements Comparable<Chromosome> {
    private BitSet chrosome;
    private float fitness=Float.MIN_VALUE;
    public Chromosome(int num_genes){
        chrosome=new BitSet(num_genes);
    };
    public boolean getBit(int index){
        return chrosome.get(index);
    }

    public void setBit(int index,boolean value){
        chrosome.set(index,value);
    }

    public void setBit(int index,int value) {
        setBit(index, value!=0);
    }
    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chromosome that = (Chromosome) o;

        return !(chrosome != null ? !chrosome.equals(that.chrosome) : that.chrosome != null);

    }

    @Override
    public int hashCode() {
        return chrosome != null ? chrosome.hashCode() : 0;
    }

    @Override
    public int compareTo(Chromosome o) {
        if (this==null) {
            if (o==null) {
                return 0;
            } else {
                return -1;
            }
        }
        if (o==null){
            return 1;
        }
        return Float.compare(this.getFitness(),o.getFitness());
    }
}
