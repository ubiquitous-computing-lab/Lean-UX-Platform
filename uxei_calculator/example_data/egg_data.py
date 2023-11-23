import random
import json
from datetime import timedelta
from .config import start_date, end_date, random_date

# Function to generate random EEG data
def generate_eeg_data(num_records, start_date, end_date):
    eeg_types = ["Stress", "Interest", "Relaxation", "Focus", "Excitement"]
    eeg_data = []

    for _ in range(num_records):
        eeg_record = {
            "timestamp": random_date(start_date, end_date).isoformat(),
            "eeg": {eeg: random.uniform(0, 1) for eeg in eeg_types}  # Random values between 0 and 1
        }
        eeg_data.append(eeg_record)

    return json.dumps(eeg_data, indent=4)


