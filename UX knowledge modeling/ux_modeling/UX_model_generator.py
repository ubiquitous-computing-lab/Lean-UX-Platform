import networkx as nx
from sklearn.metrics.pairwise import cosine_similarity
from sentence_transformers import SentenceTransformer

# Assuming you have a dictionary where the key is the topic number and the value is the topic name
topic_labels = {0: "Usability", 1: "Functionality", 2: "Aesthetics"}

# Load a pre-trained sentence transformer model (e.g., all-MiniLM-L6-v2)
model = SentenceTransformer('all-MiniLM-L6-v2')

# Extract topic names and convert them to embeddings
topic_names = list(topic_labels.values())
topic_embeddings = model.encode(topic_names)

# Compute the cosine similarity between topic embeddings
similarity_matrix = cosine_similarity(topic_embeddings)

# Create a graph
G = nx.Graph()

# Add nodes (topics) to the graph
for topic, name in topic_labels.items():
    G.add_node(topic, label=name)

# Add edges between nodes if their similarity is above a certain threshold
threshold = 0.5  # Define your own threshold
for i, topic1 in enumerate(topic_labels):
    for j, topic2 in enumerate(topic_labels):
        if i != j and similarity_matrix[i, j] > threshold:
            G.add_edge(topic1, topic2, weight=similarity_matrix[i, j])

# Use a graph algorithm to find the hierarchical structure
# This could be as simple as finding the maximal spanning tree
# or more complex clustering algorithms

# For example, using the maximal spanning tree to define a hierarchy
hierarchy = nx.maximum_spanning_tree(G)

# Print the edges of the hierarchy
for edge in hierarchy.edges(data=True):
    print(f"{topic_labels[edge[0]]} <---> {topic_labels[edge[1]]}, similarity: {edge[2]['weight']:.2f}")

# If you need to visualize the hierarchy
nx.draw(hierarchy, with_labels=True)
