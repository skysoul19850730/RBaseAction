package com.skysoul.plugin.rbase.ui;

import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.util.PsiUtil;
import com.skysoul.plugin.rbase.options.CustomClass;
import com.skysoul.plugin.rbase.options.RBaseOptions;

import javax.swing.*;
import java.awt.event.*;

public class SelectDialog extends JDialog {
    public static final int SELECT_ACTIVITY = 1;
    public static final int SELECT_FRAGMENT = 2;

    public interface SelectDialogListener {
        void onClickOk(SelectDialog dialog, String preName, String name, int select);
        AnActionEvent getActionEvent();
    }

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField et_name_pre;
    private JTextField et_name;
    private JRadioButton rbtn_activity;
    private JRadioButton rbtn_fragment;
    private JLabel errorLabel;
    private JButton RBaseActivityButton;
    private JButton RBaseFragmentButton;
    private JButton RBaseViewModelButton;
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

        CustomClass activity = RBaseOptions.INSTANCE.getInstanse().getActivity();
        if(activity!=null){
            RBaseActivityButton.setText(activity.getClassName());
        }
        CustomClass fragment = RBaseOptions.INSTANCE.getInstanse().getFragment();
        if(fragment!=null){
            RBaseFragmentButton.setText(fragment.getClassName());
        }
        CustomClass viewmodel = RBaseOptions.INSTANCE.getInstanse().getViewModel();
        if(viewmodel!=null){
            RBaseViewModelButton.setText(viewmodel.getClassName());
        }
        RBaseActivityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreeClassChooser classChooser = TreeClassChooserFactory.getInstance(listener.getActionEvent().getProject()).createAllProjectScopeChooser("Select the java File which does the Dfga log");
                classChooser.showDialog();
                PsiClass selectJavaClass = classChooser.getSelected();
                if(selectJavaClass!=null){
                    RBaseActivityButton.setText(selectJavaClass.getName());
                    CustomClass customClass = new CustomClass();
                    customClass.setClassName(selectJavaClass.getName());
                    String pName = JavaDirectoryService.getInstance().getPackage((PsiDirectory) selectJavaClass.getContainingFile().getParent()).getQualifiedName();
                    customClass.setClassPackage(pName);
                    RBaseOptions.INSTANCE.getInstanse().setActivity(customClass);
                }
            }
        });
        RBaseFragmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreeClassChooser classChooser = TreeClassChooserFactory.getInstance(listener.getActionEvent().getProject()).createAllProjectScopeChooser("Select the java File which does the Dfga log");
                classChooser.showDialog();
                PsiClass selectJavaClass = classChooser.getSelected();
                if(selectJavaClass!=null){
                    RBaseFragmentButton.setText(selectJavaClass.getName());
                    CustomClass customClass = new CustomClass();
                    customClass.setClassName(selectJavaClass.getName());
                    String pName = JavaDirectoryService.getInstance().getPackage((PsiDirectory) selectJavaClass.getContainingFile().getParent()).getQualifiedName();
                    customClass.setClassPackage(pName);
                    RBaseOptions.INSTANCE.getInstanse().setFragment(customClass);
                }
            }
        });
        RBaseViewModelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreeClassChooser classChooser = TreeClassChooserFactory.getInstance(listener.getActionEvent().getProject()).createAllProjectScopeChooser("Select the java File which does the Dfga log");
                classChooser.showDialog();
                PsiClass selectJavaClass = classChooser.getSelected();
                if(selectJavaClass!=null){
                    RBaseViewModelButton.setText(selectJavaClass.getName());
                    CustomClass customClass = new CustomClass();
                    customClass.setClassName(selectJavaClass.getName());
                    String pName = JavaDirectoryService.getInstance().getPackage((PsiDirectory) selectJavaClass.getContainingFile().getParent()).getQualifiedName();
                    customClass.setClassPackage(pName);
                    RBaseOptions.INSTANCE.getInstanse().setViewModel(customClass);
                }
            }
        });

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
