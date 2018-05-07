package be.kdg.t13.politiekebarometer.injection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import be.kdg.t13.politiekebarometer.R;
import be.kdg.t13.politiekebarometer.model.Notification;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bague on 28/04/2018.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
    private Context context;
    private List<Notification> notifications;

    public NotificationAdapter(Context context, List<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }
    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification, parent, false);
        return new NotificationViewHolder(view);
    }
    @Override
    public void onBindViewHolder(NotificationViewHolder holder, final int position) {
        Notification notification = notifications.get(position);
        holder.title.setText("Nieuwe melding");
        holder.msg.setText(notification.getMessage());
        holder.msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Selected index " + position, Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void removeItem(int position) {
        notifications.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Notification notification, int position) {
        notifications.add(position, notification);
        notifyItemInserted(position);
    }

    public static int getResourseId(Context context, String pVariableName, String pResourcename, String pPackageName) throws RuntimeException {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Resource ID.", e);
        }
    }
}
