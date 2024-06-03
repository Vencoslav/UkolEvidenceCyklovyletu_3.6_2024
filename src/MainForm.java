import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainForm extends JFrame {
    private JPanel panelMain;
    private JButton btSmaz;
    private JTextArea textArea1;
    private JTextField tfSmazVyletCislo;
    private List<Cyklovylet> seznam = new ArrayList<>();
    private File selectedFile;

    public MainForm(){
        setContentPane(panelMain);
        setSize(400,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Cyklovýlety");

        nactiZaznamy();
        display();
        initMenu();
        tlacitkoSmaz();
    }

    public void nactiZaznamy(){
        String nazevSoboru = "C:\\Users\\xdole\\OneDrive\\Plocha\\IntelliJ IDEA\\UkolEvidenceCyklovyletu\\src\\vstup";
        try(Scanner sc = new Scanner(new BufferedReader(new FileReader(nazevSoboru)))){
            while (sc.hasNextLine()){
                String radek = sc.nextLine();
                String[] rozdelovac = radek.split(",");
                String cil = rozdelovac[0];
                Integer delka = Integer.parseInt(rozdelovac[1]);
                LocalDate datum = LocalDate.parse(rozdelovac[2]);

                Cyklovylet cyklovylet1 = new Cyklovylet(cil,delka,datum);
                seznam.add(cyklovylet1);
            }

        } catch (FileNotFoundException e){
            throw new RuntimeException("Soubor " + nazevSoboru + " nebyl nalezen " + e.getLocalizedMessage());
        }

    }
    public void display(){
        textArea1.setText("");
        for(int i = 0; i < seznam.size(); i++){
            Cyklovylet cyklovylet = seznam.get(i);
            textArea1.append((i + 1) + ". " + cyklovylet.getCil() + " (" + cyklovylet.getDelka() + " km)" + "\n");
        }
    }

    public void tlacitkoSmaz(){
        btSmaz.addActionListener(e -> {
            try {
                int index = Integer.parseInt(tfSmazVyletCislo.getText()) - 1; // Převedeme na nula-indexované pole
                if(index >= 0 && index < seznam.size()){
                    seznam.remove(index);
                    display();
                } else {
                    JOptionPane.showMessageDialog(this, "Neplatné číslo výletu", "Chyba", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Zadejte platné číslo", "Chyba", JOptionPane.ERROR_MESSAGE);
            }
        });
    }


    public void initMenu(){
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu soubor = new JMenu("Soubor");
        menuBar.add(soubor);

        JMenuItem nacti = new JMenuItem("Načti");
        soubor.add(nacti);
        nacti.addActionListener(e-> vyberSoubor());

        JMenuItem refresh = new JMenuItem("Refresh");
        soubor.add(refresh);
        refresh.addActionListener(e-> {
            if (selectedFile != null && selectedFile.exists()) {
                nactiSoubor(selectedFile);
                display();
            } else {
                JOptionPane.showMessageDialog(this, "Není vybrán žádný soubor nebo soubor není dostupný", "Chyba", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void vyberSoubor(){
        JFileChooser fc = new JFileChooser(".");
        int result = fc.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            selectedFile = fc.getSelectedFile();
            nactiSoubor(selectedFile);
            display();
        }
    }

    public void nactiSoubor(File selectedFile){
        if(selectedFile == null){
            JOptionPane.showMessageDialog(this, "Není vybrán žádný soubor", "Chyba", JOptionPane.ERROR_MESSAGE);
            return;
        }

        seznam.clear(); // Vyčistí aktuální seznam
        try(Scanner sc = new Scanner(new BufferedReader(new FileReader(selectedFile)))){
            while (sc.hasNextLine()){
                String radek = sc.nextLine();
                String[] rozdelovac = radek.split(",");
                String cil = rozdelovac[0];
                Integer delka = Integer.parseInt(rozdelovac[1]);
                LocalDate datum = LocalDate.parse(rozdelovac[2]);

                Cyklovylet cyklovylet1 = new Cyklovylet(cil,delka,datum);
                seznam.add(cyklovylet1);
            }

            //display();
        } catch (FileNotFoundException e){
            throw new RuntimeException("Soubor " + selectedFile + " nebyl nalezen " + e.getLocalizedMessage());
        }

    }
}


