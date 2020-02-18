package com.jinasoft.chatting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{


    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdfNOW = new SimpleDateFormat("HH:mm");
    private String NOWDATE = sdfNOW.format(date);


    String ViewTypeNAME;

    public static class MessageViewHolder extends RecyclerView.ViewHolder{


        TextView tvName;
        TextView tvMessage;
        TextView tvDate;
        ImageView imageView;
        CircleImageView Profile;




        public MessageViewHolder(View v){
            super(v);
                tvName = itemView.findViewById(R.id.chat_tvName);
                tvMessage = itemView.findViewById(R.id.chat_tvMessage);
                imageView = itemView.findViewById(R.id.imageview);
                tvDate = itemView.findViewById(R.id.chat_tvDate);
                Profile = itemView.findViewById(R.id.chat_profile);


        }


    }


    private FirebaseRecyclerAdapter<ChatMessage, MessageViewHolder> mFirebaseAdapter;



    private GoogleApiClient mGoogleApiCilent;

    private  RecyclerView ChatRecyclerView;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private String mUserName;
    private String mPhotoUrl;

    private DatabaseReference mFirebaseDatabaseReference;
    private EditText mMessageEditText;
    public static final String MESSAGES_CHILD = "messages";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ChatRecyclerView = findViewById(R.id.chat_recycler_view);

        mGoogleApiCilent = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();



    mFirebaseAuth = FirebaseAuth.getInstance();
    mFirebaseUser = mFirebaseAuth.getCurrentUser();
    if(mFirebaseUser ==null){
        startActivity(new Intent(this, SignActivity.class));
        finish();
        return;
    }else{
        mUserName = mFirebaseUser.getDisplayName();
        if(mFirebaseUser.getPhotoUrl() != null) {
            mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
//            mPhotoUrl ="http://58.230.203.182/GoodShare/uploads/20200208-1652451A.png";
//            mPhotoUrl="";
        }
    }
//    onCreateOptionMenu();
//    onOptionItemSelected();
    mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    mMessageEditText = findViewById(R.id.chat_edit);

    findViewById(R.id.chat_btnsend).setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            ChatMessage chatMessage= new ChatMessage(mMessageEditText.getText().toString(),"email",mUserName,mPhotoUrl,null,NOWDATE);
            mFirebaseDatabaseReference.child(MESSAGES_CHILD)
                    .push().setValue(chatMessage);
            mMessageEditText.setText("");
        }
    });
/**----- 전송**/

/**----- 읽기**/
        Query query = mFirebaseDatabaseReference.child(MESSAGES_CHILD);

        FirebaseRecyclerOptions<ChatMessage> options =
                new FirebaseRecyclerOptions.Builder<ChatMessage>()
                .setQuery(query,ChatMessage.class)
                .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<ChatMessage, MessageViewHolder>(options) {
            @Override
            protected void onBindViewHolder(MessageViewHolder holder, int position, ChatMessage model) {
                holder.tvName.setText(model.getName());
                holder.tvMessage.setText(model.getText());
                holder.tvDate.setText(model.getDate());

                ViewTypeNAME = model.getPhotourl();

                if(model.getName().equals("2번")) {

                }else {
                        if (model.getPhotourl().equals("")) {
                            holder.Profile.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_action_name));


                        } else {
                       /**이메일이 내 이메일 이면 프로필 x**/
                            Glide.with(MainActivity.this)
                                .load(model.getPhotourl())
//                            .override(10)
                                .error(R.drawable.button_back_google)
                                .into(holder.Profile);


                    }
                }

            }
            @Override
            public int getItemViewType(int position) {
                ChatMessage model = getItem(position);
                if (model.getName().equals("2번")) {
                    return 1;
                } else {
                    return 2;
                    }

                }
                    @NonNull
                    @Override
                    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                        View view;
                                switch (viewType) {
                                    case 0:
                                     Log.d("FFFF", "온크리트뷰홀더 :" + viewType);
                                     Log.d("FFFF", "온크리트뷰홀더 : 0인 경우");
                                      view = LayoutInflater.from(viewGroup.getContext())
                                             .inflate(R.layout.item_image, viewGroup, false);
                            return new MessageViewHolder(view);
                                    case 1:
                                        Log.d("FFFF", "온크리트뷰홀더 :" + viewType);
                                        Log.d("FFFF", "온크리트뷰홀더 : 1인 경우");
                                        view = LayoutInflater.from(viewGroup.getContext())
                                                .inflate(R.layout.item_image2, viewGroup, false);
                            return new MessageViewHolder(view);
                    }
                    view = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.item_image, viewGroup, false);
                    return new MessageViewHolder(view);
                }
        };
        ChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ChatRecyclerView.setAdapter(mFirebaseAdapter);






        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver(){
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart,itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                LinearLayoutManager layoutManager = (LinearLayoutManager)
                        ChatRecyclerView.getLayoutManager();
                int lastVisiblePostition = layoutManager.findLastCompletelyVisibleItemPosition();

                if (lastVisiblePostition == -1 || (positionStart >= (friendlyMessageCount -1) && lastVisiblePostition == (positionStart -1))) {
                    ChatRecyclerView.scrollToPosition(positionStart);
                }
            }
        });
        ChatRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener(){
            @Override
            public void onLayoutChange(View v, int left, int top, int right,
                                       int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom){
                if (bottom <oldBottom){
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            ChatRecyclerView.smoothScrollToPosition(
                                    mFirebaseAdapter.getItemCount());
                        }
                    },100);
                }
            }
        });
        onStart();
        onStop();
    }
@Override
public void onStart(){
        super.onStart();
        mFirebaseAdapter.startListening();
}
@Override
public void onStop(){
        super.onStop();
        mFirebaseAdapter.stopListening();
}

    public boolean onCreateOptionMenu (Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    public boolean onOptionItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiCilent);
                mUserName="";
                startActivity(new Intent(this, SignActivity.class));
                finish();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,"Google play Service error",Toast.LENGTH_SHORT).show();

    }


}
