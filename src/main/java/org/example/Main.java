package org.example;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.sql.*;

import static java.awt.SystemColor.text;

public class Main extends JFrame {
    public JPanel panel = new JPanel();
    public JPanel panel2 = new JPanel();
    public JPanel panel3 = new JPanel();
    public JPanel panel4 = new JPanel();
    public JPanel panel5 = new JPanel();


    JLabel popis = new JLabel("Popis potraviny:");
    JTextField popis2 = new JTextField(10);
    JLabel kcal = new JLabel("kcal na 100 g: ");
    JTextField kcal2 = new JTextField(5);
    JLabel kJ = new JLabel("kJ na 100 g: ");
    JTextField kJ2 = new JTextField(5);
    JLabel tuky = new JLabel("Tuky: ");
    JTextField tuky2 = new JTextField(4);
    JLabel zTohoNas = new JLabel("Z toho nasycené: ");
    JTextField zTohoNas2 = new JTextField(4);
    JLabel sacharidy = new JLabel("Sacharidy: ");
    JTextField sacharidy2 = new JTextField(4);
    JLabel zTohoCukry = new JLabel("Z toho cukry: ");
    JTextField zTohoCukry2 = new JTextField(4);
    JLabel bilkoviny = new JLabel("Bílkoviny: ");
    JTextField bilkoviny2 = new JTextField(4);
    JLabel vlaknina = new JLabel("Vláknina: ");
    JTextField vlaknina2 = new JTextField(4);
    JLabel sul = new JLabel("Sůl: ");
    JTextField sul2 = new JTextField(4);

    JLabel hmotnostJednohoKusu = new JLabel("Hmotnost 1 ks: ");
    JTextField hmotnostJednohoKusu2 = new JTextField(4);
    JLabel minimalniMnozstvi = new JLabel("Minimální množství: ");
    JTextField minimalniMnozstvi2 = new JTextField(4);
    JLabel nakupovaneMnozstvi = new JLabel("Nakupované množství: ");
    JTextField nakupovaneMnozstvi2 = new JTextField(4);
    JButton zapsat = new JButton("Zapsat");




    public JButton tlac2 = new JButton("Zapsat potravinu");
    JButton tlac = new JButton("Zapsat konzumaci");
    public boolean pom = false;
    public  Main() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,300);

        add(panel, BorderLayout.NORTH);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(tlac);
        tlac.addActionListener((e)-> tlacitko());

        panel.add(tlac2);
        tlac2.addActionListener((e -> tlacitko2()));





    }
    private void tlacitko() {
//        panel.add(tlac2, BorderLayout.SOUTH);
//        tlac2.addActionListener((e)-> tlacitko2());
//        panel.remove(tlac);
//        panel.revalidate();
//        panel.repaint();
    }
    private void tlacitko2() {
//        panel.add(tlac);
//        panel.remove(tlac2);
//        panel.revalidate();

        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
        panel2.add(popis);
        panel2.add(popis2);
        panel2.add(kcal);
        panel2.add(kcal2);
        panel2.add(kJ);
        panel2.add(kJ2);

        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
        panel3.add(tuky);
        panel3.add(tuky2);
        panel3.add(zTohoNas);
        panel3.add(zTohoNas2);
        panel3.add(sacharidy);
        panel3.add(sacharidy2);
        panel3.add(zTohoCukry);
        panel3.add(zTohoCukry2);

        panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));
        panel4.add(bilkoviny);
        panel4.add(bilkoviny2);
        panel4.add(vlaknina);
        panel4.add(vlaknina2);
        panel4.add(sul);
        panel4.add(sul2);

        panel5.setLayout(new BoxLayout(panel5, BoxLayout.X_AXIS));
        panel5.add(hmotnostJednohoKusu);
        panel5.add(hmotnostJednohoKusu2);
        panel5.add(minimalniMnozstvi);
        panel5.add(minimalniMnozstvi2);
        panel5.add(nakupovaneMnozstvi);
        panel5.add(nakupovaneMnozstvi2);
        panel5.add(zapsat);
        zapsat.addActionListener((e) -> zapsatPotravinu());


        panel.removeAll();
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel4);
        panel.add(panel5);

        panel2.revalidate();
        panel3.revalidate();
        panel4.revalidate();
        panel5.revalidate();


    }

    private void zapsatPotravinu() {

        String url = "jdbc:mysql://127.0.0.1:3306/KalTab";
        String user = "root";
        String password = "iukjl8M7UOJKL9I";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String sql = "INSERT INTO potravina "
                    + "(popis, kcal_100g, kJ_100g, tuky, zTohoNasMK, sacharidy, zTohoCukry, bilkoviny, vlaknina, sul, hmotnostJednohoKusu, minSkladMnoz, objMnoz) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, popis2.getText());
            ps.setDouble(2, Double.parseDouble(cnt(kcal2.getText())));
            ps.setDouble(3, Double.parseDouble(cnt(kJ2.getText())));
            ps.setDouble(4, Double.parseDouble(cnt(tuky2.getText())));
            ps.setDouble(5, Double.parseDouble(cnt(zTohoNas2.getText())));
            ps.setDouble(6, Double.parseDouble(cnt(sacharidy2.getText())));
            ps.setDouble(7, Double.parseDouble(cnt(zTohoCukry2.getText())));
            ps.setDouble(8, Double.parseDouble(cnt(bilkoviny2.getText())));
            ps.setDouble(9, Double.parseDouble(cnt(vlaknina2.getText())));
            ps.setDouble(10, Double.parseDouble(cnt(sul2.getText())));
            ps.setDouble(11, Double.parseDouble(cnt(hmotnostJednohoKusu2.getText())));
            ps.setDouble(12, Double.parseDouble(cnt(minimalniMnozstvi2.getText())));
            ps.setDouble(13, Double.parseDouble(cnt(nakupovaneMnozstvi2.getText())));

            ps.executeUpdate();

            panel.removeAll();
            panel.add(tlac);
            panel.add(tlac2);
            panel.revalidate();
;

            System.out.println("Uloženo!");

        } catch (SQLException e) {
            e.printStackTrace();
        }







    }

    public String cnt(String text) {
        String vys = "";
        char []pole = text.toCharArray();
        int kon = text.length();
        for (int i = 0; i < kon; i++) {
            if (pole[i] == ',') {
                pole[i] = '.';
            }
            vys = vys + pole[i];
        }
        return vys;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {
            Main okno = new Main();
            okno.setVisible(true);
        });

        String url = "jdbc:mysql://127.0.0.1:3306/KalTab";
        String user = "root";
        String password = "iukjl8M7UOJKL9I";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String sql = "SELECT * FROM ciselnik";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String jmeno = rs.getString("druh");

                System.out.println(id + " " + jmeno);
            }
 
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Ahoj");

//        Main m = new Main();
//        System.out.println(m.cnt("0,0"));
    }


}
//
//String sql = "INSERT INTO uzivatele (jmeno, vek) VALUES (?, ?)";
//PreparedStatement ps = conn.prepareStatement(sql);
//
//ps.setString(1, "Petr");
//ps.setInt(2, 25);
//
//ps.executeUpdate();