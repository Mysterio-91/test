import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class V_Connexion extends JFrame implements ActionListener {
	
	static Connection bdd;
	static Statement st;
	static ResultSet rs;
	static ResultSet resultat;
	public static void connexionBDD() {
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		bdd = DriverManager.getConnection("jdbc:mysql://172.16.203.100/2019boudalia?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "iboudalia", "123456");
		st = bdd.createStatement();
		}
		
		catch(ClassNotFoundException erreur) {
			 System.out.println("Driver non chargé !" + erreur);
		}
		
		catch(SQLException erreur) {
			 System.out.println("La connexion à la base de données a échoué ou Erreur SQL…" + erreur);
		}
	
	}
	
	public static void deconnexionBDD() {
		try {
			bdd.close();
		}
		catch(SQLException erreur) {
			 System.out.println("Echec de la deconnexion" + erreur);
		}
	}
	
	  	Container container = getContentPane();
	    JLabel utilisateurLabel = new JLabel("PSEUDO");
	    JLabel mdpLabel = new JLabel("MOT DE PASSE");
	    JTextField userTextField = new JTextField();
	    JPasswordField passwordField = new JPasswordField();
		JButton connexButton = new JButton("CONNEXION");
		
		V_Connexion(){
			setLayoutManager();
	        setLocationAndSize();
	        addComponentsToContainer();
	        addActionEvent();
	        setTitle("Formulaire de connexion");
	        setBounds(10, 10, 370, 400);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setResizable(false);
	        setLocationRelativeTo(null);
	        setVisible(true);
			
		}
		
		public void setLayoutManager() {
		    container.setLayout(null);
		}
		    
		public void setLocationAndSize() {
		    utilisateurLabel.setBounds(50, 100, 100, 30);
		    mdpLabel.setBounds(50, 150, 100, 30);
		    userTextField.setBounds(150, 100, 150, 30);
		    passwordField.setBounds(150, 150, 150, 30);
		    connexButton.setBounds(100, 250, 150, 30);


		}
		
		public void addComponentsToContainer() {
	        container.add(utilisateurLabel);
	        container.add(mdpLabel);
	        container.add(userTextField);
	        container.add(passwordField);
	        container.add(connexButton);
	    }
		
		public void addActionEvent() {
	        connexButton.addActionListener(this);
	    }
		
		public void actionPerformed(ActionEvent e) {
			connexionBDD();
			String chaine = "";
	        if (e.getSource() == connexButton) {
	            String userText;
	            String pwdText;
	            userText = userTextField.getText();
	            pwdText = passwordField.getText();
	            String pseudo = userText;
	            String mdp = pwdText;  
	            String requete = "SELECT mdp FROM inscription WHERE pseudo = '"+pseudo+"'";
				try {
					ResultSet res = st.executeQuery(requete);		
				
				if(res.next()){
                    
                    String motDePasse = res.getString(1);
          
                if(motDePasse.equals(mdp)){
              
                    JOptionPane.showMessageDialog(null,"Connexion réussie ! ","Success",JOptionPane.PLAIN_MESSAGE);
                    this.dispose();
                    Resultat unResultat = new Resultat();
                }else {
                      
                    JOptionPane.showMessageDialog(null,"Mot de passe incorrect ! ","Error",1);
                }
                }else {
                      
                    JOptionPane.showMessageDialog(null,"Login incorrect ! ","Error",1);
                }
				} catch (SQLException erreur) {
					erreur.printStackTrace();
					chaine += "Votre requête ne peux aboutir.";
				}

	        }
		}
}
