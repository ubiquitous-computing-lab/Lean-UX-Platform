package com.emotiv.iedk;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.*;

public interface EmoState extends Library {
	EmoState INSTANCE = (EmoState) Native.loadLibrary("edk", EmoState.class);

	public enum IEE_EmotivSuite_t {
		IEE_FacialExpression, 
		IEE_PerformanceMetric, 
		IEE_MentalCommand
	}

	/**
	 * FacialExpression facial expression type enumerator
	 */
	public enum IEE_FacialExpressionAlgo_t {

		FE_NEUTRAL(0x0001), 
		FE_BLINK(0x0002), 
		FE_WINK_LEFT(0x0004), 
		FE_WINK_RIGHT(0x0008), 
		FE_HORIEYE(0x0010), 
		FE_SURPRISE(0x0020), 
		FE_FROWN(0x0040), 
		FE_SMILE(0x0080), 
		FE_CLENCH(0x0100),

		FE_LAUGH(0x0200),
		FE_SMIRK_LEFT(0x0400),
		FE_SMIRK_RIGHT(0x0800);
		
		private int bit;

		IEE_FacialExpressionAlgo_t(int bitNumber) {
			bit = bitNumber;
		}

		public int ToInt() {
			return (bit);
		}

	}

	/**
	 * MentalCommand action type enumerator
	 */
	public enum IEE_MentalCommandAction_t {

		MC_NEUTRAL(0x0001), 
		MC_PUSH(0x0002), 
		MC_PULL(0x0004), 
		MC_LIFT(0x0008), 
		MC_DROP(0x0010), 
		MC_LEFT(0x0020), 
		MC_RIGHT(0x0040), 
		MC_ROTATE_LEFT(0x0080), 
		MC_ROTATE_RIGHT(0x0100), 
		MC_ROTATE_CLOCKWISE(0x0200), 
		MC_ROTATE_COUNTER_CLOCKWISE(0x0400), 
		MC_ROTATE_FORWARDS(0x0800), 
		MC_ROTATE_REVERSE(0x1000), 
		MC_DISAPPEAR(0x2000);

		private int bit;

		IEE_MentalCommandAction_t(int bitNumber) {
			bit = bitNumber;
		}

		public int ToInt() {
			return (bit);
		}

	}

	/**
	 * Wireless Signal Strength enumerator
	 */
	public enum IEE_SignalStrength_t {
		NO_SIGNAL, 
		BAD_SIGNAL, 
		GOOD_SIGNAL
	}

	// ! Logical input channel identifiers

	public enum IEE_InputChannels_t {
        IEE_CHAN_CMS(0),
		IEE_CHAN_DRL(1),
		IEE_CHAN_FP1(2),
        IEE_CHAN_AF3(3),
		IEE_CHAN_F7(4),
		IEE_CHAN_F3(5),
		IEE_CHAN_FC5(6),
        IEE_CHAN_T7(7),
		IEE_CHAN_P7(8),
        IEE_CHAN_Pz(9),
		IEE_CHAN_O1(9), // O1 = Pz
		IEE_CHAN_O2(10),
		IEE_CHAN_P8(11),
        IEE_CHAN_T8(12),
		IEE_CHAN_FC6(13),
		IEE_CHAN_F4(14),
		IEE_CHAN_F8(15),
        IEE_CHAN_AF4(16),
		IEE_CHAN_FP2(17);
		
		private int cType;

		IEE_InputChannels_t(int val) {
			cType = val;
		}

		public int getType() {
			return cType;
		}
	}

	// ! EEG Electrode Contact Quality enumeration
	/*
	 * ! Used to characterize the EEG signal reception or electrode contact for
	 * a sensor on the headset. Note that this differs from the wireless signal
	 * strength, which refers to the radio communication between the headset
	 * transmitter and USB dongle receiver.
	 */
	public enum IEE_EEG_ContactQuality_t {
		EEG_CQ_NO_SIGNAL, 
		EEG_CQ_VERY_BAD, 
		EEG_CQ_POOR, 
		EEG_CQ_FAIR, 
		EEG_CQ_GOOD
	}

	// ! Create EmoState handle.
	/*
	 * ! NOTE: THIS FUNCTION HAS BEEN DEPRECATED - please use IEE_EmoStateCreate
	 * instead.
	 * 
	 * IS_Init is called automatically after the creation of the EmoState
	 * handle. IS_Free must be called to free up resources during the creation
	 * of the EmoState handle.
	 * 
	 * \return the Pointer if succeeded
	 * 
	 * \sa IEE_EmoStateCreate, IS_Free, IS_Init
	 */
	Pointer IS_Create();

