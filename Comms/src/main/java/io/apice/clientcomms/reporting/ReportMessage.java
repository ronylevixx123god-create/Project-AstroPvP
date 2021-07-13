package io.apice.clientcomms.reporting;

/*import io.apice.clientcomms.xlib.apache.http.HttpResponse;
import io.apice.clientcomms.xlib.apache.http.client.methods.HttpPost;
import io.apice.clientcomms.xlib.apache.http.entity.StringEntity;
import io.apice.clientcomms.xlib.apache.http.impl.client.CloseableHttpClient;
import io.apice.clientcomms.xlib.apache.http.impl.client.HttpClientBuilder;
import io.apice.clientcomms.xlib.google.common.base.Charsets;
import io.apice.clientcomms.xlib.google.common.hash.Hashing;
import io.apice.clientcomms.xlib.google.gson.Gson;*/

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class ReportMessage {
    private static final String CC_API_KEY = "CC-803B09F9F-803B09FD6-3B0A00C";
    private static final String SALT = "6rvZU8QrzMzsySpvnBXActsNX";
    private MessageType messageType;
    private String uuid;
    private String username;
    private String connectionID;
    private String server;
    private String subServer;
    private String system;
    private String version;
    private String data;

    public void postData() throws IOException {
        this.connectionID = this.connectionID + SALT;
        try {
            this.connectionID = Hashing.sha1().hashString(this.connectionID, Charsets.UTF_8).toString();
        }
        catch (Exception e) {
            this.connectionID = null;
        }
        Gson gson = new Gson();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(this.messageType.getURL());
        StringEntity postingString = new StringEntity(gson.toJson(this));
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        post.setHeader("cc-api-key", CC_API_KEY);
        HttpResponse response = httpClient.execute(post);
    }

    public MessageType getMessageType() {
        return this.messageType;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getUsername() {
        return this.username;
    }

    public String getConnectionID() {
        return this.connectionID;
    }

    public String getServer() {
        return this.server;
    }

    public String getSubServer() {
        return this.subServer;
    }

    public String getSystem() {
        return this.system;
    }

    public String getVersion() {
        return this.version;
    }

    public String getData() {
        return this.data;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setConnectionID(String connectionID) {
        this.connectionID = connectionID;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setSubServer(String subServer) {
        this.subServer = subServer;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ReportMessage)) {
            return false;
        }
        ReportMessage other = (ReportMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        MessageType this$messageType = this.getMessageType();
        MessageType other$messageType = other.getMessageType();
        if (this$messageType == null ? other$messageType != null : !((Object)((Object)this$messageType)).equals((Object)other$messageType)) {
            return false;
        }
        String this$uuid = this.getUuid();
        String other$uuid = other.getUuid();
        if (this$uuid == null ? other$uuid != null : !this$uuid.equals(other$uuid)) {
            return false;
        }
        String this$username = this.getUsername();
        String other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) {
            return false;
        }
        String this$connectionID = this.getConnectionID();
        String other$connectionID = other.getConnectionID();
        if (this$connectionID == null ? other$connectionID != null : !this$connectionID.equals(other$connectionID)) {
            return false;
        }
        String this$server = this.getServer();
        String other$server = other.getServer();
        if (this$server == null ? other$server != null : !this$server.equals(other$server)) {
            return false;
        }
        String this$subServer = this.getSubServer();
        String other$subServer = other.getSubServer();
        if (this$subServer == null ? other$subServer != null : !this$subServer.equals(other$subServer)) {
            return false;
        }
        String this$system = this.getSystem();
        String other$system = other.getSystem();
        if (this$system == null ? other$system != null : !this$system.equals(other$system)) {
            return false;
        }
        String this$version = this.getVersion();
        String other$version = other.getVersion();
        if (this$version == null ? other$version != null : !this$version.equals(other$version)) {
            return false;
        }
        String this$data = this.getData();
        String other$data = other.getData();
        return !(this$data == null ? other$data != null : !this$data.equals(other$data));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ReportMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        MessageType $messageType = this.getMessageType();
        result = result * 59 + ($messageType == null ? 43 : ((Object)((Object)$messageType)).hashCode());
        String $uuid = this.getUuid();
        result = result * 59 + ($uuid == null ? 43 : $uuid.hashCode());
        String $username = this.getUsername();
        result = result * 59 + ($username == null ? 43 : $username.hashCode());
        String $connectionID = this.getConnectionID();
        result = result * 59 + ($connectionID == null ? 43 : $connectionID.hashCode());
        String $server = this.getServer();
        result = result * 59 + ($server == null ? 43 : $server.hashCode());
        String $subServer = this.getSubServer();
        result = result * 59 + ($subServer == null ? 43 : $subServer.hashCode());
        String $system = this.getSystem();
        result = result * 59 + ($system == null ? 43 : $system.hashCode());
        String $version = this.getVersion();
        result = result * 59 + ($version == null ? 43 : $version.hashCode());
        String $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "ReportMessage(messageType=" + (Object)((Object)this.getMessageType()) + ", uuid=" + this.getUuid() + ", username=" + this.getUsername() + ", connectionID=" + this.getConnectionID() + ", server=" + this.getServer() + ", subServer=" + this.getSubServer() + ", system=" + this.getSystem() + ", version=" + this.getVersion() + ", data=" + this.getData() + ")";
    }

    public ReportMessage(MessageType messageType, String uuid, String username, String connectionID, String server, String subServer, String system, String version, String data) {
        this.messageType = messageType;
        this.uuid = uuid;
        this.username = username;
        this.connectionID = connectionID;
        this.server = server;
        this.subServer = subServer;
        this.system = system;
        this.version = version;
        this.data = data;
    }
}

