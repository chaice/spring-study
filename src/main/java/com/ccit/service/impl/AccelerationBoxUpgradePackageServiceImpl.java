package com.ccit.service.impl;

import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.ccit.bean.AccelerationBoxUpgradePackageBean;
import com.ccit.dao.AccelerationBoxUpgradePackageDao;
import com.ccit.dao.AccelerationBoxUpgradeTaskDao;
import com.ccit.entity.AccelerationBoxUpgradePackageEntity;
import com.ccit.entity.AccelerationBoxUpgradeTaskEntity;
import com.ccit.enums.BoxUpgradeTaskStatus;
import com.ccit.model.Assembler;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.sextant.interfaces.UpgradeIF;
import com.ccit.rest.sextant.request.BoxUpgradePackageReq;
import com.ccit.rest.sextant.response.BoxUpgradeRes;
import com.ccit.service.AccelerationBoxUpgradePackageService;
import com.ccit.service.GlobalDictionaryService;
import com.ccit.util.MD5Utils;
import com.ccit.util.StringUtils;
import com.ccit.vo.AccelerationBoxUpgradePackageVo;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class AccelerationBoxUpgradePackageServiceImpl implements AccelerationBoxUpgradePackageService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private AccelerationBoxUpgradePackageDao accelerationBoxUpgradePackageDao;

    @Autowired
    private Assembler<AccelerationBoxUpgradePackageBean, AccelerationBoxUpgradePackageEntity, AccelerationBoxUpgradePackageVo> accelerationBoxUpgradePackageAssembler;

    @Autowired
    protected GlobalDictionaryService dictionaryService;

    @Autowired
    private AccelerationBoxUpgradeTaskDao accelerationBoxUpgradeTaskDao;

    @Autowired
    @Qualifier("sextantUpgradeIF")
    private UpgradeIF upgradeIF;

    @Override
    public List<AccelerationBoxUpgradePackageVo> listUpgradePackage() {
        List<AccelerationBoxUpgradePackageVo> upgradePackageList = new LinkedList<>();
        List<AccelerationBoxUpgradePackageEntity> upgradePackageEntityList = accelerationBoxUpgradePackageDao.findAll();
        upgradePackageEntityList.forEach(upgradePackageEntity -> {
            upgradePackageList.add(accelerationBoxUpgradePackageAssembler.toVo(upgradePackageEntity));
        });

        return upgradePackageList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createUpgradePackage(AccelerationBoxUpgradePackageBean upgradePackageBean) throws IOException {
        String tmpDir = dictionaryService.fromKey("cube.upgrade.package.tmp.directory");
        String prefix = dictionaryService.fromKey("cube.upgrade.package.url.prefix");
        String directory = dictionaryService.fromKey("cube.upgrade.package.directory");


        File tmpFile = new File(tmpDir + File.separator + upgradePackageBean.getTmpFileName());
        if (!tmpFile.exists()) {
            throw new BusinessException(CustomerErrorConstants.UPLOAD_FILE_NULL);
        }

        String fileMd5 = MD5Utils.fileMD5(tmpFile.getPath());
        Validator.isTrue(StringUtils.equals(fileMd5, upgradePackageBean.getFileMd5()), ResourceException.error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_FILEMD5_NOT_EQUALS));

        String fileName = tmpFile.getName();
        fileName = fileName.substring(fileName.indexOf("_"));//去掉uuid
        fileName = upgradePackageBean.getPackageVersion() + fileName;//版本号+文件名
        upgradePackageBean.setFileName(fileName);

        AccelerationBoxUpgradePackageEntity entity = accelerationBoxUpgradePackageDao.findByFileMd5(fileMd5);
        Validator.isNull(entity, ResourceException.error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_FILEMD5_EXIST));

        upgradePackageBean.setCreateTime(new Timestamp(System.currentTimeMillis()));
        upgradePackageBean.setDownloadUrl(prefix + "/" + fileName);

        AccelerationBoxUpgradePackageEntity packageEntity = accelerationBoxUpgradePackageAssembler.fromBean(upgradePackageBean);
        accelerationBoxUpgradePackageDao.saveOrUpdate(packageEntity);

        updateUpgradePackageOfSextant(packageEntity);

        File targetFile = new File(directory + File.separator + fileName);

        try {
            FileUtils.copyFile(tmpFile, targetFile);
        } catch (Exception e) {
            logger.error("Matrix-更新企业升级包-临时文件复制失败", e);
            throw new BusinessException(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_UPLOAD_FAIL);
        }

        try {
            FileUtils.deleteDirectory(new File(tmpDir));
        } catch (Exception e) {
            logger.error("Matrix-更新企业升级包-临时文件删除失败", e);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removePackage(long packageId) {
        AccelerationBoxUpgradePackageEntity entity = accelerationBoxUpgradePackageDao.findOne(packageId);
        Validator.notNull(entity, ResourceException.error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_NOT_EXIST));

        accelerationBoxUpgradePackageDao.deleteById(packageId);

        // 升级中盒子不能删除
        AccelerationBoxUpgradeTaskEntity taskEntity = accelerationBoxUpgradeTaskDao.findByPackageIdAndStatus(packageId, BoxUpgradeTaskStatus.UPGRADING.getStatus());
        if (taskEntity != null) {
            throw new BusinessException(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_NOT_DELETE);
        }

        accelerationBoxUpgradeTaskDao.deleteByPackageId(packageId);

        removeUpgradePackageOfSextant(packageId);

        String directory = dictionaryService.fromKey("cube.upgrade.package.directory");
        File tempFile = new File(directory, entity.getFileName());
        try {
            if (tempFile.exists()) {
                FileUtils.deleteQuietly(tempFile);
            }
        } catch (Exception e) {
            logger.error("Matrix-更新企业升级包-服务器文件删除失败", e);
            throw new BusinessException(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_UPLOAD_DEL_FAIL);
        }


    }

    @Override
    public AccelerationBoxUpgradePackageVo findById(long packageId) {
        AccelerationBoxUpgradePackageEntity entity = accelerationBoxUpgradePackageDao.findOne(packageId);
        Validator.notNull(entity, ResourceException.error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_NOT_EXIST));
        return accelerationBoxUpgradePackageAssembler.toVo(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modifyUpgradePackage(AccelerationBoxUpgradePackageBean upgradePackageBean) throws IOException {
        String directory = dictionaryService.fromKey("cube.upgrade.package.directory");
        String prefix = dictionaryService.fromKey("cube.upgrade.package.url.prefix");

        AccelerationBoxUpgradePackageEntity entity = accelerationBoxUpgradePackageDao.findOne(upgradePackageBean.getId());

        String oldPackageVersion = entity.getPackageVersion();

        File oldFile = new File(directory + File.separator + entity.getFileName());

        if (!StringUtils.equals(upgradePackageBean.getPackageVersion(), entity.getPackageVersion())) {//修改版本号，需将文件重命名

            int index = entity.getFileName().indexOf("_");
            String newFileName = upgradePackageBean.getPackageVersion() + entity.getFileName().substring(index);
            entity.setFileName(newFileName);
        }

        entity.setPackageVersion(upgradePackageBean.getPackageVersion());
        entity.setSuitableVersion(upgradePackageBean.getSuitableVersion());
        entity.setDownloadUrl(prefix + "/" + entity.getFileName());

        accelerationBoxUpgradePackageDao.saveOrUpdate(entity);
        updateUpgradePackageOfSextant(entity);

        try {

            if (!StringUtils.equals(upgradePackageBean.getPackageVersion(), oldPackageVersion)) {
                oldFile.renameTo(new File(directory + File.separator + entity.getFileName()));//文件重命名
            }
        } catch (Exception e) {
            logger.error("Matrix-更新企业升级包-服务器文件重命名失败", e);
            throw new BusinessException(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_UPLOAD_FAIL);
        }


    }

    @Override
    public String uploadUpgradePackage(MultipartFile file) {
        String tmpDir = dictionaryService.fromKey("cube.upgrade.package.tmp.directory");
        //文件名 uuid _ 文件名
        File tempFile = new File(tmpDir, UUID.randomUUID() + "_" + file.getOriginalFilename());
        System.out.println(tempFile.getPath());
        try {
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            file.transferTo(tempFile);
        } catch (Exception e) {
            throw new BusinessException(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_UPLOAD_FAIL);
        }
        return tempFile.getName();
    }

    private void updateUpgradePackageOfSextant(AccelerationBoxUpgradePackageEntity entity) {
        BoxUpgradeRes boxUpgradeRes = upgradeIF.updateUpgradePackage(new BoxUpgradePackageReq(entity));
        if (!boxUpgradeRes.isSuccess()) {
            logger.error(String.format("Sextant-更新企业升级包-同步失败，错误码：%s，错误信息：%s",
                    boxUpgradeRes.getErrorBody().getErrorCode(),
                    boxUpgradeRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.BOX_ENTERPRISE_SYNCHRONIZATION_FAIL);
        }

    }

    private void removeUpgradePackageOfSextant(long id) {
        BoxUpgradeRes boxUpgradeRes = upgradeIF.removeUpgradePackage(id);
        if (!boxUpgradeRes.isSuccess()) {
            logger.error(String.format("Sextant-删除企业升级包-同步失败，错误码：%s，错误信息：%s",
                    boxUpgradeRes.getErrorBody().getErrorCode(),
                    boxUpgradeRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.BOX_ENTERPRISE_SYNCHRONIZATION_FAIL);
        }
    }


}
