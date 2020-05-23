// Made by Bastiaan van der Plaat (0983259) from TINPRO02-4 this is my first try

package ml.bastiaan.admino;

import javax.swing.SwingUtilities;

// The main entry point
public class Main {
    private Main() {}

    public static void main(String[] args) {
        // Create an app instance and run in right swing thread
        SwingUtilities.invokeLater(App.getInstance());
    }
}
