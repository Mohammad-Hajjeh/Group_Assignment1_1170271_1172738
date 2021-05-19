package edu.bzu.group_assignment1_1170271_1172738;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    private SimpleAdapter sa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HashMap<String,String> item;
        for(int i=0;i<StatesAndCapitals.length;i++){
            item = new HashMap<String,String>();
            item.put( "line1", StatesAndCapitals[i][0]);
            item.put( "line2", StatesAndCapitals[i][1]);
            list.add( item );
        }
        sa = new SimpleAdapter(this, list,
                R.layout.twolines,
                new String[] { "line1","line2" },
                new int[] {R.id.line_a, R.id.line_b});
        ((ListView)findViewById(R.id.mobile_list)).setAdapter(sa);
    }

    private String[][] StatesAndCapitals =
            {{"Alabama","Montgomery"},
                    {"Alaska","Juneau"},
                    {"Arizona","Phoenix"},
                    {"Arkansas","Little Rock"},
                    {"California","Sacramento"},
                    {"Colorado","Denver"},
                    {"Connecticut","Hartford"},
                    {"Delaware","Dover"},
                    {"Florida","Tallahassee"},
                    {"Georgia","Atlanta"},
                    {"Hawaii","Honolulu"},
                    {"Idaho","Boise"},
                    {"Illinois","Springfield"},
                    {"Indiana","Indianapolis"},
                    {"Iowa","Des Moines"},
                    {"Kansas","Topeka"},
                    {"Kentucky","Frankfort"},
                    {"Louisiana","Baton Rouge"},
                    {"Maine","Augusta"}
            ,{"Alaska","Juneau"},
                    {"Arizona","Phoenix"},
                    {"Arkansas","Little Rock"},
                    {"California","Sacramento"},
                    {"Colorado","Denver"},
                    {"Connecticut","Hartford"},
                    {"Delaware","Dover"},
                    {"Florida","Tallahassee"},
                    {"Georgia","Atlanta"},
                    {"Hawaii","Honolulu"},
                    {"Idaho","Boise"},
                    {"Illinois","Springfield"},
                    {"Indiana","Indianapolis"},
                    {"Iowa","Des Moines"},
                    {"Kansas","Topeka"},
                    {"Kentucky","Frankfort"},
                    {"Louisiana","Baton Rouge"},
                    {"Maine","Augusta"}};
}