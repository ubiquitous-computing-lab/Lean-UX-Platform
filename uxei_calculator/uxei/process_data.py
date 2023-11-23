import json
import numpy as np

def process_time_series_data(json_data, data_type):
    # Load JSON data
    data = json.loads(json_data)

    # Aggregate data
    counts = {type: 0 for type in data_type}
    for record in data:
        counts[record[data_type[0]]] += 1
    return counts


def process_eeg_data(json_data):
    data = json.loads(json_data)
    eeg_aggregates = {key: [] for key in ["Stress", "Interest", "Relaxation", "Focus", "Excitement"]}

    for record in data:
        for key, value in record["eeg"].items():
            eeg_aggregates[key].append(value)

    # Calculate average values for each EEG metric
    eeg_averages = {key: np.mean(values) if values else 0 for key, values in eeg_aggregates.items()}
    return eeg_averages


def process_user_task_data(json_data):
    data = json.loads(json_data)
    user_success_rates = {}

    for user, tasks in data.items():
        total_tasks = len(tasks)
        successful_tasks = sum(1 for task, result in tasks.items() if result["Task_success"])
        success_rate = (successful_tasks / total_tasks) * 100
        user_success_rates[user] = success_rate

    return user_success_rates


def calculate_average_success_rate(user_success_rates):
    total_success_rate = sum(user_success_rates.values())
    number_of_users = len(user_success_rates)
    average_success_rate = total_success_rate / number_of_users if number_of_users > 0 else 0
    return average_success_rate