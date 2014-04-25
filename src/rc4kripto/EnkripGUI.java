/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rc4kripto;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Aradea
 */
public class EnkripGUI {	
	JPanel menu;
	GroupLayout layout;
	JButton plaintext;
	JButton key;
	JButton encrypt;
	JButton save;
	JTextField pathPlain;
	JTextField pathKey;
	JTextArea textArea;
	JScrollPane scrollText;
	
	public JPanel menuList()
	{					
		plaintext = new JButton("File Plaintext");
		plaintext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e)
			{
				//setTableContent();
			}
		});
		
		key = new JButton("File Key");
		key.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e)
			{
				//setTableContent();
			}
		});
		
		encrypt = new JButton("Mulai Enkripsi");
		encrypt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e)
			{
				//setTableContent();
			}
		});
		
		save = new JButton("Simpan Hasil");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e)
			{
				//setTableContent();
			}
		});
		
		pathPlain = new JTextField(2);
		pathKey = new JTextField(2);
		pathPlain.setEditable(false);
		pathKey.setEditable(false);
		
		textArea = new JTextArea(8, 22);
        textArea.setBorder(BorderFactory.createLineBorder(Color.black));
        textArea.setEditable(false);
		
		scrollText = new JScrollPane(textArea);
		
		JLabel text = new JLabel("Pilih File: ");
		menu = new JPanel();
		layout = new GroupLayout(menu);
		menu.setLayout(layout);		
		layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(textArea)
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
					.addComponent(textArea)
					.addComponent(save, GroupLayout.Alignment.CENTER))
		);
		return menu;
	}
}
