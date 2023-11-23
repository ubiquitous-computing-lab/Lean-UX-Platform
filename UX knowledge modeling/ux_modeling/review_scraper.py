from google_play_scraper import Sort, reviews
import pandas as pd

# Function to scrape reviews for a single app
def scrape_reviews(app_id, number_of_reviews):
    result, _ = reviews(
        app_id,
        lang='en',
        country='us',
        sort=Sort.MOST_RELEVANT,
        count=number_of_reviews
    )
    return pd.DataFrame(result)

# List of app IDs you want to scrape reviews for
app_ids = ['com.alibaba.aliexpresshd', 'com.canva.editor', 'tv.kidoodle.android']  # Replace with real App IDs

# Number of reviews per app
number_of_reviews = 100

# Loop over each app and collect reviews
all_reviews = pd.DataFrame()
for app_id in app_ids:
    app_reviews = scrape_reviews(app_id, number_of_reviews)
    app_reviews['app_id'] = app_id  # Add app_id to distinguish between apps
    all_reviews = pd.concat([all_reviews, app_reviews], ignore_index=True)

# Save to CSV
all_reviews.to_csv('dataset/multiple_app_reviews.csv', index=False)
print("Reviews successfully saved to multiple_app_reviews.csv")
