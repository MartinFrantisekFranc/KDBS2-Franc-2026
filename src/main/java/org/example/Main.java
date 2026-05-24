package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.awt.SystemColor.text;

public class Main extends JFrame {
    public JPanel panel = new JPanel();
    public JPanel panel2 = new JPanel();
    public JPanel panel3 = new JPanel();
    public JPanel panel4 = new JPanel();
    public JPanel panel5 = new JPanel();
    public JPanel panel6 = new JPanel();
    public JPanel panel7 = new JPanel();
    public JPanel panel8 = new JPanel();
    public JPanel panel9 = new JPanel();


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

    JLabel vyberDruhJidla = new JLabel("Vyber druh jídla: ");
    JComboBox<Druh> combo = new JComboBox<>();
    JLabel mnozstvi = new JLabel("Spotřebované množství: ");
    JTextField mnozstvi2 = new JTextField(5);

    JLabel potravina = new JLabel("Vyber potravinu: ");
    JComboBox<Druh> potravina2 = new JComboBox<>();

    JLabel potravinaZP = new JLabel("Vyber potravinu: ");
    JComboBox<Druh> potravinaZP2 = new JComboBox<>();

    JLabel potravinaZP3 = new JLabel("Množ.: ");
    JTextField potravinaZP4 = new JTextField(5);
    JLabel potravinaZP5 = new JLabel("Dat. spotř.: ");
    JTextField potravinaZP6 = new JTextField(5);

    JButton zapsatZasobu = new JButton("Zapsat zásobu");

    JButton tlac3 = new JButton("Zapsat konzumaci");
    JButton zapsatZasobu2 = new JButton("Zapsat zásobu");
    JButton prehledKonzumace = new JButton("Přehled konzumace");
    JLabel datumKonzumace = new JLabel("Datum konzumece: ");
    JTextField datumKonzum = new JTextField(5);
    JLabel celkovaSpotreba = new JLabel("Celková spotřeba: ");
    JLabel celkovaSpotreba2 = new JLabel("");
    JPanel novyPanel = new JPanel();

    JButton zasoba = new JButton("Ukaž zásobu");
    JButton tlacitkoZpet = new JButton("Zpět");
    JButton nakup = new JButton("Nákup");
    JLabel zadejID = new JLabel("Zadej ID položky nákupu:");
    JTextField zadanoID = new JTextField(5);
    JButton zadejPolozku = new JButton("Potvrď koupenou položku");


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
        tlac2.addActionListener((e) -> tlacitko2());

        panel.add(zapsatZasobu);
        zapsatZasobu.addActionListener((e) -> zapsatZasobu());

        zapsatZasobu2.addActionListener((e)->zapsatZasobu2());

