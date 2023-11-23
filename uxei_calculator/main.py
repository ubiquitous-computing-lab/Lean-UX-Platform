from example_data import *
from uxei.process_data import process_user_task_data, process_eeg_data, process_time_series_data, calculate_average_success_rate
from uxei.calculator import calculate_uxei


start_date= config.start_date
end_date = config.end_date
num_records= 10

dummy_emotions_data =emotions_data.generate_emotions_data(num_records, start_date, end_date)
dummy_eeg_data = egg_data.generate_eeg_data(num_records, start_date, end_date)

dummy_sentiment_data = sentiment_data.generate_sentiment_data(num_records, start_date, end_date)
dummy_user_task_data =task_data.generate_user_task_data(11, 4)
# print(dummy_user_task_data)
# print(dummy_sentiment_data)  
# print(dummy_eeg_data)  
# print(dummy_emotions_data) 

emotions = ["Joyful", "Angry", "Sad", "Neutral"]
# process the collected data
user_success_rates = process_user_task_data(dummy_user_task_data)
emotion_data = process_time_series_data(dummy_emotions_data, ["emotion"] + emotions)
sentiment_data = process_time_series_data(dummy_sentiment_data, ["sentiment"] + ["positive", "negative", "neutral"])
eeg_data = process_eeg_data(dummy_eeg_data)

print(f"user_success_rates {user_success_rates}")

# Calculate the average success rate
average_success_rate = calculate_average_success_rate(user_success_rates)

print(f"average_success_rate: {average_success_rate}")

five_star_rating = 5  # Example rating
# Calculate UXEI
uxei = calculate_uxei(emotion_data, sentiment_data,eeg_data, average_success_rate, five_star_rating)
print(f"emotion data : {emotion_data}, sentiment Data: {sentiment_data} , eeg_data : {eeg_data}")
print(f"User Experience Evaluation Index (UXEI): {uxei:.2f}")

