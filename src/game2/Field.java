package game2;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Field extends JFrame{
	private int n;
	private int m;
	private Vector<Vector<Cell>> field = null;
	private Point selected = null;
	private final int X = 50;
	private final int Y = 100;
	private final int S = 50;
	public Field(int n, int m){
		super("Порталы");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setOptions(n, m);
		setKeys();
		setMenuBar();
		setSize(getMinimumSize());
		setUndecorated(true);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(700, 700));
		setVisible(true);
	}
	private void setKeys(){
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent key){
				boolean changes = false;
				switch (key.getKeyCode()){
					case KeyEvent.VK_LEFT:
						if (selected.x > 0){
							selected.x--;
							changes = true;
						}
						break;
					case KeyEvent.VK_RIGHT:
						if (selected.x < n-1){
							selected.x++;
							changes = true;
						}
						break;
					case KeyEvent.VK_UP:
						if (selected.y > 0){	
							selected.y--;
							changes = true;
						}
						break;
					case KeyEvent.VK_DOWN:
						if (selected.y < m-1){
							selected.y++;
							changes = true;
						}
						break;
					case KeyEvent.VK_SPACE:
						int a = field.get(selected.y).get(selected.x).getSecondJ();
						int b = field.get(selected.y).get(selected.x).getSecondI();
						if (!pointEquals(new Point(a, b), selected)){
							selected.x=a;
							selected.y=b;
							changes = true;
						}
						break;
				}
				if (changes){
					validate();
					repaint();
				}
			}
		});
	}
	private void setOptions(int n, int m){
		setSelected(new Point(0, 0));
		this.setN(n);
		this.setM(m);
		field = new Vector<>();
		for (int i=0; i<n; i++){
			Vector<Cell> temp = new Vector<>();
			for (int j=0; j<m; j++){
				Cell t = new Cell(i, j, 3, 5);
				temp.add(t);
			}
			field.add(temp);
		}
		getCell(0, 1).setSecondI(0);
		getCell(0, 1).setSecondJ(getM()-1);
		getCell(1, 0).setSecondI(getN()-1);
		getCell(1, 0).setSecondJ(0);
	}
	private void clear(){
		n=0;
		m=0;
		field = null;
		selected=null;
	}
	private boolean sizeEquals(Dimension d1, Dimension d2){
		return (d1.height == d2.height && d1.width == d2.width);
	}
	private boolean pointEquals(Point p1, Point p2){
		return (p1.x == p2.x && p1.y == p2.y);
	}
	private void setMenuBar(){
		Menu options = new Menu("Options");
		MenuItem settings = new MenuItem("Settings");
		settings.addActionListener(new ActionListener(){
			class Settings{
				Settings(){
					JFrame jf = new JFrame("Options");
					JRadioButton fs = new JRadioButton("Полноэкранный режим");
					JRadioButton nfs = new JRadioButton("Оконный режим");
					ButtonGroup gr = new ButtonGroup();
					gr.add(fs);
					gr.add(nfs);
					if (sizeEquals(getSize(), Toolkit.getDefaultToolkit().getScreenSize())){
						fs.setSelected(true);
					}
					else{
						nfs.setSelected(true);
					}
					JPanel jp = new JPanel();
					JButton ok = new JButton("Ok");
					ok.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
							if (fs.isSelected() && !sizeEquals(getSize(), Toolkit.getDefaultToolkit().getScreenSize())){
								setSize(Toolkit.getDefaultToolkit().getScreenSize());
								setLocationRelativeTo(null);
								dispose();
								setUndecorated(true);
								setVisible(true);
							}
							else if (nfs.isSelected() && sizeEquals(getSize(), Toolkit.getDefaultToolkit().getScreenSize())){
								setSize(4*X+S*n, 2*Y+S*m);
								setLocationRelativeTo(null);
								dispose();
								setUndecorated(false);
								setVisible(true);
							}
							jf.dispose();
						}
						
					});
					jf.add(jp);
					jp.add(fs);
					jp.add(nfs);
					jf.add(ok, BorderLayout.PAGE_END);
					jf.setSize(300, 300);
					jf.setLocationRelativeTo(null);
					jf.setVisible(true);
				}
			}
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Settings();
				repaint();
			}
		});
		options.add(settings);
		Menu game = new Menu("Игра");
		MenuItem newGame = new MenuItem("Новая игра");
		MenuItem exit = new MenuItem("Выход");
		newGame.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
				setOptions(5, 5);
			}
			
		});
		exit.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		game.add(newGame);
		game.addSeparator();
		game.add(exit);
		MenuBar mb = new MenuBar();
		mb.add(game);
		mb.add(options);
		setMenuBar(mb);
	}
	private void setCell(int i, int j, Cell c){
		field.get(i).get(j).setI(c.getI());
		field.get(i).get(j).setJ(c.getJ());
		field.get(i).get(j).setSecondI(c.getSecondI());
		field.get(i).get(j).setSecondJ(c.getSecondJ());		
	}
	private Cell getCell(int i, int j){
		return field.get(i).get(j);
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		for (int i=0; i<n; i++){
			for (int j=0; j<m; j++){
				g2d.drawRect(X+S*i, Y+S*j, S, S);
			}
		}
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(4));
		g2d.drawRect(S*selected.x+X, S*selected.y+Y, S, S);
	}
	private int getN() {
		return n;
	}
	private void setN(int n) {
		this.n = n;
	}
	private int getM() {
		return m;
	}
	private void setM(int m) {
		this.m = m;
	}
	private Point getSelected() {
		return selected;
	}
	private void setSelected(Point selected) {
		this.selected = selected;
	}
}