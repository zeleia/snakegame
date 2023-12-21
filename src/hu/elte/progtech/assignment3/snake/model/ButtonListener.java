/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.elte.progtech.assignment3.snake.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import hu.elte.progtech.assignment3.snake.view.Scoreboard;

/**
 *
 * @author zelei
 */
public class ButtonListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        Scoreboard.uploadData(Scoreboard.getNameField().getText(),GameBoard.getPoints());
    }
    
}
