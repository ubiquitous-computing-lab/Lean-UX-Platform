package com.emotiv.iedk;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.*;

public interface PerformanceMetrics extends Library {
    PerformanceMetrics INSTANCE = (PerformanceMetrics) Native.loadLibrary("edk", PerformanceMetrics.class);

    //! PerformanceMetric emotional type enumerator
    /**
     * PerformanceMetric type enumerator
     */
     
    public enum IEE_PerformanceMetricAlgo_t {
        PM_EXCITEMENT(0x0001),
        PM_RELAXATION(0x0002),
        PM_STRESS(0x0004),
        PM_ENGAGEMENT(0x0008),
        PM_INTEREST(0x0010),
        PM_FOCUS(0x0020);

        private int bit;

        IEE_PerformanceMetricAlgo_t(int bitNumber) {
            bit = bitNumber;
        }

        public int ToInt() {
            return (bit);
        }
    } 

    // ! Returns the long term excitement level of the user
    /*
     * \param state - EmoStateHandle
     * 
     * \return excitement level (0.0 to 1.0)
     *
     * \sa IS_PerformanceMetricGetExcitementShortTermScore
     */
     float IS_PerformanceMetricGetExcitementLongTermScore(Pointer state);


    // ! Returns short term excitement level of the user
    /*
     * \param state - EmoStateHandle
     * 
     * \return excitement level (0.0 to 1.0)
     *
     * \sa IS_PerformanceMetricGetExcitementLongTermScore
     */
     float IS_PerformanceMetricGetInstantaneousExcitementScore(Pointer state);


    // ! Query whether the signal is too noisy for PerformanceMetric detection to be active
    /*
     * \param state - EmoStateHandle
     * \param type  - PerformanceMetric detection type
     * 
     * \return detection state (0: Not Active, 1: Active)
     *
     * \sa IEE_PerformanceMetricAlgo_t
     */
     int IS_PerformanceMetricIsActive(Pointer state, int type);


    // ! Returns relaxation level of the user
    /*
     * \param state - EmoStateHandle     
     * 
     * \return relaxation level (0.0 to 1.0)
     *
     * \sa IEE_PerformanceMetricAlgo_t
     */
     float IS_PerformanceMetricGetRelaxationScore(Pointer state);


    // ! Returns stress level of the user
    /*
     * \param state - EmoStateHandle
     * 
     * \return stress level (0.0 to 1.0)
     */
     float IS_PerformanceMetricGetStressScore(Pointer state);


    // ! Returns engagement/boredom level of the user
    /*
     * \param state - EmoStateHandle  
     * 
     * \return engagement/boredom level (0.0 to 1.0)
     *
     */
     float IS_PerformanceMetricGetEngagementBoredomScore(Pointer state);


    // ! Returns interest level of the user
    /*
     * \param state - EmoStateHandle  
     * 
     * \return interest level (0.0 to 1.0)
     *
     */
     float IS_PerformanceMetricGetInterestScore(Pointer state);


    // ! Returns focus level of the user
    /*
     * \param state - EmoStateHandle  
     * 
     * \return focus level (0.0 to 1.0)
     *
     */
     float IS_PerformanceMetricGetFocusScore(Pointer state);


    // ! Returns short term excitement model parameters
    /*
     * \param state - EmoStateHandle  
     * \param rawScore             - return raw score
     * \param minScale, maxScale   - return scale range
     */
     void IS_PerformanceMetricGetInstantaneousExcitementModelParams(Pointer state,
                                                                     DoubleByReference rawScore,
                                                                     DoubleByReference minScale,
                                                                     DoubleByReference maxScale);


    // ! Returns Relaxation model parameters
    /*
     * \param state                - EmoStateHandle
     * \param rawScore             - return raw score
     * \param minScale, maxScale   - return scale range
     */
     void IS_PerformanceMetricGetRelaxationModelParams(Pointer state,
                                                       DoubleByReference rawScore,
                                                       DoubleByReference minScale,
                                                       DoubleByReference maxScale);


    // ! Returns EngagementBoredom model parameters
    /*
     * \param state                - EmoStateHandle
     * \param rawScore             - return raw score
     * \param minScale, maxScale   - return scale range
     */
     void IS_PerformanceMetricGetEngagementBoredomModelParams(Pointer state,
                                                              DoubleByReference rawScore,
                                                              DoubleByReference minScale,
                                                              DoubleByReference maxScale);


    // ! Returns Stress model parameters
    /*
     * \param state                - EmoStateHandle
     * \param rawScore             - return raw score
     * \param minScale, maxScale   - return scale range
     */
     void IS_PerformanceMetricGetStressModelParams(Pointer state,
                                                   DoubleByReference rawScore,
                                                   DoubleByReference minScale,
                                                   DoubleByReference maxScale);


    // ! Returns Interest model parameters
    /*
     * \param state                - EmoStateHandle
     * \param rawScore             - return raw score
     * \param minScale, maxScale   - return scale range
     */
     void IS_PerformanceMetricGetInterestModelParams(Pointer state,
                                                     DoubleByReference rawScore,
                                                     DoubleByReference minScale,
                                                     DoubleByReference maxScale);

    // ! Returns Focus model parameters
    /*
     * \param state                - EmoStateHandle
     * \param rawScore             - return raw score
     * \param minScale, maxScale   - return scale range
     */
     void IS_PerformanceMetricGetFocusModelParams(Pointer state,
                                                  DoubleByReference rawScore,
                                                  DoubleByReference minScale,
                                                  DoubleByReference maxScale);


    // ! Check whether two states are with identical 'emotiv' state
    /*
     * \param a - EmoStateHandle
     * \param b - EmoStateHandle
     * \sa IS_PerformanceMetricEqual, IS_MentalCommandEqual, IS_EmoEngineEqual, IS_Equal
     */
     int IS_PerformanceMetricEqual(Pointer a, Pointer b);
}
