import random
import json
from .config import start_date, end_date, random_date

# Emotions categories
emotions = ["Joyful", "Angry", "Sad", "Neutral"]

def generate_emotions_data(num_records, start_date, end_date):

    # Generating dummy data
    dummy_data = []
    for _ in range(num_records):
        emotion_record = {
            "timestamp": random_date(start_date, end_date).isoformat(),
            "emotion": random.choice(emotions)
        }
        dummy_data.append(emotion_record)

    return json.dumps(dummy_data, indent=4)
    

