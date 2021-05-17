package com.codesample.report2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class SearchActivity extends AppCompatActivity {

    EditText edit;
    TextView text;
    XmlPullParser xpp;

    String key="xcJU6PIYBJEVCE85ZFQWFDqeFXQ5BE%2BhIJxpjFaQdvJVjDniCwL74gYKb8Ft96C7LESTsp4F2q239KvW%2Fcn5oA%3D%3D";
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        edit= (EditText)findViewById(R.id.edit);
        text= (TextView)findViewById(R.id.result);

    }
    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            data=getXmlData();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText(data);
                            }
                        });
                    }
                }).start();
                break;
        }
    }
    String getXmlData() throws UnsupportedEncodingException {
        StringBuffer buffer=new StringBuffer();
        String str= edit.getText().toString();

        String location = URLEncoder.encode(str, "UTF-8");



        String queryUrl="http://apis.data.go.kr/openapi/service/rest/sjHptMcalPstateInfoService/getSjJijeongHptChakgiList?serviceKey=xcJU6PIYBJEVCE85ZFQWFDqeFXQ5BE%2BhIJxpjFaQdvJVjDniCwL74gYKb8Ft96C7LESTsp4F2q239KvW%2Fcn5oA%3D%3D&addr=" + location;
        try{
            URL url= new URL(queryUrl);
            InputStream is= url.openStream();

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );

            String tag;


            int eventType= xpp.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("검색 결과\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();

                        if(tag.equals("item")) ;
                        else if(tag.equals("addr")){
                            buffer.append("주소 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("faxTel")){
                            buffer.append("팩스 번호 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("gwanriJisaCd")){
                            buffer.append("관리지사 코드 :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("hospitalNm")){
                            buffer.append("병원 이름 :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("jhHospital")){
                            buffer.append("연관 장소 :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("jisaNm")){
                            buffer.append("지사 이름 :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("  ,  ");
                        }
                        else if(tag.equals("tel")){
                            buffer.append("전화 번호 :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();

                        if(tag.equals("item")) buffer.append("\n");
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        buffer.append("검색 완료...\n");
        return buffer.toString();

    }

}


