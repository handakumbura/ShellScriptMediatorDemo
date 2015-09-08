/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wso2.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author dumiduh
 */
public class ShellExecutor {
    private String command;

    /**
     * executes linux commands
     *
     * @param cmd string containing linux bash commands
     */
    public ShellExecutor(String cmd)
    {
        this.command=cmd;
    }


    /**
     * executed command passed at time of object creation
     *
     * @return returns stdout of executed command.
     */
    public String execute()
    {
        StringBuilder output = new StringBuilder();
        Process p;
        try
        {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\t");
            }
            
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        return output.toString();
    }
}
