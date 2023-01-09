/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orders.pkg;

import javax.swing.JFrame;

/**
 *
 * @author Bozz
 */
public class OrderMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //ΚΑΤΑΣΚΕΥΗ ΤΟΥ ΚΕΝΤΡΙΚΟΥ ΠΑΡΑΘΥΡΟΥ
        OrderFrame frame = new OrderFrame("Order Application");

        //ΚΛΗΣΗ ΜΕΘΟΔΟΥ ΓΙΑ ΤΗΝ ΚΑΤΑΣΚΕΥΗ ΤΗΣ ΕΦΑΡΜΟΓΗΣ
        frame.prepareUI();

        //ΕΝΤΟΛΕΣ ΓΙΑ ΤΗΝ ΕΜΦΑΝΙΣΗ ΤΟΥ ΚΕΝΤΡΙΚΟΥ ΠΑΡΑΘΥΡΟΥ ΣΤΗΝ ΟΘΟΝΗ
        frame.pack();
        frame.setSize(610, 350);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
