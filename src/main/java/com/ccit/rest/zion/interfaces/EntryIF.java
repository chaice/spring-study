package com.ccit.rest.zion.interfaces;

import com.ccit.rest.zion.response.EntryRes;
import com.ccit.rest.zion.request.EntryReq;
import org.springframework.stereotype.Component;

@Component("zionEntryIF")
public class EntryIF extends AbstractZionIF {

    private final String ENTRY_URI = "business/entries";

    public EntryRes updateEntry(long id, EntryReq entryReq) {
        return super.put(ENTRY_URI + "/" + id, entryReq, EntryRes.class);
    }

    public EntryRes removeEntry(long id) {
        return super.delete(ENTRY_URI + "/" + id, EntryRes.class);
    }

}