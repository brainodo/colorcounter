package com.upsellit.colorcounter;

import javax.swing.*;

/**
 * Main class of program
 *
 * @author Lee Dudley
 * Created: 3/25/2019
 */
public class Main {

    /**
     * Invokes Color Counter application
     *
     * @param args not applicable
     */
    public static void main(String[] args) {
        String fileLocation = JOptionPane.showInputDialog("Input location of a .RAR file to process");
        if (!isInputValid(fileLocation)) {
            showError("Invalid file/input value");
        } else {
            JOptionPane.showMessageDialog(null, "Please wait....");
            ColorCounter colorCounter = new ColorCounter(fileLocation);
            colorCounter.load();
            if (colorCounter.hasError()) {
                showError(colorCounter.getErrorMsg());
            } else {
                colorCounter.process();
            }

            if (colorCounter.hasError()) {
                showError(colorCounter.getErrorMsg());
            } else {
                JOptionPane.showMessageDialog(null, colorCounter.getDisplayResult());
            }
        }

        System.exit(0);
    }

    private static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Validation rules for input value
     *
     * @param value what to check
     * @return true if it passes, false if it doesn't
     */
    private static boolean isInputValid(String value) {
        return value != null &&
                value.length() > 0 &&
                value.toLowerCase().endsWith(".rar");
    }
}
