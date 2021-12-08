package com.emotiv.iedk;


public enum EdkErrorCode {
	EDK_OK(0x0000),
	// ! An internal error occurred
	EDK_UNKNOWN_ERROR(0x0001),

	// ! The contents of the buffer supplied to EE_SetUserProfile aren't a
	// valid, serialized EmoEngine profile.
	EDK_INVALID_PROFILE_ARCHIVE(0x0101),
	// ! Returned from EE_EmoEngineEventGetUserId if the event supplied contains
	// a base profile (which isn't associated with specific user).
	EDK_NO_USER_FOR_BASEPROFILE(0x0102),

	// ! The EmoEngine is unable to acquire EEG data for processing.
	EDK_CANNOT_ACQUIRE_DATA(0x0200),

	// ! The buffer supplied to the function isn't large enough
	EDK_BUFFER_TOO_SMALL(0x0300),
	// ! A parameter supplied to the function is out of range
	EDK_OUT_OF_RANGE(0x0301),
	// ! One of the parameters supplied to the function is invalid
	EDK_INVALID_PARAMETER(0x0302),
	// ! The parameter value is currently locked by a running detection and
	// cannot be modified at this time.
	EDK_PARAMETER_LOCKED(0x0303),
	// ! The current training action is not in the list of expected training
	// actions
	EDK_MC_INVALID_TRAINING_ACTION(0x0304),
	// ! The current training control is not in the list of expected training
	// controls
	EDK_MC_INVALID_TRAINING_CONTROL(0x0305),
	// ! One of the field in the action bits vector is invalid
	EDK_MC_INVALID_ACTIVE_ACTION(0x0306),
	// ! The current action bits vector contains more action types than it is
	// allowed
	EDK_MC_EXCESS_MAX_ACTIONS(0x0307),
	// ! A trained signature is not currently available for use - addition
	// actions (including neutral) may be required
	EDK_FE_NO_SIG_AVAILABLE(0x0308),
	// ! A filesystem error occurred that prevented the function from succeeding
	EDK_FILESYSTEM_ERROR(0x0309),

	// ! The user ID supplied to the function is invalid
	EDK_INVALID_USER_ID(0x0400),

	// ! The EDK needs to be initialized via EE_EngineConnect or
	// EE_EngineRemoteConnect
	EDK_EMOENGINE_UNINITIALIZED(0x0500),
	// ! The connection with a remote instance of the EmoEngine (made via
	// EE_EngineRemoteConnect) has been lost
	EDK_EMOENGINE_DISCONNECTED(0x0501),
	// ! The API was unable to establish a connection with a remote instance of
	// the EmoEngine.
	EDK_EMOENGINE_PROXY_ERROR(0x0502),

	// ! There are no new EmoEngine events at this time
	EDK_NO_EVENT(0x0600),

	// ! The gyro is not calibrated. Ask the user to stay still for at least
	// 0.5s
	EDK_GYRO_NOT_CALIBRATED(0x0700),

	// ! Operation failure due to optimization
	EDK_OPTIMIZATION_IS_ON(0x0800),

	// ! Reserved return value
    EDK_RESERVED1(0x0900),

	//! An internal error occurred.
    EDK_COULDNT_RESOLVE_PROXY(0x1001),

	EDK_COULDNT_RESOLVE_HOST(0x1002),

	EDK_COULDNT_CONNECT(0x1003),

	//! Profile created by EDK_SaveUserProfile() is existed in Emotiv Cloud.
	EDK_CLOUD_PROFILE_EXISTS(0x1010),

	//! The file uploaded to cloud is failed
	EDK_UPLOAD_FAILED(0x1011),

	//! The cloud user ID supplied to the function is invalid.
	EDK_INVALID_CLOUD_USER_ID(0x1020),

	//! The user ID supplied to the function is invalid
	EDK_INVALID_ENGINE_USER_ID(0x1021),

	//! The user ID supplied to the function dont login, call EDK_Login() first
	EDK_CLOUD_USER_ID_DONT_LOGIN(0x1022),

	//! The Emotiv Cloud needs to be initialized via EDK_Connect()
	EDK_EMOTIVCLOUD_UNINITIALIZED(0x1023), 


	EDK_FILE_EXISTS(0x2000),	

	//! The headset is not available to work
	EDK_HEADSET_NOT_AVAILABLE(0x2001),

	//! The headset is off
	EDK_HEADSET_IS_OFF(0x2002),

	//! Other session of saving is running
	EDK_SAVING_IS_RUNNING(0x2003),

	EDK_DEVICE_CODE_ERROR(0x2004),

	//! The license error. 
	EDK_LICENSE_ERROR(0x2010),  

	//! The license expried
	EDK_LICENSE_EXPIRED(0x2011),

	//! The license was not found
	EDK_LICENSE_NOT_FOUND(0x2012),

	//! The license is over quota
	EDK_OVER_QUOTA(0x2013),

	//! Debit number is invalid
	EDK_INVALID_DEBIT_ERROR(0x2014),

	//! Device list of the license is over
	EDK_OVER_DEVICE_LIST(0x2015),


	EDK_APP_QUOTA_EXCEEDED(0x2016),


	EDK_APP_INVALID_DATE(0x2017),

	//! Application register device number is exceeded. 
	EDK_LICENSE_DEVICE_LIMITED(0x2019),  

	//! The license registered with the device. 
	EDK_LICENSE_REGISTERED(0x2020),

	//! No license is activated
	EDK_NO_ACTIVE_LICENSE(0x2021),

	//! The license is not EEG data ouput
	EDK_LICENSE_NO_EEG(0x2022),

	//! The file was not found
	EDK_FILE_NOT_FOUND(0x2030),

	EDK_ACCESS_DENIED(0x2031);
		

	private int bit;

	EdkErrorCode(int bitNumber) {
		bit = bitNumber;
	}

	public int ToInt() {
		return (bit);
	}
}
