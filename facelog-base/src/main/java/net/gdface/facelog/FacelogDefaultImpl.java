package net.gdface.facelog;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.DeviceGroupBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.LogLightBean;
import net.gdface.facelog.db.PermitBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.PersonGroupBean;
import net.gdface.facelog.service.DuplicateRecordException;

public class FacelogDefaultImpl implements IFaceLog {

	public FacelogDefaultImpl() {
	}

	@Override
	public PersonBean getPerson(int personId) {
		return null;
	}

	@Override
	public List<PersonBean> getPersons(List<Integer> idList) {
		return null;
	}

	@Override
	public PersonBean getPersonByPapersNum(String papersNum) {
		return null;
	}

	@Override
	public List<String> getFeatureBeansByPersonId(int personId) {
		return null;
	}

	@Override
	public int deletePerson(int personId, Token token) {
		return 0;
	}

	@Override
	public int deletePersons(List<Integer> personIdList, Token token) {
		return 0;
	}

	@Override
	public int deletePersonByPapersNum(String papersNum, Token token) {
		return 0;
	}

	@Override
	public int deletePersonsByPapersNum(List<String> papersNumlist, Token token) {
		return 0;
	}

	@Override
	public boolean existsPerson(int persionId) {
		return false;
	}

	@Override
	public boolean isDisable(int personId) {
		return false;
	}

	@Override
	public void disablePerson(int personId, Token token) {
		
	}

	@Override
	public void setPersonExpiryDate(int personId, long expiryDate, Token token) {
		
	}

	@Override
	public void setPersonExpiryDate(List<Integer> personIdList, long expiryDate, Token token) {
		
	}

	@Override
	public void disablePerson(List<Integer> personIdList, Token token) {
		
	}

	@Override
	public List<LogBean> getLogBeansByPersonId(int personId) {
		return null;
	}

	@Override
	public List<Integer> loadAllPerson() {
		return null;
	}

	@Override
	public List<Integer> loadPersonIdByWhere(String where) {
		return null;
	}

	@Override
	public List<PersonBean> loadPersonByWhere(String where, int startRow, int numRows) {
		return null;
	}

	@Override
	public int countPersonByWhere(String where) {
		return 0;
	}

	@Override
	public PersonBean savePerson(PersonBean bean, Token token) {
		return null;
	}

	@Override
	public void savePersons(List<PersonBean> beans, Token token) {
		
	}

	@Override
	public PersonBean savePerson(PersonBean bean, byte[] idPhoto, Token token) {
		return null;
	}

	@Override
	public int savePerson(Map<ByteBuffer, PersonBean> persons, Token token) {
		return 0;
	}

	@Override
	public PersonBean savePerson(PersonBean bean, String idPhotoMd5, String featureMd5, Token token) {
		return null;
	}

	@Override
	public PersonBean savePerson(PersonBean bean, byte[] idPhoto, FeatureBean featureBean, Integer deviceId,
			Token token) {
		return null;
	}

	@Override
	public PersonBean savePerson(PersonBean bean, byte[] idPhoto, byte[] feature, List<FaceBean> faceBeans,
			Token token) {
		return null;
	}

	@Override
	public PersonBean savePerson(PersonBean bean, byte[] idPhoto, byte[] feature,
			Map<ByteBuffer, FaceBean> faceInfo, Integer deviceId, Token token) {
		return null;
	}

	@Override
	public PersonBean savePerson(PersonBean bean, byte[] idPhoto, byte[] feature, byte[] featureImage,
			FaceBean featureFaceBean, Integer deviceId, Token token) {
		return null;
	}

	@Override
	public void replaceFeature(Integer personId, String featureMd5, boolean deleteOldFeatureImage, Token token) {
		
	}

	@Override
	public List<Integer> loadUpdatedPersons(long timestamp) {
		return null;
	}

	@Override
	public List<Integer> loadPersonIdByUpdateTime(long timestamp) {
		return null;
	}

	@Override
	public List<String> loadFeatureMd5ByUpdate(long timestamp) {
		return null;
	}

	@Override
	public void addLog(LogBean bean, Token token) throws DuplicateRecordException {
		
	}

	@Override
	public void addLogs(List<LogBean> beans, Token token) throws DuplicateRecordException {
		
	}

	@Override
	public List<LogBean> loadLogByWhere(String where, int startRow, int numRows) {
		return null;
	}

