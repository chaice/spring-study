package com.ccit.rest.sextant.interfaces;

import com.ccit.rest.sextant.request.CollectionReq;
import com.ccit.rest.sextant.request.IpsetReq;
import com.ccit.rest.sextant.response.BoxRes;
import org.springframework.stereotype.Component;

@Component("sextantCollectionIF")
public class CollectionIF extends AbstractSextantIF {
    private final String BOX_URI = "acceleration/enterprise/collections";

    public BoxRes updateCollection(CollectionReq collectionReq) {
        return super.put(BOX_URI + "/" + collectionReq.getId(), collectionReq, BoxRes.class);
    }

    public BoxRes deleteCollection(long id) {
        return super.delete(BOX_URI + "/" + id, BoxRes.class);
    }

    public BoxRes updateIpset(IpsetReq ipsetReq) {
        return super.put(BOX_URI + "/" + ipsetReq.getCollectionId() + "/ipsets/" + ipsetReq.getId(), ipsetReq, BoxRes.class);
    }

    public BoxRes deleteIpset(long collectionId, long ipsetId) {
        return super.delete(BOX_URI + "/" + collectionId + "/ipsets/" + ipsetId, BoxRes.class);
    }

//    public BoxRes updateCidr(CidrReq cidrReq) {
//        return super.put(BOX_URI + "/" + cidrReq.getCollectionId() + "/ipsets/" + cidrReq.getIpsetId() + "/cidrs/" + cidrReq.getId(), cidrReq, BoxRes.class);
//    }
//
//    public BoxRes syncCidr(long collectionId, long ipsetId, List<CidrReq> cidrReqList) {
//        return super.put(BOX_URI + "/" + collectionId + "/ipsets/" + ipsetId + "/cidrs", cidrReqList, BoxRes.class);
//    }
//
//    public BoxRes deleteCidr(long collectionId, long ipsetId, long cidrId) {
//        return super.delete(BOX_URI + "/" + collectionId + "/ipsets/" + ipsetId + "/cidrs/" + cidrId, BoxRes.class);
//    }
//
//    public BoxRes updateDomain(DomainReq domainReq) {
//        return super.put(BOX_URI + "/" + domainReq.getCollectionId() + "/ipsets/" + domainReq.getIpsetId() + "/domains/" + domainReq.getId(), domainReq, BoxRes.class);
//    }
//
//    public BoxRes syncDomain(long collectionId, long ipsetId, List<DomainReq> domainReqList) {
//        return super.put(BOX_URI + "/" + collectionId + "/ipsets/" + ipsetId + "/domains", domainReqList, BoxRes.class);
//    }
//
//    public BoxRes deleteDomain(long collectionId, long ipsetId, long domainId) {
//        return super.delete(BOX_URI + "/" + collectionId + "/ipsets/" + ipsetId + "/domains/" + domainId, BoxRes.class);
//    }
}

