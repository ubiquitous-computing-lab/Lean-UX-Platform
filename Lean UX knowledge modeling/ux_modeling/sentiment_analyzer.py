import pandas as pd
from transformers import pipeline

def analyze_sentiment_transformers(data):
    """
    Analyzes the sentiment of reviews using a transformer-based model.

    Parameters:
    data (DataFrame): A DataFrame containing the preprocessed reviews.

    Returns:
    DataFrame: The original DataFrame with an added sentiment column.
    """
    # Initialize the sentiment-analysis pipeline
    sentiment_pipeline = pipeline("sentiment-analysis")

    # Function to get sentiment
    def get_sentiment(text):
        return sentiment_pipeline(text)[0]

    # Apply sentiment analysis
    data['sentiment'] = data['content'].apply(lambda x: get_sentiment(x)['label'])

    return data

# Load preprocessed data
preprocessed_reviews = pd.read_csv('dataset/preprocessed_reviews.csv')

# Analyze sentiment using transformers
transformer_sentiment_reviews = analyze_sentiment_transformers(preprocessed_reviews)
transformer_sentiment_reviews.to_csv('dataset/transformer_sentiment_reviews.csv', index=False)
print("Sentiment analysis with transformer model completed and saved to transformer_sentiment_reviews.csv")
