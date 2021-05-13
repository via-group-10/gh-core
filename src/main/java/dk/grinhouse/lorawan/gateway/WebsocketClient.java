package dk.grinhouse.lorawan.gateway;

import com.google.gson.Gson;
import dk.grinhouse.lorawan.messages.DownlinkMessage;
import dk.grinhouse.lorawan.messages.UplinkMessage;
import dk.grinhouse.lorawan.services.LorawanService;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;

@Service
public class WebsocketClient implements WebSocket.Listener
{
     private final LorawanService lorawanService;
     private WebSocket server = null;
     private final Gson jsonConverter;

     public WebsocketClient(String url, Gson gson, LorawanService lorawanService) {
          this.lorawanService = lorawanService;
          this.jsonConverter = gson;
          HttpClient client = HttpClient.newHttpClient();
          CompletableFuture<WebSocket> ws = client.newWebSocketBuilder()
               .buildAsync(URI.create(url), this);
          System.out.println("Websocket is being initialized.");
          server = ws.join();
     }

     // Send down-link message to device
     // Must be in Json format according to https://github.com/ihavn/IoT_Semester_project/blob/master/LORA_NETWORK_SERVER.md
     public void sendDownLink(String jsonTelegram) {
          server.sendText(jsonTelegram,true);
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
          UplinkMessage uplinkMessage = jsonConverter.fromJson(messageData.toString(), UplinkMessage.class);
          if (uplinkMessage == null)
          {
               return CompletableFuture.completedFuture("onText() with no data.").thenAccept(System.out::println);
          }
          System.out.println("#################");
          System.out.println("MESSAGE");
          System.out.println(jsonConverter.toJson(uplinkMessage));
          System.out.println("#################");

          lorawanService.handleUplinkMessage(uplinkMessage);

          var dm = lorawanService.getDownlinkMessageCache();
          if (dm != null)
               sendDownLinkProfile(dm);

          webSocket.request(1);
          return CompletableFuture.completedFuture("onText() completed.").thenAccept(System.out::println);
     }

     private void sendDownLinkProfile(DownlinkMessage profile)
     {
          String telegram = jsonConverter.toJson(profile);
          sendDownLink(telegram);
     }
}
