package net.roy.learn.ai.neuralnetwork;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hopfield Demo
 * Rename some attributes, according to terms in "A brief introduction to Neural Network (ebook version)",Chap 8,P151.
 * Created by Roy on 2016/1/27.
 * remembered patterns
 */
public class Hopfield {
    private int numInputs;
    private float weights[][];
    private float theta[];
    private List<float[]> trainingData;

    public Hopfield(int numInputs) {
        this.numInputs = numInputs;
        weights=new float[numInputs][numInputs];
        theta =new float[numInputs];

        trainingData=new ArrayList<>();
    }

    public void addTrainingData(float[] data){
        Preconditions.checkArgument(data.length== numInputs);
        trainingData.add(data);
    }

    public void train() {
        //the network is table when the weight matrix is symmetrical,
        //  and all diagonal elements is 0
        for(int i=0;i<numInputs;i++) {
            Arrays.fill(weights[i],0);
        }
        for (int i=1;i<numInputs;i++) {
            for (int j=0;j<i;j++) {
                for (int k=0;k<trainingData.size();k++) {
                    float data[]=trainingData.get(k);
                    int temp1=adjustInput(data[i])*adjustInput(data[j]);
                    float temp=temp1+weights[i][j];
                    weights[i][j]=temp;
                    weights[j][i]=temp;
                }
            }
        }

        for (int i=0;i<numInputs;i++) {
            theta[i]=0.0f;
            for (int j=0;j<numInputs;j++) {
                theta[i]=weights[i][j];
            }
            theta[i]/=2;
        }
    }

    public float[] recall(float[] pattern, int numIterations) {
        float[] activationState=Arrays.copyOf(pattern,numInputs);
        for (int ii=0;ii<numIterations;ii++) {
            boolean changed=false;
            //Recalcuate the activation state each neural
            for (int i=0;i<numInputs;i++) {
                float oldState=1.0f;
                if (deltaEnergy(i,activationState)>0.0f) {
                    activationState[i]=1.0f;
                }else{
                    activationState[i]=-1.0f;
                }
                if (oldState!=activationState[i]){
                    changed=true;
                }
            }
            if (!changed) {
                break;
            }
        }
        return activationState;
    }

    private float deltaEnergy(int i, float[] activationState) {
        float temp=0.0f;
        for (int j=0;j<numInputs;j++){
            temp+=weights[i][j]*activationState[j];
        }
        return temp- theta[i];
    }

    public int getNumInputs() {
        return numInputs;
    }

    private int adjustInput(float x){
        if (x<0.0f) return -1;
        return 1;
    }


}
