package com.example.robinxyuan.rxyo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.example.robinxyuan.rxyo.Image.ImageLoader;
import com.example.robinxyuan.rxyo.Image.ListViewAdapter;
import com.example.robinxyuan.rxyo.Image.SelectPhotoActivity;
import com.example.robinxyuan.rxyo.Image.SelectPhotoAdapter;
import com.wingjay.blurimageviewlib.BlurImageView;
import com.wingjay.blurimageviewlib.FastBlurUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageLoader imageLoader;
    int screenWidth = 0;
    ListViewAdapter<SelectPhotoAdapter.SelectPhotoEntity> photoListViewAdapter;

    private long firstTime = 0;
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<ImageView> blurImages;
    private List<View> dots;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{
            R.drawable.ca,
            R.drawable.ck,
            R.drawable.da,
            R.drawable.li,
            R.drawable.ro
    };
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPaper = (ViewPager) findViewById(R.id.vp);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

//        ImageView buttonBackground = (ImageView) findViewById(R.id.btn_background);

        //显示的图片
        images = new ArrayList<ImageView>();
        for(int i = 0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(this);
//            ImageView topImageView = new ImageView(this);
//            imageView.setBackgroundResource(imageIds[i]);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageIds[i]);
//            bitmap = FastBlurUtil.doBlur(bitmap, 20, false);
//
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setColorFilter(Color.argb(50, 50, 50, 50), PorterDuff.Mode.DARKEN);
//            imageView.setBackgroundResource(imageIds[i]);
            imageView.setImageBitmap(bitmap);
//            imageView.setBackgroundColor(Color.argb(30, 30, 30, 30));

//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageIds[i]);
//            Bitmap blurBitmap = FastBlurUtil.doBlur(bitmap, 10, false);

//            buttonBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            buttonBackground.setImageBitmap(blurBitmap);
            images.add(imageView);
//            images.add(buttonBackground);

        }
        //显示的小点
        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));

        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);

        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
                dots.get(position).setBackgroundResource(R.drawable.dot_focused);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);

                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        View bt_add_photo = findViewById(R.id.image_button);
        bt_add_photo.setOnClickListener(this);
//        imageLoader = new ImageLoader(this);
//        screenWidth = SelectPhotoAdapter.getScreenWidth(this);
//        photoListViewAdapter = new ListViewAdapter<SelectPhotoAdapter.SelectPhotoEntity>(this,R.layout.listview_item,null) {
//            @Override
//            public void convert(ViewHolder holder, int position, SelectPhotoAdapter.SelectPhotoEntity entity) {
//                ImageView imageView = (ImageView) holder.getView(R.id.iv_selected_photo);
//                imageLoader.setAsyncBitmapFromSD(entity.url,imageView,screenWidth,true,true,false);//这里因为图片太大，所以不要保存缩略图
//            }
//        };
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_button:
                Intent intent = new Intent(this,SelectPhotoActivity.class);
//                startActivityForResult(intent,10);
                startActivity(intent);
//                overridePendingTransition(R.anim.in_from_left_to_center, R.anim.out_from_center_to_right);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "Press back again quit RXYO", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//            alertDialogBuilder.setTitle("Question");
//            alertDialogBuilder.setMessage("Do you want to quit RXYO?");
//            alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    finish();
////                    Toast.makeText(getApplicationContext(),"YES",Toast.LENGTH_SHORT).show();
//                }
//            });
//            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_SHORT).show();
//                }
//            });
//            alertDialogBuilder.setCancelable(true);
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            alertDialog.show();
//            return false;
//        }else {
//            return super.onKeyDown(keyCode, event);
//        }
//
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
////        Log.i("Alex","mainActivity的onActivityResult req="+requestCode+"    result="+requestCode);
//        if(data == null || resultCode != SelectPhotoActivity.SELECT_PHOTO_OK)
//            return;
//        boolean isFromCamera = data.getBooleanExtra("isFromCamera",false);
//        ArrayList<SelectPhotoAdapter.SelectPhotoEntity> selectedPhotos = data.getParcelableArrayListExtra("selectPhotos");
////        Log.i("Alex","选择的图片是"+selectedPhotos);
//        ListView listView = (ListView) findViewById(R.id.listView);
//        photoListViewAdapter.setmDatas(selectedPhotos);
//        listView.setAdapter(photoListViewAdapter);
//    }

    /**
     * 自定义Adapter
     * @author liuyazhuang
     *
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
//          super.destroyItem(container, position, object);
//          view.removeView(view.getChildAt(position));
//          view.removeViewAt(position);
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));
            return images.get(position);
        }

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.main, menu);
////        return true;
//    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                5,
                5,
                TimeUnit.SECONDS);
    }


    /**
     * 图片轮播任务
     * @author liuyazhuang
     *
     */
    private class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        };
    };
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }
}