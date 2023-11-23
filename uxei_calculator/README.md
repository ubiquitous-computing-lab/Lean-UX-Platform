# User Experience Evaluation Index (UXEI) Calculator

## Overview

This repository contains the code for calculating the User Experience Evaluation Index (UXEI), a comprehensive metric designed to evaluate user experience based on various factors such as emotions, sentiments, EEG data, and task success rates. The UXEI provides a quantitative measure to gauge user satisfaction and engagement, making it a valuable tool for UX researchers and developers.

## Features

- **Emotion Analysis**: Processes time-series data to understand user emotions.
- **Sentiment Analysis**: Evaluates user sentiments over time.
- **EEG Data Integration**: Utilizes EEG data to assess user cognitive states.
- **Task Success Rate Calculation**: Determines the success rate of users in completing specific tasks.
- **User Experience Index**: Calculates a comprehensive UXEI based on the above metrics.

## Installation

To use this project, clone the repository and install the necessary dependencies:

```bash
    git https://github.com/ubiquitous-computing-lab/Lean-UX-Platform/tree/master/uxei_calculator

    cd uxei-calculator


## Usage
To run the UXEI calculator, use the following command:

```python 
 python uxei_calculator.py


Make sure to update uxei_calculator.py with your specific data inputs.


## Data Format
The project expects the following data formats:

- **Emotion Data**: JSON format detailing user emotions over time.
- **Sentiment Data**: JSON format with time-stamped user sentiment data.
- **EEG Data**: JSON format containing EEG metrics such as Stress, Interest, etc.
- **Task Data**: JSON format with user task success or failure records.

Example data formats can be found in the data_examples directory.

## Contributing
Contributions to improve the UXEI calculator are welcome. Please follow these steps to contribute:

1. Fork the repository.
2. Create a new branch (git checkout -b feature-branch).
3. Make your changes and commit (git commit -am 'Add some feature').
4. Push to the branch (git push origin feature-branch).
5. Create a new Pull Request.

## License
This project is licensed under the Apache 2 