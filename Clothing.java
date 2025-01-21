import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.text.DecimalFormat;

public class Clothing extends JPanel{
    public String itemType, itemSubcategory, warehouseHoused;
    private double buyPrice,predictedSellPrice;
    private int itemCode;
    private JLabel itemTypeLabel, itemCodeLabel, itemSubcategoryLabel, buyPriceLabel, predictedSellPriceLabel,warehouseHousedLabel, image;
    private JButton moreInfoButton, sellButton;
    private DecimalFormat df = new DecimalFormat("0000"); 
    private Border b;

    public Clothing(String itemType, String itemSubcategory, String warehouseHoused, double buyPrice, double predictedSellPrice, int itemCode) throws IOException{
        this.itemType = itemType;
        this.itemSubcategory = itemSubcategory;
        this.warehouseHoused = warehouseHoused;
        this.buyPrice = buyPrice;
        this.predictedSellPrice = predictedSellPrice;
        this.itemCode = itemCode;

        initAll();

        this.setLayout(null);
        this.setSize(215,120);
        this.setBackground(new Color(205, 207, 209));
        this.setVisible(true);

    }
    public void initAll() throws IOException{
            itemTypeLabel = new JLabel(itemType + " " + itemSubcategory); 
            itemTypeLabel.setBounds(10,5,265,20);
            this.add(itemTypeLabel);

            itemCodeLabel = new JLabel("ID: " + df.format(itemCode) + "");
            itemCodeLabel.setBounds(10,25,265,20);
            this.add(itemCodeLabel);

            buyPriceLabel = new JLabel("Buy Price: " + buyPrice);
            buyPriceLabel.setBounds(10,45,265,20);
            this.add(buyPriceLabel);

            predictedSellPriceLabel = new JLabel("Sell: " + predictedSellPrice);
            predictedSellPriceLabel.setBounds(10,65,265,20);
            this.add(predictedSellPriceLabel);

            warehouseHousedLabel = new JLabel("Warehouse: " + warehouseHoused);
            warehouseHousedLabel.setBounds(10,85,265,20);
            this.add(warehouseHousedLabel);

            moreInfoButton = new JButton("More");
            moreInfoButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Working");
                }
                
            });
            
            
            moreInfoButton.setBounds(130,30,70,25);
            this.add(moreInfoButton);

            sellButton = new JButton("Sell");
            sellButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    Supply.sellCommand(itemCode);
                    setVisible(false);
                }
                
            });

            sellButton.setBounds(130,60,70,25);
            this.add(sellButton);
            b = BorderFactory.createRaisedBevelBorder();
            this.setBorder(b);


            // if(itemSubcategory.equals("Sari")){
            //     ImageIcon photIcon = new ImageIcon("images\\jeans3.png");
            //     image = new JLabel(photIcon);
            //     image.setBounds(175,5,100,100);
            //     this.add(image);
                
            // }


            
    }
    public Clothing(){
        this.itemType = null;
        this.itemSubcategory = null;
        this.warehouseHoused = null;
        this.buyPrice = 0.0;
        this.predictedSellPrice = 0.0;
        this.itemCode = 0;
        this.setBackground(Color.red);
        this.setVisible(true);
        this.setBounds(200, 200, 50, 50);
        
    }
    public String getItemType() {
        return itemType;
    }
    
    public String getItemSubcategory() {
        return itemSubcategory;
    }
    
    public String getWarehouseHoused() {
        return warehouseHoused;
    }
    
    public double getBuyPrice() {
        return buyPrice;
    }
    
    public double getPredictedSellPrice() {
        return predictedSellPrice;
    }
    
    public int getItemCode() {
        return itemCode;
    }
    
}
