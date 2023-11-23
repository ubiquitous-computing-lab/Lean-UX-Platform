import numpy as np

def calculate_uxei(emotion_data, sentiment_data,eeg_data, success_rate, five_star_rating ):
    # Calculate emotion percentages
    total_emotions = sum(emotion_data.values())
    emotion_percentages = {k: (v / total_emotions) * 100 for k, v in emotion_data.items()}

    # Calculate sentiment percentages
    total_sentiments = sum(sentiment_data.values())
    sentiment_percentages = {k: (v / total_sentiments) * 100 for k, v in sentiment_data.items()}

    # Extracting EEG metrics
    avg_stress = eeg_data.get('Stress', 0)
    avg_interest = eeg_data.get('Interest', 0)
    avg_relaxation = eeg_data.get('Relaxation', 0)
    avg_focus = eeg_data.get('Focus', 0)
    avg_excitement = eeg_data.get('Excitement', 0)

    # Incorporate EEG data into UXEI
    # Here we assume that each EEG metric contributes equally to the UXEI
    eeg_score = np.mean([avg_stress, avg_interest, avg_relaxation, avg_focus, avg_excitement]) *100
    print(f"eeg_score: {eeg_score}")

    # Calculate weighted scores for each component
    # Adjust weights as per your analysis requirements
    emotion_weight = 0.25
    sentiment_weight = 0.25
    success_weight = 0.25
    eeg_weight = 0.25
    rating_weight = 0.25

    # Assuming 'Joyful' and 'positive' sentiments contribute to UXEI
    joyful_emotion_score = emotion_percentages.get('Joyful', 0)
    print(f"joyful_emotion_score: {joyful_emotion_score}")
    positive_sentiment_score = sentiment_percentages.get('positive', 0)
    print(f"positive_sentiment_score: {positive_sentiment_score}")

    # Final UXEI calculation
    uxei = (joyful_emotion_score * emotion_weight +
            positive_sentiment_score * sentiment_weight +
            success_rate * success_weight +
            eeg_score * eeg_weight + five_star_rating*rating_weight ) / 5

    return uxei