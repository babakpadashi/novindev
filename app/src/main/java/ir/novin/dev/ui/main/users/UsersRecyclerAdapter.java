package ir.novin.dev.ui.main.users;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

import ir.novin.dev.R;
import ir.novin.dev.modules.User;
import ir.novin.dev.ui.details.DetailsActivity;
import ir.novin.dev.ui.main.MainActivity;


public class UsersRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> users = new ArrayList<>();


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_users_list_item, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((UsersViewHolder) holder).bind(users.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(fragment.getActivity(), DetailsActivity.class);
                intent.putExtra("id",users.get(position).getId());
                fragment.getActivity().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<User> users) {
        this.users.addAll(users);
    }

    RequestManager requestManager;

    public void addRequestManager(RequestManager requestManager) {
        this.requestManager=requestManager;
    }

    UsersFragment fragment;

    public void addContext(UsersFragment fragment) {
        this.fragment=fragment;
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView emailTxt, firstnameTxt, lastnameTxt;
        ImageView image;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            emailTxt=itemView.findViewById(R.id.email_txt);
            firstnameTxt=itemView.findViewById(R.id.firstname_txt);
            lastnameTxt=itemView.findViewById(R.id.lastname_txt);
        }

        public void bind(User users) {

            requestManager.load(users.getAvatar()).into(image);

            emailTxt.setText(users.getEmail());
            firstnameTxt.setText(users.getFirst_name());
            lastnameTxt.setText(users.getLast_name());
        }
    }
}





