package com.ccit.web.controller;

import com.ccit.bean.AccelerationCidrEnterpriseBean;
import com.ccit.bean.AccelerationCollectionEnterpriseBean;
import com.ccit.bean.AccelerationDomainEnterpriseBean;
import com.ccit.bean.AccelerationIpsetEnterpriseBean;
import com.ccit.enums.AccelerateMode;
import com.ccit.exception.ParameterException;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.service.AccelerationCollectionEnterpriseService;
import com.ccit.util.StringUtils;
import com.ccit.vo.AccelerationCidrEnterpriseVo;
import com.ccit.vo.AccelerationCollectionEnterpriseVo;
import com.ccit.vo.AccelerationDomainEnterpriseVo;
import com.ccit.vo.AccelerationIpsetEnterpriseVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Controller
@RequestMapping("/acceleration/enterprise/collections")
public class AccelerationCollectionEnterpriseController extends BaseController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private AccelerationCollectionEnterpriseService accelerationCollectionEnterpriseService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> listCollection(String accelerateMode) {
        List<AccelerationCollectionEnterpriseVo> accelerationCollectionEnterpriseVos = null;
        if (StringUtils.isNull(accelerateMode)) {
            accelerationCollectionEnterpriseVos = accelerationCollectionEnterpriseService.listAllCollection();
        } else {
            Validator.isTrue(StringUtils.equals(accelerateMode, AccelerateMode.ADVANCED_MODE.getMode()) ||
                            StringUtils.equals(accelerateMode, AccelerateMode.SIMPLE_MODE.getMode()),
                    error(CustomerErrorConstants.ACCELERATION_COLLECTION_MODE_NULL));
            accelerationCollectionEnterpriseVos = accelerationCollectionEnterpriseService.listCollectionByAccelerateMode(accelerateMode);
        }

