package ir.rastanco.mobilemarket.presenter.ProductInfoPresenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ir.rastanco.mobilemarket.R;
import ir.rastanco.mobilemarket.dataModel.Product;
import ir.rastanco.mobilemarket.dataModel.ProductOption;
import ir.rastanco.mobilemarket.dataModel.serverConnectionModel.ServerConnectionHandler;
import ir.rastanco.mobilemarket.utility.Configuration;

/**
 * Created by ShaisteS on 1394/11/05
 * this activity show product option and product comment and product description
 */
public class ProductOptionActivity extends Activity{
    private boolean onBackBtnPressed = false;
    private ListView lvProductOption;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_option);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Configuration.getConfig().productOptionActivityContext = this;
        ServerConnectionHandler sch = new ServerConnectionHandler(Configuration.getConfig().productOptionActivityContext);
        lvProductOption = (ListView) findViewById(R.id.lv_productOption);

        Intent intent = this.getIntent();
        int productId = intent.getIntExtra("productId", 0);
        Product aProduct = sch.getAProduct(productId);
        TextView nameOfCurrentProduct = (TextView) findViewById(R.id.eachProductName);
        nameOfCurrentProduct.setText(aProduct.getTitle());
        ImageButton btnBack = (ImageButton) findViewById(R.id.back_full_screen);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackBtnPressed = true;
                checkBackButtonState();
            }
        });
        ArrayList<ProductOption> options = sch.getProductOptionFromDataBase(productId);
        ProductInfoItemAdapter adapter = new ProductInfoItemAdapter(Configuration.getConfig().productOptionActivityContext, options);
        lvProductOption.setAdapter(adapter);
        ListView lvComment = (ListView) findViewById(R.id.lv_comments);
        ArrayList<String> commentsAProduct = sch.getContentCommentsAllProduct(productId);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                Configuration.getConfig().productOptionActivityContext,
                android.R.layout.simple_list_item_1,
                commentsAProduct);

        lvComment.setAdapter(arrayAdapter);

    }

    private void checkBackButtonState() {
        if (onBackBtnPressed) {
            super.onBackPressed();
        }
    }
}
