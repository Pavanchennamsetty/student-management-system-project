package com.example.sms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class Main extends JFrame {
    private DefaultListModel<Student> listModel = new DefaultListModel<>();
    private JList<Student> studentJList = new JList<>(listModel);
    private StudentDAO dao = new StudentDAO();

    private JTextField txtName = new JTextField(20);
    private JTextField txtAge = new JTextField(5);
    private JTextField txtEmail = new JTextField(20);

    public Main() {
        super("Student Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,400);
        setLocationRelativeTo(null);

        JPanel left = new JPanel(new BorderLayout());
        left.add(new JScrollPane(studentJList), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.gridx = 0; c.gridy = 0; form.add(new JLabel("Name:"), c);
        c.gridx = 1; form.add(txtName, c);
        c.gridx = 0; c.gridy = 1; form.add(new JLabel("Age:"), c);
        c.gridx = 1; form.add(txtAge, c);
        c.gridx = 0; c.gridy = 2; form.add(new JLabel("Email:"), c);
        c.gridx = 1; form.add(txtEmail, c);

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnRefresh = new JButton("Refresh");

        JPanel buttons = new JPanel();
        buttons.add(btnAdd); buttons.add(btnUpdate); buttons.add(btnDelete); buttons.add(btnRefresh);

        JPanel right = new JPanel(new BorderLayout());
        right.add(form, BorderLayout.NORTH);
        right.add(buttons, BorderLayout.SOUTH);

        getContentPane().setLayout(new GridLayout(1,2));
        getContentPane().add(left);
        getContentPane().add(right);

        // event handlers
        btnAdd.addActionListener(e -> {
            try {
                Student s = new Student(txtName.getText(), Integer.parseInt(txtAge.getText()), txtEmail.getText());
                dao.addStudent(s);
                JOptionPane.showMessageDialog(this, "Student added.");
                refreshList();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            Student sel = studentJList.getSelectedValue();
            if (sel == null) { JOptionPane.showMessageDialog(this, "Select a student to update."); return; }
            try {
                sel.setName(txtName.getText());
                sel.setAge(Integer.parseInt(txtAge.getText()));
                sel.setEmail(txtEmail.getText());
                dao.updateStudent(sel);
                JOptionPane.showMessageDialog(this, "Student updated.");
                refreshList();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            Student sel = studentJList.getSelectedValue();
            if (sel == null) { JOptionPane.showMessageDialog(this, "Select a student to delete."); return; }
            int confirm = JOptionPane.showConfirmDialog(this, "Delete selected student?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    dao.deleteStudent(sel.getId());
                    JOptionPane.showMessageDialog(this, "Deleted.");
                    refreshList();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        });

        btnRefresh.addActionListener(e -> refreshList());

        studentJList.addListSelectionListener(e -> {
            Student s = studentJList.getSelectedValue();
            if (s != null) {
                txtName.setText(s.getName());
                txtAge.setText(String.valueOf(s.getAge()));
                txtEmail.setText(s.getEmail());
            }
        });

        refreshList();
    }

    private void refreshList() {
        try {
            listModel.clear();
            List<Student> students = dao.getAllStudents();
            for (Student s : students) listModel.addElement(s);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not load students: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
