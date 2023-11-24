# UX Modeling

## Overview
It utilizes advanced natural language processing techniques to analyze sentiment and extract key UX aspects from unstructured review text.It employs several Python scripts to scrape, preprocess, analyze, and model topics from app reviews to understand user sentiment and key areas of interest.


### Features
- Sentiment analysis to gauge user sentiment.
- Topic modeling to identify common themes in reviews.
- Generation of UX constructs and hierarchies to structure the feedback.
- Output of actionable insights for UX improvement.



### Requirements
- Python 3.6 or higher
- pandas
- nltk
- google_play_scraper
- BERTopic
- networkx
- scikit-learn
- matplotlib (for visualization)

To install these dependencies, run:
```bash
pip install pandas nltk google-play-scraper bertopic networkx scikit-learn matplotlib



## Usage
To run the project:
``` Usage
# Navigate to the ux_modeling package directory
cd ux_modeling

```

## Scripts and Modules

#### review_scraper.py
Uses google_play_scraper to scrape reviews for specified apps.

### review_preprocessor.py
This script preprocesses the reviews by converting text to lowercase, removing punctuation, and tokenizing the text.

### topic_modeling.py
Applies BERTopic for topic modeling on the reviews to extract key themes.

### UX_model_generator.py
Apply networkx to create a hierarchy after assigning names to the topics. This example assumes you already have a mapping of topics to names and you're looking to organize these into a hierarchy based on their semantic similarity, which could be computed using word embeddings or other similarity measures:



## Contributing
Contributions to improve the UXEI calculator are welcome. Please follow these steps to contribute:

1. Fork the repository.
2. Create a new branch (git checkout -b feature-branch).
3. Make your changes and commit (git commit -am 'Add some feature').
4. Push to the branch (git push origin feature-branch).
5. Create a new Pull Request.

## License
The code is licensed under the [Apache License Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
