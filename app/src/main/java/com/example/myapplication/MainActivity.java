/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 */
package com.example.myapplication;
import java.net.URI;
import java.text.NumberFormat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import org.w3c.dom.Text;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //String priceMessage = "Price $ " + (quantity  * 5) + "\n" + "Thank your!";
        //displayMessage(priceMessage);

        /**获取whipped_cream_checkbox的值*/
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream =  whippedCreamCheckBox.isChecked();
        //Log.v("MainActivity","The checkbox is " + hasWhippedCream);
        /**获取chocolate_checkbox的值*/
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean haschocolate =  chocolateCheckBox.isChecked();
        /**获取name_edit_view的值，即用户的名字*/
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_view);
        String name = nameEditText.getText().toString();
        /**计算总价*/
        int price = calculatePrice( haschocolate, hasWhippedCream);
        //Log.v("MainActivity","The price is " + price);

        String priceMessage = createOrderSummary(price, hasWhippedCream, haschocolate, name);
       /** displayPrice(quantity * 5);*/

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));//仅仅邮件APP处理该消息
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);

        displayMessage(priceMessage);
    }
    public void increment(View View) {
        if (quantity >= 100)
        {       //通过toast,给出一个错误信息
            Toast.makeText(this, "You cannot have more than 100 coffees.", Toast.LENGTH_LONG
            ).show();
        //退出这个方法
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }
    public void decrement(View View)
    {
        if (quantity < 1) {
            //通过toast,给出一个错误信息
            Toast.makeText(this, "You cannot have less than 1 coffees.", Toast.LENGTH_LONG
            ).show();
            //退出这个方法
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**显式字符串*/
    private void displayMessage(String str)
    {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text);
        orderSummaryTextView.setText(str);
    }

    /** 计算总价
     *
     * @return order price
     */
    private int calculatePrice(boolean addChocolate, boolean addWhippedCream)
    {
        int eachCupOfPrice = 5;
        if (addChocolate)
            eachCupOfPrice += 2;
        if (addWhippedCream)
            eachCupOfPrice += 1;
        return  quantity * eachCupOfPrice;
    }

    /**显式订单信息
     *
     * @param price
     * @param name
     * @param addChocolate
     * @param addWhippedCream
     * @return order measage
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name)
    {
        //String priceMessage = "Name: " + name;
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage +=  "\nQuantity: " + quantity;
        priceMessage +=  "\nTotal: $" + price;
        priceMessage +=  "\nThanks you!";
        return priceMessage;
    }

}