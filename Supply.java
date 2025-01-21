import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Supply {
    private JFrame frame, addFrame;
    private JPanel panel;
    private JScrollPane localPanel;
    private static JPanel contentPanel;
        private JButton newButton, exitButton, addButton, cancelButton;
        private JLabel title, title2, itemTypeLabel, itemCodeLabel, itemSubcategoryLabel, buyPriceLabel, predictedSellPriceLabel, warehouseHousedLabel, invalidLabel;
        private JTextField itemTypeField, warehouseHousedField, buypriceField, predictedSellPricTextField, itemCodeField;
        private JComboBox itemSubcategoryBox;
        private static ArrayList<Clothing> clothingList;
            private ArrayList<String> itemTypeList = new ArrayList<String>();
            private ArrayList<String> typeList = new ArrayList<String>();
            public ArrayList<Integer> codeList = new ArrayList<Integer>();
            private ArrayList<Double> buyList = new ArrayList<Double>();
            private ArrayList<Double> sellList = new ArrayList<Double>();
            private ArrayList<String> locationList = new ArrayList<String>();
            private ArrayList<ArrayList> arrayParser = new ArrayList<ArrayList>();
            Encryption encrypt;
            public Supply() {
                encrypt = new Encryption();
                ClickListener click = new ClickListener();
        
                frame = new JFrame();
                frame.setBackground(new Color(190, 199, 212));
        
                panel = new JPanel();
                panel.setBounds(0, 0, 700, 450);
                panel.setLayout(null);
                panel.setBackground(new Color(190, 199, 212));
        
                // Create content panel for scrollable content
                contentPanel = new JPanel();
                contentPanel.setLayout(null);
                contentPanel.setBackground(new Color(237, 237, 237));
                contentPanel.setPreferredSize(new Dimension(645, 1000));  // Adjust height as needed
        
                // Setup scroll pane with content panel
                localPanel = new JScrollPane(contentPanel);
                localPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                localPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                localPanel.setWheelScrollingEnabled(true);
                localPanel.setBounds(20, 45, 645, 300);
        
                panel.add(localPanel);
                clothingList = new ArrayList<Clothing>();
        
                createLabels();
                try {
                    validatePrevious();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                        createButtons(click);
                
                        frame.add(panel);
                        frame.setSize(700, 450);
                        frame.setTitle("Inventory Manager");
                        frame.setLayout(null);
                        frame.setVisible(true);
                        frame.setResizable(false);
                        frame.setLocationRelativeTo(null);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    }
                
                    private void createButtons(ClickListener c) {
                newButton = new JButton("New");
                exitButton = new JButton("Exit");
                newButton.setBounds(20, 350, 250, 30);
                exitButton.setBounds(565, 350, 100, 30);
        
                newButton.addActionListener(c);
                exitButton.addActionListener(c);
                panel.add(exitButton);
                panel.add(newButton);
            }
            public static void sellCommand(int c){
                for(int i = 0; i < clothingList.size();i++){
                    if(i == 0){
                        if(clothingList.get(i).getItemCode() == c){
                            clothingList.removeFirst();
                        }
                    }
                    if(clothingList.get(i).getItemCode() == c){
                        clothingList.remove(i - 1);
                        break;
                    }
                }
                reloadClothing();
            }
            @SuppressWarnings("unchecked")
            private void validatePrevious() throws IOException {
                arrayParser.add(itemTypeList);
                arrayParser.add(typeList);
                arrayParser.add(buyList);
                arrayParser.add(sellList);
                arrayParser.add(locationList);
                arrayParser.add(codeList);
                try {
                    encrypt.decryptFile("supply.txt", "decryptedSupply.txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                File file = new File("decryptedSupply.txt");
                    FileReader fr = new FileReader(file);
                    int ch;
                    String setter = "";
                    int i = 0; 
                    while ((ch=fr.read()) != -1) {
                        char d = (char)ch;
                        if(d == ','){
                            arrayParser.get(i - 1).add(setter);
                            setter = "";
                        }else if(d == '|'){
                            setter = "";
                            i++;
                        }else{
                            setter += d;
                        }
                        }
                    //  System.out.println(arrayParser.toString());
                        classMaker();
                        try {
                        encrypt.encryptFile("decryptedSupply.txt", "Supply.txt");
                        encrypt.encryptFile("decryptedSupply.txt", "decryptedSupply.txt");
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                        fr.close();
            }
            private void classMaker() {
            for (int i = 0; i < arrayParser.get(0).size(); i++) {
                try {
                    String subcat = arrayParser.get(1).get(i)+ "";
                    String type = arrayParser.get(0).get(i)+ "";
                    String loc = arrayParser.get(5).get(i)+ "";
                    double sell = Double.parseDouble(arrayParser.get(3).get(i) + "");
                    double buy = Double.parseDouble(arrayParser.get(2).get(i)+ "");
                    int code = Integer.parseInt(arrayParser.get(4).get(i)+ "");
                    clothingList.add(new Clothing(subcat,type,loc,sell,buy,code));
                } catch (Exception e) {
                    System.out.println("Error at index " + i);
                    System.out.println("Location: " + arrayParser.get(4).get(i));
                    System.out.println("Code: " + arrayParser.get(5).get(i));
                    e.printStackTrace();
                    break;
                }
            }
            reloadClothing();
            }
            private static void reloadClothing(){
            int xIndex = 0;
            int yIndex = 0;
            for (int i = 0; i < clothingList.size(); i++) {
                if (i * 215 % 645 == 0 && i != 0) {
                    yIndex++;
                }
                clothingList.get(i).setBounds(xIndex * 215, yIndex * 110, 215, 110);
                contentPanel.add(clothingList.get(i));
            contentPanel.revalidate();
            contentPanel.repaint();
            
            if (xIndex == 2) {
                xIndex = 0;
            } else {
                xIndex++;
            }
        }
        int totalHeight = (yIndex + 1) * 110 + 50; // Height per row (110) plus padding
        contentPanel.setPreferredSize(new Dimension(645, totalHeight));
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void createLabels() {
        title = new JLabel("Inventory Manager");
        title.setBounds(300, 20, 750, 20);
        panel.add(title);
    }

    private class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == newButton) {
                makeNewFrame();
            } else if (e.getSource() == exitButton) {
                System.exit(0);
            }
        }

        private void makeNewFrame() {
            addFrame = new JFrame();
            makeAddLabels();
            addFrame.setSize(530, 400);
            addFrame.setTitle("Add New Inventory");
            addFrame.setLayout(null);
            addFrame.setVisible(true);
            addFrame.setResizable(false);
            addFrame.setLocationRelativeTo(null);
            addFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }

        @SuppressWarnings("rawtypes")
        private void makeAddLabels() {
            title2 = new JLabel("ADD NEW INVENTORY:");
            title2.setBounds(190, 20, 530, 20);

            itemTypeLabel = new JLabel("What is the item type?");
            itemTypeLabel.setBounds(20, 60, 265, 20);
            itemTypeField = new JTextField("");
            itemTypeField.setBounds(155, 60, 70, 20);

            itemCodeLabel = new JLabel("What is the item code?");
            itemCodeLabel.setBounds(20, 100, 265, 20);
            itemCodeField = new JTextField("");
            itemCodeField.setBounds(155, 100, 70, 20);

            String[] types = {"Sari", "T-Shirt", "Jeans", "Leggings", "Underwear", "Shirt"};
            itemSubcategoryLabel = new JLabel("What is the item subcategory?");
            itemSubcategoryLabel.setBounds(20, 140, 265, 20);
            itemSubcategoryBox = new JComboBox(types);
            itemSubcategoryBox.setBounds(200, 140, 70, 20);

            buyPriceLabel = new JLabel("What is the buy price?");
            buyPriceLabel.setBounds(20, 180, 265, 20);
            buypriceField = new JTextField("");
            buypriceField.setBounds(155, 180, 70, 20);

            predictedSellPriceLabel = new JLabel("What is the predicted sell price?");
            predictedSellPriceLabel.setBounds(20, 220, 265, 20);
            predictedSellPricTextField = new JTextField("");
            predictedSellPricTextField.setBounds(205, 220, 70, 20);

            warehouseHousedLabel = new JLabel("In what warehouse is it in?");
            warehouseHousedLabel.setBounds(20, 260, 265, 20);
            warehouseHousedField = new JTextField("");
            warehouseHousedField.setBounds(175, 260, 70, 20);

            invalidLabel = new JLabel("Invalid input. Try Again");
            invalidLabel.setBounds(20, 300, 265, 20);
            invalidLabel.setVisible(false);

            addComponents();
            setupButtons();
        }

        private void addComponents() {
            addFrame.add(invalidLabel);
            addFrame.add(title2);
            addFrame.add(itemTypeLabel);
            addFrame.add(itemCodeLabel);
            addFrame.add(itemSubcategoryLabel);
            addFrame.add(buyPriceLabel);
            addFrame.add(predictedSellPriceLabel);
            addFrame.add(warehouseHousedLabel);
            addFrame.add(itemTypeField);
            addFrame.add(itemCodeField);
            addFrame.add(itemSubcategoryBox);
            addFrame.add(buypriceField);
            addFrame.add(predictedSellPricTextField);
            addFrame.add(warehouseHousedField);
        }

        private void setupButtons() {
            addButton = new JButton("Add");
            addButton.setBounds(350, 60, 100, 70);

            cancelButton = new JButton("Cancel");
            cancelButton.setBounds(350, 180, 100, 70);

            ActionListener buttonListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == addButton) {
                        handleAddButton();
                    }
                    if (e.getSource() == cancelButton) {
                        addFrame.setVisible(false);
                    }
                }
            };

            addButton.addActionListener(buttonListener);
            cancelButton.addActionListener(buttonListener);
            addFrame.add(cancelButton);
            addFrame.add(addButton);
        }

        private void handleAddButton() {
            Clothing f = null;
            try {
                f = new Clothing(
                    itemTypeField.getText(),
                    "" + itemSubcategoryBox.getSelectedItem(),
                    warehouseHousedField.getText(),
                    Double.parseDouble(buypriceField.getText()),
                    Double.parseDouble(predictedSellPricTextField.getText()),
                    Integer.parseInt(itemCodeField.getText())
                );
            } catch (Exception fsg) {
                invalidLabel.setVisible(true);
                return;
            }
            handleWriter(f);
            clothingList.add(f);
            addFrame.setVisible(false);

            int xIndex = 0;
            int yIndex = 0;
            for (int i = 0; i < clothingList.size(); i++) {
                if (i * 215 % 645 == 0 && i != 0) {
                    yIndex++;
                }
                clothingList.get(i).setBounds(xIndex * 215, yIndex * 110, 215, 110);
                contentPanel.add(clothingList.get(i));
                contentPanel.revalidate();
                contentPanel.repaint();
                
                if (xIndex == 2) {
                    xIndex = 0;
                } else {
                    xIndex++;
                }
            }

            // Update content panel size based on content
            int totalHeight = (yIndex + 1) * 110 + 50; // Add some padding
            contentPanel.setPreferredSize(new Dimension(645, Math.max(1000, totalHeight)));

            // try {
            //     // addToFile();
            // } catch (IOException e) {
            //     System.err.println("Fail");
            // }
            // localPanel.revalidate();
        }
        private void handleWriter(Clothing f){
            try {
                
                try {
                    encrypt.decryptFile("supply.txt", "decryptedSupply.txt");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                File file = new File("decryptedSupply.txt");
                FileReader fr = new FileReader(file);
                String wholeFile = "";
                int ch;
                int index = 0;
                while ((ch=fr.read()) != -1) {
                    if((char)ch == '|'){

                        if(index == 1){
                            wholeFile = wholeFile.substring(0, wholeFile.length() - 1);
                            wholeFile = wholeFile + f.getItemType()+ ",\n";
                        }
                        if(index == 2){
                            wholeFile = wholeFile.substring(0, wholeFile.length() - 1);
                            wholeFile = wholeFile + f.getItemSubcategory()+ ",\n";
                        }
                        if(index == 3){
                            wholeFile = wholeFile.substring(0, wholeFile.length() - 1);
                            wholeFile = wholeFile + f.getBuyPrice()+ ",\n";
                        }
                        if(index == 4){
                            wholeFile = wholeFile.substring(0, wholeFile.length() - 1);
                            wholeFile = wholeFile + f.getPredictedSellPrice()+ ",\n";
                        }
                        if(index == 5){
                            wholeFile = wholeFile.substring(0, wholeFile.length() - 1);
                            wholeFile = wholeFile + f.getWarehouseHoused() + ",\n";
                        }
                        if(index == 6){
                            wholeFile = wholeFile.substring(0, wholeFile.length() - 1);
                            wholeFile = wholeFile + f.getItemCode() + ",\n";
                        }
                        index ++;
                    }
                    wholeFile = wholeFile + (char)ch;
                    
                 }
                FileWriter myWriter = new FileWriter("decryptedSupply.txt");
                myWriter.write(wholeFile);
                myWriter.close();
                encrypt.encryptFile("decryptedSupply.txt", "supply.txt");
                encrypt.encryptFile("decryptedSupply.txt", "decryptedSupply.txt");
            } catch (IOException e) {
                System.out.println("An error occurred in writer handling.");
                e.printStackTrace();
            } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
        }

    }
}

