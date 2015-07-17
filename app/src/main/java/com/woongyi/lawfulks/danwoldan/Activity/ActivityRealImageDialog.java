package com.woongyi.lawfulks.danwoldan.Activity;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.woongyi.lawfulks.danwoldan.R;
import com.woongyi.lawfulks.danwoldan.Utils.AssetFileDescript;

import java.io.IOException;

public class ActivityRealImageDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
        layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount= 0.7f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_real_image_dialog);

        int position = getIntent().getIntExtra("position", -1);

        if (position == -1) {
            finish();
        }

        ImageView realImgView = (ImageView)findViewById(R.id.realImgView);

        try {
            String imgName = "real_image_resize/img_monster_real_" + position + ".png";
            AssetFileDescriptor assetFileDescriptor = AssetFileDescript.getAssetFileDescriptor(this, imgName);
            Drawable drawable = Drawable.createFromStream(assetFileDescriptor.createInputStream(), null);
            realImgView.setImageDrawable(drawable);
        } catch (IOException e) {
        }
    }
}
