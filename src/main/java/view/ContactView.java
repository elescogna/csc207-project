package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import interface_adapter.ViewManagerModel;
import interface_adapter.contact.ContactController;
import interface_adapter.contact.ContactState;
import interface_adapter.contact.ContactViewModel;
import interface_adapter.home.HomeViewModel;

public class ContactView extends JPanel implements PropertyChangeListener {

    private static final long serialVersionUID = 1L;

    private JTextField textFieldSubject;
    private JLabel lblTitle;
    private JLabel lblSubject;
    private JLabel lblBody;
    private JTextArea textAreaBody;
    private JLabel lblTo;
    private JLabel lblItem;
    private JButton btnBack;
    private JButton btnSend;

    private ContactViewModel contactViewModel;
    private ContactController contactController;
    private HomeViewModel homeViewModel;
    private ViewManagerModel viewManagerModel;
    private Image backgroundImage;

    /**
     * Create the panel.
     */
    public ContactView(ContactViewModel contactViewModel,
            ContactController contactController,
            HomeViewModel homeViewModel, ViewManagerModel viewManagerModel) {
    	setBackground(new Color(0, 0, 0));
        this.setLayout(null);

        this.contactViewModel = contactViewModel;
        this.contactController = contactController;
        this.homeViewModel = homeViewModel;
        this.viewManagerModel = viewManagerModel;

        try {
            String basePath = System.getProperty("user.dir");
            String imagePath = basePath + "/assets/images/UC2.png";
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        lblTitle = new JLabel("Contact");
        lblTitle.setForeground(new Color(255, 255, 255));
        lblTitle.setFont(new Font("Modern No. 20", Font.BOLD, 26));
        lblTitle.setBounds(451, 115, 200, 26);
        add(lblTitle);

        lblSubject = new JLabel("Subject:");
        lblSubject.setForeground(new Color(255, 255, 255));
        lblSubject.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        lblSubject.setBounds(80, 276, 60, 17);
        add(lblSubject);

        textFieldSubject = new JTextField();
        textFieldSubject.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                ContactState currentState =
                    ContactView.this.contactViewModel.getState();
                currentState.setSubjectText(textFieldSubject.getText() +
                        e.getKeyChar());
            }
        });
        textFieldSubject.setBounds(80, 326, 816, 21);
        add(textFieldSubject);
        textFieldSubject.setColumns(10);

        lblBody = new JLabel("Body:");
        lblBody.setForeground(new Color(255, 255, 255));
        lblBody.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        lblBody.setBounds(83, 386, 119, 17);
        add(lblBody);

        textAreaBody = new JTextArea();
        textAreaBody.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                ContactState currentState =
                    ContactView.this.contactViewModel.getState();
                currentState.setBodyText(textAreaBody.getText() + e.getKeyChar());
            }
        });
        textAreaBody.setBounds(83, 432, 813, 197);
        add(textAreaBody);

        lblTo = new JLabel("To:");
        lblTo.setForeground(new Color(255, 255, 255));
        lblTo.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        lblTo.setBounds(80, 187, 60, 17);
        add(lblTo);

        lblItem = new JLabel("Item:");
        lblItem.setForeground(new Color(255, 255, 255));
        lblItem.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        lblItem.setBounds(80, 231, 60, 17);
        add(lblItem);

        btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ContactView.this.viewManagerModel.setActiveView(ContactView.this.homeViewModel.getViewName());
                ContactView.this.viewManagerModel.firePropertyChanged();
            }
        });
        btnBack.setBounds(221, 702, 105, 27);
        add(btnBack);

        btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ContactState currentState =
                    ContactView.this.contactViewModel.getState();

                ContactView.this.contactController.execute(
                        currentState.getCurrentItem(), currentState.getSubjectText(),
                        currentState.getBodyText());
            }
        });
        btnSend.setBounds(586, 702, 105, 27);
        add(btnSend);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        // two situations: error is null (flash success message) or error is not
        // (flash error message)
        ContactState currentState = (ContactState)e.getNewValue();

        if (currentState.getError().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Message sent successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to send message.");
        }
    }
}
