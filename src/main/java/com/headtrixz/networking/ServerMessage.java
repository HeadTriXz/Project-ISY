package com.headtrixz.networking;

import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Represents a message received from the server.
 *
 * @param message The raw message.
 */
public record ServerMessage(String message) {
  /**
   * Parses the provided array from the raw message and returns it.
   *
   * @return The parsed array from the message.
   */
  public String[] getArray() {
    String[] result = new String[0];
    int start = message.indexOf('[');
    int end = message.indexOf(']');

    if (end > -1 && end > start) {
      JSONArray jsonArray = new JSONArray(message.substring(start, end + 1));
      result = new String[jsonArray.length()];

      for (int i = 0; i < jsonArray.length(); i++) {
        result[i] = jsonArray.getString(i);
      }
    }

    return result;
  }

  /**
   * Returns the message content received from the server.
   *
   * @return The parsed message received from the server.
   */
  public String getMessage() {
    ServerMessageType type = getType();
    if (type != null) {
      int offset = type.toString().length();
      if (offset < message.length()) {
        return message.substring(offset + 1);
      }
    }

    return message;
  }

  /**
   * Parses the provided object from the raw message and returns it as a HashMap.
   *
   * @return The parsed object from the message.
   */
  public HashMap<String, String> getObject() {
    HashMap<String, String> result = new HashMap<>();
    int start = message.indexOf('{');
    int end = message.indexOf('}');

    if (end > -1 && end > start) {
      JSONObject jsonObject = new JSONObject(message.substring(start, end + 1));
      Iterator<String> keys = jsonObject.keys();

      while (keys.hasNext()) {
        String key = keys.next();
        String value = jsonObject.getString(key);

        result.put(key, value);
      }
    }

    return result;
  }

  /**
   * Returns the type of ServerMessage.
   *
   * @return The type of ServerMessage.
   */
  public ServerMessageType getType() {
    for (ServerMessageType value : ServerMessageType.values()) {
      if (message.startsWith(value.toString())) {
        return value;
      }
    }

    return null;
  }

  /**
   * Returns the raw message received from the server.
   *
   * @return The raw message received from the server.
   */
  @Override
  public String toString() {
    return message;
  }
}
