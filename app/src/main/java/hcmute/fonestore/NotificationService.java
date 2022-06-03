package hcmute.fonestore;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hcmute.fonestore.Activity.ProductActivity;
import hcmute.fonestore.Object.Notification;
import hcmute.fonestore.Object.Product;

public class NotificationService extends Service {
    NotificationManager nm;
    ValueEventListener listener;
    private static final String CHANNEL_ID = "default";
    private static final String CHANNEL_NAME = "my notification channel";
    private static final String CHANNEL_DESCRIPTION = "my notification channel description";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        createNotificationChannel(getApplicationContext());
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Notification n = ds.getValue(Notification.class);

                    FirebaseDatabase.getInstance().getReference().child("product").child(n.getProductId()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Product product = snapshot.getValue(Product.class);
                            product.setId(snapshot.getKey());

                            Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                            intent.putExtra("id", product.getId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                            PendingIntent pendingIntent = PendingIntent.getActivity(
                                    getApplicationContext(),
                                    0,
                                    intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);

                            nm.notify(
                                    0,
                                    createNotification(String.format("%s đã bình luận về sản phẩm %s", n.getCustomerName(), product.getName()))
                                            .setContentIntent(pendingIntent)
                                            .build()
                            );
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        FirebaseDatabase.getInstance().getReference().child("notifications").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(listener);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FirebaseDatabase.getInstance().getReference().child("notifications").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeEventListener(listener);
    }

    private void createNotificationChannel(Context context) {
        nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT < 26) {
            return;
        }

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(CHANNEL_DESCRIPTION);
        nm.createNotificationChannel(channel);
    }

    private NotificationCompat.Builder createNotification(String text) {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.img_no_image)
                    .setContentTitle(getString(R.string.app_name))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        }
        else {
            builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.img_no_image)
                    .setContentTitle(getString(R.string.app_name))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        }
        return builder;
    }
}
