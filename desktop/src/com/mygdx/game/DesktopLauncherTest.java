package com.mygdx.game;


import com.mygdx.game.Test.TestJunit;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;



public class DesktopLauncherTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestJunit.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("yes" + result.wasSuccessful());
    }
}