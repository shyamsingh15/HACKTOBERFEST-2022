package swing;

import JDBC.studentDatabase.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserDetail extends JFrame implements ActionListener
{
	 public UserDetail(String id)
	 {
		super("User Details");
		setLayout(null);
		setSize(350,370);
		setLocation(300,90);
		try{
		Connection con=new JDBCConnection().getConnection();
		Statement st=con.createStatement();
	    String query="select * from Registration where mob_no='"+id+"'";
		ResultSet rs=st.executeQuery(query);
		if(rs.next()){}
		String name="Name:  "+rs.getString(1);
		String num="Mob no:  "+Long.toString(rs.getLong(2));
		String gen="Gender:  "+rs.getString(3);
		String dob="DOB:  "+rs.getString(4).substring(0,10);
		String addr="Address:  "+rs.getString(5);
		JLabel l=new JLabel("User Details");
		l.setBounds(100,20,220,35);
		l.setFont(new Font("Monaco", Font.BOLD, 25));
		add(l);
		
		JLabel l1=new JLabel(name);
		l1.setBounds(50,70,300,30);
		l1.setFont(new Font("Monaco", Font.BOLD, 20));
		add(l1);
		
		JLabel l2=new JLabel(num);
		l2.setBounds(50,110,300,30);
		l2.setFont(new Font("Monaco", Font.BOLD, 20));
		add(l2);
		
		JLabel l3=new JLabel(gen);
		l3.setBounds(50,150,300,30);
		l3.setFont(new Font("Monaco", Font.BOLD, 20));
		add(l3);
		
		JLabel l4=new JLabel(dob);
		l4.setBounds(50,190,300,30);
		l4.setFont(new Font("Monaco", Font.BOLD, 20));
		add(l4);
		
		JLabel l5=new JLabel(addr);
		l5.setBounds(50,230,300,30);
		l5.setFont(new Font("Monaco", Font.BOLD, 20));
		add(l5);
		
		Icon icon = new ImageIcon("D:/java/swing/logout.jpg");
		JButton b=new JButton(icon);
		b.setBounds(240,270,40,40);
		b.addActionListener(this);
		add(b);
		new JDBCConnection().stopConnection();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		catch(Exception ex){
		  System.out.println(ex);
		}
	 }
	 public void actionPerformed(ActionEvent e)
	 {
		 this.setVisible(false);
		 new Login();
	 }
	 
	 public static void main(String[] args)
	 {
		 new UserDetail("9005342733");
	 }
}