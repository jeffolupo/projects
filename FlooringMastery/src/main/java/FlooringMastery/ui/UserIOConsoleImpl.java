/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.ui;

import java.util.Scanner;

/**
 *
 * @author jeffolupo
 */
public class UserIOConsoleImpl implements UserIO{
    
    Scanner sc = new Scanner(System.in);
    
    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        print(prompt);
        String input= sc.nextLine();
        return Double.parseDouble(input);
        
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        print(prompt);
        Double input = Double.parseDouble(sc.nextLine());
        
        while(input < min || input > max){
            System.out.println("Invalid Input, enter a value from " + min + " to "
                    + max);
            print(prompt);
            input = Double.parseDouble(sc.nextLine());
        }
        return input;
    }

    @Override
    public float readFloat(String prompt) {
        print(prompt);
        String input= sc.nextLine();
        return Float.parseFloat(input);
        
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        print(prompt);
        Float input = Float.parseFloat(sc.nextLine());
        
        while(input < min || input > max){
            System.out.println("Invalid Input, enter a value from " + min + " to "
                    + max);
            print(prompt);
            input = Float.parseFloat(sc.nextLine());
        }
        return input;
    }

    @Override
    public int readInt(String prompt) {
        print(prompt);
        String input= sc.nextLine();
        return Integer.parseInt(input);
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        print(prompt);
        int input = Integer.parseInt(sc.nextLine());
        
        while(input < min || input > (max-1)){
            System.out.println("Invalid Input, enter a value from " + min + " to "
                    + (max - 1));
            print(prompt);
            input = Integer.parseInt(sc.nextLine());
        }
        return input;
    }

    @Override
    public long readLong(String prompt) {
        print(prompt);
        String input= sc.nextLine();
        return Long.parseLong(input);
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        print(prompt);
        Long input = Long.parseLong(sc.nextLine());
        
        while(input < min || input > max){
            System.out.println("Invalid Input, enter a value from " + min + " to "
                    + max);
            print(prompt);
            input = Long.parseLong(sc.nextLine());
        }
        return input;
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        String input= sc.nextLine();
        return input;
    }
    
}