	// ! Free EmoState handle
	/*
	 * ! NOTE: THIS FUNCTION HAS BEEN DEPRECATED - please use IEE_EmoStateFree
	 * instead.
	 * 
	 * \param state - Pointer that was created by IS_Create function call
	 * 
	 * \sa IEE_EmoStateFree, IS_Create
	 */
	void IS_Free(Pointer state);

	// ! Initialize the EmoState into neutral state
	/*
	 * ! \param state - Pointer
	 * 
	 * \sa IS_Create, IS_Free
	 */
	void IS_Init(Pointer state);

	// ! Return the time since EmoEngine has been successfully connected to the
	// headset
	/*
	 * ! If the headset is disconnected from EmoEngine due to low battery or
	 * weak wireless signal, the time will be reset to zero.
	 * 
	 * \param state - Pointer
	 * 
	 * \return float - time in second
	 */
	float IS_GetTimeFromStart(Pointer state);

	// ! Return whether the headset has been put on correctly or not
	/*
	 * ! If the headset cannot not be detected on the head, then signal quality
	 * will not report any results for all the channels
	 * 
	 * \param state - EmoStatehandle
	 * 
	 * \return int - (1: On, 0: Off)
	 */
	int IS_GetHeadsetOn(Pointer state);

	// ! Query the number of channels of available sensor contact quality data
	/*
	 * ! \param state - Pointer \return number of channels for which contact
	 * quality data is available (int)
	 * 
	 * \sa IS_GetNumContactQualityChannels
	 */
	int IS_GetNumContactQualityChannels(Pointer state);

	// ! Query the contact quality of a specific EEG electrode
	/*
	 * ! \param state - Pointer \param electroIdx - The index of the electrode
	 * for query
	 * 
	 * \return int - Enumerated value that characterizes the EEG electrode
	 * contact for the specified input channel
	 * 
	 * \sa IS_GetContactQuality
	 */
	int IS_GetContactQuality(Pointer state, int electroIdx);

	// ! Query the contact quality of all the electrodes in one single call
	/*
	 * ! The contact quality will be stored in the array, contactQuality, passed
	 * to the function. The value stored in contactQuality[0] is identical to
	 * the result returned by IS_GetContactQuality(state, 0) The value stored in
	 * contactQuality[1] is identical to the result returned by
	 * IS_GetContactQuality(state, 1). etc. The ordering of the array is
	 * consistent with the ordering of the logical input channels in
	 * IEE_InputChannels_enum.
	 * 
	 * \param state - Pointer \param contactQuality - array of 32-bit float of
	 * size numChannels \param numChannels - size (number of floats) of the
	 * input array
	 * 
	 * \return Number of signal quality values copied to the contactQuality
	 * array.
	 * 
	 * \sa IS_GetContactQualityFromAllChannels
	 */
	int IS_GetContactQualityFromAllChannels(Pointer state,
			IntByReference contactQuality, int numChannels);

	// ! Query whether the user is blinking at the time the EmoState is
	// captured.
	/*
	 * ! \param state - Pointer
	 * 
	 * \return blink status (1: blink, 0: not blink)
	 */
	int IS_FacialExpressionIsBlink(Pointer state);

	// ! Query whether the user is winking left at the time the EmoState is
	// captured.
	/*
	 * ! \param state - Pointer
	 * 
	 * \return left wink status (1: wink, 0: not wink)
	 * 
	 * \sa IS_FacialExpressionIsRightWink
	 */
	int IS_FacialExpressionIsLeftWink(Pointer state);

	// ! Query whether the user is winking right at the time the EmoState is
	// captured.
	/*
	 * ! \param state - Pointer
	 * 
	 * \return right wink status (1: wink, 0: not wink)
	 * 
	 * \sa IS_FacialExpressionIsLeftWink
	 */
	int IS_FacialExpressionIsRightWink(Pointer state);

	// ! Query whether the eyes of the user are opened at the time the EmoState
	// is captured.
	/*
	 * ! \param state - Pointer
	 * 
	 * \return eye open status (1: eyes open, 0: eyes closed)
	 */
	int IS_FacialExpressionIsEyesOpen(Pointer state);

	// ! Query whether the user is looking up at the time the EmoState is
	// captured.
	/*
	 * ! \param state - Pointer
	 * 
	 * \return eyes position (1: looking up, 0: not looking up)
	 * 
	 * \sa IS_FacialExpressionIsLookingDown
	 */
	int IS_FacialExpressionIsLookingUp(Pointer state);

