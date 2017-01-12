package controller;
import gui.Juego;

import javax.swing.*;
import java.awt.*;

public class SudokuController
{
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Juego frame = new Juego();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