        tlac3.addActionListener((e)-> {
            try {
                zapsatKonzumaci();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        zapsat.addActionListener((e) -> zapsatPotravinu());

        //panel9.setLayout(new BoxLayout(panel9, BoxLayout.X_AXIS));
        //panel9.add(datumKonzumace);
        //panel9.add(datumKonzum);
        //panel9.add(prehledKonzumace);
        //panel.add(panel9);
        panel.add(datumKonzumace);
        panel.add(datumKonzum);
        panel.add(prehledKonzumace);

        prehledKonzumace.addActionListener((e)-> prehledKonzum());

        panel.add(zasoba);
        zasoba.addActionListener((e)-> ukazZasobu());
        panel.add(nakup);
        nakup.addActionListener((e) -> ukazNakup());

        tlacitkoZpet.addActionListener((e)->zpet());

        zadejPolozku.addActionListener((e)-> odstranit());
    }

    private void odstranit() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("popis");
        model.addColumn("koupit množství");
        model.addColumn("je koupeno?");

        String url = "jdbc:mysql://127.0.0.1:3306/KalTab";
        String user = "root";
        String password = "iukjl8M7UOJKL9I";


        int id = 0;

        id = Integer.parseInt(zadanoID.getText());

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String sql = "UPDATE nakupPolozek SET koupeno = true where ID = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            sql = "select n.ID, p.popis, n.koupitMnozstvi, n.koupeno from nakupPolozek n join potravina p on n.ID_potravina = p.ID where koupeno = false;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);



            while (rs.next()) {

                model.addRow(new Object[]{

                        rs.getInt("ID"),
                        rs.getString("popis"),
                        rs.getFloat("koupitMnozstvi"),
                        rs.getBoolean("koupeno")

                });
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        JTable table = new JTable(model);

        table.setPreferredScrollableViewportSize(new Dimension(800, table.getRowHeight() * 10));

        JScrollPane scroll = new JScrollPane(table);

        panel.removeAll();
        panel.add(scroll);
        novyPanel.removeAll();
        novyPanel.add(zadejID);
        novyPanel.add(zadanoID);
        novyPanel.add(zadejPolozku);
        novyPanel.add(tlacitkoZpet);
        panel.add(novyPanel);

        panel.revalidate();
        panel.repaint();
    }



    private void ukazNakup() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("popis");
        model.addColumn("koupit množství");
        model.addColumn("je koupeno?");

        String url = "jdbc:mysql://127.0.0.1:3306/KalTab";
        String user = "root";
        String password = "iukjl8M7UOJKL9I";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {


            String sql = "select n.ID, p.popis, n.koupitMnozstvi, n.koupeno from nakupPolozek n join potravina p on n.ID_potravina = p.ID where koupeno = false;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);



            while (rs.next()) {

                model.addRow(new Object[]{

                        rs.getInt("ID"),
                        rs.getString("popis"),
                        rs.getFloat("koupitMnozstvi"),
                        rs.getBoolean("koupeno")

                });
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        JTable table = new JTable(model);

        table.setPreferredScrollableViewportSize(new Dimension(800, table.getRowHeight() * 10));

        JScrollPane scroll = new JScrollPane(table);

        panel.removeAll();
        panel.add(scroll);
        novyPanel.removeAll();
        novyPanel.add(zadejID);
        novyPanel.add(zadanoID);
        novyPanel.add(zadejPolozku);
        novyPanel.add(tlacitkoZpet);
        panel.add(novyPanel);

        panel.revalidate();
        panel.repaint();
    }

    private void zpet() {
        panel.removeAll();
        panel.add(tlac);
        panel.add(tlac2);
        panel.add(zapsatZasobu);
        panel.add(datumKonzumace);
        panel.add(datumKonzum);
        panel.add(prehledKonzumace);
        panel.add(zasoba);
        panel.add(nakup);
        panel.revalidate();

    }

    private void ukazZasobu() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("popis");
        model.addColumn("množství");
        model.addColumn("datum spotřeby");

        String url = "jdbc:mysql://127.0.0.1:3306/KalTab";
        String user = "root";
        String password = "iukjl8M7UOJKL9I";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {


            String sql = "select p.popis, z.mnozstvi, z.datumSpotreby from zasoba z join potravina p on z.ID_potravina = p.ID where z.mnozstvi > 0;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);



            while (rs.next()) {

                model.addRow(new Object[]{

                        rs.getString("popis"),
                        rs.getFloat("mnozstvi"),
                        rs.getDate("datumSpotreby")

                });
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        JTable table = new JTable(model);

        table.setPreferredScrollableViewportSize(new Dimension(800, table.getRowHeight() * 10));

        JScrollPane scroll = new JScrollPane(table);

        panel.removeAll();
        panel.add(scroll);
        novyPanel.removeAll();
        novyPanel.add(tlacitkoZpet);
        panel.add(novyPanel);

        panel.revalidate();
        panel.repaint();
    }


    private void prehledKonzum() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate datum = LocalDate.parse(datumKonzum.getText(), formatter);

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("datum");
        model.addColumn("cas");
        model.addColumn("druh");
        model.addColumn("popis");
        model.addColumn("spotrebovaneMnozstvi");
        model.addColumn("energieKcal");
        model.addColumn("energieKJ");

        String url = "jdbc:mysql://127.0.0.1:3306/KalTab";
        String user = "root";
        String password = "iukjl8M7UOJKL9I";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            //volání procedury

            CallableStatement cs = conn.prepareCall("{CALL konzumaceZaDen(?)}");

            cs.setDate(1, java.sql.Date.valueOf(datum));

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{

                        rs.getDate("datum"),
                        rs.getTime("cas"),
                        rs.getString("druh"),
                        rs.getString("popis"),
                        rs.getFloat("spotrebovaneMnozstvi"),
                        rs.getFloat("energieKcal"),
                        rs.getFloat("energieKJ")

                });
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            //volání funkce přes proceduru

            CallableStatement cs = conn.prepareCall("{CALL konzumaceZaDenKcal(?)}");

            cs.setDate(1, java.sql.Date.valueOf(datum));

            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                celkovaSpotreba2.setText(rs.getString("energieKcal")+" kCal");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        JTable table = new JTable(model);

        table.setPreferredScrollableViewportSize(new Dimension(800, table.getRowHeight() * 10));

        JScrollPane scroll = new JScrollPane(table);

        panel.removeAll();
        panel.add(scroll);

        novyPanel.removeAll();
        novyPanel.add(tlacitkoZpet);
        novyPanel.add(celkovaSpotreba);
        novyPanel.add(celkovaSpotreba2);
        panel.add(novyPanel);

        panel.revalidate();
        panel.repaint();
    }

    private void zapsatZasobu() {
        panel.removeAll();
        panel.revalidate();
        panel8.removeAll();
        panel8.revalidate();
        panel8.setLayout(new BoxLayout(panel8, BoxLayout.X_AXIS));
        panel8.add(potravinaZP);

        String url = "jdbc:mysql://127.0.0.1:3306/KalTab";
        String user = "root";
        String password = "iukjl8M7UOJKL9I";



        potravinaZP2.removeAllItems();

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String sql = "SELECT * FROM potravina";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Druh d = new Druh(rs.getInt("ID"), rs.getString("popis"));
                potravinaZP2.addItem(d);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        panel8.add(potravinaZP2);
        panel8.add(potravinaZP3);
        panel8.add(potravinaZP4);
        panel8.add(potravinaZP5);
        panel8.add(potravinaZP6);
        panel8.add(tlacitkoZpet);
        panel8.add(zapsatZasobu2);


        panel.add(panel8);
        panel.revalidate();

    }

    public void zapsatZasobu2() {
        Druh vybrany = (Druh) potravinaZP2.getSelectedItem();

        int id = vybrany.getId();

        float mnoz = Float.parseFloat(cnt(potravinaZP4.getText()));

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd.MM.yyyy");

        LocalDate datum = LocalDate.parse(potravinaZP6.getText(), formatter);



        String url = "jdbc:mysql://127.0.0.1:3306/KalTab";
        String user = "root";
        String password = "iukjl8M7UOJKL9I";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String sql = "INSERT INTO zasoba "
                    + "(ID_potravina, mnozstvi, datumSpotreby) "
                    + "VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setDouble(2, mnoz);
            ps.setDate(3, java.sql.Date.valueOf(datum));


            ps.executeUpdate();

            panel.removeAll();
            panel.revalidate();
            panel.add(tlacitkoZpet);

            panel.revalidate();


            System.out.println("Uloženo!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void tlacitko() {
//        panel.add(tlac2, BorderLayout.SOUTH);
//        tlac2.addActionListener((e)-> tlacitko2());
//        panel.remove(tlac);
//        panel.revalidate();
//        panel.repaint();

        String url = "jdbc:mysql://127.0.0.1:3306/KalTab";
        String user = "root";
        String password = "iukjl8M7UOJKL9I";

        combo.removeAllItems();

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String sql = "SELECT * FROM ciselnik";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Druh d = new Druh(rs.getInt("ID"), rs.getString("druh"));
                combo.addItem(d);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        potravina2.removeAllItems();

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String sql = "SELECT * FROM potravina";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Druh d = new Druh(rs.getInt("ID"), rs.getString("popis"));
                potravina2.addItem(d);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        


        panel.removeAll();
        panel.revalidate();
        panel6.removeAll();
        panel6.revalidate();
        panel6.setLayout(new BoxLayout(panel6, BoxLayout.X_AXIS));
        //panel.removeAll();
        panel6.add(vyberDruhJidla);
        panel6.add(combo);
        panel6.add(potravina);
        panel6.add(potravina2);
        panel6.add(mnozstvi);
        panel6.add(mnozstvi2);

        panel7.removeAll();
        panel7.revalidate();
        panel7.setLayout(new BoxLayout(panel7, BoxLayout.X_AXIS));
        panel7.add(tlacitkoZpet);
        panel7.add(tlac3);

        panel7.revalidate();

        panel6.revalidate();

        panel.add(panel6);
        panel.add(panel7);

        panel.revalidate();
        panel.repaint();

    }
    private void zapsatKonzumaci() throws SQLException {


        Druh vybrany = (Druh) combo.getSelectedItem();

        int id = vybrany.getId();
        Druh vybrany2 = (Druh) potravina2.getSelectedItem();

        int id2 = vybrany2.getId();

        float mnoz = Float.parseFloat(cnt(mnozstvi2.getText()));



        String url = "jdbc:mysql://127.0.0.1:3306/KalTab";
        String user = "uzivatel";
        String password = "uzivatel";


        Connection conn = DriverManager.getConnection(url, user, password);

        try {

            conn.setAutoCommit(false);



            PreparedStatement ps1 = conn.prepareStatement(
                    "INSERT INTO konzumace" +
                            "(datum, cas, ID_ciselnik, ID_potravina, spotrebovaneMnozstvi)" +
                            "VALUES (?, ?, ?, ?, ?)"
            );

            ps1.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            ps1.setTime(2, java.sql.Time.valueOf(LocalTime.now()));

            ps1.setInt(3, id);
            ps1.setInt(4, id2);

            ps1.setDouble(5, mnoz);



            ps1.executeUpdate();

            //přepočet
            PreparedStatement ps2 = conn.prepareStatement(
                    "SELECT * FROM zasoba "
                    + "WHERE ID_potravina = ? "
                    + "ORDER BY datumSpotreby"
            );

            ps2.setInt(1, id2);
            ResultSet rs = ps2.executeQuery();
            while (rs.next()) {

                int idd = rs.getInt("ID");
                float mnozs = (float) rs.getDouble("mnozstvi");
                if (mnozs > 0) {
                    PreparedStatement  ps3 = conn.prepareStatement(
                            "UPDATE zasoba SET mnozstvi = ? WHERE ID = ?"
                    );

                    if (mnoz > mnozs) {
                        ps3.setDouble(1, 0);
                        mnoz = mnoz - mnozs;
                    } else {
                        ps3.setDouble(1, mnozs - mnoz);
                    }
                    ps3.setInt(2, idd);
                    ps3.executeUpdate();
                }


            }





            conn.commit();

        } catch (Exception e) {

            conn.rollback();

            e.printStackTrace();

        } finally {

            conn.setAutoCommit(true);

        }

        panel.removeAll();
        panel.add(tlacitkoZpet);
        panel.revalidate();

    }
    private void tlacitko2() {
//        panel.add(tlac);
//        panel.remove(tlac2);
//        panel.revalidate();

        panel2.removeAll();
        panel2.revalidate();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
        panel2.add(popis);
        panel2.add(popis2);
        panel2.add(kcal);
        panel2.add(kcal2);
        panel2.add(kJ);
        panel2.add(kJ2);

        panel3.removeAll();
        panel3.revalidate();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
        panel3.add(tuky);
        panel3.add(tuky2);
        panel3.add(zTohoNas);
        panel3.add(zTohoNas2);
        panel3.add(sacharidy);
        panel3.add(sacharidy2);
        panel3.add(zTohoCukry);
        panel3.add(zTohoCukry2);

        panel4.removeAll();
        panel4.revalidate();
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));
        panel4.add(bilkoviny);
        panel4.add(bilkoviny2);
        panel4.add(vlaknina);
        panel4.add(vlaknina2);
        panel4.add(sul);
        panel4.add(sul2);

        panel5.removeAll();
        panel5.revalidate();
        panel5.setLayout(new BoxLayout(panel5, BoxLayout.X_AXIS));
        panel5.add(hmotnostJednohoKusu);
        panel5.add(hmotnostJednohoKusu2);
        panel5.add(minimalniMnozstvi);
        panel5.add(minimalniMnozstvi2);
        panel5.add(nakupovaneMnozstvi);
        panel5.add(nakupovaneMnozstvi2);
        panel5.add(tlacitkoZpet);
        panel5.add(zapsat);



        panel.removeAll();
        panel.revalidate();
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
            panel.revalidate();
            panel.add(tlacitkoZpet);
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