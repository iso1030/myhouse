package com.jerrylin.myhouse.service.user;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Clock;
import org.springside.modules.utils.Encodes;

import com.jerrylin.myhouse.entity.User;
import com.jerrylin.myhouse.entity.UserAccount;
import com.jerrylin.myhouse.entity.UserProfile;
import com.jerrylin.myhouse.repository.UserAccountDao;
import com.jerrylin.myhouse.repository.UserProfileDao;
import com.jerrylin.myhouse.service.AppConfigService;
import com.jerrylin.myhouse.service.ServiceException;

@Component
@Transactional
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public static final String HASH_CHARSET = "utf-8";
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	
	@Autowired
	private AppConfigService appConfigService;

	@Autowired
	private UserProfileDao userProfileDao;

	@Autowired
	private UserAccountDao userAccountDao;

	private Clock clock = Clock.DEFAULT;
	
	/**
	 * 创建用户目录
	 * 
	 * @param userId
	 * @return
	 */
//	public boolean createUserDir(Long userId) {
//		boolean result = false;
//		String uploadDir = this.appConfigService.getUploadDir();
//		String targetDir = uploadDir + File.separator + String.valueOf(userId);
//		File file = new File(targetDir);
//		if (!file.exists() || !file.isDirectory()) {
//			result = file.mkdir();
//		} else {
//			// exists
//			result = true;
//		}
//		return result;
//	}
	/**
	 * 删除用户目录
	 * 
	 * @param userId
	 * @return
	 */
