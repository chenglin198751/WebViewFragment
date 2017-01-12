package meilishuo.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends BaseActivity {
    EditText mEditText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.edit_text);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mEditText.getText().toString();
                if (TextUtils.isEmpty(url)) {
                    return;
                }

                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

    }

}


