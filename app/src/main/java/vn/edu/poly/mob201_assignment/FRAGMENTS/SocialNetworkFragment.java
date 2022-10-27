package vn.edu.poly.mob201_assignment.FRAGMENTS;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import vn.edu.poly.mob201_assignment.R;

public class SocialNetworkFragment extends Fragment {
    EditText edContent,edContentImg;
    Button btnShare;
    TextView tvTime,toolBarTitle;
    ImageView imgSetting, imgChooseColor, imgAddImage,imgShare,icBack;
    Bitmap bitmap;
    CardView layoutBottom;
    Toolbar toolbar;
    CoordinatorLayout layoutContainer;
    ConstraintLayout layoutShowImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_social_network,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findByIdView(view);
        view.findViewById(R.id.ic_back).setOnClickListener(ic ->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new HomeFragment()).commit();
        });

        imgAddImage.setVisibility(View.GONE);
        imgChooseColor.setVisibility(View.GONE);
        imgSetting.setVisibility(View.VISIBLE);
        edContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edContent.length() == 0){
                    btnShare.setBackground(getContext().getDrawable(R.drawable.custom_button_social));
                }else {
                    btnShare.setBackground(getContext().getDrawable(R.drawable.custom_button_social_2));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edContentImg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edContentImg.length() == 0){
                    btnShare.setBackground(getContext().getDrawable(R.drawable.custom_button_social));
                }else {
                    btnShare.setBackground(getContext().getDrawable(R.drawable.custom_button_social_2));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        imgAddImage.setOnClickListener((image)->{
          dialogUploadImage(getContext(),view);
        });


        view.findViewById(R.id.btnShare).setOnClickListener((button) ->{
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, edContent.getText().toString()+"" +
                    "\n"+edContentImg.getText().toString());
            intent.putExtra(Intent.EXTRA_SUBJECT, "Nội dung chia sẻ.");

            if(bitmap != null){
                Uri uri = getImageToShare(bitmap);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("image/png");
            }else{
                intent.setType("text/plain");
            }

            startActivity(Intent.createChooser(intent, "Chia sẻ nội dung thông qua."));
        });
        imgChooseColor.setOnClickListener((image) ->{
            dialogChangeColor(getContext());
        });


        aniViewSetting(getContext());
        setPresentTime(tvTime);
    }

    private void findByIdView(View view){
        edContent = view.findViewById(R.id.edContentShare);
        edContentImg = view.findViewById(R.id.edContentImg);
        tvTime = view.findViewById(R.id.tv_time_social);
        btnShare = view.findViewById(R.id.btnShare);
        imgSetting =view.findViewById(R.id.icSetting);
        imgAddImage =view.findViewById(R.id.icAddImg);
        imgShare = view.findViewById(R.id.imgShare);
        imgChooseColor =view.findViewById(R.id.icChooserColor);
        layoutBottom = view.findViewById(R.id.layout_bottom_social);
        toolbar = view.findViewById(R.id.tool_bar);
        layoutContainer = view.findViewById(R.id.layout_container_social);
        toolBarTitle = view.findViewById(R.id.toolbar_title);
        icBack = view.findViewById(R.id.ic_back);
        layoutShowImg = view.findViewById(R.id.layout_show_img);
    }

    private void dialogUploadImage(Context context, View view){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_layout_upload_image);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        findByIdView(view);
        dialog.findViewById(R.id.imgCamera).setOnClickListener(ic ->{
            if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},999);
            }else {
                try {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    getActivity().startActivityForResult(cameraIntent, 1111);
                    dialog.cancel();
                }catch (ActivityNotFoundException e){
                    Toast.makeText(context, "Không load được ảnh!", Toast.LENGTH_SHORT).show();
                }
            }

        });


        dialog.findViewById(R.id.imgGallery).setOnClickListener(ic ->{
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);

            chooseImage.launch(i);
            dialog.cancel();
        });

        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1111){
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            imgShare.setImageBitmap(bmp);
        }
    }

    ActivityResultLauncher<Intent> chooseImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            imgShare.setImageURI(selectedImageUri);
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgShare.getDrawable();
                            bitmap = bitmapDrawable.getBitmap();
                            layoutShowImg.setVisibility(View.VISIBLE);
                            btnShare.setBackground(getContext().getDrawable(R.drawable.custom_button_social_2));
                        }
                    }
                }
            });

    private Uri getImageToShare(Bitmap bitmap) {
        File imageFolder = new File(getActivity().getCacheDir(), "images");
        Uri uri = null;
        try {
            imageFolder.mkdirs();
            File file = new File(imageFolder, "sharedImage.png");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(getContext(), "vn.edu.poly.mob201_assignment.fileProvider", file);
        } catch (Exception e) {
            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }


    private void aniViewSetting(Context context){

        imgSetting.setOnClickListener((img)->{
            Animation aniShow = AnimationUtils.loadAnimation(context,R.anim.ani_moving_show);
            imgChooseColor.startAnimation(aniShow);
            imgAddImage.startAnimation(aniShow);
            imgChooseColor.setVisibility(View.VISIBLE);
            imgAddImage.setVisibility(View.VISIBLE);
            tvTime.setGravity(Gravity.LEFT);
        });


        imgSetting.setOnLongClickListener((img)->{
            Animation aniHide = AnimationUtils.loadAnimation(context,R.anim.ani_moving_hide);
            imgChooseColor.startAnimation(aniHide);
            imgAddImage.startAnimation(aniHide);
            imgChooseColor.setVisibility(View.GONE);
            imgAddImage.setVisibility(View.GONE);
            tvTime.setGravity(Gravity.CENTER);
            return true;
        });


    }


    private void setPresentTime(TextView textView){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String time = dateFormat.format(Calendar.getInstance().getTime());
        textView.setText("Đã chỉnh sửa lúc: "+time);
    }



    ImageView color1,color2,color3, color4,color5,cavBackgroundHide,
            border1,border2,border3,border4,border5,border6,border7;
    CardView cavBackground1,cavBackground2,cavBackground3,cavBackground4,cavBackground5;

    private void dialogChangeColor(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_chooser_color);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        findByIdDialog(dialog);
        LinearLayout dialogLayout = dialog.findViewById(R.id.dialog_chooser_color);
        animationDialog(dialogLayout);
        dialog.findViewById(R.id.icCloseDialog).setOnClickListener(ic ->{
            dialog.cancel();
        });


        color1.setOnClickListener(ic ->{
            changeColorEditTextDefault();
        });
        color2.setOnClickListener(ic ->{
            changeColorAndBorderEditText(getContext().getDrawable(R.drawable.cusotm_bg_edtext_yellow),500);
        });
        color3.setOnClickListener(ic ->{
            changeColorAndBorderEditText(getContext().getDrawable(R.drawable.cusotm_bg_edtext_blue),500);
        });
        color4.setOnClickListener(ic ->{
            changeColorAndBorderEditText(getContext().getDrawable(R.drawable.cusotm_bg_edtext_green),500);
        });
        color5.setOnClickListener(ic ->{
            changeColorAndBorderEditText(getContext().getDrawable(R.drawable.cusotm_bg_edtext_brow),500);
        });

        border1.setOnClickListener(ic ->{
            changeColorAndBorderEditText(getContext().getDrawable(R.drawable.bg_border_1),500);
        });
        border2.setOnClickListener(ic ->{
            changeColorAndBorderEditText(getContext().getDrawable(R.drawable.bg_border_2),500);
        });
        border3.setOnClickListener(ic ->{
            changeColorAndBorderEditText(getContext().getDrawable(R.drawable.bg_border_3),500);
        });
        border4.setOnClickListener(ic ->{
            changeColorAndBorderEditText(getContext().getDrawable(R.drawable.bg_border_4),500);
        });
        border5.setOnClickListener(ic ->{
            changeColorAndBorderEditText(getContext().getDrawable(R.drawable.bg_border_5),500);
        });
        border6.setOnClickListener(ic ->{
            changeColorAndBorderEditText(getContext().getDrawable(R.drawable.bg_border_6),500);
        });
        border7.setOnClickListener(ic ->{
            changeColorAndBorderEditText(getContext().getDrawable(R.drawable.bg_border_7),500);
        });





        cavBackgroundHide.setOnClickListener(cav ->{
            changeBackgroundLayoutDefault();
        });

        cavBackground1.setOnClickListener(cav ->{
            changeBackgroundLayout(getContext().getDrawable(R.drawable.background_1));
        });
        cavBackground2.setOnClickListener(cav ->{
            changeBackgroundLayout(getContext().getDrawable(R.drawable.background_2));
        });
        cavBackground3.setOnClickListener(cav ->{
            changeBackgroundLayout(getContext().getDrawable(R.drawable.background_3));
        });
        cavBackground4.setOnClickListener(cav ->{
            changeBackgroundLayout(getContext().getDrawable(R.drawable.background_4));
        });
        cavBackground5.setOnClickListener(cav ->{
            changeBackgroundLayout(getContext().getDrawable(R.drawable.background_5));

        });

        dialog.show();
    }


    private void findByIdDialog(Dialog dialog){
        color1 = dialog.findViewById(R.id.color1);
        color2 = dialog.findViewById(R.id.color2);
        color3 = dialog.findViewById(R.id.color3);
        color4 = dialog.findViewById(R.id.color4);
        color5 = dialog.findViewById(R.id.color5);
        border1 = dialog.findViewById(R.id.border1);
        border2 = dialog.findViewById(R.id.border2);
        border3 = dialog.findViewById(R.id.border3);
        border4 = dialog.findViewById(R.id.border4);
        border5 = dialog.findViewById(R.id.border5);
        border6 = dialog.findViewById(R.id.border6);
        border7 = dialog.findViewById(R.id.border7);
        cavBackgroundHide = dialog.findViewById(R.id.cav_background_hide);
        cavBackground1 = dialog.findViewById(R.id.cav_background_1);
        cavBackground2 = dialog.findViewById(R.id.cav_background_2);
        cavBackground3 = dialog.findViewById(R.id.cav_background_3);
        cavBackground4 = dialog.findViewById(R.id.cav_background_4);
        cavBackground5 = dialog.findViewById(R.id.cav_background_5);
    }




    private void animationDialog(LinearLayout dialog){
      Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.ani_show_dialog);
      dialog.startAnimation(animation);
      animationItemDialog();
    }

    private void animationItemDialog(){
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.ani_items_diaog);
        color1.startAnimation(animation);
        color2.startAnimation(animation);
        color3.startAnimation(animation);
        color4.startAnimation(animation);
        color5.startAnimation(animation);
        cavBackgroundHide.startAnimation(animation);
        cavBackground1.startAnimation(animation);
        cavBackground2.startAnimation(animation);
        cavBackground3.startAnimation(animation);
        cavBackground4.startAnimation(animation);
        cavBackground5.startAnimation(animation);


    }



    private void changeColorEditTextDefault() {
        edContent.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
        edContent.setBackground(getContext().getDrawable(R.drawable.cusotm_bg_edtext_white));
        edContent.setGravity(Gravity.LEFT);
        edContent.setTextColor(getContext().getResources().getColor(R.color.black));
        edContent.setHintTextColor(getContext().getResources().getColor(R.color.black));
        edContentImg.setTextColor(getContext().getResources().getColor(R.color.black));
        edContentImg.setHintTextColor(getContext().getResources().getColor(R.color.black));
    }

    private void changeColorAndBorderEditText(Drawable idBg, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        layoutParams.setMargins(0,20,0,0);
        edContent.setLayoutParams(layoutParams);
        edContent.setBackground(idBg);
        edContent.setGravity(Gravity.CENTER);
        edContent.setTextColor(getContext().getResources().getColor(R.color.black));
        edContent.setHintTextColor(getContext().getResources().getColor(R.color.black));
    }




    private void changeBackgroundLayoutDefault(){
        layoutContainer.setBackground(getContext().getDrawable(R.color.white));
        toolbar.setBackground(getContext().getDrawable(R.color.white));
        edContent.setGravity(Gravity.LEFT);
        changeTextColorNotesBlack();
    }

    private void changeBackgroundLayout(Drawable idBg){
        layoutContainer.setBackground(idBg);
        setBackgroundTransparent();
    }

    private void setBackgroundTransparent(){
        edContent.setBackground(getContext().getDrawable(android.R.color.transparent));
        toolbar.setBackground(getContext().getDrawable(android.R.color.transparent));
        edContent.setGravity(Gravity.LEFT);
        layoutBottom.setBackground(getContext().getDrawable(android.R.color.transparent));
        changeTextColorNotesWhite();
    }


    private void changeTextColorNotesWhite(){
        edContent.setTextColor(Color.parseColor("#FFFFFF"));
        edContent.setHintTextColor(Color.parseColor("#FFFFFF"));
        edContentImg.setTextColor(Color.parseColor("#FFFFFF"));
        edContentImg.setHintTextColor(Color.parseColor("#FFFFFF"));
        tvTime.setTextColor(Color.parseColor("#FFFFFF"));
        toolBarTitle.setTextColor(Color.parseColor("#FFFFFF"));
        icBack.setColorFilter(Color.parseColor("#FFFFFF"));
    }
    private void changeTextColorNotesBlack(){
        edContent.setTextColor(Color.parseColor("#000000"));
        edContentImg.setTextColor(Color.parseColor("#000000"));
        edContentImg.setHintTextColor(Color.parseColor("#000000"));
        tvTime.setTextColor(Color.parseColor("#000000"));
        edContent.setHintTextColor(Color.parseColor("#000000"));
        toolBarTitle.setTextColor(Color.parseColor("#000000"));
        icBack.setColorFilter(Color.parseColor("#717171"));
    }

}
