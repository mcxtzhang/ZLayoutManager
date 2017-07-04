package com.mcxtzhang.flowlayoutmanager.gallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.mcxtzhang.flowlayoutmanager.R;
import com.mcxtzhang.layoutmanager.gallery.BaseLoopGallery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LoopGalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_gallery);
        BaseLoopGallery<String> alyLoopGallery = (BaseLoopGallery) findViewById(R.id.alyLoopGallery);
        alyLoopGallery.setDatasAndLayoutId(initDatas(), R.layout.uc_item_main_image_header, new BaseLoopGallery.BindDataListener<String>() {
            @Override
            public void onBindData(ViewHolder holder, String data) {
                Picasso.with(holder.itemView.getContext())
                        .load(data)
                        .into((ImageView) holder.getView(R.id.image));
            }
        });
    }


    public List<String> initDatas() {
        List<String> datas = new ArrayList<>();
        datas.add(new String("http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg"));
/*        datas.add(new String("http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg"));
        datas.add(new String("http://news.k618.cn/tech/201604/W020160407281077548026.jpg"));
        datas.add(new String("http://www.kejik.com/image/1460343965520.jpg"));
        datas.add(new String("http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg"));
        datas.add(new String("http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg"));
        datas.add(new String("http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg"));
        datas.add(new String("http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg"));*/
        return datas;
    }
}
