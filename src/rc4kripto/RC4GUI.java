/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rc4kripto;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Aradea Krisnaraga & Daniel Januar
 */
public class RC4GUI implements ActionListener {
	String input;
	String action;
	JPanel menu;
	GroupLayout layout;
	JButton buttonInput;
	JButton buttonKey;
	JButton buttonDE;
	JButton buttonSave;
	JTextField inputPath;
	JTextField keyPath;
	JTextArea log;
	JScrollPane scrollText;
	JFileChooser fc;
	File inputFile;
	File keyFile;
	int[] result;
	int[][] sAwal;
	int[][] sAkhir;
	
	public RC4GUI(String ed) {
		if (ed.equals("E")) {
			input = "Plaintext";
			action = "Enkripsi";
		} else {
			input = "Ciphertext";
			action = "Dekripsi";
		}
		sAwal = new int[26][10];
		sAkhir = new int[26][10];
	}
	
	public JPanel menuList() {
		buttonInput = new JButton("File " + input);
		buttonInput.addActionListener(this);

		buttonKey = new JButton("File Key");
		buttonKey.addActionListener(this);

		buttonDE = new JButton("Mulai " + action);
		buttonDE.addActionListener(this);

		buttonSave = new JButton("Simpan Hasil");
		buttonSave.addActionListener(this);

		inputPath = new JTextField(2);
		keyPath = new JTextField(2);
		inputPath.setEditable(false);
		keyPath.setEditable(false);

		log = new JTextArea(8, 22);
		log.setEditable(false);
		log.setMargin(new Insets(5,5,5,5));
		
		scrollText = new JScrollPane(log);
		fc = new JFileChooser("D:");		
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new FileNameExtensionFilter("Text Documents (*.txt)", "txt"));
		
