package com.emotiv.iedk;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.*;

public interface IEegData extends Library {
	IEegData INSTANCE = (IEegData) Native.loadLibrary("edk", IEegData.class);


	//! Initializes EmoEngine instance which reads data from a pre-recorded session file.
	/*!
	 *  \remark Only available in Insight SDK Premium Edition.
	 *  \param strFilePath       - File path to the pre-recorded EEG data file.
	 *  \param strMotionFilePath - File path to the pre-recorded motion data file.
	 *  \return EDK_ERROR_CODE
	 *                     - EDK_OK if an instance is created successfully
	*/
	int IEE_EngineLocalConnect(String strEegFilePath, String strMotionFilePath);	
	

	// ! Returns a handle to memory that can hold data. This handle can be
	// reused by the caller to retrieve subsequent data.
	/*
	 * ! \return Pointer
	 */
	Pointer IEE_DataCreate();

	
	// ! Frees memory referenced by a data handle.
	/*
	 * ! \param hData  - a handle returned by IEE_DataCreate()
	 */
	void IEE_DataFree(Pointer hData);

	
	// ! Updates the content of the data handle to point to new data since the
	// last call
	/*
	 * ! \param userId  - user ID 
	 *   \param hData   - a handle returned by IEE_DataCreate() 
	 *   \return EDK_ERROR_CODE 
	 *                  - EDK_ERROR_CODEEDK_OK if successful
	 */
	int IEE_DataUpdateHandle(int userId, Pointer hData);

	
	// ! Extracts data from the data handle
	/*
	 * ! \param hData               - a handle returned by IEE_DataCreate() 
	 *   \param channel             - channel that you are interested in \param buffer - pre-allocated buffer
	 *   \param bufferSizeInSample  - size of the pre-allocated buffer 
	 *   \return 
	 *                              - EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 */
	int IEE_DataGet(Pointer hData, int channel, double[] buffer,
			int bufferSizeInSample);
	

	// ! Returns number of sample of data stored in the data handle
	/*
	 * ! \param hData       - a handle returned by IEE_DataCreate() 
	 *   \param nSampleOut  - receives the number of sample of data stored in teh data handle 
	 *   \return 
	 *                      - EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 */
	int IEE_DataGetNumberOfSample(Pointer hData, IntByReference nSampleOut);
	

	// ! Sets the size of the data buffer. The size of the buffer affects how
	// frequent IEE_DataUpdateHandle() needs to be called to prevent data loss.
	/*
	 * ! \param bufferSizeInSec - buffer size in second 
	 *   \return 
	 *                          - EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 */
	int IEE_DataSetBufferSizeInSec(float bufferSizeInSec);
	

	// ! Returns the size of the data buffer
	/*
	 * ! \param pBufferSizeInSecOut - receives the size of the data buffer
	 *   \return 
	 *                              - EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if successful
	 */
	int IEE_DataGetBufferSizeInSec(FloatByReference pBufferSizeInSecOut);
	

	// ! Controls acquisition of data from EmoEngine (which is off by default).
	/*
	 * ! \param userId - user ID 
	 *   \param enable - If true, then enables data acquisition - If false, then disables data acquisition 
	 *   \return 
	 *                 - EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if the command succeeded
	 */
	int IEE_DataAcquisitionEnable(int userId, Boolean enable);
	

	// ! Returns whether data acquisition is enabled
	/*
	 * ! \param userId     - user ID 
	 *   \param pEnableOut - receives whether data acquisition is enabled 
	 *   \return 
	 *                     - EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if the command succeeded
	 */
	int IEE_DataAcquisitionIsEnabled(int userId, IntByReference pEnableOut);
	

	// ! Sets sychronization signal
	/*
	 * ! \param userId - user ID 
	 *   \param signal - value of the sychronization signal 
	 *   \return 
	 *                 - EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if the command succeeded
	 */
	int IEE_DataSetSychronizationSignal(int userId, int signal);

	// ! Sets marker
	/*
	 * ! \param userId - user ID 
	 *   \param marker - value of the marker 
	 *   \return
	 *                 - EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if the command succeeded
	 */
	int IEE_DataSetMarker(int userId, int marker);
	

	// ! Gets sampling rate
	/*
	 * ! \param userId          - user ID 
	 *   \param samplingRateOut - receives the sampling rate 
	 *   \return 
	 *                          - EDK_ERROR_CODE - EDK_ERROR_CODEEDK_OK if the command succeeded
	 */
	int IEE_DataGetSamplingRate(int userId, IntByReference samplingRateOut);

}
