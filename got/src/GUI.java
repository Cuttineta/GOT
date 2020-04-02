import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GUI extends JFrame {

	private Logica logica;
	private JButton btnComenzar, btnCargar, btnEng, btnEs;
	private Map<String, Double> entradas;
	private Entry<String, Double>[] arreglo;
	private InputStream fisEsp, fisEng;
	private Properties properties;
	private JLabel lblTitulo, label_1, label_2, label_3, label_4, label_5, lblCambiarLenguaje;
	private JTextArea textArea_1, textArea_2, textArea_3, textArea_4, textArea_5;

	public GUI() {

		setTitle("Winter is Coming");
		setResizable(false);
		getContentPane().setLayout(null);
		properties = new Properties();

		try {
			fisEsp = new FileInputStream("esp.properties");
			fisEng = new FileInputStream("eng.properties");
			properties.load(fisEsp);
		} catch (IOException e) {
			e.printStackTrace();
		}

		inicializar();
	}

	private void inicializar() {

		logica = new Logica();
		// Botones
		btnCargar = new JButton(properties.getProperty("c_directorio"));
		btnCargar.setBounds(10, 72, 150, 23);
		btnCargar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String ruta = JOptionPane.showInputDialog(properties.getProperty("cartel"));

				if (ruta != null) {
					try {
						logica.cargarCarpeta(ruta);
						btnCargar.setEnabled(false);
						btnComenzar.setEnabled(true);
					} catch (InvalidPathException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}

				} else {
					JOptionPane.showMessageDialog(null, properties.getProperty("error"));
					btnCargar.setEnabled(true);
					btnComenzar.setEnabled(false);
				}

			}
		});
		getContentPane().add(btnCargar);

		btnComenzar = new JButton(properties.getProperty("comienzo"));
		btnComenzar.setEnabled(false);
		btnComenzar.setBounds(10, 126, 150, 23);
		btnComenzar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logica.mapearPalabras();
				entradas = logica.ordenarEntradas();
				Iterator<Entry<String, Double>> it = entradas.entrySet().iterator();
				arreglo = (Entry<String, Double>[]) new Entry[5];
				int index = 0;
				while (it.hasNext() && index < 5) {
					Entry<String, Double> act = it.next();
					arreglo[index] = act;
					index++;

				}

				posicionarPalabras();
			}
		});

		getContentPane().add(btnComenzar);

		btnEs = new JButton("Español");
		btnEs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fisEsp = new FileInputStream("esp.properties");
					properties.load(fisEsp);
					actualizarComponentes(properties);

				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnEs.setBounds(37, 225, 90, 23);
		getContentPane().add(btnEs);

		btnEng = new JButton("English");
		btnEng.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					fisEng = new FileInputStream("eng.properties");
					properties.load(fisEng);
					actualizarComponentes(properties);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		btnEng.setBounds(37, 196, 90, 23);
		getContentPane().add(btnEng);

		// Etiqueta del titulo sobre las palabras
		lblTitulo = new JLabel(properties.getProperty("titulo"));
		lblTitulo.setBounds(206, 11, 311, 14);
		getContentPane().add(lblTitulo);

		// Etiquetas de posiciones
		label_1 = new JLabel("1");
		label_1.setBounds(231, 60, 12, 14);
		getContentPane().add(label_1);

		label_2 = new JLabel("2");
		label_2.setBounds(231, 95, 12, 14);
		getContentPane().add(label_2);

		label_3 = new JLabel("3");
		label_3.setBounds(231, 130, 12, 14);
		getContentPane().add(label_3);

		label_4 = new JLabel("4");
		label_4.setBounds(231, 165, 12, 14);
		getContentPane().add(label_4);

		label_5 = new JLabel("5");
		label_5.setBounds(231, 200, 21, 14);
		getContentPane().add(label_5);

		// Palabras con mas apariciones

		textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		textArea_1.setBounds(253, 60, 264, 22);
		getContentPane().add(textArea_1);

		textArea_2 = new JTextArea();
		textArea_2.setEditable(false);
		textArea_2.setBounds(253, 95, 264, 22);
		getContentPane().add(textArea_2);

		textArea_3 = new JTextArea();
		textArea_3.setEditable(false);
		textArea_3.setBounds(253, 130, 264, 22);
		getContentPane().add(textArea_3);

		textArea_4 = new JTextArea();
		textArea_4.setEditable(false);
		textArea_4.setBounds(253, 165, 264, 22);
		getContentPane().add(textArea_4);

		textArea_5 = new JTextArea();
		textArea_5.setEditable(false);
		textArea_5.setBounds(253, 200, 264, 22);
		getContentPane().add(textArea_5);

		lblCambiarLenguaje = new JLabel(properties.getProperty("idioma"));
		lblCambiarLenguaje.setBounds(37, 173, 150, 14);
		getContentPane().add(lblCambiarLenguaje);
	}

	private void posicionarPalabras() {

		textArea_1.setText(arreglo[0].getKey() + " ---> " + String.format("%.3f", arreglo[0].getValue()) + "%");
		textArea_2.setText(arreglo[1].getKey() + " ---> " + String.format("%.3f", arreglo[1].getValue()) + "%");
		textArea_3.setText(arreglo[2].getKey() + " ---> " + String.format("%.3f", arreglo[2].getValue()) + "%");
		textArea_4.setText(arreglo[3].getKey() + " ---> " + String.format("%.3f", arreglo[3].getValue()) + "%");
		textArea_5.setText(arreglo[4].getKey() + " ---> " + String.format("%.3f", arreglo[4].getValue()) + "%");

	}

	private void actualizarComponentes(Properties p) {
		lblCambiarLenguaje.setText(p.getProperty("idioma"));
		lblTitulo.setText(p.getProperty("titulo"));
		btnCargar.setText(p.getProperty("c_directorio"));
		btnComenzar.setText(p.getProperty("comienzo"));

	}

}
