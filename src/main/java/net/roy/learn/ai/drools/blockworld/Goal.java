package net.roy.learn.ai.drools.blockworld;

/**
 * Created by Roy on 2016/1/26.
 */
public class Goal {
    private String supportingBlock;
    private String supportedBlock;

    public Goal(String supportingBlock, String supportedBlock) {
        this.supportingBlock = supportingBlock;
        this.supportedBlock = supportedBlock;
    }

    public String getSupportingBlock() {
        return supportingBlock;
    }

    public void setSupportingBlock(String supportingBlock) {
        this.supportingBlock = supportingBlock;
    }

    public String getSupportedBlock() {
        return supportedBlock;
    }

    public void setSupportedBlock(String supportedBlock) {
        this.supportedBlock = supportedBlock;
    }

    public String toString() {
        return "[Goal_" + this.hashCode() +
                " Goal: supporting block: " +
                supportingBlock +
                " and supported block: " +
                supportedBlock +"]";
    }

}
