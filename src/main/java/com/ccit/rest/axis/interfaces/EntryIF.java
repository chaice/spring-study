package com.ccit.rest.axis.interfaces;

import com.ccit.rest.axis.request.EntryReq;
import com.ccit.rest.axis.response.EntryRes;
import org.springframework.stereotype.Component;

@Component("axisEntryIF")
public class EntryIF extends AbstractAxisIF {

    private final String ENTRY_URI = "business/entries";

    public EntryRes updateEntry(long id, EntryReq entryReq) {
        return super.put(ENTRY_URI + "/" + id, entryReq, EntryRes.class);
    }

    public EntryRes removeEntry(long id) {
        return super.delete(ENTRY_URI + "/" + id, EntryRes.class);
    }

}