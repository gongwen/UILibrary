package com.gw.ui.library.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.gw.ui.library.R;
import com.gw.ui.library.util.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GongWen on 17/5/12.
 * Android Shape 形状
 * http://blog.csdn.net/jjwwmlp456/article/details/46928859
 */
/*
Shape继承体系：
Shape (Android.graphics.drawable.shapes)
----PathShape (android.graphics.drawable.shapes)
----RectShape (android.graphics.drawable.shapes)
--------ArcShape (android.graphics.drawable.shapes)
--------OvalShape (android.graphics.drawable.shapes)
--------RoundRectShape (android.graphics.drawable.shapes)
*/

public class ShapeActivity extends AppCompatActivity {
    @BindView(R.id.rectShapeView)
    View rectShapeView;
    @BindView(R.id.roundShapeView1)
    View roundShapeView1;
    @BindView(R.id.roundShapeView2)
    View roundShapeView2;
    @BindView(R.id.ovalShapeView)
    View ovalShapeView;
    @BindView(R.id.arcShapeView)
    View arcShapeView;
    @BindView(R.id.arcImageView)
    ImageView arcImageView;
    @BindView(R.id.pathShapeView)
    View pathShapeView;
    @BindView(R.id.gdView)
    View gdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape);
        ButterKnife.bind(this);
        //矩形
        setRectShape(rectShapeView);
        //圆角矩形
        float[] outerRadii = {20, 20, 40, 40, 60, 60, 80, 80};
        RectF inset = new RectF(40, 40, 60, 60);//内矩形距外矩形，左上角x,y距离， 右下角x,y距离
        float[] innerRadii = {20, 20, 20, 20, 20, 20, 20, 20};//内矩形 圆角半径
        setRoundRectShape(roundShapeView1, outerRadii, null, null);//无内矩形
        setRoundRectShape(roundShapeView2, outerRadii, inset, innerRadii);
        //椭圆
        setOvalShape(ovalShapeView);
        //扇形
        setArcShape(arcShapeView);
        //shape+图片
        setArgBitmap(arcImageView);
        //PathShape
        setPathShape(pathShapeView);
        //GradientDrawable 背景
        setGdBg();
    }

    private void setGdBg() {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(30);
        gd.setColor(Color.RED);
        ViewUtil.setBackground(gdView, gd);
    }

    private void setRectShape(View mView) {
        RectShape rectShape = new RectShape();
        ShapeDrawable drawable = new ShapeDrawable(rectShape);
        drawable.getPaint().setColor(Color.RED);
        drawable.getPaint().setAntiAlias(true);
        drawable.getPaint().setStyle(Paint.Style.FILL); //填充
        ViewUtil.setBackground(mView, drawable);
    }

    private void setRoundRectShape(View mView, float[] outerRadii, RectF inset, float[] innerRadii) {
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, inset, innerRadii);
        ShapeDrawable drawable = new ShapeDrawable(roundRectShape);
        drawable.getPaint().setColor(Color.RED);
        drawable.getPaint().setAntiAlias(true);
        drawable.getPaint().setStyle(Paint.Style.STROKE);
        ViewUtil.setBackground(mView, drawable);
    }

    private void setOvalShape(View mView) {
        OvalShape ovalShape = new OvalShape();
        ShapeDrawable drawable = new ShapeDrawable(ovalShape);
        drawable.getPaint().setColor(Color.RED);
        drawable.getPaint().setAntiAlias(true);
        drawable.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        ViewUtil.setBackground(mView, drawable);
    }

    private void setArcShape(View mView) {
        ArcShape arcShape = new ArcShape(45, 270); //顺时针  开始角度45， 扫描的角度270   扇形
        ShapeDrawable drawable = new ShapeDrawable(arcShape);
        drawable.getPaint().setColor(Color.RED);
        drawable.getPaint().setAntiAlias(true);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        ViewUtil.setBackground(mView, drawable);
    }

    private void setArgBitmap(ImageView mView) {
        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.header)).getBitmap();
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.REPEAT);
        Matrix matrix = new Matrix();
        matrix.preScale(600.00f / bitmap.getWidth(), 600.00f / bitmap.getHeight());//view:w=600,h=600
        bitmapShader.setLocalMatrix(matrix);

        ArcShape arcShape = new ArcShape(45, 270); //顺时针  开始角度45， 扫描的角度270   扇形
        ShapeDrawable drawable = new ShapeDrawable(arcShape);
        drawable.getPaint().setAntiAlias(true);
        drawable.getPaint().setShader(bitmapShader);
        ViewUtil.setBackground(mView, drawable);
    }

    private void setPathShape(View mView) {
        Path path = new Path();
        path.moveTo(50, 0);
        path.lineTo(0, 50);
        path.lineTo(50, 100);
        path.lineTo(100, 50);
        path.lineTo(50, 0);
        PathShape pathShape = new PathShape(path, 200, 100);
        ShapeDrawable drawable = new ShapeDrawable(pathShape);
        drawable.getPaint().setColor(Color.RED);
        drawable.getPaint().setAntiAlias(true);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        ViewUtil.setBackground(mView, drawable);
    }
}
