package net.roy.learn.ai.drools.simple;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Roy on 2016/1/25.
 */
public class DroolsTest {
    public static void main(String[] args){
        RuleBase ruleBase = null;
        try {
            ruleBase = readRule();
            WorkingMemory workingMemory=ruleBase.newStatefulSession();
            Message message=new Message();
            message.setMessage("Hello world!");
            message.setStatus(Message.HELLO);
            workingMemory.insert(message);
            workingMemory.fireAllRules();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DroolsParserException e) {
            e.printStackTrace();
        }

    }

    private static RuleBase readRule() throws IOException, DroolsParserException {
        Reader source=new InputStreamReader(DroolsTest.class.getResourceAsStream("/rules/sample.drl"));
        PackageBuilder builder=new PackageBuilder();
        builder.addPackageFromDrl(source);
        Package aPackage=builder.getPackage();
        RuleBase ruleBase= RuleBaseFactory.newRuleBase();
        ruleBase.addPackage(aPackage);
        return ruleBase;
    }
}
