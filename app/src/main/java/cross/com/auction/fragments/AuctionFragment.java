package cross.com.auction.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cross.com.auction.AccountActivity;
import cross.com.auction.AddItemActivity;
import cross.com.auction.AuctionAdapter;
import cross.com.auction.ItemDetailsActivity;
import cross.com.auction.LoginActivity;
import cross.com.auction.R;
import cross.com.auction.datamodel.ItemInfo;
import cross.com.auction.datamodel.ItemManager;
import cross.com.auction.interfaces.ItemClickListener;
import cross.com.auction.utils.AppConstants;
import cross.com.auction.utils.AppPreference;

public class AuctionFragment extends Fragment implements ItemClickListener {

    private RecyclerView mRecyclerView;
    private List<ItemInfo> mItemList;
    private AuctionAdapter mAdapter;

    public AuctionFragment() {
    }

    @Override
    public void onItemClick(int position) {
        startItemDetailsActivity(position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_auction, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mItemList = ItemManager.getInstance().getItemInfoList();
        mAdapter = new AuctionAdapter(getActivity().getApplicationContext(), mItemList, this);
        mRecyclerView.setAdapter(mAdapter);

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((LoginActivity) getActivity())
                .setActionBarTitle(getString(R.string.app_name));
        mAdapter.notifyDataSetChanged();

        AppPreference preference = new AppPreference(getActivity().getApplicationContext());
        if (!preference.getBoolPreference(AppConstants.USER_LOGIN_PREF)){
            moveToLoginPage();
        }
    }


    private void moveToLoginPage() {
        LoginFragment fragment = new LoginFragment();
        getFragmentManager().beginTransaction().replace(R.id.frag_container, fragment,
                "user_login").addToBackStack("user_login").commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_auction, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            startNewItemActivity();
        } else if (id == R.id.action_account) {
            startAccountActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startNewItemActivity() {
        Intent intent = new Intent(getActivity(), AddItemActivity.class);
        startActivityForResult(intent, AppConstants.ACTIVITY_NEW_ITEM_START);
    }

    private void startAccountActivity() {
        Intent intent = new Intent(getActivity(), AccountActivity.class);
        startActivityForResult(intent, AppConstants.ACTIVITY_NEW_ITEM_START);
    }

    private void startItemDetailsActivity(int position) {
        Intent intent = new Intent(getActivity(), ItemDetailsActivity.class);
        intent.putExtra(AppConstants.ITEM_POSITION, position);
        startActivityForResult(intent, AppConstants.ACTIVITY_LIST_ITEM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //refresh list on activity result
        if (requestCode == AppConstants.ACTIVITY_NEW_ITEM_START ||
                requestCode == AppConstants.ACTIVITY_LIST_ITEM) {

            mItemList = ItemManager.getInstance().getItemInfoList();
            mAdapter = new AuctionAdapter(getActivity().getApplicationContext(), mItemList, this);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
