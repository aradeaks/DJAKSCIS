/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rc4kripto;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Aradea Krisnaraga & Daniel Januar
 */
public class MainGUI extends JFrame {
	
	private static final int FRAME_WIDTH = 650;
    private static final int FRAME_HEIGHT = 450;
    private static final int BORDER_WIDTH = 5;
	
	public MainGUI(String title) {
        super(title);
    }
	
	public void initGUI() {
        //Mengatur ukuran JFrame agar tidak dapat diubah
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        
        //Mengubah layout JFrame menjadi BorderLayout
        setLayout(new BorderLayout());
        
        //Memanggil method createTab yang akan mengembalikan
        //JTabbedPane dan dimasukkan dalam JFrame di posisi tengah
        add(createTab(), BorderLayout.CENTER);
        
        //Memindahkan posisi frame ke tengah-tengah layar
        setLocationRelativeTo(null);
        setVisible(true);
    }

	private JTabbedPane createTab() {
        //Menginisialisasi sebuah objek JTabbedPane bernama tab
        JTabbedPane tab = new JTabbedPane();
        //Menambahkan tiga buah tab ke dalam tab
        tab.addTab("Enkripsi", null, new RC4GUI("E").menuList(), "Enkripsi RC4");
        tab.addTab("Dekripsi", null, new RC4GUI("D").menuList(), "Dekripsi RC4");
        tab.addTab("About", null, about(), "Tentang Program");
        
        //Membuat border kosong di sekeliling tab
        tab.setBorder(new EmptyBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH));
        return tab;
    }
	
	public static void main(String[] args) {
		new MainGUI("Tugas Pemrograman CIS").initGUI();
	}
	
	public JPanel about() {
		String about = "<html><center>Tugas Pemrograman Kriptografi & Keamanan Informasi<br/>";
		about += "Implementasi RC4 + GUI<br/>";
		about += "<br/>Dikerjakan oleh:<br/>";
		about += "Aradea Krisnaraga - 1106003900<br/>";
		about += "Daniel Januar - 1106008403</center></html>";
		JLabel tentang = new JLabel();
		tentang.setPreferredSize(new Dimension(615, 140));
        tentang.setText(about);
        tentang.setHorizontalAlignment(JLabel.CENTER);
        tentang.setVerticalAlignment(JLabel.CENTER);
		JPanel jpanel = new JPanel();
		jpanel.add(tentang);
		return jpanel;
	}
}