//	public boolean deleteUserDir(Long userId) {
//		boolean result = true;
//		String uploadDir = this.appConfigService.getUploadDir();
//		String targetDir = uploadDir + File.separator + String.valueOf(userId);
//		File file = new File(targetDir);
//		if (file.exists() && file.isDirectory()) {
//			// move to temp dir
//			String removeDir = this.appConfigService.getDeleteDir();
//			result = file.renameTo(new File(removeDir + File.separator + String.valueOf(userId)));
//		} else {
//			result = true;
//		}
//		return result;
//	}
	
	public long getCount() {
		return userProfileDao.count();
	}
	/**
	 * 查询单个用户基本信息
	 * 
	 * @param userId
	 * @return
	 */
	public User getUser(Long userId) {
		UserProfile userProfile = userProfileDao.findOne(userId);
		UserAccount userAccount = userAccountDao.findOne(userId);

		if (userProfile != null) {
			User user = new User(userProfile);
			user.setCreateTime(userAccount.getCreateTime());
			user.setType(userAccount.getType());
			return user;
		}
		return null;
	}
	/**
	 * 分页查询用户信息
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<User> getUserPage(int pageNumber, int pageSize) {
		Page<UserProfile> page = userProfileDao.findAll(new PageRequest(pageNumber - 1, pageSize));
		
		List<UserProfile> userProfiles = page.getContent();
		List<Long> ids = new ArrayList<Long>();
		for (UserProfile userProfile : userProfiles) {
			ids.add(userProfile.getId());
		}
		// 不知道这里有没有针对批量查询做优化的，如果没有的话，其实可以直接在上面循环直接做掉
		Iterable<UserAccount> userAccounts = userAccountDao.findAll(ids);
		Map<Long, UserAccount> temp = new HashMap<Long, UserAccount>();
		for (UserAccount userAccount : userAccounts) {
			temp.put(userAccount.getId(), userAccount);
		}

		List<User> userList = new ArrayList<User>();
		for (UserProfile userProfile : userProfiles) {
			User user = new User(userProfile);
			UserAccount account = temp.get(userProfile.getId());
			if (account != null) {
				user.setCreateTime(account.getCreateTime());
				user.setType(account.getType());
			}
			userList.add(user);
		}
		Page<User> userPage = new PageImpl<User>(userList, new PageRequest(pageNumber - 1, pageSize), page.getTotalElements());
		
		return userPage;
	}
	/**
	 * 获取用户列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<User> getUserList(int pageNumber, int pageSize) {
		Page<UserProfile> page = userProfileDao.findAll(new PageRequest(
				pageNumber - 1, pageSize));

		List<UserProfile> userProfiles = page.getContent();
		List<Long> ids = new ArrayList<Long>();
		for (UserProfile userProfile : userProfiles) {
			ids.add(userProfile.getId());
		}
		// 不知道这里有没有针对批量查询做优化的，如果没有的话，其实可以直接在上面循环直接做掉
		Iterable<UserAccount> userAccounts = userAccountDao.findAll(ids);
		Map<Long, UserAccount> temp = new HashMap<Long, UserAccount>();
		for (UserAccount userAccount : userAccounts) {
			temp.put(userAccount.getId(), userAccount);
		}

		List<User> users = new ArrayList<User>();
		for (UserProfile userProfile : userProfiles) {
			User user = new User(userProfile);
			UserAccount account = temp.get(userProfile.getId());
			if (account != null) {
				user.setCreateTime(account.getCreateTime());
				user.setType(account.getType());
			}
			users.add(user);
		}
		return users;
	}
	/**
	 * 查询单个用户Account信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserAccount getUserAccount(Long userId) {
		return userAccountDao.findOne(userId);
	}
	/**
	 * 创建新用户
	 * 
	 * @param account
	 * @return
	 */
	public void createUser(UserAccount account) {
		UserProfile profile = new UserProfile();
		profile.setId(account.getId());
		this.createUser(account, profile);
	}

	/**
	 * 创建新用户
	 * 
	 * @param account
	 */
	public boolean createUser(UserAccount account, UserProfile profile) {
		if (profile == null)
			throw new ServiceException("不能使用空的用户基本信息");
		if (StringUtils.isNotBlank(account.getPlainPassword())) {
			encryptPassword(account);
		}

		account.setCreateTime(clock.getCurrentDate());
		userAccountDao.save(account);

		profile.setId(account.getId());
		userProfileDao.save(profile);
		
//		this.createUserDir(account.getId());
		return true;
	}

	/**
	 * 删除用户
	 * 
	 * @param userId
	 */
	public void deleteUser(Long userId) {
		userAccountDao.delete(userId);
		userProfileDao.delete(userId);
//		this.deleteUserDir(userId);
	}

	/**
	 * 更新用户Profile信息
	 * 
	 * @param profle
	 */
	public void updateUserProfile(UserProfile profile) {
		if (profile.getId() <= 0)
			return;
		UserProfile oldProfile = userProfileDao.findOne(profile.getId());
		if (oldProfile == null)
			return;
		oldProfile.setAvatar(profile.getAvatar());
		oldProfile.setEmail(profile.getEmail());
		oldProfile.setNickname(profile.getNickname());
		oldProfile.setRealname(profile.getRealname());
		oldProfile.setTelephone(profile.getTelephone());
		oldProfile.setCompany(profile.getCompany());
		userProfileDao.save(oldProfile);
	}

	/**
	 * 更新用户Account信息
	 * 
	 * @param account
	 */
	public void updateUserAccount(UserAccount account) {
		if (StringUtils.isNotBlank(account.getPlainPassword())) {
			encryptPassword(account);
		}
		userAccountDao.save(account);
	}

	/**
	 * 密码加密
	 * 
	 * @param account
	 */
	protected void encryptPassword(UserAccount account)  {
		try {
			byte[] salt = Digests.generateSalt(SALT_SIZE);
			account.setSalt(Encodes.encodeHex(salt));

			byte[] hashPassword = Digests.sha1(
					account.getPlainPassword().getBytes(HASH_CHARSET), salt,
					HASH_INTERATIONS);
			account.setPassword(Encodes.encodeHex(hashPassword));
		} catch (UnsupportedEncodingException e) {
			logger.warn("Unsupported Encode Exception for plain password {}", account.getPlainPassword());
			throw new ServiceException("密码加密错误");
		}
	}
	
}
