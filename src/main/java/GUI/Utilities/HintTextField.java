package GUI.Utilities;

import GUI.ChessPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class HintTextField extends JTextField implements FocusListener, ActionListener {

    private final String hintText;
    private boolean edited;

    public HintTextField(String hintText){
        this.hintText = hintText;
        this.edited = false;

        this.addFocusListener(this);
        this.addActionListener(this);

        this.setText(hintText);
        this.setFont(new Font("Serif", Font.ITALIC, 18));

        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setBorder(null);

        //color
        this.setBackground(ChessPanel.LIGHT_COLOR);
        this.setForeground(ChessPanel.MOVE_COLOR);
        this.setSelectionColor(ChessPanel.MOVE_COLOR);
        this.setSelectedTextColor(ChessPanel.LIGHT_COLOR);
    }

    /* hint text update */

    @Override
    public void focusGained(FocusEvent e) {
        if (!edited){
            this.setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (getText().isEmpty()){
            this.setText(hintText);
            edited = false;
            return;
        }

        edited = true;
    }

    /* enter pressed */
    @Override
    public void actionPerformed(ActionEvent e) {
        enterPressed();
    }

    public void enterPressed(){}
}
