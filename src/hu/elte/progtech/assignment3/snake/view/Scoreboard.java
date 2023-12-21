/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.elte.progtech.assignment3.snake.view;

import hu.elte.progtech.assignment3.snake.model.ButtonListener;
import java.awt.BorderLayout;
import java.sql.*;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author zelei
 */
public class Scoreboard extends JFrame {

    private JPanel listPanel;
    private JList<String> top10Names;
    private JList<String> top10Scores;
    private DefaultListModel<String> listNameModel;
    private DefaultListModel<String> listScoreModel;
    private static int rowCnt = 1;
    private JButton submit;
    private JPanel submitPanel;
    private static JTextField nameField;
    
    public static JTextField getNameField(){
        return nameField;
    }

    public Scoreboard() {
        setTitle("Scoreboard");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        downloadDatabase();
        top10Names = new JList<>(listNameModel);
        top10Scores = new JList<>(listScoreModel);

        listPanel = new JPanel();
        listPanel.add(top10Names);
        listPanel.add(top10Scores);

        submitPanel = new JPanel();
        
        ButtonListener l = new ButtonListener();
        submit = new JButton("Submit!");
        submit.addActionListener(l);
        nameField = new JTextField("",20);

        submitPanel.add(submit);
        submitPanel.add(nameField);

        getContentPane().add(listPanel,BorderLayout.NORTH);

        getContentPane().add(submitPanel, BorderLayout.SOUTH);

        setVisible(true);
        pack();
    }

    private void downloadDatabase() {

        ResultSet resultSet = null;
        listNameModel = new DefaultListModel<>();
        listScoreModel = new DefaultListModel<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/snake", "root", "admin"); Statement stat = con.createStatement();) {
            String selectTop = "SELECT name, score FROM scoreboard ORDER BY score DESC LIMIT 10;";
            resultSet = stat.executeQuery(selectTop);

            while (resultSet.next()) {
                rowCnt++;
                listNameModel.addElement(resultSet.getString(1));
                listScoreModel.addElement(resultSet.getString(2));
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void uploadData(String name, int score) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/snake", "root", "admin"); Statement stat = con.createStatement();) {
            String updateTable = "INSERT INTO scoreboard (Id, Name, Score) VALUES (" + rowCnt++ + ",'" + name + "'," + score + ")";
            stat.executeUpdate(updateTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
