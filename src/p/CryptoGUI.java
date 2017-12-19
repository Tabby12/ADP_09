package p;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class CryptoGUI
{
  JLabel lp;
  JLabel lq;
  JLabel lmod;
  JLabel lphi;
  JLabel le;
  JLabel ld;
  JLabel ldescription;
  JLabel l_rsakey;
  JLabel l_rsamod;
  JLabel l_sessionkey;
  JLabel l_de_sessionkey;
  JLabel ldescription2;
  JTextArea t_publickeygenerated;
  
  CryptoGUI()
  {
    myrsa = new RSA();
    hawcipher = new HAWcipher();
    JFrame f = new JFrame();
    f.setTitle("HAW cipher. Algorithmen Datenstrukturen. Stephan Pareigis. 2017");
    f.setDefaultCloseOperation(3);
    

    lp = new JLabel("p =");
    lq = new JLabel("q =");
    lmod = new JLabel("N =");
    lphi = new JLabel("phi =");
    le = new JLabel("public key =");
    ld = new JLabel("private key =");
    lp.setBounds(500, 25, 500, 30);
    lq.setBounds(500, 50, 500, 30);
    lmod.setBounds(500, 75, 500, 30);
    lphi.setBounds(500, 100, 500, 30);
    le.setBounds(500, 125, 500, 30);
    ld.setBounds(500, 150, 500, 30);
    
    ldescription = new JLabel("Give this public key to your friend.");
    ldescription.setBounds(50, 120, 300, 50);
    
    t_publickeygenerated = new JTextArea();
    t_publickeygenerated.setBounds(50, 80, 400, 50);
    t_publickeygenerated.setLineWrap(true);
    t_publickeygenerated.setWrapStyleWord(true);
    t_publickeygenerated.setEditable(false);
    t_publickeygenerated.setBackground(Color.LIGHT_GRAY);
    t_publickeygenerated.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
    
    b_createpubickey = new JButton("create public key");
    b_createpubickey.setBounds(250, 25, 150, 50);
    b_createpubickey.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        String text = t_publickeygenerated.getText();
        String[] words = text.split("\\s");
        t_publickeygenerated.setText(myrsa.generateKeys(16));
        le.setText("public key = " + myrsa.publickey);
        ld.setText("private key = " + myrsa.privatekey);
        lp.setText("p = " + myrsa.prime1);
        lq.setText("q = " + myrsa.prime2);
        lmod.setText("N = " + myrsa.modulus);
        lphi.setText("phi = " + myrsa.phi);

      }
      


    });
    b_usepublickey = new JButton("activate this public key");
    b_usepublickey.setBounds(600, 280, 200, 50);
    b_usepublickey.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        try {
          hisrsa = new RSA();
          hisrsa.createRSAwithPubKey(t_pubkeytobeused.getText());
        } catch (Exception exception) {
          javax.swing.JOptionPane.showMessageDialog(null, exception.toString(), "Fehler", 1);
        }
        l_rsakey.setText("public key = " + hisrsa.publickey);
        l_rsamod.setText("modulus = " + hisrsa.modulus);
      }
      
    });
    l_rsakey = new JLabel("public key =");
    l_rsakey.setBounds(300, 330, 300, 50);
    l_rsamod = new JLabel("modulus =");
    l_rsamod.setBounds(600, 330, 400, 50);
    
    ldescription2 = new JLabel("Copy the public key of your friend in here.");
    ldescription2.setBounds(300, 245, 300, 50);
    



    t_pubkeytobeused = new JTextArea();
    t_pubkeytobeused.setBounds(300, 280, 300, 50);
    t_pubkeytobeused.setLineWrap(true);
    t_pubkeytobeused.setWrapStyleWord(true);
    t_pubkeytobeused.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
    


    b_encrypt = new JButton("ENCRYPT");
    b_encrypt.setBounds(50, 280, 230, 90);
    b_encrypt.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        if ((hisrsa == null) || (hisrsa.modulus == null) || (hisrsa.publickey == null) || (hisrsa.modulus == java.math.BigInteger.ZERO) || (hisrsa.publickey == java.math.BigInteger.ZERO)) {
          javax.swing.JOptionPane.showMessageDialog(null, "Error: No public key available!", "Fehler", 1);
        } else {
          try
          {
            t_ciphertext.setText(hawcipher.encrypt(t_cleartext.getText(), hisrsa));
            l_sessionkey.setText("session key = " + HAWcipher.Byte2BigInt(hawcipher.sessionkey));
          } catch (Exception exception) {
            javax.swing.JOptionPane.showMessageDialog(null, exception.toString(), "Fehler", 1);
            exception.printStackTrace();
          }
          
        }
        
      }
    });
    t_cleartext = new JTextArea("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. ");
    t_cleartext.setBounds(50, 380, 800, 60);
    t_cleartext.setLineWrap(true);
    t_cleartext.setWrapStyleWord(true);
    t_cleartext.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
    
    t_ciphertext = new JTextArea();
    t_ciphertext.setBounds(50, 450, 800, 108);
    t_ciphertext.setLineWrap(true);
    t_ciphertext.setWrapStyleWord(true);
    
    t_ciphertext.setEditable(false);
    t_ciphertext.setBackground(Color.LIGHT_GRAY);
    t_ciphertext.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
    

    l_sessionkey = new JLabel("session key =");
    l_sessionkey.setBounds(400, 550, 300, 50);
    

    t_de_cipher = new JTextArea();
    t_de_cipher.setBounds(50, 690, 800, 60);
    t_de_cipher.setLineWrap(true);
    t_de_cipher.setWrapStyleWord(true);
    t_de_cipher.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
    
    t_de_clear = new JTextArea();
    t_de_clear.setBounds(50, 760, 800, 108);
    t_de_clear.setLineWrap(true);
    t_de_clear.setWrapStyleWord(true);
    t_de_clear.setEditable(false);
    t_de_clear.setBackground(Color.LIGHT_GRAY);
    t_de_clear.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
    
    l_de_sessionkey = new JLabel("session key =");
    l_de_sessionkey.setBounds(400, 860, 300, 50);
    
    b_decrypt = new JButton("DECRYPT");
    b_decrypt.setBounds(50, 620, 600, 70);
    b_decrypt.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        try {
          t_de_clear.setText(hawcipher.decrypt(t_de_cipher.getText(), myrsa));
          l_de_sessionkey.setText("session key = " + HAWcipher.Byte2BigInt(hawcipher.sessionkey));
        } catch (Exception exception) {
          javax.swing.JOptionPane.showMessageDialog(null, exception.toString(), "Fehler", 1);
          exception.printStackTrace();
        }
        
      }
      

    });
    b_quit = new JButton("QUIT");
    b_quit.setBounds(800, 870, 50, 50);
    b_quit.setForeground(Color.RED);
    b_quit.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        System.exit(0);

      }
      

    });
    f.add(lp);
    f.add(lq);
    f.add(lmod);
    f.add(lphi);
    f.add(le);
    f.add(ld);
    f.add(ldescription);
    f.add(ldescription2);
    f.add(t_publickeygenerated);
    f.add(b_createpubickey);
    f.add(b_usepublickey);
    f.add(t_pubkeytobeused);
    f.add(l_rsakey);
    f.add(l_rsamod);
    f.add(b_encrypt);
    f.add(t_cleartext);
    f.add(t_ciphertext);
    f.add(l_sessionkey);
    f.add(t_de_cipher);
    f.add(t_de_clear);
    f.add(b_decrypt);
    f.add(l_de_sessionkey);
    f.add(b_quit);
    f.setSize(1200, 1000);
    f.setLayout(null);
    f.setVisible(true);
  }
  
  JTextArea t_pubkeytobeused;
  JTextArea t_cleartext;
  JTextArea t_ciphertext;
  JTextArea t_de_cipher;
  JTextArea t_de_clear;
  JButton b_createpubickey;
  JButton b_usepublickey;
  JButton b_encrypt;
  JButton b_decrypt;
  JButton b_quit;
  RSA myrsa;
  RSA hisrsa;
  HAWcipher hawcipher;
}
