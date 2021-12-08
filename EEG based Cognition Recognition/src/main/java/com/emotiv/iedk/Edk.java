package com.emotiv.iedk;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.*;
import com.sun.jna.Structure;

public interface Edk extends Library {
	Edk INSTANCE = (Edk) Native.loadLibrary("edk", Edk.class);

	public enum IEE_FacialExpressionThreshold_t {
		FE_SENSITIVITY
	};

	// ! FacialExpression Suite training control enumerator
	public enum IEE_FacialExpressionTrainingControl_t {
		FE_NONE(0), 
		FE_START(1), 
		FE_ACCEPT(2), 
		FE_REJECT(3), 
		FE_ERASE(4), 
		FE_RESET(5);
		private int type;

		IEE_FacialExpressionTrainingControl_t(int val) {
			type = val;
		}

		public int toInt() {
			return type;
		}
	}

	// ! FacialExpression Suite signature type enumerator
	public enum IEE_FacialExpressionSignature_t {
		FE_SIG_UNIVERSAL, FE_SIG_TRAINED
	}

	// ! MentalCommand Suite training control enumerator
	public enum IEE_MentalCommandTrainingControl_t {
		MC_NONE(0), 
		MC_START(1), 
		MC_ACCEPT(2), 
		MC_REJECT(3), 
		MC_ERASE(4), 
		MC_RESET(5);

		private int type;

		IEE_MentalCommandTrainingControl_t(int val) {
			type = val;
		}

		public int getType() {
			return type;
		}
	}


	// ! MentalCommand Suite level enumerator
	// @@ This constant has been obsoleted
	public enum IEE_MentalCommandLevel_t {
		MC_LEVEL1(0), 
		MC_LEVEL2(1), 
		MC_LEVEL3(2), 
		MC_LEVEL4(3);
		
		private int level;

		IEE_MentalCommandLevel_t(int val) {
			level = val;
		}

		public int toInt() {
			return level;
		}
	}

	// ! EmoEngine event types
	public enum IEE_Event_t {
		IEE_UnknownEvent(0x0000), 
		IEE_EmulatorError(0x0001), 
		IEE_ReservedEvent(0x0002), 
		IEE_UserAdded(0x0010), 
		IEE_UserRemoved(0x0020), 
		IEE_EmoStateUpdated(0x0040), 
		IEE_ProfileEvent(0x0080), 
		IEE_MentalCommandEvent(0x0100), 
		IEE_FacialExpressionEvent(0x0200), 
		IEE_InternalStateChanged(0x0400), 
		IEE_AllEvent(0x07F0);

		private int bit;

		IEE_Event_t(int bitNumber) {
			bit = bitNumber;
		}

		public int ToInt() {
			return (bit);
		}
	}

	// ! FacialExpression-specific event types
	public enum IEE_FacialExpressionEvent_t {
		IEE_FacialExpressionNoEvent, 
		IEE_FacialExpressionTrainingStarted, 
		IEE_FacialExpressionTrainingSucceeded, 
		IEE_FacialExpressionTrainingFailed, 
		IEE_FacialExpressionTrainingCompleted, 
		IEE_FacialExpressionTrainingDataErased, 
		IEE_FacialExpressionTrainingRejected, 
		IEE_FacialExpressionTrainingReset
	}

	// ! MentalCommand-specific event types
	public enum IEE_MentalCommandEvent_t {
		IEE_MentalCommandNoEvent(0), 
		IEE_MentalCommandTrainingStarted(1), 
		IEE_MentalCommandTrainingSucceeded(2), 
		IEE_MentalCommandTrainingFailed(3), 
		IEE_MentalCommandTrainingCompleted(4), 
		IEE_MentalCommandTrainingDataErased(5), 
		IEE_MentalCommandTrainingRejected(6), 
		IEE_MentalCommandTrainingReset(7), 
		IEE_MentalCommandAutoSamplingNeutralCompleted(8), 
		IEE_MentalCommandSignatureUpdated(9);

		private int cType;

		IEE_MentalCommandEvent_t(int val) {
			cType = val;
		}

		public int getType() {
			return cType;
		}
	}
	
	public enum IEE_DataChannels_t {
		IED_COUNTER(0),            //!< Sample counter
		IED_INTERPOLATED(1),       //!< Indicate if data is interpolated
		IED_RAW_CQ(2),             //!< Raw contact quality value
		IED_AF3(3),                //!< Channel AF3
		IED_F7(4),                 //!< Channel F7
		IED_F3(5),                 //!< Channel F3
		IED_FC5(6),                //!< Channel FC5
		IED_T7(7),                 //!< Channel T7
		IED_P7(8),                 //!< Channel P7
		IED_Pz(9),                 //!< Channel Pz
		IED_O1(9),        		   //!< Channel O1 = Pz
		IED_O2(10),                //!< Channel O2
		IED_P8(11),                //!< Channel P8
		IED_T8(12),                //!< Channel T8
		IED_FC6(13),               //!< Channel FC6
		IED_F4(14),                //!< Channel F4
		IED_F8(15),                //!< Channel F8
		IED_AF4(16),               //!< Channel AF4
		IED_GYROX(17),             //!< Gyroscope X-axis
		IED_GYROY(18),             //!< Gyroscope Y-axis
		IED_TIMESTAMP(19),         //!< System timestamp
		IED_MARKER_HARDWARE(20),    //!< Marker from extender
		IED_ES_TIMESTAMP(21),      //!< EmoState timestamp
		IED_FUNC_ID(22),           //!< Reserved function id
		IED_FUNC_VALUE(23),        //!< Reserved function value
		IED_MARKER(24),            //!< Marker value from hardware
		IED_SYNC_SIGNAL(25);       //!< Synchronisation signal
					
