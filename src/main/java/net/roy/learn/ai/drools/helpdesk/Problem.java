package net.roy.learn.ai.drools.helpdesk;

/**
 * Created by Roy on 2016/1/26.
 */
public class Problem {

    private long serviceId;
    private ApplicationType applicationType = ApplicationType.NONE;
    private ProblemType problemType=ProblemType.NONE;
    private EnvironmentData environmentalData=EnvironmentData.NONE;

    public Problem(long serviceId, ApplicationType applicationType) {
        this.serviceId = serviceId;
        this.applicationType = applicationType;
    }

    public long getServiceId() {
        return serviceId;
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public ProblemType getProblemType() {
        return problemType;
    }

    public void setProblemType(ProblemType problemType) {
        this.problemType = problemType;
    }

    public EnvironmentData getEnvironmentalData() {
        return environmentalData;
    }

    public void setEnvironmentalData(EnvironmentData environmentalData) {
        this.environmentalData = environmentalData;
    }

    public String toString() {
        return "[Problem: " + applicationType +
                " problem type: " + problemType +
                " environmental data: " +
                environmentalData + "]";
    }
}
