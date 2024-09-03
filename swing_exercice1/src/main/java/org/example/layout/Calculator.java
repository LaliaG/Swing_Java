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
    private String operator = "";
    private double operand1 = 0;
    private boolean isNewOperation = true;

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

        // Si le bouton "=" est pressé, effectuez le calcul
        if (command.equals("=")) {
            double operand2 = Double.parseDouble(display.getText());

            switch (operator) {
                case "+":
                    operand1 = operand1 + operand2;
                    break;
                case "-":
                    operand1 = operand1 - operand2;
                    break;
                case "*":
                    operand1 = operand1 * operand2;
                    break;
                case "/":
                    if (operand2 != 0) {
                        operand1 = operand1 / operand2;
                    } else {
                        display.setText("Error"); // Division par zéro
                        isNewOperation = true;
                        return;
                    }
                    break;
            }

            display.setText(String.valueOf(operand1));
            isNewOperation = true;

            // Si un opérateur est pressé, enregistrez le premier opérande et l'opérateur
        } else if ("+-*/".contains(command)) {
            operator = command;
            operand1 = Double.parseDouble(display.getText());
            isNewOperation = true;

            // Si le bouton "C" est pressé, réinitialisez tout
        } else if (command.equals("C")) {
            display.setText("");
            operator = "";
            operand1 = 0;
            isNewOperation = true;

            // Sinon, ajoutez les chiffres au display
        } else {
            if (isNewOperation) {
                display.setText(command);
                isNewOperation = false;
            } else {
                display.setText(display.getText() + command);
            }
        }


        /*switch (command) {
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
        }*/
    }


}
