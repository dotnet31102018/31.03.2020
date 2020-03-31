package hello.itay.com.demo6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextViewResult = findViewById(R.id.text_view_result);

        // create client
        OkHttpClient client = new OkHttpClient();

        String url = "https://itayhau-coupon.herokuapp.com/api/coupon";

        final Gson gson = new Gson();

        Message msg = new Message();
        msg.Id = 10;
        msg.Sender = "Tommy";
        msg.Body = "Hello Charlie!";

        Request request = new Request.Builder().
                url(url).
                get()
                .build(); // Fluent

        // enqueue - add item to the request queue
        // ANR - Application not responding
        client.newCall(request).enqueue(
                new Callback()  // anonymous class
                {
            @Override
            public void onFailure(Call call, final IOException e) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextViewResult.setText(e.getMessage());
                    }
                });
            }

            // SUCCESS!!!
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) { // HTTP 200+
                    final String myResponse = response.body().string();

                    // Dispathcer.Invoke(...)
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextViewResult.setText(myResponse);
                        }
                    });
                }
                else
                {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextViewResult.setText(response.toString());
                        }
                    });
                }
            }
        });
    }
}

//        String url = "http://10.0.2.2:58882/api/flights";