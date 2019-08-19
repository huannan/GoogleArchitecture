package com.nan.architecture.userprofile;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nan.architecture.R;
import com.nan.architecture.db.User;
import com.nan.architecture.mvvm.BaseActivity;

public class UserProfileActivity extends BaseActivity<UserProfileViewModel> implements View.OnClickListener {

    public static final String TAG = "UserProfileActivity";

    private TextView mTvPageState;
    private EditText mEtUserName;
    private Button mBtnSearch;
    private ImageView mIvImage;
    private TextView mTvUsername;
    private TextView mTvCompany;
    private TextView mTvWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initView();
    }

    protected void initView() {
        mTvPageState = findViewById(R.id.page_state);
        mEtUserName = findViewById(R.id.et_username);
        mBtnSearch = findViewById(R.id.btn_search);
        mIvImage = findViewById(R.id.iv_image);
        mTvUsername = findViewById(R.id.tv_username);
        mTvCompany = findViewById(R.id.tv_company);
        mTvWebsite = findViewById(R.id.tv_website);
        mBtnSearch.setOnClickListener(this);

        mTvPageState.setText("初始化，比如设置标题栏等");
        mViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user != null) {
                    updateUI(user);
                }
            }
        });
    }

    @Override
    protected UserProfileViewModel createViewModel() {
        return ViewModelProviders.of(this).get(UserProfileViewModel.class);
    }


    @Override
    protected void showLoading() {
        mTvPageState.setText("正在加载");
    }

    @Override
    protected void showLoadSuccess() {
        mTvPageState.setText("加载成功");
    }

    @Override
    protected void showLoadError() {
        mTvPageState.setText("网络异常");
    }

    @Override
    protected void showNoNetwork() {
        mTvPageState.setText("暂无网络");
    }

    private void updateUI(User user) {
        Glide.with(this).load(user.getAvatar_url()).into(mIvImage);
        mTvUsername.setText(user.getName());
        mTvCompany.setText(user.getCompany());
        mTvWebsite.setText(user.getBlog());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                String userName = mEtUserName.getText().toString().trim();
                mViewModel.searchUser(userName);
                dismissKeyboard(mEtUserName.getWindowToken());
                break;
            default:
                break;
        }
    }

    private void dismissKeyboard(IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(windowToken, 0);

    }
}