		private int cType;

		IEE_DataChannels_t(int val) {
			cType = val;
		}

		public int getType() {
			return cType;
		}
	}
	
	
	public enum IEE_MotionDataChannel_t {
        IMD_COUNTER,        	//!< Sample counter
        IMD_GYROX,              //!< Gyroscope X-axis
        IMD_GYROY,              //!< Gyroscope Y-axis
        IMD_GYROZ,              //!< Gyroscope Z-axis
        IMD_ACCX,               //!< Accelerometer X-axis
        IMD_ACCY,               //!< Accelerometer Y-axis
        IMD_ACCZ,               //!< Accelerometer Z-axis
        IMD_MAGX,               //!< Magnetometer X-axis
        IMD_MAGY,               //!< Magnetometer Y-axis
        IMD_MAGZ,               //!< Magnetometer Z-axis
        IMD_TIMESTAMP;          //!< Timestamp of the motion data stream
	} ;

	// ! Input sensor description
	public static class InputSensorDescriptor_t extends Structure {
		EmoState.IEE_InputChannels_t channelId; // logical channel id
		int fExists;     // does this sensor exist on this headset model
		String pszLabel; // text label identifying this sensor
		double xLoc;     // x coordinate from center of head towards nose
		double yLoc;     // y coordinate from center of head towards ears
		double zLoc;     // z coordinate from center of head toward top of skull
		@Override
		protected List getFieldOrder() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public enum IEE_LicenseType_t {
	        IEE_EEG(1),      // Enable EEG data
	        IEE_PM(2),       // Enable Performance Metric detection   
	        IEE_EEG_PM(3);   // Enable EEG data and Performance Metric detection  
	        
	        private int cType;

		IEE_LicenseType_t(int val) {
			cType = val;
		}

		public int getType() {
			return cType;
		}
	} 
	
	public static class LicenseInfos_t extends Structure {
		
		public static class ByReference extends LicenseInfos_t implements Structure.ByReference {}
		
		public int scopes;            // license type
        public int date_from;         // epoch time  //maximum date : 07 Feb 2106
        public int date_to;           // epoch time
        public int soft_limit_date;   // need authorize the license, then your current quota will be reset to the debit number
        							  // if not, you can still use current quota to hard_limit_date

        public int  hard_limit_date;  // After this date. Your current quota will be reset to 0 and stop using the library
        public int  seat_count;       // number of seat
        public int  usedQuota;        // total session used
        public int  quota;            // total session in the license
        
		//@Override
		@Override
		protected List<String> getFieldOrder() {
			// TODO Auto-generated method stub
			return Arrays.asList(new String[] { "scopes", "date_from", "date_to", "soft_limit_date","hard_limit_date", "seat_count" , "usedQuota", 
					"quota"});
		}
	}
	
    public static class DebitInfos_t extends Structure {
        public static class ByReference extends DebitInfos_t implements Structure.ByReference {}
        public int remainingSessions;       // remaining session number of the license
        public int total_session_inMonth;   // the total number of session can be debitable in month, -1: yearly license.
        public int total_session_inYear;    // the total number of session can be debitable in year, -1: monthly license.
        
        //@Override
        @Override
        protected List<String> getFieldOrder() {
            // TODO Auto-generated method stub
            return Arrays.asList(new String[] { "remainingSessions", "total_session_inMonth", "total_session_inYear"});
        }
    }
	
	
	//! Check infos of the debit
    /*!    
     * \param licenseID - License key
     * \param debitInfo - debit information    
     * \return    EDK_ERROR_CODE
     *            EDK_OK
     * \sa IedkErrorCode.h
    */
    public int IEE_GetDebitInformation(String licenseID, DebitInfos_t.ByReference debitInfo);
    
    //! Check infos of the license
    /*!    
     * \param licenseInfo - License Information    
     * \return    EDK_ERROR_CODE
     *            EDK_OVER_QUOTA_IN_DAY
     *            EDK_OVER_QUOTA_IN_MONTH
     *            EDK_LICENSE_EXPIRED
     *            EDK_OVER_QUOTA
     *            EDK_ACCESS_DENIED
     *            EDK_LICENSE_ERROR
     *            EDK_NO_ACTIVE_LICENSE
     *            EDK_OK
     * \sa IedkErrorCode.h
    */
    public int IEE_LicenseInformation(LicenseInfos_t.ByReference licenseInfo);

  //! Authorize a license with a session debit number
    /*!
        \param licenseID - License key
        \param debitNum  - Indicates number of sessions will be deducted from license
        
        \return EDK_ERROR_CODE
                         - EDK_OK if the command succeeded

        \sa IedkErrorCode.h
    */
    int IEE_AuthorizeLicense(String licenseID, int debitNum);
    

