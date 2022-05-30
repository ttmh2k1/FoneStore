package hcmute.fonestore.fragment.user;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.fonestore.R;

public class InfoActivity extends AppCompatActivity {
    TextView information;
    String info = "    Sản phẩm là kết quả đồ án môn Lập trình di động " +
            "Người dùng có thể xem thông tin chi tiết của các mặt hàng đang được bày bán, các mặt hàng nổi bật, nhiều người mua. Người dùng cũng có thể đăng mặt hàng lên để bán, cập nhật thuộc tính, mô tả của sản phẩm hoặc xóa sản phẩm trong kho của mình. Một cơ sở dữ liệu lưu trữ và cập nhật thông tin về những khách hàng. Thông báo tình trạng hàng đặt mua, giỏ hàng của khách. Diễn đàn trao đổi thông tin, các ý kiến khách hàng, các bài bình luận,.. Quản lý khách VIP, các dịch vụ khuyến mại dành cho khách hàng thân thiết ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        information = findViewById(R.id.app_info);

        information.setText(info);
    }
}
