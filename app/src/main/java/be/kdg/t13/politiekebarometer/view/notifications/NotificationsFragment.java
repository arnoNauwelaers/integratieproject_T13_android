package be.kdg.t13.politiekebarometer.view.notifications;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import be.kdg.t13.politiekebarometer.MainActivity;
import be.kdg.t13.politiekebarometer.R;
import be.kdg.t13.politiekebarometer.injection.NotificationAdapter;
import be.kdg.t13.politiekebarometer.injection.NotificationRecyclerTouchHelper;
import be.kdg.t13.politiekebarometer.injection.NotificationViewHolder;
import be.kdg.t13.politiekebarometer.model.Notification;
import be.kdg.t13.politiekebarometer.utils.ApiManager;
import be.kdg.t13.politiekebarometer.utils.UserManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.widget.LinearLayout.HORIZONTAL;

public class NotificationsFragment extends Fragment implements NotificationRecyclerTouchHelper.RecyclerItemTouchHelperListener {
    @BindView(R.id.notifications) RecyclerView notificationsView;
    @BindView(R.id.notification_fragment) RelativeLayout constraintLayout;
    private Unbinder unbinder;
    private List<Notification> notifications;
    private NotificationAdapter notificationAdapter;

    public NotificationsFragment() {

    }
    public static NotificationsFragment newInstance() {
        Bundle args = new Bundle();
        NotificationsFragment fragment = new NotificationsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(!UserManager.isLoggedIn()) {
            UserManager.redirectToLogin((MainActivity)getActivity());
        }
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        unbinder = ButterKnife.bind(this, view);
        notifications = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(this.getContext(), notifications);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        notificationsView.setLayoutManager(mLayoutManager);
        notificationsView.setItemAnimator(new DefaultItemAnimator());
        notificationsView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        notificationsView.setAdapter(notificationAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new NotificationRecyclerTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(notificationsView);
        loadNotifications();
        return view;
    }

    private void loadNotifications() {
        notifications.clear();
        notifications.addAll(UserManager.getInstance().getNotifications());
        notificationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof NotificationViewHolder) {
            String name = notifications.get(viewHolder.getAdapterPosition()).getMessage();
            final Notification deletedItem = notifications.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            notificationAdapter.removeItem(viewHolder.getAdapterPosition());
            Snackbar snackbar = Snackbar
                    .make(constraintLayout, "Melding verwijderd", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notificationAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
