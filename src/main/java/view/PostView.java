package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import interface_adapter.ViewManagerModel;
import interface_adapter.home.HomeViewModel;
import interface_adapter.post.PostController;
import interface_adapter.post.PostState;
import interface_adapter.post.PostViewModel;

public class PostView extends JPanel {
    private final HomeViewModel homeViewModel;
    private ViewManagerModel viewManagerModel;
    private final PostController postController;
    private final PostViewModel postViewModel;
    private JLabel categoryLabel;
    private JLabel typeLabel;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel conditionScoreLabel;
    private JLabel pickupAddressLabel;
    private JLabel ageLabel;
    private JLabel priceLabel;
    private JLabel imageLabel;
    private JLabel lengthLabel;
    private JLabel widthLabel;
    private JLabel heightLabel;
    private JLabel technologyBrandLabel;
    private JLabel capabilitiesLabel;
    private JLabel technologyColourLabel;
    private JLabel schoolItemBrandLabel;
    private JLabel schoolItemColourLabel;
    private JLabel clothingBrandLabel;
    private JLabel clothingColourLabel;
    private JLabel sizeLabel;
    private JLabel materialLabel;
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> typeComboBox;
    private JTextField nameTextField;
    private JTextField descriptionTextField;
    private JComboBox<Integer> conditionScoreComboBox;
    private JTextField pickupAddressTextField;
    private JTextField ageTextField;
    private JTextField priceTextField;
    private JTextField lengthTextField;
    private JTextField widthTextField;
    private JTextField heightTextField;
    private JComboBox<String> technologyBrandComboBox;
    private JTextField capabilitiesTextField;
    private JTextField technologyColourTextField;
    private JTextField schoolItemBrandTextField;
    private JTextField schoolItemColourTextField;
    private JComboBox<String> clothingColourComboBox;
    private JComboBox<String> sizeComboBox;
    private JTextField materialTextField;
    private JTextField clothingBrandTextField;
    private JTextField imageTextField;
    private JButton postButton;
    private JButton backButton;
    private JButton btnBrowse;
    private JLabel lblTitle;
    private Image backgroundImage;

