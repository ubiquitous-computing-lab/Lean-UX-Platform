from bertopic import BERTopic
import pandas as pd

class ReviewTopicModeler:
    def __init__(self, language='english', n_gram_range=(1, 2)):
        self.model = BERTopic(language=language, n_gram_range=n_gram_range)

    def fit_model(self, documents):
        topics, probabilities = self.model.fit_transform(documents)
        return topics, probabilities

    def get_topic_info(self):
        return self.model.get_topic_info()

    def visualize_topics(self):
        return self.model.visualize_topics()

# Example usage:
# Assuming you have a DataFrame 'df' with a column 'review' containing text reviews
modeler = ReviewTopicModeler()

preprocessed_reviews = pd.read_csv('dataset/preprocessed_reviews.csv')

topics, probabilities = modeler.fit_model(preprocessed_reviews['content'])
topic_info = modeler.get_topic_info()
visualization = modeler.visualize_topics()
