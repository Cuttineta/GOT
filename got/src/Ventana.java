import java.awt.EventQueue;

public class Ventana {

	GUI gui;

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				Ventana v = new Ventana();
				v.gui.setVisible(true);

			}
		});

	}

	public Ventana() {
		initialize();

	}

	private void initialize() {

		gui = new GUI();
		gui.setBounds(200, 100, 600, 350);

	}

}