    public PostView(HomeViewModel homeViewModel,
            PostController postController, PostViewModel postViewModel, ViewManagerModel viewManagerModel) {
    	setBackground(new Color(0, 0, 0));
        this.postController = postController;
        this.postViewModel = postViewModel;
        this.homeViewModel = homeViewModel;
        this.viewManagerModel = viewManagerModel;

        try {
            String basePath = System.getProperty("user.dir");
            String imagePath = basePath + "/assets/images/UC2.png";
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setLayout(null);
        initializeComponents();
        addComponents();
        addListeners();

        setVisible(true);

        postButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PostState currentState = PostView.this.postViewModel.getState();
                if ("Furniture".equals(currentState.getCategory())) {
                    PostView.this.postController.execute(
                            currentState.getStudent(), currentState.getCategory(),
                            currentState.getType(), currentState.getName(),
                            currentState.getDescription(), currentState.getPickupAddress(),
                            currentState.getConditionScore(), currentState.getAge(),
                            currentState.getPrice(), currentState.getLength(),
                            currentState.getWidth(), currentState.getHeight(), currentState.getImagePath());
                } else if ("Technology".equals(currentState.getCategory())) {
                    PostView.this.postController.execute(
                            currentState.getStudent(), currentState.getCategory(),
                            currentState.getType(), currentState.getName(),
                            currentState.getDescription(), currentState.getPickupAddress(),
                            currentState.getConditionScore(), currentState.getAge(),
                            currentState.getPrice(), currentState.getBrand(),
                            currentState.getCapabilities(), currentState.getColour(), currentState.getImagePath());
                } else if ("SchoolItem".equals(currentState.getCategory())) {
                    PostView.this.postController.execute(
                            currentState.getStudent(), currentState.getCategory(),
                            currentState.getType(), currentState.getName(),
                            currentState.getDescription(), currentState.getPickupAddress(),
                            currentState.getConditionScore(), currentState.getAge(),
                            currentState.getPrice(), currentState.getBrand(),
                            currentState.getColour(), currentState.getImagePath());
                } else if ("Clothing".equals(currentState.getCategory())) {
                    PostView.this.postController.execute(
                            currentState.getStudent(), currentState.getCategory(),
                            currentState.getType(), currentState.getName(),
                            currentState.getDescription(), currentState.getPickupAddress(),
                            currentState.getConditionScore(), currentState.getAge(),
                            currentState.getPrice(), currentState.getBrand(),
                            currentState.getColour(), currentState.getSize(),
                            currentState.getMaterial(), currentState.getImagePath());
                } else {
                    System.out.println(
                            "Invalid category selected or all fields have not been filled");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PostView.this.viewManagerModel.setActiveView(PostView.this.homeViewModel.getViewName());
                PostView.this.viewManagerModel.firePropertyChanged();
            }
        });

        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(categoryComboBox)) {
                    PostState currentState = PostView.this.postViewModel.getState();
                    Object item = categoryComboBox.getSelectedItem();
                    currentState.setCategory((String)item);
                    PostView.this.postViewModel.setState(currentState);
                }
            }
        });

        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(typeComboBox)) {
                    PostState currentState = PostView.this.postViewModel.getState();
                    Object item = typeComboBox.getSelectedItem();
                    currentState.setType((String)item);
                    PostView.this.postViewModel.setState(currentState);
                }
            }
        });

        nameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                PostState currentState = PostView.this.postViewModel.getState();
                String text = nameTextField.getText() + e.getKeyChar();
                currentState.setName(text);
                PostView.this.postViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        descriptionTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                PostState currentState = PostView.this.postViewModel.getState();
                String text = descriptionTextField.getText() + e.getKeyChar();
                currentState.setDescription(text);
                PostView.this.postViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        conditionScoreComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(conditionScoreComboBox)) {
                    PostState currentState = PostView.this.postViewModel.getState();
                    Object item = conditionScoreComboBox.getSelectedItem();
                    currentState.setConditionScore((int)item);
                    PostView.this.postViewModel.setState(currentState);
                }
            }
        });

        pickupAddressTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                PostState currentState = PostView.this.postViewModel.getState();
                String text = pickupAddressTextField.getText() + e.getKeyChar();
                currentState.setPickupAddress(text);
                PostView.this.postViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        ageTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                PostState currentState = PostView.this.postViewModel.getState();
                String text = (ageTextField.getText()) + e.getKeyChar();
                currentState.setAge(Integer.parseInt(text));
                PostView.this.postViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        priceTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                PostState currentState = PostView.this.postViewModel.getState();
                String text = (priceTextField.getText()) + e.getKeyChar();
                currentState.setPrice(Integer.parseInt(text));
                PostView.this.postViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        lengthTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                PostState currentState = PostView.this.postViewModel.getState();
                String text = (lengthTextField.getText()) + e.getKeyChar();
                currentState.setLength(Double.parseDouble(text));
                PostView.this.postViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        widthTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                PostState currentState = PostView.this.postViewModel.getState();
                String text = (widthTextField.getText()) + e.getKeyChar();
                currentState.setWidth(Double.parseDouble(text));
                PostView.this.postViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        heightTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                PostState currentState = PostView.this.postViewModel.getState();
                String text = (heightTextField.getText()) + e.getKeyChar();
                currentState.setHeight(Double.parseDouble(text));
                PostView.this.postViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        technologyBrandComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(technologyBrandComboBox)) {
                    PostState currentState = PostView.this.postViewModel.getState();
                    Object item = technologyBrandComboBox.getSelectedItem();
                    currentState.setBrand((String)item);
                    PostView.this.postViewModel.setState(currentState);
                }
            }
        });

        capabilitiesTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                PostState currentState = PostView.this.postViewModel.getState();
                String text = (capabilitiesTextField.getText()) + e.getKeyChar();
                currentState.setCapabilities(text);
                PostView.this.postViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        technologyColourTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                PostState currentState = PostView.this.postViewModel.getState();
                String text = (technologyColourTextField.getText()) + e.getKeyChar();
                currentState.setColour(text);
                PostView.this.postViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        schoolItemBrandTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                PostState currentState = PostView.this.postViewModel.getState();
                String text = (schoolItemBrandTextField.getText()) + e.getKeyChar();
                currentState.setBrand(text);
                PostView.this.postViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        schoolItemColourTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                PostState currentState = PostView.this.postViewModel.getState();
                String text = (schoolItemColourTextField.getText()) + e.getKeyChar();
                currentState.setColour(text);
                PostView.this.postViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        clothingBrandTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                PostState currentState = PostView.this.postViewModel.getState();
                String text = (clothingBrandTextField.getText()) + e.getKeyChar();
                currentState.setBrand(text);
                PostView.this.postViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        clothingColourComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(clothingColourComboBox)) {
                    PostState currentState = PostView.this.postViewModel.getState();
                    Object item = clothingColourComboBox.getSelectedItem();
                    currentState.setColour((String)item);
                    PostView.this.postViewModel.setState(currentState);
                }
            }
        });

        sizeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(sizeComboBox)) {
                    PostState currentState = PostView.this.postViewModel.getState();
                    Object item = sizeComboBox.getSelectedItem();
                    currentState.setSize((String)item);
                    PostView.this.postViewModel.setState(currentState);
                }
            }
        });

        materialTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                PostState currentState = PostView.this.postViewModel.getState();
                String text = (materialTextField.getText()) + e.getKeyChar();
                currentState.setMaterial(text);
                PostView.this.postViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    private void initializeComponents() {
        postButton = new JButton("Post");
        postButton.setBounds(640, 672, 89, 23);

        backButton = new JButton("Back");
        backButton.setBounds(323, 672, 89, 23);

        categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        categoryLabel.setForeground(new Color(255, 255, 255));
        categoryLabel.setBounds(76, 156, 87, 20);
        categoryComboBox = new JComboBox<>(
                new String[] {"Furniture", "Technology", "SchoolItem", "Clothing"});
        categoryComboBox.setBounds(211, 158, 98, 24);
        categoryComboBox.setSelectedIndex(0);

        typeLabel = new JLabel("Type:");
        typeLabel.setForeground(new Color(255, 255, 255));
        typeLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        typeLabel.setBounds(76, 202, 87, 20);
        typeComboBox = new JComboBox<>();
        typeComboBox.setBounds(211, 204, 98, 24);

        nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        nameLabel.setForeground(new Color(255, 255, 255));
        nameLabel.setBounds(76, 248, 87, 20);
        nameTextField = new JTextField();
        nameTextField.setBounds(211, 250, 100, 20);
        nameTextField.setColumns(10);

        descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        descriptionLabel.setForeground(new Color(255, 255, 255));
        descriptionLabel.setBounds(76, 296, 87, 20);
        descriptionTextField = new JTextField();
        descriptionTextField.setBounds(211, 298, 100, 20);
        descriptionTextField.setColumns(10);

        conditionScoreLabel = new JLabel("Condition:");
        conditionScoreLabel.setForeground(new Color(255, 255, 255));
        conditionScoreLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        conditionScoreLabel.setBounds(76, 351, 109, 20);
        conditionScoreComboBox = new JComboBox<>(new Integer[] {1, 2, 3, 4, 5});
        conditionScoreComboBox.setBounds(211, 353, 100, 24);
        conditionScoreComboBox.setSelectedIndex(0);

        pickupAddressLabel = new JLabel("Pickup Address:");
        pickupAddressLabel.setFont(
                new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        pickupAddressLabel.setForeground(new Color(255, 255, 255));
        pickupAddressLabel.setBounds(76, 404, 120, 20);
        pickupAddressTextField = new JTextField();
        pickupAddressTextField.setBounds(211, 406, 100, 20);
        pickupAddressTextField.setColumns(10);

        ageLabel = new JLabel("Age:");
        ageLabel.setForeground(new Color(255, 255, 255));
        ageLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        ageLabel.setBounds(76, 451, 87, 20);
        ageTextField = new JTextField();
        ageTextField.setBounds(211, 453, 100, 20);
        ageTextField.setColumns(10);

        priceLabel = new JLabel("Price:");
        priceLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        priceLabel.setForeground(new Color(255, 255, 255));
        priceLabel.setBounds(76, 502, 87, 20);
        priceTextField = new JTextField();
        priceTextField.setBounds(211, 502, 100, 20);
        priceTextField.setColumns(10);

        imageLabel = new JLabel("Image (png):");
        imageLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        imageLabel.setForeground(new Color(255, 255, 255));
        imageLabel.setBounds(76, 545, 100, 20);

        lengthLabel = new JLabel("Length:");
        lengthLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        lengthLabel.setForeground(new Color(255, 255, 255));
        lengthLabel.setBounds(608, 351, 87, 20);
        lengthTextField = new JTextField();
        lengthTextField.setBounds(744, 353, 100, 20);
        lengthTextField.setColumns(10);

        widthLabel = new JLabel("Width:");
        widthLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        widthLabel.setForeground(new Color(255, 255, 255));
        widthLabel.setBounds(608, 404, 87, 20);
        widthTextField = new JTextField();
        widthTextField.setBounds(744, 406, 100, 20);
        widthTextField.setColumns(10);

        heightLabel = new JLabel("Height:");
        heightLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        heightLabel.setForeground(new Color(255, 255, 255));
        heightLabel.setBounds(608, 451, 87, 20);
        heightTextField = new JTextField();
        heightTextField.setBounds(744, 453, 100, 20);
        heightTextField.setColumns(10);

        technologyBrandLabel = new JLabel("Brand:");
        technologyBrandLabel.setFont(
                new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        technologyBrandLabel.setForeground(new Color(255, 255, 255));
        technologyBrandLabel.setBounds(608, 351, 87, 20);
        technologyBrandComboBox =
            new JComboBox<>(new String[] {"Apple", "Samsung", "Dell", "HP",
                "Xiaomi", "Huawei", "Lenovo", "Asus"});
        technologyBrandComboBox.setSize(100, 20);
        technologyBrandComboBox.setLocation(744, 353);
        technologyBrandComboBox.setSelectedIndex(0);

        capabilitiesLabel = new JLabel("Capabilities:");
        capabilitiesLabel.setFont(
                new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        capabilitiesLabel.setForeground(new Color(255, 255, 255));
        capabilitiesLabel.setBounds(608, 404, 87, 20);
        capabilitiesTextField = new JTextField();
        capabilitiesTextField.setBounds(744, 406, 100, 20);
        capabilitiesTextField.setColumns(10);

        technologyColourLabel = new JLabel("Colour:");
        technologyColourLabel.setFont(
                new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        technologyColourLabel.setForeground(new Color(255, 255, 255));
        technologyColourLabel.setBounds(608, 451, 87, 20);
        technologyColourTextField = new JTextField();
        technologyColourTextField.setBounds(744, 453, 100, 20);
        technologyColourTextField.setColumns(10);

        schoolItemBrandLabel = new JLabel("Brand:");
        schoolItemBrandLabel.setFont(
                new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        schoolItemBrandLabel.setForeground(new Color(255, 255, 255));
        schoolItemBrandLabel.setBounds(608, 351, 87, 20);
        schoolItemBrandTextField = new JTextField();
        schoolItemBrandTextField.setBounds(744, 353, 100, 20);
        schoolItemBrandTextField.setColumns(10);

        schoolItemColourLabel = new JLabel("Colour:");
        schoolItemColourLabel.setFont(
                new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        schoolItemColourLabel.setForeground(new Color(255, 255, 255));
        schoolItemColourLabel.setBounds(608, 404, 87, 20);
        schoolItemColourTextField = new JTextField();
        schoolItemColourTextField.setBounds(744, 406, 100, 20);
        schoolItemColourTextField.setColumns(10);

        clothingBrandLabel = new JLabel("Brand:");
        clothingBrandLabel.setFont(
                new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        clothingBrandLabel.setForeground(new Color(255, 255, 255));
        clothingBrandLabel.setBounds(608, 351, 87, 20);
        clothingBrandTextField = new JTextField();
        clothingBrandTextField.setBounds(744, 353, 100, 20);
        clothingBrandTextField.setColumns(10);

        clothingColourLabel = new JLabel("Colour:");
        clothingColourLabel.setFont(
                new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        clothingColourLabel.setForeground(new Color(255, 255, 255));
        clothingColourLabel.setBounds(608, 404, 87, 20);
        clothingColourComboBox = new JComboBox<>(new String[] {
            "Red", "Black", "Blue", "Green", "Grey", "White", "Purple", "Orange",
                "Yellow", "Brown", "Beige", "Pink", "Multicolour"});
        clothingColourComboBox.setBounds(744, 406, 100, 20);
        clothingColourComboBox.setSelectedIndex(0);

        sizeLabel = new JLabel("Size:");
        sizeLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        sizeLabel.setForeground(new Color(255, 255, 255));
        sizeLabel.setBounds(608, 451, 87, 20);
        sizeComboBox =
            new JComboBox<>(new String[] {"XXS", "XS", "S", "M", "L", "XL", "XXL"});
        sizeComboBox.setBounds(744, 453, 100, 20);
        sizeComboBox.setSelectedIndex(0);

        materialLabel = new JLabel("Material:");
        materialLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
        materialLabel.setForeground(new Color(255, 255, 255));
        materialLabel.setBounds(608, 502, 87, 20);
        materialTextField = new JTextField();
        materialTextField.setBounds(744, 504, 100, 20);
        materialTextField.setColumns(10);

        // Initially disable additional fields
        lengthLabel.setVisible(false);
        lengthTextField.setVisible(false);

        widthLabel.setVisible(false);
        widthTextField.setVisible(false);

        heightLabel.setVisible(false);
        heightTextField.setVisible(false);

        technologyBrandLabel.setVisible(false);
        technologyBrandComboBox.setVisible(false);

        capabilitiesLabel.setVisible(false);
        capabilitiesTextField.setVisible(false);

        technologyColourLabel.setVisible(false);
        technologyColourTextField.setVisible(false);

        schoolItemColourLabel.setVisible(false);
        schoolItemColourTextField.setVisible(false);

        schoolItemBrandLabel.setVisible(false);
        schoolItemBrandTextField.setVisible(false);

        clothingBrandLabel.setVisible(false);
        clothingBrandTextField.setVisible(false);

        clothingColourLabel.setVisible(false);
        clothingColourComboBox.setVisible(false);

        sizeLabel.setVisible(false);
        sizeComboBox.setVisible(false);

        materialLabel.setVisible(false);
        materialTextField.setVisible(false);
    }

    private void addComponents() {
        this.add(postButton);
        this.add(backButton);
        this.add(categoryLabel);
        this.add(categoryComboBox);
        this.add(typeLabel);
        this.add(typeComboBox);
        this.add(nameLabel);
        this.add(nameTextField);
        this.add(descriptionLabel);
        this.add(descriptionTextField);
        this.add(pickupAddressLabel);
        this.add(pickupAddressTextField);
        this.add(conditionScoreLabel);
        this.add(conditionScoreComboBox);
        this.add(ageLabel);
        this.add(ageTextField);
        this.add(priceLabel);
        this.add(priceTextField);
        this.add(lengthLabel);
        this.add(lengthTextField);
        this.add(widthLabel);
        this.add(widthTextField);
        this.add(heightLabel);
        this.add(heightTextField);
        this.add(imageLabel);
        this.add(technologyBrandLabel);
        this.add(technologyBrandComboBox);
        this.add(capabilitiesLabel);
        this.add(capabilitiesTextField);
        this.add(technologyColourLabel);
        this.add(technologyColourTextField);
        this.add(schoolItemBrandLabel);
        this.add(schoolItemBrandTextField);
        this.add(schoolItemColourLabel);
        this.add(schoolItemColourTextField);
        this.add(clothingBrandLabel);
        this.add(clothingBrandTextField);
        this.add(clothingColourLabel);
        this.add(clothingColourComboBox);
        this.add(sizeLabel);
        this.add(sizeComboBox);
        this.add(materialLabel);
        this.add(materialTextField);

        lblTitle = new JLabel("Post Item");
        lblTitle.setForeground(new Color(255, 255, 255));
        lblTitle.setFont(new Font("Modern No. 20", Font.BOLD, 24));
        lblTitle.setBounds(439, 66, 122, 28);
        add(lblTitle);

        imageTextField = new JTextField();
        imageTextField.setBounds(211, 545, 100, 20);
        add(imageTextField);
        imageTextField.setColumns(10);

        btnBrowse = new JButton("Browse");
        btnBrowse.setBounds(323, 545, 100, 20);
        add(btnBrowse);
    }

    private void addListeners() {
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTypeComboBox();
                updateAdditionalFields();
            }
        });

        btnBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(PostView.this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    PostView.this.imageTextField.setText(filePath);
                    PostView.this.postViewModel.getState().setImagePath(filePath);
                }
            }
        });
    }

    private void updateTypeComboBox() {
        String selectedCategory = (String)categoryComboBox.getSelectedItem();
        String[] types;

        // Customize types based on the selected category
        switch (selectedCategory) {
            case "Technology":
                types = new String[] {"Laptop",  "Phone",  "Tablet",     "Earphones",
                    "Charger", "Stylus", "Television", "Speaker"};
                break;
            case "Furniture":
                types = new String[] {"Chair",    "Table",    "Couch",      "Bedframe",
                    "Mattress", "Desk",     "Nightstand", "Lights",
                    "Shelf",    "Cupboard", "Decor"};
                break;
            case "Clothing":
                types = new String[] {"Shirt",  "Pants",  "Dress",       "Jeans", "Tops",
                    "Formal", "Jacket", "Winter Gear", "Hoodie"};
                break;
            case "SchoolItem":
                types = new String[] {"Textbook", "Notebook", "Stationery",
                    "Pens",     "Bag",      "Calculator"};
                break;
            default:
                types = new String[0]; // Default to an empty array
                break;
        }
        typeComboBox.setModel(new DefaultComboBoxModel<>(types));
        typeComboBox.setSelectedIndex(0);
    }

    private void updateAdditionalFields() {
        String selectedCategory = (String)categoryComboBox.getSelectedItem();

        // Enable or disable additional fields based on the selected category
        lengthLabel.setVisible("Furniture".equals(selectedCategory));
        widthLabel.setVisible("Furniture".equals(selectedCategory));
        heightLabel.setVisible("Furniture".equals(selectedCategory));
        lengthTextField.setVisible("Furniture".equals(selectedCategory));
        widthTextField.setVisible("Furniture".equals(selectedCategory));
        heightTextField.setVisible("Furniture".equals(selectedCategory));
        technologyBrandLabel.setVisible("Technology".equals(selectedCategory));
        technologyBrandComboBox.setVisible("Technology".equals(selectedCategory));
        capabilitiesLabel.setVisible("Technology".equals(selectedCategory));
        capabilitiesTextField.setVisible("Technology".equals(selectedCategory));
        technologyColourLabel.setVisible("Technology".equals(selectedCategory));
        technologyColourTextField.setVisible("Technology".equals(selectedCategory));
        schoolItemBrandLabel.setVisible("SchoolItem".equals(selectedCategory));
        schoolItemBrandTextField.setVisible("SchoolItem".equals(selectedCategory));
        schoolItemColourLabel.setVisible("SchoolItem".equals(selectedCategory));
        schoolItemColourTextField.setVisible("SchoolItem".equals(selectedCategory));
        clothingBrandLabel.setVisible("Clothing".equals(selectedCategory));
        clothingBrandTextField.setVisible("Clothing".equals(selectedCategory));
        clothingColourLabel.setVisible("Clothing".equals(selectedCategory));
        clothingColourComboBox.setVisible("Clothing".equals(selectedCategory));
        sizeLabel.setVisible("Clothing".equals(selectedCategory));
        sizeComboBox.setVisible("Clothing".equals(selectedCategory));
        materialLabel.setVisible("Clothing".equals(selectedCategory));
        materialTextField.setVisible("Clothing".equals(selectedCategory));
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