        return ResponseEntity.ok(accelerationCollectionEnterpriseVos);
    }

    /**
     * 获得加速集合类型为常规的
     *
     * @return
     */
    @RequestMapping(value = "/common", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> listCommonCollection(String accelerateMode) {
        List<AccelerationCollectionEnterpriseVo> accelerationCollectionEnterpriseVos = accelerationCollectionEnterpriseService.listCommonCollectionByAccelerateMode(accelerateMode);
        return ResponseEntity.ok(accelerationCollectionEnterpriseVos);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getCollection(@PathVariable long id) {
        AccelerationCollectionEnterpriseVo accelerationCollectionEnterpriseVo = accelerationCollectionEnterpriseService.getCollectionById(id);
        return ResponseEntity.ok(accelerationCollectionEnterpriseVo);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createCollection(@RequestBody AccelerationCollectionEnterpriseBean collectionBean) {
        verfiyAccelerationCollectionEnterpriseBean(collectionBean);
        accelerationCollectionEnterpriseService.createCollection(collectionBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> updateCollection(@PathVariable long id, @RequestBody AccelerationCollectionEnterpriseBean collectionBean) {
        verfiyUpdateCollectionEnterpriseBean(collectionBean);
        collectionBean.setId(id);
        accelerationCollectionEnterpriseService.modifyCollection(collectionBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> removeCollection(@PathVariable long id) {
        accelerationCollectionEnterpriseService.removeCollection(id);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{id}/ipsets", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> listIpset(@PathVariable long id) {
        List<AccelerationIpsetEnterpriseVo> accelerationIpsetEnterpriseVos = accelerationCollectionEnterpriseService.listIpsetByCollectionId(id);
        return ResponseEntity.ok(accelerationIpsetEnterpriseVos);
    }

    @RequestMapping(value = "/{id}/ipsets/{ipsetId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getIpset(@PathVariable long id, @PathVariable long ipsetId) {
        AccelerationIpsetEnterpriseVo accelerationIpsetEnterpriseVo = accelerationCollectionEnterpriseService.getIpsetById(ipsetId);
        return ResponseEntity.ok(accelerationIpsetEnterpriseVo);
    }

    @RequestMapping(value = "/{id}/ipsets", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createIpset(@PathVariable long id, @RequestBody AccelerationIpsetEnterpriseBean ipsetBean) {
        verfiyAccelerationIpsetEnterpriseBean(ipsetBean);
        ipsetBean.setCollectionId(id);
        accelerationCollectionEnterpriseService.createIpset(ipsetBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{id}/ipsets/{ipsetId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> updateIpset(@PathVariable long id, @PathVariable long ipsetId, @RequestBody AccelerationIpsetEnterpriseBean ipsetBean) {
        ipsetBean.setId(ipsetId);
        ipsetBean.setCollectionId(id);
        if (ipsetBean.getEnable() == null) {
            verfiyAccelerationIpsetEnterpriseBean(ipsetBean);
            accelerationCollectionEnterpriseService.modifyIpset(ipsetBean);
        } else {
            accelerationCollectionEnterpriseService.availableIpset(ipsetBean);
        }
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{id}/ipsets/{ipsetId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> removeIpset(@PathVariable long id, @PathVariable long ipsetId) {
        accelerationCollectionEnterpriseService.removeIpset(id, ipsetId);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{collectionId}/cidrs", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createCidr(@PathVariable long collectionId, @RequestBody AccelerationCidrEnterpriseBean cidrBean) {
        verifyAccelerationCidrEnterpriseBean(cidrBean);
        cidrBean.setCollectionId(collectionId);
        accelerationCollectionEnterpriseService.createCidr(cidrBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{collectionId}/ipsets/{ipsetId}/cidrs", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createCidr(@PathVariable long collectionId, @PathVariable long ipsetId, @RequestBody AccelerationCidrEnterpriseBean cidrBean) {
        verifyAccelerationCidrEnterpriseBean(cidrBean);
        cidrBean.setCollectionId(collectionId);
        cidrBean.setIpsetId(ipsetId);
        accelerationCollectionEnterpriseService.createCidr(cidrBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{collectionId}/ipsets/{ipsetId}/domains", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createDomain(@PathVariable long collectionId, @PathVariable long ipsetId, @RequestBody AccelerationDomainEnterpriseBean domainBean) {
        verifyAccelerationDomainEnterpriseBean(domainBean);
        domainBean.setCollectionId(collectionId);
        domainBean.setIpsetId(ipsetId);
        accelerationCollectionEnterpriseService.createDomain(domainBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{collectionId}/ipsets/{ipsetId}/cidrs/{cidrId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> removeCidr(@PathVariable long collectionId, @PathVariable long ipsetId, @PathVariable long cidrId) {
        accelerationCollectionEnterpriseService.removeCidr(collectionId, ipsetId, cidrId);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{collectionId}/ipsets/{ipsetId}/domains/{domainId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> removeDomain(@PathVariable long collectionId, @PathVariable long ipsetId, @PathVariable long domainId) {
        accelerationCollectionEnterpriseService.removeDomain(collectionId, ipsetId, domainId);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{collectionId}/ipsets/{ipsetId}/cidrs", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity<Object> importCIDRs(@PathVariable long collectionId, @PathVariable long ipsetId, @RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        if (file != null) {
            String fileContent = new String(file.getBytes());
            String[] cidrs = fileContent.replaceAll("\\r\\n", "\n").split("\\n");

            List<AccelerationCidrEnterpriseBean> cidrBeanList = verifyImportCIDRs(cidrs, collectionId, ipsetId);

            accelerationCollectionEnterpriseService.syncCidr(collectionId, ipsetId, cidrBeanList);
            return ResponseEntity.ok(null);
        }
        throw new ParameterException(CustomerErrorConstants.UPLOAD_FILE_NULL);
    }

    @RequestMapping(value = "/{collectionId}/ipsets/{ipsetId}/domains", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity<Object> importDomain(@PathVariable long collectionId, @PathVariable long ipsetId, @RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        if (file != null) {
            String fileContent = new String(file.getBytes());
            String[] domains = fileContent.replaceAll("\\r\\n", "\n").split("\\n");
            List<AccelerationDomainEnterpriseBean> domainBeanList = verifyImportDomains(domains, collectionId, ipsetId);
            accelerationCollectionEnterpriseService.syncDomain(collectionId, ipsetId, domainBeanList);
            return ResponseEntity.ok(null);
        }
        throw new ParameterException(CustomerErrorConstants.UPLOAD_FILE_NULL);
    }

    @RequestMapping(value = "/{collectionId}/ipsets/{ipsetId}/cidrs", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> exportCIDR(@PathVariable long collectionId, @PathVariable long ipsetId) {
        List<AccelerationCidrEnterpriseVo> cidrList = accelerationCollectionEnterpriseService.listCidrByIpsetId(ipsetId);
        AccelerationCollectionEnterpriseVo accelerationCollectionEnterpriseVo = accelerationCollectionEnterpriseService.getCollectionById(collectionId);

        StringBuilder sb = new StringBuilder();
        for (AccelerationCidrEnterpriseVo cidrEnterpriseVo : cidrList) {
            sb.append(StringUtils.trim(cidrEnterpriseVo.getCidr()));

            //判读国家是否为空，不为空时导出
            if (!StringUtils.isNull(cidrEnterpriseVo.getCountry())) {
                sb.append(" ");
                try {
                    sb.append(new String(cidrEnterpriseVo.getCountry().getBytes(), "ISO8859-1"));
                } catch (UnsupportedEncodingException e) {
                    logger.error("字符集转换异常", e);
                }
            }

            sb.append("\r\n");
        }

        HttpHeaders headers = new HttpHeaders();

        String fileName = "";
        try {
            fileName = new String(accelerationCollectionEnterpriseVo.getCollectionName().getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            logger.error("字符集转换异常", e);
        }

        headers.setContentDispositionFormData("attachment", fileName + "_CIDRs.txt");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(sb.toString(), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{collectionId}/ipsets/{ipsetId}/domains", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> exportDomain(@PathVariable long collectionId, @PathVariable long ipsetId) {
        List<AccelerationDomainEnterpriseVo> domainList = accelerationCollectionEnterpriseService.listDomainByIpsetId(ipsetId);
        AccelerationCollectionEnterpriseVo accelerationCollectionEnterpriseVo = accelerationCollectionEnterpriseService.getCollectionById(collectionId);

        StringBuilder sb = new StringBuilder();
        for (AccelerationDomainEnterpriseVo domainEnterpriseVo : domainList) {
            sb.append(StringUtils.trim(domainEnterpriseVo.getDomain())).append("\r\n");
        }

        String fileName = "";
        try {
            fileName = new String(accelerationCollectionEnterpriseVo.getCollectionName().getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            logger.error("字符集转换异常", e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName + "_domains.txt");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(sb.toString(), headers, HttpStatus.CREATED);
    }
}
