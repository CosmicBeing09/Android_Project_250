package com.example.raihan.hobbies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;
import static com.example.raihan.hobbies.MainActivity.node;

public class sell_post_fragment extends android.support.v4.app.Fragment {

    private EditText postET,price,location;
    private String S_post,S_price,S_location,S_type,S_pet_type;
    private ImageButton sale_Image;
    private Button postSubmit;
    private static final int Gallery_Reques = 1;
    private Uri SelectImgaeUri = null;
    private StorageReference dataStorage;
    private DatabaseReference profileDatabase;
    private DatabaseReference userImageInfo;
    private DatabaseReference globalpost;
    private RadioGroup radioGroup;
    private Spinner spinner;
    String userImage;
    String[] pet_type;
//    String node;
    Integer temp = 0;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sell_post,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Bundle bundle = this.getArguments();
//        if(bundle!=null)
//        {
//            node = bundle.getString("user").trim();
//        }


        pet_type = getResources().getStringArray(R.array.pet_type);

        spinner = (Spinner) view.findViewById(R.id.pet_type);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_sample,R.id.sampleText,pet_type);

        spinner.setAdapter(arrayAdapter);

        sale_Image = (ImageButton) view.findViewById(R.id.Sale_Image);

        profileDatabase = FirebaseDatabase.getInstance().getReference();
        userImageInfo = FirebaseDatabase.getInstance().getReference("Users_info");
        globalpost = FirebaseDatabase.getInstance().getReference("global_sale_post");




        postET = (EditText) view.findViewById(R.id.Sellpos_tEditText);
        price = (EditText) view.findViewById(R.id.price);
        location = (EditText) view.findViewById(R.id.pickUp_location);

        radioGroup = (RadioGroup)view.findViewById(R.id.radio_group);

       radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup radioGroup, int i) {
               if(radioGroup.getCheckedRadioButtonId() == R.id.adpotationRadio)
               {
                   temp = 0;
                   price.setVisibility(View.INVISIBLE);
               }
               else if(radioGroup.getCheckedRadioButtonId() == R.id.sellRadio)
               {
                   temp = 1;
                   price.setVisibility(View.VISIBLE);
               }
           }
       });





        dataStorage = FirebaseStorage.getInstance().getReference();

        postSubmit = (Button) view.findViewById(R.id.submitPost);


        userImageInfo.child(node).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profile_info pi = dataSnapshot.getValue(profile_info.class);
                userImage = pi.getRegister_ImageUri();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sale_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Gallery_Reques);
            }
        });
        postSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }






    public void startPosting()
    {
        //  mProgress.setMessage("Posting ....");
        // mProgress.show();


        S_post = postET.getText().toString().trim();

        if(temp == 1)
        {
            S_price = price.getText().toString().trim();
            S_type = "Sale";
        }
        else if(temp == 0)
        {
            S_price = "Free";
            S_type = "Adoptation";
        }
        S_location = location.getText().toString().trim();
        S_pet_type = spinner.getSelectedItem().toString().trim().toLowerCase();


        if(SelectImgaeUri != null)
        {

            StorageReference filePath = dataStorage.child("Sale Post Image").child(SelectImgaeUri.getLastPathSegment());
            final ProgressDialog Dialog = new ProgressDialog(getActivity());

            filePath.putFile(SelectImgaeUri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    Dialog.setMessage("Uploading...");
                    Dialog.show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    Toast.makeText(getActivity(),downloadUri.toString(),Toast.LENGTH_LONG).show();


                    sell_post_object  spobj = new sell_post_object(downloadUri.toString(),S_post,node,userImage,S_location,S_price,S_type,S_pet_type);
                    profileDatabase.child("Sale_post").child(node).push().setValue(spobj);
                    globalpost.child(S_pet_type).push().setValue(spobj);

                    Dialog.dismiss();

                }
            });
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Gallery_Reques && resultCode == RESULT_OK)
        {
            SelectImgaeUri = data.getData();
            sale_Image.setImageURI(SelectImgaeUri);
        }
    }
    }

    class sell_post_object implements Parcelable{
        private String imgaeUrl,post_text,user,user_imageUri,location,price,post_type,pet_type;
        public sell_post_object(String imageUrl,String post_text,String user,String user_imageUri,String location,String price,String post_type,String pet_type)
        {
            this.imgaeUrl = imageUrl;
            this.post_text = post_text;
            this.user = user;
            this.user_imageUri = user_imageUri;
            this.location = location;
            this.price = price;
            this.post_type = post_type;
            this.pet_type = pet_type;

        }

        protected sell_post_object(Parcel in) {
            imgaeUrl = in.readString();
            post_text = in.readString();
            user = in.readString();
            user_imageUri = in.readString();
            location = in.readString();
            price = in.readString();
            post_type = in.readString();
            pet_type = in.readString();
        }

        public static final Creator<sell_post_object> CREATOR = new Creator<sell_post_object>() {
            @Override
            public sell_post_object createFromParcel(Parcel in) {
                return new sell_post_object(in);
            }

            @Override
            public sell_post_object[] newArray(int size) {
                return new sell_post_object[size];
            }
        };

        public String getPet_type() {
            return pet_type;
        }

        public void setPet_type(String pet_type) {
            this.pet_type = pet_type;
        }

        public sell_post_object(){}

        public String getImgaeUrl() {
            return imgaeUrl;
        }

        public void setImgaeUrl(String imgaeUrl) {
            this.imgaeUrl = imgaeUrl;
        }

        public String getPost_text() {
            return post_text;
        }

        public void setPost_text(String post_text) {
            this.post_text = post_text;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getUser_imageUri() {
            return user_imageUri;
        }

        public void setUser_imageUri(String user_imageUri) {
            this.user_imageUri = user_imageUri;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPost_type() {
            return post_type;
        }

        public void setPost_type(String post_type) {
            this.post_type = post_type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(imgaeUrl);
            parcel.writeString(post_text);
            parcel.writeString(user);
            parcel.writeString(user_imageUri);
            parcel.writeString(location);
            parcel.writeString(price);
            parcel.writeString(post_type);
            parcel.writeString(pet_type);
        }
    }
