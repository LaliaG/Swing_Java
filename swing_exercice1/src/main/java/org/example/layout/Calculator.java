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
        display.setEditable(false);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
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

        Color[] buttonColors = {
                new Color(128, 128, 128), // gray for functions like C, +/-, %
                new Color(255, 165, 0),   // orange for operators
                new Color(192, 192, 192)  // light gray for numbers and dot
        };

        int row = 1;
        int col = 0;

        for (String rowLabelArray : buttonLabels) {
            //for (String label : rowLabelArray) {
              JButton button = createRoundButton(rowLabelArray);
              if ("0123456789.".contains(rowLabelArray)) {
                    button.setBackground(buttonColors[2]);
                    button.setForeground(Color.BLACK);
              } else if ("+-*/=".contains(rowLabelArray)) {
                    button.setBackground(buttonColors[1]);
                    button.setForeground(Color.WHITE);
              } else {
                    button.setBackground(buttonColors[0]);
                    button.setForeground(Color.WHITE);
              }
              button.setFont(new Font("Arial", Font.PLAIN, 24));
              button.addActionListener(this);
              gbc.gridwidth = (rowLabelArray.equals("0")) ? 2 : 1;
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

    // Helper method to create round buttons
    private JButton createRoundButton(String label) {
        JButton button = new JButton(label) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(getBackground());
                g.fillOval(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(getForeground());
                g.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
            }

            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width = size.height = Math.max(size.width, size.height);
                return size;
            }
        };
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // If "=" button is pressed, perform the calculation
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
                        display.setText("Error"); // Division by zero
                        isNewOperation = true;
                        return;
                    }
                    break;
            }

            display.setText(String.valueOf(operand1));
            isNewOperation = true;

            // If an operator is pressed, save the first operand and the operator
        } else if ("+-*/".contains(command)) {
            operator = command;
            operand1 = Double.parseDouble(display.getText());
            isNewOperation = true;

            // If "C" is pressed, reset everything
        } else if (command.equals("C")) {
            display.setText("");
            operator = "";
            operand1 = 0;
            isNewOperation = true;

            // If any other button is pressed (numbers and dot)
        } else {
            if (isNewOperation) {
                display.setText(command);
                isNewOperation = false;
            } else {
                display.setText(display.getText() + command);
            }
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
        }
    }*/


}
