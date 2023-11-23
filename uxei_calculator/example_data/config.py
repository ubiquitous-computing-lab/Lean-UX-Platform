import random
from datetime import datetime, timedelta

# Start and end dates for our time series
start_date = datetime(2023, 1, 1)
end_date = datetime(2023, 12, 31)


# Function to generate a random date
def random_date(start, end):
    return start + timedelta(
        seconds=random.randint(0, int((end - start).total_seconds())),
    )