	// ! Initializes the connection to EmoEngine. This function should be called
	// at the beginning of programs that make use of EmoEngine, most probably in
	// initialization routine or constructor.
	/*
	 * ! \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if a connection is
	 * established
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_EngineConnect(String strDevID);

	// ! Initializes the connection to a remote instance of EmoEngine.
	/*
	 * ! Blocking call
	 * 
	 * \param szHost - A null-terminated string identifying the hostname or IP
	 * address of the remote EmoEngine server \param port - The port number of
	 * the remote EmoEngine server - If connecting to the Emotiv Control Panel,
	 * use port 3008 - If connecting to the EmoComposer, use port 1726
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if a connection is
	 * established
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_EngineRemoteConnect(String szHost, short port, String strDevID);


	// ! Terminates the connection to EmoEngine. This function should be called
	// at the end of programs which make use of EmoEngine, most probably in
	// clean up routine or destructor.
	/*
	 * ! \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if disconnection is
	 * achieved
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_EngineDisconnect();

	// ! Controls the output of logging information from EmoEngine (which is off
	// by default). This should only be enabled if instructed to do so by Emotiv
	// developer support for the purposes of collecting diagnostic information.
	/*
	 * ! \param szFilename - The path of the logfile \param fEnable - If
	 * non-zero, then diagnostic information will be written to logfile. - If
	 * zero, then all diagnostic information is suppressed (default). \param
	 * nReserved - Reserved for future use.
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if the command succeeded
	 */
	int IEE_EnableDiagnostics(String szFilename, int fEnable, int nReserved);

	// ! Returns a handle to memory that can hold an EmoEngine event. This
	// handle can be reused by the caller to retrieve subsequent events.
	/*
	 * ! \return Pointer
	 */
	Pointer IEE_EmoEngineEventCreate();

	// ! Frees memory referenced by an event handle.
	/*
	 * ! \param hEvent - a handle returned by IEE_EmoEngineEventCreate() or
	 * IEE_ProfileEventCreate()
	 */
	void IEE_EmoEngineEventFree(Pointer hEvent);

	// ! Returns a handle to memory that can store an EmoState. This handle can
	// be reused by the caller to retrieve subsequent EmoStates.
	/*
	 * ! \return Pointer
	 */
	Pointer IEE_EmoStateCreate();

	// ! Frees memory referenced by an EmoState handle.
	/*
	 * ! \param hState - a handle returned by IEE_EmoStateCreate()
	 */
	void IEE_EmoStateFree(Pointer hState);

	// ! Returns the event type for an event already retrieved using
	// IEE_EngineGetNextEvent.
	/*
	 * ! \param hEvent - a handle returned by IEE_EmoEngineEventCreate()
	 * 
	 * \return int
	 */
	int IEE_EmoEngineEventGetType(Pointer hEvent);

	// ! Returns the MentalCommand-specific event type for an
	// IEE_MentalCommandEvent event already retrieved using
	// IEE_EngineGetNextEvent.
	/*
	 * ! \param hEvent - a handle returned by IEE_EmoEngineEventCreate()
	 * 
	 * \return int
	 */
	int IEE_MentalCommandEventGetType(Pointer hEvent);

	// ! Returns the FacialExpression-specific event type for an
	// IEE_FacialExpressionEvent event already retrieved using
	// IEE_EngineGetNextEvent.
	/*
	 * ! \param hEvent - a handle returned by IEE_EmoEngineEventCreate()
	 * 
	 * \return int
	 */
	int IEE_FacialExpressionEventGetType(Pointer hEvent);

	// ! Retrieves the user ID for IEE_UserAdded and IEE_UserRemoved events.
	/*
	 * ! \param hEvent - a handle returned by IEE_EmoEngineEventCreate() \param
	 * pUserIdOut - receives the user ID associated with the current event
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful.
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_EmoEngineEventGetUserId(Pointer hEvent, IntByReference pUserIdOut);

	// ! Copies an EmoState returned with a IEE_EmoStateUpdate event to memory
	// referenced by an Pointer.
	/*
	 * ! \param hEvent - a handle returned by IEE_EmoEngineEventCreate() and
	 * populated with IEE_EmoEngineGetNextEvent() \param hEmoState - a handle
	 * returned by IEE_EmoStateCreate
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful.
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_EmoEngineEventGetEmoState(Pointer hEvent, Pointer hEmoState);

	// ! Retrieves the next EmoEngine event
	/*
	 * ! Non-blocking call
	 * 
	 * \param hEvent - a handle returned by IEE_EmoEngineEventCreate()
	 * 
	 * \return EDK_ERROR_CODE <ul> <li> EDK_ERROR_CODEEDK_OK if a new event has
	 * been retrieved <li> EDK_ERROR_CODEEDK_NO_EVENT if no new events have been
	 * generated by EmoEngine </ul>
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_EngineGetNextEvent(Pointer hEvent);

	// ! Clear a specific EmoEngine event type or all events currently inside
	// the event queue. Event flags can be combined together as one argument
	// except IEE_UnknownEvent and IEE_EmulatorError.
	/*
	 * ! \param eventTypes - EmoEngine event type (int), multiple events can be
	 * combined such as (IEE_UserAdded | IEE_UserRemoved)
	 * 
	 * \return EDK_ERROR_CODE <ul> <li> EDK_ERROR_CODEEDK_OK if the events have
	 * been cleared from the queue <li> EDK_ERROR_CODEEDK_INVALID_PARAMETER if
	 * input event types are invalid </ul>
	 * 
	 * \sa int, edkErrorCode.h
	 */
	int IEE_EngineClearEventQueue(int eventTypes);

