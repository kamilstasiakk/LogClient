import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Kamil on 2016-11-11.
 */
public class LogClient {
    private String ipAddress;

    public LogClient(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String addLog(String logName, List<String> fieldsNames) {
        Client client = Client.create();

        WebResource webResource = client
                .resource("http://localhost:8080/logs/addLog");

        StringBuilder body = new StringBuilder();
        body.append("{\"logName\":\"" + logName + "\",");
        body.append("\"fieldsNames\":[");
        for (String fieldName : fieldsNames) {
            body.append("\"" + fieldName + "\",");
        }
        if (fieldsNames.size() > 0) {
            body.deleteCharAt(body.length()-1);
        }
        body.append("]}");

        ClientResponse response = webResource.type("application/json")
                .post(ClientResponse.class, body.toString());

        if (response.getStatus() == 201) {
            return "Dodano log o nazwie " + logName;
        }
        if (response.getStatus() == 409) {
            return "Podana nazwa logu jest już zajęta";
        }
        if (response.getStatus() == 500) {
            return "Błąd serwera";
        }



        return "";
    }

    public String addLogRecord(String logName, List<String> otherFieldsValues) {
        Client client = Client.create();

        WebResource webResource = client
                .resource("http://localhost:8080/logs/addLogRecord");

        StringBuilder body = new StringBuilder();
        body.append("{\"logName\":\"" + logName + "\",");
        body.append("\"source\":\"" + ipAddress + "\",");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        body.append("\"time\":\"" + df.format(new Date())  + "\",");
        body.append("\"otherFields\":[");

        for (String fieldValue : otherFieldsValues) {
            body.append("\"" + fieldValue + "\",");
        }
        if (otherFieldsValues.size() > 0) {
            body.deleteCharAt(body.length()-1);
        }

        body.append("]}");

        ClientResponse response = webResource.type("application/json")
                .post(ClientResponse.class, body.toString());

        if (response.getStatus() == 201) {
            return "Dodano rekord logu o nazwie " + logName;
        } else if (response.getStatus() == 422) {
            return "Log o podanej nazwie nie istnieje";
        } else if (response.getStatus() == 500) {
            return "Błąd serwera - najprawdopodobniej zła liczba podanych pól";
        } else {
            return "Inny błąd - najprawdopodobniej zła liczba podanych pól";
        }

    }
}
