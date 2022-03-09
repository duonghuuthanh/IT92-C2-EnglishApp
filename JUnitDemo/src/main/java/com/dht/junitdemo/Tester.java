/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.junitdemo;

import java.util.InputMismatchException;

/**
 *
 * @author Admin
 */
public class Tester {
    public  static boolean isNt(int n) {
        if (n < 0)
            throw new InputMismatchException();
        
        
        if (n >= 2) {
            for (int i = 2; i < Math.sqrt(n); i++)
                if (n % 2 == 0)
                    return false;
            
            return true;
        }
        
        return false;
    }
}
