package com.example.abclinic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.abclinic.dto.MediaDto;
import com.abclinic.utils.services.MediaService;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.Collections;

public class AvatarDialogFragment extends DialogFragment {
    private String imageUri;
    private MediaDto mediaDto;
    private Activity context;

    public static AvatarDialogFragment getInstance(Activity context, String imageUri) {
        AvatarDialogFragment avatarDialogFragment = new AvatarDialogFragment();
        avatarDialogFragment.setImgView(imageUri);
        avatarDialogFragment.setContext(context);
        return avatarDialogFragment;
    }

    public void setImgView(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public MediaDto getMediaDto() {
        return mediaDto;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.avatar_title)
                .setItems(R.array.avatarArr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                new ImageViewer.Builder<>(context, Collections.singletonList(imageUri))
                                        .show();
                                break;
                            case 1:
                                mediaDto = MediaService.getCameraIntent(context);
                                context.startActivityForResult(mediaDto.getIntent(), 1);
                                break;
                            case 2:
                                mediaDto = new MediaDto();
                                mediaDto.setIntent(MediaService.getGalleryIntent("Chọn ảnh đại diện"));
                                context.startActivityForResult(mediaDto.getIntent(), 2);
                                break;
                        }
                    }
                });
        return builder.create();
    }

}
