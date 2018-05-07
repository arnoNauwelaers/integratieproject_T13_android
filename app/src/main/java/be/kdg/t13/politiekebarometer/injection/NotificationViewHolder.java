package be.kdg.t13.politiekebarometer.injection;

/**
 * Created by bague on 28/04/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import be.kdg.t13.politiekebarometer.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.notification_title) public TextView title;
    @BindView(R.id.notification_msg) public TextView msg;
    @BindView(R.id.view_foreground) RelativeLayout viewForeground;
    @BindView(R.id.view_background) RelativeLayout viewBackground;

    public NotificationViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
