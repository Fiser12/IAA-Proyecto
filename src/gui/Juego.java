package gui;

import vo.Celda;
import controller.algorithm.Solver;
import vo.Sudoku;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Juego extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private String [][]valores = new String[9][9];
	private int [][] valoresInt = {
			{0,0,6,		4,2,8,	0,0,0},
			{4,5,0,		1,7,6,	0,3,2},
			{0,8,7,		3,9,5,	0,4,1},

			{0,9,3,		5,8,0,	7,0,0},
			{0,4,0,		2,0,7,	0,9,3},
			{7,2,0,		9,0,0,	5,6,0},

			{5,6,8,		0,3,4,	2,0,9},
			{0,1,0,		8,0,0,	3,7,6},
			{0,7,0,		6,1,0,	4,8,0}
	};

	private boolean [][]contiene = new boolean[9][9];
	private JPanel panel;
	private Sudoku nuevo;
	private DefaultTableModel dtm;
	/**
	 * Create the frame.
	 */
	public Juego() {
		nuevo = new Sudoku();
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				if(valoresInt[i][j]!=0)
					nuevo.setCelda(new Celda(valoresInt[i][j], true), i, j);
				else
					nuevo.setCelda(new Celda(valoresInt[i][j], false), i, j);
				valores[i][j] = new Integer(nuevo.getCelda(i,j).getNumero()).toString();
			}
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 4, 0, 0));

		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		contentPane.add(table, BorderLayout.CENTER);

		dtm = new DefaultTableModel(valores, new String[]{"1", "2", "3","4","5","6","7","8","9"}){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(dtm);
		table.setRowHeight(45);
		table.setDefaultRenderer(Object.class, new CellRenderer());
		this.setVisible(true);
		this.repaint();
		new Thread(() -> {
            Solver solver = new Solver();
            solver.setPista(nuevo);
            nuevo = solver.solve();
            for(int i = 0; i<9; i++){
                for(int j = 0; j<9; j++){
                    valores[i][j] = new Integer(nuevo.getCelda(i,j).getNumero()).toString();
                }
            }
            dtm = new DefaultTableModel(valores, new String[]{"1", "2", "3","4","5","6","7","8","9"}){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table.setModel(dtm);
            table.setRowHeight(45);
            table.setDefaultRenderer(Object.class, new CellRenderer());

        }).start();


	}
	
	public class CellRenderer extends DefaultTableCellRenderer{

		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent ( JTable table, Object value, boolean selected, boolean focused, int row, int column )
		{
			JLabel etiqueta = new JLabel();
			etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
			etiqueta.setFont(new Font("Consolas", Font.PLAIN, 20));
			etiqueta.setBorder(new MatteBorder(0, 0, 0, 0,Color.GRAY));
			if(column == 3 || column == 6)
			{
				etiqueta.setBorder(new MatteBorder(0, 5, 0, 0,Color.GRAY));
			}
			if(row == 2 || row == 5)
			{
				etiqueta.setBorder(new MatteBorder(0, 0, 5, 0,Color.GRAY));
			}
			if((row == 2 && column == 3) || (row == 5 && column == 3) || 
					(row == 5 && column == 6) || (row == 2 && column == 6))
			{
				etiqueta.setBorder(new MatteBorder(0, 5, 5, 0,Color.GRAY));
			}
			if((row % 2 == 0 && column % 2 != 0) || (row % 2 != 0 && column % 2 == 0))
			{
				etiqueta.setBackground( Color.lightGray);
			}
			else
			{
				etiqueta.setBackground( Color.WHITE);
			}
			etiqueta = seleccionarColor(table, etiqueta, (String) value);
			etiqueta.setOpaque(true);
			if(esUnNumero((String)value))
			{
				String ayuda = "";
				String a = value.toString();
				valores[row][column]= a;
				a=NumerosIguales(a);
				if(a.length()>1){
					for(int i=0;i<a.length();i++){
						if(i==0){
       				ayuda=ayuda+a.substring(i, i+1);
       			}
       			else{
       				ayuda=ayuda+"|"+a.substring(i, i+1);}}

       		if(ayuda.length()>3){
       			etiqueta.setFont(new Font("Consolas", Font.PLAIN, 10));
       		}  
       		etiqueta.setText(ayuda);
       	}
       	else{
       		etiqueta.setText(a);  
       	}
	    }
	    return etiqueta;
	    }
		public String NumerosIguales(String a)
		{
			String[]b=new String[a.length()];
			String[]c=new String[a.length()];
			String devolver="";
			boolean enc =false;
			for(int i=0;i<a.length();i++){
				b[i]=a.substring(i, i+1);
				enc=false;
				for(int j=0;j<c.length;j++)
				{
					if(b[i].equals(c[j]))
					{
						enc=true;
					}
				}
				if(!enc)
				{
					c[i]=b[i];
				}
				else
				{
					c[i]="";	
				}
			}

			for(int j=0;j < c.length;j++){

				devolver=devolver+c[j];

			}

			return devolver;
		}
		public  boolean esUnNumero(String cadena)
		{
			try {
				Integer.parseInt(cadena);
				return true;
			} catch (NumberFormatException nfe){
				return false;
			}
		}
		public  JLabel seleccionarColor(JTable table, JLabel etiqueta, String value)
		{
			for(int i = 0; i < 9; i++)
			{
				for(int j = 0; j < 9; j++)
				{
					contiene[i][j] = false;
					if(value instanceof String)
					{
						if(value.equals(valores[i][j]))
						{
							contiene[i][j] = true;	
						}
						else
						{
							contiene[i][j] = false;
						}
					}
				}
			}
			for(int i = 0; i < contiene.length; i++)
			{
				for(int j = 0; j < contiene[i].length; j++)
				{
					if(contiene[i][j])
					{
					}
				}
			}
			return etiqueta;
		}
	}
}
