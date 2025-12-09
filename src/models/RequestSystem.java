package models;

import helpers.Parser;
import java.util.*;
/**
 * Request System that handles logic concerning Requests.
 */
public class RequestSystem implements SubSystem {

    private final List<Request> requests = new ArrayList<>();

    private final String requestsPath = "src/assets/Requests.json";

    public static int nextReqID = 100;

    @Override
    public void load() {
        Parser.parseRequests(this.requestsPath, this.requests);
    }

    @Override
    public void save() {
        Parser.saveRequests(this.requestsPath, this.requests);
    }

    public void addRequest(Request r) {
        this.requests.add(r);
    }

}