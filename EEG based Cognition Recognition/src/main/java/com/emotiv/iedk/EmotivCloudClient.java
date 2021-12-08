package com.emotiv.iedk;

import java.util.List;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.*;
import com.sun.jna.NativeLong;
import com.sun.jna.Structure;

public interface EmotivCloudClient extends Library {
	EmotivCloudClient INSTANCE = (EmotivCloudClient) Native.loadLibrary("edk", EmotivCloudClient.class);

	//! Profile types
    public enum profileType_t {
        TRAINING(0),
        EMOKEY(1);
        
        private int type;
        profileType_t(int val) {
        	type = val;
        }
        
        public int toInt() {
			return type;
		}
    } ;
    
    public static class profileVerInfo extends Structure{
        int version;
        char[] last_modified;
		@Override
		protected List getFieldOrder() {
			// TODO Auto-generated method stub
			return null;
		}
    }
    
    
    //! Initialize the connection to Emotiv Cloud Server
    /*!
     *  \return EDK_ERROR_CODE
     *              - true if connect successfully
     */
    int EC_Connect();


	//! Reconnection to Emotiv engine
	/*!
	 *  \return EDK_ERROR_CODE
	 *              - true if Reconnect successfully
	*/
	int EC_ReconnectEngine();


	//! Disconnection to Emotiv engine
	/*!
	 *   \return EDK_ERROR_CODE
	 *              - true if Reconnect successfully
	*/
	int EC_DisconnectEngine();

    
    //! Terminate the connection to Emotiv Cloud server
    /*!
     */
    int EC_Disconnect();

    
    //! Login Emotiv Cloud with EmotivID
    /*!
     *  To register a new EmotivID please visit https://id.emotivcloud.com/ .
     *  \param username  - username
     *  \param password  - password
     *  \return EDK_ERROR_CODE
     *              - true if login successfully
     */
    int EC_Login(String username, String password);

    
    //! Logout Emotiv Cloud
    /*
     *  \return EDK_ERROR_CODE
     *              - true if logout successfully
     */
    int EC_Logout(int userCloudID);

    
    //! Get user ID after login
    /*!
     *  \param userCloudID - return user ID for subsequence requests
     *  \return EDK_ERROR_CODE
     *              - true if fetched successfully
     */
    int EC_GetUserDetail(IntByReference userCloudID);

    
    //! Save user profile to Emotiv Cloud
    /*!
     *  \param userCloudID  - user ID from EC_GetUserDetail()
     *  \param engineUserID - user ID from current EmoEngine (first user will be 0)
     *  \param profileName  - profile name to be saved as
     *  \param ptype        - profile type
     *  \return EDK_ERROR_CODE
     *              - true if saved successfully
     */
    int EC_SaveUserProfile(int userCloudID, int engineUserID, String profileName, int ptype);
    
    
    //! Update user profile to Emotiv Cloud
    /*!
     *  \param userCloudID  - user ID from EC_GetUserDetail()
     *  \param engineUserID - user ID from current EmoEngine (first user will be 0)
     *  \param profileId    - profile ID to be updated, from EC_GetProfileId()
     *  \param profileName  - profile name to be saved as
     *  \return EDK_ERROR_CODE 
     *               - true if updated successfully
     */
    int EC_UpdateUserProfile(int userCloudID, int engineUserID, int profileId);
    
    
    //! Delete user profile from Emotiv Cloud
    /*!
     *  \param userCloudID  - user ID from EC_GetUserDetail()
     *  \param profileId    - profile ID to be deleted, from EC_GetProfileId()
     *  \return EDK_ERROR_CODE
     *                - true if updated successfully
     */
    int EC_DeleteUserProfile(int userCloudID, int profileId);

    
    //! Get profile ID of a user
    /*!
     *  \param userCloudID  - user ID from EC_GetUserDetail()
     *  \param profileName  - profile name to look for
     *  \param profileID    - profile id with name
     *  \return EDK_ERROR_CODE
     */
    int EC_GetProfileId(int userCloudID, String profileName, IntByReference profileID);
    

    //! Load profile from Emotiv Cloud
    /*!
     *  \remark Time to take to load a profile from Emotiv Cloud depends on network speed and profile size.
     *  \param userCloudID  - user ID from EC_GetUserDetail()
     *  \param engineUserID - user ID from current EmoEngine (first user will be 0)
     *  \param profileId    - profile ID to be loaded, from EC_GetProfileId()
     *  \param version      - version of profile to download (default: -1 for lastest version)
     *  \return EDK_ERROR_CODE
     *               - true if loaded successfully
     */
    int EC_LoadUserProfile(int userCloudID, int engineUserID, int profileId, int version);

    
    //! Update all the profile info from Emotiv Cloud
    /*!
     *  This function needs to be called first before calling EC_ProfileIDAtIndex(), EC_ProfileNameAtIndex(),
     *  EC_ProfileLastModifiedAtIndex(), EC_ProfileTypeAtIndex()
     *  \param userCloudID  - user ID from EC_GetUserDetail()
     *  \return int 
     *              - number of existing profiles (only latest version for each profile are counted)
     */
    int EC_GetAllProfileName(int userCloudID);

    
    //! Return the profile ID of a profile in cache
    /*!
     *  \param userCloudID  - user ID from EC_GetUserDetail()
     *  \param index        - index of profile (starts from 0)
     *  \return int 
     *               - profile ID
     */
    int EC_ProfileIDAtIndex(int userCloudID, int index);
    
    
    //! Return the profile name of a profile in cache
    /*! \param userCloudID  - user ID from EC_GetUserDetail()
     *  \param index        - index of profile (starts from 0)
     *  \return String 
     *               - profile name
     */
    String EC_ProfileNameAtIndex(int userCloudID, int index);
           
   
           
  //! Return the last modified timestamp of a profile in cache
    /*!
     * \param userCloudID  - user ID from EC_GetUserDetail()
     * \param index        - index of profile (starts from 0)
     * \return String - last modified timestamp
    */
    String EC_ProfileLastModifiedAtIndex(int userCloudID, int index);


    //! Return the type of a profile in cache
    /*!
     * \param userCloudID  - user ID from EC_GetUserDetail()
     * \param index        - index of profile (starts from 0)
     * \return profileFileType - profile type
    */
    int EC_ProfileTypeAtIndex(int userCloudID, int index);


    //! Donwload file Profile
    /*!
     * \param cloudUserID  - id of user
     * \param profileId
     * \param destPath
     * \param version      - default = -1 for download lastest version
     * \return EDK_ERROR_CODE
                        - EDK_OK if success
    */
    int EC_DownloadProfileFile(int cloudUserId, int profileId,
    		                   String destPath, int version);


    //! Upload file profile of user
    /*!
     * \param cloudUserID   - id of user
     * \param profileName
     * \param filePath
     * \param ptype
     * \return EDK_ERROR_CODE
     *                      - EDK_OK if success
    */
    int EC_UploadProfileFile(int cloudUserId, String profileName, 
    		                 String filePath, int ptype,
    		                 Boolean overwrite_if_exists);
   

    //! get lastest version of profile
    /*
     * \param profileID    - profileID
     * \param pVersionInfo - receives array of version Informations
     * \param nVersion     - receives number of versions
     * \return EDK_ERROR_CODE
     *                     - EDK_OK if success
    */
    int EC_GetLastestProfileVersions(int userID, int profileID, 
    		                         IntByReference nVersion);
}
