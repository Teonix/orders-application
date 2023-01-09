/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orders.pkg;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 *
 * @author Bozz
 */
public class OrderFrame extends JFrame {

    //ΔΗΛΩΣΕΙΣ ΤΩΝ ΜΕΤΑΒΛΗΤΩΝ ΤΗΣ ΕΦΑΡΜΟΓΗΣ(π.χ.ΚΟΥΜΠΙΑ,MENU κτλ.)
    private static final long serialVersionUID = 1L;
    private JMenu fileMenu, infoMenu;
    private JMenuBar menuBar;
    private JMenuItem newOrd, open, save, saveAs, exit, stat, about;
    private JTextArea area;
    private JButton newOrBtn, opBtn, savBtn, savAsBtn, exBtn, stBtn, abBtn;
    private ArrayList<Order> orderList;
    private JScrollPane scrollPane;
    private JLabel pathLbl;
    private JTextField pathTf;

    //ΑΡΧΙΚΟΠΟΙΗΣΕΙΣ ΤΩΝ ΜΕΤΑΒΛΗΤΩΝ ΤΗΣ ΕΦΑΡΜΟΓΗΣ
    public OrderFrame(String title) {
        super(title);

        //MENU
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        infoMenu = new JMenu("Info");

        fileMenu.setMnemonic('F');
        infoMenu.setMnemonic('I');

        //MENU NEW ORDER
        newOrd = fileMenu.add("New Order");
        newOrd.setAccelerator(KeyStroke.getKeyStroke('N', Event.CTRL_MASK));

        fileMenu.addSeparator();

        //MENU OPEN FILE
        open = fileMenu.add("Open");
        open.setAccelerator(KeyStroke.getKeyStroke('O', Event.CTRL_MASK));

        fileMenu.addSeparator();

        //MENU SAVE
        save = fileMenu.add("Save");
        save.setAccelerator(KeyStroke.getKeyStroke('S', Event.CTRL_MASK));
        saveAs = fileMenu.add("Save As...");

        fileMenu.addSeparator();

        //MENU EXIT
        exit = fileMenu.add("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke('E', Event.CTRL_MASK));

        //MENU STATISTICS
        stat = infoMenu.add("Statistics");
        stat.setAccelerator(KeyStroke.getKeyStroke('T', Event.CTRL_MASK));

        infoMenu.addSeparator();

        //MENU ABOUT
        about = infoMenu.add("About");
        about.setAccelerator(KeyStroke.getKeyStroke('A', Event.CTRL_MASK));

        menuBar.add(fileMenu);
        menuBar.add(infoMenu);

        //ΟΘΟΝΗ ΚΕΝΤΡΙΚΟΥ ΠΑΡΑΘΥΡΟΥ
        area = new JTextArea(12, 52);
        area.setEditable(false);

        //ΚΟΥΜΠΙΑ ΚΕΝΤΡΙΚΟΥ ΠΑΡΑΘΥΡΟΥ
        newOrBtn = new JButton("New Order");
        opBtn = new JButton("Open File");
        savBtn = new JButton("Save");
        savAsBtn = new JButton("Save As...");
        stBtn = new JButton("Statistics");
        abBtn = new JButton("About");
        exBtn = new JButton("Exit");

        //ΔΗΜΙΟΥΡΓΙΑ ΛΙΣΤΑΣ ΠΑΡΑΓΓΕΛΙΩΝ
        orderList = new ArrayList();

        //ΔΗΜΙΟΥΡΓΙΑ SCROLLPANE ΓΙΑ ΤΗΝ ΟΘΟΝΗ
        scrollPane = new JScrollPane(area);

        //ΔΗΜΙΟΥΡΓΙΑ ΕΙΔΙΚΟΥ ΠΕΔΙΟΥ ΓΙΑ ΤΟ PATH FILE
        pathLbl = new JLabel();
        pathLbl.setText("File path:");

        pathTf = new JTextField(25);
        pathTf.setText("Not selected path yet.");

    }

    //Η ΜΕΘΟΔΟΣ ΠΟΥ ΑΠΟΤΕΛΕΙ ΤΗΝ "ΚΑΡΔΙΑ" ΤΗΣ ΕΦΑΡΜΟΓΗΣ
    public void prepareUI() {

        //ΤΟΠΟΘΕΤΗΣΗ ΤΟΥ MENU
        setJMenuBar(menuBar);

        //ΔΗΜΙΟΥΡΓΙΑ 1ου ΠΑΝΕΛ ΜΕ ΤΗΝ ΟΘΟΝΗ
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.add(scrollPane);

        //ΔΗΜΙΟΥΡΓΙΑ 2ου ΠΑΝΕΛ ΜΕ ΤΑ ΚΟΥΜΠΙΑ
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());
        panel2.add(newOrBtn);
        panel2.add(opBtn);
        panel2.add(savBtn);
        panel2.add(savAsBtn);
        panel2.add(stBtn);
        panel2.add(abBtn);
        panel2.add(exBtn);

        //ΠΡΟΣΘΗΚΗ ΤΩΝ ΠΑΝΕΛ ΚΑΙ ΤΟΥ ΕΙΔΚΟΥ ΠΕΔΙΟΥ ΣΤΟ ΚΕΝΤΡΙΚΟ ΠΑΡΑΘΥΡΟ
        this.setLayout(new FlowLayout());
        this.add(panel1);
        this.add(panel2);
        this.add(pathLbl);
        this.add(pathTf);

        //ΠΡΟΣΘΗΚΗ WINDOW LISTENER ΚΑΤΑ ΤΗΝ ΕΞΟΔΟ ΜΕ ΕΡΩΤΗΣΗ ΓΙΑ SAVE
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                //ΕΜΦΑΝΙΣΗ ΠΑΡΑΘΥΡΟΥ ΕΡΩΤΗΣΗΣ
                int i = JOptionPane.showConfirmDialog(OrderFrame.this,
                        "Do you want to save the orders before exiting the app?");

                //ΑΝ Η ΑΠΑΝΤΗΣΗ ΕΙΝΑΙ YES
                if (i == JOptionPane.YES_OPTION) {
                    //ΑΝ ΤΟ PATH ΔΕΝ ΕΙΝΑΙ KENO
                    if (!pathTf.getText().equals("Not selected path yet.")) {
                        //ΑΝ Η ΛΙΣΤΑ ΕΙΝΑΙ ΚΕΝΗ
                        if (orderList.isEmpty()) {
                            JOptionPane.showMessageDialog(saveAs,
                                    "Nothing to save.",
                                    "File access error",
                                    JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                        }
                        //ΑΠΟΘΗΚΕΥΣΗ ΤΟΥ PATH ΣΕ ΜΕΤΑΒΛΗΤΗ
                        String savePath = pathTf.getText();

                        //ΚΑΛΕΣΜΑ ΣΥΝΑΡΤΗΣΗΣ ΓΙΑ ΑΠΟΘΗΚΕΥΣΗ ΠΑΡΑΓΓΕΛΙΩΝ
                        saveOrderList(savePath);

                        System.exit(0);
                        //ΑΝ ΤΟ PATH ΕΙΝΑΙ KENO
                    } else {
                        //ΑΝ Η ΛΙΣΤΑ ΕΙΝΑΙ ΚΕΝΗ
                        if (orderList.isEmpty()) {
                            JOptionPane.showMessageDialog(saveAs,
                                    "Nothing to save.",
                                    "File access error",
                                    JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                        }
                        //ΕΚΤΕΛΕΣΗ ΤΟΥ FILECHOOSER ΓΙΑ ΤΟ ΑΝΟΙΓΜΑ ΚΑΠΟΙΟΥ FILE
                        final JFileChooser fc = new JFileChooser();
                        int returnVal = fc.showSaveDialog(saveAs);

                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            //ΑΠΟΘΗΚΕΥΣΗ ΤΟΥ PATH ΣΕ ΜΕΤΑΒΛΗΤΗ
                            String fileName = fc.getSelectedFile().getPath();

                            if (fileName != null && !fileName.isEmpty()) {
                                //ΚΑΛΕΣΜΑ ΣΥΝΑΡΤΗΣΗΣ ΓΙΑ ΑΠΟΘΗΚΕΥΣΗ ΠΑΡΑΓΓΕΛΙΩΝ
                                saveOrderList(fileName);
                            }
                        }

                        System.exit(0);
                    }
                    //ΑΝ Η ΑΠΑΝΤΗΣΗ ΕΙΝΑΙ NO
                } else if (i == JOptionPane.NO_OPTION) {
                    System.exit(0);
                    //ΑΝ Η ΑΠΑΝΤΗΣΗ ΕΙΝΑΙ CANCEL
                } else if (i == JOptionPane.CANCEL_OPTION) {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }

            }
        });

        //ΣΕ ΠΕΡΙΠΤΩΣΗ ΠΟΥ ΠΑΤΗΘΕΙ ΑΠΟ ΤΟ MENU ΤΟ EXIT
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ΕΜΦΑΝΙΣΗ ΠΑΡΑΘΥΡΟΥ ΕΡΩΤΗΣΗΣ
                int i = JOptionPane.showConfirmDialog(OrderFrame.this,
                        "Do you want to save the orders before exiting the app?");

                //ΑΝ Η ΑΠΑΝΤΗΣΗ ΕΙΝΑΙ YES
                if (i == JOptionPane.YES_OPTION) {
                    //ΑΝ ΤΟ PATH ΔΕΝ ΕΙΝΑΙ KENO
                    if (!pathTf.getText().equals("Not selected path yet.")) {
                        //ΑΝ Η ΛΙΣΤΑ ΕΙΝΑΙ ΚΕΝΗ
                        if (orderList.isEmpty()) {
                            JOptionPane.showMessageDialog(exit,
                                    "Nothing to save.",
                                    "File access error",
                                    JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                        }
                        //ΑΠΟΘΗΚΕΥΣΗ ΤΟΥ PATH ΣΕ ΜΕΤΑΒΛΗΤΗ
                        String savePath = pathTf.getText();

                        //ΚΑΛΕΣΜΑ ΜΕΘΟΔΟΥ ΓΙΑ ΑΠΟΘΗΚΕΥΣΗ ΠΑΡΑΓΓΕΛΙΩΝ
                        saveOrderList(savePath);

                        System.exit(0);
                        //ΑΝ ΤΟ PATH ΕΙΝΑΙ KENO
                    } else {

                        //ΑΝ Η ΛΙΣΤΑ ΕΙΝΑΙ ΚΕΝΗ
                        if (orderList.isEmpty()) {
                            JOptionPane.showMessageDialog(exit,
                                    "Nothing to save.",
                                    "File access error",
                                    JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                        }

                        //ΕΚΤΕΛΕΣΗ ΤΟΥ FILECHOOSER ΓΙΑ ΤΟ ΑΝΟΙΓΜΑ ΚΑΠΟΙΟΥ FILE
                        final JFileChooser fc = new JFileChooser();
                        int returnVal = fc.showSaveDialog(exit);

                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            //ΑΠΟΘΗΚΕΥΣΗ ΤΟΥ PATH ΣΕ ΜΕΤΑΒΛΗΤΗ
                            String fileName = fc.getSelectedFile().getPath();

                            //ΚΑΛΕΣΜΑ ΜΕΘΟΔΟΥ ΓΙΑ ΑΠΟΘΗΚΕΥΣΗ ΠΑΡΑΓΓΕΛΙΩΝ
                            if (fileName != null && !fileName.isEmpty()) {
                                saveOrderList(fileName);
                            }
                        }

                        System.exit(0);
                    }

                    //ΑΝ Η ΑΠΑΝΤΗΣΗ ΕΙΝΑΙ NO
                } else if (i == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        });

        //ΣΕ ΠΕΡΙΠΤΩΣΗ ΠΟΥ ΠΑΤΗΘΕΙ ΑΠΟ ΤΟ ΚΟΥΜΠΙ ΤΟ EXIT
        exBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ΕΜΦΑΝΙΣΗ ΠΑΡΑΘΥΡΟΥ ΕΡΩΤΗΣΗΣ
                int i = JOptionPane.showConfirmDialog(OrderFrame.this,
                        "Do you want to save the orders before exiting the app?");

                //ΑΝ Η ΑΠΑΝΤΗΣΗ ΕΙΝΑΙ YES
                if (i == JOptionPane.YES_OPTION) {
                    //ΑΝ ΤΟ PATH ΔΕΝ ΕΙΝΑΙ KENO
                    if (!pathTf.getText().equals("Not selected path yet.")) {
                        //ΑΝ Η ΛΙΣΤΑ ΕΙΝΑΙ ΚΕΝΗ
                        if (orderList.isEmpty()) {
                            JOptionPane.showMessageDialog(exBtn,
                                    "Nothing to save.",
                                    "File access error",
                                    JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                        }
                        //ΑΠΟΘΗΚΕΥΣΗ ΤΟΥ PATH ΣΕ ΜΕΤΑΒΛΗΤΗ
                        String savePath = pathTf.getText();

                        //ΚΑΛΕΣΜΑ ΜΕΘΟΔΟΥ ΓΙΑ ΑΠΟΘΗΚΕΥΣΗ ΠΑΡΑΓΓΕΛΙΩΝ
                        saveOrderList(savePath);

                        System.exit(0);
                        //ΑΝ ΤΟ PATH ΕΙΝΑΙ KENO
                    } else {

                        //ΑΝ Η ΛΙΣΤΑ ΕΙΝΑΙ ΚΕΝΗ
                        if (orderList.isEmpty()) {
                            JOptionPane.showMessageDialog(exBtn,
                                    "Nothing to save.",
                                    "File access error",
                                    JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                        }

                        //ΕΚΤΕΛΕΣΗ ΤΟΥ FILECHOOSER ΓΙΑ ΤΟ ΑΝΟΙΓΜΑ ΚΑΠΟΙΟΥ FILE
                        final JFileChooser fc = new JFileChooser();
                        int returnVal = fc.showSaveDialog(exBtn);

                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            //ΑΠΟΘΗΚΕΥΣΗ ΤΟΥ PATH ΣΕ ΜΕΤΑΒΛΗΤΗ
                            String fileName = fc.getSelectedFile().getPath();

                            //ΚΑΛΕΣΜΑ ΜΕΘΟΔΟΥ ΓΙΑ ΑΠΟΘΗΚΕΥΣΗ ΠΑΡΑΓΓΕΛΙΩΝ
                            if (fileName != null && !fileName.isEmpty()) {
                                saveOrderList(fileName);
                            }
                        }

                        System.exit(0);
                    }

                    //ΑΝ Η ΑΠΑΝΤΗΣΗ ΕΙΝΑΙ NO
                } else if (i == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        });

        //ΣΕ ΠΕΡΙΠΤΩΣΗ ΠΟΥ ΠΑΤΗΘΕΙ ΑΠΟ ΤΟ MENU ΤΟ NEW ORDER
        newOrd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //ΚΑΤΑΣΚΕΥΗ ΤΩΝ ΠΕΔΙΩΝ ΠΟΥ ΜΠΑΙΝΕΙ ΤΟ TEXT
                JTextField appIdTf = new JTextField(20);
                JTextField orderIdTf = new JTextField(20);
                JTextField orderDateTf = new JTextField(20);
                JTextField clientNameTf = new JTextField(20);
                JTextField itemNameTf = new JTextField(20);
                JTextField UnitsCountTf = new JTextField(20);
                JTextField netItemPriceTf = new JTextField(20);
                JTextField taxPercentageTf = new JTextField(20);

                //ΚΑΤΑΣΚΕΥΗ ΤΩΝ ΕΤΙΚΕΤΩΝ
                JLabel appIdLbl = new JLabel("Enter Application ID");
                JLabel orderIdLbl = new JLabel("Enter Order ID");
                JLabel orderDateLbl = new JLabel("Enter Order Date");
                JLabel clientNameLbl = new JLabel("Enter Client Name");
                JLabel itemNameLbl = new JLabel("Enter Item Name");
                JLabel UnitsCountLbl = new JLabel("Enter the number of units");
                JLabel netItemPriceLbl = new JLabel("Enter Net Item Price");
                JLabel taxPercentageLbl = new JLabel("Enter Tax Percentage");

                //ΚΑΤΑΣΚΕΥΗ ΤΟΥ ΚΟΥΜΠΙΟΥ
                JButton complBtn = new JButton("Complete Order");

                //ΚΑΤΑΣΚΕΥΗ ΤΟΥ ΠΑΡΑΘΥΡΟΥ NEW ORDER
                JFrame openFrame = new JFrame("New Order");
                openFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
                openFrame.add(appIdLbl);
                openFrame.add(appIdTf);
                openFrame.add(orderIdLbl);
                openFrame.add(orderIdTf);
                openFrame.add(orderDateLbl);
                openFrame.add(orderDateTf);
                openFrame.add(clientNameLbl);
                openFrame.add(clientNameTf);
                openFrame.add(itemNameLbl);
                openFrame.add(itemNameTf);
                openFrame.add(UnitsCountLbl);
                openFrame.add(UnitsCountTf);
                openFrame.add(netItemPriceLbl);
                openFrame.add(netItemPriceTf);
                openFrame.add(taxPercentageLbl);
                openFrame.add(taxPercentageTf);
                openFrame.add(complBtn);
                openFrame.pack();
                openFrame.setSize(250, 450);
                openFrame.setLocationRelativeTo(null);
                openFrame.setVisible(true);
                openFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                //ΣΕ ΠΕΡΙΠΤΩΣΗ ΠΟΥ ΠΑΤΗΘΕΙ ΤΟ ΚΟΥΜΠΙ
                complBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String appId, orderId, orderDate, clientName,
                                itemName, UnitsCount, netItemPrice, taxPercentage;

                        //ΠΑΙΡΝΕΙ ΤΙΣ ΕΙΣΟΔΟΥΣ ΤΟΥ ΧΡΗΣΤΗ
                        appId = appIdTf.getText();
                        orderId = orderIdTf.getText();
                        orderDate = orderDateTf.getText();
                        clientName = clientNameTf.getText();
                        itemName = itemNameTf.getText();
                        UnitsCount = UnitsCountTf.getText();
                        netItemPrice = netItemPriceTf.getText();
                        taxPercentage = taxPercentageTf.getText();

                        //ΕΛΕΓΧΟΣ ΑΝ ΕΙΝΑΙ ΚΑΠΟΙΟ ΠΕΔΙΟ ΚΕΝΟ
                        if (appId.equals("") || orderId.equals("") || orderDate.equals("") || clientName.equals("")
                                || itemName.equals("") || UnitsCount.equals("") || taxPercentage.equals("")) {
                            JOptionPane.showMessageDialog(complBtn,
                                    "Please complete the whole form.",
                                    "Error", JOptionPane.ERROR_MESSAGE);

                            //ΕΛΕΓΧΟΣ ΑΝ ΤΟ APP ID ΠΕΡΙΕΧΕΙ ΤΟ ΔΙΚΟ ΜΟΥ Α.Μ.(12345)
                        } else if (!appId.equals("12345")) {
                            JOptionPane.showMessageDialog(complBtn,
                                    "This app has a unique application ID.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            appIdTf.setText("");
                        } else {
                            //ΕΛΕΓΧΟΣ ΑΝ Η ΤΙΜΗ,Ο ΦΟΡΟΣ ΚΑΙ Η ΠΟΣΟΤΗΤΑ ΕΙΝΑΙ ΑΡΙΘΜΟΙ
                            try {
                                int units = Integer.parseInt(UnitsCount);
                                double price = Double.valueOf(netItemPrice);
                                double tax = Double.valueOf(taxPercentage);
                                Order order = new Order();
                                order.setAppId(appId);
                                order.setOrderId(orderId);
                                order.setOrderDate(orderDate);
                                order.setClientName(clientName);
                                order.setItemName(itemName);
                                order.setUnitsCount(UnitsCount);
                                order.setNetItemPrice(netItemPrice);
                                order.setTaxPercentage(taxPercentage);

                                orderList.add(order);

                                //ΤΥΠΩΣΗ ΤΗΣ ΠΑΡΑΓΓΕΛΙΑΣ ΣΤΗΝ ΟΘΟΝΗ
                                area.append(order.printScreen());
                                area.append("\n");

                                openFrame.dispose();

                            } catch (NumberFormatException d) {
                                JOptionPane.showMessageDialog(complBtn,
                                        "Please insert the correct values.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                UnitsCountTf.setText("");
                                netItemPriceTf.setText("");
                                taxPercentageTf.setText("");
                            }

                        }
                    }
                });

            }

        });

        //ΣΕ ΠΕΡΙΠΤΩΣΗ ΠΟΥ ΠΑΤΗΘΕΙ ΑΠΟ ΤΟ ΚΟΥΜΠΙ ΤΟ NEW ORDER
        newOrBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //ΚΑΤΑΣΚΕΥΗ ΤΩΝ ΠΕΔΙΩΝ ΠΟΥ ΜΠΑΙΝΕΙ ΤΟ TEXT
                JTextField appIdTf = new JTextField(20);
                JTextField orderIdTf = new JTextField(20);
                JTextField orderDateTf = new JTextField(20);
                JTextField clientNameTf = new JTextField(20);
                JTextField itemNameTf = new JTextField(20);
                JTextField UnitsCountTf = new JTextField(20);
                JTextField netItemPriceTf = new JTextField(20);
                JTextField taxPercentageTf = new JTextField(20);

                //ΚΑΤΑΣΚΕΥΗ ΤΩΝ ΕΤΙΚΕΤΩΝ
                JLabel appIdLbl = new JLabel("Enter Application ID");
                JLabel orderIdLbl = new JLabel("Enter Order ID");
                JLabel orderDateLbl = new JLabel("Enter Order Date");
                JLabel clientNameLbl = new JLabel("Enter Client Name");
                JLabel itemNameLbl = new JLabel("Enter Item Name");
                JLabel UnitsCountLbl = new JLabel("Enter the number of units");
                JLabel netItemPriceLbl = new JLabel("Enter Net Item Price");
                JLabel taxPercentageLbl = new JLabel("Enter Tax Percentage");

                //ΚΑΤΑΣΚΕΥΗ ΤΟΥ ΚΟΥΜΠΙΟΥ
                JButton complBtn = new JButton("Complete Order");

                //ΚΑΤΑΣΚΕΥΗ ΤΟΥ ΠΑΡΑΘΥΡΟΥ NEW ORDER
                JFrame openFrame = new JFrame("New Order");
                openFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
                openFrame.add(appIdLbl);
                openFrame.add(appIdTf);
                openFrame.add(orderIdLbl);
                openFrame.add(orderIdTf);
                openFrame.add(orderDateLbl);
                openFrame.add(orderDateTf);
                openFrame.add(clientNameLbl);
                openFrame.add(clientNameTf);
                openFrame.add(itemNameLbl);
                openFrame.add(itemNameTf);
                openFrame.add(UnitsCountLbl);
                openFrame.add(UnitsCountTf);
                openFrame.add(netItemPriceLbl);
                openFrame.add(netItemPriceTf);
                openFrame.add(taxPercentageLbl);
                openFrame.add(taxPercentageTf);
                openFrame.add(complBtn);
                openFrame.pack();
                openFrame.setSize(250, 450);
                openFrame.setLocationRelativeTo(null);
                openFrame.setVisible(true);
                openFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                //ΣΕ ΠΕΡΙΠΤΩΣΗ ΠΟΥ ΠΑΤΗΘΕΙ ΤΟ ΚΟΥΜΠΙ
                complBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String appId, orderId, orderDate, clientName,
                                itemName, UnitsCount, netItemPrice, taxPercentage;

                        //ΠΑΙΡΝΕΙ ΤΙΣ ΕΙΣΟΔΟΥΣ ΤΟΥ ΧΡΗΣΤΗ
                        appId = appIdTf.getText();
                        orderId = orderIdTf.getText();
                        orderDate = orderDateTf.getText();
                        clientName = clientNameTf.getText();
                        itemName = itemNameTf.getText();
                        UnitsCount = UnitsCountTf.getText();
                        netItemPrice = netItemPriceTf.getText();
                        taxPercentage = taxPercentageTf.getText();

                        //ΕΛΕΓΧΟΣ ΑΝ ΕΙΝΑΙ ΚΑΠΟΙΟ ΠΕΔΙΟ ΚΕΝΟ
                        if (appId.equals("") || orderId.equals("") || orderDate.equals("") || clientName.equals("")
                                || itemName.equals("") || UnitsCount.equals("") || taxPercentage.equals("")) {
                            JOptionPane.showMessageDialog(complBtn,
                                    "Please complete the whole form.",
                                    "Error", JOptionPane.ERROR_MESSAGE);

                            ////ΕΛΕΓΧΟΣ ΑΝ ΤΟ APP ID ΠΕΡΙΕΧΕΙ ΤΟ ΔΙΚΟ ΜΟΥ Α.Μ.(12345)
                        } else if (!appId.equals("12345")) {
                            JOptionPane.showMessageDialog(complBtn,
                                    "This app has a unique application ID.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            appIdTf.setText("");
                        } else {
                            //ΕΛΕΓΧΟΣ ΑΝ Η ΤΙΜΗ,Ο ΦΟΡΟΣ ΚΑΙ Η ΠΟΣΟΤΗΤΑ ΕΙΝΑΙ ΑΡΙΘΜΟΙ
                            try {
                                int units = Integer.parseInt(UnitsCount);
                                double price = Double.valueOf(netItemPrice);
                                double tax = Double.valueOf(taxPercentage);
                                Order order = new Order();
                                order.setAppId(appId);
                                order.setOrderId(orderId);
                                order.setOrderDate(orderDate);
                                order.setClientName(clientName);
                                order.setItemName(itemName);
                                order.setUnitsCount(UnitsCount);
                                order.setNetItemPrice(netItemPrice);
                                order.setTaxPercentage(taxPercentage);

                                orderList.add(order);

                                //ΤΥΠΩΣΗ ΤΗΣ ΠΑΡΑΓΓΕΛΙΑΣ ΣΤΗΝ ΟΘΟΝΗ
                                area.append(order.printScreen());
                                area.append("\n");

                                openFrame.dispose();

                            } catch (NumberFormatException d) {
                                JOptionPane.showMessageDialog(complBtn,
                                        "Please insert the correct values.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                UnitsCountTf.setText("");
                                netItemPriceTf.setText("");
                                taxPercentageTf.setText("");
                            }

                        }
                    }
                });

            }

        });

        //ΣΕ ΠΕΡΙΠΤΩΣΗ ΠΟΥ ΠΑΤΗΘΕΙ ΑΠΟ ΤΟ MENU ΤΟ SAVE AS
        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //ΑΝ Η ΛΙΣΤΑ ΕΙΝΑΙ ΑΔΕΙΑ
                if (orderList.isEmpty()) {
                    JOptionPane.showMessageDialog(saveAs,
                            "Nothing to save.",
                            "File access error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //ΕΚΤΕΛΕΣΗ ΤΟΥ FILECHOOSER ΓΙΑ ΤΟ ΑΝΟΙΓΜΑ ΚΑΠΟΙΟΥ FILE
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showSaveDialog(saveAs);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    //ΑΠΟΘΗΚΕΥΣΗ ΤΟΥ PATH ΣΕ ΜΕΤΑΒΛΗΤΗ
                    String fileName = fc.getSelectedFile().getPath();

                    if (fileName != null && !fileName.isEmpty()) {
                        //ΚΑΛΕΣΜΑ ΜΕΘΟΔΟΥ ΓΙΑ ΑΠΟΘΗΚΕΥΣΗ ΤΩΝ ΠΑΡΑΓΓΕΛΙΩΝ
                        saveOrderList(fileName);
                    }
                }

            }
        });

        //ΣΕ ΠΕΡΙΠΤΩΣΗ ΠΟΥ ΠΑΤΗΘΕΙ ΑΠΟ ΤΟ ΚΟΥΜΠΙ ΤΟ SAVE AS
        savAsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ΑΝ Η ΛΙΣΤΑ ΕΙΝΑΙ ΑΔΕΙΑ
                if (orderList.isEmpty()) {
                    JOptionPane.showMessageDialog(savAsBtn,
                            "Nothing to save.",
                            "File access error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //ΕΚΤΕΛΕΣΗ ΤΟΥ FILECHOOSER ΓΙΑ ΤΟ ΑΝΟΙΓΜΑ ΚΑΠΟΙΟΥ FILE
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showSaveDialog(savAsBtn);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    //ΑΠΟΘΗΚΕΥΣΗ ΤΟΥ PATH ΣΕ ΜΕΤΑΒΛΗΤΗ
                    String fileName = fc.getSelectedFile().getPath();

                    if (fileName != null && !fileName.isEmpty()) {
                        //ΚΑΛΕΣΜΑ ΜΕΘΟΔΟΥ ΓΙΑ ΑΠΟΘΗΚΕΥΣΗ ΤΩΝ ΠΑΡΑΓΓΕΛΙΩΝ
                        saveOrderList(fileName);
                    }
                }

            }
        });

        //ΣΕ ΠΕΡΙΠΤΩΣΗ ΠΟΥ ΠΑΤΗΘΕΙ ΑΠΟ ΤΟ MENU ΤΟ SAVE
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //ΑΝ ΤΟ PATH ΔΕΝ ΕΙΝΑΙ KENO
                if (!pathTf.getText().equals("Not selected path yet.")) {

                    //ΑΝ Η ΛΙΣΤΑ ΕΙΝΑΙ ΚΕΝΗ
                    if (orderList.isEmpty()) {
                        JOptionPane.showMessageDialog(save,
                                "Nothing to save.",
                                "File access error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    //ΑΠΟΘΗΚΕΥΣΗ ΤΟΥ PATH ΣΕ ΜΕΤΑΒΛΗΤΗ
                    String savePath = pathTf.getText();

                    //ΚΑΛΕΣΜΑ ΜΕΘΟΔΟΥ ΓΙΑ ΑΠΟΘΗΚΕΥΣΗ ΠΑΡΑΓΓΕΛΙΩΝ
                    saveOrderList(savePath);

                    //ΑΝ ΤΟ PATH ΕΙΝΑΙ KENO
                } else {

                    //ΑΝ Η ΛΙΣΤΑ ΕΙΝΑΙ ΚΕΝΗ
                    if (orderList.isEmpty()) {
                        JOptionPane.showMessageDialog(save,
                                "Nothing to save.",
                                "File access error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    //ΕΚΤΕΛΕΣΗ ΤΟΥ FILECHOOSER ΓΙΑ ΤΟ ΑΝΟΙΓΜΑ ΚΑΠΟΙΟΥ FILE
                    final JFileChooser fc = new JFileChooser();
                    int returnVal = fc.showSaveDialog(save);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        //ΑΠΟΘΗΚΕΥΣΗ ΤΟΥ PATH ΣΕ ΜΕΤΑΒΛΗΤΗ
                        String fileName = fc.getSelectedFile().getPath();

                        if (fileName != null && !fileName.isEmpty()) {
                            //ΚΑΛΕΣΜΑ ΜΕΘΟΔΟΥ ΓΙΑ ΑΠΟΘΗΚΕΥΣΗ ΠΑΡΑΓΓΕΛΙΩΝ
                            saveOrderList(fileName);
                        }
                    }
                }
            }
        });

        //ΣΕ ΠΕΡΙΠΤΩΣΗ ΠΟΥ ΠΑΤΗΘΕΙ ΑΠΟ ΤΟ ΚΟΥΜΠΙ ΤΟ SAVE
        savBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //ΑΝ ΤΟ PATH ΔΕΝ ΕΙΝΑΙ KENO
                if (!pathTf.getText().equals("Not selected path yet.")) {

                    //ΑΝ Η ΛΙΣΤΑ ΕΙΝΑΙ ΚΕΝΗ
                    if (orderList.isEmpty()) {
                        JOptionPane.showMessageDialog(savBtn,
                                "Nothing to save.",
                                "File access error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    //ΑΠΟΘΗΚΕΥΣΗ ΤΟΥ PATH ΣΕ ΜΕΤΑΒΛΗΤΗ
                    String savePath = pathTf.getText();
                    //ΚΑΛΕΣΜΑ ΜΕΘΟΔΟΥ ΓΙΑ ΑΠΟΘΗΚΕΥΣΗ ΠΑΡΑΓΓΕΛΙΩΝ
                    saveOrderList(savePath);

                    //ΑΝ ΤΟ PATH ΕΙΝΑΙ KENO
                } else {

                    //ΑΝ Η ΛΙΣΤΑ ΕΙΝΑΙ ΚΕΝΗ
                    if (orderList.isEmpty()) {
                        JOptionPane.showMessageDialog(savBtn,
                                "Nothing to save.",
                                "File access error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    //ΕΚΤΕΛΕΣΗ ΤΟΥ FILECHOOSER ΓΙΑ ΤΟ ΑΝΟΙΓΜΑ ΚΑΠΟΙΟΥ FILE
                    final JFileChooser fc = new JFileChooser();
                    int returnVal = fc.showSaveDialog(savBtn);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        //ΑΠΟΘΗΚΕΥΣΗ ΤΟΥ PATH ΣΕ ΜΕΤΑΒΛΗΤΗ
                        String fileName = fc.getSelectedFile().getPath();

                        if (fileName != null && !fileName.isEmpty()) {
                            //ΚΑΛΕΣΜΑ ΜΕΘΟΔΟΥ ΓΙΑ ΑΠΟΘΗΚΕΥΣΗ ΠΑΡΑΓΓΕΛΙΩΝ
                            saveOrderList(fileName);
                        }
                    }
                }
            }
        });

        //ΣΕ ΠΕΡΙΠΤΩΣΗ ΠΟΥ ΠΑΤΗΘΕΙ ΑΠΟ ΤΟ MENU ΤΟ OPEN
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //ΕΚΤΕΛΕΣΗ ΤΟΥ FILECHOOSER ΓΙΑ ΤΟ ΑΝΟΙΓΜΑ ΚΑΠΟΙΟΥ FILE
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(open);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    //ΑΠΟΘΗΚΕΥΣΗ ΤΟΥ PATH ΣΕ ΜΕΤΑΒΛΗΤΗ
                    String fileName = fc.getSelectedFile().getPath();

                    if (fileName != null && !fileName.isEmpty()) {
                        //ΚΑΛΕΣΜΑ ΜΕΘΟΔΟΥ ΓΙΑ ΤΟ ΑΝΟΙΓΜΑ FILE
                        int i = openFromFile(fileName);
                        if (i == 1) {
                            //ΑΝ ΠΕΤΥΧΕ ΤΟ ΑΝΟΙΓΜΑ ΜΠΑΙΝΕΙ ΣΤΟ ΕΙΔΙΚΟ ΠΕΔΙΟ ΤΟ PATH
                            pathTf.setText(fileName);
                        }
                    }

                }
            }
        });

        //ΣΕ ΠΕΡΙΠΤΩΣΗ ΠΟΥ ΠΑΤΗΘΕΙ ΑΠΟ ΤΟ ΚΟΥΜΠΙ ΤΟ OPEN
        opBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //ΕΚΤΕΛΕΣΗ ΤΟΥ FILECHOOSER ΓΙΑ ΤΟ ΑΝΟΙΓΜΑ ΚΑΠΟΙΟΥ FILE
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(opBtn);

                if (returnVal == JFileChooser.APPROVE_OPTION) {

                    //ΑΠΟΘΗΚΕΥΣΗ ΤΟΥ PATH ΣΕ ΜΕΤΑΒΛΗΤΗ
                    String fileName = fc.getSelectedFile().getPath();

                    if (fileName != null && !fileName.isEmpty()) {
                        //ΚΑΛΕΣΜΑ ΜΕΘΟΔΟΥ ΓΙΑ ΤΟ ΑΝΟΙΓΜΑ FILE
                        int i = openFromFile(fileName);
                        if (i == 1) {
                            //ΑΝ ΠΕΤΥΧΕ ΤΟ ΑΝΟΙΓΜΑ ΜΠΑΙΝΕΙ ΣΤΟ ΕΙΔΙΚΟ ΠΕΔΙΟ ΤΟ PATH
                            pathTf.setText(fileName);
                        }
                    }
                }
            }
        });

        //ΣΕ ΠΕΡΙΠΤΩΣΗ ΠΟΥ ΠΑΤΗΘΕΙ ΑΠΟ ΤΟ MENU ΤΟ STATISTICS
        stat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //ΚΑΤΑΣΚΕΥΗ ΤΟΥ ΠΑΡΑΘΥΡΟΥ STATISTICS
                JFrame statFrame = new JFrame("Statistics");
                statFrame.setLayout(new FlowLayout());

                //ΟΡΙΣΜΟΣ ΕΤΙΚΕΤΩΝ
                JLabel ordSizeLbl = new JLabel("The number of orders:");
                JLabel sumKathLbl = new JLabel("Total cost of orders without tax:");
                JLabel sumMiktLbl = new JLabel("Total cost of orders with tax:");
                JLabel minOrdLbl = new JLabel("Order id of mininum cost order:");
                JLabel maxOrdLbl = new JLabel("Order id of maximum cost order:");

                //ΚΑΤΑΣΚΕΥΗ ΤΩΝ ΠΕΔΙΩΝ ΓΙΑ ΝΑ ΜΠΟΥΝ ΟΙ ΑΠΑΝΤΗΣΕΙΣ
                JTextArea ordSizeTa = new JTextArea(2, 15);
                ordSizeTa.setEditable(false);
                JTextArea sumKathTa = new JTextArea(2, 15);
                sumKathTa.setEditable(false);
                JTextArea sumMiktTa = new JTextArea(2, 15);
                sumMiktTa.setEditable(false);
                JTextArea minOrdTa = new JTextArea(2, 15);
                minOrdTa.setEditable(false);
                JTextArea maxOrdTa = new JTextArea(2, 15);
                maxOrdTa.setEditable(false);

                //ΕΛΕΓΧΟΣ ΑΝ Η ΟΘΟΝΗ ΔΕΝ ΕΧΕΙ ΠΑΡΑΓΓΕΛΙΕΣ
                if (area.getText().equals("")) {
                    JOptionPane.showMessageDialog(stat,
                            "No orders available.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    statFrame.add(ordSizeLbl);
                    statFrame.add(ordSizeTa);
                    statFrame.add(sumKathLbl);
                    statFrame.add(sumKathTa);
                    statFrame.add(sumMiktLbl);
                    statFrame.add(sumMiktTa);
                    statFrame.add(minOrdLbl);
                    statFrame.add(minOrdTa);
                    statFrame.add(maxOrdLbl);
                    statFrame.add(maxOrdTa);
                    statFrame.pack();
                    statFrame.setSize(230, 350);
                    statFrame.setLocationRelativeTo(null);
                    statFrame.setVisible(true);
                    statFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    int i, orderSize;
                    String minOrdId = "", maxOrdId = "";
                    double sumKath = 0, sumMikt = 0, tax, kath, mikt;
                    //ΟΡΙΣΜΟΣ ΩΣ MIN ΤΗΝ ΠΡΩΤΗ ΜΙΚΤΗ ΤΙΜΗ ΤΗΣ ΛΙΣΤΑΣ ΜΕ ΜΕΤΑΤΡΟΠΗ ΑΠΟ STRING
                    double min = (Double.valueOf(orderList.get(0).getNetItemPrice())
                            + (Double.valueOf(orderList.get(0).getNetItemPrice())
                            * ((Double.valueOf(orderList.get(0).getTaxPercentage())) / 100)))
                            * (Integer.parseInt(orderList.get(0).getUnitsCount()));
                    //ΟΡΙΣΜΟΣ ΩΣ MAX ΤΗΝ ΠΡΩΤΗ ΜΙΚΤΗ ΤΙΜΗ ΤΗΣ ΛΙΣΤΑΣ ΜΕ ΜΕΤΑΤΡΟΠΗ ΑΠΟ STRING
                    double max = (Double.valueOf(orderList.get(0).getNetItemPrice())
                            + (Double.valueOf(orderList.get(0).getNetItemPrice())
                            * ((Double.valueOf(orderList.get(0).getTaxPercentage())) / 100)))
                            * (Integer.parseInt(orderList.get(0).getUnitsCount()));

                    orderSize = orderList.size();

                    //ΛΟΥΠΑ ΟΛΩΝ ΤΩΝ ΠΑΡΑΓΓΕΛΙΩΝ ΑΠΟ ΤΗΝ ΛΙΣΤΑ
                    for (i = 0; i < orderList.size(); i++) {
                        //ΓΙΑ ΝΑ ΓΙΝΟΥΝ ΟΙ ΠΡΑΞΕΙΣ,ΑΝΑΓΚΑΣΤΙΚΑ ΠΡΕΠΕΙ ΝΑ ΓΙΝΕΙ ΜΕΤΑΤΡΟΠΗ ΑΠΟ STRING
                        kath = Double.valueOf(orderList.get(i).getNetItemPrice()) * Integer.parseInt(orderList.get(i).getUnitsCount());
                        tax = ((Double.valueOf(orderList.get(i).getTaxPercentage())) / 100);
                        mikt = kath + (kath * tax);
                        sumKath = sumKath + kath;
                        sumMikt = sumMikt + mikt;

                        //ΕΥΡΕΣΗ MIN
                        if (mikt < min) {
                            min = mikt;
                            minOrdId = orderList.get(i).getOrderId();
                        }

                        //ΕΥΡΕΣΗ MAX
                        if (mikt > max) {
                            max = mikt;
                            maxOrdId = orderList.get(i).getOrderId();
                        }
                    }

                    //ΤΥΠΩΣΗ ΤΩΝ ΑΠΟΤΕΛΕΣΜΑΤΩΝ ΣΤΑ ΠΕΔΙΑ
                    ordSizeTa.append(String.valueOf(orderSize));
                    sumKathTa.append(String.valueOf(sumKath));
                    sumMiktTa.append(String.valueOf(sumMikt));
                    minOrdTa.append(minOrdId);
                    maxOrdTa.append(maxOrdId);

                }
            }

        });

        //ΣΕ ΠΕΡΙΠΤΩΣΗ ΠΟΥ ΠΑΤΗΘΕΙ ΑΠΟ ΤΟ ΚΟΥΜΠΙ ΤΟ STATISTICS
        stBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //ΚΑΤΑΣΚΕΥΗ ΤΟΥ ΠΑΡΑΘΥΡΟΥ STATISTICS
                JFrame statFrame = new JFrame("Statistics");
                statFrame.setLayout(new FlowLayout());

                //ΟΡΙΣΜΟΣ ΕΤΙΚΕΤΩΝ
                JLabel ordSizeLbl = new JLabel("The number of orders:");
                JLabel sumKathLbl = new JLabel("Total cost of orders without tax:");
                JLabel sumMiktLbl = new JLabel("Total cost of orders with tax:");
                JLabel minOrdLbl = new JLabel("Order id of mininum cost order:");
                JLabel maxOrdLbl = new JLabel("Order id of maximum cost order:");

                //ΚΑΤΑΣΚΕΥΗ ΤΩΝ ΠΕΔΙΩΝ ΓΙΑ ΝΑ ΜΠΟΥΝ ΟΙ ΑΠΑΝΤΗΣΕΙΣ
                JTextArea ordSizeTa = new JTextArea(2, 15);
                ordSizeTa.setEditable(false);
                JTextArea sumKathTa = new JTextArea(2, 15);
                sumKathTa.setEditable(false);
                JTextArea sumMiktTa = new JTextArea(2, 15);
                sumMiktTa.setEditable(false);
                JTextArea minOrdTa = new JTextArea(2, 15);
                minOrdTa.setEditable(false);
                JTextArea maxOrdTa = new JTextArea(2, 15);
                maxOrdTa.setEditable(false);

                //ΕΛΕΓΧΟΣ ΑΝ Η ΟΘΟΝΗ ΔΕΝ ΕΧΕΙ ΠΑΡΑΓΓΕΛΙΕΣ
                if (area.getText().equals("")) {
                    JOptionPane.showMessageDialog(stBtn,
                            "No orders available.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    statFrame.add(ordSizeLbl);
                    statFrame.add(ordSizeTa);
                    statFrame.add(sumKathLbl);
                    statFrame.add(sumKathTa);
                    statFrame.add(sumMiktLbl);
                    statFrame.add(sumMiktTa);
                    statFrame.add(minOrdLbl);
                    statFrame.add(minOrdTa);
                    statFrame.add(maxOrdLbl);
                    statFrame.add(maxOrdTa);
                    statFrame.pack();
                    statFrame.setSize(230, 350);
                    statFrame.setLocationRelativeTo(null);
                    statFrame.setVisible(true);
                    statFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    int i, orderSize;
                    String minOrdId = "", maxOrdId = "";
                    double sumKath = 0, sumMikt = 0, tax, kath, mikt;
                    //ΟΡΙΣΜΟΣ ΩΣ MIN ΤΗΝ ΠΡΩΤΗ ΜΙΚΤΗ ΤΙΜΗ ΤΗΣ ΛΙΣΤΑΣ ΜΕ ΜΕΤΑΤΡΟΠΗ ΑΠΟ STRING
                    double min = (Double.valueOf(orderList.get(0).getNetItemPrice())
                            + (Double.valueOf(orderList.get(0).getNetItemPrice())
                            * ((Double.valueOf(orderList.get(0).getTaxPercentage())) / 100)))
                            * (Integer.parseInt(orderList.get(0).getUnitsCount()));
                    //ΟΡΙΣΜΟΣ ΩΣ MAX ΤΗΝ ΠΡΩΤΗ ΜΙΚΤΗ ΤΙΜΗ ΤΗΣ ΛΙΣΤΑΣ ΜΕ ΜΕΤΑΤΡΟΠΗ ΑΠΟ STRING
                    double max = (Double.valueOf(orderList.get(0).getNetItemPrice())
                            + (Double.valueOf(orderList.get(0).getNetItemPrice())
                            * ((Double.valueOf(orderList.get(0).getTaxPercentage())) / 100)))
                            * (Integer.parseInt(orderList.get(0).getUnitsCount()));

                    orderSize = orderList.size();

                    //ΛΟΥΠΑ ΟΛΩΝ ΤΩΝ ΠΑΡΑΓΓΕΛΙΩΝ ΑΠΟ ΤΗΝ ΛΙΣΤΑ
                    for (i = 0; i < orderList.size(); i++) {
                        //ΓΙΑ ΝΑ ΓΙΝΟΥΝ ΟΙ ΠΡΑΞΕΙΣ,ΑΝΑΓΚΑΣΤΙΚΑ ΠΡΕΠΕΙ ΝΑ ΓΙΝΕΙ ΜΕΤΑΤΡΟΠΗ ΑΠΟ STRING
                        kath = Double.valueOf(orderList.get(i).getNetItemPrice()) * Integer.parseInt(orderList.get(i).getUnitsCount());
                        tax = ((Double.valueOf(orderList.get(i).getTaxPercentage())) / 100);
                        mikt = kath + (kath * tax);
                        sumKath = sumKath + kath;
                        sumMikt = sumMikt + mikt;

                        //ΕΥΡΕΣΗ MIN
                        if (mikt < min) {
                            min = mikt;
                            minOrdId = orderList.get(i).getOrderId();
                        }

                        //ΕΥΡΕΣΗ MAX
                        if (mikt > max) {
                            max = mikt;
                            maxOrdId = orderList.get(i).getOrderId();
                        }
                    }

                    //ΤΥΠΩΣΗ ΤΩΝ ΑΠΟΤΕΛΕΣΜΑΤΩΝ ΣΤΑ ΠΕΔΙΑ
                    ordSizeTa.append(String.valueOf(orderSize));
                    sumKathTa.append(String.valueOf(sumKath));
                    sumMiktTa.append(String.valueOf(sumMikt));
                    minOrdTa.append(minOrdId);
                    maxOrdTa.append(maxOrdId);

                }
            }

        });

        //ΣΕ ΠΕΡΙΠΤΩΣΗ ΠΟΥ ΠΑΤΗΘΕΙ ΑΠΟ ΤΟ MENU ΤΟ ABOUT
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ΚΑΤΑΣΚΕΥΗ ΤΟΥ ΠΑΡΑΘΥΡΟΥ ABOUT
                JFrame aboutFrame = new JFrame("About");
                aboutFrame.setLayout(new FlowLayout());

                //ΚΑΤΑΣΚΕΥΗ ΤΩΝ ΕΤΙΚΕΤΩΝ
                JLabel onomaLbl = new JLabel("Name of the creator:");
                JLabel epwnLbl = new JLabel("Surname of the creator:");
                JLabel amLbl = new JLabel("AM of the creator:");
                JLabel timeLbl = new JLabel("Duration of the exercise:");

                //ΚΑΤΑΣΚΕΥΗ ΤΩΝ ΠΕΔΙΩΝ
                JTextArea onomaTa = new JTextArea(1, 15);
                onomaTa.setEditable(false);
                JTextArea epwnTa = new JTextArea(1, 15);
                epwnTa.setEditable(false);
                JTextArea amTa = new JTextArea(1, 15);
                amTa.setEditable(false);
                JTextArea timeTa = new JTextArea(1, 15);
                timeTa.setEditable(false);

                //ΕΙΣΑΓΩΓΗ ΤΗΣ ΦΩΤΟΓΡΑΦΙΑΣ ΤΟΥ DESKTOP ΣΕ ΕΤΙΚΕΤΑ
                Image img = new ImageIcon(this.getClass().getResource("desktop.PNG"))
                        .getImage().getScaledInstance(1366, 768, Image.SCALE_SMOOTH);
                JLabel photo = new JLabel();
                photo.setIcon(new ImageIcon(img));

                //ΚΑΤΑΣΚΕΥΗ ΤΟΥ ΠΑΝΕΛ ΠΟΥ ΘΑ ΜΠΕΙ Η ΦΩΤΟΓΡΑΦΙΑ
                JPanel panelPhoto = new JPanel();
                panelPhoto.setLayout(new FlowLayout());
                panelPhoto.add(photo);

                //ΚΑΤΑΣΚΕΥΗ ΤΟΥ ΠΑΝΕΛ ΠΟΥ ΘΑ ΜΠΟΥΝ ΤΑ ΣΤΟΙΧΕΙΑ ΤΟΥ ΚΑΤΑΣΚΕΥΑΣΤΗ
                JPanel panelCreator = new JPanel();
                panelCreator.setLayout(new FlowLayout());
                panelCreator.add(onomaLbl);
                panelCreator.add(onomaTa);
                panelCreator.add(epwnLbl);
                panelCreator.add(epwnTa);
                panelCreator.add(amLbl);
                panelCreator.add(amTa);
                panelCreator.add(timeLbl);
                panelCreator.add(timeTa);

                //ΤΟΠΟΘΕΤΗΣΗ ΤΩΝ ΠΑΝΕΛ ΣΤΟ ΠΑΡΑΘΥΡΟ
                aboutFrame.add(panelCreator, BorderLayout.NORTH);
                aboutFrame.add(panelPhoto, BorderLayout.CENTER);

                //ΕΜΦΑΝΙΣΗ ΤΩΝ ΣΤΟΙΧΕΙΩΝ ΚΑΙ ΤΗΣ ΦΩΤΟΓΡΑΦΙΑΣ
                onomaTa.append("Όνομα");
                epwnTa.append("Επίθετο");
                amTa.append("12345");
                timeTa.append("25/5/2020-28/5/2020");
                aboutFrame.pack();
                aboutFrame.setSize(1500, 890);
                aboutFrame.setLocationRelativeTo(null);
                aboutFrame.setVisible(true);
                aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            }
        });

        //ΣΕ ΠΕΡΙΠΤΩΣΗ ΠΟΥ ΠΑΤΗΘΕΙ ΑΠΟ ΤΟ ΚΟΥΜΠΙ ΤΟ ABOUT
        abBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ΚΑΤΑΣΚΕΥΗ ΤΟΥ ΠΑΡΑΘΥΡΟΥ ABOUT
                JFrame aboutFrame = new JFrame("About");
                aboutFrame.setLayout(new FlowLayout());

                //ΚΑΤΑΣΚΕΥΗ ΤΩΝ ΕΤΙΚΕΤΩΝ
                JLabel onomaLbl = new JLabel("Name of the creator:");
                JLabel epwnLbl = new JLabel("Surname of the creator:");
                JLabel amLbl = new JLabel("AM of the creator:");
                JLabel timeLbl = new JLabel("Duration of the exercise:");

                //ΚΑΤΑΣΚΕΥΗ ΤΩΝ ΠΕΔΙΩΝ
                JTextArea onomaTa = new JTextArea(1, 15);
                onomaTa.setEditable(false);
                JTextArea epwnTa = new JTextArea(1, 15);
                epwnTa.setEditable(false);
                JTextArea amTa = new JTextArea(1, 15);
                amTa.setEditable(false);
                JTextArea timeTa = new JTextArea(1, 15);
                timeTa.setEditable(false);

                //ΕΙΣΑΓΩΓΗ ΤΗΣ ΦΩΤΟΓΡΑΦΙΑΣ ΤΟΥ DESKTOP ΣΕ ΕΤΙΚΕΤΑ
                Image img = new ImageIcon(this.getClass().getResource("desktop.PNG"))
                        .getImage().getScaledInstance(1366, 768, Image.SCALE_SMOOTH);
                JLabel photo = new JLabel();
                photo.setIcon(new ImageIcon(img));

                //ΚΑΤΑΣΚΕΥΗ ΤΟΥ ΠΑΝΕΛ ΠΟΥ ΘΑ ΜΠΕΙ Η ΦΩΤΟΓΡΑΦΙΑ
                JPanel panelPhoto = new JPanel();
                panelPhoto.setLayout(new FlowLayout());
                panelPhoto.add(photo);

                //ΚΑΤΑΣΚΕΥΗ ΤΟΥ ΠΑΝΕΛ ΠΟΥ ΘΑ ΜΠΟΥΝ ΤΑ ΣΤΟΙΧΕΙΑ ΤΟΥ ΚΑΤΑΣΚΕΥΑΣΤΗ
                JPanel panelCreator = new JPanel();
                panelCreator.setLayout(new FlowLayout());
                panelCreator.add(onomaLbl);
                panelCreator.add(onomaTa);
                panelCreator.add(epwnLbl);
                panelCreator.add(epwnTa);
                panelCreator.add(amLbl);
                panelCreator.add(amTa);
                panelCreator.add(timeLbl);
                panelCreator.add(timeTa);

                //ΤΟΠΟΘΕΤΗΣΗ ΤΩΝ ΠΑΝΕΛ ΣΤΟ ΠΑΡΑΘΥΡΟ
                aboutFrame.add(panelCreator, BorderLayout.NORTH);
                aboutFrame.add(panelPhoto, BorderLayout.CENTER);

                //ΕΜΦΑΝΙΣΗ ΤΩΝ ΣΤΟΙΧΕΙΩΝ ΚΑΙ ΤΗΣ ΦΩΤΟΓΡΑΦΙΑΣ
                onomaTa.append("Όνομα");
                epwnTa.append("Επίθετο");
                amTa.append("12345");
                timeTa.append("25/5/2020-28/5/2020");
                aboutFrame.pack();
                aboutFrame.setSize(1500, 890);
                aboutFrame.setLocationRelativeTo(null);
                aboutFrame.setVisible(true);
                aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            }
        });
    }

    //ΜΕΘΟΔΟΣ ΓΙΑ ΤΗΝ ΑΠΟΘΗΚΕΥΣΗ ΤΩΝ ΠΑΡΑΓΓΕΛΙΩΝ
    private void saveOrderList(String fileName) {
        try {
            //ΔΗΜΙΟΥΡΓΙΑ OBJECT ΓΙΑ ΤΗΝ ΕΓΓΡΑΦΗ ΣΕ FILE
            BufferedWriter file = new BufferedWriter(new FileWriter(fileName, true));

            //ΛΟΥΠΑ ΟΛΩΝ ΤΩΝ ΠΑΡΑΓΓΕΛΙΩΝ ΤΗΣ ΛΙΣΤΑΣ
            for (Order order : orderList) {
                //ΕΓΓΡΑΦΗ ΜΙΑ ΓΡΑΜΜΗΣ ΠΟΥ ΠΕΡΙΕΧΕΙ ΜΙΑ ΟΛΟΚΛΗΡΗ ΠΑΡΑΓΓΕΛΙΑ
                file.write(order.toString());
                file.newLine();
            }
            file.close();

            JOptionPane.showMessageDialog(this,
                    orderList.size() + " orders saved to " + fileName,
                    "Save completed",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
        }

    }

    //ΜΕΘΟΔΟΣ ΓΙΑ ΤΟ ΑΝΟΙΓΜΑ ΤΩΝ ΠΑΡΑΓΓΕΛΙΩΝ
    private int openFromFile(String fileName) {

        int prevListSize = orderList.size();

        try {
            //ΔΗΜΙΟΥΡΓΙΑ OBJECT ΓΙΑ ΤΗΝ ΑΝΑΓΝΩΣΗ ΑΠΟ FILE
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line = "";
            String[] token;

            Order order;

            while (reader.ready()) {

                line = reader.readLine();
                //ΤΡΟΠΟΣ ΔΙΑΧΩΡΙΣΗΣ ΤΩΝ 8 ΣΤΟΙΧΕΙΩΝ ΠΟΥ ΕΙΝΑΙ ΤΟ SEMICOLON
                token = line.split(";");

                //ΕΛΕΓΧΟΣ ΓΙΑ ΑΝ ΒΡΙΣΚΟΝΤΑΙ ΣΤΗΝ ΓΡΑΜΜΗ 8 ΣΤΟΙΧΕΙΑ
                if (token.length == 8) {
                    //ΕΛΕΓΧΟΣ ΓΙΑ ΑΝ ΤΟ APP ID(12345) ΕΙΝΑΙ ΤΟ ΔΙΚΟ ΜΟΥ
                    if (!token[0].equals("12345")) {
                        JOptionPane.showMessageDialog(this,
                                "This file has the wrong application ID.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return 0;
                    }
                    //ΕΛΕΓΧΟΣ ΓΙΑ ΤΟ ΑΝ Ο ΦΟΡΟΣ,Η ΤΙΜΗ ΚΑΙ Η ΠΟΣΟΣΤΗΤΑ ΕΙΝΑΙ ΑΡΙΘΜΟΙ
                    try {
                        int units = Integer.parseInt(token[5]);
                        double price = Double.valueOf(token[6]);
                        double tax = Double.valueOf(token[7]);
                        order = new Order(token[0], token[1], token[2],
                                token[3], token[4], token[5], token[6], token[7]);
                        orderList.add(order);

                    } catch (NumberFormatException d) {
                        JOptionPane.showMessageDialog(this,
                                "Please insert a file with correct values.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return 0;
                    }

                }

            }

            reader.close();

        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }

        //ΕΛΕΓΧΟΣ ΓΙΑ ΤΟ ΑΝ ΠΡΟΣΤΕΘΗΚΑΝ ΠΑΡΑΓΓΕΛΙΕΣ ΣΤΗΝ ΛΙΣΤΑ
        if (prevListSize == orderList.size()) {
            JOptionPane.showMessageDialog(this,
                    "No orders were added.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        //ΚΑΛΕΣΜΑ ΜΕΘΟΔΟΥ ΓΙΑ ΤΗΝ ΤΥΠΩΣΗ ΤΩΝ ΠΑΡΑΓΓΕΛΙΩΝ ΣΤΗΝ ΟΘΟΝΗ
        showList();
        return 1;
    }

    //ΜΕΘΟΔΟΣ ΓΙΑ ΤΗΝ ΕΜΦΑΝΙΣΗ ΤΩΝ ΠΑΡΑΓΓΕΛΙΩΝ ΣΤΗΝ ΟΘΟΝΗ
    private void showList() {
        //ΛΟΥΠΑ ΟΛΗΣ ΤΗΣ ΛΙΣΤΑΣ ΚΑΙ ΤΥΠΩΣΗ ΤΩΝ ΠΑΡΑΓΓΕΛΙΩΝ ΣΤΗΝ ΟΘΟΝΗ
        for (Order order : orderList) {
            area.append(order.printScreen());
            area.append("\n");
        }
    }

}