	// ! Retrieves number of active users connected to the EmoEngine.
	/*
	 * ! \param pNumUserOut - receives number of users
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful.
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_EngineGetNumUser(IntByReference pNumUserOut);

	// ! Sets the player number displayed on the physical input device
	// (currently the USB Dongle) that corresponds to the specified user
	/*
	 * ! \param userId - EmoEngine user ID \param playerNum - application
	 * assigned player number displayed on input device hardware (must be in the
	 * range 1-4) \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_SetHardwarePlayerDisplay(int userId, int playerNum);

	// ! Set threshold for FacialExpression algorithms
	/*
	 * ! \param userId - user ID \param algoName - FacialExpression algorithm
	 * type \param thresholdName - FacialExpression threshold type \param value
	 * - threshold value (min: 0 max: 1000)
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h, int, int
	 */
	int IEE_FacialExpressionSetThreshold(int userId, int algoName,
			int thresholdName, int value);

	// ! Get threshold from FacialExpression algorithms
	/*
	 * ! \param userId - user ID \param algoName - FacialExpression algorithm
	 * type \param thresholdName - FacialExpression threshold type \param
	 * pValueOut - receives threshold value
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h, int, int
	 */
	int IEE_FacialExpressionGetThreshold(int userId, int algoName,
			int thresholdName, IntByReference pValueOut);

	// ! Set the current facial expression for FacialExpression training
	/*
	 * ! Blocking call
	 * 
	 * \param userId - user ID \param action - which facial expression would
	 * like to be trained
	 * 
	 * \return EDK_ERROR_CODE - current status of EmoEngine. If the query is
	 * successful, EDK_ERROR_CODEOK.
	 * 
	 * \sa edkErrorCode.h, int
	 */
	int IEE_FacialExpressionSetTrainingAction(int userId, int action);

	// ! Set the control flag for FacialExpression training
	/*
	 * ! Blocking call
	 * 
	 * \param userId - user ID \param control - pre-defined control command
	 * 
	 * \return EDK_ERROR_CODE - current status of EmoEngine. If the query is
	 * successful, EDK_ERROR_CODEOK.
	 * 
	 * \sa edkErrorCode.h, int
	 */
	int IEE_FacialExpressionSetTrainingControl(int userId, int control);

	// ! Gets the facial expression currently selected for FacialExpression
	// training
	/*
	 * ! Blocking call
	 * 
	 * \param userId - user ID \param pActionOut - receives facial expression
	 * currently selected for training
	 * 
	 * \return EDK_ERROR_CODE - current status of EmoEngine. If the query is
	 * successful, EDK_ERROR_CODEOK.
	 * 
	 * \sa EDK_ERROR_CODE, int
	 */
	int IEE_FacialExpressionGetTrainingAction(int userId,
			IntByReference pActionOut);

	// ! Return the duration of a FacialExpression training session
	/*
	 * ! \param userId - user ID \param pTrainingTimeOut - receive the training
	 * time in ms
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_FacialExpressionGetTrainingTime(int userId,
			IntByReference pTrainingTimeOut);

	// ! Gets a list of the actions that have been trained by the user
	/*
	 * ! Blocking call
	 * 
	 * \param userId - user ID \param pTrainedActionsOut - receives a bit vector
	 * composed of int contants
	 * 
	 * \return EDK_ERROR_CODE - current status of EmoEngine. If the query is
	 * successful, EDK_ERROR_CODEOK.
	 * 
	 * \sa EDK_ERROR_CODE, int
	 */
	int IEE_FacialExpressionGetTrainedSignatureActions(int userId,
			NativeLongByReference pTrainedActionsOut);

	// ! Gets a flag indicating if the user has trained sufficient actions to
	// activate a trained signature
	/*
	 * !pfAvailableOut will be set to 1 if the user has trained FE_NEUTRAL and
	 * at least one other FacialExpression action. Otherwise, *pfAvailableOut ==
	 * 0.
	 * 
	 * Blocking call
	 * 
	 * \param userId - user ID \param pfAvailableOut - receives an int that is
	 * non-zero if a trained signature can be activated
	 * 
	 * \return EDK_ERROR_CODE - current status of EmoEngine. If the query is
	 * successful, EDK_ERROR_CODEOK.
	 * 
	 * \sa EDK_ERROR_CODE
	 */
	int IEE_FacialExpressionGetTrainedSignatureAvailable(int userId,
			IntByReference pfAvailableOut);

	// ! Configures the FacialExpression suite to use either the built-in,
	// universal signature or a personal, trained signature
	/*
	 * ! Note: FacialExpression defaults to use its universal signature. This
	 * function will fail if IEE_FacialExpressionGetTrainedSignatureAvailable
	 * returns false.
	 * 
	 * Blocking call
	 * 
	 * \param userId - user ID \param sigType - signature type to use
	 * 
	 * \return EDK_ERROR_CODE - current status of EmoEngine. If the query is
	 * successful, EDK_ERROR_CODEOK.
	 * 
	 * \sa EDK_ERROR_CODE, int
	 */
	int IEE_FacialExpressionSetSignatureType(int userId, int sigType);

