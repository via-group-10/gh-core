package dk.grinhouse.lorawan.gateway;

import dk.grinhouse.events.EventType;
import dk.grinhouse.events.GrinhouseEvent;
import dk.grinhouse.lorawan.messages.MeasurementBatch;
import dk.grinhouse.lorawan.decoder.MeasurementDataDecoder;
import dk.grinhouse.lorawan.services.LorawanService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;

@Service
public class WebsocketClient implements WebSocket.Listener, ApplicationListener<GrinhouseEvent>
{
     private final LorawanService lorawanService;
     private WebSocket server = null;

     // Send down-link message to device
     // Must be in Json format according to https://github.com/ihavn/IoT_Semester_project/blob/master/LORA_NETWORK_SERVER.md
     public void sendDownLink(String jsonTelegram) {
          server.sendText(jsonTelegram,true);
     }

     // E.g. url: "wss://iotnet.teracom.dk/app?token=??????????????????????????????????????????????="
     // Substitute ????????????????? with the token you have been given
     public WebsocketClient(String url, LorawanService lorawanService) {
          this.lorawanService = lorawanService;
          HttpClient client = HttpClient.newHttpClient();
          CompletableFuture<WebSocket> ws = client.newWebSocketBuilder()
               .buildAsync(URI.create(url), this);
          System.out.println("Websocket is being initialized.");
          server = ws.join();
     }

     //onOpen()
     public void onOpen(WebSocket webSocket) {
          // This WebSocket will invoke onText, onBinary, onPing, onPong or onClose methods on the associated listener (i.e. receive methods) up to n more times
          webSocket.request(1);
          System.out.println("WebSocket Listener has been opened for requests.");
     }

     //onError()
     public void onError(WebSocket webSocket, Throwable error) {
          System.out.println("A " + error.getCause() + " exception was thrown.");
          System.out.println("Message: " + error.getLocalizedMessage());
          webSocket.abort();
     }
     //onClose()
     public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
          System.out.println("WebSocket closed!");
          System.out.println("Status:" + statusCode + " Reason: " + reason);
          return CompletableFuture.completedFuture("onClose() completed.").thenAccept(System.out::println);
     }

     //onPing()
     public CompletionStage<?> onPing(WebSocket webSocket, ByteBuffer message) {
          webSocket.request(1);
          System.out.println("Ping: Client ---> Server");
          System.out.println(message.asCharBuffer());
          return CompletableFuture.completedFuture("Ping completed.").thenAccept(System.out::println);
     }

     //onPong()
     public CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message) {
          webSocket.request(1);
          System.out.println("Pong: Client ---> Server");
          System.out.println(message.asCharBuffer());
          return CompletableFuture.completedFuture("Pong completed.").thenAccept(System.out::println);
     }

     //onText()
     public CompletionStage<?> onText(WebSocket webSocket, CharSequence messageData, boolean last)
     {
          JSONObject socketData = null;
          String indented = null;
          try {
               socketData = new JSONObject(messageData.toString());
               indented = socketData.toString(4);
          }
          catch (JSONException e) {
               e.printStackTrace();
               onError(webSocket, e);
          }
          if (socketData == null || indented == null)
          {
               return CompletableFuture.completedFuture("onText() with no data.").thenAccept(System.out::println);
          }
          System.out.println("#################");
          System.out.println("MESSAGE");
          System.out.println(indented);
          try {
               if (socketData.getString("cmd").equals("rx")) {
                    String data = socketData.getString("data");
                    MeasurementDataDecoder decoder = new MeasurementDataDecoder(data);
                    MeasurementBatch batch = new MeasurementBatch(decoder.getDataAsBytes());
                    lorawanService.addNewMeasurementBatch(batch);
               }
          }
          catch (JSONException e) {
               e.printStackTrace();
               onError(webSocket, e);
          }
          System.out.println("#################");
          webSocket.request(1);
          return CompletableFuture.completedFuture("onText() completed.").thenAccept(System.out::println);
     }

     @Override
     public void onApplicationEvent(GrinhouseEvent event)
     {
          if (event.getType() == EventType.SEND_DOWNLINK_PROFILE)
          {
               sendDownLinkProfile((JSONObject) event.getArgument());
          }
     }

     private void sendDownLinkProfile(JSONObject profile)
     {
          try {
               System.out.println("Sending to loriot server...");
               var telegram = profile.toString(4);
               sendDownLink(telegram);
          }
          catch (JSONException e) {
               e.printStackTrace();
          }
     }
}
