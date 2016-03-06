package ir.rastanco.mobilemarket.presenter.FirstTabPresenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.rastanco.mobilemarket.R;
import ir.rastanco.mobilemarket.presenter.CheckConnectionFragment;
import ir.rastanco.mobilemarket.presenter.LoadingFragment;
import ir.rastanco.mobilemarket.presenter.Observer.ObserverChangeFragment;
import ir.rastanco.mobilemarket.presenter.Observer.ObserverChangeFragmentListener;
import ir.rastanco.mobilemarket.presenter.Observer.ObserverConnectionInternetOK;
import ir.rastanco.mobilemarket.presenter.Observer.ObserverConnectionInternetOKListener;
import ir.rastanco.mobilemarket.presenter.shopPresenter.ShopFragment;
import ir.rastanco.mobilemarket.utility.Configuration;

/**
 * Created by shaisteS on 1394/12/10.
 * This class managed first Tab from Main Menu for displaying fragments(Loading Fragment or Information Product Fragment)
 * First tab from Main Menu = مبلمان منزل
 */
public class FirstTabFragmentManager extends Fragment {

    private String pageName;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View firstProductView = inflater.inflate(R.layout.fragment_first_tab_manager, container, false);
        pageName=getArguments().getString("name");

        if (Configuration.productTableEmptyStatus==true && !Configuration.connectionStatus) {

            CheckConnectionFragment check=new CheckConnectionFragment();
            FragmentTransaction setCheck=getFragmentManager().beginTransaction();
            setCheck.replace(R.id.firstTabManager,check);
            setCheck.commit();

        }
        if (Configuration.productTableEmptyStatus==true && Configuration.connectionStatus){

            LoadingFragment loading = new LoadingFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.firstTabManager, loading);
            transaction.commit();

        }

        else if (Configuration.productTableEmptyStatus==false)
        {
            Bundle args = new Bundle();
            args.putString("name",pageName);
            ShopFragment shop=new ShopFragment();
            shop.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.firstTabManager, shop);
            transaction.commit();
        }

        ObserverChangeFragment.ObserverChangeFragmentListener(new ObserverChangeFragmentListener() {
            @Override
            public void changeFragment() {

                if (Configuration.MainPager.getCurrentItem()==3){
                    Bundle args = new Bundle();
                    args.putString("name", pageName);
                    ShopFragment shop = new ShopFragment();
                    shop.setArguments(args);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.firstTabManager, shop);
                    transaction.commit();
                }
            }
        });

        ObserverConnectionInternetOK.ObserverConnectionInternetOKListener(new ObserverConnectionInternetOKListener() {
            @Override
            public void connectionOK() {
                if (Configuration.MainPager.getCurrentItem() == 3) {
                    LoadingFragment loading1 = new LoadingFragment();
                    FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                    transaction1.replace(R.id.firstTabManager, loading1);
                    transaction1.commit();
                }

            }
        });

        return firstProductView;
    }
}
