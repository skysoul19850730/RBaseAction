package com.skysoul.plugin.rbase.ui;

import javax.swing.*;
import java.awt.event.*;

public class SelectDialog extends JDialog {
    public static final int SELECT_ACTIVITY = 1;
    public static final int SELECT_FRAGMENT = 2;

    public interface SelectDialogListener {
        void onClickOk(SelectDialog dialog, String preName, String name, int select);
    }

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField et_name_pre;
    private JTextField et_name;
    private JRadioButton rbtn_activity;
    private JRadioButton rbtn_fragment;
    private JLabel errorLabel;
    private SelectDialogListener mListener;

    public SelectDialog(SelectDialogListener listener) {
        super();
        this.mListener = listener;

        setContentPane(contentPane);
//        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setAlwaysOnTop(true);
        setSize(350, 350);
        setLocationRelativeTo(null);


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void showError(String msg) {
        errorLabel.setText(msg);
    }

    public static void show(SelectDialogListener listener) {
        SelectDialog dialog = new SelectDialog(listener);
        dialog.setVisible(true);
    }

    private void onOK() {
        // add your code here
        if (et_name.getText() == null || et_name.getText().length() == 0) {
            return;
        }
        if (mListener != null) {
            int select = SELECT_ACTIVITY;
            if (rbtn_fragment.isSelected()) {
                select = SELECT_FRAGMENT;
            }
            mListener.onClickOk(this, et_name_pre.getText(), et_name.getText(), select);
        } else {
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
