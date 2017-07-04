package com.mcxtzhang.flowlayoutmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mcxtzhang.flowlayoutmanager.avatar.TanTanAvatarActivity;
import com.mcxtzhang.flowlayoutmanager.gallary.GalleryActivity;
import com.mcxtzhang.flowlayoutmanager.gallery.LoopGalleryActivity;
import com.mcxtzhang.flowlayoutmanager.swipecard.SwipeCardActivity;
import com.mcxtzhang.flowlayoutmanager.tantan.TanTanActivity;
import com.mcxtzhang.flowlayoutmanager.zuimei.ScaleCardActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        findViewById(R.id.btnFlow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, MainActivity.class));
            }
        });
        findViewById(R.id.btnSwipeCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, SwipeCardActivity.class));
            }
        });
        findViewById(R.id.btnKing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, TanTanActivity.class));
            }
        });
        findViewById(R.id.btnGallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, GalleryActivity.class));
            }
        });
        findViewById(R.id.btnZuimeiCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, ScaleCardActivity.class));
            }
        });
        findViewById(R.id.btnTantanAvatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, TanTanAvatarActivity.class));
            }
        });
        findViewById(R.id.btnLoopGallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, LoopGalleryActivity.class));
            }
        });
    }
}
