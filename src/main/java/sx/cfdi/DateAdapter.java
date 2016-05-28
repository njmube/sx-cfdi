package sx.cfdi;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rcancino on 24/05/16.
 */
public class DateAdapter extends XmlAdapter<String,Date> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


    public Date unmarshal(String v) throws Exception {
        synchronized (dateFormat) {
            if(v!=null)
                return dateFormat.parse(v);
            else
                return null;
        }
    }

    public String marshal(Date v) throws Exception {
        synchronized (dateFormat) {
            if(v!=null)
                return dateFormat.format(v);
            else
                return null;
        }
    }
}