		JLabel text = new JLabel("Pilih File: ");
		menu = new JPanel();
		layout = new GroupLayout(menu);
		menu.setLayout(layout);		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(scrollText)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(text))
						.addComponent(inputPath)
						.addComponent(keyPath))
					.addComponent(buttonDE, GroupLayout.Alignment.CENTER))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(buttonInput, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, 120)
					.addComponent(buttonKey, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, 120)
					.addComponent(buttonSave, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, 120))
		);
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addComponent(text)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(inputPath)
					.addComponent(buttonInput))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(keyPath)
					.addComponent(buttonKey))
				.addComponent(buttonDE)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(scrollText)
					.addComponent(buttonSave, GroupLayout.Alignment.CENTER))
		);
		return menu;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == buttonSave) {
				saveFile();
			} else if (e.getSource()== buttonDE) {
				startDE();
			} else {
                fc.setDialogTitle("Pilih File Input");
				int returnVal = fc.showOpenDialog(menu);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					if (!file.getName().endsWith("txt")) throw new Exception("Hanya dapat menerima file berformat .txt!");
					log.append("Membuka: " + file.getName() + "\n");
					if (e.getSource() == buttonInput) {
						inputFile = file;						
						inputPath.setText(file.getAbsolutePath());
					} else if (e.getSource() == buttonKey) {
						keyFile = file;
						keyPath.setText(file.getAbsolutePath());
					}
				} else {
					log.append("Perintah buka dibatalkan\n");
				}
			}
			log.setCaretPosition(log.getDocument().getLength());
		} catch (FileNotFoundException fne) {
			if (fne.getMessage() == null) {
				JOptionPane.showMessageDialog(null, "File tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, fne.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			log.append("FileNotFoundException tertangkap\n");
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat membaca file!", "Error", JOptionPane.ERROR_MESSAGE);
			log.append("IOException tertangkap\n");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);	
			log.append("Exception tertangkap\n");
		}
	}
	
	public void startDE() throws FileNotFoundException, IOException, Exception {
		if (inputFile == null || keyFile == null) throw new FileNotFoundException(input + " / key tidak ditemukan!");
		
		FileInputStream fis = new FileInputStream(inputFile);
		if (fis.available() == 0) throw new Exception("File input kosong!");
		
		log.append("Isi " + input.toLowerCase() + " : ");
		int[] T = new int[fis.available()];
		for (int i = 0; i < T.length; i++) {
			T[i] = fis.read();
			log.append(String.valueOf((char)T[i]));
		}				
		fis.close();
		log.append("\n");
		
		fis = new FileInputStream(keyFile);
		if (fis.available() == 0) throw new Exception("File input kosong!");
		if (fis.available() < 16) throw new Exception("Panjang key kurang dari 128 bit!");
		
		log.append("Isi key: ");
		byte[] K = new byte[fis.available()];
		for (int i = 0; i < K.length; i++) {
			K[i] = (byte) fis.read();
			log.append(String.valueOf((char)K[i]));
		}
		fis.close();
		log.append("\n");
		
		log.append("\nMemulai " + action.toLowerCase() + " " + inputFile.getName() + " dengan key " + keyFile.getName() + "\n");
		RC4Kripto rc = new RC4Kripto();
		rc.permuteS(K);
		
		sAkhir = rc.getS();
		for (int ii=0;ii<sAkhir.length;ii++) {
			System.arraycopy(sAkhir[ii], 0, sAwal[ii], 0, sAwal[ii].length);
		}		
		result = rc.encryption(T, K);
		
		log.append("Hasil " + action.toLowerCase() + " (dalam ASCII): ");
		for (int i = 0; i < result.length; i++) {
			log.append(String.valueOf((char) result[i]));
		}
		log.append("\n");
		log.append("Hasil " + action.toLowerCase() + " (dalam hexadecimal): ");
		for (int i = 0; i < result.length; i++) {
			log.append(Integer.toHexString(result[i]));
		}
		log.append("\n\n");
	}

	public void saveFile() throws FileNotFoundException, Exception {
		if (result == null) throw new FileNotFoundException("Hasil " + action.toLowerCase() + " tidak ditemukan!");		
		fc.setDialogTitle("Simpan Hasil " + action + "");

		int returnVal = fc.showSaveDialog(menu);
		File file = fc.getSelectedFile();
		if (!file.exists()) file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);		
		if (!file.getName().endsWith("txt")) throw new Exception("Hanya dapat menerima file berformat .txt!");

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			fc.setDialogTitle("Simpan S-Box");
			int returnValS = fc.showSaveDialog(menu);
			File fileS = fc.getSelectedFile();
			if (!fileS.exists()) fileS.createNewFile();
			PrintWriter pw = new PrintWriter(fileS);

			if (returnValS == JFileChooser.APPROVE_OPTION) {
				log.append("Menyimpan: " + file.getName() + " sebagai hasil " + action.toLowerCase() + "\n");
				log.append("Menyimpan: " + fileS.getName() + " sebagai S-Box awal dan S-Box Akhir\n");

				pw.println("S-Box Awal");
				printSBox(pw, sAwal);
				pw.println("S-box Akhir");
				printSBox(pw, sAkhir);
				pw.close();

				for (int ii = 0; ii < result.length; ii++) {
					fos.write(result[ii]);
				}
				fos.close();
			} else {
				log.append("Perintah simpan dibatalkan\n");
			}
		} else {
			log.append("Perintah simpan dibatalkan\n");
		}
	}

	public void printSBox(PrintWriter pw, int[][] s) {
		for (int i = -1; i < s[0].length; i++) {
			if (i < 0) {
				pw.print(String.format("%2s |", "S"));
			} else {
				pw.print(String.format("%2d |", i));
			}
		}
		pw.println();
		
		for (int i = 0; i < s.length; i++) {
			for (int j = -1; j < s[i].length; j++) {
				if (j < 0) {
					pw.print(String.format("%2d |", i));
				} else {
					pw.print(String.format("%3d|", s[i][j]));
				}
			}
			pw.println();
		}
		pw.println();
	}
}