	// ! Indicates whether the FacialExpression suite is currently using either
	// the built-in, universal signature or a trained signature
	/*
	 * ! Blocking call
	 * 
	 * \param userId - user ID \param pSigTypeOut - receives the signature type
	 * currently in use
	 * 
	 * \return EDK_ERROR_CODE - current status of EmoEngine. If the query is
	 * successful, EDK_ERROR_CODEOK.
	 * 
	 * \sa EDK_ERROR_CODE, int
	 */
	int IEE_FacialExpressionGetSignatureType(int userId,
			IntByReference pSigTypeOut);

	// @@ These APIs have been obsoleted
	// @@ Use IEE_MentalCommandSetActiveActions and
	// IEE_MentalCommandGetActiveActions instead

	// ! Set the current MentalCommand level and corresponding action types
	/*
	 * ! \param userId - user ID \param level - current level (min: 1, max: 4)
	 * \param level1Action - action type in level 1 \param level2Action - action
	 * type in level 2 \param level3Action - action type in level 3 \param
	 * level4Action - action type in level 4
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h, int, int
	 */
	int IEE_MentalCommandSetCurrentLevel(int userId, int level,
			int level1Action, int level2Action, int level3Action,
			int level4Action);

	// ! Query the current MentalCommand level and corresponding action types
	/*
	 * ! \param userId - user ID \param pLevelOut - current level (min: 1, max:
	 * 4) \param pLevel1ActionOut - action type in level 1 \param
	 * pLevel2ActionOut - action type in level 2 \param pLevel3ActionOut -
	 * action type in level 3 \param pLevel4ActionOut - action type in level 4
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h, int, int
	 */
	int IEE_MentalCommandGetCurrentLevel(int userId, IntByReference pLevelOut,
			IntByReference pLevel1ActionOut, IntByReference pLevel2ActionOut,
			IntByReference pLevel3ActionOut, IntByReference pLevel4ActionOut);

	// ! Set the current MentalCommand active action types
	/*
	 * ! \param userId - user ID \param activeActions - a bit vector composed of
	 * int contants
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h, int
	 */
	// int
	// IEE_MentalCommandSetActiveActions(int userId, NativeLong activeActions);

	int IEE_MentalCommandSetActiveActions(int userId, long activeActions);

	// ! Get the current MentalCommand active action types
	/*
	 * ! \param userId - user ID \param pActiveActionsOut - receive a bit vector
	 * composed of int contants
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h, int
	 */
	int IEE_MentalCommandGetActiveActions(int userId,
			NativeLongByReference pActiveActionsOut);

	// ! Return the duration of a MentalCommand training session
	/*
	 * ! \param userId - user ID \param pTrainingTimeOut - receive the training
	 * time in ms
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_MentalCommandGetTrainingTime(int userId,
			IntByReference pTrainingTimeOut);

	// ! Set the training control flag for MentalCommand training
	/*
	 * ! \param userId - user ID \param control - pre-defined MentalCommand
	 * training control
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h, int
	 */
	int IEE_MentalCommandSetTrainingControl(int userId, int control);

	// ! Set the type of MentalCommand action to be trained
	/*
	 * ! \param userId - user ID \param action - which action would like to be
	 * trained
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h, int
	 */
	int IEE_MentalCommandSetTrainingAction(int userId, int action);

	// ! Get the type of MentalCommand action currently selected for training
	/*
	 * ! \param userId - user ID \param pActionOut - action that is currently
	 * selected for training
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h, int
	 */
	int IEE_MentalCommandGetTrainingAction(int userId, IntByReference pActionOut);

	// ! Gets a list of the MentalCommand actions that have been trained by the
	// user
	/*
	 * ! Blocking call
	 * 
	 * \param userId - user ID \param pTrainedActionsOut - receives a bit vector
	 * composed of int contants
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h, int
	 */
	int IEE_MentalCommandGetTrainedSignatureActions(int userId,
			NativeLongByReference pTrainedActionsOut);

	// ! Gets the current overall skill rating of the user in MentalCommand
	/*
	 * ! Blocking call
	 * 
	 * \param userId - user ID \param pOverallSkillRatingOut - receives the
	 * overall skill rating [from 0.0 to 1.0]
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_MentalCommandGetOverallSkillRating(int userId,
			FloatByReference pOverallSkillRatingOut);

	// ! Gets the current skill rating for particular MentalCommand actions of
	// the user
	/*
	 * ! Blocking call
	 * 
	 * \param userId - user ID \param action - a particular action of int
	 * contant \param pActionSkillRatingOut - receives the action skill rating
	 * [from 0.0 to 1.0]
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h, int
	 */
	int IEE_MentalCommandGetActionSkillRating(int userId, int action,
			FloatByReference pActionSkillRatingOut);

