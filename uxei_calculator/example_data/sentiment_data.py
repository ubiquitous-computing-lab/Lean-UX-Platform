import random
import json
from .config import start_date, end_date, random_date

def generate_sentiment_data(num_records, start_date, end_date):
    sentiment_types = ["positive", "negative", "neutral"]
    sentiment_data = []

    for _ in range(num_records):
        sentiment_record = {
            "timestamp": random_date(start_date, end_date).isoformat(),
            "sentiment": random.choice(sentiment_types)
        }
        sentiment_data.append(sentiment_record)

    return json.dumps(sentiment_data, indent=4)