package com.example.battleparty.WebSockets;

public class UtilEnum {

    public enum URL {
        BASE_URL("ws://coms-309-053.class.las.iastate.edu:8080/"),
        FRIEND_URL("ws://coms-309-053.class.las.iastate.edu:8080/player/"),
        ITEM_URL("ws://coms-309-053.class.las.iastate.edu:8080/item/"),
        PARTY_URL("ws://coms-309-053.class.las.iastate.edu:8080/party/"),
        BASE_URL_H("http://coms-309-053.class.las.iastate.edu:8080/"),
        BASE_URL_H_LOCAL("http://10.0.2.2:8080/"),
        BASE_URL_LOCAL("ws://10.0.2.2:8080/"),
        FRIEND_URL_LOCAL("ws://10.0.2.2:8080/player/"),
        ITEM_URL_LOCAL("ws://10.0.2.2:8080/item/"),
        PARTY_URL_LOCAL("ws://10.0.2.2:8080/party/");

        private final String url;

        private URL(String url) {
            this.url = url;
        }

        public String getURL() {
            return url;
        }
    }//end of URL enum

    public enum CONTROLLER {
        FRIENDCONTROLLER,
        ITEMCONTROLLER,
        PARTYCONTROLLER;
    }

}