	// ! Query whether the user is looking down at the time the EmoState is
	// captured.
	/*
	 * ! \param state - Pointer
	 * 
	 * \return eyes position (1: looking down, 0: not looking down)
	 * 
	 * \sa IS_FacialExpressionIsLookingUp
	 */
	int IS_FacialExpressionIsLookingDown(Pointer state);
	
    // ! Query whether the user is looking left at the time the EmoState is 
	// captured with EPOC/EPOC+ headset.
	/*
	 * ! \remark Available EPOC/EPOC+ headset.
	 * ! \param state - Pointer
	 * 
	 * \return eyes position (1: looking right, 0: not looking right)
	 * 
	 * \sa IS_FacialExpressionIsLookingLeft
	 */
	int IS_FacialExpressionIsLookingLeft(Pointer state);

	// ! Query whether the user is looking down at the time the EmoState is
	// captured.
	/*
	 * ! \remark Available EPOC/EPOC+ headset.
	 * ! \param state - Pointer
	 * 
	 * \return eyes position (1: looking down, 0: not looking down)
	 * 
	 * \sa IS_FacialExpressionIsLookingUp
	 */
	int IS_FacialExpressionIsLookingRight(Pointer state);

	// ! The left and right eyelid state are stored in the parameter leftEye and rightEye
    //  respectively. They are floating point values ranging from 0.0 to 1.0.
    //  0.0 indicates that the eyelid is fully opened while 1.0 indicates that the
    //  eyelid is fully closed.
	/*
	 * ! \param state - EmoStatehandle
	 * ! \param leftEye - the left eyelid state (0.0 to 1.0)
     * ! \param rightEye - the right eyelid state (0.0 to 1.0)
	 * 
	 * \sa IS_FacialExpressionGetEyelidState
	 */
	void IS_FacialExpressionGetEyelidState(Pointer state,
			FloatByReference leftEye, FloatByReference rightEye);

	// ! Query the eyes position of the user
	/*
	 * ! The horizontal and vertical position of the eyes are stored in the
	 * parameter x and y respectively. They are floating point values ranging
	 * from -1.0 to 1.0.
	 * 
	 * For horizontal position, -1.0 indicates that the user is looking left
	 * while 1.0 indicates that the user is looking right.
	 * 
	 * For vertical position, -1.0 indicates that the user is looking down while
	 * 1.0 indicatest that the user is looking up.
	 * 
	 * This function assumes that both eyes have the same horizontal or vertical
	 * positions. (i.e. no cross eyes)
	 * 
	 * \param state - Pointer 
	 * \param x - the horizontal position of the eyes
	 * \param y - the veritcal position of the eyes
	 */
	void IS_FacialExpressionGetEyeLocation(Pointer state, FloatByReference x,
			FloatByReference y);

	// ! Returns the Surprise extent of the user (Obsolete function)
	/*
	 * ! \param state - Pointer
	 * 
	 * \return Surprise extent value (0.0 to 1.0)
	 * 
	 * \sa IS_FacialExpressionGetUpperFaceAction,
	 * IS_FacialExpressionGetUpperFaceActionPower
	 */
	float IS_FacialExpressionGetSurpriseExtent(Pointer state);

	// ! Returns the smile extent of the user (Obsolete function)
	/*
	 * ! \param state - EmoStatehandle
	 * 
	 * \return smile extent value (0.0 to 1.0)
	 * 
	 * \sa IS_FacialExpressionGetLowerFaceAction,
	 * IS_FacialExpressionGetLowerFaceActionPower
	 */
	float IS_FacialExpressionGetSmileExtent(Pointer state);

	// ! Returns the clench extent of the user (Obsolete function)
	/*
	 * ! \param state - EmoStatehandle
	 * 
	 * \return clench extent value (0.0 to 1.0)
	 * 
	 * \sa IS_FacialExpressionGetLowerFaceAction,
	 * IS_FacialExpressionGetLowerFaceActionPower
	 */
	float IS_FacialExpressionGetClenchExtent(Pointer state);

	// ! Returns the detected upper face FacialExpression action of the user
	/*
	 * ! \param state - EmoStatehandle
	 * 
	 * \return pre-defined FacialExpression action types
	 * 
	 * \sa IS_FacialExpressionGetUpperFaceActionPower
	 */
	int IS_FacialExpressionGetUpperFaceAction(Pointer state);

	// ! Returns the detected upper face FacialExpression action power of the
	// user
	/*
	 * ! \param state - EmoStatehandle
	 * 
	 * \return power value (0.0 to 1.0)
	 * 
	 * \sa IS_FacialExpressionGetUpperFaceAction
	 */
	float IS_FacialExpressionGetUpperFaceActionPower(Pointer state);

