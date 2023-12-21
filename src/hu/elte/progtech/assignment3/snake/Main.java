/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.elte.progtech.assignment3.snake;

import hu.elte.progtech.assignment3.snake.view.Snake;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author zelei
 */
public class Main {
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            JFrame ex = new Snake();
            ex.setVisible(true);
        });
    }
}