	@Override
	public List<LogLightBean> loadLogLightByWhere(String where, int startRow, int numRows) {
		return null;
	}

	@Override
	public int countLogLightByWhere(String where) {
		return 0;
	}

	@Override
	public int countLogByWhere(String where) {
		return 0;
	}

	@Override
	public List<LogLightBean> loadLogLightByVerifyTime(long timestamp, int startRow, int numRows) {
		return null;
	}

	@Override
	public int countLogLightByVerifyTime(long timestamp) {
		return 0;
	}

	@Override
	public boolean existsImage(String md5) {
		return false;
	}

	@Override
	public ImageBean addImage(byte[] imageData, Integer deviceId, FaceBean faceBean, Integer personId, Token token)
			throws DuplicateRecordException {
		return null;
	}

	@Override
	public boolean existsFeature(String md5) {
		return false;
	}

	@Override
	public FeatureBean addFeature(byte[] feature, Integer personId, List<FaceBean> faecBeans, Token token)
			throws DuplicateRecordException {
		return null;
	}

	@Override
	public FeatureBean addFeature(byte[] feature, Integer personId, Map<ByteBuffer, FaceBean> faceInfo,
			Integer deviceId, Token token) throws DuplicateRecordException {
		return null;
	}
	
	@Override
	public List<String> deleteFeature(String featureMd5, boolean deleteImage, Token token) {
		return null;
	}

	@Override
	public int deleteAllFeaturesByPersonId(int personId, boolean deleteImage, Token token) {
		return 0;
	}

	@Override
	public FeatureBean getFeature(String md5) {
		return null;
	}

	@Override
	public List<FeatureBean> getFeatures(List<String> md5) {
		return null;
	}

	@Override
	public List<String> getFeaturesOfPerson(int personId) {
		return null;
	}

	@Override
	public byte[] getFeatureBytes(String md5) {
		return null;
	}

	@Override
	public byte[] getImageBytes(String imageMD5) {
		return null;
	}

	@Override
	public ImageBean getImage(String imageMD5) {
		return null;
	}

	@Override
	public List<String> getImagesAssociatedByFeature(String featureMd5) {
		return null;
	}

	@Override
	public Integer getDeviceIdOfFeature(String featureMd5) {
		return null;
	}

	@Override
	public int deleteImage(String imageMd5, Token token) {
		return 0;
	}

	@Override
	public boolean existsDevice(int id) {
		return false;
	}

	@Override
	public DeviceBean saveDevice(DeviceBean deviceBean, Token token) {
		return null;
	}

	@Override
	public DeviceBean updateDevice(DeviceBean deviceBean, Token token) {
		return null;
	}

	@Override
	public DeviceBean getDevice(int deviceId) {
		return null;
	}

	@Override
	public List<DeviceBean> getDevices(List<Integer> idList) {
		return null;
	}

	@Override
	public List<DeviceBean> loadDeviceByWhere(String where, int startRow, int numRows) {
		return null;
	}

	@Override
	public int countDeviceByWhere(String where) {
		return 0;
	}

	@Override
	public List<Integer> loadDeviceIdByWhere(String where) {
		return null;
	}

	@Override
	public DeviceGroupBean saveDeviceGroup(DeviceGroupBean deviceGroupBean, Token token) {
		return null;
	}

	@Override
	public DeviceGroupBean getDeviceGroup(int deviceGroupId) {
		return null;
	}

	@Override
	public List<DeviceGroupBean> getDeviceGroups(List<Integer> groupIdList) {
		return null;
	}

	@Override
	public int deleteDeviceGroup(int deviceGroupId, Token token) {
		return 0;
	}

	@Override
	public List<Integer> getSubDeviceGroup(int deviceGroupId) {
		return null;
	}

	@Override
	public List<Integer> getDevicesOfGroup(int deviceGroupId) {
		return null;
	}

	@Override
	public List<Integer> listOfParentForDeviceGroup(int deviceGroupId) {
		return null;
	}

	@Override
	public List<Integer> getDeviceGroupsBelongs(int deviceId) {
		return null;
	}

	@Override
	public PersonGroupBean savePersonGroup(PersonGroupBean personGroupBean, Token token) {
		return null;
	}

	@Override
	public PersonGroupBean getPersonGroup(int personGroupId) {
		return null;
	}