	// ! Set the overall sensitivity for all MentalCommand actions
	/*
	 * ! \param userId - user ID \param level - sensitivity level of all actions
	 * (lowest: 1, highest: 7)
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_MentalCommandSetActivationLevel(int userId, int level);

	// ! Set the sensitivity of MentalCommand actions
	/*
	 * ! \param userId - user ID \param action1Sensitivity - sensitivity of
	 * action 1 (min: 1, max: 10) \param action2Sensitivity - sensitivity of
	 * action 2 (min: 1, max: 10) \param action3Sensitivity - sensitivity of
	 * action 3 (min: 1, max: 10) \param action4Sensitivity - sensitivity of
	 * action 4 (min: 1, max: 10)
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_MentalCommandSetActionSensitivity(int userId,
			int action1Sensitivity, int action2Sensitivity,
			int action3Sensitivity, int action4Sensitivity);

	// ! Get the overall sensitivity for all MentalCommand actions
	/*
	 * ! \param userId - user ID \param pLevelOut - sensitivity level of all
	 * actions (min: 1, max: 10)
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_MentalCommandGetActivationLevel(int userId, IntByReference pLevelOut);

	// ! Query the sensitivity of MentalCommand actions
	/*
	 * ! \param userId - user ID \param pAction1SensitivityOut - sensitivity of
	 * action 1 \param pAction2SensitivityOut - sensitivity of action 2 \param
	 * pAction3SensitivityOut - sensitivity of action 3 \param
	 * pAction4SensitivityOut - sensitivity of action 4
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_MentalCommandGetActionSensitivity(int userId,
			IntByReference pAction1SensitivityOut,
			IntByReference pAction2SensitivityOut,
			IntByReference pAction3SensitivityOut,
			IntByReference pAction4SensitivityOut);

	// ! Start the sampling of Neutral state in MentalCommand
	/*
	 * ! \param userId - user ID
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_MentalCommandStartSamplingNeutral(int userId);

	// ! Stop the sampling of Neutral state in MentalCommand
	/*
	 * ! \param userId - user ID
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_MentalCommandStopSamplingNeutral(int userId);

	// ! Enable or disable signature caching in MentalCommand
	/*
	 * ! \param userId - user ID \param enabled - flag to set status of caching
	 * (1: enable, 0: disable)
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_MentalCommandSetSignatureCaching(int userId, int enabled);

	// ! Query the status of signature caching in MentalCommand
	/*
	 * ! \param userId - user ID \param pEnabledOut - flag to get status of
	 * caching (1: enable, 0: disable)
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_MentalCommandGetSignatureCaching(int userId,
			IntByReference pEnabledOut);

	// ! Set the cache size for the signature caching in MentalCommand
	/*
	 * ! \param userId - user ID \param size - number of signatures to be kept
	 * in the cache (0: unlimited)
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_MentalCommandSetSignatureCacheSize(int userId, int size);

	// ! Get the current cache size for the signature caching in MentalCommand
	/*
	 * ! \param userId - user ID \param pSizeOut - number of signatures to be
	 * kept in the cache (0: unlimited)
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_MentalCommandGetSignatureCacheSize(int userId,
			IntByReference pSizeOut);

	// ! Returns a struct containing details about the specified EEG channel's
	// headset
	/*
	 * ! \param channelId - channel identifier (see EmoStateDll.h) \param
	 * pDescriptorOut - provides detailed sensor location and other info
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa EmoStateDll.h, edkErrorCode.h
	 */
	int IEE_HeadsetGetSensorDetails(int channelId,
			InputSensorDescriptor_t pDescriptorOut);

	// ! Returns the current hardware version of the headset and dongle for a
	// particular user
	/*
	 * ! \param userId - user ID for query \param pHwVersionOut - hardware
	 * version for the user headset/dongle pair. hiword is headset version,
	 * loword is dongle version.
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa EmoStateDll.h, edkErrorCode.h
	 */
	int IEE_HardwareGetVersion(int userId, NativeLongByReference pHwVersionOut);

	// ! Returns the current version of the Emotiv SDK software
	/*
	 * ! \param pszVersionOut - SDK software version in X.X.X.X format. Note:
	 * current beta releases have a major version of 0. \param nVersionChars -
	 * Length of byte buffer pointed to by pszVersion argument. \param
	 * pBuildNumOut - Build number. Unique for each release.
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa edkErrorCode.h
	 */
	int IEE_SoftwareGetVersion(String pszVersionOut, int nVersionChars,
			NativeLongByReference pBuildNumOut);

	// ! Returns the delta of the movement of the gyro since the previous call
	// for a particular user
	/*
	 * ! \param userId - user ID for query \param pXOut - horizontal
	 * displacement \param pYOut - vertical displacment
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa EmoStateDll.h \sa edkErrorCode.h
	 */
	int IEE_HeadsetGetGyroDelta(int userId, IntByReference pXOut,
			IntByReference pYOut);

