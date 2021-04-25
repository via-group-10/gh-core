package dk.grinhouse.lorawan.gateway;

import dk.grinhouse.lorawan.MeasurementBatch;
import dk.grinhouse.lorawan.decoder.MeasurementDataDecoder;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;

public class WebsocketClient implements WebSocket.Listener {
     private Collection<LorawanListener> listeners;
     private WebSocket server = null;

     public void registerListener(LorawanListener listener)
     {
          listeners.add(listener);
     }

     public void eventTrigger(Object obj)
     {
          for (var listener : listeners) {
               listener.update(obj);
          }
     }


     // Send down-link message to device
     // Must be in Json format according to https://github.com/ihavn/IoT_Semester_project/blob/master/LORA_NETWORK_SERVER.md
     public void sendDownLink(String jsonTelegram) {
          server.sendText(jsonTelegram,true);
     }

     // E.g. url: "wss://iotnet.teracom.dk/app?token=??????????????????????????????????????????????="
     // Substitute ????????????????? with the token you have been given
     public WebsocketClient(String url) {
          listeners = new ArrayList<>();
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
                    eventTrigger(batch);
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
}
