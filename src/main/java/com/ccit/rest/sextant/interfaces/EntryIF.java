package com.ccit.rest.sextant.interfaces;

import com.ccit.rest.sextant.request.EntryGroupReq;
import com.ccit.rest.sextant.request.EntryReq;
import com.ccit.rest.sextant.request.L2tpEntryReq;
import com.ccit.rest.sextant.response.EntryRes;
import org.springframework.stereotype.Component;

@Component("sextantEntryIF")
public class EntryIF extends AbstractSextantIF {

    private final String SS_ENTRY_GROUP_URI = "business/ss/groups/";

    private final String SS_ENTRY_URI = "business/ss/entries/";

    private final String L2TP_ENTRY_URI = "business/l2tp/entries/";

    public EntryRes updateSSGroupEntry(long id, EntryGroupReq entryGroupReq) {
        return super.put(SS_ENTRY_GROUP_URI + "/" + id, entryGroupReq, EntryRes.class);
    }

    public EntryRes removeSSGroupEntry(long id) {
        return super.delete(SS_ENTRY_GROUP_URI + "/" + id, EntryRes.class);
    }

    public EntryRes updateSSEntry(long id, EntryReq entryReq) {
        return super.put(SS_ENTRY_URI + "/" + id, entryReq, EntryRes.class);
    }

    public EntryRes removeSSEntry(long id) {
        return super.delete(SS_ENTRY_URI + "/" + id, EntryRes.class);
    }

    public EntryRes updateL2tpEntry(long id, L2tpEntryReq entryReq) {
        return super.put(L2TP_ENTRY_URI + "/" + id, entryReq, EntryRes.class);
    }

    public EntryRes removeL2tpEntry(long id) {
        return super.delete(L2TP_ENTRY_URI + "/" + id, EntryRes.class);
    }
}