	// ! Re-zero the gyro for a particular user
	/*
	 * ! \param userId - user ID for query
	 * 
	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 * 
	 * \sa EmoStateDll.h \sa edkErrorCode.h
	 */
	int IEE_HeadsetGyroRezero(int userId);
	
	
	//! Get headset settings from EPOC+ headset
	/*!
	 *  \param userId - user ID
	 *  \param EPOCmode	    - If 0, EPOC mode is EPOC.
	 *                      - If 1, EPOC mode is EPOC+.
	 *  \param eegRate      - If 0, EEG sample rate is 128Hz.
	 *                      - If 1, EEG sample rate is 256Hz.
	 *                      - If 2, no signals.
	 *  \param eegRes       - If 0, EEG Resolution is 14bit.
	 *                      - If 1, EEG Resolution is 16bit.
	 *                      - If 2, no signals.
	 *  \param memsRate     - If 0, MEMS sample rate is OFF.
	 *                      - If 1, MEMS sample rate is 32Hz.
	 *                      - If 2, MEMS sample rate is 64Hz.
	 *                      - If 3, MEMS sample rate is 128Hz.
	 *  \param memsRes      - If 0, MEMS Resolution is 12bit.
	 *                      - If 1, MEMS Resolution is 14bit.
	 *                      - If 2, MEMS Resolution is 16bit.
	 *                      - If 3, no signals.
	 *  \return EDK_ERROR_CODE
	 *                      - EDK_ERROR_CODE = EDK_OK if the command successful
	*/
	int IEE_GetHeadsetSettings(int userId, IntByReference EPOCmode, IntByReference eegRate, IntByReference eegRes, IntByReference memsRate, IntByReference memsRes);
	
	
	//! Set headset setting for EPOC+ headset
	/*!
	 *  \param userId       - user ID
	 *  \param EPOCmode     - If 0, then EPOC mode is EPOC.
	 *                      - If 1, then EPOC mode is EPOC+.
	 *  \param eegRate      - If 0, then EEG sample rate is 128Hz.
	 *                      - If 1, then EEG sample rate is 256Hz.
	 *  \param eegRes       - If 0, then EEG Resolution is 14bit.
	 *                      - If 1, then EEG Resolution is 16bit.
	 *  \param memsRate     - If 0, then MEMS sample rate is OFF.
	 *                      - If 1, then MEMS sample rate is 32Hz.
	 *                      - If 2, then MEMS sample rate is 64Hz.
	 *                      - If 3, then MEMS sample rate is 128Hz.
	 *  \param memsRes      - If 0, then MEMS Resolution is 12bit.
	 *                      - If 1, then MEMS Resolution is 14bit.
	 *                      - If 2, then MEMS Resolution is 16bit.
	 *  \return EDK_ERROR_CODE 
	 *                      - EDK_ERROR_CODE = EDK_OK if the command successful
	*/
	int IEE_SetHeadsetSettings(int userId, int EPOCmode, int eegRate, int eegRes, int memsRate, int memsRes);
	
	
	//! Return a handle to memory that can hold motion data.
    //  This handle can be reused by the caller to retrieve subsequent data.
    /*!
     * \return DataHandle
     */
	Pointer	IEE_MotionDataCreate();
    
    
    //! Free memory referenced by a data handle.
    /*!
     *  \param hData - a handle returned by IEE_MotionDataCreate()
     */
    void IEE_MotionDataFree(Pointer hData);
    
    
    //! Update the content of the data handle to point to new data since the last call
    /*!
     *  \param userId - user ID
     *  \param hData - a handle returned by IEE_MotionDataCreate()
     *  \return EDK_ERROR_CODE
     *                - EDK_OK if successful
     */
    int	IEE_MotionDataUpdateHandle(int userId, Pointer hData);
    
    
    //! Extract data of a particular channel from the data handle
    /*!
     *  \param hData - a handle returned by IEE_MotionDataCreate()
     *  \param channel - channel that you are interested in
     *  \param buffer - pre-allocated buffer
     *  \param bufferSizeInSample - size of the pre-allocated buffer
     *  \return EDK_ERROR_CODE
     *                 - EDK_OK if successful
     */
    int IEE_MotionDataGet(Pointer hData, int channel, double[] buffer, 
    		int bufferSizeInSample);
	
    //! Extract data of a list of channels from the data handle
    /*!
     *  \param hData - a handle returned by IEE_MotionDataCreate()
     *  \param channels - a list of channel that you are interested in
     *  \param nChannels - number of channels in the channel list
     *  \param buffer - pre-allocated 2 dimensional buffer, has to be nChannels x bufferSizeInSample
     *  \param bufferSizeInSample - size of the pre-allocated buffer for each channel
     *  \return EDK_ERROR_CODE
     *                  - EDK_OK if successful
     */
    int IEE_MotionDataGetMultiChannels(Pointer hData, int[] channels, int nChannels,
    		DoubleByReference[] buffer, int bufferSizeInSample);
    
    
    //! Return number of sample of motion data stored in the data handle
    /*!
     *  \param hData - a handle returned by IEE_MotionDataCreate()
     *  \param nSampleOut - receives the number of sample of data stored in the data handle
     *  \return EDK_ERROR_CODE
     *                 - EDK_OK if successful
     */
    int IEE_MotionDataGetNumberOfSample(Pointer hData, IntByReference nSampleOut);
    
    
    //! Set the size of the motion data buffer.
    /*!
     *   The size of the buffer affects how frequent IEE_MotionDataUpdateHandle() needs to be called to prevent data loss.
     *   \param bufferSizeInSec - buffer size in second
     *   \return EDK_ERROR_CODE
     *                 - EDK_OK if successful
     */
    int IEE_MotionDataSetBufferSizeInSec(float bufferSizeInSec);
    
    
    //! Return the size of the motion data buffer
    /*!
     *  \param pBufferSizeInSecOut - receives the size of the data buffer
     *  \return EDK_ERROR_CODE
     *                 - EDK_OK if successful
     */
    int IEE_MotionDataGetBufferSizeInSec(FloatByReference pBufferSizeInSecOut);
    
    
    //! Get sampling rate of the motion data stream
    /*!
     *  \param userId - user ID
     *  \param samplingRateOut - receives the sampling rate
     *  \return EDK_ERROR_CODE
     *                 - EDK_OK if successful
     */
    int IEE_MotionDataGetSamplingRate(int userId, IntByReference samplingRateOut);
    
    
    //! Get averge band power values for a channel
    /*!
     *  Return the average band power for a specific channel from the latest epoch with
     *  0.125 seconds (8 Hz) step size and 2 seconds window size.
     *  \param userId    - user ID
     *  \param channel   - channel that is interested in
     *  \param theta     - theta band value (4-8 Hz)
     *  \param alpha     - alpha band value (8-12 Hz)
     *  \param low_beta  - low-beta value (12-16 Hz)
     *  \param high_beta - high-beta value (16-25 Hz)
     *  \param gamma     - gamma value (25-45 Hz)
     *  \return EDK_ERROR_CODE
     *                   - EDK_OK if successful
    */
    int IEE_GetAverageBandPowers(int userId, int channel, DoubleByReference theta, DoubleByReference alpha, DoubleByReference low_beta, 
    		DoubleByReference high_beta, DoubleByReference gamma);

    
    //! Set the current windowing type for band power calculation
    /*!
     *  \param userId - user ID
     *  \param type   - windowing type enum from IEE_WindowingTypes
     *  \return EDK_ERROR_CODE
     *                   - EDK_OK if successful
    */
    int IEE_FFTSetWindowingType(int userId, int type);
    

