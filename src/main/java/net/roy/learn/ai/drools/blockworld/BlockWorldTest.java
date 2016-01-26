package net.roy.learn.ai.drools.blockworld;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.DroolsError;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.compiler.PackageBuilderErrors;
import org.drools.rule.*;
import org.drools.rule.Package;
import org.drools.runtime.rule.FactHandle;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;

/**
 * Created by Roy on 2016/1/26.
 */
public class BlockWorldTest {
    public static void main(String[] args) {
        RuleBase ruleBase= null;
        try {
            ruleBase = readRule();
            WorkingMemory workingMemory=ruleBase.newStatefulSession();
            System.out.println("\nInitial Working Memory:\n\n"+workingMemory.toString());
            workingMemory.fireAllRules(1);
            for (FactHandle factHandle:workingMemory.getFactHandles()) {
                System.out.println(factHandle);
            }
            System.out.println("\n\n** Before firing rules...");
            workingMemory.fireAllRules(20);
            System.out.println("\n\n** After firing rules.");
            System.out.println("\nFinal Working Memory:\n" +
                    workingMemory.toString());
            for (FactHandle factHandle:workingMemory.getFactHandles()) {
                System.out.println(factHandle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DroolsParserException e) {
            e.printStackTrace();
        }

    }

    private static RuleBase readRule() throws IOException, DroolsParserException {
        Reader source=new InputStreamReader(BlockWorldTest.class.getResourceAsStream("/rules/blockworld.drl"));
        System.out.println(source);
        PackageBuilder builder=new PackageBuilder();
        builder.addPackageFromDrl(source);
        for (DroolsError error:builder.getErrors().getErrors()){
            System.out.println(error);
        };
        Package aPackage=builder.getPackage();
        RuleBase ruleBase= RuleBaseFactory.newRuleBase();
        ruleBase.addPackage(aPackage);
        return ruleBase;
    }
}