	// ! Returns the detected lower face FacialExpression action of the user
	/*
	 * ! \param state - EmoStatehandle
	 * 
	 * \return pre-defined FacialExpression action types
	 * 
	 * \sa IS_FacialExpressionGetLowerFaceActionPower
	 */
	int IS_FacialExpressionGetLowerFaceAction(Pointer state);

	// ! Returns the detected lower face FacialExpression action power of the
	// user
	/*
	 * ! \param state - EmoStatehandle
	 * 
	 * \return power value (0.0 to 1.0)
	 * 
	 * \sa IS_FacialExpressionGetLowerFaceAction
	 */
	float IS_FacialExpressionGetLowerFaceActionPower(Pointer state);

	// ! Query whether the signal is too noisy for FacialExpression detection to
	// be active
	/*
	 * ! \param state - Pointer \param type - FacialExpression detection type
	 * 
	 * \return detection state (0: Not Active, 1: Active)
	 * 
	 * \sa int
	 */
	int IS_FacialExpressionIsActive(Pointer state, int type);

	// ! Returns the detected MentalCommand action of the user
	/*
	 * ! \param state - Pointer
	 * 
	 * \return MentalCommand action type
	 * 
	 * \sa int, IS_MentalCommandGetCurrentActionPower
	 */
	int IS_MentalCommandGetCurrentAction(Pointer state);

	// ! Returns the detected MentalCommand action power of the user
	/*
	 * ! \param state - Pointer
	 * 
	 * \return MentalCommand action power (0.0 to 1.0)
	 * 
	 * \sa IS_MentalCommandGetCurrentAction
	 */
	float IS_MentalCommandGetCurrentActionPower(Pointer state);

	// ! Query whether the signal is too noisy for MentalCommand detection to be
	// active
	/*
	 * ! \param state - Pointer
	 * 
	 * \return detection state (0: Not Active, 1: Active)
	 */
	int IS_MentalCommandIsActive(Pointer state);

	// ! Query of the current wireless signal strength
	/*
	 * ! \param state - Pointer
	 * 
	 * \return wireless signal strength [No Signal, Bad, Fair, Good, Excellent].
	 * 
	 * \sa int
	 */
	int IS_GetWirelessSignalStatus(Pointer state);

	// ! Clone Pointer
	/*
	 * ! \param a - Destination of Pointer \param b - Source of Pointer
	 * 
	 * \sa IS_Create
	 */
	void IS_Copy(Pointer a, Pointer b);

	// ! Check whether two states are with identical FacialExpression state,
	// i.e. are both state representing the same facial expression
	/*
	 * ! \param a - Pointer \param b - Pointer
	 * 
	 * \return 1: Equal, 0: Different
	 * 
	 * \sa IS_PerformanceMetricEqual, IS_MentalCommandEqual, IS_EmoEngineEqual,
	 * IS_Equal
	 */
	int IS_FacialExpressionEqual(Pointer a, Pointer b);

	// ! Check whether two states are with identical MentalCommand state
	/*
	 * ! \param a - Pointer \param b - Pointer
	 * 
	 * \return 1: Equal, 0: Different
	 * 
	 * \sa IS_PerformanceMetricEqual, IS_FacialExpressionEqual,
	 * IS_EmoEngineEqual, IS_Equal
	 */
	int IS_MentalCommandEqual(Pointer a, Pointer b);

	// ! Check whether two states are with identical EmoEngine state.
	/*
	 * ! This function is comparing the time since EmoEngine start, the wireless
	 * signal strength and the signal quality of different channels
	 * 
	 * \param a - Pointer \param b - Pointer
	 * 
	 * \return 1: Equal, 0: Different
	 * 
	 * \sa IS_FacialExpressionEqual,
	 * IS_MentalCommandEqual, IS_Equal
	 */
	int IS_EmoEngineEqual(Pointer a, Pointer b);

	// ! Check whether two Pointers are identical
	/*
	 * ! \param a - Pointer \param b - Pointer
	 * 
	 * \return 1: Equal, 0: Different
	 * 
	 * \IS_FacialExpressionEqual,
	 * IS_EmoEngineEqual
	 */
	int IS_Equal(Pointer a, Pointer b);

	// ! Get the level of charge remaining in the headset battery
	/*
	 * ! \param state - Pointer \param chargeLevel - the current level of charge
	 * in the headset battery \param maxChargeLevel - the maximum level of
	 * charge in the battery
	 */
	void IS_GetBatteryChargeLevel(Pointer state, IntByReference chargeLevel, 
			IntByReference maxChargeLevel);
	
}