    //! Get the current windowing type for band power calculation
    /*! 
     *  \param userId - user ID
     *  \param type   - windowing type enum from IEE_WindowingTypes (default: IEE_HANNING)
     *  \return EDK_ERROR_CODE
     *                - EDK_OK if successful
    */
    int IEE_FFTGetWindowingType(int userId, IntByReference type);
    
    
    // ! Returns a handle to memory that can hold a profile byte stream. This
 	// handle can be reused by the caller to retrieve subsequent profile bytes.
 	/*
 	 * ! \return Pointer
 	 */
 	Pointer IEE_ProfileEventCreate();
    
    // ! Loads an EmoEngine profile for the specified user.
 	/*
 	 * ! \param userId - user ID \param profileBuffer - pointer to buffer
 	 * containing a serialized user profile previously returned from EmoEngine.
 	 * \param length - buffer size (number of byte[]s)
 	 * 
 	 * \return EDK_ERROR_CODE - EDK_ERROR_CODE if the function succeeds in
 	 * loading this profile
 	 * 
 	 * \sa edkErrorCode.h
 	 */
 	int IEE_SetUserProfile(int userId, byte[] profileBuffer, int length);

 	// ! Returns user profile data in a synchronous manner.
 	/*
 	 * ! Fills in the event referred to by hEvent with an IEE_ProfileEvent event
 	 * that contains the profile data for the specified user.
 	 * 
 	 * \param userId - user ID \param hEvent - a handle returned by
 	 * IEE_EmoEngineEventCreate()
 	 * 
 	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
 	 * 
 	 * \sa edkErrorCode.h
 	 */
 	int IEE_GetUserProfile(int userId, Pointer hEvent);

 	// ! Returns a serialized user profile for a default user in a synchronous
 	// manner.
 	/*
 	 * ! Fills in the event referred to by hEvent with an IEE_ProfileEvent event
 	 * that contains the profile data for the default user
 	 * 
 	 * \param hEvent - a handle returned by IEE_EmoEngineEventCreate()
 	 * 
 	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
 	 * 
 	 * \sa edkErrorCode.h
 	 */
 	int IEE_GetBaseProfile(Pointer hEvent);

 	// ! Returns the number of bytes required to store a serialized version of
 	// the requested user profile.
 	/*
 	 * ! \param hEvt - an Pointer of type IEE_ProfileEvent \param
 	 * pProfileSizeOut - receives number of bytes required by the profile
 	 * 
 	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
 	 * 
 	 * \sa edkErrorCode.h
 	 */
 	int IEE_GetUserProfileSize(Pointer hEvt, IntByReference pProfileSizeOut);

 	// ! Copies a serialized version of the requested user profile into the
 	// caller's buffer.
 	/*
 	 * ! \param hEvt - an Pointer returned in a IEE_ProfileEvent event \param
 	 * destBuffer - pointer to a destination buffer \param length - the size of
 	 * the destination buffer in byte[]s
 	 * 
 	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
 	 * 
 	 * \sa edkErrorCode.h
 	 */
 	int IEE_GetUserProfileBytes(Pointer hEvt, byte[] destBuffer, int length);

 	// ! Loads a user profile from disk and assigns it to the specified user
 	/*
 	 * ! \param userID - a valid user ID \param szInputFilename -
 	 * platform-dependent filesystem path of saved user profile
 	 * 
 	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
 	 * 
 	 * \sa edkErrorCode.h
 	 */
 	int IEE_LoadUserProfile(int userID, String szInputFilename);

 	// ! Saves a user profile for specified user to disk
 	/*
 	 * ! \param userID - a valid user ID \param szOutputFilename -
 	 * platform-dependent filesystem path for output file
 	 * 
 	 * \return EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
 	 * 
 	 * \sa edkErrorCode.h
 	 */
 	int IEE_SaveUserProfile(int userID, String szOutputFilename);
}
