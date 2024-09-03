package org.example.layout;

import lombok.Data;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;



@Data
public class Calculator extends JPanel implements ActionListener {

    private JTextField display;

    public Calculator() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 36));
        display.setHorizontalAlignment(JTextField.RIGHT);
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(display, gbc);

        String[] buttonLabels = {
                "C", "+/-", "%", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "="
        };

        int row = 1;
        int col = 0;

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            button.addActionListener(this);
            gbc.gridwidth = (label.equals("0")) ? 2 : 1;
            gbc.gridx = col;
            gbc.gridy = row;
            add(button, gbc);

            col += gbc.gridwidth;
            if (col > 3) {
                col = 0;
                row++;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "C":
                display.setText("");
                break;
            case "=":
                try {
                    double result = evaluate(display.getText());
                    display.setText(String.valueOf(result));
                } catch (Exception ex) {
                    display.setText("Error");
                }
                break;
            case "+/-":
                if (!display.getText().isEmpty()) {
                    double value = Double.parseDouble(display.getText());
                    value = value * -1;
                    display.setText(String.valueOf(value));
                }
                break;
            default:
                display.setText(display.getText() + command);
                break;
        }
    }

    public double evaluate(String expression) {
        // Utilisez un script JavaScript pour évaluer l'expression
        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
            return Double.parseDouble(engine.eval(expression).toString());
        } catch (Exception e) {
            throw new RuntimeException("Invalid expression");
        }
    }


}