	@Override
	public List<PersonGroupBean> getPersonGroups(List<Integer> groupIdList) {
		return null;
	}

	@Override
	public int deletePersonGroup(int personGroupId, Token token) {
		return 0;
	}

	@Override
	public List<Integer> getSubPersonGroup(int personGroupId) {
		return null;
	}

	@Override
	public List<Integer> getPersonsOfGroup(int personGroupId) {
		return null;
	}

	@Override
	public List<Integer> listOfParentForPersonGroup(int personGroupId) {
		return null;
	}

	@Override
	public List<Integer> getPersonGroupsBelongs(int personId) {
		return null;
	}

	@Override
	public List<Integer> loadDeviceGroupByWhere(String where, int startRow, int numRows) {
		return null;
	}

	@Override
	public int countDeviceGroupByWhere(String where) {
		return 0;
	}

	@Override
	public List<Integer> loadDeviceGroupIdByWhere(String where) {
		return null;
	}

	@Override
	public void addPermit(DeviceGroupBean deviceGroup, PersonGroupBean personGroup, Token token) {
		
	}

	@Override
	public void addPermit(int deviceGroupId, int personGroupId, Token token) {
		
	}

	@Override
	public int deletePermit(DeviceGroupBean deviceGroup, PersonGroupBean personGroup, Token token) {
		return 0;
	}

	@Override
	public boolean getGroupPermit(int deviceId, int personGroupId) {
		return false;
	}

	@Override
	public boolean getPersonPermit(int deviceId, int personId) {
		return false;
	}

	@Override
	public List<Boolean> getGroupPermits(int deviceId, List<Integer> personGroupIdList) {
		return null;
	}

	@Override
	public List<Boolean> getPersonPermits(int deviceId, List<Integer> personIdList) {
		return null;
	}

	@Override
	public List<PermitBean> loadPermitByUpdate(long timestamp) {
		return null;
	}

	@Override
	public List<Integer> loadPersonGroupByWhere(String where, int startRow, int numRows) {
		return null;
	}

	@Override
	public int countPersonGroupByWhere(String where) {
		return 0;
	}

	@Override
	public List<Integer> loadPersonGroupIdByWhere(String where) {
		return null;
	}

	@Override
	public DeviceBean registerDevice(DeviceBean newDevice) throws ServiceSecurityException {
		return null;
	}

	@Override
	public void unregisterDevice(int deviceId, Token token) throws ServiceSecurityException {
		
	}

	@Override
	public Token online(DeviceBean device) throws ServiceSecurityException {
		return null;
	}

	@Override
	public void offline(Token token) throws ServiceSecurityException {
		
	}

	@Override
	public Token applyPersonToken(int personId, String password, boolean isMd5) throws ServiceSecurityException {
		return null;
	}

	@Override
	public void releasePersonToken(Token token) throws ServiceSecurityException {
		
	}

	@Override
	public Token applyRootToken(String password, boolean isMd5) throws ServiceSecurityException {
		return null;
	}

	@Override
	public void releaseRootToken(Token token) throws ServiceSecurityException {
		
	}

	@Override
	public boolean isValidPassword(String userId, String password, boolean isMd5, Token token)
			throws ServiceSecurityException {
		return false;
	}

	@Override
	public String applyAckChannel(Token token) {
		return null;
	}

	@Override
	public String applyAckChannel(Token token, long duration) {
		return null;
	}

	@Override
	public long applyCmdSn(Token token) {
		return 0;
	}

	@Override
	public boolean isValidCmdSn(long cmdSn) {
		return false;
	}

	@Override
	public boolean isValidAckChannel(String ackChannel) {
		return false;
	}

	@Override
	public Map<MQParam, String> getRedisParameters(Token token) {
		return null;
	}

	@Override
	public String getProperty(String key, Token token) {
		return null;
	}

	@Override
	public Map<String, String> getServiceConfig(Token token) {
		return null;
	}

	@Override
	public void setProperty(String key, String value, Token token) {
		
	}

	@Override
	public void setProperties(Map<String, String> config, Token token) {
		
	}

	@Override
	public void saveServiceConfig(Token token) {
		
	}

	@Override
	public String version() {
		return null;
	}

	@Override
	public Map<String, String> versionInfo() {
		return null;
	}

	@Override
	public boolean isLocal() {
		return false;
	}

}