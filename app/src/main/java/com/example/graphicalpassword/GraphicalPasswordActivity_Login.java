package com.example.graphicalpassword;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphicalPasswordActivity_Login extends AppCompatActivity {

    public static final String EXTRA_GRAPHICAL_PASSWORD = "graphical_password";
    private List<ImageView> selectedImageViews = new ArrayList<>();
    private LinearLayout selectedImagesLayout;
    private GridLayout gridLayout;
    private Map<ImageView, ImageView> clonedImageViewsMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphical_password);

        selectedImagesLayout = findViewById(R.id.selected_images_layout);
        gridLayout = findViewById(R.id.gridLayout);

        setupClickListeners();

        Button cancalButton = findViewById(R.id.cancel_button);
        cancalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImageViews.size() == 4) {
                    StringBuilder graphicalPasswordBuilder = new StringBuilder();
                    for (ImageView imageView : selectedImageViews) {
                        graphicalPasswordBuilder.append(imageView.getTag().toString());
                    }
                    String graphicalPassword = graphicalPasswordBuilder.toString();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(EXTRA_GRAPHICAL_PASSWORD, graphicalPassword);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(GraphicalPasswordActivity_Login.this, "Please choose 4 images", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupClickListeners() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            if (child instanceof ImageView) {
                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageView selectedImageView = (ImageView) v;
                        if (selectedImageView.isSelected()) {
                            selectedImageView.setSelected(false);
                            selectedImageViews.remove(selectedImageView);
                            // Remove the image from the selectedImagesLayout
                            ImageView clonedImageView = clonedImageViewsMap.get(selectedImageView);
                            if (clonedImageView != null) {
                                selectedImagesLayout.removeView(clonedImageView);
                                clonedImageViewsMap.remove(selectedImageView);
                            }
                        } else {
                            if (selectedImageViews.size() < 4) {
                                selectedImageView.setSelected(true);
                                selectedImageViews.add(selectedImageView);
                                // Add the image to the selectedImagesLayout
                                ImageView clonedImageView = new ImageView(GraphicalPasswordActivity_Login.this);
                                clonedImageView.setImageDrawable(selectedImageView.getDrawable());
                                clonedImageView.setLayoutParams(new LinearLayout.LayoutParams(150, 150)); // Set the width and height
                                clonedImageView.setScaleType(ImageView.ScaleType.FIT_CENTER); // Set the scale type
                                clonedImageView.setAdjustViewBounds(true); // Adjust the view bounds
                                selectedImagesLayout.addView(clonedImageView);
                                clonedImageViewsMap.put(selectedImageView, clonedImageView);
                            } else {
                                Toast.makeText(GraphicalPasswordActivity_Login.this, "You can only select 4 images", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        }
    }
}



