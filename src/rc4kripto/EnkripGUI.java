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
public class EnkripGUI implements ActionListener {	
	JPanel menu;
	GroupLayout layout;
	JButton plaintext;
	JButton key;
	JButton encrypt;
	JButton save;
	JTextField pathPlain;
	JTextField pathKey;
	JTextArea log;
	JScrollPane scrollText;	
	JFileChooser fc;
	File plainFile;
	File keyFile;
	int[] enkripsi;
	
	public JPanel menuList() {					
		plaintext = new JButton("File Plaintext");
		plaintext.addActionListener(this);
		
		key = new JButton("File Key");
		key.addActionListener(this);
		
		encrypt = new JButton("Mulai Enkripsi");
		encrypt.addActionListener(this);
		
		save = new JButton("Simpan Hasil");
		save.addActionListener(this);
		
		pathPlain = new JTextField(2);
		pathKey = new JTextField(2);
		pathPlain.setEditable(false);
		pathKey.setEditable(false);
		
		log = new JTextArea(8, 22);
        log.setEditable(false);
        log.setMargin(new Insets(5,5,5,5));
		
		scrollText = new JScrollPane(log);
		fc = new JFileChooser("D:");		
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new FileNameExtensionFilter("Text (*.txt)", "txt"));
		
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
						.addComponent(pathPlain)
						.addComponent(pathKey))
					.addComponent(encrypt, GroupLayout.Alignment.CENTER))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(plaintext, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, 120)
					.addComponent(key, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, 120)
					.addComponent(save))
		);
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addComponent(text)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(pathPlain)
					.addComponent(plaintext))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(pathKey)
					.addComponent(key))
				.addComponent(encrypt)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(scrollText)
					.addComponent(save, GroupLayout.Alignment.CENTER))
		);
		return menu;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == save) {
				saveFile();
			} else if (e.getSource()== encrypt) {
				startEncrypt();
			} else {
				int returnVal = fc.showOpenDialog(menu);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					if (!file.getName().endsWith("txt")) throw new Exception();
					log.append("Membuka: " + file.getName() + "\n");
					if (e.getSource() == plaintext) {
						plainFile = file;						
						pathPlain.setText(file.getAbsolutePath());
					} else if (e.getSource() == key) {
						keyFile = file;
						pathKey.setText(file.getAbsolutePath());
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
			JOptionPane.showMessageDialog(null, "Hanya dapat menerima file berformat .txt!", "Error", JOptionPane.ERROR_MESSAGE);
			log.append("Exception tertangkap\n");
		}
 	}

 	public void startEncrypt() throws FileNotFoundException, IOException { 		
		if (plainFile == null || keyFile == null) throw new FileNotFoundException("Plaintext / key tidak ditemukan!");
		FileInputStream fis = new FileInputStream(plainFile);
		log.append("Isi plaintext: ");
		int[] M = new int[fis.available()];
		for (int i = 0; i < M.length; i++) {
			M[i] = fis.read();
			log.append(String.valueOf((char)M[i]));
		}				
		fis.close();
		log.append("\n");
		
		fis = new FileInputStream(keyFile);
		log.append("Isi key: ");
		byte[] K = new byte[fis.available()];
		for (int i = 0; i < K.length; i++) {
			K[i] = (byte) fis.read();
			log.append(String.valueOf((char)K[i]));
		}
		fis.close();
		log.append("\n");
		
		log.append("\nMemulai enkripsi " + plainFile.getName() + " dengan key " + keyFile.getName() + "\n");
		RC4Kripto rc = new RC4Kripto();
		rc.permuteS(K);
		enkripsi = rc.encryption(M, K);
		log.append("Hasil enkripsi (dalam hexadecimal): ");
		for (int i = 0; i < enkripsi.length; i++) {
            log.append(Integer.toHexString(enkripsi[i]));
        }
		log.append("\n\n");
 	}

 	public void saveFile() throws FileNotFoundException, Exception {
		if (enkripsi == null) throw new FileNotFoundException("Hasil enkripsi tidak ditemukan!");
 		int returnVal = fc.showSaveDialog(menu);
		File file = fc.getSelectedFile();
                String path = file.getAbsolutePath();
                FileOutputStream fos = new FileOutputStream(file);
		if (!file.getName().endsWith("txt")) throw new Exception();
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			log.append("Menyimpan: " + file.getName() + "\n");
                        for(int ii=0;ii<enkripsi.length;ii++){
                            fos.write(enkripsi[ii]);
                        }
                        fos.close();
		} else {
			log.append("Perintah simpan dibatalkan\n");
		}
		
		//TO-DO simpan file S-box serta hasil enkripsi
 	}
}
