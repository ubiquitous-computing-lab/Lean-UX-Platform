import pandas as pd
import nltk
import string

# Download the necessary NLTK data
nltk.download('punkt')

def preprocess_text(text):
    """
    Preprocesses a single text string.

    Parameters:
    text (str): The text to preprocess.

    Returns:
    str: The preprocessed text.
    """
    # Convert text to lowercase
    text = text.lower()

    # Remove punctuation
    text = text.translate(str.maketrans('', '', string.punctuation))

    # Tokenize the text
    tokens = nltk.word_tokenize(text)

    # Reconstruct the text from tokens
    return ' '.join(tokens)

def preprocess_reviews(file_path):
    """
    Loads and preprocesses reviews from a CSV file.

    Parameters:
    file_path (str): Path to the CSV file containing reviews.

    Returns:
    DataFrame: A DataFrame containing the preprocessed reviews.
    """
    # Load reviews from CSV
    reviews_df = pd.read_csv(file_path)

    # Apply preprocessing to each review
    reviews_df['content'] = reviews_df['content'].apply(preprocess_text)

    return reviews_df

# Path to your CSV file
file_path = 'dataset/multiple_app_reviews.csv'

# Preprocess the reviews and store in a DataFrame
preprocessed_reviews = preprocess_reviews(file_path)

# Optionally, save the preprocessed reviews back to a CSV
preprocessed_reviews.to_csv('dataset/preprocessed_reviews.csv', index=False)
print("Preprocessed reviews saved to preprocessed_reviews.csv")
