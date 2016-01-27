package net.roy.learn.ai.neuralnetwork;

/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */


public class Test_2H {

    static float[] in1 = {0.1f, 0.1f, 0.9f};
    static float[] in2 = {0.1f, 0.9f, 0.1f};
    static float[] in3 = {0.9f, 0.1f, 0.1f};

    static float[] out1 = {0.9f, 0.1f, 0.1f};
    static float[] out2 = {0.1f, 0.1f, 0.9f};
    static float[] out3 = {0.1f, 0.9f, 0.1f};

    static float[] test1 = {0.1f, 0.1f, 0.9f};
    static float[] test2 = {0.1f, 0.9f, 0.1f};
    static float[] test3 = {0.9f, 0.1f, 0.1f};

    public static void main(String[] args) {
        Neural_2H nn = new Neural_2H(3, 3, 3, 3);
        nn.setTrainingRate(0.5f);
        double error = 0;
        for (int i = 0; i < 10000; i++) {
            switch(i%3){
                case 0:
                    error+=nn.train(in1,out1);
                    break;
                case 1:
                    error+=nn.train(in2,out2);
                    break;
                case 2:
                    error+=nn.train(in3,out3);
                    break;
            }
            if (i > 0 && (i % 100 == 0)) {
                error /= 100;
                System.out.println(
                        String.format("cycle %d  error is %.8f",i, error));
            }
        }
        test_recall(nn, test1);
        test_recall(nn, test2);
        test_recall(nn, test3);
/*
        nn.save("test.neural");
        System.out.println("Reload a previously trained NN from disk and re-test:");
        Neural_2H nn2 = Neural_2H.Factory("test.neural");
        nn2.addTrainingExample(in1, out1);
        nn2.addTrainingExample(in2, out2);
        nn2.addTrainingExample(in3, out3);
        test_recall(nn2, test1);
        test_recall(nn2, test2);
        test_recall(nn2, test3);
        */
    }

    public static void test_recall(Neural_2H nn, float[] inputs) {
        float[] results = nn.recall(inputs);
        System.out.print("Test case: ");
        for (int i = 0; i < inputs.length; i++) System.out.print(pp(inputs[i]) + " ");
        System.out.print(" results: ");
        for (int i = 0; i < results.length; i++) System.out.print(pp(results[i]) + " ");
        System.out.println();
        float error=0;
        for (int i=0;i<inputs.length;i++) {
            error+=(inputs[i]-results[i])*(inputs[i]-results[i])/2;
        }
        System.out.println("error:"+error);
    }

    public static String pp(float x) {
        String s = new String("" + x + "00");
        int index = s.indexOf(".");
        if (index > -1) s = s.substring(0, index + 3);
        if (s.startsWith("-") == false) s = " " + s;
        return s;
    }
}
