package net.roy.learn.ai.drools.blockworld;

/**
 * Created by Roy on 2016/1/26.
 */
public class OldBlockState  {
    private String name;
    private String onTopOf;
    private String supporting;

    public OldBlockState(String name, String onTopOf, String supporting) {
        this.name = name;
        this.onTopOf = onTopOf;
        this.supporting = supporting;
    }

    public String toString() {
        return "[OldBlockState_" + this.hashCode() +
                " " + getName() + " on top of: " + getOnTopOf() +
                " supporting: " + getSupporting() +"]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnTopOf() {
        return onTopOf;
    }

    public void setOnTopOf(String onTopOf) {
        this.onTopOf = onTopOf;
    }

    public String getSupporting() {
        return supporting;
    }

    public void setSupporting(String supporting) {
        this.supporting = supporting;
    }
}
