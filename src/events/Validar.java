/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Viviana
 */
public class Validar {

    public Validar() {
    }

    public void validarSoloNumeros(JTextField campo) {
        campo.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
                char c = e.getKeyChar();
                if(Character.isLetter(c)){
                    JOptionPane.showMessageDialog(null,"Solo se permiten Numeros");
                    e.consume();
                }
            }
        });
    }
    
    private JTextField jtextfield = new JTextField();
    
    int limite = 3;
    public void validaLongitud(final JTextField jtextfield){
        jtextfield.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent ke) {
                if(jtextfield.getText().length()>=limite){
                    JOptionPane.showMessageDialog(null, "Solo permite Ingresar del 0 al 100");
                    ke.consume();
                }
            }

        });
    }

}
