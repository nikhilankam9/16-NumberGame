package number_game;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class gameClass extends Frame implements ActionListener {
	static final int n=4; 
	int dif,moves=0;
	boolean alter = false,undo =false;
	int nowa, nowb;//currently swapped buttons
	int[] a ={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	Button blist[] = new Button[16];
	MenuBar mbar= new MenuBar();
	Menu newgame = new Menu("New Game");
	Menu help = new Menu("Help");
	Menu credits = new Menu("Credits");
	MenuItem item1,item2,item3,item4,item5,item6;
	gameClass() {
		setLayout(new GridLayout(n,n));
		setMenuBar(mbar);
		setFont(new Font("SansSerif", Font.BOLD, 20));

		
    	newgame.add(item1 = new MenuItem("Easy"));
		newgame.add(item2= new MenuItem("Medium"));
		newgame.add(item3 = new MenuItem("Difficult"));
		
		mbar.add(newgame);
		
		help.add(item4 = new MenuItem("Undo"));
		help.add(item5 = new MenuItem("Rules"));
		
		mbar.add(help);
		
		credits.add(item6 = new MenuItem("Credits"));
		
		mbar.add(credits);
		
		for(int i=0;i<n*n-1;i++) {
			blist[i]= new Button();
			blist[i].setLabel(""+(i+1));
			add(blist[i]);
		}
		blist[15]=new Button("");
		add(blist[15]);
		
		
		/*	for(int i=0;i<n;i++)
				int k=randomNum();
				int t = i*n+j;
				if(k==0) {
					blist[t]= new Button();
					blist[t].setLabel("");
					add(blist[t]);
				}
				else {
					blist[t]= new Button();
					blist[t].setLabel(""+(k));
					add(blist[t]);
				}
			}*/
		
		
		for(int i=0;i<n*n;i++) {
			blist[i].addActionListener(this);
		}
		
		MyMenuHandler handler = new MyMenuHandler(this);
		item1.addActionListener(handler);
		item2.addActionListener(handler);
		item3.addActionListener(handler);
		item4.addActionListener(handler);
		item5.addActionListener(handler);
		item6.addActionListener(handler);
		
		addWindowListener(new mywinadap());
	//	repaint();
	}
	
	public void alterfunc(){
		int inswapp=0,lvl=0;
		while(lvl++<dif&&alter) {
			for(int i=0;i<n*n;i++) {
				if(blist[i].getLabel().equals("")) {
					inswapp=i;
				}
			}
			int k=(int) (Math.random()*100)%16;
			//System.out.println(inswapp+" "+k);
			if(isadjacent(k,inswapp)&&k!=0) {
				String str;
				str=blist[k].getLabel();
				blist[k].setLabel(blist[inswapp].getLabel());
				blist[inswapp].setLabel(str);
			}
			
		}
	}
	
	public void actionPerformed(ActionEvent ae) {
		int curr=0, toswapp=0;
		for(int i=0;i<n*n;i++) {
			if(ae.getSource() == blist[i]) {
				curr=i;
			}
			if(blist[i].getLabel().equals("")) {
				toswapp=i;
			}
		}
		if(isadjacent(curr,toswapp)) {
			String str;
			str=blist[curr].getLabel();
			blist[curr].setLabel(blist[toswapp].getLabel());
			blist[toswapp].setLabel(str);
			moves++;
		//	System.out.println(blist[curr].getLabel() + " " + blist[toswapp].getLabel());
		}
		
		if(isdone()){
			winDialog w1 = new winDialog(this,moves);
			w1.setVisible(true);
			//System.out.println("you won");
		}
		nowa=curr;
		nowb=toswapp;
	//	repaint();
	}
	
	public boolean isadjacent(int c,int s){
		if(c+1==s&&c!=7&&c!=11&&c!=3) {
			return true;
		}
		else
		if(c-1==s&&c!=8&&c!=4&&c!=12){
			return true;
		}
		else
		if(c+4==s){
			return true;
		}
		else
		if(c-4==s){
			return true;
		}
		return false;
	}
	
	public boolean isdone(){
		int t=1;
		String str;
		for(int i=0;i<n*n;i++) {
			str=""+(i+1);
			if(str.equals(blist[i].getLabel())) {
				t++;
			}
			//System.out.println(str+" "+t+" ");
		}
		if(t==16)
			return true;
		return false;
	}
	
	public int randomNum() {
		int k;
		while(true) {
			k=(int) (Math.random()*100)%16;
			if(a[k]==0) {
				break;
			}
		}
		a[k]=1;
		 return k;
	}
/*	public void paint(Graphics g){
		String str = "Moves = ";
		g.drawString(str , 350, 5);
	}*/
	
	public static void main(String args[]) {
		gameClass g1 = new gameClass();
		//g1.setFont(new Font("SansSerif", Font.BOLD, 16));
		g1.setSize(new Dimension(300,300));
		g1.setTitle("ankam number game");
		g1.setVisible(true);
	}
}
	
	
class MyMenuHandler implements ActionListener {
	gameClass g;
	public MyMenuHandler(gameClass g) {
		this.g = g;
	}
	
	public void actionPerformed(ActionEvent ae) {
		String arg = ae.getActionCommand();
		if(arg.equals("Easy")) {
			g.dif = 5000;
			g.alter = true;
			g.moves=0;
		}
		else
		if(arg.equals("Medium")) {
				g.dif = 10000;
				g.alter = true;
				g.moves=0;
			}
		else
			if(arg.equals("Difficult")) {
				g.dif = 20000;
				g.alter = true;
				g.moves=0;
			}
		else
		if(arg.equals("Undo")){
			String str;
			str=g.blist[g.nowa].getLabel();
			g.blist[g.nowa].setLabel(g.blist[g.nowb].getLabel());
			g.blist[g.nowb].setLabel(str);
			g.nowa=0;
			g.nowb=0;
			g.moves++;
		}
		else
		if(arg.equals("Rules")){
			String rules = "You must click on a button to move it.\n"+
					"Only one swap will be undone.\n"+"Arrange the numbers 1-15 to win.\n";
			ruleDialog r1 = new ruleDialog(g,rules,4);
			r1.setVisible(true);
			}
		else
		if(arg.equals("Credits")){
			String credit ="This code is written by ANKAM NIKHIL.\n"+"Using AWT API in java UI programming.\n"
						+"It contains the following features\n"+"Buttons\n"+"MenuBar\n"+"TextArea\n"+"Event Handling\n"+"Layouts\n"
					+"Frames and Dialogs Boxes\n";
			ruleDialog r2 = new ruleDialog(g,credit,10);
			r2.setVisible(true);
		}
		if(g.alter){
			g.alterfunc();
			g.alter=false;
		}		
	}
}

class mywinadap extends WindowAdapter {
	public void windowClosing(WindowEvent w) {
		System.exit(0);
	}
}


@SuppressWarnings("serial")
class winDialog extends Dialog implements ActionListener{
	int mov;
	winDialog(Frame parent,int mov){
		super(parent, "Winner", true);
		this.mov=mov;
		setLayout(new FlowLayout());
		setFont(new Font("SansSerif", Font.BOLD, 20));
		setSize(300,200);
		
		add(new Label ("Press this button to end"));
		Button b;
		add(b= new Button("close"));
		b.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae){
		dispose();
	}
	public void paint(Graphics g){
	//	String s = "";
		System.out.println(mov);
		g.drawString("Moves = ", 90, 135);
		g.drawString("You won",100,170);
	}
}

class ruleDialog extends Dialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6203303774993737126L;

	ruleDialog(Frame parent,String value,int n){
		super(parent, "Winner", true);
		setLayout(new FlowLayout());
		setFont(new Font("SansSerif", Font.ITALIC, 16));
		setSize(400,300);
				
		add(new Label ("Press this button to end"));
		Button b;
		add(b= new Button("close"));
		b.addActionListener(this);
		
		TextArea text = new TextArea(value,n,35);
		text.setEditable(false);
		add(text);
	}
	
	public void actionPerformed(ActionEvent ae){
		dispose();
	}
}

    
