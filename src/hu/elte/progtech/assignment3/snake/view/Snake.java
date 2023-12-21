/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.elte.progtech.assignment3.snake.view;

import hu.elte.progtech.assignment3.snake.model.GameBoard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author zelei
 */

public class Snake extends JFrame{
    
    private JMenuBar fileMenu;

    public Snake() {   
        fileMenu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem m = new JMenuItem("New game...");
        NewGameListener l = new NewGameListener();
        m.addActionListener(l);
        file.add(m);
        fileMenu.add(file);
        setJMenuBar(fileMenu);
        
        initUI();
    }
    
    private void initUI() {       
        add(new GameBoard());
               
        setResizable(false);
        pack();
        
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private class NewGameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        }
        
    }
}
