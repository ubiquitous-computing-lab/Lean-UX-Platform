from bertopic import BERTopic
import pandas as pd

# Load your dataset containing preprocessed reviews
preprocessed_reviews = pd.read_csv('dataset/preprocessed_reviews.csv')

# Initialize BERTopic
topic_model = BERTopic()

# Fit the model on your dataset
topics, _ = topic_model.fit_transform(preprocessed_reviews['content'])

# Define your UX domain labels and corresponding keywords
ux_labels = {
    "Usability": ["easy", "intuitive", "simple", "use"],
    "Functionality": ["feature", "functional", "capability", "work"],
    "Aesthetics": ["design", "look", "beautiful", "style"],
    # ... add more labels as needed
}

# Function that assigns labels based on the words in a topic
def assign_ux_labels(topic_words):
    for label, keywords in ux_labels.items():
        if any(keyword in topic_words for keyword in keywords):
            return label
    return "Other"

# Create a dictionary to hold topic numbers and their assigned labels
topic_labels = {}

# Get the most representative words for each topic
for topic_num in set(topics):  # Ensure that we only get unique topics
    if topic_num == -1:  # Skip the outlier topic
        continue
    topic_words = topic_model.get_topic(topic_num)
    # Extract just the words for labeling
    words_for_labeling = [word for word, _ in topic_words]
    # Assign a label to the topic
    label = assign_ux_labels(words_for_labeling)
    topic_labels[topic_num] = label

# Now topic_labels dictionary contains topic numbers as keys and their assigned UX labels as values
print(topic_labels)
